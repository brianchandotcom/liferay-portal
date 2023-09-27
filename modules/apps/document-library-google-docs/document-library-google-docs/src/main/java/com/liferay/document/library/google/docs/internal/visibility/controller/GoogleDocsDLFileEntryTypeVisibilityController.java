/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.google.docs.internal.visibility.controller;

import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.document.library.google.drive.configuration.DLGoogleDriveCompanyConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.visibility.controller.DLFileEntryTypeVisibilityController;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
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