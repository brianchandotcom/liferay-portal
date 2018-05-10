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

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.internal.form.BlogPostingCommentCreatorForm;
import com.liferay.apio.architect.sample.internal.form.BlogPostingCommentUpdaterForm;
import com.liferay.apio.architect.sample.internal.identifier.BlogPostingCommentIdentifier;
import com.liferay.apio.architect.sample.internal.identifier.BlogPostingIdentifier;
import com.liferay.apio.architect.sample.internal.identifier.PersonIdentifier;
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
		<BlogPostingCommentModel, Long, BlogPostingCommentIdentifier, Long,
			BlogPostingIdentifier> {

	@Override
	public NestedCollectionRoutes<BlogPostingCommentModel, Long>
		collectionRoutes(
			NestedCollectionRoutes.Builder<BlogPostingCommentModel, Long>
				builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogPostingComment, Credentials.class,
			(credentials, blogPostingModelId) -> hasPermission(credentials),
			BlogPostingCommentCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "comments";
	}

	@Override
	public ItemRoutes<BlogPostingCommentModel, Long> itemRoutes(
		ItemRoutes.Builder<BlogPostingCommentModel, Long> builder) {

		return builder.addGetter(
			this::_getBlogPostingComment
		).addRemover(
			this::_deleteBlogPostingComment, Credentials.class,
			(credentials, id) -> hasPermission(credentials)
		).addUpdater(
			this::_updateBlogPostingComment, Credentials.class,
			(credentials, id) -> hasPermission(credentials),
			BlogPostingCommentUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogPostingCommentModel> representor(
		Representor.Builder<BlogPostingCommentModel, Long> builder) {

		return builder.types(
			"Comment"
		).identifier(
			BlogPostingCommentModel::getId
		).addDate(
			"dateCreated", BlogPostingCommentModel::getCreateDate
		).addDate(
			"dateModified", BlogPostingCommentModel::getModifiedDate
		).addLinkedModel(
			"author", PersonIdentifier.class,
			BlogPostingCommentModel::getAuthorId
		).addString(
			"text", BlogPostingCommentModel::getContent
		).build();
	}

	private BlogPostingCommentModel _addBlogPostingComment(
		Long blogPostingModelId,
		BlogPostingCommentCreatorForm blogPostingCommentCreatorForm,
		Credentials credentials) {

		if (!hasPermission(credentials)) {
			throw new ForbiddenException();
		}

		return BlogPostingCommentModel.create(
			blogPostingCommentCreatorForm.getAuthor(), blogPostingModelId,
			blogPostingCommentCreatorForm.getText());
	}

	private void _deleteBlogPostingComment(Long id, Credentials credentials) {
		if (!hasPermission(credentials)) {
			throw new ForbiddenException();
		}

		BlogPostingCommentModel.remove(id);
	}

	private BlogPostingCommentModel _getBlogPostingComment(Long id) {
		Optional<BlogPostingCommentModel> optional =
			BlogPostingCommentModel.get(id);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " + id));
	}

	private PageItems<BlogPostingCommentModel> _getPageItems(
		Pagination pagination, Long blogPostingModelId) {

		List<BlogPostingCommentModel> blogPostingCommentModels =
			BlogPostingCommentModel.getPage(
				blogPostingModelId, pagination.getStartPosition(),
				pagination.getEndPosition());
		int count = BlogPostingCommentModel.getCount(blogPostingModelId);

		return new PageItems<>(blogPostingCommentModels, count);
	}

	private BlogPostingCommentModel _updateBlogPostingComment(
		Long id, BlogPostingCommentUpdaterForm blogPostingCommentUpdaterForm,
		Credentials credentials) {

		if (!hasPermission(credentials)) {
			throw new ForbiddenException();
		}

		Optional<BlogPostingCommentModel> optional =
			BlogPostingCommentModel.update(
				id, blogPostingCommentUpdaterForm.getText());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting comment " + id));
	}

}