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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portlet.messageboards.model.MBMessage"
	}
)
public class MBMessagePermission
	implements BaseModelPermissionChecker, ResourcePermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long messageId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, messageId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, message, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(classPK);

		MBMessage message = null;

		if (mbThread == null) {
			message = MBMessageLocalServiceUtil.getMessage(classPK);
		}
		else {
			message = MBMessageLocalServiceUtil.getMessage(
				mbThread.getRootMessageId());
		}

		return contains(permissionChecker, message, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		if (MBBanLocalServiceUtil.hasBan(
				message.getGroupId(), permissionChecker.getUserId())) {

			return false;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, message.getGroupId(), MBMessage.class.getName(),
			message.getMessageId(), PortletKeys.MESSAGE_BOARDS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (message.isDraft() || message.isScheduled()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!contains(permissionChecker, message, ActionKeys.UPDATE)) {

				return false;
			}
		}
		else if (message.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, message.getGroupId(),
				message.getWorkflowClassName(), message.getMessageId(),
				actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long categoryId = message.getCategoryId();

			if ((categoryId ==
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
				(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

				if (!MBPermission.contains(
						permissionChecker, message.getGroupId(), actionId)) {

					return false;
				}
			}
			else {
				try {
					MBCategory category =
						MBCategoryLocalServiceUtil.getCategory(categoryId);

					if (!MBCategoryPermission.contains(
							permissionChecker, category, actionId)) {

						return false;
					}
				}
				catch (NoSuchCategoryException nsce) {
					if (!message.isInTrash()) {
						throw nsce;
					}
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(
				message.getCompanyId(), MBMessage.class.getName(),
				message.getRootMessageId(), message.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			message.getGroupId(), MBMessage.class.getName(),
			message.getMessageId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Override
	public Boolean checkResource(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return contains(permissionChecker, classPK, actionId);
	}

}