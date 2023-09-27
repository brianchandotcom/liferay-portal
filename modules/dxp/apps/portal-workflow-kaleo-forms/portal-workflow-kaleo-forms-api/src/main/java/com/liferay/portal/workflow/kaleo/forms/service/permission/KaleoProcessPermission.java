/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class KaleoProcessPermission {

	public static void check(
			PermissionChecker permissionChecker, KaleoProcess kaleoProcess,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoProcess, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long kaleoProcessId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoProcessId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, KaleoProcess kaleoProcess,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				kaleoProcess.getCompanyId(), KaleoProcess.class.getName(),
				kaleoProcess.getKaleoProcessId(), kaleoProcess.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			kaleoProcess.getGroupId(), KaleoProcess.class.getName(),
			kaleoProcess.getKaleoProcessId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long kaleoProcessId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			KaleoProcessLocalServiceUtil.getKaleoProcess(kaleoProcessId),
			actionId);
	}

}