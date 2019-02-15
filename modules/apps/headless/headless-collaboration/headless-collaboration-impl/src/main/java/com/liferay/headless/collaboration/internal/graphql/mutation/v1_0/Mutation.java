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

package com.liferay.headless.collaboration.internal.graphql.mutation.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.ImageObject;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.ImageObjectResource;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLInvokeDetached
	public Response deleteBlogPosting( @GraphQLName("blog-posting-id") Long blogPostingId ) throws Exception {
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();

	}

	@GraphQLInvokeDetached
	public BlogPosting putBlogPosting( @GraphQLName("blog-posting-id") Long blogPostingId , @GraphQLName("BlogPosting") BlogPosting blogPosting ) throws Exception {
return _getBlogPostingResource().putBlogPosting( blogPostingId , blogPosting );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Response postBlogPostingCategories( @GraphQLName("blog-posting-id") Long blogPostingId , @GraphQLName("Long") Long referenceId ) throws Exception {
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Response postBlogPostingCategoriesBatchCreate( @GraphQLName("blog-posting-id") Long blogPostingId , @GraphQLName("Long") Long referenceId ) throws Exception {
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting postContentSpaceBlogPosting( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("BlogPosting") BlogPosting blogPosting ) throws Exception {
return _getBlogPostingResource().postContentSpaceBlogPosting( contentSpaceId , blogPosting );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting postContentSpaceBlogPostingBatchCreate( @GraphQLName("content-space-id") Long contentSpaceId , @GraphQLName("BlogPosting") BlogPosting blogPosting ) throws Exception {
return _getBlogPostingResource().postContentSpaceBlogPostingBatchCreate( contentSpaceId , blogPosting );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ImageObject postImageObjectRepositoryImageObject( @GraphQLName("image-object-repository-id") Long imageObjectRepositoryId , @GraphQLName("ImageObject") ImageObject imageObject ) throws Exception {
return _getImageObjectResource().postImageObjectRepositoryImageObject( imageObjectRepositoryId , imageObject );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ImageObject postImageObjectRepositoryImageObjectBatchCreate( @GraphQLName("image-object-repository-id") Long imageObjectRepositoryId , @GraphQLName("ImageObject") ImageObject imageObject ) throws Exception {
return _getImageObjectResource().postImageObjectRepositoryImageObjectBatchCreate( imageObjectRepositoryId , imageObject );
	}

	@GraphQLInvokeDetached
	public Response deleteImageObject( @GraphQLName("image-object-id") Long imageObjectId ) throws Exception {
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();

	}

	private static BlogPostingResource _getBlogPostingResource() {
			return _blogPostingResourceServiceTracker.getService();
	}

	private static final ServiceTracker<BlogPostingResource, BlogPostingResource> _blogPostingResourceServiceTracker;
	private static ImageObjectResource _getImageObjectResource() {
			return _imageObjectResourceServiceTracker.getService();
	}

	private static final ServiceTracker<ImageObjectResource, ImageObjectResource> _imageObjectResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

			ServiceTracker<BlogPostingResource, BlogPostingResource> blogPostingResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), BlogPostingResource.class, null);

			blogPostingResourceServiceTracker.open();

			_blogPostingResourceServiceTracker = blogPostingResourceServiceTracker;
			ServiceTracker<ImageObjectResource, ImageObjectResource> imageObjectResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), ImageObjectResource.class, null);

			imageObjectResourceServiceTracker.open();

			_imageObjectResourceServiceTracker = imageObjectResourceServiceTracker;
	}

}