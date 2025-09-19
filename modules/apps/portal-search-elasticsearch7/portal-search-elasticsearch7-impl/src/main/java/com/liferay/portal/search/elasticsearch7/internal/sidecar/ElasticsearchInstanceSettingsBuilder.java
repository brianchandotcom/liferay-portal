/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsHelperImpl;
import com.liferay.portal.search.elasticsearch7.internal.sidecar.constants.SidecarConstants;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;

import java.nio.file.Path;

import java.util.List;

import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class ElasticsearchInstanceSettingsBuilder {

	public static ElasticsearchInstanceSettingsBuilder builder() {
		return new ElasticsearchInstanceSettingsBuilder();
	}

	public Settings build() {
		String defaultConfigurations = ResourceUtil.getResourceAsString(
			getClass(),
			SidecarConstants.ELASTICSEARCH_OPTIONAL_DEFAULTS_FILE_NAME);

		_settingsHelperImpl.loadFromSource(defaultConfigurations);

		_settingsHelperImpl.put("action.auto_create_index", false);
		_settingsHelperImpl.put(
			"bootstrap.memory_lock",
			_elasticsearchConfigurationWrapper.bootstrapMlockAll());

		_configureClustering();

		_configureHttp();

		_configureNetworking();

		_settingsHelperImpl.put("node.name", _nodeName);
		_settingsHelperImpl.put(
			"node.roles", List.of("master", "ingest", "data"));

		_configurePaths();

		if (JavaDetector.isJDK21()) {
			_settingsHelperImpl.put("thread_pool.warmer.max", "20");
		}

		_settingsHelperImpl.put("node.store.allow_mmap", false);

		_settingsHelperImpl.loadFromSource(
			_elasticsearchConfigurationWrapper.additionalConfigurations());

		return _settingsHelperImpl.build();
	}

	public ElasticsearchInstanceSettingsBuilder clusterName(
		String clusterName) {

		_clusterName = clusterName;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder
		elasticsearchConfigurationWrapper(
			ElasticsearchConfigurationWrapper
				elasticsearchConfigurationWrapper) {

		_elasticsearchConfigurationWrapper = elasticsearchConfigurationWrapper;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder elasticsearchInstancePaths(
		ElasticsearchInstancePaths elasticsearchInstancePaths) {

		_elasticsearchInstancePaths = elasticsearchInstancePaths;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder httpPortRange(
		HttpPortRange httpPortRange) {

		_httpPortRange = httpPortRange;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder nodeName(String nodeName) {
		_nodeName = nodeName;

		return this;
	}

	private void _configureClustering() {
		_settingsHelperImpl.put("cluster.name", _clusterName);
		_settingsHelperImpl.put(
			"cluster.routing.allocation.disk.threshold_enabled", false);
		_settingsHelperImpl.put("discovery.type", "single-node");
	}

	private void _configureHttp() {
		_settingsHelperImpl.put("http.port", _httpPortRange.toSettingsString());

		_settingsHelperImpl.put(
			"http.cors.enabled",
			_elasticsearchConfigurationWrapper.httpCORSEnabled());

		if (!_elasticsearchConfigurationWrapper.httpCORSEnabled()) {
			return;
		}

		_settingsHelperImpl.put(
			"http.cors.allow-origin",
			_elasticsearchConfigurationWrapper.httpCORSAllowOrigin());

		_settingsHelperImpl.loadFromSource(
			_elasticsearchConfigurationWrapper.httpCORSConfigurations());
	}

	private void _configureNetworking() {
		String networkBindHost =
			_elasticsearchConfigurationWrapper.networkBindHost();
		String networkHost = _elasticsearchConfigurationWrapper.networkHost();
		String networkPublishHost =
			_elasticsearchConfigurationWrapper.networkPublishHost();

		if (Validator.isNotNull(networkBindHost)) {
			_settingsHelperImpl.put("network.bind_host", networkBindHost);
		}

		if (Validator.isNotNull(networkHost)) {
			_settingsHelperImpl.put("network.host", networkHost);
		}

		if (Validator.isNotNull(networkPublishHost)) {
			_settingsHelperImpl.put("network.publish_host", networkPublishHost);
		}

		String transportTcpPort =
			_elasticsearchConfigurationWrapper.transportTcpPort();

		if (Validator.isNotNull(transportTcpPort)) {
			_settingsHelperImpl.put("transport.port", transportTcpPort);
		}
	}

	private void _configurePaths() {
		Path workPath = _elasticsearchInstancePaths.getWorkPath();

		Path dataParentPath = workPath.resolve("data/elasticsearch7");

		Path homePath = _elasticsearchInstancePaths.getHomePath();

		if (homePath == null) {
			homePath = workPath.resolve("data/elasticsearch7");
		}

		_settingsHelperImpl.put(
			"path.data", String.valueOf(dataParentPath.resolve("indices")));

		_settingsHelperImpl.put(
			"path.home", String.valueOf(homePath.toAbsolutePath()));

		_settingsHelperImpl.put(
			"path.logs", String.valueOf(workPath.resolve("logs")));

		_settingsHelperImpl.put(
			"path.repo", String.valueOf(dataParentPath.resolve("repo")));
	}

	private String _clusterName;
	private ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private ElasticsearchInstancePaths _elasticsearchInstancePaths;
	private HttpPortRange _httpPortRange;
	private String _nodeName;
	private final SettingsHelperImpl _settingsHelperImpl =
		new SettingsHelperImpl(Settings.builder());

}