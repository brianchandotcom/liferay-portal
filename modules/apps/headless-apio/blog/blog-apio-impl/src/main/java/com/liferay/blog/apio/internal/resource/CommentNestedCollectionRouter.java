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
package com.liferay.blog.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blog.apio.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.comment.apio.identifier.CommentIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Comment">Comment</a> resources contained inside a <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> through a web API. The
 * resources are mapped from the internal model {@link Comment} and
 * {@link com.liferay.blogs.model.BlogsEntry}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class CommentNestedCollectionRouter implements NestedCollectionRouter<Comment, CommentIdentifier, Long, BlogPostingIdentifier>{
	@Override
	public NestedCollectionRoutes<Comment, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Comment, Long> builder) {
		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<Comment> _getPageItems(
		Pagination pagination, Long blogEntryId) throws PortalException {

		BlogsEntry blogEntry = _blogsEntryLocalService.getEntry(blogEntryId);

		ClassNameClassPK classNameClassPK = ClassNameClassPK.create(blogEntry);

		List<Comment> comments = _commentManager.getRootComments(
			classNameClassPK.getClassName(), classNameClassPK.getClassPK(),
			WorkflowConstants.STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _commentManager.getRootCommentsCount(
			classNameClassPK.getClassName(), classNameClassPK.getClassPK(),
			WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(comments, count);
	}

	@Reference
	CommentManager _commentManager;

	@Reference
	BlogsEntryLocalService _blogsEntryLocalService;
}
