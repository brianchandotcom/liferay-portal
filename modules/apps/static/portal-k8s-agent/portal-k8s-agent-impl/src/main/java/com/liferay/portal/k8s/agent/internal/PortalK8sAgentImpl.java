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

package com.liferay.portal.k8s.agent.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.file.install.properties.ConfigurationProperties;
import com.liferay.portal.file.install.properties.ConfigurationPropertiesFactory;
import com.liferay.portal.k8s.agent.PortalK8sExtensionConfigMapModifier;
import com.liferay.portal.k8s.agent.configuration.v1.PortalK8sAgentConfiguration;
import com.liferay.portal.k8s.agent.constants.PortalK8sAgentConstants;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.util.PropsValues;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerEventListener;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.felix.configurator.impl.json.BinUtil;
import org.apache.felix.configurator.impl.json.BinaryManager;
import org.apache.felix.configurator.impl.json.JSONUtil;
import org.apache.felix.configurator.impl.model.ConfigurationFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.k8s.agent.configuration.v1.PortalK8sAgentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "k8sConfiguratPropertiesMutators.cardinality.minimum:Integer=3",
	service = PortalK8sExtensionConfigMapModifier.class
)
public class PortalK8sAgentImpl implements PortalK8sExtensionConfigMapModifier {

	@Activate
	public PortalK8sAgentImpl(
			BundleContext bundleContext,
			@Reference ConfigurationAdmin configurationAdmin,
			@Reference(
				name = "k8sConfiguratPropertiesMutators",
				policyOption = ReferencePolicyOption.GREEDY
			)
			List
				<PortalK8sConfigurationPropertiesMutator>
					k8sConfiguratPropertiesMutators,
			Map<String, Object> properties)
		throws Exception {

		_configurationAdmin = configurationAdmin;
		_k8sConfiguratPropertiesMutators = k8sConfiguratPropertiesMutators;

		_portalK8sAgentConfiguration = ConfigurableUtil.createConfigurable(
			PortalK8sAgentConfiguration.class, properties);

		_bundle = bundleContext.getBundle();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Initializing ", PortalK8sAgentConstants.AGENT_NAME,
					" with: ", properties));
		}

		Config config = Config.empty();

		String protocol = Http.HTTP;

		if (_portalK8sAgentConfiguration.apiServerSSL()) {
			protocol = Http.HTTPS;
		}

		String apiServerHost = _portalK8sAgentConfiguration.apiServerHost();
		int apiServerPort = _portalK8sAgentConfiguration.apiServerPort();

		String apiServerAddress = StringBundler.concat(
			protocol, Http.PROTOCOL_DELIMITER, apiServerHost, StringPool.COLON,
			apiServerPort, StringPool.SLASH);

		String caCertData = _portalK8sAgentConfiguration.caCertData();
		String namespace = _portalK8sAgentConfiguration.namespace();
		String saToken = _portalK8sAgentConfiguration.saToken();

		config.setCaCertData(caCertData);
		config.setMasterUrl(apiServerAddress);
		config.setNamespace(namespace);
		config.setOauthToken(saToken);

		Map<Integer, String> errorMessages = config.getErrorMessages();

		errorMessages.put(401, "Unauthorized! ".concat(_ERROR_MESSAGE));
		errorMessages.put(403, "Forbidden!".concat(_ERROR_MESSAGE));

		Config.configFromSysPropsOrEnvVars(config);

		_kubernetesClient = new DefaultKubernetesClient(config);

		SharedInformerFactory sharedInformerFactory =
			_kubernetesClient.informers();

		sharedInformerFactory.addSharedInformerEventListener(
			new SharedInformerEventListener() {

				@Override
				public void onException(Exception exception) {
					_log.error(exception);
				}

			});

		_sharedIndexInformer = _kubernetesClient.configMaps(
		).inNamespace(
			_portalK8sAgentConfiguration.namespace()
		).withLabel(
			_portalK8sAgentConfiguration.labelSelector()
		).inform(
			new ResourceEventHandler<ConfigMap>() {

				@Override
				public void onAdd(ConfigMap configMap) {
					_add(configMap);
				}

				@Override
				public void onDelete(
					ConfigMap configMap, boolean deletedFinalStateUnknown) {

					_delete(configMap);
				}

				@Override
				public void onUpdate(
					ConfigMap oldConfigMap, ConfigMap newConfigMap) {

					_update(oldConfigMap, newConfigMap);
				}

			}
		);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Initialized ".concat(PortalK8sAgentConstants.AGENT_NAME));
		}
	}

	@Override
	public Result modifyExtensionConfigMap(
		Consumer<Map<String, String>> configMapDataConsumer, String serviceId) {

		Objects.requireNonNull(configMapDataConsumer, "must not be null");
		Objects.requireNonNull(serviceId, "must not be null");

		String configMapName = serviceId.concat(_EXTENSION_CONFIG_MAP_SUFFIX);

		ConfigMap configMap = _kubernetesClient.configMaps(
		).inNamespace(
			_portalK8sAgentConfiguration.namespace()
		).withName(
			configMapName
		).get();

		if (configMap != null) {
			Map<String, String> data = configMap.getData();

			Map<String, String> originalCopy = new TreeMap<>(data);

			configMapDataConsumer.accept(data);

			if (data.isEmpty()) {
				_kubernetesClient.configMaps(
				).delete(
					configMap
				);

				if (_log.isDebugEnabled()) {
					_log.debug("Deleted ".concat(configMap.toString()));
				}

				return Result.DELETED;
			}
			else if (!Objects.equals(data, originalCopy)) {
				configMap = _kubernetesClient.configMaps(
				).withName(
					configMapName
				).createOrReplace(
					configMap
				);

				if (_log.isDebugEnabled()) {
					_log.debug("Updated ".concat(configMap.toString()));
				}

				return Result.UPDATED;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Unchanged ".concat(configMap.toString()));
				}

				return Result.UNCHANGED;
			}
		}
		else {
			Map<String, String> data = new TreeMap<>();

			configMapDataConsumer.accept(data);

			configMap = new ConfigMapBuilder().withNewMetadata(
			).withNamespace(
				_portalK8sAgentConfiguration.namespace()
			).withName(
				configMapName
			).endMetadata(
			).addToData(
				data
			).build();

			configMap = _kubernetesClient.configMaps(
			).withName(
				configMapName
			).createOrReplace(
				configMap
			);

			if (_log.isDebugEnabled()) {
				_log.debug("Created ".concat(configMap.toString()));
			}

			return Result.CREATED;
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isDebugEnabled()) {
			_log.debug("Deactivating " + PortalK8sAgentConstants.AGENT_NAME);
		}

		_sharedIndexInformer.close();
		_kubernetesClient.close();

		if (_log.isDebugEnabled()) {
			_log.debug("Deactivated " + PortalK8sAgentConstants.AGENT_NAME);
		}
	}

	private void _add(ConfigMap configMap) {
		if (_log.isDebugEnabled()) {
			_log.debug("Adding: ".concat(configMap.toString()));
		}

		Map<String, String> data = configMap.getData();

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Data is null, skipping: ".concat(configMap.toString()));
			}

			return;
		}

		Set<Map.Entry<String, String>> entrySet = data.entrySet();

		for (Map.Entry<String, String> entry : entrySet) {
			String configName = entry.getKey();

			try {
				if (configName.endsWith(PortalK8sAgentConstants.FILE_EXT)) {
					_processConfigMapConfigFileEntry(
						configName,
						_fromStringContent(configName, entry.getValue()),
						configMap.getMetadata());
				}
				else if (configName.endsWith(
							PortalK8sAgentConstants.FILE_JSON_EXT)) {

					_processConfigMapConfigJSONResource(
						configName, entry.getValue(), configMap);
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _delete(ConfigMap configMap) {
		if (_log.isDebugEnabled()) {
			_log.debug("Deleting: ".concat(configMap.toString()));
		}

		Map<String, String> data = configMap.getData();

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Data is null, skipping: ".concat(configMap.toString()));
			}

			return;
		}

		ObjectMeta metadata = configMap.getMetadata();

		String configurationFilter = StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, PortalK8sAgentConstants.K8S_CONFIG_UID,
			StringPool.EQUAL, metadata.getUid(), StringPool.CLOSE_PARENTHESIS);

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(configurationFilter);

			if (configurations != null) {
				for (Configuration configuration : configurations) {
					try {
						configuration.delete();
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private Configuration _findExistingConfiguration(String fileName)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				StringPool.OPEN_PARENTHESIS,
				PortalK8sAgentConstants.K8S_CONFIG_KEY, StringPool.EQUAL,
				fileName, StringPool.CLOSE_PARENTHESIS));

		if ((configurations != null) && (configurations.length > 0)) {
			return configurations[0];
		}

		return null;
	}

	private Dictionary<String, Object> _fromStringContent(
			String configName, String configurationContent)
		throws IOException {

		Dictionary<String, Object> dictionary = new Hashtable<>();

		ConfigurationProperties configurationProperties =
			ConfigurationPropertiesFactory.create(
				configName, configurationContent,
				PropsValues.MODULE_FRAMEWORK_FILE_INSTALL_CONFIG_ENCODING);

		for (String key : configurationProperties.keySet()) {
			dictionary.put(key, configurationProperties.get(key));
		}

		return dictionary;
	}

	private Configuration _getConfiguration(String pid, String name)
		throws Exception {

		if (name != null) {
			return _configurationAdmin.getFactoryConfiguration(
				pid, name, StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private String[] _parsePid(String path) {
		String pid = path;

		if (path.endsWith(PortalK8sAgentConstants.FILE_EXT)) {
			pid = path.substring(
				0, path.length() - PortalK8sAgentConstants.FILE_EXT.length());
		}
		else if (path.endsWith(PortalK8sAgentConstants.FILE_JSON_EXT)) {
			pid = path.substring(
				0,
				path.length() - PortalK8sAgentConstants.FILE_JSON_EXT.length());
		}

		int index = pid.indexOf(CharPool.TILDE);

		if (index <= 0) {
			index = pid.indexOf(CharPool.UNDERLINE);

			if (index <= 0) {
				index = pid.indexOf(CharPool.DASH);
			}
		}

		if (index > 0) {
			String name = pid.substring(index + 1);

			pid = pid.substring(0, index);

			return new String[] {pid, name};
		}

		return new String[] {pid, null};
	}

	private void _processConfigMapConfigFileEntry(
			String configName, Dictionary<String, Object> dictionary,
			ObjectMeta metadata)
		throws Exception {

		String resourceVersion = metadata.getResourceVersion();

		String[] pid = _parsePid(configName);

		Configuration configuration = _findExistingConfiguration(configName);

		if (configuration == null) {
			configuration = _getConfiguration(pid[0], pid[1]);
		}
		else {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String existingResourceVersion = (String)properties.get(
				PortalK8sAgentConstants.K8S_CONFIG_RESOURCE_VERSION);

			if (Objects.equals(resourceVersion, existingResourceVersion)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"The resourceVersion of the configuration (",
							existingResourceVersion,
							") is same as that of Kubernetes (",
							resourceVersion,
							") so this action will be ignored"));
				}

				return;
			}
		}

		Set<Configuration.ConfigurationAttribute> configurationAttributes =
			configuration.getAttributes();

		if (configurationAttributes.contains(
				Configuration.ConfigurationAttribute.READ_ONLY)) {

			configuration.removeAttributes(
				Configuration.ConfigurationAttribute.READ_ONLY);
		}

		for (PortalK8sConfigurationPropertiesMutator
				portalK8sConfigurationPropertiesMutator :
					_k8sConfiguratPropertiesMutators) {

			portalK8sConfigurationPropertiesMutator.
				mutateConfigurationProperties(
					metadata.getAnnotations(), metadata.getLabels(),
					dictionary);
		}

		dictionary.put(PortalK8sAgentConstants.K8S_CONFIG_KEY, configName);
		dictionary.put(
			PortalK8sAgentConstants.K8S_CONFIG_UID, metadata.getUid());
		dictionary.put(
			PortalK8sAgentConstants.K8S_CONFIG_RESOURCE_VERSION,
			resourceVersion);

		if (_log.isDebugEnabled()) {
			_log.debug("Created Configuration " + dictionary);
		}

		configuration.updateIfDifferent(dictionary);

		configuration.addAttributes(
			Configuration.ConfigurationAttribute.READ_ONLY);
	}

	private void _processConfigMapConfigJSONResource(
			final String configName, String configurationContent,
			ConfigMap configMap)
		throws Exception {

		URL url = new URL("file", null, configName);

		final JSONUtil.Report report = new JSONUtil.Report();

		BinaryManager binaryManager = new BinaryManager(
			new BinUtil.ResourceProvider() {

				@Override
				public Enumeration<URL> findEntries(
					String path, String filePattern) {

					return Collections.emptyEnumeration();
				}

				@Override
				public long getBundleId() {
					return _bundle.getBundleId();
				}

				@Override
				public URL getEntry(String path) {
					return null;
				}

				@Override
				public String getIdentifier() {
					return configName;
				}

			},
			report);

		ConfigurationFile configurationFile = JSONUtil.readJSON(
			binaryManager, configName, url, _bundle.getBundleId(),
			configurationContent, report);

		for (final String warning : report.warnings) {
			if (_log.isWarnEnabled()) {
				_log.warn(warning);
			}
		}

		for (final String error : report.errors) {
			_log.error(error);
		}

		if (configurationFile == null) {
			return;
		}

		for (org.apache.felix.configurator.impl.model.Config config :
				configurationFile.getConfigurations()) {

			try {
				_processConfigMapConfigFileEntry(
					config.getPid(), config.getProperties(),
					configMap.getMetadata());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _update(ConfigMap oldConfigMap, ConfigMap newConfigMap) {
		if (_log.isDebugEnabled()) {
			_log.debug("Updating: " + newConfigMap);
		}

		Map<String, String> data = newConfigMap.getData();
		ObjectMeta metadata = newConfigMap.getMetadata();

		if (data != null) {
			Set<Map.Entry<String, String>> entrySet = data.entrySet();

			for (Map.Entry<String, String> entry : entrySet) {
				String configName = entry.getKey();

				try {
					if (configName.endsWith(PortalK8sAgentConstants.FILE_EXT)) {
						_processConfigMapConfigFileEntry(
							configName,
							_fromStringContent(configName, entry.getValue()),
							metadata);
					}
					else if (configName.endsWith(
								PortalK8sAgentConstants.FILE_JSON_EXT)) {

						_processConfigMapConfigJSONResource(
							configName, entry.getValue(), newConfigMap);
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}

		// Remove left over configurations which were deleted from the ConfigMap

		ObjectMeta oldMetadata = oldConfigMap.getMetadata();

		String configurationFilter = StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, StringPool.AMPERSAND,
			StringPool.OPEN_PARENTHESIS, PortalK8sAgentConstants.K8S_CONFIG_UID,
			StringPool.EQUAL, metadata.getUid(), StringPool.CLOSE_PARENTHESIS,
			StringPool.OPEN_PARENTHESIS,
			PortalK8sAgentConstants.K8S_CONFIG_RESOURCE_VERSION,
			StringPool.EQUAL, oldMetadata.getResourceVersion(),
			StringPool.CLOSE_PARENTHESIS, StringPool.CLOSE_PARENTHESIS);

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(configurationFilter);

			if (configurations != null) {
				for (Configuration configuration : configurations) {
					try {
						configuration.delete();
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final String _ERROR_MESSAGE =
		"Configured service account does not have access. Service account " +
			"may have been revoked.";

	private static final String _EXTENSION_CONFIG_MAP_SUFFIX =
		"-lxc-ext-init-metadata";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalK8sAgentImpl.class);

	private final Bundle _bundle;
	private final ConfigurationAdmin _configurationAdmin;
	private final List<PortalK8sConfigurationPropertiesMutator>
		_k8sConfiguratPropertiesMutators;
	private final KubernetesClient _kubernetesClient;
	private final PortalK8sAgentConfiguration _portalK8sAgentConfiguration;
	private final SharedIndexInformer<ConfigMap> _sharedIndexInformer;

}