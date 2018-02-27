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
import com.liferay.apio.architect.liferay.portal.context.CurrentUser;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.identifier.CommentableIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.CommentForm;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
@Component(immediate = true)
public class CommentNestedCollectionResource
	implements ItemResource<Comment, Long>,
			   ReusableNestedCollectionRouter<Comment, CommentableIdentifier> {

	@Override
	public NestedCollectionRoutes<Comment> collectionRoutes(
		NestedCollectionRoutes.Builder<Comment, CommentableIdentifier>
			builder) {

		return builder.addGetter(
			this::_getPageItems, CurrentUser.class
		).addCreator(
			this::_addComment, CurrentUser.class, CommentForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public ItemRoutes<Comment> itemRoutes(
		ItemRoutes.Builder<Comment, Long> builder) {

		return builder.addGetter(
			this::_getComment
		).addRemover(
			this::_deleteComment
		).addUpdater(
			this::_updateComment, CommentForm::buildForm
		).build();
	}

	@Override
	public Representor<Comment, Long> representor(
		Representor.Builder<Comment, Long> builder) {

		return builder.types(
			"Comment"
		).identifier(
			Comment::getCommentId
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addString(
			"text", Comment::getBody
		).build();
	}

	private Comment _addComment(
		CommentableIdentifier commentableIdentifier, CommentForm commentForm,
		CurrentUser currentUser) {

		User user = currentUser.getUser();

		Function<String, ServiceContext> createServiceContextFunction =
			string -> new ServiceContext();

		Try<Long> commentIdLongTry = Try.fromFallible(
			() -> _commentManager.addComment(
				user.getUserId(), commentableIdentifier.getGroupId(),
				commentableIdentifier.getClassName(),
				commentableIdentifier.getClassPK(), commentForm.getText(),
				createServiceContextFunction));

		return commentIdLongTry.map(
			_commentManager::fetchComment
		).getUnchecked();
	}

	private void _deleteComment(Long commentId) {
		try {
			_commentManager.deleteComment(commentId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Comment _getComment(Long commentId) {
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
		Pagination pagination, CommentableIdentifier commentableIdentifier,
		CurrentUser currentUser) {

		List<Comment> comments = new ArrayList<>();

		DiscussionCommentIterator discussionCommentIterator =
			_getDiscussionCommentIterator(
				commentableIdentifier.getClassName(),
				commentableIdentifier.getClassPK(), pagination, currentUser);

		int i = pagination.getEndPosition() - pagination.getStartPosition();

		while (discussionCommentIterator.hasNext() && (i > 0)) {
			DiscussionComment discussionComment =
				discussionCommentIterator.next();

			comments.add(discussionComment);

			i--;
		}

		int count = _commentManager.getCommentsCount(
			commentableIdentifier.getClassName(),
			commentableIdentifier.getClassPK());

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

	private Comment _updateComment(Long commentId, CommentForm commentForm) {
		Comment comment = _getComment(commentId);

		Function<String, ServiceContext> createServiceContextFunction =
			string -> new ServiceContext();

		Try<Long> commentIdLongTry = Try.fromFallible(
			() -> _commentManager.updateComment(
				comment.getUserId(), comment.getClassName(),
				comment.getClassPK(), commentId, StringPool.BLANK,
				commentForm.getText(), createServiceContextFunction));

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