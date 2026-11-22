/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.security.permisison.resource;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Matija Petanjek
 */
public class SchedulerResponsePermission {

	public static boolean contains(
		PermissionChecker permissionChecker,
		SchedulerResponse schedulerResponse) {

		String jobName = schedulerResponse.getJobName();

		if (jobName.startsWith("DISPATCH_JOB_")) {
			return false;
		}

		Message message = schedulerResponse.getMessage();

		long companyId = message.getLong("companyId");

		if (companyId == CompanyConstants.SYSTEM) {
			return permissionChecker.isOmniadmin();
		}

		if (companyId == permissionChecker.getCompanyId()) {
			return true;
		}

		return false;
	}

}