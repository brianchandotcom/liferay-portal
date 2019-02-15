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

package com.liferay.headless.collaboration.internal.graphql.query.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.AggregateRating;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.dto.v1_0.Creator;
import com.liferay.headless.collaboration.dto.v1_0.ImageObject;
import com.liferay.headless.collaboration.dto.v1_0.ImageObjectRepository;
import com.liferay.headless.collaboration.resource.v1_0.AggregateRatingResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.headless.collaboration.resource.v1_0.CreatorResource;
import com.liferay.headless.collaboration.resource.v1_0.ImageObjectRepositoryResource;
import com.liferay.headless.collaboration.resource.v1_0.ImageObjectResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public AggregateRating getAggregateRating( @GraphQLName("aggregate-rating-id") Long aggregateRatingId ) throws Exception {
return _getAggregateRatingResource().getAggregateRating( aggregateRatingId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting getBlogPosting( @GraphQLName("blog-posting-id") Long blogPostingId ) throws Exception {
return _getBlogPostingResource().getBlogPosting( blogPostingId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Long> getBlogPostingCategoriesPage( @GraphQLName("blog-posting-id") Long blogPostingId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getBlogPostingResource().getBlogPostingCategoriesPage(

					blogPostingId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPosting> getContentSpaceBlogPostingsPage( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getBlogPostingResource().getContentSpaceBlogPostingsPage(

					contentSpaceId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getBlogPostingCommentsPage( @GraphQLName("blog-posting-id") Long blogPostingId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getCommentResource().getBlogPostingCommentsPage(

					blogPostingId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment getComment( @GraphQLName("comment-id") Long commentId ) throws Exception {
return _getCommentResource().getComment( commentId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getCommentCommentsPage( @GraphQLName("comment-id") Long commentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getCommentResource().getCommentCommentsPage(

					commentId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Creator getCreator( @GraphQLName("creator-id") Long creatorId ) throws Exception {
return _getCreatorResource().getCreator( creatorId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ImageObject> getImageObjectRepositoryImageObjectsPage( @GraphQLName("image-object-repository-id") Long imageObjectRepositoryId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getImageObjectResource().getImageObjectRepositoryImageObjectsPage(

					imageObjectRepositoryId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ImageObject getImageObject( @GraphQLName("image-object-id") Long imageObjectId ) throws Exception {
return _getImageObjectResource().getImageObject( imageObjectId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ImageObjectRepository getImageObjectRepository( @GraphQLName("image-object-repository-id") Long imageObjectRepositoryId ) throws Exception {
return _getImageObjectRepositoryResource().getImageObjectRepository( imageObjectRepositoryId );
	}

	private static AggregateRatingResource _getAggregateRatingResource() {
			return _aggregateRatingResourceServiceTracker.getService();
	}

	private static final ServiceTracker<AggregateRatingResource, AggregateRatingResource> _aggregateRatingResourceServiceTracker;
	private static BlogPostingResource _getBlogPostingResource() {
			return _blogPostingResourceServiceTracker.getService();
	}

	private static final ServiceTracker<BlogPostingResource, BlogPostingResource> _blogPostingResourceServiceTracker;
	private static CommentResource _getCommentResource() {
			return _commentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CommentResource, CommentResource> _commentResourceServiceTracker;
	private static CreatorResource _getCreatorResource() {
			return _creatorResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CreatorResource, CreatorResource> _creatorResourceServiceTracker;
	private static ImageObjectResource _getImageObjectResource() {
			return _imageObjectResourceServiceTracker.getService();
	}

	private static final ServiceTracker<ImageObjectResource, ImageObjectResource> _imageObjectResourceServiceTracker;
	private static ImageObjectRepositoryResource _getImageObjectRepositoryResource() {
			return _imageObjectRepositoryResourceServiceTracker.getService();
	}

	private static final ServiceTracker<ImageObjectRepositoryResource, ImageObjectRepositoryResource> _imageObjectRepositoryResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

			ServiceTracker<AggregateRatingResource, AggregateRatingResource> aggregateRatingResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), AggregateRatingResource.class, null);

			aggregateRatingResourceServiceTracker.open();

			_aggregateRatingResourceServiceTracker = aggregateRatingResourceServiceTracker;
			ServiceTracker<BlogPostingResource, BlogPostingResource> blogPostingResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), BlogPostingResource.class, null);

			blogPostingResourceServiceTracker.open();

			_blogPostingResourceServiceTracker = blogPostingResourceServiceTracker;
			ServiceTracker<CommentResource, CommentResource> commentResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), CommentResource.class, null);

			commentResourceServiceTracker.open();

			_commentResourceServiceTracker = commentResourceServiceTracker;
			ServiceTracker<CreatorResource, CreatorResource> creatorResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), CreatorResource.class, null);

			creatorResourceServiceTracker.open();

			_creatorResourceServiceTracker = creatorResourceServiceTracker;
			ServiceTracker<ImageObjectResource, ImageObjectResource> imageObjectResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), ImageObjectResource.class, null);

			imageObjectResourceServiceTracker.open();

			_imageObjectResourceServiceTracker = imageObjectResourceServiceTracker;
			ServiceTracker<ImageObjectRepositoryResource, ImageObjectRepositoryResource> imageObjectRepositoryResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), ImageObjectRepositoryResource.class, null);

			imageObjectRepositoryResourceServiceTracker.open();

			_imageObjectRepositoryResourceServiceTracker = imageObjectRepositoryResourceServiceTracker;
	}

}