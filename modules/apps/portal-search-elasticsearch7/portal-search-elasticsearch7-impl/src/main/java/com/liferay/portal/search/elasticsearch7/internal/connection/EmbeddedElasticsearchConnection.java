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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.settings.SettingsContributor;

import io.netty.buffer.ByteBufUtil;

import java.io.IOException;

import java.nio.file.Paths;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;

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
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to preload ", ByteBufUtil.class,
						" to prevent Netty shutdown concurrent class loading ",
						"interruption issue"),
					classNotFoundException);
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
			catch (InterruptedException interruptedException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Thread pool shutdown wait was interrupted",
						interruptedException);
				}
			}
		}

		try {
			_node.close();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		_node = null;

		_file.deltree(_jnaTmpDirName);
	}

	@Override
	public String getConnectionId() {
		return String.valueOf(OperationMode.EMBEDDED);
	}

	@Override
	public OperationMode getOperationMode() {
		return OperationMode.EMBEDDED;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		java.io.File tempDir = bundleContext.getDataFile(JNA_TMP_DIR);

		_jnaTmpDirName = tempDir.getAbsolutePath();

		close();
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
		SearchLogHelperUtil.setRESTClientLoggerLevel(
			elasticsearchConfiguration.restClientLoggerLevel());

		startNode();

		return RestHighLevelClientFactory.builder(
		).clusterName(
			getClusterName()
		).hostName(
			"localhost"
		).httpPortRange(
			new HttpPortRange(elasticsearchConfiguration)
		).nodeName(
			getNodeName()
		).scheme(
			"http"
		).build(
		).getRestHighLevelClientOptional(
		).get();
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
		close();
	}

	protected String getClusterName() {
		return elasticsearchConfiguration.clusterName();
	}

	protected ElasticsearchInstancePaths getElasticsearchInstancePaths() {
		ElasticsearchInstancePathsBuilder elasticsearchInstancePathsBuilder =
			new ElasticsearchInstancePathsBuilder();

		return elasticsearchInstancePathsBuilder.workPath(
			Paths.get(props.get(PropsKeys.LIFERAY_HOME))
		).build();
	}

	protected String getNodeName() {
		return GetterUtil.getString(
			elasticsearchConfiguration.nodeName(), "liferay");
	}

	protected void installPlugin(String name, Settings settings) {
		EmbeddedElasticsearchPluginManager embeddedElasticsearchPluginManager =
			createEmbeddedElasticsearchPluginManager(name, settings);

		try {
			embeddedElasticsearchPluginManager.install();
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to install " + name + " plugin", exception);
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

	protected void removeObsoletePlugin(String name, Settings settings) {
		EmbeddedElasticsearchPluginManager embeddedElasticsearchPluginManager =
			createEmbeddedElasticsearchPluginManager(name, settings);

		try {
			embeddedElasticsearchPluginManager.removeObsoletePlugin();
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to remove " + name + " plugin", exception);
		}
	}

	protected void removeSettingsContributor(
		SettingsContributor settingsContributor) {

		_settingsContributors.remove(settingsContributor);
	}

	protected void startNode() {
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

		Settings settings = ElasticsearchInstanceSettingsBuilder.builder(
		).clusterName(
			getClusterName()
		).elasticsearchConfiguration(
			elasticsearchConfiguration
		).elasticsearchInstancePaths(
			getElasticsearchInstancePaths()
		).httpPortRange(
			new HttpPortRange(elasticsearchConfiguration)
		).localBindInetAddressSupplier(
			clusterExecutor::getBindInetAddress
		).nodeName(
			getNodeName()
		).settingsContributors(
			_settingsContributors
		).build();

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Starting embedded Elasticsearch cluster ",
					getClusterName(), " with settings: ", settings.toString()));
		}

		_node = createNode(settings);

		try {
			_node.start();
		}
		catch (NodeValidationException nodeValidationException) {
			throw new RuntimeException(nodeValidationException);
		}

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				StringBundler.concat(
					"Started ", getClusterName(), " in ", stopWatch.getTime(),
					" ms"));
		}
	}

	protected static final String JNA_TMP_DIR = "elasticSearch-tmpDir";

	@Reference
	protected ClusterExecutor clusterExecutor;

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	@Reference
	protected Props props;

	private static void _logRejectedExecution(
		Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Discarded ", runnable, " on ", threadPoolExecutor));
		}
	}

	/**
	 * Keep this as a static field to avoid the class loading failure during
	 * Tomcat shutdown.
	 */
	private static final RejectedExecutionHandler _REJECTED_EXECUTION_HANDLER =
		(runnable, threadPoolExecutor) -> _logRejectedExecution(
			runnable, threadPoolExecutor);

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchConnection.class);

	private static String _jnaTmpDirName;

	@Reference
	private File _file;

	private Node _node;
	private final Set<SettingsContributor> _settingsContributors =
		new ConcurrentSkipListSet<>();

}