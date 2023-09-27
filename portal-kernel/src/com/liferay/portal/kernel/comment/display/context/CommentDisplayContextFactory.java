/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment.display.context;

import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.display.context.DisplayContextFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public interface CommentDisplayContextFactory extends DisplayContextFactory {

	public CommentSectionDisplayContext getCommentSectionDisplayContext(
		CommentSectionDisplayContext parentCommentSectionDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DiscussionPermission discussionPermission, Discussion discussion);

	public CommentTreeDisplayContext getCommentTreeDisplayContext(
		CommentTreeDisplayContext parentCommentTreeDisplayContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DiscussionPermission discussionPermission,
		DiscussionComment discussionComment);

}