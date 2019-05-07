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

package com.liferay.comment.taglib.internal.context;

import com.liferay.comment.taglib.internal.context.util.DiscussionRequestHelper;
import com.liferay.comment.taglib.internal.context.util.DiscussionTaglibHelper;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.display.context.CommentDisplayContextFactory;
import com.liferay.portal.kernel.comment.display.context.CommentSectionDisplayContext;
import com.liferay.portal.kernel.comment.display.context.CommentTreeDisplayContext;
import com.liferay.portal.kernel.display.context.BaseDisplayContextProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class CommentDisplayContextProviderImpl
	extends BaseDisplayContextProvider<CommentDisplayContextFactory>
	implements CommentDisplayContextProvider {

	public CommentDisplayContextProviderImpl() {
		super(CommentDisplayContextFactory.class);
	}

	@Override
	public CommentSectionDisplayContext getCommentSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DiscussionPermission discussionPermission, Discussion discussion) {

		DiscussionRequestHelper discussionRequestHelper =
			new DiscussionRequestHelper(httpServletRequest);
		DiscussionTaglibHelper discussionTaglibHelper =
			new DiscussionTaglibHelper(httpServletRequest);

		CommentSectionDisplayContext commentSectionDisplayContext =
			new DefaultCommentSectionDisplayContext(
				discussionRequestHelper, discussionTaglibHelper,
				discussionPermission, discussion);

		for (CommentDisplayContextFactory commentDisplayContextFactory :
				getDisplayContextFactories()) {

			commentSectionDisplayContext =
				commentDisplayContextFactory.getCommentSectionDisplayContext(
					commentSectionDisplayContext, httpServletRequest,
					httpServletResponse, discussionPermission, discussion);
		}

		return commentSectionDisplayContext;
	}

	@Override
	public CommentTreeDisplayContext getCommentTreeDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DiscussionPermission discussionPermission,
		DiscussionComment discussionComment) {

		DiscussionRequestHelper discussionRequestHelper =
			new DiscussionRequestHelper(httpServletRequest);
		DiscussionTaglibHelper discussionTaglibHelper =
			new DiscussionTaglibHelper(httpServletRequest);

		CommentTreeDisplayContext commentTreeDisplayContext =
			new DefaultCommentTreeDisplayContext(
				discussionRequestHelper, discussionTaglibHelper,
				discussionPermission, discussionComment);

		for (CommentDisplayContextFactory commentDisplayContextFactory :
				getDisplayContextFactories()) {

			commentTreeDisplayContext =
				commentDisplayContextFactory.getCommentTreeDisplayContext(
					commentTreeDisplayContext, httpServletRequest,
					httpServletResponse, discussionPermission,
					discussionComment);
		}

		return commentTreeDisplayContext;
	}

}