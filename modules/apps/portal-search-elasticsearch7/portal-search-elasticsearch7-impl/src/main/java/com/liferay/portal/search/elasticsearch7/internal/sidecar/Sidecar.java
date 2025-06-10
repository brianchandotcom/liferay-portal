/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.sidecar.constants.SidecarConstants;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.elasticsearch.common.settings.Settings;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tina Tian
 */
public class Sidecar {

	public Sidecar(
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper,
		ElasticsearchInstancePaths elasticsearchInstancePaths,
		ProcessExecutor processExecutor, SidecarManager sidecarManager) {

		_elasticsearchConfigurationWrapper = elasticsearchConfigurationWrapper;
		_elasticsearchInstancePaths = elasticsearchInstancePaths;
		_processExecutor = processExecutor;
		_sidecarManager = sidecarManager;

		_sidecarHomePath = elasticsearchInstancePaths.getHomePath();
	}

	public String getNetworkHostAddress() {
		return _address;
	}

	public void start() {
		if (_log.isDebugEnabled()) {
			_log.debug("Sidecar Elasticsearch starting");
		}

		String sidecarVersion = _getSidecarVersion();

		_installElasticsearchIfNeeded(sidecarVersion);

		ProcessChannel<Serializable> processChannel =
			_executeSidecarMainProcess();

		FutureListener<Serializable> futureListener = new RestartFutureListener(
			_sidecarManager);

		_addFutureListener(processChannel, futureListener);

		String address = _startElasticsearch(processChannel);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Sidecar Elasticsearch ", sidecarVersion, StringPool.SPACE,
					_getNodeName(), " started at ", address));
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

	private ProcessConfig _createProcessConfig() {
		ProcessConfig.Builder builder = new ProcessConfig.Builder();

		URL bundleURL = _getBundleURL();

		String bootstrapClassPath = _getBootstrapClassPath();

		return builder.setArguments(
			_getJVMArguments(bundleURL)
		).setBootstrapClassPath(
			bootstrapClassPath
		).setEnvironment(
			_getEnvironment()
		).setJavaExecutable(
			System.getProperty("java.home") + "/bin/java"
		).setProcessLogConsumer(
			this::_consumeProcessLog
		).setReactClassLoader(
			Sidecar.class.getClassLoader()
		).setRuntimeClassPath(
			StringBundler.concat(
				bundleURL.getPath(), File.pathSeparator, bootstrapClassPath)
		).build();
	}

	private ProcessChannel<Serializable> _executeSidecarMainProcess() {
		if (!Files.isDirectory(_sidecarHomePath)) {
			throw new IllegalArgumentException(
				"Sidecar Elasticsearch home does not exist: " +
					_sidecarHomePath);
		}

		try {
			return _processExecutor.execute(
				_createProcessConfig(),
				new SidecarMainProcessCallable(
					_elasticsearchConfigurationWrapper.
						sidecarHeartbeatInterval()));
		}
		catch (ProcessException processException) {
			throw new RuntimeException(
				"Unable to start sidecar Elasticsearch process",
				processException);
		}
	}

	private boolean _fileNameContains(Path path, String s) {
		String name = String.valueOf(path.getFileName());

		return name.contains(s);
	}

	private String _getBootstrapClassPath() {
		return _createClasspath(
			Paths.get(PropsValues.LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR),
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

	private Distribution _getElasticsearchDistribution(String sidecarVersion) {
		if (sidecarVersion.equals(ElasticsearchDistribution.VERSION)) {
			return new ElasticsearchDistribution();
		}

		throw new IllegalArgumentException(
			"Unsupported Elasticsearch version: " + sidecarVersion);
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
		arguments.add("-Des.distribution.type=tar");
		arguments.add("-Des.networkaddress.cache.negative.ttl=10");
		arguments.add("-Des.networkaddress.cache.ttl=60");
		arguments.add("-Dfile.encoding=UTF-8");
		arguments.add("-Dio.netty.noUnsafe=true");
		arguments.add("-Dio.netty.noKeySetOptimization=true");
		arguments.add("-Dio.netty.recycler.maxCapacityPerThread=0");
		arguments.add("-Dlog4j.shutdownHookEnabled=false");
		arguments.add("-Dlog4j2.disable.jmx=true");
		arguments.add("-Dlog4j2.formatMsgNoLookups=true");
		arguments.add(
			"-Dorg.apache.lucene.vectorization.upperJavaFeatureVersion=21");
		arguments.add("-Djava.awt.headless=true");
		arguments.add("-Djava.io.tmpdir=" + _sidecarTempDirPath);

		if (JavaDetector.isJDK17() || JavaDetector.isJDK21()) {
			arguments.add("-Djava.security.manager=allow");
		}

		arguments.add(
			"-Djava.security.policy=" + _getSecurityPolicyURL(bundleURL));
		arguments.add("-Djna.nosys=true");

		if (JavaDetector.isJDK21() && OSDetector.isLinux()) {
			arguments.add("-XX:-UseContainerSupport");
		}

		arguments.add(
			"--enable-native-access=org.elasticsearch.nativeaccess," +
				"org.apache.lucene.core");
		arguments.add("--enable-native-access=ALL-UNNAMED");

		// Modules

		arguments.add("--module-path=" + _sidecarHomePath.resolve("lib"));
		arguments.add("--add-modules=jdk.incubator.vector");
		arguments.add("--add-modules=jdk.net");
		arguments.add("--add-modules=jdk.management.agent");
		arguments.add("--add-modules=ALL-MODULE-PATH");
		arguments.add(
			"--add-opens=org.elasticsearch.server/org.elasticsearch." +
				"bootstrap=ALL-UNNAMED");
		arguments.add("-Djdk.module.main=org.elasticsearch.server");

		// Apply module patches for class modifications

		Map<String, Path> patchModulePaths = _patchModuleClasses(
			_createClasspath(_sidecarHomePath.resolve("lib"), path -> true));

		patchModulePaths.forEach(
			(moduleName, path) -> {
				arguments.add("--patch-module");
				arguments.add(moduleName + "=" + path.toAbsolutePath());
			});

		return arguments;
	}

	private String _getLogProperties() {
		return StringPool.BLANK;
	}

	private String _getNodeName() {
		String nodeName = _elasticsearchConfigurationWrapper.nodeName();

		if (!Validator.isBlank(nodeName)) {
			return nodeName;
		}

		return "liferay_sidecar";
	}

	private URL _getSecurityPolicyURL(URL bundleURL) {
		try (URLClassLoader urlClassLoader = new URLClassLoader(
				new URL[] {bundleURL})) {

			return urlClassLoader.findResource(
				SidecarConstants.SIDECAR_POLICY_FILE_NAME);
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
		).nodeName(
			_getNodeName()
		).build();
	}

	private SidecarServerArgs _getSidecarServerArgs() {
		Settings settings = _getSettings();

		StringBundler sb = new StringBundler((2 * settings.size()) + 1);

		sb.append("Sidecar Elasticsearch properties : {");

		Map<String, Serializable> settingsMap = new HashMap<>();

		for (String key : settings.keySet()) {
			List<String> list = settings.getAsList(key);

			if (ListUtil.isEmpty(list)) {
				continue;
			}

			String keyValue = StringBundler.concat(
				key, StringPool.EQUAL, StringUtil.merge(list));

			sb.append(keyValue);

			sb.append(StringPool.COMMA);

			if (list.size() == 1) {
				settingsMap.put(key, list.get(0));
			}
			else {
				settingsMap.put(key, new ArrayList<>(list));
			}
		}

		sb.setStringAt(StringPool.CLOSE_CURLY_BRACE, sb.index() - 1);

		if (_log.isDebugEnabled()) {
			_log.debug(sb.toString());
		}

		return new SidecarServerArgs(
			false, false, null, settingsMap,
			String.valueOf(_sidecarTempDirPath.resolve("config")),
			String.valueOf(_sidecarHomePath.resolve("logs")));
	}

	private String _getSidecarVersion() {
		return ResourceUtil.getResourceAsString(
			getClass(), SidecarConstants.SIDECAR_VERSION_FILE_NAME);
	}

	private void _installElasticsearchIfNeeded(String sidecarVersion) {
		ElasticsearchInstaller.builder(
		).distributablesDirectoryPath(
			_elasticsearchInstancePaths.getWorkPath()
		).distribution(
			_getElasticsearchDistribution(sidecarVersion)
		).installationDirectoryPath(
			_sidecarHomePath
		).build(
		).install();
	}

	private void _patchModuleClass(
			Map<String, Path> patchModulePaths, String moduleName,
			String className, String methodName,
			Consumer<MethodVisitor> methodVisitorConsumer,
			ClassLoader classLoader)
		throws Exception {

		Path patchModulePath = _sidecarTempDirPath.resolve(
			Paths.get("patch-module", moduleName));

		String[] parts = StringUtil.split(className, CharPool.PERIOD);

		Path classPackagePath = patchModulePath.resolve(
			Paths.get(parts[0], ArrayUtil.subset(parts, 1, parts.length - 1)));

		Files.createDirectories(classPackagePath);

		Files.write(
			classPackagePath.resolve(parts[parts.length - 1] + ".class"),
			ClassModificationUtil.getModifiedClassBytes(
				className, methodName, methodVisitorConsumer, classLoader),
			StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		patchModulePaths.putIfAbsent(moduleName, patchModulePath);
	}

	private Map<String, Path> _patchModuleClasses(String sidecarLibClassPath) {
		Map<String, Path> patchModulePaths = new HashMap<>();

		try {
			ClassLoader classLoader = new URLClassLoader(
				ClassPathUtil.getClassPathURLs(sidecarLibClassPath), null);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.entitlement",
				"org.elasticsearch.entitlement.bootstrap.EntitlementBootstrap",
				"bootstrap",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.nativeaccess",
				"org.elasticsearch.nativeaccess.PosixNativeAccess",
				"definitelyRunningAsRoot",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.ICONST_0);
					methodVisitor.visitInsn(Opcodes.IRETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.server",
				"org.elasticsearch.bootstrap.Bootstrap", "sendCliMarker",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.server",
				"org.elasticsearch.bootstrap.Elasticsearch",
				"startCliMonitorThread",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.server",
				"org.elasticsearch.bootstrap.Elasticsearch$" +
					"EntitlementSelfTester",
				"entitlementSelfTest",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.server",
				"org.elasticsearch.common.settings.KeyStoreWrapper", "save",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.server",
				"org.elasticsearch.bootstrap.Security", "configure",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);

			_patchModuleClass(
				patchModulePaths, "org.elasticsearch.server",
				"org.elasticsearch.bootstrap.Spawner", "spawnNativeControllers",
				methodVisitor -> {
					methodVisitor.visitCode();
					methodVisitor.visitInsn(Opcodes.RETURN);
				},
				classLoader);
		}
		catch (Exception exception) {
			_log.error("Unable to modify classes", exception);
		}

		return patchModulePaths;
	}

	private String _startElasticsearch(
		ProcessChannel<Serializable> processChannel) {

		NoticeableFuture<String> noticeableFuture = processChannel.write(
			new StartSidecarProcessCallable(_getSidecarServerArgs()));

		try {
			return _waitForPublishedAddress(noticeableFuture);
		}
		catch (IOException ioException) {
			if (Objects.equals(ioException.getMessage(), "Stream closed")) {
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
			throw new Exception(executionException.getCause());
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(Sidecar.class);

	private String _address;
	private final ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private final ElasticsearchInstancePaths _elasticsearchInstancePaths;
	private ProcessChannel<Serializable> _processChannel;
	private final ProcessExecutor _processExecutor;
	private FutureListener<Serializable> _restartFutureListener;
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