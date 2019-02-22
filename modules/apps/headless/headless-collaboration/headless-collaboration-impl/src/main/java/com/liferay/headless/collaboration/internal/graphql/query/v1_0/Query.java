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

import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImageImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.BlogPostingImageResourceImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.BlogPostingResourceImpl;
import com.liferay.headless.collaboration.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImpl getBlogPosting(
	@GraphQLName("blog-posting-id") Long blogPostingId)
			throws Exception {

				BlogPostingResourceImpl blogPostingResourceImpl = _getBlogPostingResourceImpl();

				blogPostingResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (BlogPostingImpl)blogPostingResourceImpl.getBlogPosting(
					blogPostingId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPostingImpl> getContentSpaceBlogPostingsPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				BlogPostingResourceImpl blogPostingResourceImpl = _getBlogPostingResourceImpl();

				blogPostingResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = blogPostingResourceImpl.getContentSpaceBlogPostingsPage(
					contentSpaceId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<CommentImpl> getBlogPostingCommentsPage(
	@GraphQLName("blog-posting-id") Long blogPostingId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

				commentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = commentResourceImpl.getBlogPostingCommentsPage(
					blogPostingId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public CommentImpl getComment(
	@GraphQLName("comment-id") Long commentId)
			throws Exception {

				CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

				commentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (CommentImpl)commentResourceImpl.getComment(
					commentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<CommentImpl> getCommentCommentsPage(
	@GraphQLName("comment-id") Long commentId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

				commentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = commentResourceImpl.getCommentCommentsPage(
					commentId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<BlogPostingImageImpl> getContentSpaceBlogPostingImagesPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				BlogPostingImageResourceImpl blogPostingImageResourceImpl = _getBlogPostingImageResourceImpl();

				blogPostingImageResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = blogPostingImageResourceImpl.getContentSpaceBlogPostingImagesPage(
					contentSpaceId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public BlogPostingImageImpl getImageObject(
	@GraphQLName("image-object-id") Long imageObjectId)
			throws Exception {

				BlogPostingImageResourceImpl blogPostingImageResourceImpl = _getBlogPostingImageResourceImpl();

				blogPostingImageResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return (BlogPostingImageImpl)blogPostingImageResourceImpl.getImageObject(
					imageObjectId);
	}

	private static BlogPostingResourceImpl _getBlogPostingResourceImpl() {
			return new BlogPostingResourceImpl();
	}
	private static BlogPostingImageResourceImpl _getBlogPostingImageResourceImpl() {
			return new BlogPostingImageResourceImpl();
	}
	private static CommentResourceImpl _getCommentResourceImpl() {
			return new CommentResourceImpl();
	}

}