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
import com.liferay.vulcan.resource.Representor.Builder;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.ScopedCollectionResource;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.sample.internal.model.BlogPostingComment;
import com.liferay.vulcan.sample.internal.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the information necessary to expose <a
 * href="http://schema.org/Comment">Comment </a> resources through a web API.
 * The resources are mapped from the internal {@link BlogPostingComment} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = CollectionResource.class)
public class BlogPostingCommentScopedCollectionResource
	implements ScopedCollectionResource<BlogPostingComment, LongIdentifier> {

	@Override
	public Representor<BlogPostingComment, LongIdentifier> buildRepresentor(
		Representor.Builder<BlogPostingComment, LongIdentifier>
			representorBuilder) {

		return representorBuilder.identifier(
			blogPostingComment -> blogPostingComment::getBlogPostingCommentId
		).addDate(
			"dateCreated", BlogPostingComment::getCreateDate
		).addDate(
			"dateModified", BlogPostingComment::getModifiedDate
		).addEmbeddedModel(
			"author", Person.class,
			blogPostingComment ->
				Person.getPerson(blogPostingComment.getAuthorId())
		).addString(
			"text", BlogPostingComment::getContent
		).addType(
			"Comment"
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public Routes<BlogPostingComment> routes(
		RoutesBuilder<BlogPostingComment, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, LongIdentifier.class
		).addCollectionPageItemCreator(
			this::_addBlogPostingComment, LongIdentifier.class
		).addCollectionPageItemGetter(
			this::_getBlogPostingComment
		).addCollectionPageItemRemover(
			this::_deleteBlogPostingComment
		).addCollectionPageItemUpdater(
			this::_updateBlogPostingComment
		).build();
	}

	private BlogPostingComment _addBlogPostingComment(
		LongIdentifier blogPostLongIdentifier, Map<String, Object> body) {

		Long authorId = (Long)body.get("author");
		String content = (String)body.get("text");

		return BlogPostingComment.addBlogPostingComment(
			authorId, blogPostLongIdentifier.getId(), content);
	}

	private void _deleteBlogPostingComment(
		LongIdentifier blogPostingCommentLongIdentifier) {

		BlogPostingComment.deleteBlogPostingComment(
			blogPostingCommentLongIdentifier.getId());
	}

	private BlogPostingComment _getBlogPostingComment(
		LongIdentifier blogPostingCommentLongIdentifier) {

		Optional<BlogPostingComment> optional =
			BlogPostingComment.getBlogPostingCommentOptional(
				blogPostingCommentLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " +
					blogPostingCommentLongIdentifier.getId()));
	}

	private PageItems<BlogPostingComment> _getPageItems(
		Pagination pagination, LongIdentifier blogPostLongIdentifier) {

		List<BlogPostingComment> blogsEntries =
			BlogPostingComment.getBlogPostingComments(
				blogPostLongIdentifier.getId(), pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = BlogPostingComment.getBlogPostingCommentsCount(
			blogPostLongIdentifier.getId());

		return new PageItems<>(blogsEntries, count);
	}

	private BlogPostingComment _updateBlogPostingComment(
		LongIdentifier blogPostingCommentLongIdentifier,
		Map<String, Object> body) {

		String content = (String)body.get("text");

		Optional<BlogPostingComment> optional =
			BlogPostingComment.updateBlogPostingComment(
				blogPostingCommentLongIdentifier.getId(), content);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " +
					blogPostingCommentLongIdentifier.getId()));
	}

}