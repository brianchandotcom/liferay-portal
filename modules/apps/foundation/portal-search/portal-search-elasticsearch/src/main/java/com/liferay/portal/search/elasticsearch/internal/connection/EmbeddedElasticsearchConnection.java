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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.OperationMode;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.io.IOException;

import java.net.InetAddress;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.DiscoveryService;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchService;
import org.elasticsearch.search.action.SearchServiceTransportAction;
import org.elasticsearch.search.internal.ShardSearchTransportRequest;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportChannel;
import org.elasticsearch.transport.TransportRequestHandler;
import org.elasticsearch.transport.TransportService;

import org.jboss.netty.util.internal.ByteBufferUtil;

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

		try {
			Class.forName(ByteBufferUtil.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to preload " + ByteBufferUtil.class +
						" to prevent Netty shutdown concurrent class loading " +
							"interruption issue",
					cnfe);
			}
		}

		_node.close();

		_node = null;

		_file.deltree(_jnaTmpDir);
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
		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
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

	protected void configureClustering() {
		settingsBuilder.put(
			"cluster.name", elasticsearchConfiguration.clusterName());
		settingsBuilder.put(
			"cluster.routing.allocation.disk.threshold_enabled", false);
		settingsBuilder.put("discovery.zen.ping.multicast.enabled", false);
	}

	protected void configureHttp() {
		settingsBuilder.put(
			"http.enabled", elasticsearchConfiguration.httpEnabled());

		if (!elasticsearchConfiguration.httpEnabled()) {
			return;
		}

		settingsBuilder.put(
			"http.cors.enabled", elasticsearchConfiguration.httpCORSEnabled());

		if (!elasticsearchConfiguration.httpCORSEnabled()) {
			return;
		}

		settingsBuilder.put(
			"http.cors.allow-origin",
			elasticsearchConfiguration.httpCORSAllowOrigin());

		String httpCORSConfigurations =
			elasticsearchConfiguration.httpCORSConfigurations();

		if (Validator.isNotNull(httpCORSConfigurations)) {
			settingsBuilder.loadFromSource(httpCORSConfigurations);
		}
	}

	protected void configureNetworking() {
		String networkBindHost = elasticsearchConfiguration.networkBindHost();

		if (Validator.isNotNull(networkBindHost)) {
			settingsBuilder.put("network.bind.host", networkBindHost);
		}

		String networkHost = elasticsearchConfiguration.networkHost();

		if (Validator.isNull(networkBindHost) &&
			Validator.isNull(networkHost) &&
			Validator.isNull(elasticsearchConfiguration.networkPublishHost())) {

			InetAddress localBindInetAddress =
				clusterSettingsContext.getLocalBindInetAddress();

			if (localBindInetAddress != null) {
				networkHost = localBindInetAddress.getHostAddress();
			}
		}

		if (Validator.isNotNull(networkHost)) {
			settingsBuilder.put("network.host", networkHost);
		}

		String networkPublishHost =
			elasticsearchConfiguration.networkPublishHost();

		if (Validator.isNotNull(networkPublishHost)) {
			settingsBuilder.put("network.publish.host", networkPublishHost);
		}

		String transportTcpPort = elasticsearchConfiguration.transportTcpPort();

		if (Validator.isNotNull(transportTcpPort)) {
			settingsBuilder.put("transport.tcp.port", transportTcpPort);
		}
	}

	protected void configurePaths() {
		settingsBuilder.put(
			"path.data",
			props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/indices");
		settingsBuilder.put(
			"path.home",
			props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch");
		settingsBuilder.put(
			"path.logs", props.get(PropsKeys.LIFERAY_HOME) + "/logs");
		settingsBuilder.put(
			"path.plugins",
			props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/plugins");
		settingsBuilder.put(
			"path.repo",
			props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch/repo");
		settingsBuilder.put(
			"path.work", SystemProperties.get(SystemProperties.TMP_DIR));
	}

	protected void configurePlugin(String name, Settings settings) {
		EmbeddedElasticsearchPluginManager embeddedElasticsearchPluginManager =
			new EmbeddedElasticsearchPluginManager(
				name, settings.get("path.plugins"),
				new PluginManagerFactoryImpl(settings),
				new PluginZipFactoryImpl());

		try {
			embeddedElasticsearchPluginManager.install();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to install " + name + " plugin", ioe);
		}
	}

	protected void configurePlugins() {
		Settings settings = settingsBuilder.build();

		String[] plugins = {
			"analysis-icu", "analysis-kuromoji", "analysis-smartcn",
			"analysis-stempel"
		};

		for (String plugin : plugins) {
			configurePlugin(plugin, settings);
		}
	}

	@Override
	protected Client createClient() {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(6);

			sb.append("Liferay is configured to use embedded Elasticsearch ");
			sb.append("as its search engine. Do NOT use embedded ");
			sb.append("Elasticsearch in production. Embedded Elasticsearch ");
			sb.append("is useful for development and demonstration purposes. ");
			sb.append("Remote Elasticsearch connections can be configured in ");
			sb.append("the Control Panel.");

			_log.warn(sb);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Starting embedded Elasticsearch cluster " +
					elasticsearchConfiguration.clusterName());
		}

		_node = createNode(settingsBuilder.build());

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

	protected Node createNode(Settings settings) {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		Class<?> clazz = getClass();

		thread.setContextClassLoader(clazz.getClassLoader());

		String jnaTmpdir = "jna.tmpdir";

		String originalJnaTmpdir = System.getProperty(jnaTmpdir);

		System.setProperty(jnaTmpdir, _jnaTmpDir);

		try {
			NodeBuilder nodeBuilder = new NodeBuilder();

			nodeBuilder.settings(settings);

			nodeBuilder.local(true);

			Node node = nodeBuilder.build();

			if (elasticsearchConfiguration.syncSearch()) {
				Injector injector = node.injector();

				_replaceTransportRequestHandler(
					injector.getInstance(TransportService.class),
					injector.getInstance(SearchService.class));
			}

			return node;
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);

			if (originalJnaTmpdir == null) {
				System.clearProperty(jnaTmpdir);
			}
			else {
				System.setProperty(jnaTmpdir, originalJnaTmpdir);
			}
		}
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
		close();
	}

	@Override
	protected void loadRequiredDefaultConfigurations() {
		settingsBuilder.put("action.auto_create_index", false);
		settingsBuilder.put(
			"bootstrap.mlockall",
			elasticsearchConfiguration.bootstrapMlockAll());

		configureClustering();

		configureHttp();

		settingsBuilder.put("index.number_of_replicas", 0);
		settingsBuilder.put("index.number_of_shards", 1);

		configureNetworking();

		settingsBuilder.put("node.client", false);
		settingsBuilder.put("node.data", true);
		settingsBuilder.put(
			DiscoveryService.SETTING_DISCOVERY_SEED,
			SecureRandomUtil.nextLong());

		configurePaths();

		configurePlugins();

		if (PortalRunMode.isTestMode()) {
			settingsBuilder.put("index.refresh_interval", "1ms");
			settingsBuilder.put("index.translog.flush_threshold_ops", "1");
			settingsBuilder.put("index.translog.interval", "1ms");
		}
	}

	@Override
	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		super.removeSettingsContributor(settingsContributor);
	}

	@Reference
	protected ClusterSettingsContext clusterSettingsContext;

	@Reference
	protected Props props;

	private void _replaceTransportRequestHandler(
		TransportService transportService, SearchService searchService) {

		String action = SearchServiceTransportAction.QUERY_FETCH_ACTION_NAME;

		transportService.removeHandler(action);

		transportService.registerRequestHandler(
			action, ShardSearchTransportRequest.class, ThreadPool.Names.SAME,
			new TransportRequestHandler<ShardSearchTransportRequest>() {

				@Override
				public void messageReceived(
						ShardSearchTransportRequest shardSearchTransportRequest,
						TransportChannel transportChannel)
					throws Exception {

					transportChannel.sendResponse(
						searchService.executeFetchPhase(
							shardSearchTransportRequest));
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private static final String _jnaTmpDir =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/elasticSearch-tmpDir";

	@Reference
	private File _file;

	private Node _node;

}