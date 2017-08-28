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

package com.liferay.vulcan.sample.liferay.portal.internal.resource;

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
import com.liferay.vulcan.liferay.portal.context.CurrentUser;
import com.liferay.vulcan.liferay.portal.filter.ClassNameClassPKFilter;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides all the necessary information to expose <a
 * href="http://schema.org/Comment">Comment</a> resources through a web API.
 *
 * The resources are mapped from the internal {@link Comment} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CommentResource implements Resource<Comment> {

	@Override
	public void buildRepresentor(
		RepresentorBuilder<Comment> representorBuilder) {

		representorBuilder.identifier(
			comment -> String.valueOf(comment.getCommentId())
		).addField(
			"text", Comment::getBody
		).addEmbeddedModel(
			"author", User.class, this::_getUserOptional
		).addType(
			"Comment"
		);
	}

	@Override
	public String getPath() {
		return "comments";
	}

	@Override
	public Routes<Comment> routes(RoutesBuilder<Comment> routesBuilder) {
		return routesBuilder.collectionItem(
			this::_getComment, Long.class
		).filteredCollectionPage(
			this::_getPageItems, ClassNameClassPKFilter.class, CurrentUser.class
		).build();
	}

	private Comment _getComment(Long id) {
		return _commentManager.fetchComment(id);
	}

	private DiscussionCommentIterator _getDiscussionCommentIterator(
		ClassNameClassPKFilter classNameClassPKFilter, Pagination pagination,
		CurrentUser currentUser) {

		AssetEntry assetEntry = null;

		try {
			assetEntry = _assetEntryLocalService.getEntry(
				classNameClassPKFilter.getClassName(),
				classNameClassPKFilter.getClassPK());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}

		Discussion discussion = null;

		try {
			discussion = _commentManager.getDiscussion(
				currentUser.getUserId(), assetEntry.getGroupId(),
				classNameClassPKFilter.getClassName(),
				classNameClassPKFilter.getClassPK(),
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
		ClassNameClassPKFilter classNameClassPKFilter, Pagination pagination,
		CurrentUser currentUser) {

		List<Comment> comments = new ArrayList<>();

		DiscussionCommentIterator discussionCommentIterator =
			_getDiscussionCommentIterator(
				classNameClassPKFilter, pagination, currentUser);

		int i = pagination.getEndPosition() - pagination.getStartPosition();

		while (discussionCommentIterator.hasNext() && (i > 0)) {
			DiscussionComment discussionComment =
				discussionCommentIterator.next();

			comments.add(discussionComment);

			i--;
		}

		int commentsCount = _commentManager.getCommentsCount(
			classNameClassPKFilter.getClassName(),
			classNameClassPKFilter.getClassPK());

		return new PageItems<>(comments, commentsCount);
	}

	private Optional<User> _getUserOptional(Comment comment) {
		long userId = comment.getUserId();

		try {
			return Optional.ofNullable(_userService.getUserById(userId));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"No User can be found with id: " + userId, e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private UserLocalService _userService;

}