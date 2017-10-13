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
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.RootIdentifier;
import com.liferay.vulcan.sample.internal.model.BlogPost;
import com.liferay.vulcan.sample.internal.model.User;

import java.time.Instant;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides all the necessary information to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> resources through a web
 * API.
 *
 * The resources are mapped from the internal {@link BlogPost} model.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true)
public class BlogPostingCollectionResource
	implements CollectionResource<BlogPost, LongIdentifier> {

	@Override
	public Representor<BlogPost, LongIdentifier> buildRepresentor(
		RepresentorBuilder<BlogPost, LongIdentifier> representorBuilder) {

		return representorBuilder.identifier(
			blogPost -> blogPost::getId
		).addDate(
			"createDate", BlogPost::getCreateDate
		).addDate(
			"displayDate", BlogPost::getDisplayDate
		).addDate(
			"modifiedDate", BlogPost::getModifiedDate
		).addEmbeddedModel(
			"creator", User.class,
			blogPost -> User.getUser(blogPost.getCreatorId())
		).addString(
			"alternativeHeadline", BlogPost::getSubtitle
		).addString(
			"articleBody", BlogPost::getContent
		).addString(
			"fileFormat", blogPost -> "text/html"
		).addString(
			"headline", BlogPost::getTitle
		).addType(
			"BlogPosting"
		).build();
	}

	@Override
	public String getName() {
		return "blog-postings";
	}

	@Override
	public Routes<BlogPost> routes(
		RoutesBuilder<BlogPost, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class
		).addCollectionPageItemCreator(
			this::_addBlogPost, RootIdentifier.class
		).addCollectionPageItemGetter(
			this::_getBlogPost
		).addCollectionPageItemRemover(
			this::_deleteBlogPost
		).addCollectionPageItemUpdater(
			this::_updateBlogPost
		).build();
	}

	private BlogPost _addBlogPost(
		RootIdentifier rootIdentifier, Map<String, Object> body) {

		String title = (String)body.get("headline");
		String subtitle = (String)body.get("alternativeHeadline");
		String content = (String)body.get("articleBody");
		String displayDateString = (String)body.get("displayDate");
		Long creatorId = (Long)body.get("creator");

		Date displayDate = Date.from(Instant.parse(displayDateString));

		return BlogPost.addBlogPost(
			title, subtitle, content, displayDate, creatorId);
	}

	private void _deleteBlogPost(LongIdentifier blogPostingLongIdentifier) {
		BlogPost.deleteBlogPost(blogPostingLongIdentifier.getId());
	}

	private BlogPost _getBlogPost(LongIdentifier blogPostingLongIdentifier) {
		Optional<BlogPost> optional = BlogPost.getBlogPost(
			blogPostingLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get Blog Posting " +
					blogPostingLongIdentifier.getId()));
	}

	private PageItems<BlogPost> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier) {

		List<BlogPost> blogsEntries = BlogPost.getBlogPosts(
			pagination.getStartPosition(), pagination.getEndPosition());

		int count = BlogPost.getBlogPostsCount();

		return new PageItems<>(blogsEntries, count);
	}

	private BlogPost _updateBlogPost(
		LongIdentifier blogPostingLongIdentifier, Map<String, Object> body) {

		String title = (String)body.get("headline");
		String subtitle = (String)body.get("alternativeHeadline");
		String content = (String)body.get("articleBody");
		String displayDateString = (String)body.get("displayDate");
		Long creatorId = (Long)body.get("creator");

		Date displayDate = Date.from(Instant.parse(displayDateString));

		Optional<BlogPost> optional = BlogPost.updateBlogPost(
			blogPostingLongIdentifier.getId(), title, subtitle, content,
			displayDate, creatorId);

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get Blog Posting " +
					blogPostingLongIdentifier.getId()));
	}

}