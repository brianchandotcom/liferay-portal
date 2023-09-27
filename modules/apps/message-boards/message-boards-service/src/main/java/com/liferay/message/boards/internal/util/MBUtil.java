/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.util;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.ThemeConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sergio González
 */
public class MBUtil {

	public static void propagatePermissions(
			long companyId, long groupId, long parentMessageId,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMBMessage(
			parentMessageId);

		Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(
			groupId);
		Role guestRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			MBMessage.class.getName());

		Map<Long, Set<String>> roleIdsToActionIds =
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, MBMessage.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(parentMessage.getMessageId()), actionIds);

		String[] groupPermissions = _getRolePermissions(
			defaultGroupRole, roleIdsToActionIds);
		String[] guestPermissions = _getRolePermissions(
			guestRole, roleIdsToActionIds);

		serviceContext.setModelPermissions(
			ModelPermissionsFactory.create(
				groupPermissions, guestPermissions, MBMessage.class.getName()));
	}

	public static String replaceMessageBodyPaths(
		ThemeDisplay themeDisplay, String messageBody) {

		return StringUtil.replace(
			messageBody,
			new String[] {
				ThemeConstants.TOKEN_THEME_IMAGES_PATH, "href=\"/", "src=\"/"
			},
			new String[] {
				themeDisplay.getPathThemeImages(),
				"href=\"" + themeDisplay.getURLPortal() + "/",
				"src=\"" + themeDisplay.getURLPortal() + "/"
			});
	}

	private static String[] _getRolePermissions(
		Role role, Map<Long, Set<String>> roleIdsToActionIds) {

		String[] rolePermissions = null;

		Set<String> defaultRoleActionIds = roleIdsToActionIds.get(
			role.getRoleId());

		if (defaultRoleActionIds != null) {
			rolePermissions = defaultRoleActionIds.toArray(new String[0]);
		}
		else {
			rolePermissions = new String[0];
		}

		return rolePermissions;
	}

}