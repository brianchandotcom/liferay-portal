/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.permission;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Charles May
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = BaseModelPermissionChecker.class
)
public class MBDiscussionPermission implements BaseModelPermissionChecker {

	public static boolean contains(
		PermissionChecker permissionChecker, long companyId, long groupId,
		String className, long classPK, String actionId) {

		if (_mbBanLocalService.hasBan(groupId, permissionChecker.getUserId())) {
			return false;
		}

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			className);

		if (!resourceActions.contains(actionId)) {
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

		return contains(
			permissionChecker, _mbMessageLocalService.getMessage(messageId),
			actionId);
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
			Boolean hasPermission = _workflowPermission.hasPermission(
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

		MBDiscussion mbDiscussion = _mbDiscussionLocalService.getMBDiscussion(
			primaryKey);

		if (!contains(
				permissionChecker, mbDiscussion.getCompanyId(), groupId,
				mbDiscussion.getClassName(), mbDiscussion.getClassPK(),
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, mbDiscussion.getClassName(),
				mbDiscussion.getClassPK(), actionId);
		}
	}

	@Reference(unbind = "-")
	protected void setMBBanLocalService(MBBanLocalService mbBanLocalService) {
		_mbBanLocalService = mbBanLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowPermission(
		WorkflowPermission workflowPermission) {

		_workflowPermission = workflowPermission;
	}

	private static MBBanLocalService _mbBanLocalService;
	private static MBDiscussionLocalService _mbDiscussionLocalService;
	private static MBMessageLocalService _mbMessageLocalService;
	private static WorkflowPermission _workflowPermission;

}