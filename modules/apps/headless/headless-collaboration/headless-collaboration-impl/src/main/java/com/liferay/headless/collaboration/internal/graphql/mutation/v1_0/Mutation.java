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
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.internal.resource.v1_0.BlogPostingImageResourceImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.BlogPostingResourceImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

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

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.deleteBlogPosting(blogPostingId);
	}

	@GraphQLInvokeDetached
	public boolean deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.deleteComment(commentId);
	}

	@GraphQLInvokeDetached
	public boolean deleteImageObject(
			@GraphQLName("image-object-id") Long imageObjectId)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.deleteImageObject(imageObjectId);
	}

	@GraphQLInvokeDetached
	public BlogPosting patchBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.patchBlogPosting(blogPostingId, blogPosting);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postBlogPostingComment(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.postBlogPostingComment(blogPostingId, comment);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postCommentComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.postCommentComment(commentId, comment);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPosting postContentSpaceBlogPosting(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.postContentSpaceBlogPosting(
			contentSpaceId, blogPosting);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage postContentSpaceBlogPostingImage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.postContentSpaceBlogPostingImage(
			contentSpaceId, multipartBody);
	}

	@GraphQLInvokeDetached
	public BlogPosting putBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.putBlogPosting(blogPostingId, blogPosting);
	}

	@GraphQLInvokeDetached
	public Comment putComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.putComment(commentId, comment);
	}

	@GraphQLInvokeDetached
	public BlogPostingImage putImageObject(
			@GraphQLName("image-object-id") Long imageObjectId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.putImageObject(
			imageObjectId, multipartBody);
	}

	private static BlogPostingImageResource _createBlogPostingImageResource() {
		return new BlogPostingImageResourceImpl();
	}

	private static BlogPostingResource _createBlogPostingResource() {
		return new BlogPostingResourceImpl();
	}

	private static CommentResource _createCommentResource() {
		return new CommentResourceImpl();
	}

}