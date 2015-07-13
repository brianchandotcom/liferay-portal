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

package com.liferay.portal.comments.context;

import com.liferay.portal.comments.context.util.DiscussionRequestHelper;
import com.liferay.portal.comments.context.util.DiscussionTaglibHelper;
import com.liferay.portal.kernel.comments.Discussion;
import com.liferay.portal.kernel.comments.DiscussionComment;
import com.liferay.portal.kernel.comments.DiscussionPermission;
import com.liferay.portal.kernel.comments.context.CommentSectionDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author Adolfo Pérez
 */
public class DefaultCommentSectionDisplayContext
	extends BaseCommentDisplayContext implements CommentSectionDisplayContext {

	public DefaultCommentSectionDisplayContext(
		DiscussionRequestHelper discussionRequestHelper,
		DiscussionTaglibHelper discussionTaglibHelper,
		DiscussionPermission discussionPermission, Discussion discussion) {

		_discussionRequestHelper = discussionRequestHelper;
		_discussionTaglibHelper = discussionTaglibHelper;
		_discussionPermission = discussionPermission;
		_rootDiscussionComment = discussion.getRootDiscussionComment();
	}

	@Override
	public boolean isControlsVisible() throws PortalException {
		if (_discussionTaglibHelper.isHideControls()) {
			return false;
		}

		return _discussionPermission.hasAddPermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	@Override
	public boolean isDiscussionVisible() throws PortalException {
		if (_rootDiscussionComment == null) {
			return false;
		}

		if ((_rootDiscussionComment.getThreadCommentsCount() > 1) ||
			hasViewPermission()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMessageThreadVisible() {
		if ((_rootDiscussionComment != null) &&
			(_rootDiscussionComment.getThreadCommentsCount() > 1)) {

			return true;
		}

		return false;
	}

	@Override
	protected ThemeDisplay getThemeDisplay() {
		return _discussionRequestHelper.getThemeDisplay();
	}

	protected boolean hasViewPermission() throws PortalException {
		return _discussionPermission.hasViewPermission(
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	private final DiscussionPermission _discussionPermission;
	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;
	private final DiscussionComment _rootDiscussionComment;

}