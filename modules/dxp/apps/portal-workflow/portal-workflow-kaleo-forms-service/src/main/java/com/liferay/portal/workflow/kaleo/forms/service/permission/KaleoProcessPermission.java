/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

		KaleoProcess kaleoProcess =
			KaleoProcessLocalServiceUtil.getKaleoProcess(kaleoProcessId);

		return contains(permissionChecker, kaleoProcess, actionId);
	}

}