/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.comment.taglib.internal.context;

import com.liferay.comment.taglib.internal.context.helper.DiscussionRequestHelper;
import com.liferay.comment.taglib.internal.context.helper.DiscussionTaglibHelper;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.display.context.CommentSectionDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

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
		_discussion = discussion;
	}

	@Override
	public boolean isControlsVisible() throws PortalException {
		if ((_discussionPermission == null) ||
			_discussionTaglibHelper.isHideControls()) {

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
	public boolean isDiscussionVisible() throws PortalException {
		if (_discussion == null) {
			return false;
		}

		if ((_discussion.getDiscussionCommentsCount() > 0) ||
			hasViewPermission()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMessageThreadVisible() {
		if ((_discussion != null) &&
			(_discussion.getDiscussionCommentsCount() > 0)) {

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
			_discussionRequestHelper.getPermissionChecker(),
			_discussionRequestHelper.getCompanyId(),
			_discussionRequestHelper.getScopeGroupId(),
			_discussionTaglibHelper.getClassName(),
			_discussionTaglibHelper.getClassPK());
	}

	private final Discussion _discussion;
	private final DiscussionPermission _discussionPermission;
	private final DiscussionRequestHelper _discussionRequestHelper;
	private final DiscussionTaglibHelper _discussionTaglibHelper;

}