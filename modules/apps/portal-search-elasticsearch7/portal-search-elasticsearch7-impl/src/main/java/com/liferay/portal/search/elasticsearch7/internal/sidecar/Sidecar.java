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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.elasticsearch.common.settings.Settings;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tina Tian
 */
public class Sidecar {

	public Sidecar(
		ElasticsearchConfiguration elasticsearchConfiguration,
		ProcessExecutor processExecutor, Props props) {

		_elasticsearchConfiguration = elasticsearchConfiguration;
		_processExecutor = processExecutor;
		_props = props;

		Path liferayHomePath = Paths.get(props.get(PropsKeys.LIFERAY_HOME));

		liferayHomePath = liferayHomePath.toAbsolutePath();

		_dataHomePath = liferayHomePath.resolve("data/elasticsearch7");
		_logsPath = liferayHomePath.resolve("logs");

		_indicesPath = _dataHomePath.resolve("indices");
		_repoPath = _dataHomePath.resolve("repo");

		Path sidecarHomePath = liferayHomePath.resolve(
			elasticsearchConfiguration.sidecarHome());

		if (!Files.isDirectory(sidecarHomePath)) {
			sidecarHomePath = Paths.get(
				elasticsearchConfiguration.sidecarHome());

			if (!Files.isDirectory(sidecarHomePath)) {
				throw new IllegalArgumentException(
					"Sidecar home " + elasticsearchConfiguration.sidecarHome() +
						" does not exist");
			}
		}

		_sidecarHomePath = sidecarHomePath.toAbsolutePath();
	}

	public String getNetworkHostAddress() {
		if (_address == null) {
			try {
				_address = _addressNoticeableFuture.get();

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Sidecar ", getNodeName(), " is at ", _address));
				}
			}
			catch (Exception exception) {
				_processChannel.write(new StopSidecarProcessCallable());

				throw new IllegalStateException(
					"Unable to get network host address", exception);
			}
		}

