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

import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImageImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.BlogPostingImageResourceImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.BlogPostingResourceImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.CommentResourceImpl;
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

		return (boolean)_getBlogPostingResourceImpl().deleteBlogPosting(
			blogPostingId);
	}

	@GraphQLInvokeDetached
	public boolean deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		return (boolean)_getCommentResourceImpl().deleteComment(commentId);
	}

	@GraphQLInvokeDetached
	public boolean deleteImageObject(
			@GraphQLName("image-object-id") Long imageObjectId)
		throws Exception {

		return (boolean)_getBlogPostingImageResourceImpl().deleteImageObject(
			imageObjectId);
	}

	@GraphQLInvokeDetached
	public BlogPostingImpl patchBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPostingImpl blogPostingImpl)
		throws Exception {

		return (BlogPostingImpl)_getBlogPostingResourceImpl().patchBlogPosting(
			blogPostingId, blogPostingImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public CommentImpl postBlogPostingComment(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("Comment") CommentImpl commentImpl)
		throws Exception {

		return (CommentImpl)_getCommentResourceImpl().postBlogPostingComment(
			blogPostingId, commentImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public CommentImpl postCommentComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") CommentImpl commentImpl)
		throws Exception {

		return (CommentImpl)_getCommentResourceImpl().postCommentComment(
			commentId, commentImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImpl postContentSpaceBlogPosting(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("BlogPosting") BlogPostingImpl blogPostingImpl)
		throws Exception {

		return (BlogPostingImpl)
			_getBlogPostingResourceImpl().postContentSpaceBlogPosting(
				contentSpaceId, blogPostingImpl);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImageImpl postContentSpaceBlogPostingImage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return (BlogPostingImageImpl)
			_getBlogPostingImageResourceImpl().postContentSpaceBlogPostingImage(
				contentSpaceId, multipartBody);
	}

	@GraphQLInvokeDetached
	public BlogPostingImpl putBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPostingImpl blogPostingImpl)
		throws Exception {

		return (BlogPostingImpl)_getBlogPostingResourceImpl().putBlogPosting(
			blogPostingId, blogPostingImpl);
	}

	@GraphQLInvokeDetached
	public CommentImpl putComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") CommentImpl commentImpl)
		throws Exception {

		return (CommentImpl)_getCommentResourceImpl().putComment(
			commentId, commentImpl);
	}

	private static BlogPostingImageResourceImpl
		_getBlogPostingImageResourceImpl() {

		return new BlogPostingImageResourceImpl();
	}

	private static BlogPostingResourceImpl _getBlogPostingResourceImpl() {
		return new BlogPostingResourceImpl();
	}

	private static CommentResourceImpl _getCommentResourceImpl() {
		return new CommentResourceImpl();
	}

}