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

package com.liferay.message.boards.comment.internal;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.portal.kernel.comment.BaseDiscussionPermission;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class MBDiscussionPermissionImpl extends BaseDiscussionPermission {

	public MBDiscussionPermissionImpl(
		PermissionChecker permissionChecker,
		MBBanLocalService mbBanLocalService,
		MBMessageLocalService mbMessageLocalService) {

		_permissionChecker = permissionChecker;
		_mbBanLocalService = mbBanLocalService;
		_mbMessageLocalService = mbMessageLocalService;
	}

	@Override
	public boolean hasAddPermission(
		long companyId, long groupId, String className, long classPK) {

		if (_mbBanLocalService.hasBan(
				groupId, _permissionChecker.getUserId())) {

			return false;
		}

		return MBDiscussionPermission.contains(
			_permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.ADD_DISCUSSION);
	}

	@Override
	public boolean hasPermission(Comment comment, String actionId)
		throws PortalException {

		if (_mbBanLocalService.hasBan(
				comment.getGroupId(), _permissionChecker.getUserId())) {

			return false;
		}

		if (comment instanceof MBCommentImpl) {
			MBCommentImpl mbCommentImpl = (MBCommentImpl)comment;

			MBMessage mbMessage = mbCommentImpl.getMessage();

			return MBDiscussionPermission.contains(
				_permissionChecker, mbMessage, actionId);
		}

		return MBDiscussionPermission.contains(
			_permissionChecker, comment.getCommentId(), actionId);
	}

	@Override
	public boolean hasPermission(long commentId, String actionId)
		throws PortalException {

		MBMessage message = _mbMessageLocalService.getMessage(commentId);

		if (_mbBanLocalService.hasBan(
				message.getGroupId(), _permissionChecker.getUserId())) {

			return false;
		}

		return MBDiscussionPermission.contains(
			_permissionChecker, commentId, actionId);
	}

	@Override
	public boolean hasSubscribePermission(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		if (_mbBanLocalService.hasBan(
				groupId, _permissionChecker.getUserId())) {

			return false;
		}

		return MBDiscussionPermission.contains(
			_permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.SUBSCRIBE);
	}

	@Override
	public boolean hasViewPermission(
		long companyId, long groupId, String className, long classPK) {

		if (_mbBanLocalService.hasBan(
				groupId, _permissionChecker.getUserId())) {

			return false;
		}

		return MBDiscussionPermission.contains(
			_permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.VIEW);
	}

	private final MBBanLocalService _mbBanLocalService;
	private final MBMessageLocalService _mbMessageLocalService;
	private final PermissionChecker _permissionChecker;

}