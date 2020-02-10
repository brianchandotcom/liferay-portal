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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderProcessConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process.GetLabelProbabilitiesProcessCallable;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.TensorflowProcessHolder;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
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
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class TensorFlowImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			TensorFlowImageAssetAutoTagProviderCompanyConfiguration
				tensorFlowImageAssetAutoTagProviderCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						TensorFlowImageAssetAutoTagProviderCompanyConfiguration.
							class,
						fileEntry.getCompanyId());

			if (tensorFlowImageAssetAutoTagProviderCompanyConfiguration.
					enabled() &&
				!_isTemporary(fileEntry)) {

				FileVersion fileVersion = fileEntry.getFileVersion();

				if (_isSupportedMimeType(fileVersion.getMimeType())) {
					return _label(
						FileUtil.getBytes(fileVersion.getContentStream(false)),
						fileVersion.getMimeType(),
						tensorFlowImageAssetAutoTagProviderCompanyConfiguration.
							confidenceThreshold());
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return Collections.emptyList();
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws IOException {

		Bundle bundle = bundleContext.getBundle();

		URL url = bundle.getResource(
			"META-INF/tensorflow/imagenet_comp_graph_label_strings.txt");

		_labels = StringUtil.splitLines(StringUtil.read(url.openStream()));

		modified(properties);

		_tensorflowProcessHolder = new TensorflowProcessHolder(
			_processExecutor, bundle);
	}

	@Deactivate
	protected void deactivate() {
		_tensorflowProcessHolder.destroy();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_tensorFlowImageAssetAutoTagProviderProcessConfiguration =
			ConfigurableUtil.createConfigurable(
				TensorFlowImageAssetAutoTagProviderProcessConfiguration.class,
				properties);
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

	private boolean _isSupportedMimeType(String mimeType) {
		return _supportedMimeTypes.contains(mimeType);
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private List<String> _label(
		byte[] imageBytes, String mimeType, float confidenceThreshold) {

		int maximumNumberOfRelaunches =
			_tensorFlowImageAssetAutoTagProviderProcessConfiguration.
				maximumNumberOfRelaunches();
		long maximumNumberOfRelaunchesTimeout =
			_tensorFlowImageAssetAutoTagProviderProcessConfiguration.
				maximumNumberOfRelaunchesTimeout();

		float[] labelProbabilities = _tensorflowProcessHolder.execute(
			new GetLabelProbabilitiesProcessCallable(imageBytes, mimeType),
			maximumNumberOfRelaunches, maximumNumberOfRelaunchesTimeout * 1000);

		Stream<Integer> stream = _getBestIndexesStream(
			labelProbabilities, confidenceThreshold);

		return stream.map(
			i -> _labels[i]
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TensorFlowImageAssetAutoTagProvider.class);

	private static final Set<String> _supportedMimeTypes = new HashSet<>(
		Arrays.asList(
			ContentTypes.IMAGE_BMP, ContentTypes.IMAGE_JPEG,
			ContentTypes.IMAGE_PNG));

	@Reference
	private ConfigurationProvider _configurationProvider;

	private String[] _labels;

	@Reference
	private ProcessExecutor _processExecutor;

	private volatile TensorFlowImageAssetAutoTagProviderProcessConfiguration
		_tensorFlowImageAssetAutoTagProviderProcessConfiguration;
	private TensorflowProcessHolder _tensorflowProcessHolder;

}