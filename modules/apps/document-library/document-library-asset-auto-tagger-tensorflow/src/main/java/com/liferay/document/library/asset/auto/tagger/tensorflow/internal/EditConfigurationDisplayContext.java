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

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.TensorFlowDownloadHelper;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class EditConfigurationDisplayContext {

	public EditConfigurationDisplayContext(
		TensorFlowDownloadHelper tensorFlowDownloadHelper,
		TensorFlowImageAssetAutoTagProviderCompanyConfiguration
			tensorFlowImageAssetAutoTagProviderCompanyConfiguration) {

		_tensorFlowDownloadHelper = tensorFlowDownloadHelper;
		_tensorFlowImageAssetAutoTagProviderCompanyConfiguration =
			tensorFlowImageAssetAutoTagProviderCompanyConfiguration;
	}

	public boolean isDownloaded() throws PortalException {
		return _tensorFlowDownloadHelper.isDownloaded();
	}

	public boolean isDownloadFailed() {
		if (isTensorFlowImageAssetAutoTagProviderEnabled() &&
			_tensorFlowDownloadHelper.isDownloadFailed()) {

			return true;
		}

		return false;
	}

	public boolean isTensorFlowImageAssetAutoTagProviderEnabled() {
		if ((_tensorFlowImageAssetAutoTagProviderCompanyConfiguration !=
				null) &&
			_tensorFlowImageAssetAutoTagProviderCompanyConfiguration.
				enabled()) {

			return true;
		}

		return false;
	}

	private final TensorFlowDownloadHelper _tensorFlowDownloadHelper;
	private final TensorFlowImageAssetAutoTagProviderCompanyConfiguration
		_tensorFlowImageAssetAutoTagProviderCompanyConfiguration;

}