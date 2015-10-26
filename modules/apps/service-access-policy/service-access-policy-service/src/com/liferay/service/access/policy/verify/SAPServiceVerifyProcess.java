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

package com.liferay.service.access.policy.verify;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.service.access.policy.service.SAPEntryLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = SAPServiceVerifyProcess.class)
public class SAPServiceVerifyProcess extends VerifyProcess {

	@Activate
	@Override
	protected void doVerify() throws Exception {
		verifyDefaultSAPEntry();
	}

	@Reference
	protected void setSAPEntryLocalService(
		SAPEntryLocalService sapEntryLocalService) {

		_sapEntryLocalService = sapEntryLocalService;
	}

	@Reference(target ="(original.bean=true)")
	protected void setServiceContext(ServletContext serviceContext) {
	}

	protected void verifyDefaultSAPEntry() {
		for (long companyId : PortalInstances.getCompanyIds()) {
			try {
				_sapEntryLocalService.checkSystemSAPEntries(companyId);
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add default service access policy for company " +
						companyId,
					pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAPServiceVerifyProcess.class);

	private SAPEntryLocalService _sapEntryLocalService;

}