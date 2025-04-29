/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upload.configuration;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Pei-Jung Lan
 */
public class UploadServletRequestConfigurationProviderUtil {

	public static long getMaxSize() {
		UploadServletRequestConfigurationProvider
			uploadServletRequestConfigurationProvider =
				_uploadServletRequestConfigurationProviderSnapshot.get();

		return uploadServletRequestConfigurationProvider.getMaxSize();
	}

	public static long getMaxSizeWithPadding() {

		// Add padding for metadata. See LPD-52625.

		return getMaxSize() + _UPLOAD_MAX_SIZE_PADDING;
	}

	public static long getMaxTries() {
		UploadServletRequestConfigurationProvider
			uploadServletRequestConfigurationProvider =
				_uploadServletRequestConfigurationProviderSnapshot.get();

		return uploadServletRequestConfigurationProvider.getMaxTries();
	}

	public static String getTempDir() {
		UploadServletRequestConfigurationProvider
			uploadServletRequestConfigurationProvider =
				_uploadServletRequestConfigurationProviderSnapshot.get();

		return uploadServletRequestConfigurationProvider.getTempDir();
	}

	private static final long _UPLOAD_MAX_SIZE_PADDING = 10000;

	private static final Snapshot<UploadServletRequestConfigurationProvider>
		_uploadServletRequestConfigurationProviderSnapshot = new Snapshot<>(
			UploadServletRequestConfigurationProviderUtil.class,
			UploadServletRequestConfigurationProvider.class);

}