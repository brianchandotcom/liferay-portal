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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.elasticsearch.common.settings.Settings;

import org.osgi.service.component.ComponentContext;

/**
 * @author Tina Tian
 */
public class Sidecar {

	public Sidecar(
		ComponentContext componentContext, String componentName,
		ElasticsearchConfiguration elasticsearchConfiguration,
		com.liferay.portal.kernel.util.File file,
		ProcessExecutor processExecutor, Props props) {

		String liferayHome = props.get(PropsKeys.LIFERAY_HOME);

		File sidecarHome = new File(
			liferayHome, elasticsearchConfiguration.sidecarHome());

		if (!sidecarHome.exists() || !sidecarHome.isDirectory()) {
			sidecarHome = new File(elasticsearchConfiguration.sidecarHome());

			if (!sidecarHome.exists() || !sidecarHome.isDirectory()) {
				throw new IllegalArgumentException(
					"Sidecar home " + elasticsearchConfiguration.sidecarHome() +
						" does not exist");
			}
		}

		_sidecarHome = sidecarHome;

		_pathLogs = new File(liferayHome, "logs");

		File dataHome = new File(liferayHome, "data/elasticsearch7");

		_pathData = new File(dataHome, "indices");
		_pathRepo = new File(dataHome, "repo");

		_componentContext = componentContext;
		_componentName = componentName;
		_elasticsearchConfiguration = elasticsearchConfiguration;
		_file = file;
		_processExecutor = processExecutor;
		_props = props;
	}

	public String getNetworkHostAddress() {
		try {
			return _addressNoticeableFuture.get();
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to get network host address", exception);
		}
	}

	public void start() {
		if (_log.isInfoEnabled()) {
			_log.info("Starting sidecar");
		}

		try {
			_processChannel = _processExecutor.execute(
				_createProcessConfig(),
				new SidecarMainProcessCallable(
					_elasticsearchConfiguration.sidecarHeartbeatInterval()));

			NoticeableFuture<Serializable> noticeableFuture =
				_processChannel.getProcessNoticeableFuture();

			_restartFutureListener = new RestartFutureListener();

			noticeableFuture.addFutureListener(_restartFutureListener);

			_addressNoticeableFuture = new DefaultNoticeableFuture<>();

			NoticeableFuture<String> startNoticeableFuture =
				_processChannel.write(
					new StartSidecarProcessCallable(_getSidecarArguments()));

			startNoticeableFuture.addFutureListener(
				future -> {
					try {
						String address = future.get();

						if (_log.isInfoEnabled()) {
							_log.info(
								StringBundler.concat(
									"Started sidecar with name ",
									_DEFAULT_NODE_NAME, " at ", address));
						}

						_addressNoticeableFuture.set(address);
					}
					catch (Exception exception) {
						_log.error(
							"Unable to start elasticsearch server", exception);

						_processChannel.write(new StopSidecarProcessCallable());
					}
				});
		}
		catch (Exception exception) {
			_log.error("Unable to start sidecar process", exception);
		}
	}

	public void stop() {
		if (_sidecarTempDir != null) {
			_file.deltree(_sidecarTempDir);
		}

		if (_processChannel == null) {
			return;
		}

		NoticeableFuture<Serializable> noticeableFuture =
			_processChannel.getProcessNoticeableFuture();

		noticeableFuture.removeFutureListener(_restartFutureListener);

		_processChannel.write(new StopSidecarProcessCallable());

		_processChannel = null;
	}

	private String _createClasspath(File folder, String filter) {
		File[] files = folder.listFiles();

		StringBundler sb = new StringBundler(2 * files.length - 1);

		for (File file : files) {
			String path = file.getAbsolutePath();

			if (Validator.isNotNull(filter) && !path.contains(filter)) {
				continue;
			}

			if (sb.index() > 0) {
				sb.append(File.pathSeparator);
			}

			sb.append(path);
		}

		return sb.toString();
	}

