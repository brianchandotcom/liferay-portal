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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch7.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch7.settings.SettingsContributor;

import java.net.InetAddress;

import java.nio.file.Path;

import java.util.Collection;
import java.util.function.Supplier;

import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class ElasticsearchInstanceSettingsBuilder {

	public static ElasticsearchInstanceSettingsBuilder builder() {
		return new ElasticsearchInstanceSettingsBuilder();
	}

	public Settings build() {
		load();

		Settings.Builder builder = _settingsBuilder.getBuilder();

		return builder.build();
	}

	public ElasticsearchInstanceSettingsBuilder clusterInitialMasterNodes(
		String clusterInitialMasterNodes) {

		_clusterInitialMasterNodes = clusterInitialMasterNodes;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder clusterName(
		String clusterName) {

		_clusterName = clusterName;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder discoverySeedHosts(
		String discoverySeedHosts) {

		_discoverySeedHosts = discoverySeedHosts;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder discoveryTypeSingleNode(
		boolean discoveryTypeSingleNode) {

		_discoveryTypeSingleNode = discoveryTypeSingleNode;

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

	public ElasticsearchInstanceSettingsBuilder localBindInetAddressSupplier(
		Supplier<InetAddress> localBindInetAddressSupplier) {

		_localBindInetAddressSupplier = localBindInetAddressSupplier;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder networkHost(
		String networkHost) {

		_networkHost = networkHost;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder nodeName(String nodeName) {
		_nodeName = nodeName;

		return this;
	}

	public ElasticsearchInstanceSettingsBuilder settingsContributors(
		Collection<SettingsContributor> settingsContributors) {

		_settingsContributors = settingsContributors;

		return this;
	}

	public interface LocalBindInetAddressSupplier
		extends Supplier<InetAddress> {
	}

	protected Path getHomePath() {
		Path homePath = _elasticsearchInstancePaths.getHomePath();

		if (homePath != null) {
			return homePath;
		}

		Path workPath = _elasticsearchInstancePaths.getWorkPath();

		return workPath.resolve("data/elasticsearch7");
	}

	protected void load() {
		_loadDefaultConfigurations();

		_loadSidecarConfigurations();

		_loadAdditionalConfigurations();

		_loadSettingsContributors();
	}

	protected void put(String key, boolean value) {
		_settingsBuilder.put(key, value);
	}

	protected void put(String key, String value) {
		_settingsBuilder.put(key, value);
	}

	private void _configureClustering() {
		put("cluster.name", _clusterName);
		put("cluster.routing.allocation.disk.threshold_enabled", false);

		if (!Validator.isBlank(_clusterInitialMasterNodes)) {
			put("cluster.initial_master_nodes", _clusterInitialMasterNodes);
		}

		if (!Validator.isBlank(_discoverySeedHosts)) {
			put("discovery.seed_hosts", _discoverySeedHosts);
		}

		if (_discoveryTypeSingleNode) {
			put("discovery.type", "single-node");
		}
	}

	private void _configureHttp() {
		put("http.port", _httpPortRange.toSettingsString());

		put(
			"http.cors.enabled",
			_elasticsearchConfigurationWrapper.httpCORSEnabled());

		if (!_elasticsearchConfigurationWrapper.httpCORSEnabled()) {
			return;
		}

		put(
			"http.cors.allow-origin",
			_elasticsearchConfigurationWrapper.httpCORSAllowOrigin());

		_settingsBuilder.loadFromSource(
			_elasticsearchConfigurationWrapper.httpCORSConfigurations());
	}

	private void _configureNetworking() {
		String networkBindHost =
			_elasticsearchConfigurationWrapper.networkBindHost();
		String networkHost = _elasticsearchConfigurationWrapper.networkHost();
		String networkPublishHost =
			_elasticsearchConfigurationWrapper.networkPublishHost();

		if (Validator.isNotNull(networkBindHost)) {
			put("network.bind_host", networkBindHost);
		}

		if (!Validator.isBlank(_networkHost)) {
			put("network.host", _networkHost);
		}
		else {
			if (Validator.isNull(networkBindHost) &&
				Validator.isNull(networkHost) &&
				Validator.isNull(networkPublishHost)) {

				InetAddress inetAddress = _localBindInetAddressSupplier.get();

				if (inetAddress != null) {
					networkHost = inetAddress.getHostAddress();
				}
			}

			if (Validator.isNotNull(networkHost)) {
				put("network.host", networkHost);
			}
		}

		if (Validator.isNotNull(networkPublishHost)) {
			put("network.publish_host", networkPublishHost);
		}

		String transportTcpPort =
			_elasticsearchConfigurationWrapper.transportTcpPort();

		if (Validator.isNotNull(transportTcpPort)) {
			put("transport.port", transportTcpPort);
		}
	}

	private void _configurePaths() {
		Path workPath = _elasticsearchInstancePaths.getWorkPath();

		Path dataParentPath = workPath.resolve("data/elasticsearch7");

		Path homePath = getHomePath();

		put("path.data", String.valueOf(dataParentPath.resolve("indices")));

		put("path.home", String.valueOf(homePath.toAbsolutePath()));

		put("path.logs", String.valueOf(workPath.resolve("logs")));

		put("path.repo", String.valueOf(dataParentPath.resolve("repo")));
	}

	private void _configureTestMode() {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		put("monitor.jvm.gc.enabled", StringPool.FALSE);
	}

	private void _disableGeoipDownloader() {
		put("ingest.geoip.downloader.enabled", false);
	}

	private void _disableXpack() {
		put("xpack.ml.enabled", false);
		put("xpack.monitoring.enabled", false);
		put("xpack.security.enabled", false);
		put("xpack.sql.enabled", false);
		put("xpack.watcher.enabled", false);
	}

	private void _loadAdditionalConfigurations() {
		_settingsBuilder.loadFromSource(
			_elasticsearchConfigurationWrapper.additionalConfigurations());
	}

	private void _loadDefaultConfigurations() {
		String defaultConfigurations = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/elasticsearch-optional-defaults.yml");

		_settingsBuilder.loadFromSource(defaultConfigurations);

		put("action.auto_create_index", false);
		put(
			"bootstrap.memory_lock",
			_elasticsearchConfigurationWrapper.bootstrapMlockAll());

		_configureClustering();

		_configureHttp();

		_configureNetworking();

		put("node.data", true);
		put("node.ingest", true);
		put("node.master", true);
		put("node.name", _nodeName);

		_configurePaths();

		_configureTestMode();

		_disableGeoipDownloader();

		_disableXpack();
	}

	private void _loadSettingsContributors() {
		ClientSettingsHelper clientSettingsHelper = new ClientSettingsHelper() {

			@Override
			public void put(String setting, String value) {
				_settingsBuilder.put(setting, value);
			}

			@Override
			public void putArray(String setting, String... values) {
				_settingsBuilder.putList(setting, values);
			}

		};

		for (SettingsContributor settingsContributor : _settingsContributors) {
			settingsContributor.populate(clientSettingsHelper);
		}
	}

	private void _loadSidecarConfigurations() {
		put("bootstrap.system_call_filter", false);
		put("node.store.allow_mmap", false);
	}

	private String _clusterInitialMasterNodes;
	private String _clusterName;
	private String _discoverySeedHosts;
	private boolean _discoveryTypeSingleNode;
	private ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private ElasticsearchInstancePaths _elasticsearchInstancePaths;
	private HttpPortRange _httpPortRange;
	private Supplier<InetAddress> _localBindInetAddressSupplier;
	private String _networkHost;
	private String _nodeName;
	private final SettingsBuilder _settingsBuilder = new SettingsBuilder(
		Settings.builder());
	private Collection<SettingsContributor> _settingsContributors;

}