		return _address;
	}

	public void start() {
		if (_log.isInfoEnabled()) {
			_log.info("Starting sidecar");
		}

		String sidecarLibClassPath = _createClasspath(
			_sidecarHomePath.resolve("lib"), path -> true);

		try {
			_processChannel = _processExecutor.execute(
				_createProcessConfig(sidecarLibClassPath),
				new SidecarMainProcessCallable(
					_elasticsearchConfiguration.sidecarHeartbeatInterval(),
					_getModifiedClasses(sidecarLibClassPath)));
		}
		catch (ProcessException processException) {
			throw new RuntimeException(
				"Unable to start sidecar process", processException);
		}

		NoticeableFuture<Serializable> noticeableFuture =
			_processChannel.getProcessNoticeableFuture();

		_restartFutureListener = new RestartFutureListener();

		noticeableFuture.addFutureListener(_restartFutureListener);

		_addressNoticeableFuture = _processChannel.write(
			new StartSidecarProcessCallable(_getSidecarArguments()));
	}

	public void stop() {
		deleteDir(_sidecarTempDirPath);

		if (_processChannel == null) {
			return;
		}

		NoticeableFuture<Serializable> noticeableFuture =
			_processChannel.getProcessNoticeableFuture();

		noticeableFuture.removeFutureListener(_restartFutureListener);

		_processChannel.write(new StopSidecarProcessCallable());

		try {
			noticeableFuture.get(
				_elasticsearchConfiguration.sidecarShutdownTimeout(),
				TimeUnit.MILLISECONDS);
		}
		catch (Exception exception) {
			if (!noticeableFuture.isDone()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Forcibly shutdown sidecar process because it did ",
							"not shut down in ",
							_elasticsearchConfiguration.
								sidecarShutdownTimeout(),
							" ms"));
				}

				noticeableFuture.cancel(true);
			}
		}

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
							Path path, IOException ioException)
						throws IOException {

						if (ioException != null) {
							throw ioException;
						}

						Files.delete(path);

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						Files.delete(path);

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(
							Path path, IOException ioException)
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
				_log.warn("Unable to delete " + dirPath, ioException);
			}
		}
	}

	protected Path getDataHomePath() {
		return _dataHomePath;
	}

	protected String getLogProperties() {
		return StringPool.BLANK;
	}

	protected String getNodeName() {
		return "liferay";
	}

	protected Path getPathData() {
		return _indicesPath;
	}

	protected void setClusterDiscoverySettings(
		SettingsBuilder settingsBuilder) {

		settingsBuilder.put("cluster.initial_master_nodes", getNodeName());
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
		ProcessConfig.Builder processConfigBuilder =
			new ProcessConfig.Builder();

		ProtectionDomain protectionDomain = Sidecar.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL bundleURL = codeSource.getLocation();

		processConfigBuilder.setArguments(_getJVMArguments(bundleURL));

		File file = new File(_props.get(PropsKeys.LIFERAY_LIB_GLOBAL_DIR));

		String bootstrapClasspath = _createClasspath(
			file.toPath(),
			path -> {
				String name = String.valueOf(path.getFileName());

				if (name.contains("petra")) {
					return true;
				}

				return false;
			});

		processConfigBuilder.setBootstrapClassPath(bootstrapClasspath);

		processConfigBuilder.setEnvironment(
			HashMapBuilder.putAll(
				System.getenv()
			).put(
				"HOSTNAME", "localhost"
			).build());

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
					"logger.deprecation.level=error", getLogProperties(),
					ResourceUtil.getResourceAsString(
						Sidecar.class, "/log4j2.properties")));
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to copy log4j2.properties to " + configFolder,
				ioException);
		}

		arguments.add("-Des.path.conf=" + configFolder);
		arguments.add("-Des.path.home=" + _sidecarHomePath.toString());
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

	private Map<String, byte[]> _getModifiedClasses(
		String sidecarLibClassPath) {

		Map<String, byte[]> modifiedClasses = new HashMap<>();

		try {
			ClassLoader classLoader = new URLClassLoader(
				ClassPathUtil.getClassPathURLs(sidecarLibClassPath), null);

			modifiedClasses.put(
				"org.elasticsearch.bootstrap.Natives",
				_getModifiedClassBytes(
					classLoader.loadClass(
						"org.elasticsearch.bootstrap.Natives"),
					"definitelyRunningAsRoot",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.ICONST_0);
						methodVisitor.visitInsn(Opcodes.IRETURN);
					}));

			modifiedClasses.put(
				"org.elasticsearch.common.settings.KeyStoreWrapper",
				_getModifiedClassBytes(
					classLoader.loadClass(
						"org.elasticsearch.common.settings.KeyStoreWrapper"),
					"save",
					methodVisitor -> {
						methodVisitor.visitCode();
						methodVisitor.visitInsn(Opcodes.RETURN);
					}));
		}
		catch (Exception exception) {
			_log.error("Unable to modify classes", exception);
		}

		return modifiedClasses;
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
		settingsBuilder.put("path.data", _indicesPath.toString());
		settingsBuilder.put("path.logs", _logsPath.toString());
		settingsBuilder.put("path.repo", _repoPath.toString());

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

	private static final Log _log = LogFactoryUtil.getLog(Sidecar.class);

	private String _address;
	private NoticeableFuture<String> _addressNoticeableFuture;
	private final Path _dataHomePath;
	private final ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Path _indicesPath;
	private final Path _logsPath;
	private ProcessChannel<Serializable> _processChannel;
	private final ProcessExecutor _processExecutor;
	private final Props _props;
	private final Path _repoPath;
	private FutureListener<Serializable> _restartFutureListener;
	private final Path _sidecarHomePath;
	private Path _sidecarTempDirPath;

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

			SidecarComponentUtil.disableSidecarElasticsearchConnectionManager();

			if (_log.isInfoEnabled()) {
				_log.info("Restaring sidecar process");
			}

			SidecarComponentUtil.enableSidecarElasticsearchConnectionManager();
		}

	}

}