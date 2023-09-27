/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upload.configuration;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Pei-Jung Lan
 */
public class UploadServletRequestConfigurationProviderUtil {

	public static long getMaxSize() {
		return _uploadServletRequestConfigurationProvider.getMaxSize();
	}

	public static long getMaxTries() {
		return _uploadServletRequestConfigurationProvider.getMaxTries();
	}

	public static String getTempDir() {
		return _uploadServletRequestConfigurationProvider.getTempDir();
	}

	private static volatile UploadServletRequestConfigurationProvider
		_uploadServletRequestConfigurationProvider =
			ServiceProxyFactory.newServiceTrackedInstance(
				UploadServletRequestConfigurationProvider.class,
				UploadServletRequestConfigurationProviderUtil.class,
				"_uploadServletRequestConfigurationProvider", false);

}