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
import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

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
	public boolean deleteBlogPosting(
	@GraphQLName("blog-posting-id") Long blogPostingId)
			throws Exception {

				return _getBlogPostingResource().deleteBlogPosting(
					blogPostingId);
	}
	@GraphQLInvokeDetached
	public BlogPosting patchBlogPosting(
	@GraphQLName("blog-posting-id") Long blogPostingId,@GraphQLName("BlogPosting") BlogPosting blogPosting)
			throws Exception {

				return _getBlogPostingResource().patchBlogPosting(
					blogPostingId,blogPosting);
	}
	@GraphQLInvokeDetached
	public BlogPosting putBlogPosting(
	@GraphQLName("blog-posting-id") Long blogPostingId,@GraphQLName("BlogPosting") BlogPosting blogPosting)
			throws Exception {

				return _getBlogPostingResource().putBlogPosting(
					blogPostingId,blogPosting);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting postContentSpaceBlogPosting(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("BlogPosting") BlogPosting blogPosting)
			throws Exception {

				return _getBlogPostingResource().postContentSpaceBlogPosting(
					contentSpaceId,blogPosting);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage postContentSpaceBlogPostingImage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("MultipartBody") MultipartBody multipartBody)
			throws Exception {

				return _getBlogPostingImageResource().postContentSpaceBlogPostingImage(
					contentSpaceId,multipartBody);
	}
	@GraphQLInvokeDetached
	public boolean deleteImageObject(
	@GraphQLName("image-object-id") Long imageObjectId)
			throws Exception {

				return _getBlogPostingImageResource().deleteImageObject(
					imageObjectId);
	}

	private static BlogPostingResource _getBlogPostingResource() {
			return _blogPostingResourceServiceTracker.getService();
	}

	private static final ServiceTracker<BlogPostingResource, BlogPostingResource>
			_blogPostingResourceServiceTracker;
	private static BlogPostingImageResource _getBlogPostingImageResource() {
			return _blogPostingImageResourceServiceTracker.getService();
	}

	private static final ServiceTracker<BlogPostingImageResource, BlogPostingImageResource>
			_blogPostingImageResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

				ServiceTracker<BlogPostingResource, BlogPostingResource>
					blogPostingResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							BlogPostingResource.class, null);

				blogPostingResourceServiceTracker.open();

				_blogPostingResourceServiceTracker =
					blogPostingResourceServiceTracker;
				ServiceTracker<BlogPostingImageResource, BlogPostingImageResource>
					blogPostingImageResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							BlogPostingImageResource.class, null);

				blogPostingImageResourceServiceTracker.open();

				_blogPostingImageResourceServiceTracker =
					blogPostingImageResourceServiceTracker;
	}

}