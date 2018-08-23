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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderProcessConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.osgi.commands.TensorflowAssetAutoTagProviderOSGiCommands;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process.TensorFlowDaemonProcessCallable;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessConfig.Builder;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.process.ProcessLog.Level;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalClassPathUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.Dictionary;
import java.util.concurrent.Future;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * @author Alejandro Tardín
 */
public class TensorflowProcessUtil {

	public static void activate(BundleContext bundleContext)
		throws IOException {

		Bundle bundle = bundleContext.getBundle();

		_tensorflowWorkDir = bundle.getDataFile("tensorflow-workdir");

		_tensorflowWorkDir.mkdirs();

		_processConfig = _createProcessConfig(
			bundle, _tensorflowWorkDir.toPath());
	}

	public static void deactivate() {
		stop();

		FileUtil.deltree(_tensorflowWorkDir);
	}

	public static void resetCounter() {
		_processStarts = 0;
	}

	public static <T extends Serializable> T run(
		ProcessExecutor processExecutor,
		TensorFlowImageAssetAutoTagProviderProcessConfiguration
			tensorFlowImageAssetAutoTagProviderProcessConfiguration,
		ProcessCallable<T> processCallable) {

		ProcessChannel<String> processChannel = _processChannel;

		if (processChannel == null) {
			synchronized (TensorflowProcessUtil.class) {
				processChannel = _startProcess(
					processExecutor,
					tensorFlowImageAssetAutoTagProviderProcessConfiguration);
			}
		}

		Future<T> future = processChannel.write(processCallable);

		try {
			return future.get();
		}
		catch (Exception e) {
			stop();

			return ReflectionUtil.throwException(e);
		}
	}

	public static synchronized void stop() {
		if (_processChannel != null) {
			Future<?> future = _processChannel.getProcessNoticeableFuture();

			future.cancel(true);

			_processChannel = null;
		}
	}

	private static String _createClassPath(Bundle bundle, Path tempPath)
		throws IOException {

		StringBundler sb = new StringBundler();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		for (String pathString :
				StringUtil.split(headers.get(Constants.BUNDLE_CLASSPATH))) {

			if (pathString.equals(StringPool.PERIOD)) {
				continue;
			}

			URL url = bundle.getEntry(pathString);

			Path path = Paths.get(url.getFile());

			try (InputStream inputStream = url.openStream()) {
				Path targetPath = tempPath.resolve(path.getFileName());

				sb.append(targetPath);

				sb.append(File.pathSeparator);

				Files.copy(
					inputStream, targetPath,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}

		ProtectionDomain protectionDomain =
			TensorflowProcessUtil.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		sb.append(url.getPath());

		sb.append(File.pathSeparator);

		ProcessConfig portalProcessConfig =
			PortalClassPathUtil.getPortalProcessConfig();

		sb.append(portalProcessConfig.getBootstrapClassPath());

		return sb.toString();
	}

	private static ProcessConfig _createProcessConfig(
			Bundle bundle, Path tempPath)
		throws IOException {

		Builder builder = new Builder();

		String classPath = _createClassPath(bundle, tempPath);

		builder.setBootstrapClassPath(classPath);

		builder.setProcessLogConsumer(
			processLog -> {
				if (Level.DEBUG == processLog.getLevel()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else if (Level.INFO == processLog.getLevel()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							processLog.getMessage(), processLog.getThrowable());
					}
				}
				else if (Level.WARN == processLog.getLevel()) {
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
		builder.setReactClassLoader(
			TensorflowProcessUtil.class.getClassLoader());
		builder.setRuntimeClassPath(classPath);

		return builder.build();
	}

	private static ProcessChannel<String> _startProcess(
		ProcessExecutor processExecutor,
		TensorFlowImageAssetAutoTagProviderProcessConfiguration
			tensorFlowImageAssetAutoTagProviderProcessConfiguration) {

		ProcessChannel<String> processChannel;

		if (_processChannel == null) {
			try {
				int maximumNumberOfRelaunches =
					tensorFlowImageAssetAutoTagProviderProcessConfiguration.
						maximumNumberOfRelaunches();

				long maximumNumberOfRelaunchesTimeoutMillis =
					tensorFlowImageAssetAutoTagProviderProcessConfiguration.
						maximumNumberOfRelaunchesTimeout() * 1000;

				if ((System.currentTimeMillis() - _lastProcessStartMillis) >
						maximumNumberOfRelaunchesTimeoutMillis) {

					_processStarts = 0;
				}

				if (_processStarts++ > maximumNumberOfRelaunches) {
					throw new SystemException(
						StringBundler.concat(
							"The tensorflow process has crashed more than ",
							maximumNumberOfRelaunches,
							" times. It is now disabled. To enable it again ",
							"please open the Gogo shell and run ",
							TensorflowAssetAutoTagProviderOSGiCommands.SCOPE,
							StringPool.COLON,
							TensorflowAssetAutoTagProviderOSGiCommands.
								RESET_PROCESS_COUNTER));
				}

				_processChannel = processExecutor.execute(
					_processConfig, new TensorFlowDaemonProcessCallable());

				_lastProcessStartMillis = System.currentTimeMillis();
			}
			catch (ProcessException pe) {
				ReflectionUtil.throwException(pe);
			}
		}

		processChannel = _processChannel;

		return processChannel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TensorflowProcessUtil.class);

	private static long _lastProcessStartMillis;
	private static volatile ProcessChannel<String> _processChannel;
	private static ProcessConfig _processConfig;
	private static int _processStarts;
	private static File _tensorflowWorkDir;

}