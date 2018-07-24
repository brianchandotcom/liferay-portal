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
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorflowImageAssetAutoTagProviderConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.InceptionImageLabeler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorflowImageAssetAutoTagProviderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class TensorFlowImageAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public List<String> getTagNames(FileEntry fileEntry) {
		if (_tensorflowImageAutoTaggerConfiguration.enabled() &&
			!_isTemporary(fileEntry)) {

			try {
				FileVersion fileVersion = fileEntry.getFileVersion();

				if (_accepts(fileVersion.getMimeType())) {
					return _inceptionImageLabeler.label(
						FileUtil.getBytes(fileVersion.getContentStream(false)),
						_tensorflowImageAutoTaggerConfiguration.
							confidenceThreshold());
				}
			}
			catch (IOException | PortalException e) {
				_log.error(e, e);
			}
		}

		return Collections.emptyList();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_tensorflowImageAutoTaggerConfiguration =
			ConfigurableUtil.createConfigurable(
				TensorflowImageAssetAutoTagProviderConfiguration.class,
				properties);
	}

	private boolean _accepts(String mimeType) {
		return ArrayUtil.contains(_SUPPORTED_MIME_TYPES, mimeType);
	}

	private boolean _isTemporary(FileEntry fileEntry) {
		return fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);
	}

	private static final String[] _SUPPORTED_MIME_TYPES = {
		ContentTypes.IMAGE_JPEG, ContentTypes.IMAGE_BMP, ContentTypes.IMAGE_PNG
	};

	private static final Log _log = LogFactoryUtil.getLog(
		TensorFlowImageAssetAutoTagProvider.class);

	@Reference
	private InceptionImageLabeler _inceptionImageLabeler;

	private volatile TensorflowImageAssetAutoTagProviderConfiguration
		_tensorflowImageAutoTaggerConfiguration;

}