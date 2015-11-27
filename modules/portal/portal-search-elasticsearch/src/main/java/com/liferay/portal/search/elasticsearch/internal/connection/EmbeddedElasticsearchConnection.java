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

package com.liferay.portal.search.elasticsearch.internal.connection;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.connection.BaseElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.OperationMode;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.net.InetAddress;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.plugin.analysis.icu.AnalysisICUPlugin;
import org.elasticsearch.plugin.analysis.kuromoji.AnalysisKuromojiPlugin;
import org.elasticsearch.plugin.analysis.smartcn.AnalysisSmartChinesePlugin;
import org.elasticsearch.plugin.analysis.stempel.AnalysisStempelPlugin;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = {"operation.mode=EMBEDDED"},
	service = ElasticsearchConnection.class
)
public class EmbeddedElasticsearchConnection
	extends BaseElasticsearchConnection {

	@Override
	public void close() {
		super.close();

		if (_node == null) {
			return;
		}

		_node.close();

		_node = null;
	}

	public Node getNode() {
		return _node;
	}

	@Override
	public OperationMode getOperationMode() {
		return OperationMode.EMBEDDED;
	}

	@Override
	@Reference(unbind = "-")
	public void setIndexFactory(IndexFactory indexFactory) {
		super.setIndexFactory(indexFactory);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);
	}

	@Override
	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(operation.mode=EMBEDDED)"
	)
	protected void addSettingsContributor(
		SettingsContributor settingsContributor) {

		super.addSettingsContributor(settingsContributor);
	}

	protected void configureClustering(Settings.Builder builder) {
		builder.put("cluster.name", elasticsearchConfiguration.clusterName());
		builder.put("discovery.zen.ping.multicast.enabled", false);
	}

	protected void configureHttp(Settings.Builder builder) {
		builder.put("http.enabled", elasticsearchConfiguration.httpEnabled());

		if (!elasticsearchConfiguration.httpEnabled()) {
			return;
		}

		builder.put(
			"http.cors.enabled", elasticsearchConfiguration.httpCORSEnabled());

		if (!elasticsearchConfiguration.httpCORSEnabled()) {
			return;
		}

		String[] httpCORSConfigurations =
			elasticsearchConfiguration.httpCORSConfigurations();

		if (ArrayUtil.isEmpty(httpCORSConfigurations)) {
			return;
		}

		for (String httpCORSConfiguration : httpCORSConfigurations) {
			String[] httpCORSConfigurationPair = StringUtil.split(
				httpCORSConfiguration, StringPool.EQUAL);

			if (httpCORSConfigurationPair.length < 2) {
				continue;
			}

			builder.put(
				httpCORSConfigurationPair[0], httpCORSConfigurationPair[1]);
		}
	}

	protected void configurePaths(Settings.Builder builder) {
		builder.put(
			"path.data",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/indices");
		builder.put(
			"path.home",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch");
		builder.put("path.logs", _props.get(PropsKeys.LIFERAY_HOME) + "/logs");
		builder.put(
			"path.plugins",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/plugins");
		builder.put(
			"path.repo",
			_props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/repo");
		builder.put(
			"path.work", SystemProperties.get(SystemProperties.TMP_DIR));
	}

	protected void configurePlugins(Builder builder) {
		builder.putArray(
			"plugin.types", AnalysisICUPlugin.class.getName(),
			AnalysisKuromojiPlugin.class.getName(),
			AnalysisSmartChinesePlugin.class.getName(),
			AnalysisStempelPlugin.class.getName());
	}

	@Override
	protected Client createClient(Settings.Builder builder) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Starting embedded Elasticsearch cluster " +
					elasticsearchConfiguration.clusterName());
		}

		NodeBuilder nodeBuilder = NodeBuilder.nodeBuilder();

		nodeBuilder.settings(builder);

		_node = nodeBuilder.node();

		_node.start();

		Client client = _node.client();

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				"Finished starting " +
					elasticsearchConfiguration.clusterName() + " in " +
					stopWatch.getTime() + " ms");
		}

		return client;
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
		close();
	}

	@Override
	protected void loadRequiredDefaultConfigurations(Settings.Builder builder) {
		builder.put(
			"bootstrap.mlockall",
			elasticsearchConfiguration.bootstrapMlockAll());
		configureClustering(builder);

		configureHttp(builder);

		builder.put("index.number_of_replicas", 0);
		builder.put("index.number_of_shards", 1);

		configureNetworking(builder);

		builder.put("node.client", false);
		builder.put("node.data", true);
		builder.put("node.local", true);

		configurePaths(builder);

		configurePlugins(builder);

		if (PortalRunMode.isTestMode()) {
			builder.put("index.refresh_interval", "1ms");
			builder.put("index.translog.flush_threshold_ops", "1");
			builder.put("index.translog.interval", "1ms");
		}
	}

	@Override
	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		super.removeSettingsContributor(settingsContributor);
	}

	@Reference(unbind = "-")
	protected void setClusterSettingsContext(
		ClusterSettingsContext clusterSettingsContext) {

		_clusterSettingsContext = clusterSettingsContext;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private void configureNetworking(Settings.Builder builder) {
		String networkBindHost = elasticsearchConfiguration.networkBindHost();

		if (Validator.isNotNull(networkBindHost)) {
			builder.put("network.bind.host", networkBindHost);
		}

		String networkHost = elasticsearchConfiguration.networkHost();

		if (Validator.isNull(networkBindHost) &&
			Validator.isNull(networkHost) &&
			Validator.isNull(elasticsearchConfiguration.networkPublishHost())) {

			InetAddress localBindInetAddress =
				_clusterSettingsContext.getLocalBindInetAddress();

			if (localBindInetAddress != null) {
				networkHost = localBindInetAddress.getHostAddress();
			}
		}

		if (Validator.isNotNull(networkHost)) {
			builder.put("network.host", networkHost);
		}

		String networkPublishHost =
			elasticsearchConfiguration.networkPublishHost();

		if (Validator.isNotNull(networkPublishHost)) {
			builder.put("network.publish.host", networkPublishHost);
		}

		String transportTcpPort = elasticsearchConfiguration.transportTcpPort();

		if (Validator.isNotNull(transportTcpPort)) {
			builder.put("transport.tcp.port", transportTcpPort);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private volatile ClusterSettingsContext _clusterSettingsContext;
	private Node _node;
	private volatile Props _props;

}