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

package com.liferay.headless.web.experience.internal.graphql.query.v1_0;

import com.liferay.headless.web.experience.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.ContentStructureImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImageImpl;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.ContentStructureResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.StructuredContentImageResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.StructuredContentResourceImpl;
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

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	@GraphQLField
	@GraphQLInvokeDetached
	public CommentImpl getComment(
	@GraphQLName("comment-id") Long commentId)
			throws Exception {

				CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

				commentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return commentResourceImpl.getComment(
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
	public Collection<CommentImpl> getStructuredContentCommentsPage(
	@GraphQLName("structured-content-id") Long structuredContentId,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page)
			throws Exception {

				CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

				commentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = commentResourceImpl.getStructuredContentCommentsPage(
					structuredContentId,Pagination.of(pageSize, page));

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentStructureImpl> getContentSpaceContentStructuresPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				ContentStructureResourceImpl contentStructureResourceImpl = _getContentStructureResourceImpl();

				contentStructureResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = contentStructureResourceImpl.getContentSpaceContentStructuresPage(
					contentSpaceId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ContentStructureImpl getContentStructure(
	@GraphQLName("content-structure-id") Long contentStructureId)
			throws Exception {

				ContentStructureResourceImpl contentStructureResourceImpl = _getContentStructureResourceImpl();

				contentStructureResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return contentStructureResourceImpl.getContentStructure(
					contentStructureId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContentImpl> getContentSpaceContentStructureStructuredContentsPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("content-structure-id") Long contentStructureId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				StructuredContentResourceImpl structuredContentResourceImpl = _getStructuredContentResourceImpl();

				structuredContentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = structuredContentResourceImpl.getContentSpaceContentStructureStructuredContentsPage(
					contentSpaceId,contentStructureId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContentImpl> getContentSpaceStructuredContentsPage(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("filter") Filter filter,@GraphQLName("pageSize") int pageSize,@GraphQLName("page") int page,@GraphQLName("Sort[]") Sort[] sorts)
			throws Exception {

				StructuredContentResourceImpl structuredContentResourceImpl = _getStructuredContentResourceImpl();

				structuredContentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = structuredContentResourceImpl.getContentSpaceStructuredContentsPage(
					contentSpaceId,filter,Pagination.of(pageSize, page),sorts);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentImpl getStructuredContent(
	@GraphQLName("structured-content-id") Long structuredContentId)
			throws Exception {

				StructuredContentResourceImpl structuredContentResourceImpl = _getStructuredContentResourceImpl();

				structuredContentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return structuredContentResourceImpl.getStructuredContent(
					structuredContentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public String getStructuredContentTemplate(
	@GraphQLName("structured-content-id") Long structuredContentId,@GraphQLName("template-id") Long templateId)
			throws Exception {

				StructuredContentResourceImpl structuredContentResourceImpl = _getStructuredContentResourceImpl();

				structuredContentResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return structuredContentResourceImpl.getStructuredContentTemplate(
					structuredContentId,templateId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContentImageImpl> getStructuredContentStructuredContentImagesPage(
	@GraphQLName("structured-content-id") Long structuredContentId)
			throws Exception {

				StructuredContentImageResourceImpl structuredContentImageResourceImpl = _getStructuredContentImageResourceImpl();

				structuredContentImageResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				Page paginationPage = structuredContentImageResourceImpl.getStructuredContentStructuredContentImagesPage(
					structuredContentId);

				return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentImageImpl getStructuredContentContentDocument(
	@GraphQLName("structured-content-id") Long structuredContentId,@GraphQLName("content-document-id") Long contentDocumentId)
			throws Exception {

				StructuredContentImageResourceImpl structuredContentImageResourceImpl = _getStructuredContentImageResourceImpl();

				structuredContentImageResourceImpl.setContextCompany(
					CompanyLocalServiceUtil.getCompany(CompanyThreadLocal.getCompanyId()));

				return structuredContentImageResourceImpl.getStructuredContentContentDocument(
					structuredContentId,contentDocumentId);
	}

	private static CommentResourceImpl _getCommentResourceImpl() {
			return new CommentResourceImpl();
	}
	private static ContentStructureResourceImpl _getContentStructureResourceImpl() {
			return new ContentStructureResourceImpl();
	}
	private static StructuredContentResourceImpl _getStructuredContentResourceImpl() {
			return new StructuredContentResourceImpl();
	}
	private static StructuredContentImageResourceImpl _getStructuredContentImageResourceImpl() {
			return new StructuredContentImageResourceImpl();
	}

}