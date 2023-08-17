/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.internal.verify;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Mika Koivisto
 */
@Component(property = "initial.deployment=true", service = VerifyProcess.class)
public class SAPServiceVerifyProcess extends VerifyProcess {

	public void verifyDefaultSAPEntry() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
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
	}

	@Override
	protected void doVerify() throws Exception {
		verifyDefaultSAPEntry();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAPServiceVerifyProcess.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

}