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

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentConstants;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.context.CommentTreeDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.comment.context.util.DiscussionRequestHelper;
import com.liferay.portlet.messageboards.comment.context.util.DiscussionTaglibHelper;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class MBCommentTreeDisplayContext implements CommentTreeDisplayContext {

	public MBCommentTreeDisplayContext(
		DiscussionTaglibHelper discussionTaglibHelper,
		DiscussionRequestHelper discussionRequestHelper, Comment comment,
		DiscussionPermission discussionPermission) {

		_discussionTaglibHelper = discussionTaglibHelper;
		_discussionRequestHelper = discussionRequestHelper;
		_comment = comment;
		_discussionPermission = discussionPermission;
	}

	@Override
	public String getPublishButtonLabel(Locale locale) {
		String publishButtonLabel = LanguageUtil.get(
			_discussionRequestHelper.getRequest(), "publish");

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_discussionRequestHelper.getCompanyId(),
				_discussionRequestHelper.getScopeGroupId(),
				CommentConstants.getDiscussionClassName())) {

			if (_comment.isPending()) {
				publishButtonLabel = "save";
			}
			else {
				publishButtonLabel = LanguageUtil.get(
					_discussionRequestHelper.getRequest(),
					"submit-for-publication");
			}
		}

		return publishButtonLabel;
	}

	@Override
	public boolean isActionControlsVisible() throws PortalException {
		if (_discussionTaglibHelper.isHideControls()) {
			return false;
		}

		return !TrashUtil.isInTrash(
			_comment.getModelClassName(), _comment.getCommentId());
	}

	@Override
	public boolean isDeleteActionControlVisible() throws PortalException {
		return _discussionPermission.hasDeletePermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_comment.getCommentId(), _comment.getUserId());
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (!_comment.isApproved() && !isCommentAuthor() && !isGroupAdmin()) {
			return false;
		}

		return hasViewPermission();
	}

	@Override
	public boolean isEditActionControlVisible() throws PortalException {
		return _discussionPermission.hasUpdatePermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_comment.getCommentId(), _comment.getUserId());
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
			_comment.getModelClassName(), _comment.getCommentId());
	}

	@Override
	public boolean isReplyActionControlVisible() throws PortalException {
		return _discussionPermission.hasAddPermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_discussionTaglibHelper.getUserId());
	}

	@Override
	public boolean isWorkflowStatusVisible() {
		if ((_comment != null) && !_comment.isApproved()) {
			return true;
		}

		return false;
	}

	protected User getUser() {
		ThemeDisplay themeDisplay = _discussionRequestHelper.getThemeDisplay();

		return themeDisplay.getUser();
	}

	protected boolean hasUpdatePermission() throws PortalException {
		return _discussionPermission.hasUpdatePermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_comment.getCommentId(), _comment.getUserId());
	}

	protected boolean hasViewPermission() throws PortalException {
		return _discussionPermission.hasViewPermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_discussionTaglibHelper.getUserId());
	}

	protected boolean isCommentAuthor() {
		User user = getUser();

		if ((_comment.getUserId() == user.getUserId()) &&
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

	private final Comment _comment;
	private final DiscussionPermission _discussionPermission;
	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;

}