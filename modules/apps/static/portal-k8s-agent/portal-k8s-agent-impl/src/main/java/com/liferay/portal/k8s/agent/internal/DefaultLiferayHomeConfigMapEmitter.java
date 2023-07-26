/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalInetSocketAddressEventListener;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gregory Amerson
 */
@Component(
	property = "service.ranking:Integer=-1",
	service = PortalK8sConfigMapModifier.class
)
public class DefaultLiferayHomeConfigMapEmitter
	implements PortalInetSocketAddressEventListener,
			   PortalK8sConfigMapModifier {

	@Override
	public Result modifyConfigMap(
		Consumer<PortalK8sConfigMapModifier.ConfigMapModel>
			configMapModelConsumer,
		String configMapName) {

		Objects.requireNonNull(
			configMapModelConsumer, "Config map model consumer is null");

		Map<String, String> annotations = new TreeMap<>();
		Map<String, String> binaryData = new TreeMap<>();
		Map<String, String> data = new TreeMap<>();
		Map<String, String> labels = new TreeMap<>();

		configMapModelConsumer.accept(
			new ConfigMapModel() {

				@Override
				public Map<String, String> annotations() {
					return annotations;
				}

				@Override
				public Map<String, String> binaryData() {
					return binaryData;
				}

				@Override
				public Map<String, String> data() {
					return data;
				}

				@Override
				public Map<String, String> labels() {
					return labels;
				}

			});

		if (binaryData.isEmpty() && data.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Config map does not exist and no data was supplied ",
						"for ", configMapName, " resulting in no change"));
			}

			try {
				_deleteCXMetadata(configMapName, labels);
			}
			catch (Exception exception) {
				_log.error(
					"Could not delete configMap metadata " + configMapName,
					exception);
			}

			return Result.UNCHANGED;
		}

		_updateForPortalLocalPort(data, _getPortalLocalPort());

		try {
			_writeCXMetadata(data, labels);
		}
		catch (Exception exception) {
			_log.error("Unable to write CX metadata", exception);
		}

		return Result.CREATED;
	}

	@Override
	public void portalLocalInetSocketAddressConfigured(
		InetSocketAddress localInetSocketAddress, boolean secure) {

		_updateDxpMetadata(secure);
	}

	@Override
	public void portalServerInetSocketAddressConfigured(
		InetSocketAddress serverInetSocketAddress, boolean secure) {

		_updateDxpMetadata(secure);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistrations.add(
			bundleContext.registerService(
				PortalInetSocketAddressEventListener.class, this, null));

		_serviceRegistrations.add(
			bundleContext.registerService(
				ModelListener.class, new CompanyModelListener(), null));
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private void _deleteCXMetadata(
			String configMapName, Map<String, String> labels)
		throws Exception {

		Path cxMetadataPath = _getCXMetadataPath();

		String virtualInstanceId = labels.get(
			"dxp.lxc.liferay.com/virtualInstanceId");

		if ((cxMetadataPath == null) || (virtualInstanceId == null)) {
			return;
		}

		if (Objects.equals(
				virtualInstanceId, PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			virtualInstanceId = "default";
		}

		Path virtualInstanceIdPath = cxMetadataPath.resolve(virtualInstanceId);

		Matcher matcher = _lxcDxpMetadataPattern.matcher(configMapName);

		if (matcher.matches()) {
			if (Files.exists(virtualInstanceIdPath)) {
				_deleteFolder(virtualInstanceIdPath);
			}
		}
		else {
			matcher = _lxcExtInitMetadataPattern.matcher(configMapName);

			String projectName = labels.get("ext.lxc.liferay.com/projectName");

			if (matcher.matches() && (projectName != null)) {
				Path projectPath = virtualInstanceIdPath.resolve(projectName);

				if (Files.exists(projectPath)) {
					_deleteFolder(projectPath);
				}
			}
		}
	}

	private void _deleteFolder(Path folderPath) throws Exception {
		if (!Files.exists(folderPath)) {
			return;
		}

		Files.walkFileTree(
			folderPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					if (ioException != null) {
						throw ioException;
					}

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private Path _getCXMetadataPath() {
		String liferayHome = System.getProperty("liferay.home");

		if (!FileUtil.exists(liferayHome)) {
			return null;
		}

		Path cxMetadataPath = Paths.get(liferayHome, "cx-metadata");

		try {
			cxMetadataPath = Files.createDirectories(cxMetadataPath);
		}
		catch (IOException ioException) {
			_log.error("Could not create CX Metadata path", ioException);
		}

		return cxMetadataPath;
	}

	private int _getPortalLocalPort() {
		return _portal.getPortalLocalPort(
			Objects.equals(_getWebServerProtocol(), "https"));
	}

	private String _getWebServerProtocol() {
		String webServerProtocol = PropsValues.WEB_SERVER_PROTOCOL;

		if (Validator.isNull(webServerProtocol)) {
			return Http.HTTP;
		}

		return webServerProtocol;
	}

	private void _updateDxpMetadata(boolean secure) {
		try {
			Files.walkFileTree(
				_getCXMetadataPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (Objects.equals(
								String.valueOf(path.getFileName()),
								"dxp-metadata")) {

							File dir = path.toFile();

							Map<String, String> data = new HashMap<>();

							for (File file : dir.listFiles()) {
								data.put(
									file.getName(),
									new String(
										Files.readAllBytes(file.toPath())));
							}

							_updateForPortalLocalPort(
								data, _portal.getPortalLocalPort(secure));

							data.forEach(
								(key, value) -> {
									Path file = path.resolve(key);

									try {
										Files.write(file, value.getBytes());
									}
									catch (IOException ioException) {
										_log.error(
											"Unable to write file " +
												file.toString(),
											ioException);
									}
								});

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			_log.error("Unable to update DXP Metadata", ioException);
		}
	}

	private void _updateForPortalLocalPort(
		Map<String, String> labels, int portalLocalPort) {

		if (portalLocalPort <= 0) {
			return;
		}

		String lxcDXPMainDomain = labels.get("com.liferay.lxc.dxp.mainDomain");

		if ((lxcDXPMainDomain != null) &&
			(lxcDXPMainDomain.indexOf(':') == -1)) {

			labels.put(
				"com.liferay.lxc.dxp.mainDomain",
				lxcDXPMainDomain + ":" + portalLocalPort);
		}

		List<String> lxcDXPDomains = StringUtil.split(
			labels.get("com.liferay.lxc.dxp.domains"), CharPool.NEW_LINE);

		if (!lxcDXPDomains.isEmpty()) {
			List<String> updatedLxcDXPDomains = new ArrayList<>();

			for (String lxcDXPDomain : lxcDXPDomains) {
				if ((lxcDXPDomain != null) &&
					(lxcDXPDomain.indexOf(":") == -1)) {

					updatedLxcDXPDomains.add(
						lxcDXPDomain + ":" + portalLocalPort);
				}
				else {
					updatedLxcDXPDomains.add(lxcDXPDomain);
				}
			}

			labels.put(
				"com.liferay.lxc.dxp.domains",
				StringUtil.merge(updatedLxcDXPDomains, StringPool.NEW_LINE));
		}
	}

	private void _writeCXData(Path dataPath, Map<String, String> data) {
		data.forEach(
			(key, value) -> {
				Path keyPath = dataPath.resolve(key);

				try {
					Files.write(
						keyPath, value.getBytes(), StandardOpenOption.CREATE);
				}
				catch (IOException ioException) {
					_log.error("Unable to write CX data", ioException);
				}
			});
	}

	private void _writeCXMetadata(
			Map<String, String> data, Map<String, String> labels)
		throws Exception {

		Path cxMetadataPath = _getCXMetadataPath();

		if (cxMetadataPath == null) {
			return;
		}

		String metadataType = labels.get("lxc.liferay.com/metadataType");

		String virtualInstanceId = labels.get(
			"dxp.lxc.liferay.com/virtualInstanceId");

		if ((metadataType == null) || (virtualInstanceId == null)) {
			return;
		}

		if (Objects.equals(
				virtualInstanceId, PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			virtualInstanceId = "default";
		}

		Path virtualInstanceIdPath = cxMetadataPath.resolve(virtualInstanceId);

		Files.createDirectories(virtualInstanceIdPath);

		Path dxpMetadataPath = virtualInstanceIdPath.resolve("dxp-metadata");

		if (Objects.equals(metadataType, "dxp")) {
			Files.createDirectories(dxpMetadataPath);

			_writeCXData(dxpMetadataPath, data);
		}
		else if (Objects.equals(metadataType, "ext-init")) {
			String projectName = labels.get("ext.lxc.liferay.com/projectName");

			Path projectPath = virtualInstanceIdPath.resolve(projectName);

			Files.createDirectories(projectPath);

			_writeCXData(projectPath, data);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLiferayHomeConfigMapEmitter.class);

	private static final Pattern _lxcDxpMetadataPattern = Pattern.compile(
		"(.*)-lxc-dxp-metadata$");
	private static final Pattern _lxcExtInitMetadataPattern = Pattern.compile(
		"(.*)-lxc-ext-init-metadata$");

	@Reference
	private Portal _portal;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

	private static class CompanyModelListener
		extends BaseModelListener<Company> {

		@Override
		public void onAfterRemove(Company company)
			throws ModelListenerException {

			if (Objects.equals(
					company.getWebId(), PropsValues.COMPANY_DEFAULT_WEB_ID)) {

				return;
			}

			PortalK8sConfigMapModifier portalK8sConfigMapModifier =
				_portalK8sConfigMapModifierSnapshot.get();

			portalK8sConfigMapModifier.modifyConfigMap(
				configMapModel -> {
					Map<String, String> data = configMapModel.data();

					data.clear();

					Map<String, String> labels = configMapModel.labels();

					labels.put(
						"dxp.lxc.liferay.com/virtualInstanceId",
						company.getWebId());
				},
				_getConfigMapName(company));
		}

		private String _getConfigMapName(Company company) {
			return company.getWebId() + "-lxc-dxp-metadata";
		}

		private static final Snapshot<PortalK8sConfigMapModifier>
			_portalK8sConfigMapModifierSnapshot = new Snapshot<>(
				CompanyModelListener.class, PortalK8sConfigMapModifier.class,
				null, true);

	}

}