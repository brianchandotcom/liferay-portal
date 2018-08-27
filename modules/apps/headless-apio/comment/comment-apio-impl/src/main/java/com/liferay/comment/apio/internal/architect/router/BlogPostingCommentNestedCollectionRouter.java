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

package com.liferay.comment.apio.internal.architect.router;

import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.comment.apio.internal.architect.router.base.BaseCommentNestedCollectionRouter;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Comment">Comment</a> resources contained inside a <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> through a web API. The
 * resources are mapped from the internal model {@link Comment} and {@code
 * BlogsEntry}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class BlogPostingCommentNestedCollectionRouter
	extends BaseCommentNestedCollectionRouter<BlogPostingIdentifier>
	implements NestedCollectionRouter
		<Comment, Long, CommentIdentifier, Long, BlogPostingIdentifier> {

	@Override
	protected CommentManager getCommentManager() {
		return _commentManager;
	}

	@Override
	protected GroupedModel getGroupedModel(long blogsEntryId)
		throws PortalException {

		return _blogsEntryService.getEntry(blogsEntryId);
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

}