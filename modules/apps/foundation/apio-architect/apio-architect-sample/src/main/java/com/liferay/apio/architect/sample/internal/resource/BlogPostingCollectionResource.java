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
 * href="http://schema.org/BlogPosting">BlogPosting </a> resources through a web
 * API. The resources are mapped from the internal {@link BlogPosting} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class BlogPostingCollectionResource
	implements CollectionResource<BlogPosting, Long> {

	@Override
	public CollectionRoutes<BlogPosting> collectionRoutes(
		CollectionRoutes.Builder<BlogPosting> builder) {

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
	public ItemRoutes<BlogPosting> itemRoutes(
		ItemRoutes.Builder<BlogPosting, Long> builder) {

		return builder.addGetter(
			this::_getBlogPosting
		).addRemover(
			this::_deleteBlogPosting
		).addUpdater(
			this::_updateBlogPosting, BlogPostingForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogPosting, Long> representor(
		Representor.Builder<BlogPosting, Long> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			BlogPosting::getBlogPostingId
		).addDate(
			"dateCreated", BlogPosting::getCreateDate
		).addDate(
			"dateModified", BlogPosting::getModifiedDate
		).addLinkedModel(
			"creator", Person.class,
			blogPosting -> Person.getPerson(blogPosting.getCreatorId())
		).addRelatedCollection(
			"comments", BlogPostingComment.class, BlogPosting::getBlogPostingId
		).addString(
			"alternativeHeadline", BlogPosting::getSubtitle
		).addString(
			"articleBody", BlogPosting::getContent
		).addString(
			"fileFormat", blogPosting -> "text/html"
		).addString(
			"headline", BlogPosting::getTitle
		).build();
	}

	private BlogPosting _addBlogPosting(BlogPostingForm blogPostingForm) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		return BlogPosting.addBlogPosting(
			blogPostingForm.getArticleBody(), blogPostingForm.getCreator(),
			blogPostingForm.getAlternativeHeadline(),
			blogPostingForm.getHeadline());
	}

	private void _deleteBlogPosting(Long blogPostingId) {
		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		BlogPosting.deleteBlogPosting(blogPostingId);
	}

	private BlogPosting _getBlogPosting(Long blogPostingId) {
		Optional<BlogPosting> optional = BlogPosting.getBlogPosting(
			blogPostingId);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting " + blogPostingId));
	}

	private PageItems<BlogPosting> _getPageItems(Pagination pagination) {
		List<BlogPosting> blogPostings = BlogPosting.getBlogPostings(
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = BlogPosting.getBlogPostingCount();

		return new PageItems<>(blogPostings, count);
	}

	private BlogPosting _updateBlogPosting(
		Long blogPostingId, BlogPostingForm blogPostingForm) {

		if (!hasPermission()) {
			throw new ForbiddenException();
		}

		Optional<BlogPosting> optional = BlogPosting.updateBlogPosting(
			blogPostingId, blogPostingForm.getArticleBody(),
			blogPostingForm.getCreator(),
			blogPostingForm.getAlternativeHeadline(),
			blogPostingForm.getHeadline());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting " + blogPostingId));
	}

}