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

package com.liferay.comment.apio.internal.architect.router.base;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * Base class for {@code Comment} {@code NestedCollectionRouters}.
 *
 * @author Alejandro Hernández
 * @review
 */
public abstract class BaseCommentNestedCollectionRouter
	<T extends Identifier<Long>> implements
		NestedCollectionRouter<Comment, CommentIdentifier, Long, T> {

	@Override
	public NestedCollectionRoutes<Comment, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Comment, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, PermissionChecker.class
		).build();
	}

	protected abstract CommentManager getCommentManager();

	protected abstract GroupedModel getGroupedModel(long classPK)
		throws PortalException;

	private void _checkViewPermission(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			getCommentManager().getDiscussionPermission(permissionChecker);

		discussionPermission.checkViewPermission(
			permissionChecker.getCompanyId(), groupId, className, classPK);
	}

	private PageItems<Comment> _getPageItems(
			Pagination pagination, Long classPK,
			PermissionChecker permissionChecker)
		throws PortalException {

		GroupedModel groupedModel = getGroupedModel(classPK);

		_checkViewPermission(
			permissionChecker, groupedModel.getGroupId(),
			groupedModel.getModelClassName(), classPK);

		List<Comment> comments = getCommentManager().getRootComments(
			groupedModel.getModelClassName(), classPK,
			WorkflowConstants.STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = getCommentManager().getRootCommentsCount(
			groupedModel.getModelClassName(), classPK,
			WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(comments, count);
	}

}