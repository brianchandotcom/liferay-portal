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

import static com.liferay.apio.architect.sample.internal.auth.PermissionChecker.hasPermission;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.internal.form.BlogPostingCommentCreatorForm;
import com.liferay.apio.architect.sample.internal.form.BlogPostingCommentUpdaterForm;
import com.liferay.apio.architect.sample.internal.model.BlogPosting;
import com.liferay.apio.architect.sample.internal.model.BlogPostingComment;
import com.liferay.apio.architect.sample.internal.model.Person;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the information necessary to expose <a
 * href="http://schema.org/Comment">Comment </a> resources through a web API.
 * The resources are mapped from the internal {@link BlogPostingComment} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class BlogPostingCommentNestedCollectionResource implements
	NestedCollectionResource<BlogPostingComment, Long, BlogPosting, Long> {

	@Override
	public NestedCollectionRoutes<BlogPostingComment> collectionRoutes(
		NestedCollectionRoutes.Builder<BlogPostingComment, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogPostingComment,
			BlogPostingCommentCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public ItemRoutes<BlogPostingComment> itemRoutes(
		ItemRoutes.Builder<BlogPostingComment, Long> builder) {

		return builder.addGetter(
			this::_getBlogPostingComment
		).addRemover(
			this::_deleteBlogPostingComment
		).addUpdater(
			this::_updateBlogPostingComment,
			BlogPostingCommentUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogPostingComment, Long> representor(
		Representor.Builder<BlogPostingComment, Long> builder) {

		return builder.types(
			"Comment"
		).identifier(
			BlogPostingComment::getBlogPostingCommentId
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
		Long blogPostId,
		BlogPostingCommentCreatorForm blogPostingCommentCreatorForm) {

		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		return BlogPostingComment.addBlogPostingComment(
			blogPostingCommentCreatorForm.getAuthor(), blogPostId,
			blogPostingCommentCreatorForm.getText());
	}

	private void _deleteBlogPostingComment(Long blogPostingCommentId) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		BlogPostingComment.deleteBlogPostingComment(blogPostingCommentId);
	}

	private BlogPostingComment _getBlogPostingComment(
		Long blogPostingCommentId) {

		Optional<BlogPostingComment> optional =
			BlogPostingComment.getBlogPostingCommentOptional(
				blogPostingCommentId);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " + blogPostingCommentId));
	}

	private PageItems<BlogPostingComment> _getPageItems(
		Pagination pagination, Long blogPostId) {

		List<BlogPostingComment> blogsEntries =
			BlogPostingComment.getBlogPostingComments(
				blogPostId, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = BlogPostingComment.getBlogPostingCommentsCount(blogPostId);

		return new PageItems<>(blogsEntries, count);
	}

	private BlogPostingComment _updateBlogPostingComment(
		Long blogPostingCommentId,
		BlogPostingCommentUpdaterForm blogPostingCommentUpdaterForm) {

		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		Optional<BlogPostingComment> optional =
			BlogPostingComment.updateBlogPostingComment(
				blogPostingCommentId, blogPostingCommentUpdaterForm.getText());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " + blogPostingCommentId));
	}

}