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

import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ClassPathUtil;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchInstancePaths;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchInstanceSettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.HttpPortRange;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.SidecarVersionConstants;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch7.settings.SettingsContributor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.common.settings.Settings;

import org.objectweb.asm.Opcodes;

/**
 * @author Tina Tian
 */
public class Sidecar {

	public Sidecar(
		ClusterExecutor clusterExecutor,
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper,
		ElasticsearchInstancePaths elasticsearchInstancePaths,
		ProcessExecutor processExecutor,
		ProcessExecutorPaths processExecutorPaths,
		Collection<SettingsContributor> settingsContributors,
		SidecarManager sidecarManager) {

		_clusterExecutor = clusterExecutor;
		_elasticsearchConfigurationWrapper = elasticsearchConfigurationWrapper;
		_elasticsearchInstancePaths = elasticsearchInstancePaths;
		_processExecutor = processExecutor;
		_processExecutorPaths = processExecutorPaths;
		_settingsContributors = settingsContributors;
		_sidecarManager = sidecarManager;

		_dataHomePath = elasticsearchInstancePaths.getDataPath();
		_sidecarHomePath = elasticsearchInstancePaths.getHomePath();
	}

	public String getNetworkHostAddress() {
		return _address;
	}

	public void start() {
		if (_log.isDebugEnabled()) {
			_log.debug("Sidecar Elasticsearch starting");
		}

		_installElasticsearchIfNeeded();

		ProcessChannel<Serializable> processChannel =
			_executeSidecarMainProcess();

		FutureListener<Serializable> futureListener = new RestartFutureListener(
			_sidecarManager);

		_addFutureListener(processChannel, futureListener);

		String address = _startElasticsearch(processChannel);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Sidecar Elasticsearch ", _getNodeName(), " started at ",
					address));
		}

		_address = address;
		_processChannel = processChannel;
		_restartFutureListener = futureListener;
	}

	public void stop() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping sidecar Elasticsearch");
		}

		if (_processChannel != null) {
			NoticeableFuture<Serializable> noticeableFuture =
				_processChannel.getProcessNoticeableFuture();

			noticeableFuture.removeFutureListener(_restartFutureListener);

			_processChannel.write(new StopSidecarProcessCallable());

			try {
				noticeableFuture.get(
					_elasticsearchConfigurationWrapper.sidecarShutdownTimeout(),
					TimeUnit.MILLISECONDS);
			}
			catch (Exception exception) {
				if (!noticeableFuture.isDone()) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Forcibly shutdown sidecar Elasticsearch ",
								"process because it did not shut down in ",
								_elasticsearchConfigurationWrapper.
									sidecarShutdownTimeout(),
								" ms"),
							exception);
					}

					noticeableFuture.cancel(true);
				}
			}

			_processChannel = null;
		}

		PathUtil.deleteDir(_sidecarTempDirPath);
	}

	private void _addFutureListener(
		ProcessChannel<Serializable> processChannel,
		FutureListener<Serializable> futureListener) {

		NoticeableFuture<Serializable> noticeableFuture =
			processChannel.getProcessNoticeableFuture();

		noticeableFuture.addFutureListener(futureListener);
	}

	private void _consumeProcessLog(ProcessLog processLog) {
		if (ProcessLog.Level.DEBUG == processLog.getLevel()) {
			if (_log.isDebugEnabled()) {
				_log.debug(processLog.getMessage(), processLog.getThrowable());
			}
		}
		else if (ProcessLog.Level.INFO == processLog.getLevel()) {
			if (_log.isInfoEnabled()) {
				_log.info(processLog.getMessage(), processLog.getThrowable());
			}
		}
		else if (ProcessLog.Level.WARN == processLog.getLevel()) {
			if (_log.isWarnEnabled()) {
				_log.warn(processLog.getMessage(), processLog.getThrowable());
			}
		}
		else {
			_log.error(processLog.getMessage(), processLog.getThrowable());
		}
	}

	private String _createClasspath(
		Path dirPath, DirectoryStream.Filter<Path> filter) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, filter)) {

			StringBundler sb = new StringBundler();

			directoryStream.forEach(
				path -> {
					sb.append(path);
					sb.append(File.pathSeparator);
				});

			if (sb.index() > 0) {
				sb.setIndex(sb.index() - 1);
			}

			return sb.toString();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to iterate " + dirPath, ioException);
		}
	}

	private ProcessConfig _createProcessConfig(String sidecarLibClassPath) {
		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		URL bundleURL = _getBundleURL();

		return builder.setArguments(
			_getJVMArguments(bundleURL)
		).setBootstrapClassPath(
			_getBootstrapClassPath()
		).setEnvironment(
			_getEnvironment()
		).setProcessLogConsumer(
			this::_consumeProcessLog
		).setReactClassLoader(
			Sidecar.class.getClassLoader()
		).setRuntimeClassPath(
			StringBundler.concat(
				sidecarLibClassPath, File.pathSeparator, bundleURL.getPath(),
				File.pathSeparator, _getBootstrapClassPath())
		).build();
	}

	private ProcessChannel<Serializable> _executeSidecarMainProcess() {
		if (!Files.isDirectory(_sidecarHomePath)) {
			throw new IllegalArgumentException(
				"Sidecar Elasticsearch home does not exist: " +
					_sidecarHomePath);
		}

		String sidecarLibClassPath = _createClasspath(
			_sidecarHomePath.resolve("lib"), path -> true);

		try {
			return _processExecutor.execute(
				_createProcessConfig(sidecarLibClassPath),
				new SidecarMainProcessCallable(
					_elasticsearchConfigurationWrapper.
						sidecarHeartbeatInterval(),
					_getModifiedClasses(sidecarLibClassPath)));
		}
		catch (ProcessException processException) {
			throw new RuntimeException(
				"Unable to start sidecar Elasticsearch process",
				processException);
		}
	}

	private boolean _fileNameContains(Path path, String s) {
		String name = String.valueOf(path.getFileName());

		if (name.contains(s)) {
			return true;
		}

		return false;
	}

	private String _getBootstrapClassPath() {
		return _createClasspath(
			_processExecutorPaths.getLibPath(),
			path -> _fileNameContains(path, "petra"));
	}

	private URL _getBundleURL() {
		ProtectionDomain protectionDomain = Sidecar.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		return codeSource.getLocation();
	}

	private String _getClusterName() {
		return _elasticsearchConfigurationWrapper.clusterName();
	}

	private Distribution _getElasticsearchDistribution() {
		String versionNumber = ResourceUtil.getResourceAsString(
			getClass(), SidecarVersionConstants.SIDECAR_VERSION_FILE_NAME);

		if (versionNumber.equals(ElasticsearchDistribution.VERSION)) {
			return new ElasticsearchDistribution();
		}

		throw new IllegalArgumentException(
			"Unsupported Elasticsearch version: " + versionNumber);
	}

	private HashMap<String, String> _getEnvironment() {
		return HashMapBuilder.putAll(
			System.getenv()
		).put(
			"HOSTNAME", "localhost"
		).put(
			"LIBFFI_TMPDIR", _sidecarHomePath.toString()
		).build();
	}

	private List<String> _getJVMArguments(URL bundleURL) {
		List<String> arguments = new ArrayList<>();

		for (String jvmOption :
				_elasticsearchConfigurationWrapper.sidecarJVMOptions()) {

			if (jvmOption.contains("|")) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						jvmOption + " is not a valid format for the JVM " +
							"options and will be ignored");
				}
			}
			else {
				arguments.add(jvmOption);
			}
		}

		if (_elasticsearchConfigurationWrapper.sidecarDebug()) {
			arguments.add(
				_elasticsearchConfigurationWrapper.sidecarDebugSettings());
		}

		try {
			_sidecarTempDirPath = Files.createTempDirectory("sidecar");
		}
		catch (IOException ioException) {
			throw new IllegalStateException(
				"Unable to create temp folder", ioException);
		}

		Path configFolder = _sidecarTempDirPath.resolve("config");

		try {
			Files.createDirectories(configFolder);

			Files.write(
				configFolder.resolve("log4j2.properties"),
				Arrays.asList(
					"logger.bootstrapchecks.name=org.elasticsearch.bootstrap." +
						"BootstrapChecks",
					"logger.bootstrapchecks.level=error",
					"logger.deprecation.name=org.elasticsearch.deprecation",
					"logger.deprecation.level=error", _getLogProperties(),
					ResourceUtil.getResourceAsString(
						Sidecar.class, "/log4j2-sidecar.properties")));
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to copy log4j2.properties to " + configFolder,
				ioException);
		}

		arguments.add("-Des.path.conf=" + configFolder);
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
		arguments.add("-Djava.io.tmpdir=" + _sidecarTempDirPath);

		arguments.add(
			"-Djava.security.policy=" +
				String.valueOf(_getSecurityPolicyURL(bundleURL)));

		arguments.add("-Djna.nosys=true");

		return arguments;
	}

	private String _getLogProperties() {
		return StringPool.BLANK;
	}

	private Map<String, byte[]> _getModifiedClasses(
		String sidecarLibClassPath) {

		Map<String, byte[]> modifiedClasses = new HashMap<>();

		try {
			ClassLoader classLoader = new URLClassLoader(
				ClassPathUtil.getClassPathURLs(sidecarLibClassPath), null);

			modifiedClasses.put(
				"org.elasticsearch.bootstrap.Natives",
				ClassModificationUtil.getModifiedClassBytes(
					"org.elasticsearch.bootstrap.Natives",
					"definitelyRunningAsRoot",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.ICONST_0);
						methodVisitor.visitInsn(Opcodes.IRETURN);
					},
					classLoader));

			modifiedClasses.put(
				"org.elasticsearch.common.settings.KeyStoreWrapper",
				ClassModificationUtil.getModifiedClassBytes(
					"org.elasticsearch.common.settings.KeyStoreWrapper", "save",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.RETURN);
					},
					classLoader));

			modifiedClasses.put(
				"org.elasticsearch.bootstrap.Security",
				ClassModificationUtil.getModifiedClassBytes(
					"org.elasticsearch.bootstrap.Security", "configure",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.RETURN);
					},
					classLoader));
		}
		catch (Exception exception) {
			_log.error("Unable to modify classes", exception);
		}

		return modifiedClasses;
	}

	private String _getNodeName() {
		String nodeName = _elasticsearchConfigurationWrapper.nodeName();

		if (!Validator.isBlank(nodeName)) {
			return nodeName;
		}

		return "liferay";
	}

	private URL _getSecurityPolicyURL(URL bundleURL) {
		try (URLClassLoader urlClassLoader = new URLClassLoader(
				new URL[] {bundleURL})) {

			return urlClassLoader.findResource("META-INF/sidecar.policy");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Settings _getSettings() {
		return ElasticsearchInstanceSettingsBuilder.builder(
		).clusterName(
			_getClusterName()
		).discoveryTypeSingleNode(
			true
		).elasticsearchConfigurationWrapper(
			_elasticsearchConfigurationWrapper
		).elasticsearchInstancePaths(
			_elasticsearchInstancePaths
		).httpPortRange(
			new HttpPortRange(_elasticsearchConfigurationWrapper)
		).localBindInetAddressSupplier(
			_clusterExecutor::getBindInetAddress
		).nodeName(
			_getNodeName()
		).settingsContributors(
			_settingsContributors
		).build();
	}

	private String[] _getSidecarArguments() {
		Settings settings = _getSettings();

		StringBundler sb = new StringBundler((2 * settings.size()) + 1);

		sb.append("Sidecar Elasticsearch properties : {");

		List<String> arguments = new ArrayList<>();

		for (String key : settings.keySet()) {
			arguments.add("-E");

			List<String> list = settings.getAsList(key);

			if (!ListUtil.isEmpty(list)) {
				String keyValue = StringBundler.concat(
					key, StringPool.EQUAL, StringUtil.merge(list));

				arguments.add(keyValue);

				sb.append(keyValue);

				sb.append(StringPool.COMMA);
			}
		}

		sb.setStringAt(StringPool.CLOSE_CURLY_BRACE, sb.index() - 1);

		if (_log.isDebugEnabled()) {
			_log.debug(sb.toString());
		}

		return arguments.toArray(new String[0]);
	}

	private void _installElasticsearchIfNeeded() {
		ElasticsearchInstaller.builder(
		).distributablesDirectoryPath(
			_elasticsearchInstancePaths.getWorkPath()
		).distribution(
			_getElasticsearchDistribution()
		).installationDirectoryPath(
			_sidecarHomePath
		).build(
		).install();
	}

	private String _startElasticsearch(
		ProcessChannel<Serializable> processChannel) {

		NoticeableFuture<String> noticeableFuture = processChannel.write(
			new StartSidecarProcessCallable(_getSidecarArguments()));

		try {
			return _waitForPublishedAddress(noticeableFuture);
		}
		catch (IOException ioException) {
			if (Objects.equals("Stream closed", ioException.getMessage())) {
				throw new RuntimeException(
					StringBundler.concat(
						"Sidecar JVM did not launch successfully. ",
						SidecarMainProcessCallable.class.getSimpleName(),
						" may have crashed, or its classpath may be missing ",
						"required libraries"),
					ioException);
			}

			processChannel.write(new StopSidecarProcessCallable());

			throw new RuntimeException(ioException);
		}
		catch (Exception exception) {
			processChannel.write(new StopSidecarProcessCallable());

			if (exception instanceof RuntimeException) {
				throw (RuntimeException)exception;
			}

			throw new RuntimeException(exception);
		}
	}

	private String _waitForPublishedAddress(
			NoticeableFuture<String> noticeableFuture)
		throws Exception {

		try {
			return noticeableFuture.get();
		}
		catch (ExecutionException executionException) {
			throw (Exception)executionException.getCause();
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(Sidecar.class);

	private String _address;
	private final ClusterExecutor _clusterExecutor;
	private final Path _dataHomePath;
	private final ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private final ElasticsearchInstancePaths _elasticsearchInstancePaths;
	private ProcessChannel<Serializable> _processChannel;
	private final ProcessExecutor _processExecutor;
	private final ProcessExecutorPaths _processExecutorPaths;
	private FutureListener<Serializable> _restartFutureListener;
	private final Collection<SettingsContributor> _settingsContributors;
	private final Path _sidecarHomePath;
	private SidecarManager _sidecarManager;
	private Path _sidecarTempDirPath;

	private static class RestartFutureListener
		implements FutureListener<Serializable> {

		public RestartFutureListener(SidecarManager sidecarManager) {
			_sidecarManager = sidecarManager;
		}

		@Override
		public void complete(Future<Serializable> future) {
			try {
				future.get();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Sidecar Elasticsearch process is aborted", exception);
				}
			}

			if (_sidecarManager.isStartupSuccessful()) {
				if (_log.isInfoEnabled()) {
					_log.info("Restarting sidecar Elasticsearch process");
				}

				_sidecarManager.applyConfigurations();
			}
		}

		private SidecarManager _sidecarManager;

	}

}