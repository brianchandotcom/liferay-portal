/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lpkg.deployer.internal;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.local.LocalProcessExecutor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.lpkg.deployer.LPKGVerifyException;
import com.liferay.portal.target.platform.indexer.IndexValidator;
import com.liferay.portal.target.platform.indexer.IndexValidatorFactory;
import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.IndexerFactory;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = LPKGIndexValidator.class)
public class LPKGIndexValidator {

	@Activate
	public void activate(BundleContext bundleContext) {
		_enabled = GetterUtil.getBoolean(
			bundleContext.getProperty("lpkg.index.validator.enabled"), true);

		_processConfig = PortalClassPathUtil.createProcessConfig(
			IndexerFactory.class, _indexerFactory.getClass(), Bundle.class,
			TargetPlatformIndexerProcessCallable.class);
	}

	public boolean checkIntegrity(List<URI> indexURIs) {
		if (Files.notExists(_integrityPropertiesFilePath)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip integrity check because " +
						_integrityPropertiesFilePath + " does not exist");
			}

			return false;
		}

		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(
				_integrityPropertiesFilePath)) {

			properties.load(inputStream);
		}
		catch (IOException ioe) {
			_log.error("Unable to read " + _integrityPropertiesFilePath, ioe);

			return false;
		}

		Set<String> integrityKeys = new HashSet<>();

		for (URI uri : indexURIs) {
			integrityKeys.add(_toIntegrityKey(uri));
		}

		if (!integrityKeys.equals(properties.stringPropertyNames())) {
			if (_log.isInfoEnabled()) {
				List<String> expectedKeys = new ArrayList<>(
					properties.stringPropertyNames());

				Collections.sort(expectedKeys);

				List<String> actualKeys = new ArrayList<>(integrityKeys);

				Collections.sort(actualKeys);

				_log.info(
					StringBundler.concat(
						"Running validation because expected keys: ",
						String.valueOf(expectedKeys),
						" do not match actual keys: ",
						String.valueOf(actualKeys)));
			}

			return false;
		}

		for (URI uri : indexURIs) {
			String integrityKey = _toIntegrityKey(uri);

			try {
				String expectedChecksum = properties.getProperty(integrityKey);

				String actualChecksum = _toChecksum(uri);

				if (!Objects.equals(expectedChecksum, actualChecksum)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Running validation because of mismatched " +
								"checksum for " + integrityKey);
					}

					return false;
				}
			}
			catch (Exception e) {
				_log.error("Unable to generate checksum for " + uri);

				return false;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Passed integrity check");
		}

		return true;
	}

	public void setJarFiles(List<File> jarFiles) {
		_jarFiles = jarFiles;

		Set<String> jarFileNames = new HashSet<>();

		for (File file : jarFiles) {
			jarFileNames.add(StringUtil.toLowerCase(file.getName()));
		}

		_jarFileNames = jarFileNames;
	}

	public void setLPKGDeployer(LPKGDeployer lpkgDeployer) {
		_lpkgDeployer = lpkgDeployer;
	}

	public void updateIntegrityProperties() {
		try {
			List<URI> indexURIs = _getTargetPlatformIndexURIs();

			Collections.sort(indexURIs);

			StringBundler sb = new StringBundler(indexURIs.size() * 4);

			for (URI uri : indexURIs) {
				sb.append(_toIntegrityKey(uri));
				sb.append(StringPool.EQUAL);
				sb.append(_toChecksum(uri));
				sb.append(StringPool.NEW_LINE);
			}

			sb.setIndex(sb.index() - 1);

			Files.createDirectories(_integrityPropertiesFilePath.getParent());

			Files.write(
				_integrityPropertiesFilePath,
				Collections.singleton(sb.toString()), StandardCharsets.UTF_8);

			if (_log.isInfoEnabled()) {
				_log.info("Updated " + _integrityPropertiesFilePath);
			}
		}
		catch (Exception e) {
			_log.error("Unable to update integrity properties", e);
		}
	}

	public boolean validate(List<File> lpkgFiles) throws Exception {
		if (!_enabled) {
			return false;
		}

		long start = System.currentTimeMillis();

		List<URI> allIndexURIs = new ArrayList<>();

		List<URI> targetPlatformIndexURIs = _getTargetPlatformIndexURIs();

		allIndexURIs.addAll(targetPlatformIndexURIs);

		List<URI> lpkgIndexURIs = _indexLPKGFiles(lpkgFiles);

		allIndexURIs.addAll(lpkgIndexURIs);

		if (checkIntegrity(allIndexURIs)) {
			return false;
		}

		IndexValidator indexValidator = _indexValidatorFactory.create(
			targetPlatformIndexURIs);

		try {
			List<String> messages = indexValidator.validate(lpkgIndexURIs);

			if (!messages.isEmpty()) {
				StringBundler sb = new StringBundler((messages.size() * 3) + 1);

				sb.append("LPKG validation failed with {");

				for (String message : messages) {
					sb.append("[");
					sb.append(message);
					sb.append("], ");
				}

				sb.setIndex(sb.index() - 1);

				sb.append("]}");

				throw new LPKGVerifyException(sb.toString());
			}
		}
		finally {
			_cleanUp(targetPlatformIndexURIs);

			_cleanUp(lpkgIndexURIs);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - start;

				_log.info(
					String.format(
						"LPKG validation time %02d:%02ds",
						MILLISECONDS.toMinutes(duration),
						MILLISECONDS.toSeconds(duration % Time.MINUTE)));
			}
		}

		return true;
	}

	private void _cleanUp(List<URI> uris) throws MalformedURLException {
		for (URI uri : uris) {
			_bytesURLProtocolSupport.removeBytes(uri.toURL());
		}
	}

	private List<URI> _getTargetPlatformIndexURIs() throws Exception {
		List<File> files = new ArrayList<>();

		Map<Bundle, List<Bundle>> deployedLPKGBundles =
			_lpkgDeployer.getDeployedLPKGBundles();

		for (Bundle bundle : deployedLPKGBundles.keySet()) {
			files.add(new File(bundle.getLocation()));
		}

		List<URI> uris = _indexLPKGFiles(files);

		LocalProcessExecutor localProcessExecutor = new LocalProcessExecutor();

		List<File> additionalJarFiles = new ArrayList<>(_jarFiles);

		additionalJarFiles.add(
			new File(PropsValues.LIFERAY_LIB_PORTAL_DIR, "util-taglib.jar"));

		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.modules.compat.internal.configuration." +
				"ModuleCompatExtenderConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		boolean enabled = true;

		if (properties != null) {
			enabled = Boolean.valueOf((String)properties.get("enabled"));
		}

		if (enabled) {
			File file = new File(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR,
				"compat/com.liferay.modules.compat.data.jar");

			if (file.exists()) {
				additionalJarFiles.add(file);
			}
		}

		ProcessChannel<byte[]> processChannel = localProcessExecutor.execute(
			_processConfig,
			new TargetPlatformIndexerProcessCallable(
				additionalJarFiles,
				PropsValues.MODULE_FRAMEWORK_STOP_WAIT_TIMEOUT,
				PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/static",
				PropsValues.MODULE_FRAMEWORK_MODULES_DIR,
				PropsValues.MODULE_FRAMEWORK_PORTAL_DIR));

		Future<byte[]> future = processChannel.getProcessNoticeableFuture();

		URL url = _bytesURLProtocolSupport.putBytes(
			"liferay-target-platform", future.get());

		uris.add(url.toURI());

		return uris;
	}

	private List<URI> _indexLPKGFiles(List<File> lpkgFiles) throws Exception {
		List<URI> uris = new ArrayList<>();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try {
			for (File lpkgFile : lpkgFiles) {
				Indexer indexer = _indexerFactory.createLPKGIndexer(
					lpkgFile, _jarFileNames);

				indexer.index(unsyncByteArrayOutputStream);

				String name = lpkgFile.getName();

				URL url = _bytesURLProtocolSupport.putBytes(
					name.substring(0, name.length() - 5),
					unsyncByteArrayOutputStream.toByteArray());

				unsyncByteArrayOutputStream.reset();

				uris.add(url.toURI());
			}
		}
		catch (Exception e) {
			_cleanUp(uris);

			throw e;
		}

		return uris;
	}

	private String _toChecksum(URI uri) throws Exception {
		URL url = uri.toURL();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		StreamUtil.transfer(url.openStream(), unsyncByteArrayOutputStream);

		String content = unsyncByteArrayOutputStream.toString(StringPool.UTF8);

		Matcher matcher = _incrementPattern.matcher(content);

		if (matcher.find()) {
			String start = content.substring(0, matcher.start(1));
			String end = content.substring(matcher.end(1));

			content = start.concat(end);
		}

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		messageDigest.update(content.getBytes(StandardCharsets.UTF_8));

		return StringUtil.bytesToHexString(messageDigest.digest());
	}

	private String _toIntegrityKey(URI uri) {
		String integrityKey = uri.getPath();

		int index = integrityKey.lastIndexOf(StringPool.SLASH);

		if (index != -1) {
			integrityKey = integrityKey.substring(index + 1);
		}

		return integrityKey;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGIndexValidator.class);

	private static final Pattern _incrementPattern = Pattern.compile(
		"<repository( increment=\"\\d*\")");

	@Reference
	private BytesURLProtocolSupport _bytesURLProtocolSupport;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private boolean _enabled;

	@Reference
	private IndexerFactory _indexerFactory;

	@Reference
	private IndexValidatorFactory _indexValidatorFactory;

	private final Path _integrityPropertiesFilePath = Paths.get(
		PropsValues.MODULE_FRAMEWORK_BASE_DIR, Indexer.DIR_NAME_TARGET_PLATFORM,
		"integrity.properties");
	private Set<String> _jarFileNames;
	private List<File> _jarFiles;
	private LPKGDeployer _lpkgDeployer;
	private ProcessConfig _processConfig;

}