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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.ClassLoaderUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch7.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch7.settings.SettingsContributor;

import io.netty.buffer.ByteBufUtil;

import java.io.IOException;

import java.net.InetAddress;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.threadpool.ThreadPool;

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
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
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
			Class.forName(ByteBufUtil.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to preload ", ByteBufUtil.class,
						" to prevent Netty shutdown concurrent class loading ",
						"interruption issue"),
					cnfe);
			}
		}

		if (PortalRunMode.isTestMode()) {
			Injector injector = _node.injector();

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

		try {
			_node.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

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

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		java.io.File tempDir = bundleContext.getDataFile(JNA_TMP_DIR);

		_jnaTmpDirName = tempDir.getAbsolutePath();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(operation.mode=EMBEDDED)"
	)
	protected void addSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.add(settingsContributor);
	}

	protected void configureClustering() {
		settingsBuilder.put(
			"cluster.name", elasticsearchConfiguration.clusterName());
		settingsBuilder.put(
			"cluster.routing.allocation.disk.threshold_enabled", false);
		settingsBuilder.put("discovery.type", "single-node");
	}

	protected void configureHttp() {
		settingsBuilder.put(
			"http.port",
			String.valueOf(elasticsearchConfiguration.embeddedHttpPort()));

		settingsBuilder.put(
			"http.cors.enabled", elasticsearchConfiguration.httpCORSEnabled());

		if (!elasticsearchConfiguration.httpCORSEnabled()) {
			return;
		}

		settingsBuilder.put(
			"http.cors.allow-origin",
			elasticsearchConfiguration.httpCORSAllowOrigin());

		settingsBuilder.loadFromSource(
			elasticsearchConfiguration.httpCORSConfigurations());
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
			settingsBuilder.put("network.publish_host", networkPublishHost);
		}

		String transportTcpPort = elasticsearchConfiguration.transportTcpPort();

		if (Validator.isNotNull(transportTcpPort)) {
			settingsBuilder.put("transport.tcp.port", transportTcpPort);
		}

		settingsBuilder.put("transport.type", "netty4");
	}

	protected void configurePaths() {
		String liferayHome = props.get(PropsKeys.LIFERAY_HOME);

		settingsBuilder.put(
			"path.data", liferayHome.concat("/data/elasticsearch7/indices"));
		settingsBuilder.put(
			"path.home", liferayHome.concat("/data/elasticsearch7"));
		settingsBuilder.put("path.logs", liferayHome.concat("/logs"));
		settingsBuilder.put(
			"path.repo", liferayHome.concat("/data/elasticsearch7/repo"));
	}

	protected void configureTestMode() {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		settingsBuilder.put("monitor.jvm.gc.enabled", StringPool.FALSE);
	}

	protected EmbeddedElasticsearchPluginManager
		createEmbeddedElasticsearchPluginManager(
			String name, Settings settings) {

		return new EmbeddedElasticsearchPluginManager(
			name,
			props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch7/plugins",
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
			installPlugins(settings);

			return EmbeddedElasticsearchNode.newInstance(settings);
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

	@Override
	protected RestHighLevelClient createRestHighLevelClient() {
		startNode();

		Class<? extends EmbeddedElasticsearchConnection> clazz = getClass();

		return ClassLoaderUtil.getWithContextClassLoader(
			() -> new RestHighLevelClient(
				RestClient.builder(
					new HttpHost(
						"localhost",
						elasticsearchConfiguration.embeddedHttpPort(),
						"http"))),
			clazz);
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
		close();
	}

	protected void installPlugin(String name, Settings settings) {
		EmbeddedElasticsearchPluginManager embeddedElasticsearchPluginManager =
			createEmbeddedElasticsearchPluginManager(name, settings);

		try {
			embeddedElasticsearchPluginManager.install();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to install " + name + " plugin", e);
		}
	}

	protected void installPlugins(Settings settings) {
		String[] plugins = {
			"analysis-icu", "analysis-kuromoji", "analysis-smartcn",
			"analysis-stempel"
		};

		for (String plugin : plugins) {
			removeObsoletePlugin(plugin, settings);
		}

		for (String plugin : plugins) {
			installPlugin(plugin, settings);
		}

		LogManager.shutdown();
	}

	protected void loadAdditionalConfigurations() {
		settingsBuilder.loadFromSource(
			elasticsearchConfiguration.additionalConfigurations());
	}

	protected void loadDefaultConfigurations() {
		String defaultConfigurations = ResourceUtil.getResourceAsString(
			getClass(), "/META-INF/elasticsearch-optional-defaults.yml");

		settingsBuilder.loadFromSource(defaultConfigurations);

		settingsBuilder.put("action.auto_create_index", false);
		settingsBuilder.put(
			"bootstrap.memory_lock",
			elasticsearchConfiguration.bootstrapMlockAll());

		configureClustering();

		configureHttp();

		configureNetworking();

		settingsBuilder.put("node.data", true);
		settingsBuilder.put("node.ingest", true);
		settingsBuilder.put("node.master", true);
		settingsBuilder.put("node.name", "liferay");

		configurePaths();

		configureTestMode();
	}

	protected void loadSettingsContributors() {
		ClientSettingsHelper clientSettingsHelper = new ClientSettingsHelper() {

			@Override
			public void put(String setting, String value) {
				settingsBuilder.put(setting, value);
			}

			@Override
			public void putArray(String setting, String... values) {
				settingsBuilder.putList(setting, values);
			}

		};

		for (SettingsContributor settingsContributor : _settingsContributors) {
			settingsContributor.populate(clientSettingsHelper);
		}
	}

	protected void removeObsoletePlugin(String name, Settings settings) {
		EmbeddedElasticsearchPluginManager embeddedElasticsearchPluginManager =
			createEmbeddedElasticsearchPluginManager(name, settings);

		try {
			embeddedElasticsearchPluginManager.removeObsoletePlugin();
		}
		catch (Exception ioe) {
			throw new RuntimeException(
				"Unable to remove " + name + " plugin", ioe);
		}
	}

	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.remove(settingsContributor);
	}

	protected void startNode() {
		loadDefaultConfigurations();

		loadAdditionalConfigurations();

		loadSettingsContributors();

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(8);

			sb.append("Liferay is configured to use embedded Elasticsearch ");
			sb.append("as its search engine. Do NOT use embedded ");
			sb.append("Elasticsearch in production. Embedded Elasticsearch ");
			sb.append("is useful for development and demonstration purposes. ");
			sb.append("Refer to the documentation for details on the ");
			sb.append("limitations of embedded Elasticsearch. Remote ");
			sb.append("Elasticsearch connections can be configured in the ");
			sb.append("Control Panel.");

			_log.warn(sb.toString());
		}

		Settings settings = settingsBuilder.build();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Starting embedded Elasticsearch cluster " +
					elasticsearchConfiguration.clusterName());
		}

		_node = createNode(settings);

		try {
			_node.start();
		}
		catch (NodeValidationException nve) {
			throw new RuntimeException(nve);
		}

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				StringBundler.concat(
					"Started ", elasticsearchConfiguration.clusterName(),
					" in ", stopWatch.getTime(), " ms"));
		}
	}

	protected static final String JNA_TMP_DIR = "elasticSearch-tmpDir";

	@Reference
	protected ClusterSettingsContext clusterSettingsContext;

	@Reference
	protected Props props;

	protected SettingsBuilder settingsBuilder = new SettingsBuilder(
		Settings.builder());

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
							"Discarded ", runnable, " on ",
							threadPoolExecutor));
				}
			}

		};

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private static String _jnaTmpDirName;

	@Reference
	private File _file;

	private Node _node;
	private final Set<SettingsContributor> _settingsContributors =
		new ConcurrentSkipListSet<>();

}