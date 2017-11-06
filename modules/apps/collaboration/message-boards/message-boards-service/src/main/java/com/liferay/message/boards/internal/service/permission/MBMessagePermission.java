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

package com.liferay.message.boards.internal.service.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.message.boards.kernel.exception.NoSuchCategoryException;
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBCategoryLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.message.boards.kernel.model.MBMessage"
	},
	service = BaseModelPermissionChecker.class
)
public class MBMessagePermission implements BaseModelPermissionChecker {

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long messageId,
			String actionId)
		throws PortalException {

		MBMessage message = _getMBMessage(messageId);

		if (!_contains(permissionChecker, message, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBMessage.class.getName(), messageId,
				actionId);
		}
	}

	private boolean _contains(
			PermissionChecker permissionChecker, MBMessage message,
			String actionId)
		throws PortalException {

		if (_mbBanLocalService.hasBan(
				message.getGroupId(), permissionChecker.getUserId())) {

			return false;
		}

		String portletId = PortletProviderUtil.getPortletId(
			MBMessage.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = _stagingPermission.hasPermission(
			permissionChecker, message.getGroupId(), MBMessage.class.getName(),
			message.getMessageId(), portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (message.isDraft() || message.isScheduled()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!_contains(permissionChecker, message, ActionKeys.UPDATE)) {

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
					MBCategory category = _mbCategoryLocalService.getCategory(
						categoryId);

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

		if (!permissionChecker.hasPermission(
				message.getGroupId(), MBMessage.class.getName(),
				message.getMessageId(), actionId)) {

			return false;
		}

		if (message.isRoot() || !actionId.equals(ActionKeys.VIEW)) {
			return true;
		}

		return _contains(
			permissionChecker,
			_mbMessageLocalService.getMessage(message.getParentMessageId()),
			actionId);
	}

	private MBMessage _getMBMessage(long classPK) throws PortalException {
		MBThread mbThread = _mbThreadLocalService.fetchThread(classPK);

		if (mbThread == null) {
			return _mbMessageLocalService.getMessage(classPK);
		}

		return _mbMessageLocalService.getMessage(mbThread.getRootMessageId());
	}

	@Reference
	private MBBanLocalService _mbBanLocalService;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private StagingPermission _stagingPermission;

}