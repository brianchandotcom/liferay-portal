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

package com.liferay.portal.search.elasticsearch6.internal.connection;

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
import com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch6.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexFactory;
import com.liferay.portal.search.elasticsearch6.settings.SettingsContributor;

import io.netty.buffer.ByteBufUtil;

import java.io.IOException;

import java.net.InetAddress;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;

import org.elasticsearch.client.Client;
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
	configurationPid = "com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration",
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
		settingsBuilder.put("discovery.type", "single-node");
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
			settingsBuilder.put("network.publish.host", networkPublishHost);
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
			"path.data", liferayHome.concat("/data/elasticsearch6/indices"));
		settingsBuilder.put(
			"path.home", liferayHome.concat("/data/elasticsearch6"));
		settingsBuilder.put("path.logs", liferayHome.concat("/logs"));
		settingsBuilder.put(
			"path.repo", liferayHome.concat("/data/elasticsearch6/repo"));
	}

	protected void configureTestMode() {
		if (!PortalRunMode.isTestMode()) {
			return;
		}

		settingsBuilder.put("monitor.jvm.gc.enabled", StringPool.FALSE);
	}

	@Override
	protected Client createClient() {
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

		Client client = _node.client();

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				StringBundler.concat(
					"Started ", elasticsearchConfiguration.clusterName(),
					" in ", stopWatch.getTime(), " ms"));
		}

		return client;
	}

	protected EmbeddedElasticsearchPluginManager
		createEmbeddedElasticsearchPluginManager(
			String name, Settings settings) {

		return new EmbeddedElasticsearchPluginManager(
			name,
			props.get(PropsKeys.LIFERAY_HOME) + "/data/elasticsearch6/plugins",
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

	@Override
	protected void loadRequiredDefaultConfigurations() {
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

		configurePaths();

		configureTestMode();
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

}