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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.liferay.portal.context.CurrentUser;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.resource.ScopedCollectionResource;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.apio.architect.sample.liferay.portal.identifier.CommentableIdentifier;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Comment">Comment </a> resources through a web API.
 * The resources are mapped from the internal model {@code Comment}.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = CollectionResource.class)
public class CommentScopedCollectionResource
	implements ScopedCollectionResource<Comment, LongIdentifier> {

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public Representor<Comment, LongIdentifier> representor(
		Representor.Builder<Comment, LongIdentifier> builder) {

		return builder.types(
			"Comment"
		).identifier(
			comment -> comment::getCommentId
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addString(
			"text", Comment::getBody
		).build();
	}

	@Override
	public Routes<Comment> routes(
		Routes.Builder<Comment, LongIdentifier> builder) {

		return builder.addCollectionPageGetter(
			this::_getPageItems, CommentableIdentifier.class, CurrentUser.class
		).addCollectionPageItemCreator(
			this::_addComment, CommentableIdentifier.class, CurrentUser.class
		).addCollectionPageItemGetter(
			this::_getComment
		).addCollectionPageItemRemover(
			this::_deleteComment
		).addCollectionPageItemUpdater(
			this::_updateComment
		).build();
	}

	private Comment _addComment(
		CommentableIdentifier commentableIdentifier, Map<String, Object> body,
		CurrentUser currentUser) {

		User user = currentUser.getUser();

		String content = (String)body.get("text");

		if (Validator.isNull(content)) {
			throw new BadRequestException("Invalid body");
		}

		Function<String, ServiceContext> createServiceContextFunction =
			string -> new ServiceContext();

		Try<Long> commentIdLongTry = Try.fromFallible(
			() -> _commentManager.addComment(
				user.getUserId(), commentableIdentifier.getGroupId(),
				commentableIdentifier.getClassName(),
				commentableIdentifier.getClassPK(), content,
				createServiceContextFunction));

		return commentIdLongTry.map(
			_commentManager::fetchComment
		).getUnchecked();
	}

	private void _deleteComment(LongIdentifier commentLongIdentifier) {
		try {
			_commentManager.deleteComment(commentLongIdentifier.getId());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Comment _getComment(LongIdentifier commentLongIdentifier) {
		long commentId = commentLongIdentifier.getId();

		return _commentManager.fetchComment(commentId);
	}

	private DiscussionCommentIterator _getDiscussionCommentIterator(
		String className, long classPK, Pagination pagination,
		CurrentUser currentUser) {

		AssetEntry assetEntry = null;

		try {
			assetEntry = _assetEntryLocalService.getEntry(className, classPK);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}

		Discussion discussion = null;

		try {
			discussion = _commentManager.getDiscussion(
				currentUser.getUserId(), assetEntry.getGroupId(), className,
				classPK,
				new IdentityServiceContextFunction(new ServiceContext()));
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}

		DiscussionComment discussionComment =
			discussion.getRootDiscussionComment();

		return discussionComment.getThreadDiscussionCommentIterator(
			pagination.getStartPosition());
	}

	private PageItems<Comment> _getPageItems(
		Pagination pagination, CommentableIdentifier commentLongIdentifier,
		CurrentUser currentUser) {

		List<Comment> comments = new ArrayList<>();

		DiscussionCommentIterator discussionCommentIterator =
			_getDiscussionCommentIterator(
				commentLongIdentifier.getClassName(),
				commentLongIdentifier.getClassPK(), pagination, currentUser);

		int i = pagination.getEndPosition() - pagination.getStartPosition();

		while (discussionCommentIterator.hasNext() && (i > 0)) {
			DiscussionComment discussionComment =
				discussionCommentIterator.next();

			comments.add(discussionComment);

			i--;
		}

		int count = _commentManager.getCommentsCount(
			commentLongIdentifier.getClassName(),
			commentLongIdentifier.getClassPK());

		return new PageItems<>(comments, count);
	}

	private Optional<User> _getUserOptional(Comment comment) {
		try {
			return Optional.ofNullable(
				_userService.getUserById(comment.getUserId()));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get user " + comment.getUserId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Comment _updateComment(
		LongIdentifier commentLongIdentifier, Map<String, Object> body) {

		Comment comment = _getComment(commentLongIdentifier);

		String content = (String)body.get("text");

		if (Validator.isNull(content)) {
			throw new BadRequestException("Invalid body");
		}

		Function<String, ServiceContext> createServiceContextFunction =
			string -> new ServiceContext();

		Try<Long> commentIdLongTry = Try.fromFallible(
			() -> _commentManager.updateComment(
				comment.getUserId(), comment.getClassName(),
				comment.getClassPK(), commentLongIdentifier.getId(),
				StringPool.BLANK, content, createServiceContextFunction));

		return commentIdLongTry.map(
			_commentManager::fetchComment
		).getUnchecked();
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private UserLocalService _userService;

}