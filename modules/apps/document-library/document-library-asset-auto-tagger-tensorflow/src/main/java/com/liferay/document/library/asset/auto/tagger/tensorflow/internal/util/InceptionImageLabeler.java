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

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process.GetLabelProbabilitiesProcessCallable;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process.TensorFlowDaemonProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessConfig.Builder;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.process.ProcessLog.Level;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalClassPathUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * See the <a
 * href="https://github.com/tensorflow/tensorflow/blob/master/tensorflow/java/src/main/java/org/tensorflow/examples/LabelImage.java">org.tensorflow.examples.LabelImage</a>
 * class for more information.
 *
 * @author Alejandro Tardín
 */
@Component(service = InceptionImageLabeler.class)
public class InceptionImageLabeler {

	public List<String> label(
		byte[] imageBytes, String mimeType, float confidenceThreshold) {

		float[] labelProbabilities = _getLabelProbabilities(
			imageBytes, mimeType);

		Stream<Integer> stream = _getBestIndexesStream(
			labelProbabilities, confidenceThreshold);

		return stream.map(
			i -> _labels[i]
		).collect(
			Collectors.toList()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		Bundle bundle = bundleContext.getBundle();

		_initializeLabels(bundle);

		_tensorflowWorkDir = bundle.getDataFile("tensorflow-workdir");

		_tensorflowWorkDir.mkdirs();

		_processConfig = _createProcessConfig(
			bundle, _tensorflowWorkDir.toPath());
	}

	@Deactivate
	protected void deactivate() {
		_stopProcess();

		FileUtil.deltree(_tensorflowWorkDir);
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
			InceptionImageLabeler.class.getProtectionDomain();

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
			InceptionImageLabeler.class.getClassLoader());
		builder.setRuntimeClassPath(classPath);

		return builder.build();
	}

	private Stream<Integer> _getBestIndexesStream(
		float[] probabilities, float confidenceThreshold) {

		List<Integer> bestIndexes = new ArrayList<>();

		for (int i = 0; i < probabilities.length; i++) {
			if ((probabilities[i] >= confidenceThreshold) &&
				(i < _labels.length)) {

				bestIndexes.add(i);
			}
		}

		return bestIndexes.stream();
	}

	private InputStream _getInputStream(Bundle bundle, String path)
		throws IOException {

		URL url = bundle.getResource(path);

		return url.openStream();
	}

	private float[] _getLabelProbabilities(byte[] imageBytes, String mimeType) {
		ProcessChannel<String> processChannel = _processChannel;

		if (processChannel == null) {
			synchronized (this) {
				if (_processChannel == null) {
					try {
						_processChannel = _processExecutor.execute(
							_processConfig,
							new TensorFlowDaemonProcessCallable());
					}
					catch (ProcessException pe) {
						ReflectionUtil.throwException(pe);
					}
				}

				processChannel = _processChannel;
			}
		}

		Future<float[]> future = processChannel.write(
			new GetLabelProbabilitiesProcessCallable(imageBytes, mimeType));

		try {
			return future.get();
		}
		catch (Exception e) {
			_stopProcess();

			return ReflectionUtil.throwException(e);
		}
	}

	private void _initializeLabels(Bundle bundle) throws IOException {
		_labels = StringUtil.splitLines(
			StringUtil.read(
				_getInputStream(
					bundle,
					"META-INF/tensorflow" +
						"/imagenet_comp_graph_label_strings.txt")));
	}

	private synchronized void _stopProcess() {
		if (_processChannel != null) {
			Future<?> future = _processChannel.getProcessNoticeableFuture();

			future.cancel(true);

			_processChannel = null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InceptionImageLabeler.class);

	private String[] _labels;
	private volatile ProcessChannel<String> _processChannel;
	private ProcessConfig _processConfig;

	@Reference
	private ProcessExecutor _processExecutor;

	private File _tensorflowWorkDir;

}