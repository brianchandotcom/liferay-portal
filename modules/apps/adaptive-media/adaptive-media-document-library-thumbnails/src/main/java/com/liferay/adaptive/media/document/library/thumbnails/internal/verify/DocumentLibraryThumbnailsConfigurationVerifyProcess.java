/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.thumbnails.internal.verify;

import com.liferay.adaptive.media.document.library.thumbnails.internal.util.AMCompanyThumbnailConfigurationInitializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(property = "initial.deployment=true", service = VerifyProcess.class)
public class DocumentLibraryThumbnailsConfigurationVerifyProcess
	extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
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

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryThumbnailsConfigurationVerifyProcess.class);

	@Reference
	private AMCompanyThumbnailConfigurationInitializer
		_amCompanyThumbnailConfigurationInitializer;

	@Reference
	private CompanyLocalService _companyLocalService;

}