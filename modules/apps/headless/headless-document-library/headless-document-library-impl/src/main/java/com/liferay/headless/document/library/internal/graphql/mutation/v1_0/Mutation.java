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

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.internal.resource.v1_0.CommentResourceImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.DocumentResourceImpl;
import com.liferay.headless.document.library.internal.resource.v1_0.FolderResourceImpl;
import com.liferay.headless.document.library.resource.v1_0.CommentResource;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
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
	public boolean deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.deleteComment(commentId);
	}

	@GraphQLInvokeDetached
	public boolean deleteDocument(@GraphQLName("document-id") Long documentId)
		throws Exception {

		DocumentResource documentResource = _createDocumentResource();

		return documentResource.deleteDocument(documentId);
	}

	@GraphQLInvokeDetached
	public boolean deleteFolder(@GraphQLName("folder-id") Long folderId)
		throws Exception {

		FolderResource folderResource = _createFolderResource();

		return folderResource.deleteFolder(folderId);
	}

	@GraphQLInvokeDetached
	public Document patchDocument(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		DocumentResource documentResource = _createDocumentResource();

		return documentResource.patchDocument(documentId, multipartBody);
	}

	@GraphQLInvokeDetached
	public Folder patchFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		FolderResource folderResource = _createFolderResource();

		return folderResource.patchFolder(folderId, folder);
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
	public Document postContentSpaceDocument(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		DocumentResource documentResource = _createDocumentResource();

		return documentResource.postContentSpaceDocument(
			contentSpaceId, multipartBody);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postContentSpaceFolder(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		FolderResource folderResource = _createFolderResource();

		return folderResource.postContentSpaceFolder(contentSpaceId, folder);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postDocumentComment(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		CommentResource commentResource = _createCommentResource();

		return commentResource.postDocumentComment(documentId, comment);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Document postFolderDocument(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		DocumentResource documentResource = _createDocumentResource();

		return documentResource.postFolderDocument(folderId, multipartBody);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postFolderFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		FolderResource folderResource = _createFolderResource();

		return folderResource.postFolderFolder(folderId, folder);
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
	public Document putDocument(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		DocumentResource documentResource = _createDocumentResource();

		return documentResource.putDocument(documentId, multipartBody);
	}

	@GraphQLInvokeDetached
	public Folder putFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		FolderResource folderResource = _createFolderResource();

		return folderResource.putFolder(folderId, folder);
	}

	private static CommentResource _createCommentResource() {
		return new CommentResourceImpl();
	}

	private static DocumentResource _createDocumentResource() {
		return new DocumentResourceImpl();
	}

	private static FolderResource _createFolderResource() {
		return new FolderResourceImpl();
	}

}