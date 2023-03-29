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

package com.liferay.osb.faro.util;

import com.liferay.osb.faro.constants.FaroUserConstants;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.FaroUserLocalServiceUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Matthew Kong
 */
public class FaroPermissionChecker {

	public static boolean isGroupAdmin(long groupId) {
		return _hasPermission(
			groupId, PermissionThreadLocal.getPermissionChecker(),
			RoleConstants.SITE_ADMINISTRATOR, RoleConstants.SITE_OWNER);
	}

	public static boolean isGroupMember(long groupId) {
		return isGroupMember(
			groupId, PermissionThreadLocal.getPermissionChecker());
	}

	public static boolean isGroupMember(
		long groupId, PermissionChecker permissionChecker) {

		return _hasPermission(
			groupId, permissionChecker, RoleConstants.SITE_ADMINISTRATOR,
			RoleConstants.SITE_MEMBER, RoleConstants.SITE_OWNER);
	}

	public static boolean isGroupOwner(long groupId) {
		return _hasPermission(
			groupId, PermissionThreadLocal.getPermissionChecker(),
			RoleConstants.SITE_OWNER);
	}

	private static boolean _hasPermission(
		long groupId, PermissionChecker permissionChecker,
		String... roleNames) {

		if (permissionChecker.isOmniadmin()) {
			return true;
		}

		FaroUser faroUser = FaroUserLocalServiceUtil.fetchFaroUser(
			groupId, permissionChecker.getUserId());

		if ((faroUser == null) ||
			(faroUser.getStatus() != FaroUserConstants.STATUS_APPROVED)) {

			return false;
		}

		Role role = RoleLocalServiceUtil.fetchRole(faroUser.getRoleId());

		if (role == null) {
			return false;
		}

		for (String roleName : roleNames) {
			if (StringUtil.equals(role.getName(), roleName)) {
				return true;
			}
		}

		return false;
	}

}