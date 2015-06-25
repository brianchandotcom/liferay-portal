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

package com.liferay.service.access.control.profile.verify;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.service.access.control.profile.service.SACPEntryLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.context.ApplicationContext;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = SACPVerifyProcess.class)
public class SACPVerifyProcess extends VerifyProcess {

	@Activate
	@Override
	protected void doVerify() throws Exception {
		verifyDefaultProfile();
	}

	@Reference(
		target =
			"(org.springframework.context.service.name=" +
				"com.liferay.service.access.control.profile.service)",
		unbind = "-"
	)
	protected void setApplicationContext(
		ApplicationContext applicationContext) {
	}

	@Reference
	protected void setSACPEntryLocalService(
		SACPEntryLocalService sacpEntryLocalService) {

		_sacpEntryLocalService = sacpEntryLocalService;
	}

	@Reference(target ="(original.bean=true)")
	protected void setServiceContext(ServletContext serviceContext) {
	}

	protected void verifyDefaultProfile() {
		for (long companyId : PortalInstances.getCompanyIds()) {
			try {
				_sacpEntryLocalService.checkDefaultProfile(companyId);
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add default service access control profile " +
					"for companyId " + companyId, pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SACPVerifyProcess.class);

	private SACPEntryLocalService _sacpEntryLocalService;

}