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

package com.liferay.comment.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blog.apio.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.comment.apio.identifier.CommentIdentifier;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Comment">Comment</a> resources contained inside a <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> through a web API. The
 * resources are mapped from the internal model {@link Comment} and
 * {@code BlogsEntry}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class BlogPostingCommentNestedCollectionRouter implements
	NestedCollectionRouter
		<Comment, CommentIdentifier, Long, BlogPostingIdentifier> {

	@Override
	public NestedCollectionRoutes<Comment, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Comment, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, PermissionChecker.class
		).build();
	}

	private void _checkViewPermission(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(permissionChecker);

		discussionPermission.checkViewPermission(
			permissionChecker.getCompanyId(), groupId, className, classPK);
	}

	private PageItems<Comment> _getPageItems(
			Pagination pagination, Long blogsEntryId,
			PermissionChecker permissionChecker)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryLocalService.getBlogsEntry(
			blogsEntryId);

		_checkViewPermission(
			permissionChecker, blogsEntry.getGroupId(),
			BlogsEntry.class.getName(), blogsEntryId);

		List<Comment> comments = _commentManager.getRootComments(
			BlogsEntry.class.getName(), blogsEntryId,
			WorkflowConstants.STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _commentManager.getRootCommentsCount(
			BlogsEntry.class.getName(), blogsEntryId,
			WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(comments, count);
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private CommentManager _commentManager;

}