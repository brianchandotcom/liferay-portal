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
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;

/**
 * @author Adolfo Pérez
 */
public class MBCommentTreeDisplayContext implements CommentTreeDisplayContext {

	public MBCommentTreeDisplayContext(
		DiscussionRequestHelper discussionRequestHelper, MBMessage message) {

		_discussionRequestHelper = discussionRequestHelper;
		_message = message;
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (!_message.isApproved() && !isCommentAuthor() && !isGroupAdmin()) {
			return false;
		}

		return hasViewPermission();
	}

	protected User getUser() {
		ThemeDisplay themeDisplay = _discussionRequestHelper.getThemeDisplay();

		return themeDisplay.getUser();
	}

	protected boolean hasViewPermission() {
		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionRequestHelper.getPermissionClassName(),
			_discussionRequestHelper.getPermissionClassPK(),
			_discussionRequestHelper.getUserId(), ActionKeys.VIEW);
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
	private final MBMessage _message;

}