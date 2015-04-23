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
import com.liferay.portal.kernel.comment.context.CommentSectionDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.messageboards.comment.context.util.DiscussionRequestHelper;
import com.liferay.portlet.messageboards.comment.context.util.DiscussionTaglibHelper;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator;

/**
 * @author Adolfo Pérez
 */
public class MBCommentSectionDisplayContext
	implements CommentSectionDisplayContext {

	public MBCommentSectionDisplayContext(
		DiscussionTaglibHelper discussionTaglibHelper,
		DiscussionRequestHelper discussionRequestHelper, Comment rootComment) {

		_discussionTaglibHelper = discussionTaglibHelper;
		_discussionRequestHelper = discussionRequestHelper;
		_rootComment = rootComment;
	}

	@Override
	public boolean isControlsVisible() {
		if (_discussionTaglibHelper.isHideControls()) {
			return false;
		}

		return MBDiscussionPermission.contains(
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getPermissionClassName(),
			_discussionTaglibHelper.getPermissionClassPK(),
			_discussionTaglibHelper.getUserId(), ActionKeys.ADD_DISCUSSION);
	}

	@Override
	public boolean isDiscussionMaxComments() throws PortalException {
		if (_discussionMaxComments == null) {
			MBMessageDisplay messageDisplay = getMBMessageDisplay();

			_discussionMaxComments = messageDisplay.isDiscussionMaxComments();
		}

		return _discussionMaxComments;
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (_rootComment == null) {
			return false;
		}

		if ((_rootComment.getThreadCommentsCount() > 1) ||
			hasViewPermission()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMessageThreadVisible() throws PortalException {
		if ((_rootComment != null) &&
			(_rootComment.getThreadCommentsCount() > 1)) {

			return true;
		}

		return false;
	}

	protected MBMessageDisplay getMBMessageDisplay() throws PortalException {
		if (_discussionMessageDisplay == null) {
			_discussionMessageDisplay =
				MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
					_discussionTaglibHelper.getUserId(),
					_discussionRequestHelper.getScopeGroupId(),
					_discussionTaglibHelper.getClassName(),
					_discussionTaglibHelper.getClassPK(),
					WorkflowConstants.STATUS_ANY,
					new MessageThreadComparator());
		}

		return _discussionMessageDisplay;
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

	private Boolean _discussionMaxComments;
	private MBMessageDisplay _discussionMessageDisplay;
	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;
	private final Comment _rootComment;

}