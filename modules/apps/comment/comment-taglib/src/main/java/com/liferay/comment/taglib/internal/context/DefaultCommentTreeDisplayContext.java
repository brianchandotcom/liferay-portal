/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.taglib.internal.context;

import com.liferay.comment.constants.CommentConstants;
import com.liferay.comment.taglib.internal.context.helper.DiscussionRequestHelper;
import com.liferay.comment.taglib.internal.context.helper.DiscussionTaglibHelper;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.comment.display.context.CommentTreeDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
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
			_discussionRequestHelper.getRequest(), "save");

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_discussionRequestHelper.getCompanyId(),
				_discussionRequestHelper.getScopeGroupId(),
				CommentConstants.getDiscussionClassName()) &&
			!_isCommentPending()) {

			publishButtonLabel = LanguageUtil.get(
				_discussionRequestHelper.getRequest(), "submit-for-workflow");
		}

		return publishButtonLabel;
	}

	@Override
	public boolean isActionControlsVisible() throws PortalException {
		if ((_discussionComment == null) ||
			_discussionTaglibHelper.isHideControls() || _isStagingGroup()) {

			return false;
		}

		return !_discussionComment.isInTrash();
	}

	@Override
	public boolean isDeleteActionControlVisible() throws PortalException {
		if ((_discussionPermission == null) || _isStagingGroup()) {
			return false;
		}

		return _discussionPermission.hasPermission(
			_discussionRequestHelper.getPermissionChecker(), _discussionComment,
			ActionKeys.DELETE_DISCUSSION);
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (!_isCommentApproved() && !_isCommentAuthor() &&
			!_isContentReviewer() && !_isGroupAdmin()) {

			return false;
		}

		return hasViewPermission();
	}

	@Override
	public boolean isEditActionControlVisible() throws PortalException {
		if (!_hasUpdatePermission() || _isStagingGroup()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isEditControlsVisible() throws PortalException {
		if (_discussionTaglibHelper.isHideControls() || _isStagingGroup()) {
			return false;
		}

		return _hasUpdatePermission();
	}

	@Override
	public boolean isRatingsVisible() throws PortalException {
		if ((_discussionComment == null) ||
			!_discussionTaglibHelper.isRatingsEnabled()) {

			return false;
		}

		return !_discussionComment.isInTrash();
	}

	@Override
	public boolean isReplyActionControlVisible() throws PortalException {
		if ((_discussionPermission == null) || _isStagingGroup()) {
			return false;
		}

		return _discussionPermission.hasAddPermission(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	@Override
	public boolean isWorkflowStatusVisible() {
		if ((_discussionComment != null) && !_isCommentApproved()) {
			return true;
		}

		return false;
	}

	@Override
	protected ThemeDisplay getThemeDisplay() {
		return _discussionRequestHelper.getThemeDisplay();
	}

	protected boolean hasViewPermission() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		return _discussionPermission.hasViewPermission(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	private User _getUser() {
		ThemeDisplay themeDisplay = _discussionRequestHelper.getThemeDisplay();

		return themeDisplay.getUser();
	}

	private boolean _hasUpdatePermission() throws PortalException {
		if (_discussionPermission == null) {
			return false;
		}

		if (_hasUpdatePermission == null) {
			_hasUpdatePermission = _discussionPermission.hasPermission(
				_discussionRequestHelper.getPermissionChecker(),
				_discussionComment, ActionKeys.UPDATE_DISCUSSION);
		}

		return _hasUpdatePermission;
	}

	private boolean _isCommentApproved() {
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

	private boolean _isCommentAuthor() {
		User user = _getUser();

		if ((_discussionComment != null) &&
			(_discussionComment.getUserId() == user.getUserId()) &&
			!user.isGuestUser()) {

			return true;
		}

		return false;
	}

	private boolean _isCommentPending() {
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

	private boolean _isContentReviewer() {
		PermissionChecker permissionChecker =
			_discussionRequestHelper.getPermissionChecker();

		return permissionChecker.isContentReviewer(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId());
	}

	private boolean _isGroupAdmin() {
		PermissionChecker permissionChecker =
			_discussionRequestHelper.getPermissionChecker();

		return permissionChecker.isGroupAdmin(
			_discussionRequestHelper.getScopeGroupId());
	}

	private boolean _isStagingGroup() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		Group siteGroup = themeDisplay.getSiteGroup();

		return siteGroup.isStagingGroup();
	}

	private final DiscussionComment _discussionComment;
	private final DiscussionPermission _discussionPermission;
	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;
	private Boolean _hasUpdatePermission;

}