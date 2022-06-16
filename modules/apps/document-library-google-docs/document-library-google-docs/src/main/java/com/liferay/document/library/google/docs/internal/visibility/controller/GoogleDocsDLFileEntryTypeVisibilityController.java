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

package com.liferay.document.library.google.docs.internal.visibility.controller;

import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.document.library.google.drive.configuration.DLGoogleDriveCompanyConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.visibility.controller.DLFileEntryTypeVisibilityController;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "dl.file.entry.type.key=" + GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY,
	service = DLFileEntryTypeVisibilityController.class
)
public class GoogleDocsDLFileEntryTypeVisibilityController
	implements DLFileEntryTypeVisibilityController {

	@Override
	public boolean isVisible(long userId, DLFileEntryType dlFileEntryType) {
		try {
			DLGoogleDriveCompanyConfiguration
				dlGoogleDriveCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						DLGoogleDriveCompanyConfiguration.class,
						dlFileEntryType.getCompanyId());

			if (Validator.isNull(
					dlGoogleDriveCompanyConfiguration.pickerAPIKey())) {

				return false;
			}

			return true;
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleDocsDLFileEntryTypeVisibilityController.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}