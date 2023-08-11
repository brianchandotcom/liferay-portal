/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.comment.internal;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.permission.MBDiscussionPermission;
import com.liferay.portal.kernel.comment.BaseDiscussionPermission;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
@Component(service = DiscussionPermission.class)
public class MBDiscussionPermissionImpl extends BaseDiscussionPermission {

	@Override
	public boolean hasAddPermission(
		PermissionChecker permissionChecker, long companyId, long groupId,
		String className, long classPK) {

		return MBDiscussionPermission.contains(
			permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.ADD_DISCUSSION);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, Comment comment,
			String actionId)
		throws PortalException {

		if (comment instanceof MBCommentImpl) {
			MBCommentImpl mbCommentImpl = (MBCommentImpl)comment;

			MBMessage mbMessage = mbCommentImpl.getMessage();

			return MBDiscussionPermission.contains(
				permissionChecker, mbMessage, actionId);
		}

		return MBDiscussionPermission.contains(
			permissionChecker, comment.getCommentId(), actionId);
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, long companyId, long groupId,
		String className, long classPK, String actionId) {

		return MBDiscussionPermission.contains(
			permissionChecker, companyId, groupId, className, classPK,
			actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long commentId,
			String actionId)
		throws PortalException {

		return MBDiscussionPermission.contains(
			permissionChecker, commentId, actionId);
	}

	@Override
	public boolean hasSubscribePermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException {

		return hasViewPermission(
			permissionChecker, companyId, groupId, className, classPK);
	}

	@Override
	public boolean hasViewPermission(
		PermissionChecker permissionChecker, long companyId, long groupId,
		String className, long classPK) {

		return MBDiscussionPermission.contains(
			permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.VIEW);
	}

}