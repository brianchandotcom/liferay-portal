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

package com.liferay.portal.comment.display.context;

import com.liferay.portal.comment.display.context.util.DiscussionRequestHelper;
import com.liferay.portal.comment.display.context.util.DiscussionTaglibHelper;
import com.liferay.portal.kernel.comment.CommentConstants;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.comment.display.context.CommentTreeDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class DefaultCommentTreeDisplayContext
	extends BaseCommentDisplayContext implements CommentTreeDisplayContext {

	public DefaultCommentTreeDisplayContext(
		DiscussionRequestHelper discussionRequestHelper,
		DiscussionTaglibHelper discussionTaglibHelper,
		DiscussionPermission discussionPermission,
		DiscussionComment discussionComment) {

		_discussionRequestHelper = discussionRequestHelper;
		_discussionTaglibHelper = discussionTaglibHelper;
		_discussionPermission = discussionPermission;
		_discussionComment = discussionComment;
	}

	@Override
	public String getPublishButtonLabel(Locale locale) {
		String publishButtonLabel = LanguageUtil.get(
			_discussionRequestHelper.getRequest(), "publish");

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_discussionRequestHelper.getCompanyId(),
				_discussionRequestHelper.getScopeGroupId(),
				CommentConstants.getDiscussionClassName())) {

			if (isCommentPending()) {
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
		if ((_discussionComment == null) ||
			_discussionTaglibHelper.isHideControls()) {

			return false;
		}

		return !_discussionComment.isInTrash();
	}

	@Override
	public boolean isDeleteActionControlVisible() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		return _discussionPermission.hasDeletePermission(
			_discussionComment.getCommentId());
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (!isCommentApproved() && !isCommentAuthor() && !isGroupAdmin()) {
			return false;
		}

		return hasViewPermission();
	}

	@Override
	public boolean isEditActionControlVisible() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		return _discussionPermission.hasUpdatePermission(
			_discussionComment.getCommentId());
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
		if ((_discussionComment == null) ||
			!_discussionTaglibHelper.isRatingsEnabled()) {

			return false;
		}

		return _discussionComment.isInTrash();
	}

	@Override
	public boolean isReplyActionControlVisible() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		return _discussionPermission.hasAddPermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	@Override
	public boolean isWorkflowStatusVisible() {
		if ((_discussionComment != null) && !isCommentApproved()) {
			return true;
		}

		return false;
	}

	@Override
	protected ThemeDisplay getThemeDisplay() {
		return _discussionRequestHelper.getThemeDisplay();
	}

	protected User getUser() {
		ThemeDisplay themeDisplay = _discussionRequestHelper.getThemeDisplay();

		return themeDisplay.getUser();
	}

	protected boolean hasUpdatePermission() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		return _discussionPermission.hasUpdatePermission(
			_discussionComment.getCommentId());
	}

	protected boolean hasViewPermission() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		return _discussionPermission.hasViewPermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	protected boolean isCommentApproved() {
		boolean approved = true;

		if (_discussionComment instanceof WorkflowableComment) {
			WorkflowableComment workflowableComment =
				(WorkflowableComment)_discussionComment;

			if (workflowableComment.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

				approved = true;
			}
			else {
				approved = false;
			}
		}

		return approved;
	}

	protected boolean isCommentAuthor() {
		User user = getUser();

		if ((_discussionComment != null) &&
			(_discussionComment.getUserId() == user.getUserId()) &&
			!user.isDefaultUser()) {

			return true;
		}

		return false;
	}

	protected boolean isCommentPending() {
		boolean pending = false;

		if (_discussionComment instanceof WorkflowableComment) {
			WorkflowableComment workflowableComment =
				(WorkflowableComment)_discussionComment;

			if (workflowableComment.getStatus() ==
					WorkflowConstants.STATUS_PENDING) {

				pending = true;
			}
			else {
				pending = false;
			}
		}

		return pending;
	}

	protected boolean isGroupAdmin() {
		PermissionChecker permissionChecker =
			_discussionRequestHelper.getPermissionChecker();

		return permissionChecker.isGroupAdmin(
			_discussionRequestHelper.getScopeGroupId());
	}

	private final DiscussionComment _discussionComment;
	private final DiscussionPermission _discussionPermission;
	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;

}