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
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.comment.apio.identifier.CommentIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@link Comment}
 * resources through a web API.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CommentNestedCollectionResource
	implements NestedCollectionResource
		<Comment, Long, CommentIdentifier, Long, CommentIdentifier> {

	@Override
	public NestedCollectionRoutes<Comment, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Comment, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public ItemRoutes<Comment, Long> itemRoutes(
		ItemRoutes.Builder<Comment, Long> builder) {

		return builder.addGetter(
			_commentManager::fetchComment
		).build();
	}

	@Override
	public Representor<Comment> representor(
		Representor.Builder<Comment, Long> builder) {

		return builder.types(
			"Comment"
		).identifier(
			Comment::getCommentId
		).addBidirectionalModel(
			"parentItem", "comments", CommentIdentifier.class,
			this::_getParentId
		).addLinkedModel(
			"author", PersonIdentifier.class, Comment::getUserId
		).addString(
			"text", Comment::getBody
		).build();
	}

	private PageItems<Comment> _getPageItems(
		Pagination pagination, Long parentCommentId) {

		List<Comment> comments = _commentManager.getCommentsByParentComment(
			parentCommentId, WorkflowConstants.STATUS_APPROVED,
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _commentManager.getCommentsCountByParentComment(
			parentCommentId, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(comments, count);
	}

	private Long _getParentId(Comment comment) {
		if (!comment.isRoot()) {
			Comment parent = _commentManager.fetchComment(
				comment.getParentCommentId());

			if ((parent != null) && !parent.isRoot()) {
				return comment.getParentCommentId();
			}
		}

		return null;
	}

	@Reference
	private CommentManager _commentManager;

}