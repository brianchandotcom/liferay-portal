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
import com.liferay.petra.process.ClassPathUtil;
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
import java.io.InputStream;
import java.io.Serializable;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.elasticsearch.common.settings.Settings;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.osgi.service.component.ComponentContext;

/**
 * @author Tina Tian
 */
public class Sidecar {

	public Sidecar(
		ComponentContext componentContext, String componentName,
		ElasticsearchConfiguration elasticsearchConfiguration,
		ProcessExecutor processExecutor, Props props) {

		Path liferayHome = Paths.get(props.get(PropsKeys.LIFERAY_HOME));

		liferayHome = liferayHome.toAbsolutePath();

		Path sidecarHome = liferayHome.resolve(
			elasticsearchConfiguration.sidecarHome());

		if (!Files.isDirectory(sidecarHome)) {
			sidecarHome = Paths.get(elasticsearchConfiguration.sidecarHome());

			if (!Files.isDirectory(sidecarHome)) {
				throw new IllegalArgumentException(
					"Sidecar home " + elasticsearchConfiguration.sidecarHome() +
						" does not exist");
			}
		}

		_sidecarHome = sidecarHome.toAbsolutePath();

		_pathLogs = liferayHome.resolve("logs");

		_dataHome = liferayHome.resolve("data/elasticsearch7");

		_pathData = _dataHome.resolve("indices");
		_pathRepo = _dataHome.resolve("repo");

		_componentContext = componentContext;
		_componentName = componentName;
		_elasticsearchConfiguration = elasticsearchConfiguration;
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

		String sidecarLibClassPath = _createClasspath(
			_sidecarHome.resolve("lib"), path -> true);

		Map<String, byte[]> modifiedClasses = new HashMap<>();

		try {
			ClassLoader classLoader = new URLClassLoader(
				ClassPathUtil.getClassPathURLs(sidecarLibClassPath), null);

			modifiedClasses.put(
				_MODIFIED_CLASS_NAME_NATIVES,
				_getModifiedClassBytes(
					classLoader.loadClass(_MODIFIED_CLASS_NAME_NATIVES),
					"definitelyRunningAsRoot",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.ICONST_0);
						methodVisitor.visitInsn(Opcodes.IRETURN);
					}));

