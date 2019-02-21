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

package com.liferay.headless.document.library.internal.graphql.mutation.v1_0;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.internal.dto.v1_0.DocumentImpl;
import com.liferay.headless.document.library.internal.dto.v1_0.FolderImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.DocumentResourceImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.FolderResourceImpl;
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

	@GraphQLField
	@GraphQLInvokeDetached
	public DocumentImpl postContentSpaceDocument(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("MultipartBody") MultipartBody multipartBody)
			throws Exception {

				return _getDocumentResourceImpl().postContentSpaceDocument(
					contentSpaceId,multipartBody);
	}

	@GraphQLInvokeDetached
	public boolean deleteDocument(
	@GraphQLName("document-id") Long documentId)
			throws Exception {

				return _getDocumentResourceImpl().deleteDocument(
					documentId);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public DocumentImpl postFolderDocument(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("MultipartBody") MultipartBody multipartBody)
			throws Exception {

				return _getDocumentResourceImpl().postFolderDocument(
					folderId,multipartBody);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FolderImpl postContentSpaceFolder(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResourceImpl().postContentSpaceFolder(
					contentSpaceId,folder);
	}

	@GraphQLInvokeDetached
	public boolean deleteFolder(
	@GraphQLName("folder-id") Long folderId)
			throws Exception {

				return _getFolderResourceImpl().deleteFolder(
					folderId);
	}

	@GraphQLInvokeDetached
	public FolderImpl putFolder(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResourceImpl().putFolder(
					folderId,folder);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public FolderImpl postFolderFolder(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResourceImpl().postFolderFolder(
					folderId,folder);
	}

	private static DocumentResourceImpl _getDocumentResourceImpl() {
			return new DocumentResourceImpl();
	}
	private static FolderResourceImpl _getFolderResourceImpl() {
			return new FolderResourceImpl();
	}

}