/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.upgrade.v3_0_1;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

/**
 * @author Christopher Kian
 */
public class SAPEntryUpgradeProcess extends UpgradeProcess {

	public SAPEntryUpgradeProcess(SAPEntryLocalService sapEntryLocalService) {
		_sapEntryLocalService = sapEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				try {
					_sapEntryLocalService.checkSystemSAPEntries(companyId);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to add default service access policy for " +
							"company " + companyId,
						portalException);
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAPEntryUpgradeProcess.class);

	private final SAPEntryLocalService _sapEntryLocalService;

}