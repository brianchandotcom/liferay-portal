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
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.RootIdentifier;
import com.liferay.vulcan.sample.internal.model.BlogPosting;
import com.liferay.vulcan.sample.internal.model.BlogPostingComment;
import com.liferay.vulcan.sample.internal.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	implements CollectionResource<BlogPosting, LongIdentifier> {

	@Override
	public Representor<BlogPosting, LongIdentifier> buildRepresentor(
		Representor.Builder<BlogPosting, LongIdentifier> representorBuilder) {

		return representorBuilder.identifier(
			blogPosting -> blogPosting::getBlogPostingId
		).addDate(
			"dateCreated", BlogPosting::getCreateDate
		).addDate(
			"dateModified", BlogPosting::getModifiedDate
		).addEmbeddedModel(
			"creator", Person.class,
			blogPosting -> Person.getPerson(blogPosting.getCreatorId())
		).addRelatedCollection(
			"comments", BlogPostingComment.class,
			blogPosting -> (LongIdentifier)blogPosting::getBlogPostingId
		).addString(
			"alternativeHeadline", BlogPosting::getSubtitle
		).addString(
			"articleBody", BlogPosting::getContent
		).addString(
			"fileFormat", blogPosting -> "text/html"
		).addString(
			"headline", BlogPosting::getTitle
		).addType(
			"BlogPosting"
		).build();
	}

	@Override
	public String getName() {
		return "blog-postings";
	}

	@Override
	public Routes<BlogPosting> routes(
		RoutesBuilder<BlogPosting, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class
		).addCollectionPageItemCreator(
			this::_addBlogPosting, RootIdentifier.class
		).addCollectionPageItemGetter(
			this::_getBlogPosting
		).addCollectionPageItemRemover(
			this::_deleteBlogPosting
		).addCollectionPageItemUpdater(
			this::_updateBlogPosting
		).build();
	}

	private BlogPosting _addBlogPosting(
		RootIdentifier rootIdentifier, Map<String, Object> body) {

		String content = (String)body.get("articleBody");
		Long creatorId = (Long)body.get("creator");
		String subtitle = (String)body.get("alternativeHeadline");
		String title = (String)body.get("headline");

		return BlogPosting.addBlogPosting(content, creatorId, subtitle, title);
	}

	private void _deleteBlogPosting(LongIdentifier blogPostingLongIdentifier) {
		BlogPosting.deleteBlogPosting(blogPostingLongIdentifier.getId());
	}

	private BlogPosting _getBlogPosting(
		LongIdentifier blogPostingLongIdentifier) {

		Optional<BlogPosting> optional = BlogPosting.getBlogPosting(
			blogPostingLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting " +
					blogPostingLongIdentifier.getId()));
	}

	private PageItems<BlogPosting> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier) {

		List<BlogPosting> blogPostings = BlogPosting.getBlogPostings(
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = BlogPosting.getBlogPostingCount();

		return new PageItems<>(blogPostings, count);
	}

	private BlogPosting _updateBlogPosting(
		LongIdentifier blogPostingLongIdentifier, Map<String, Object> body) {

		String content = (String)body.get("articleBody");
		Long creatorId = (Long)body.get("creator");
		String subtitle = (String)body.get("alternativeHeadline");
		String title = (String)body.get("headline");

		Optional<BlogPosting> optional = BlogPosting.updateBlogPosting(
			blogPostingLongIdentifier.getId(), content, creatorId, subtitle,
			title);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get blog posting " +
					blogPostingLongIdentifier.getId()));
	}

}