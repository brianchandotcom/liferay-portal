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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBBanLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Charles May
 * @author Roberto Díaz
 * @author Sergio González
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.message.boards.kernel.model.MBDiscussion"
	}
)
public class MBDiscussionPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, companyId, groupId, className, classPK,
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, classPK, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long messageId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, messageId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBMessage.class.getName(), messageId,
				actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long companyId, long groupId,
		String className, long classPK, String actionId) {

		if (MBBanLocalServiceUtil.hasBan(
				groupId, permissionChecker.getUserId())) {

			return false;
		}

		MBDiscussion mbDiscussion =
			MBDiscussionLocalServiceUtil.fetchDiscussion(className, classPK);

		if (mbDiscussion == null) {
			return false;
		}

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			className);

		if (!resourceActions.contains(actionId)) {
			return true;
		}

		if ((mbDiscussion.getUserId() > 0) &&
			permissionChecker.hasOwnerPermission(
				companyId, className, classPK, mbDiscussion.getUserId(),
				actionId)) {

			return true;
		}

		Boolean hasPermission =
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, groupId, className, classPK, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			groupId, className, classPK, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long messageId,
			String actionId)
		throws PortalException {

		MBMessage message = MBMessageLocalServiceUtil.getMessage(messageId);

		return contains(permissionChecker, message, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		String className = message.getClassName();

		if (className.equals(WorkflowInstance.class.getName())) {
			return permissionChecker.hasPermission(
				message.getGroupId(), PortletKeys.WORKFLOW_DEFINITION,
				message.getGroupId(), ActionKeys.VIEW);
		}

		if (PropsValues.DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER &&
			(permissionChecker.getUserId() == message.getUserId())) {

			return true;
		}

		if (message.isPending()) {
			Boolean hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, message.getGroupId(),
				message.getWorkflowClassName(), message.getMessageId(),
				actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		return contains(
			permissionChecker, message.getCompanyId(), message.getGroupId(),
			className, message.getClassPK(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		MBDiscussion mbDiscussion =
			MBDiscussionLocalServiceUtil.getMBDiscussion(primaryKey);

		check(
			permissionChecker, mbDiscussion.getCompanyId(), groupId,
			mbDiscussion.getClassName(), mbDiscussion.getClassPK(), actionId);
	}

}