			modifiedClasses.put(
				_MODIFIED_CLASS_NAME_KEY_STORE_WRAPPER,
				_getModifiedClassBytes(
					classLoader.loadClass(
						_MODIFIED_CLASS_NAME_KEY_STORE_WRAPPER),
					"save",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.RETURN);
					}));
		}
		catch (Exception exception) {
			_log.error("Unable to modify classes", exception);
		}

		try {
			_processChannel = _processExecutor.execute(
				_createProcessConfig(sidecarLibClassPath),
				new SidecarMainProcessCallable(
					_elasticsearchConfiguration.sidecarHeartbeatInterval(),
					modifiedClasses));

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
									"Started sidecar with name ", getNodeName(),
									" at ", address));
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
		deleteDir(_sidecarTempDir);

		if (_processChannel == null) {
			return;
		}

		NoticeableFuture<Serializable> noticeableFuture =
			_processChannel.getProcessNoticeableFuture();

		noticeableFuture.removeFutureListener(_restartFutureListener);

		_processChannel.write(new StopSidecarProcessCallable());

		_processChannel = null;
	}

	protected void deleteDir(Path dirPath) {
		if (dirPath == null) {
			return;
		}

		try {
			Files.walkFileTree(
				dirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult postVisitDirectory(
							Path dir, IOException ioException)
						throws IOException {

						if (ioException != null) {
							throw ioException;
						}

						Files.delete(dir);

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Files.delete(file);

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(
							Path file, IOException ioException)
						throws IOException {

						if (ioException instanceof NoSuchFileException) {
							return FileVisitResult.CONTINUE;
						}

						throw ioException;
					}

				});
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete dir: " + dirPath, ioException);
			}
		}
	}

	protected Path getDataHome() {
		return _dataHome;
	}

	protected String getNodeName() {
		return _DEFAULT_NODE_NAME;
	}

	protected Path getPathData() {
		return _pathData;
	}

	protected void setClusterDiscoverySettings(
		SettingsBuilder settingsBuilder) {

		settingsBuilder.put("cluster.initial_master_nodes", getNodeName());
	}

	private String _createClasspath(
		Path folderPath, DirectoryStream.Filter<Path> filter) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				folderPath, filter)) {

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
				"Unable to iterate folder: " + folderPath, ioException);
		}
	}

	private ProcessConfig _createProcessConfig(String sidecarLibClassPath) {
		ProcessConfig.Builder processConfigBuilder =
			new ProcessConfig.Builder();

		ProtectionDomain protectionDomain = Sidecar.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL bundleURL = codeSource.getLocation();

		processConfigBuilder.setArguments(_getJVMArguments(bundleURL));

		String bootstrapClasspath = _createClasspath(
			Paths.get(_props.get(PropsKeys.LIFERAY_LIB_GLOBAL_DIR)),
			path -> {
				String name = String.valueOf(path.getFileName());

				if (name.contains("petra")) {
					return true;
				}

				return false;
			});

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
				sidecarLibClassPath, File.pathSeparator, bundleURL.getPath(),
				File.pathSeparator, bootstrapClasspath));

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
			_sidecarTempDir = Files.createTempDirectory("sidecar");
		}
		catch (IOException ioException) {
			throw new IllegalStateException(
				"Unable to create temp folder", ioException);
		}

		Path configFolder = _sidecarTempDir.resolve("config");

		try {
			Files.createDirectories(configFolder);

			Files.write(
				configFolder.resolve("log4j2.properties"),
				Arrays.asList(
					"logger.deprecation.name=org.elasticsearch.deprecation",
					"logger.deprecation.level=error",
					ResourceUtil.getResourceAsString(
						Sidecar.class, "/log4j2.properties")));
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to copy log4j2.properties to " + configFolder,
				ioException);
		}

		arguments.add("-Des.path.conf=" + configFolder);

		arguments.add("-Des.path.home=" + _sidecarHome.toString());
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
		arguments.add("-Djava.io.tmpdir=" + _sidecarTempDir);

		URLClassLoader urlClassLoader = new URLClassLoader(
			new URL[] {bundleURL});

		URL url = urlClassLoader.findResource("META-INF/sidecar.policy");

		arguments.add("-Djava.security.policy=" + url.toString());

		arguments.add("-Djna.nosys=true");

		return arguments;
	}

	private byte[] _getModifiedClassBytes(
			Class<?> clazz, String methodName,
			Consumer<MethodVisitor> methodVisitorConsumer)
		throws IOException {

		try (InputStream inputStream = clazz.getResourceAsStream(
				clazz.getSimpleName() + ".class")) {

			ClassReader classReader = new ClassReader(inputStream);

			ClassWriter classWriter = new ClassWriter(
				classReader, ClassWriter.COMPUTE_MAXS);

			classReader.accept(
				new ClassVisitor(Opcodes.ASM5, classWriter) {

					@Override
					public MethodVisitor visitMethod(
						int access, String name, String description,
						String signature, String[] exceptions) {

						MethodVisitor methodVisitor = super.visitMethod(
							access, name, description, signature, exceptions);

						if (!name.equals(methodName)) {
							return methodVisitor;
						}

						return new MethodVisitor(Opcodes.ASM5) {

							@Override
							public void visitCode() {
								methodVisitorConsumer.accept(methodVisitor);
							}

							@Override
							public void visitMaxs(int maxStack, int maxLocals) {
								methodVisitor.visitMaxs(0, 0);
							}

						};
					}

				},
				0);

			return classWriter.toByteArray();
		}
	}

	private String[] _getSidecarArguments() {
		SettingsBuilder settingsBuilder = new SettingsBuilder(
			Settings.builder());

		settingsBuilder.put(
			"bootstrap.memory_lock",
			_elasticsearchConfiguration.bootstrapMlockAll());
		settingsBuilder.put("bootstrap.system_call_filter", false);
		settingsBuilder.put(
			"cluster.routing.allocation.disk.threshold_enabled", false);
		settingsBuilder.put(
			"cluster.name", _elasticsearchConfiguration.clusterName());
		settingsBuilder.put(
			"http.cors.enabled", _elasticsearchConfiguration.httpCORSEnabled());
		settingsBuilder.put("path.data", _pathData.toString());
		settingsBuilder.put("path.logs", _pathLogs.toString());
		settingsBuilder.put("path.repo", _pathRepo.toString());

		if (_elasticsearchConfiguration.httpCORSEnabled()) {
			settingsBuilder.put(
				"http.cors.allow-origin",
				_elasticsearchConfiguration.httpCORSAllowOrigin());

			settingsBuilder.loadFromSource(
				_elasticsearchConfiguration.httpCORSConfigurations());
		}

		settingsBuilder.put("node.name", getNodeName());
		settingsBuilder.put("node.data", true);
		settingsBuilder.put("node.ingest", true);
		settingsBuilder.put("node.master", true);
		settingsBuilder.put("node.store.allow_mmap", false);

		settingsBuilder.loadFromSource(
			_elasticsearchConfiguration.additionalConfigurations());

		setClusterDiscoverySettings(settingsBuilder);

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

	private static final String _MODIFIED_CLASS_NAME_KEY_STORE_WRAPPER =
		"org.elasticsearch.common.settings.KeyStoreWrapper";

	private static final String _MODIFIED_CLASS_NAME_NATIVES =
		"org.elasticsearch.bootstrap.Natives";

	private static final Log _log = LogFactoryUtil.getLog(Sidecar.class);

	private DefaultNoticeableFuture<String> _addressNoticeableFuture;
	private final ComponentContext _componentContext;
	private final String _componentName;
	private final Path _dataHome;
	private final ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Path _pathData;
	private final Path _pathLogs;
	private final Path _pathRepo;
	private ProcessChannel<Serializable> _processChannel;
	private final ProcessExecutor _processExecutor;
	private final Props _props;
	private FutureListener<Serializable> _restartFutureListener;
	private final Path _sidecarHome;
	private Path _sidecarTempDir;

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