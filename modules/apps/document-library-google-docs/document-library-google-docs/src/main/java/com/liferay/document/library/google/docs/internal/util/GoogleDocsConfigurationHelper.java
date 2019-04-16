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

package com.liferay.document.library.google.docs.internal.util;

import com.liferay.document.library.google.drive.configuration.DLGoogleDriveCompanyConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;

/**
 * @author Iván Zaera
 */
public class GoogleDocsConfigurationHelper {

	public GoogleDocsConfigurationHelper(long companyId)
		throws ConfigurationException {

		_dlGoogleDriveCompanyConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				DLGoogleDriveCompanyConfiguration.class, companyId);
	}

	public String getGoogleAppsAPIKey() {
		return _dlGoogleDriveCompanyConfiguration.pickerAPIKey();
	}

	public String getGoogleClientId() {
		return _dlGoogleDriveCompanyConfiguration.clientId();
	}

	private final DLGoogleDriveCompanyConfiguration
		_dlGoogleDriveCompanyConfiguration;

}