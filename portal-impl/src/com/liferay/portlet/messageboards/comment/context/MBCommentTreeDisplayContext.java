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

package com.liferay.portlet.messageboards.comment.context;

import com.liferay.portal.kernel.comment.context.CommentTreeDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.comment.context.util.DiscussionRequestHelper;
import com.liferay.portlet.messageboards.comment.context.util.DiscussionTaglibHelper;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Adolfo Pérez
 */
public class MBCommentTreeDisplayContext implements CommentTreeDisplayContext {

	public MBCommentTreeDisplayContext(
		DiscussionTaglibHelper discussionTaglibHelper,
		DiscussionRequestHelper discussionRequestHelper, MBMessage message) {

		_discussionTaglibHelper = discussionTaglibHelper;
		_discussionRequestHelper = discussionRequestHelper;
		_message = message;
	}

	@Override
	public boolean isActionControlsVisible() throws PortalException {
		if (_discussionTaglibHelper.isHideControls()) {
			return false;
		}

		return !TrashUtil.isInTrash(
			_message.getClassName(), _message.getClassPK());
	}

	@Override
	public boolean isDeleteActionControlVisible() throws PortalException {
		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_message.getMessageId(), _message.getUserId(),
			ActionKeys.DELETE_DISCUSSION);
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (!_message.isApproved() && !isCommentAuthor() && !isGroupAdmin()) {
			return false;
		}

		return hasViewPermission();
	}

	@Override
	public boolean isEditActionControlVisible() throws PortalException {
		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_message.getMessageId(), _message.getUserId(),
			ActionKeys.UPDATE_DISCUSSION);
	}

	@Override
	public boolean isEditControlsVisible() throws PortalException {
		if (_discussionTaglibHelper.isHideControls()) {
			return false;
		}

		return hasUpdatePermission();
	}

	@Override
	public boolean isRatingsVisible() throws PortalException {
		if (!_discussionTaglibHelper.isRatingsEnabled()) {
			return false;
		}

		return !TrashUtil.isInTrash(
			_message.getClassName(), _message.getClassPK());
	}

	@Override
	public boolean isReplyActionControlVisible() throws PortalException {
		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_discussionTaglibHelper.getUserId(), ActionKeys.ADD_DISCUSSION);
	}

	@Override
	public boolean isWorkflowStatusVisible() throws PortalException {
		return (_message != null) && !_message.isApproved();
	}

	protected User getUser() {
		ThemeDisplay themeDisplay = _discussionRequestHelper.getThemeDisplay();

		return themeDisplay.getUser();
	}

	protected boolean hasUpdatePermission() throws PortalException {
		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_message.getMessageId(), _message.getUserId(),
			ActionKeys.UPDATE_DISCUSSION);
	}

	protected boolean hasViewPermission() {
		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_discussionTaglibHelper.getUserId(), ActionKeys.VIEW);
	}

	protected boolean isCommentAuthor() {
		User user = getUser();

		if ((_message.getUserId() == user.getUserId()) &&
			!user.isDefaultUser()) {

			return true;
		}

		return false;
	}

	protected boolean isGroupAdmin() {
		PermissionChecker permissionChecker =
			_discussionRequestHelper.getPermissionChecker();

		return permissionChecker.isGroupAdmin(
			_discussionRequestHelper.getScopeGroupId());
	}

	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;
	private final MBMessage _message;

}