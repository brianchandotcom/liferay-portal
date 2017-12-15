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

package com.liferay.apio.architect.sample.internal.resource;

import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.router.ItemRouter;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.internal.model.BlogPosting;
import com.liferay.apio.architect.sample.internal.model.BlogPostingComment;
import com.liferay.apio.architect.sample.internal.model.Person;

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
@Component(
	immediate = true,
	service =
		{ItemRouter.class, NestedCollectionRouter.class, Representable.class}
)
public class BlogPostingCommentNestedCollectionResource implements ItemRouter
	<BlogPostingComment, LongIdentifier>, NestedCollectionRouter
	<BlogPostingComment, BlogPosting, LongIdentifier>, Representable
	<BlogPostingComment, LongIdentifier> {

	@Override
	public NestedCollectionRoutes<BlogPostingComment> collectionRoutes(
		NestedCollectionRoutes.Builder<BlogPostingComment, LongIdentifier>
			builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogPostingComment
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public ItemRoutes<BlogPostingComment> itemRoutes(
		ItemRoutes.Builder<BlogPostingComment, LongIdentifier> builder) {

		return builder.addGetter(
			this::_getBlogPostingComment
		).addRemover(
			this::_deleteBlogPostingComment
		).addUpdater(
			this::_updateBlogPostingComment
		).build();
	}

	@Override
	public Representor<BlogPostingComment, LongIdentifier> representor(
		Representor.Builder<BlogPostingComment, LongIdentifier> builder) {

		return builder.types(
			"Comment"
		).identifier(
			blogPostingComment -> blogPostingComment::getBlogPostingCommentId
		).addDate(
			"dateCreated", BlogPostingComment::getCreateDate
		).addDate(
			"dateModified", BlogPostingComment::getModifiedDate
		).addLinkedModel(
			"author", Person.class,
			blogPostingComment ->
				Person.getPerson(blogPostingComment.getAuthorId())
		).addString(
			"text", BlogPostingComment::getContent
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