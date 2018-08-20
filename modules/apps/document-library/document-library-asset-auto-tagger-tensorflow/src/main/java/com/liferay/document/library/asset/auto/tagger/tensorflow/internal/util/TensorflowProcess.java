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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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
import java.util.Map;
import java.util.concurrent.Future;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderProcessConfiguration",
	service = TensorflowProcess.class
)
public class TensorflowProcess {

	public void resetCounter() {
		_processStarts = 0;
	}

	public <T extends Serializable> T run(ProcessCallable<T> processCallable) {
		ProcessChannel<String> processChannel = _processChannel;

		if (processChannel == null) {
			synchronized (this) {
				processChannel = _startProcess();
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

	public synchronized void stop() {
		if (_processChannel != null) {
			Future<?> future = _processChannel.getProcessNoticeableFuture();

			future.cancel(true);

			_processChannel = null;
		}
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws IOException {

		Bundle bundle = bundleContext.getBundle();

		_tensorflowWorkDir = bundle.getDataFile("tensorflow-workdir");

		_tensorflowWorkDir.mkdirs();

		_processConfig = _createProcessConfig(
			bundle, _tensorflowWorkDir.toPath());

		modified(properties);
	}

	@Deactivate
	protected void deactivate() {
		stop();

		FileUtil.deltree(_tensorflowWorkDir);
	}

	@Modified
	protected void modified(Map<String, Object> properties) throws IOException {
		_tensorFlowImageAssetAutoTagProviderProcessConfiguration =
			ConfigurableUtil.createConfigurable(
				TensorFlowImageAssetAutoTagProviderProcessConfiguration.class,
				properties);
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
			TensorflowProcess.class.getProtectionDomain();

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
		builder.setReactClassLoader(TensorflowProcess.class.getClassLoader());
		builder.setRuntimeClassPath(classPath);

		return builder.build();
	}

	private ProcessChannel<String> _startProcess() {
		ProcessChannel<String> processChannel;

		if (_processChannel == null) {
			try {
				int maximumNumberOfCrashes =
					_tensorFlowImageAssetAutoTagProviderProcessConfiguration.
						maximumNumberOfCrashes();

				if (_processStarts++ > maximumNumberOfCrashes) {
					_log.error(
						StringBundler.concat(
							"The tensorflow process has crashed more than ",
							maximumNumberOfCrashes,
							" times. It is now disabled. To enable it again ",
							"please open the Gogo shell and run ",
							TensorflowAssetAutoTagProviderOSGiCommands.SCOPE,
							StringPool.COLON,
							TensorflowAssetAutoTagProviderOSGiCommands.
								RESET_PROCESS_COUNTER));
				}

				_processChannel = _processExecutor.execute(
					_processConfig, new TensorFlowDaemonProcessCallable());
			}
			catch (ProcessException pe) {
				ReflectionUtil.throwException(pe);
			}
		}

		processChannel = _processChannel;

		return processChannel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TensorflowProcess.class);

	private volatile ProcessChannel<String> _processChannel;
	private ProcessConfig _processConfig;

	@Reference
	private ProcessExecutor _processExecutor;

	private int _processStarts;
	private volatile TensorFlowImageAssetAutoTagProviderProcessConfiguration
		_tensorFlowImageAssetAutoTagProviderProcessConfiguration;
	private File _tensorflowWorkDir;

}