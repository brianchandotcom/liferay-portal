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

import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage;
import com.liferay.headless.web.experience.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.ContentStructureResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.StructuredContentImageResourceImpl;
import com.liferay.headless.web.experience.internal.resource.v1_0.StructuredContentResourceImpl;
import com.liferay.headless.web.experience.resource.v1_0.CommentResource;
import com.liferay.headless.web.experience.resource.v1_0.ContentStructureResource;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentImageResource;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
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
	public Comment getComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

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

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = commentResource.getCommentCommentsPage(
			commentId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<ContentStructure> getContentSpaceContentStructuresPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		ContentStructureResource contentStructureResource =
			_createContentStructureResource();

		contentStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			contentStructureResource.getContentSpaceContentStructuresPage(
				contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent> getContentSpaceStructuredContentsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		StructuredContentResource structuredContentResource =
			_createStructuredContentResource();

		structuredContentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			structuredContentResource.getContentSpaceStructuredContentsPage(
				contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public ContentStructure getContentStructure(
			@GraphQLName("content-structure-id") Long contentStructureId)
		throws Exception {

		ContentStructureResource contentStructureResource =
			_createContentStructureResource();

		contentStructureResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return contentStructureResource.getContentStructure(contentStructureId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContent>
			getContentStructureStructuredContentsPage(
				@GraphQLName("content-structure-id") Long contentStructureId,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		StructuredContentResource structuredContentResource =
			_createStructuredContentResource();

		structuredContentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, filter, Pagination.of(pageSize, page),
				sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContent getStructuredContent(
			@GraphQLName("structured-content-id") Long structuredContentId)
		throws Exception {

		StructuredContentResource structuredContentResource =
			_createStructuredContentResource();

		structuredContentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return structuredContentResource.getStructuredContent(
			structuredContentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Comment> getStructuredContentCommentsPage(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = commentResource.getStructuredContentCommentsPage(
			structuredContentId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public StructuredContentImage getStructuredContentImage(
			@GraphQLName("structured-content-image-id") Long
				structuredContentImageId)
		throws Exception {

		StructuredContentImageResource structuredContentImageResource =
			_createStructuredContentImageResource();

		structuredContentImageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return structuredContentImageResource.getStructuredContentImage(
			structuredContentImageId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<StructuredContentImage>
			getStructuredContentStructuredContentImagesPage(
				@GraphQLName("structured-content-id") Long structuredContentId)
		throws Exception {

		StructuredContentImageResource structuredContentImageResource =
			_createStructuredContentImageResource();

		structuredContentImageResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage =
			structuredContentImageResource.
				getStructuredContentStructuredContentImagesPage(
					structuredContentId);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public String getStructuredContentTemplate(
			@GraphQLName("structured-content-id") Long structuredContentId,
			@GraphQLName("template-id") Long templateId)
		throws Exception {

		StructuredContentResource structuredContentResource =
			_createStructuredContentResource();

		structuredContentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return structuredContentResource.getStructuredContentTemplate(
			structuredContentId, templateId);
	}

	private static CommentResource _createCommentResource() {
		return new CommentResourceImpl();
	}

	private static ContentStructureResource _createContentStructureResource() {
		return new ContentStructureResourceImpl();
	}

	private static StructuredContentImageResource
		_createStructuredContentImageResource() {

		return new StructuredContentImageResourceImpl();
	}

	private static StructuredContentResource
		_createStructuredContentResource() {

		return new StructuredContentResourceImpl();
	}

}