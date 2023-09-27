/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.thumbnails.internal.upgrade.v1_0_0;

import com.liferay.adaptive.media.document.library.thumbnails.internal.util.AMCompanyThumbnailConfigurationInitializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Roberto Díaz
 */
public class DocumentLibraryThumbnailsConfigurationUpgradeProcess
	extends UpgradeProcess {

	public DocumentLibraryThumbnailsConfigurationUpgradeProcess(
		AMCompanyThumbnailConfigurationInitializer
			amCompanyThumbnailConfigurationInitializer,
		CompanyLocalService companyLocalService) {

		_amCompanyThumbnailConfigurationInitializer =
			amCompanyThumbnailConfigurationInitializer;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompany(
				company -> {
					try {
						_amCompanyThumbnailConfigurationInitializer.
							initializeCompany(company);
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryThumbnailsConfigurationUpgradeProcess.class);

	private final AMCompanyThumbnailConfigurationInitializer
		_amCompanyThumbnailConfigurationInitializer;
	private final CompanyLocalService _companyLocalService;

}