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

package com.liferay.headless.document.library.internal.graphql.query.v1_0;

import com.liferay.headless.document.library.internal.dto.v1_0.CommentImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.DocumentImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.FolderImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.DocumentResourceImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.FolderResourceImpl;
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
	public CommentImpl getComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

		commentResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (CommentImpl)commentResourceImpl.getComment(commentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<CommentImpl> getCommentCommentsPage(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

		commentResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = commentResourceImpl.getCommentCommentsPage(
			commentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DocumentImpl> getContentSpaceDocumentsPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		DocumentResourceImpl documentResourceImpl = _getDocumentResourceImpl();

		documentResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = documentResourceImpl.getContentSpaceDocumentsPage(
			contentSpaceId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FolderImpl> getContentSpaceFoldersPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		FolderResourceImpl folderResourceImpl = _getFolderResourceImpl();

		folderResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = folderResourceImpl.getContentSpaceFoldersPage(
			contentSpaceId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DocumentImpl getDocument(@GraphQLName("document-id") Long documentId)
		throws Exception {

		DocumentResourceImpl documentResourceImpl = _getDocumentResourceImpl();

		documentResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (DocumentImpl)documentResourceImpl.getDocument(documentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<CommentImpl> getDocumentCommentsPage(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		CommentResourceImpl commentResourceImpl = _getCommentResourceImpl();

		commentResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = commentResourceImpl.getDocumentCommentsPage(
			documentId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FolderImpl getFolder(@GraphQLName("folder-id") Long folderId)
		throws Exception {

		FolderResourceImpl folderResourceImpl = _getFolderResourceImpl();

		folderResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return (FolderImpl)folderResourceImpl.getFolder(folderId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<DocumentImpl> getFolderDocumentsPage(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("Sort[]") Sort[] sorts)
		throws Exception {

		DocumentResourceImpl documentResourceImpl = _getDocumentResourceImpl();

		documentResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = documentResourceImpl.getFolderDocumentsPage(
			folderId, filter, Pagination.of(pageSize, page), sorts);

		return paginationPage.getItems();
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<FolderImpl> getFolderFoldersPage(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		FolderResourceImpl folderResourceImpl = _getFolderResourceImpl();

		folderResourceImpl.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		Page paginationPage = folderResourceImpl.getFolderFoldersPage(
			folderId, Pagination.of(pageSize, page));

		return paginationPage.getItems();
	}

	private static CommentResourceImpl _getCommentResourceImpl() {
		return new CommentResourceImpl();
	}

	private static DocumentResourceImpl _getDocumentResourceImpl() {
		return new DocumentResourceImpl();
	}

	private static FolderResourceImpl _getFolderResourceImpl() {
		return new FolderResourceImpl();
	}

}