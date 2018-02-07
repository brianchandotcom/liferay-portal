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
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.internal.form.BlogPostingForm;
import com.liferay.apio.architect.sample.internal.identifier.BlogPostingCommentModelId;
import com.liferay.apio.architect.sample.internal.identifier.BlogPostingModelId;
import com.liferay.apio.architect.sample.internal.identifier.PersonModelId;
import com.liferay.apio.architect.sample.internal.model.BlogPostingModel;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the information necessary to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> resources through a web
 * API. The resources are mapped from the internal {@link BlogPostingModel}
 * model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class BlogPostingCollectionResource
	implements CollectionResource<BlogPostingModel, Long, BlogPostingModelId> {

	@Override
	public CollectionRoutes<BlogPostingModel> collectionRoutes(
		CollectionRoutes.Builder<BlogPostingModel> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogPosting, BlogPostingForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "blog-postings";
	}

	@Override
	public ItemRoutes<BlogPostingModel> itemRoutes(
		ItemRoutes.Builder<BlogPostingModel, Long> builder) {

		return builder.addGetter(
			this::_getBlogPosting
		).addRemover(
			this::_deleteBlogPosting
		).addUpdater(
			this::_updateBlogPosting, BlogPostingForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogPostingModel, Long> representor(
		Representor.Builder<BlogPostingModel, Long> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			BlogPostingModel::getBlogPostingId
		).addDate(
			"dateCreated", BlogPostingModel::getCreateDate
		).addDate(
			"dateModified", BlogPostingModel::getModifiedDate
		).addLinkedModel(
			"creator", PersonModelId.class, BlogPostingModel::getCreatorId
		).addRelatedCollection(
			"comments", BlogPostingCommentModelId.class
		).addString(
			"alternativeHeadline", BlogPostingModel::getSubtitle
		).addString(
			"articleBody", BlogPostingModel::getContent
		).addString(
			"fileFormat", __ -> "text/html"
		).addString(
			"headline", BlogPostingModel::getTitle
		).build();
	}

	private BlogPostingModel _addBlogPosting(BlogPostingForm blogPostingForm) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		return BlogPostingModel.addBlogPosting(
			blogPostingForm.getArticleBody(), blogPostingForm.getCreator(),
			blogPostingForm.getAlternativeHeadline(),
			blogPostingForm.getHeadline());
	}

	private void _deleteBlogPosting(Long blogPostingId) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		BlogPostingModel.deleteBlogPosting(blogPostingId);
	}

	private BlogPostingModel _getBlogPosting(Long blogPostingId) {
		Optional<BlogPostingModel> optional = BlogPostingModel.getBlogPosting(
			blogPostingId);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting " + blogPostingId));
	}

	private PageItems<BlogPostingModel> _getPageItems(Pagination pagination) {
		List<BlogPostingModel> blogPostingModels =
			BlogPostingModel.getBlogPostings(
				pagination.getStartPosition(), pagination.getEndPosition());
		int count = BlogPostingModel.getBlogPostingCount();

		return new PageItems<>(blogPostingModels, count);
	}

	private BlogPostingModel _updateBlogPosting(
		Long blogPostingId, BlogPostingForm blogPostingForm) {

		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		Optional<BlogPostingModel> optional =
			BlogPostingModel.updateBlogPosting(
				blogPostingId, blogPostingForm.getArticleBody(),
				blogPostingForm.getCreator(),
				blogPostingForm.getAlternativeHeadline(),
				blogPostingForm.getHeadline());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting " + blogPostingId));
	}

}