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
import com.liferay.apio.architect.sample.internal.identifier.BlogPostingCommentModelId;
import com.liferay.apio.architect.sample.internal.identifier.BlogPostingModelId;
import com.liferay.apio.architect.sample.internal.identifier.PersonModelId;
import com.liferay.apio.architect.sample.internal.model.BlogPostingCommentModel;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the information necessary to expose <a
 * href="http://schema.org/Comment">Comment</a> resources through a web API. The
 * resources are mapped from the internal {@link BlogPostingCommentModel} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class BlogPostingCommentNestedCollectionResource implements
	NestedCollectionResource
		<BlogPostingCommentModel, Long, BlogPostingCommentModelId, Long,
			BlogPostingModelId> {

	@Override
	public NestedCollectionRoutes<BlogPostingCommentModel, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<BlogPostingCommentModel, Long>
				builder) {

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
	public ItemRoutes<BlogPostingCommentModel> itemRoutes(
		ItemRoutes.Builder<BlogPostingCommentModel, Long> builder) {

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
	public Representor<BlogPostingCommentModel, Long> representor(
		Representor.Builder<BlogPostingCommentModel, Long> builder) {

		return builder.types(
			"Comment"
		).identifier(
			BlogPostingCommentModel::getBlogPostingCommentId
		).addDate(
			"dateCreated", BlogPostingCommentModel::getCreateDate
		).addDate(
			"dateModified", BlogPostingCommentModel::getModifiedDate
		).addLinkedModel(
			"author", PersonModelId.class, BlogPostingCommentModel::getAuthorId
		).addString(
			"text", BlogPostingCommentModel::getContent
		).build();
	}

	private BlogPostingCommentModel _addBlogPostingComment(
		Long blogPostingId,
		BlogPostingCommentCreatorForm blogPostingCommentCreatorForm) {

		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		return BlogPostingCommentModel.addBlogPostingComment(
			blogPostingCommentCreatorForm.getAuthor(), blogPostingId,
			blogPostingCommentCreatorForm.getText());
	}

	private void _deleteBlogPostingComment(Long blogPostingCommentId) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		BlogPostingCommentModel.deleteBlogPostingComment(blogPostingCommentId);
	}

	private BlogPostingCommentModel _getBlogPostingComment(
		Long blogPostingCommentId) {

		Optional<BlogPostingCommentModel> optional =
			BlogPostingCommentModel.getBlogPostingCommentOptional(
				blogPostingCommentId);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " + blogPostingCommentId));
	}

	private PageItems<BlogPostingCommentModel> _getPageItems(
		Pagination pagination, Long blogPostingId) {

		List<BlogPostingCommentModel> blogsEntries =
			BlogPostingCommentModel.getBlogPostingComments(
				blogPostingId, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = BlogPostingCommentModel.getBlogPostingCommentsCount(
			blogPostingId);

		return new PageItems<>(blogsEntries, count);
	}

	private BlogPostingCommentModel _updateBlogPostingComment(
		Long blogPostingCommentId,
		BlogPostingCommentUpdaterForm blogPostingCommentUpdaterForm) {

		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		Optional<BlogPostingCommentModel> optional =
			BlogPostingCommentModel.updateBlogPostingComment(
				blogPostingCommentId, blogPostingCommentUpdaterForm.getText());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " + blogPostingCommentId));
	}

}