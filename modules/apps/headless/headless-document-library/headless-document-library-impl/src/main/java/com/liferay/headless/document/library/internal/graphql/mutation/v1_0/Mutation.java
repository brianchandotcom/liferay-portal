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
import com.liferay.headless.document.library.resource.v1_0.CommentResource;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
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
	public boolean deleteComment(
	@GraphQLName("comment-id") Long commentId)
			throws Exception {

				return _getCommentResource().deleteComment(
					commentId);
	}
	@GraphQLInvokeDetached
	public Comment putComment(
	@GraphQLName("comment-id") Long commentId,@GraphQLName("Comment") Comment comment)
			throws Exception {

				return _getCommentResource().putComment(
					commentId,comment);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postCommentComment(
	@GraphQLName("comment-id") Long commentId,@GraphQLName("Comment") Comment comment)
			throws Exception {

				return _getCommentResource().postCommentComment(
					commentId,comment);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postDocumentComment(
	@GraphQLName("document-id") Long documentId,@GraphQLName("Comment") Comment comment)
			throws Exception {

				return _getCommentResource().postDocumentComment(
					documentId,comment);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Document postContentSpaceDocument(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("MultipartBody") MultipartBody multipartBody)
			throws Exception {

				return _getDocumentResource().postContentSpaceDocument(
					contentSpaceId,multipartBody);
	}
	@GraphQLInvokeDetached
	public boolean deleteDocument(
	@GraphQLName("document-id") Long documentId)
			throws Exception {

				return _getDocumentResource().deleteDocument(
					documentId);
	}
	@GraphQLInvokeDetached
	public Document putDocument(
	@GraphQLName("document-id") Long documentId,@GraphQLName("MultipartBody") MultipartBody multipartBody)
			throws Exception {

				return _getDocumentResource().putDocument(
					documentId,multipartBody);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Document postFolderDocument(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("MultipartBody") MultipartBody multipartBody)
			throws Exception {

				return _getDocumentResource().postFolderDocument(
					folderId,multipartBody);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postContentSpaceFolder(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResource().postContentSpaceFolder(
					contentSpaceId,folder);
	}
	@GraphQLInvokeDetached
	public boolean deleteFolder(
	@GraphQLName("folder-id") Long folderId)
			throws Exception {

				return _getFolderResource().deleteFolder(
					folderId);
	}
	@GraphQLInvokeDetached
	public Folder patchFolder(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResource().patchFolder(
					folderId,folder);
	}
	@GraphQLInvokeDetached
	public Folder putFolder(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResource().putFolder(
					folderId,folder);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postFolderFolder(
	@GraphQLName("folder-id") Long folderId,@GraphQLName("Folder") Folder folder)
			throws Exception {

				return _getFolderResource().postFolderFolder(
					folderId,folder);
	}

	private static CommentResource _getCommentResource() {
			return _commentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CommentResource, CommentResource>
			_commentResourceServiceTracker;
	private static DocumentResource _getDocumentResource() {
			return _documentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<DocumentResource, DocumentResource>
			_documentResourceServiceTracker;
	private static FolderResource _getFolderResource() {
			return _folderResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FolderResource, FolderResource>
			_folderResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

				ServiceTracker<CommentResource, CommentResource>
					commentResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							CommentResource.class, null);

				commentResourceServiceTracker.open();

				_commentResourceServiceTracker =
					commentResourceServiceTracker;
				ServiceTracker<DocumentResource, DocumentResource>
					documentResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							DocumentResource.class, null);

				documentResourceServiceTracker.open();

				_documentResourceServiceTracker =
					documentResourceServiceTracker;
				ServiceTracker<FolderResource, FolderResource>
					folderResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							FolderResource.class, null);

				folderResourceServiceTracker.open();

				_folderResourceServiceTracker =
					folderResourceServiceTracker;
	}

}