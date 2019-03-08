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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
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
	public BlogPosting getBlogPosting(
			@GraphQLName("blog-posting-id") Long blogPostingId)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		return blogPostingResource.getBlogPosting(blogPostingId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPosting> getContentSpaceBlogPostingsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		BlogPostingResource blogPostingResource = _createBlogPostingResource();

		Page paginationPage =
			blogPostingResource.getContentSpaceBlogPostingsPage(
				contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImage getBlogPostingImage(
			@GraphQLName("blog-posting-image-id") Long blogPostingImageId)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		return blogPostingImageResource.getBlogPostingImage(blogPostingImageId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPostingImage> getContentSpaceBlogPostingImagesPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_createBlogPostingImageResource();

		Page paginationPage =
			blogPostingImageResource.getContentSpaceBlogPostingImagesPage(
				contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getBlogPostingCommentsPage(
			@GraphQLName("blog-posting-id") Long blogPostingId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		Page paginationPage = commentResource.getBlogPostingCommentsPage(
			blogPostingId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment getComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.getComment(commentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getCommentCommentsPage(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		Page paginationPage = commentResource.getCommentCommentsPage(
			commentId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getContentSpaceKnowledgeBaseArticlesPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		Page paginationPage =
			knowledgeBaseArticleResource.
				getContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			@GraphQLName("knowledge-base-article-id") Long
				knowledgeBaseArticleId)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		return knowledgeBaseArticleResource.getKnowledgeBaseArticle(
			knowledgeBaseArticleId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				@GraphQLName("knowledge-base-article-id") Long
					knowledgeBaseArticleId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		Page paginationPage =
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					knowledgeBaseArticleId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				@GraphQLName("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			_createKnowledgeBaseArticleResource();

		Page paginationPage =
			knowledgeBaseArticleResource.
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseAttachment>
			getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
				@GraphQLName("knowledge-base-article-id") Long
					knowledgeBaseArticleId)
		throws Exception {

		KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource =
			_createKnowledgeBaseAttachmentResource();

		Page paginationPage =
			knowledgeBaseAttachmentResource.
				getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
					knowledgeBaseArticleId);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseAttachment getKnowledgeBaseAttachment(
			@GraphQLName("knowledge-base-attachment-id") Long
				knowledgeBaseAttachmentId)
		throws Exception {

		KnowledgeBaseAttachmentResource knowledgeBaseAttachmentResource =
			_createKnowledgeBaseAttachmentResource();

		return knowledgeBaseAttachmentResource.getKnowledgeBaseAttachment(
			knowledgeBaseAttachmentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseFolder>
			getContentSpaceKnowledgeBaseFoldersPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		Page paginationPage =
			knowledgeBaseFolderResource.getContentSpaceKnowledgeBaseFoldersPage(
				contentSpaceId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@GraphQLName("knowledge-base-folder-id") Long knowledgeBaseFolderId)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		return knowledgeBaseFolderResource.getKnowledgeBaseFolder(
			knowledgeBaseFolderId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@GraphQLName("knowledge-base-folder-id") Long
					knowledgeBaseFolderId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			_createKnowledgeBaseFolderResource();

		Page paginationPage =
			knowledgeBaseFolderResource.
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					knowledgeBaseFolderId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
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
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

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