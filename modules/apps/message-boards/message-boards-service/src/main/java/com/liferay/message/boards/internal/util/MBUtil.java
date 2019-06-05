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

package com.liferay.message.boards.internal.util;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.ThemeConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

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

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			groupPermissions, guestPermissions);

		serviceContext.setModelPermissions(modelPermissions);
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

	public static void updateCategoryMessageCount(final long categoryId) {
		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				MBCategoryLocalServiceUtil.updateMessageCount(categoryId);

				return null;
			}

		};

		TransactionCommitCallbackUtil.registerCallback(callable);
	}

	public static void updateCategoryStatistics(final long categoryId) {
		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				MBCategoryLocalServiceUtil.updateStatistics(categoryId);

				return null;
			}

		};

		TransactionCommitCallbackUtil.registerCallback(callable);
	}

	public static void updateCategoryThreadCount(final long categoryId) {
		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				MBCategoryLocalServiceUtil.updateThreadCount(categoryId);

				return null;
			}

		};

		TransactionCommitCallbackUtil.registerCallback(callable);
	}

	public static void updateThreadMessageCount(final long threadId) {
		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				MBThreadLocalServiceUtil.updateMessageCount(threadId);

				return null;
			}

		};

		TransactionCommitCallbackUtil.registerCallback(callable);
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