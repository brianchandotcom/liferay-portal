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

package com.liferay.vulcan.sample.internal.resource;

import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.ScopedCollectionResource;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.sample.internal.model.BlogPostComment;
import com.liferay.vulcan.sample.internal.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the necessary information to expose <a
 * href="http://schema.org/Comment">Comment</a> resources through a web API.
 *
 * The resources are mapped from the internal {@link BlogPostComment} model.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true, service = CollectionResource.class)
public class BlogPostCommentScopedCollectionResource
	implements ScopedCollectionResource<BlogPostComment, LongIdentifier> {

	@Override
	public Representor<BlogPostComment, LongIdentifier> buildRepresentor(
		RepresentorBuilder<BlogPostComment, LongIdentifier>
			representorBuilder) {

		return representorBuilder.identifier(
			blogPostComment -> blogPostComment::getId
		).addDate(
			"dateCreated", BlogPostComment::getCreateDate
		).addDate(
			"dateModified", BlogPostComment::getModifiedDate
		).addEmbeddedModel(
			"author", User.class,
			blogPostComment -> User.getUser(blogPostComment.getAuthorId())
		).addString(
			"text", BlogPostComment::getContent
		).addType(
			"Comment"
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public Routes<BlogPostComment> routes(
		RoutesBuilder<BlogPostComment, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, LongIdentifier.class
		).addCollectionPageItemCreator(
			this::_addBlogPostComment, LongIdentifier.class
		).addCollectionPageItemGetter(
			this::_getBlogPostComment
		).addCollectionPageItemRemover(
			this::_deleteBlogPostComment
		).addCollectionPageItemUpdater(
			this::_updateBlogPostComment
		).build();
	}

	private BlogPostComment _addBlogPostComment(
		LongIdentifier blogPostLongIdentifier, Map<String, Object> body) {

		String content = (String)body.get("text");
		Long authorId = (Long)body.get("author");

		return BlogPostComment.addBlogPostComment(
			blogPostLongIdentifier.getId(), authorId, content);
	}

	private void _deleteBlogPostComment(
		LongIdentifier blogPostCommentLongIdentifier) {

		BlogPostComment.deleteBlogPostComment(
			blogPostCommentLongIdentifier.getId());
	}

	private BlogPostComment _getBlogPostComment(
		LongIdentifier blogPostCommentLongIdentifier) {

		Optional<BlogPostComment> optional = BlogPostComment.getBlogPostComment(
			blogPostCommentLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get Blog Posting Comment " +
					blogPostCommentLongIdentifier.getId()));
	}

	private PageItems<BlogPostComment> _getPageItems(
		Pagination pagination, LongIdentifier blogPostLongIdentifier) {

		List<BlogPostComment> blogsEntries =
			BlogPostComment.getBlogPostComments(
				blogPostLongIdentifier.getId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int count = BlogPostComment.getBlogPostCommentsCount(
			blogPostLongIdentifier.getId());

		return new PageItems<>(blogsEntries, count);
	}

	private BlogPostComment _updateBlogPostComment(
		LongIdentifier blogPostCommentLongIdentifier,
		Map<String, Object> body) {

		String content = (String)body.get("text");
		Long authorId = (Long)body.get("author");

		Optional<BlogPostComment> optional =
			BlogPostComment.updateBlogPostComment(
				blogPostCommentLongIdentifier.getId(), authorId, content);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get Blog Posting Comment " +
					blogPostCommentLongIdentifier.getId()));
	}

}