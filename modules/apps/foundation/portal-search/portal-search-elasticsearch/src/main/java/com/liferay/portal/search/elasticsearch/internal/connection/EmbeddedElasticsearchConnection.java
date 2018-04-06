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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch.internal.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;

import java.io.IOException;

import java.net.InetAddress;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.DiscoveryService;
import org.elasticsearch.index.IndexService;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.elasticsearch.indices.IndicesService;
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

import org.osgi.framework.BundleContext;
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
	immediate = true, property = "operation.mode=EMBEDDED",
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
					StringBundler.concat(
						"Unable to preload ",
						String.valueOf(ByteBufferUtil.class),
						" to prevent Netty shutdown concurrent class loading ",
						"interruption issue"),
					cnfe);
			}
		}

		if (PortalRunMode.isTestMode()) {
			settingsBuilder.put("index.refresh_interval", "-1");
			settingsBuilder.put(
				"index.translog.flush_threshold_ops", Integer.MAX_VALUE);
			settingsBuilder.put("index.translog.interval", "1d");

			Settings settings = settingsBuilder.build();

			Injector injector = _node.injector();

			IndicesService indicesService = injector.getInstance(
				IndicesService.class);

			Iterator<IndexService> iterator = indicesService.iterator();

			while (iterator.hasNext()) {
				IndexService indexService = iterator.next();

				injector = indexService.injector();

				IndexSettingsService indexSettingsService =
					injector.getInstance(IndexSettingsService.class);

				indexSettingsService.refreshSettings(settings);
			}

			ThreadPool threadPool = injector.getInstance(ThreadPool.class);

			ScheduledExecutorService scheduledExecutorService =
				threadPool.scheduler();

			if (scheduledExecutorService instanceof ThreadPoolExecutor) {
				ThreadPoolExecutor threadPoolExecutor =
					(ThreadPoolExecutor)scheduledExecutorService;

				threadPoolExecutor.setRejectedExecutionHandler(
					_REJECTED_EXECUTION_HANDLER);
			}

			scheduledExecutorService.shutdown();

			try {
				scheduledExecutorService.awaitTermination(1, TimeUnit.HOURS);
			}
			catch (InterruptedException ie) {
				if (_log.isWarnEnabled()) {
					_log.warn("Thread pool shutdown wait was interrupted", ie);
				}
			}
		}

		_node.close();

		_node = null;

		_file.deltree(_jnaTmpDirName);
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
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		java.io.File tempDir = bundleContext.getDataFile(JNA_TMP_DIR);

		_jnaTmpDirName = tempDir.getAbsolutePath();
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
			createEmbeddedElasticsearchPluginManager(name, settings);

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
			removeObsoletePlugin(plugin, settings);
		}

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

			_log.warn(sb.toString());
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
				StringBundler.concat(
					"Finished starting ",
					elasticsearchConfiguration.clusterName(), " in ",
					String.valueOf(stopWatch.getTime()), " ms"));
		}

		return client;
	}

	protected Node createEmbeddedElasticsearchNode(Settings settings) {
		NodeBuilder nodeBuilder = new NodeBuilder();

		nodeBuilder.settings(settings);

		nodeBuilder.local(true);

		return nodeBuilder.build();
	}

	protected EmbeddedElasticsearchPluginManager
		createEmbeddedElasticsearchPluginManager(
			String name, Settings settings) {

		return new EmbeddedElasticsearchPluginManager(
			name, settings.get("path.plugins"),
			new PluginManagerFactoryImpl(settings), new PluginZipFactoryImpl());
	}

	protected Node createNode(Settings settings) {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		Class<?> clazz = getClass();

		thread.setContextClassLoader(clazz.getClassLoader());

		String jnaTmpDir = System.getProperty("jna.tmpdir");

		System.setProperty("jna.tmpdir", _jnaTmpDirName);

		try {
			Node node = createEmbeddedElasticsearchNode(settings);

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

			if (jnaTmpDir == null) {
				System.clearProperty("jna.tmpdir");
			}
			else {
				System.setProperty("jna.tmpdir", jnaTmpDir);
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
			settingsBuilder.put(
				"index.search.slowlog.threshold.fetch.warn", "-1");
			settingsBuilder.put(
				"index.search.slowlog.threshold.query.warn", "-1");
			settingsBuilder.put("index.translog.flush_threshold_ops", "1");
			settingsBuilder.put("index.translog.interval", "1ms");
			settingsBuilder.put(
				"monitor.jvm.enabled", Boolean.FALSE.toString());
		}
	}

	protected void removeObsoletePlugin(String name, Settings settings) {
		EmbeddedElasticsearchPluginManager embeddedElasticsearchPluginManager =
			createEmbeddedElasticsearchPluginManager(name, settings);

		try {
			embeddedElasticsearchPluginManager.removeObsoletePlugin();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to remove " + name + " plugin", ioe);
		}
	}

	@Override
	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		super.removeSettingsContributor(settingsContributor);
	}

	protected static final String JNA_TMP_DIR = "elasticSearch-tmpDir";

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

	/**
	 * Keep this as a static field to avoid the class loading failure during
	 * Tomcat shutdown.
	 */
	private static final RejectedExecutionHandler _REJECTED_EXECUTION_HANDLER =
		new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(
				Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Discarded ", String.valueOf(runnable), " on ",
							String.valueOf(threadPoolExecutor)));
				}
			}

		};

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private static String _jnaTmpDirName;

	@Reference
	private File _file;

	private Node _node;

}