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

import java.util.Collections;
import java.util.List;

/**
 * Base class for {@code Comment} {@code NestedCollectionRouters}.
 *
 * @author Alejandro Hernández
 * @review
 */
public abstract class BaseCommentNestedCollectionRouter
	<T extends Identifier<Long>> implements
		NestedCollectionRouter<Comment, Long, CommentIdentifier, Long, T> {

	@Override
	public NestedCollectionRoutes<Comment, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Comment, Long, Long> builder) {

		return builder.addGetter(
			this::getPageItems, PermissionChecker.class
		).build();
	}

	protected void checkViewPermission(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK)
		throws PortalException {

		DiscussionPermission discussionPermission =
			getCommentManager().getDiscussionPermission(permissionChecker);

		discussionPermission.checkViewPermission(
			permissionChecker.getCompanyId(), groupId, className, classPK);
	}

	/**
	 * Returns the {@code CommentManager} used to retrieve comments.
	 *
	 * @return the {@code CommentManager} instance
	 * @review
	 */
	protected abstract CommentManager getCommentManager();

	/**
	 * Transforms a {@code classPK} into its {@link GroupedModel}.
	 *
	 * @param  classPK the class PK
	 * @return the grouped model
	 * @throws PortalException if getting the {@code GroupedModel} fails
	 * @review
	 */
	protected abstract GroupedModel getGroupedModel(long classPK)
		throws PortalException;

	protected PageItems<Comment> getPageItems(
			Pagination pagination, long classPK,
			PermissionChecker permissionChecker)
		throws PortalException {

		GroupedModel groupedModel = getGroupedModel(classPK);
		long resourcePrimKey = getResourcePrimKey(classPK);

		int count = getCommentManager().getRootCommentsCount(
			groupedModel.getModelClassName(), resourcePrimKey,
			WorkflowConstants.STATUS_APPROVED);

		if (count == 0) {
			return new PageItems<>(Collections.emptyList(), 0);
		}

		checkViewPermission(
			permissionChecker, groupedModel.getGroupId(),
			groupedModel.getModelClassName(), classPK);

		List<Comment> comments = getCommentManager().getRootComments(
			groupedModel.getModelClassName(), resourcePrimKey,
			WorkflowConstants.STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition());

		return new PageItems<>(comments, count);
	}

	protected long getResourcePrimKey(long classPK) throws PortalException {
		return classPK;
	}

}