	private ProcessConfig _createProcessConfig() {
		ProcessConfig.Builder processConfigBuilder =
			new ProcessConfig.Builder();

		ProtectionDomain protectionDomain = Sidecar.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL bundleURL = codeSource.getLocation();

		processConfigBuilder.setArguments(_getJVMArguments(bundleURL));

		String bootstrapClasspath = _createClasspath(
			new File(_props.get(PropsKeys.LIFERAY_LIB_GLOBAL_DIR)), "petra");

		processConfigBuilder.setBootstrapClassPath(bootstrapClasspath);

		Map<String, String> environments = new HashMap<>();

		environments.putAll(System.getenv());

		environments.put("HOSTNAME", "localhost");

		processConfigBuilder.setEnvironment(environments);

		processConfigBuilder.setProcessLogConsumer(
			processLog -> {
				if (ProcessLog.Level.DEBUG == processLog.getLevel()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else if (ProcessLog.Level.INFO == processLog.getLevel()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else if (ProcessLog.Level.WARN == processLog.getLevel()) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else {
					_log.error(
						processLog.getMessage(), processLog.getThrowable());
				}
			});
		processConfigBuilder.setReactClassLoader(
			Sidecar.class.getClassLoader());
		processConfigBuilder.setRuntimeClassPath(
			StringBundler.concat(
				_createClasspath(new File(_sidecarHome, "lib"), null),
				File.pathSeparator, bundleURL.getPath(), File.pathSeparator,
				bootstrapClasspath));

		return processConfigBuilder.build();
	}

	private List<String> _getJVMArguments(URL bundleURL) {
		List<String> arguments = new ArrayList<>();

		for (String jvmOption :
				_elasticsearchConfiguration.sidecarJVMOptions()) {

			arguments.add(jvmOption);
		}

		if (_elasticsearchConfiguration.sidecarDebug()) {
			arguments.add(_elasticsearchConfiguration.sidecarDebugSettings());
		}

		try {
			_sidecarTempDir = _file.createTempFolder();
		}
		catch (IOException ioException) {
			throw new IllegalStateException(
				"Unable to create temp folder", ioException);
		}

		File configFolder = new File(_sidecarTempDir, "config");

		try {
			_file.write(
				new File(configFolder, "log4j2.properties"),
				ResourceUtil.getResourceAsString(
					Sidecar.class, "/log4j2.properties"));
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to copy log4j2.properties to " +
					configFolder.getAbsolutePath(),
				ioException);
		}

		arguments.add("-Des.path.conf=" + configFolder.getAbsolutePath());

		arguments.add("-Des.path.home=" + _sidecarHome.getAbsolutePath());
		arguments.add("-Des.networkaddress.cache.ttl=60");
		arguments.add("-Des.networkaddress.cache.negative.ttl=10");

		arguments.add("-Dlog4j.shutdownHookEnabled=false");
		arguments.add("-Dlog4j2.disable.jmx=true");

		arguments.add("-Dio.netty.allocator.type=unpooled");
		arguments.add("-Dio.netty.allocator.numDirectArenas=0");
		arguments.add("-Dio.netty.noUnsafe=true");
		arguments.add("-Dio.netty.noKeySetOptimization=true");
		arguments.add("-Dio.netty.recycler.maxCapacityPerThread=0");

		arguments.add("-Dfile.encoding=UTF-8");
		arguments.add("-Djava.io.tmpdir=" + _sidecarTempDir.getAbsolutePath());

		URLClassLoader urlClassLoader = new URLClassLoader(
			new URL[] {bundleURL});

		URL url = urlClassLoader.findResource("META-INF/sidecar.policy");

		arguments.add("-Djava.security.policy=" + url.toString());

		arguments.add("-Djna.nosys=true");

		return arguments;
	}

	private String[] _getSidecarArguments() {
		SettingsBuilder settingsBuilder = new SettingsBuilder(
			Settings.builder());

		settingsBuilder.put(
			"bootstrap.memory_lock",
			_elasticsearchConfiguration.bootstrapMlockAll());
		settingsBuilder.put("bootstrap.system_call_filter", false);
		settingsBuilder.put("cluster.initial_master_nodes", _DEFAULT_NODE_NAME);
		settingsBuilder.put(
			"cluster.routing.allocation.disk.threshold_enabled", false);
		settingsBuilder.put(
			"cluster.name", _elasticsearchConfiguration.clusterName());
		settingsBuilder.put(
			"http.cors.enabled", _elasticsearchConfiguration.httpCORSEnabled());
		settingsBuilder.put("path.data", _pathData.getAbsolutePath());
		settingsBuilder.put("path.logs", _pathLogs.getAbsolutePath());
		settingsBuilder.put("path.repo", _pathRepo.getAbsolutePath());

		if (_elasticsearchConfiguration.httpCORSEnabled()) {
			settingsBuilder.put(
				"http.cors.allow-origin",
				_elasticsearchConfiguration.httpCORSAllowOrigin());

			settingsBuilder.loadFromSource(
				_elasticsearchConfiguration.httpCORSConfigurations());
		}

		settingsBuilder.put("node.name", _DEFAULT_NODE_NAME);
		settingsBuilder.put("node.data", true);
		settingsBuilder.put("node.ingest", true);
		settingsBuilder.put("node.master", true);
		settingsBuilder.put("node.store.allow_mmap", false);

		settingsBuilder.loadFromSource(
			_elasticsearchConfiguration.additionalConfigurations());

		Settings settings = settingsBuilder.build();

		StringBundler sb = new StringBundler(2 * settings.size() + 1);

		sb.append("Sidecar properties : {");

		List<String> arguments = new ArrayList<>();

		for (String key : settings.keySet()) {
			arguments.add("-E");

			String value = settings.get(key);

			if (Validator.isNotNull(value)) {
				String keyValue = StringBundler.concat(
					key, StringPool.EQUAL, value);

				arguments.add(keyValue);

				sb.append(keyValue);

				sb.append(StringPool.COMMA);
			}
		}

		sb.setStringAt(StringPool.CLOSE_CURLY_BRACE, sb.index() - 1);

		if (_log.isInfoEnabled()) {
			_log.info(sb.toString());
		}

		return arguments.toArray(new String[0]);
	}

	private static final String _DEFAULT_NODE_NAME = "liferay";

	private static final Log _log = LogFactoryUtil.getLog(Sidecar.class);

	private DefaultNoticeableFuture<String> _addressNoticeableFuture;
	private final ComponentContext _componentContext;
	private final String _componentName;
	private final ElasticsearchConfiguration _elasticsearchConfiguration;
	private final com.liferay.portal.kernel.util.File _file;
	private final File _pathData;
	private final File _pathLogs;
	private final File _pathRepo;
	private ProcessChannel<Serializable> _processChannel;
	private final ProcessExecutor _processExecutor;
	private final Props _props;
	private FutureListener<Serializable> _restartFutureListener;
	private final File _sidecarHome;
	private File _sidecarTempDir;

	private class RestartFutureListener
		implements FutureListener<Serializable> {

		@Override
		public void complete(Future<Serializable> future) {
			try {
				future.get();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Sidecar process is aborted", exception);
				}
			}

			_componentContext.disableComponent(_componentName);

			if (_log.isInfoEnabled()) {
				_log.info("Sidecar process exited, will restart");
			}

			_componentContext.enableComponent(_componentName);
		}

	}

}