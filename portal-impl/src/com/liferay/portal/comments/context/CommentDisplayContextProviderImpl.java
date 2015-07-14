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
import com.liferay.portal.kernel.comments.context.CommentDisplayContextFactory;
import com.liferay.portal.kernel.comments.context.CommentSectionDisplayContext;
import com.liferay.portal.kernel.comments.context.CommentTreeDisplayContext;
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
		HttpServletRequest request, HttpServletResponse response,
		DiscussionPermission discussionPermission, Discussion discussion) {

		DiscussionRequestHelper discussionRequestHelper =
			new DiscussionRequestHelper(request);
		DiscussionTaglibHelper discussionTaglibHelper =
			new DiscussionTaglibHelper(request);

		CommentSectionDisplayContext commentSectionDisplayContext =
			new DefaultCommentSectionDisplayContext(
				discussionRequestHelper, discussionTaglibHelper,
				discussionPermission, discussion);

		for (CommentDisplayContextFactory commentDisplayContextFactory :
				getDisplayContextFactories()) {

			commentSectionDisplayContext =
				commentDisplayContextFactory.getCommentSectionDisplayContext(
					commentSectionDisplayContext, request, response,
					discussionPermission, discussion);
		}

		return commentSectionDisplayContext;
	}

	@Override
	public CommentTreeDisplayContext getCommentTreeDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DiscussionPermission discussionPermission,
		DiscussionComment discussionComment) {

		DiscussionRequestHelper discussionRequestHelper =
			new DiscussionRequestHelper(request);
		DiscussionTaglibHelper discussionTaglibHelper =
			new DiscussionTaglibHelper(request);

		CommentTreeDisplayContext commentTreeDisplayContext =
			new DefaultCommentTreeDisplayContext(
				discussionRequestHelper, discussionTaglibHelper,
				discussionPermission, discussionComment);

		for (CommentDisplayContextFactory commentDisplayContextFactory :
				getDisplayContextFactories()) {

			commentTreeDisplayContext =
				commentDisplayContextFactory.getCommentTreeDisplayContext(
					commentTreeDisplayContext, request, response,
					discussionPermission, discussionComment);
		}

		return commentTreeDisplayContext;
	}

}