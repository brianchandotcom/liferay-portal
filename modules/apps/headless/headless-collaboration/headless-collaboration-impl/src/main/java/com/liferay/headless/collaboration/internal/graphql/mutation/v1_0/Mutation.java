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
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
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

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.deleteBlogPosting(blogPostingId);
	}

	@GraphQLInvokeDetached
	public BlogPosting patchBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.patchBlogPosting(blogPostingId, blogPosting);
	}

	@GraphQLInvokeDetached
	public BlogPosting putBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("BlogPosting") BlogPosting blogPosting)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.putBlogPosting(blogPostingId, blogPosting);
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

	@GraphQLInvokeDetached
	public boolean deleteBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.deleteBlogPostingImage(
			blogPostingImageId);
	}

	@GraphQLInvokeDetached
	public BlogPostingImage patchBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.patchBlogPostingImage(
			blogPostingImageId, multipartBody);
	}

	@GraphQLInvokeDetached
	public BlogPostingImage putBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.putBlogPostingImage(
			blogPostingImageId, multipartBody);
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

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postBlogPostingComment(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.postBlogPostingComment(blogPostingId, comment);
	}

	@GraphQLInvokeDetached
	public boolean deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.deleteComment(commentId);
	}

	@GraphQLInvokeDetached
	public Comment putComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.putComment(commentId, comment);
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
	public KnowledgeBaseArticle postContentSpaceKnowledgeBaseArticle(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.
			postContentSpaceKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle);
	}

	@GraphQLInvokeDetached
	public boolean deleteKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticleId);
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.patchKnowledgeBaseArticle(
			knowledgeBaseArticleId, knowledgeBaseArticle);
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.putKnowledgeBaseArticle(
			knowledgeBaseArticleId, knowledgeBaseArticle);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.
			postKnowledgeBaseArticleKnowledgeBaseArticle(
				knowledgeBaseArticleId, knowledgeBaseArticle);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseArticle") KnowledgeBaseArticle
				knowledgeBaseArticle)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.
			postKnowledgeBaseFolderKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseAttachment
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				@GraphQLName("knowledge-base-article-id") Long
					knowledgeBaseArticleId,
				@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource =
			_createKnowledgeBaseAttachmentResource();

		return knowledgeBaseAttachmentResource.
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				knowledgeBaseArticleId, multipartBody);
	}

	@GraphQLInvokeDetached
	public boolean deleteKnowledgeBaseAttachment(
			@GraphQLName("knowledge-base-attachment-id") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource =
			_createKnowledgeBaseAttachmentResource();

		return knowledgeBaseAttachmentResource.deleteKnowledgeBaseAttachment(
			knowledgeBaseAttachmentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder postContentSpaceKnowledgeBaseFolder(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		return knowledgeBaseFolderResource.postContentSpaceKnowledgeBaseFolder(
			contentSpaceId, knowledgeBaseFolder);
	}

	@GraphQLInvokeDetached
	public boolean deleteKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		return knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
			knowledgeBaseFolderId);
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		return knowledgeBaseFolderResource.patchKnowledgeBaseFolder(
			knowledgeBaseFolderId, knowledgeBaseFolder);
	}

	@GraphQLInvokeDetached
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		return knowledgeBaseFolderResource.putKnowledgeBaseFolder(
			knowledgeBaseFolderId, knowledgeBaseFolder);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId,
			@GraphQLName("KnowledgeBaseFolder") KnowledgeBaseFolder
				knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		return knowledgeBaseFolderResource.
			postKnowledgeBaseFolderKnowledgeBaseFolder(
				knowledgeBaseFolderId, knowledgeBaseFolder);
	}

	private static BlogPostingResource _createBlogPostingResource()
		throws Exception {

		BlogPostingResource blogPostingResource =
			_blogPostingResourceServiceTracker.getService();

		blogPostingResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return blogPostingResource;
	}

	private static final ServiceTracker
		<BlogPostingResource, BlogPostingResource>
			_blogPostingResourceServiceTracker;

	private static BlogPostingImageResource _createBlogPostingImageResource()
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_blogPostingImageResourceServiceTracker.getService();

		blogPostingImageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return blogPostingImageResource;
	}

	private static final ServiceTracker
		<BlogPostingImageResource, BlogPostingImageResource>
			_blogPostingImageResourceServiceTracker;

	private static CommentResource _createCommentResource() throws Exception {
		CommentResource commentResource =
			_commentResourceServiceTracker.getService();

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return commentResource;
	}

	private static final ServiceTracker<CommentResource, CommentResource>
		_commentResourceServiceTracker;

	private static KnowledgeBaseArticleResource
			_createKnowledgeBaseArticleResource()
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_knowledgeBaseArticleResourceServiceTracker.getService();

		knowledgeBaseArticleResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return knowledgeBaseArticleResource;
	}

	private static final ServiceTracker
		<KnowledgeBaseArticleResource, KnowledgeBaseArticleResource>
			_knowledgeBaseArticleResourceServiceTracker;

	private static KnowledgeBaseAttachmentResource
			_createKnowledgeBaseAttachmentResource()
		throws Exception {

		KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource =
			_knowledgeBaseAttachmentResourceServiceTracker.getService();

		knowledgeBaseAttachmentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return knowledgeBaseAttachmentResource;
	}

	private static final ServiceTracker
		<KnowledgeBaseAttachmentResource, KnowledgeBaseAttachmentResource>
			_knowledgeBaseAttachmentResourceServiceTracker;

	private static KnowledgeBaseFolderResource
			_createKnowledgeBaseFolderResource()
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_knowledgeBaseFolderResourceServiceTracker.getService();

		knowledgeBaseFolderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return knowledgeBaseFolderResource;
	}

	private static final ServiceTracker
		<KnowledgeBaseFolderResource, KnowledgeBaseFolderResource>
			_knowledgeBaseFolderResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

		ServiceTracker<BlogPostingResource, BlogPostingResource>
			blogPostingResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), BlogPostingResource.class, null);

		blogPostingResourceServiceTracker.open();

		_blogPostingResourceServiceTracker = blogPostingResourceServiceTracker;
		ServiceTracker<BlogPostingImageResource, BlogPostingImageResource>
			blogPostingImageResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), BlogPostingImageResource.class,
				null);

		blogPostingImageResourceServiceTracker.open();

		_blogPostingImageResourceServiceTracker =
			blogPostingImageResourceServiceTracker;
		ServiceTracker<CommentResource, CommentResource>
			commentResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), CommentResource.class, null);

		commentResourceServiceTracker.open();

		_commentResourceServiceTracker = commentResourceServiceTracker;
		ServiceTracker
			<KnowledgeBaseArticleResource, KnowledgeBaseArticleResource>
				knowledgeBaseArticleResourceServiceTracker =
					new ServiceTracker<>(
						bundle.getBundleContext(),
						KnowledgeBaseArticleResource.class, null);

		knowledgeBaseArticleResourceServiceTracker.open();

		_knowledgeBaseArticleResourceServiceTracker =
			knowledgeBaseArticleResourceServiceTracker;
		ServiceTracker
			<KnowledgeBaseAttachmentResource, KnowledgeBaseAttachmentResource>
				knowledgeBaseAttachmentResourceServiceTracker =
					new ServiceTracker<>(
						bundle.getBundleContext(),
						KnowledgeBaseAttachmentResource.class, null);

		knowledgeBaseAttachmentResourceServiceTracker.open();

		_knowledgeBaseAttachmentResourceServiceTracker =
			knowledgeBaseAttachmentResourceServiceTracker;
		ServiceTracker<KnowledgeBaseFolderResource, KnowledgeBaseFolderResource>
			knowledgeBaseFolderResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), KnowledgeBaseFolderResource.class,
				null);

		knowledgeBaseFolderResourceServiceTracker.open();

		_knowledgeBaseFolderResourceServiceTracker =
			knowledgeBaseFolderResourceServiceTracker;
	}

}