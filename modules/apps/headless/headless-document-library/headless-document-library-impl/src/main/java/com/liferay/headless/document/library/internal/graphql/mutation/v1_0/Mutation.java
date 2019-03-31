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
import com.liferay.headless.document.library.dto.v1_0.Rating;
import com.liferay.headless.document.library.resource.v1_0.CommentResource;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.headless.document.library.resource.v1_0.RatingResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setCommentResourceComponentServiceObjects(
		ComponentServiceObjects<CommentResource>
			commentResourceComponentServiceObjects) {

		_commentResourceComponentServiceObjects =
			commentResourceComponentServiceObjects;
	}

	public static void setDocumentResourceComponentServiceObjects(
		ComponentServiceObjects<DocumentResource>
			documentResourceComponentServiceObjects) {

		_documentResourceComponentServiceObjects =
			documentResourceComponentServiceObjects;
	}

	public static void setFolderResourceComponentServiceObjects(
		ComponentServiceObjects<FolderResource>
			folderResourceComponentServiceObjects) {

		_folderResourceComponentServiceObjects =
			folderResourceComponentServiceObjects;
	}

	public static void setRatingResourceComponentServiceObjects(
		ComponentServiceObjects<RatingResource>
			ratingResourceComponentServiceObjects) {

		_ratingResourceComponentServiceObjects =
			ratingResourceComponentServiceObjects;
	}

	@GraphQLInvokeDetached
	public void deleteComment(@GraphQLName("comment-id") Long commentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.deleteComment(commentId));
	}

	@GraphQLInvokeDetached
	public Comment putComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.putComment(commentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postCommentComment(
			@GraphQLName("comment-id") Long commentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postCommentComment(
				commentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Comment postDocumentComment(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("Comment") Comment comment)
		throws Exception {

		return _applyComponentServiceObjects(
			_commentResourceComponentServiceObjects,
			this::_populateResourceContext,
			commentResource -> commentResource.postDocumentComment(
				documentId, comment));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postContentSpaceDocumentContentSpaceIdMultipartBody")
	public Document postContentSpaceDocument(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postContentSpaceDocument(
				contentSpaceId, multipartBody));
	}

	@GraphQLInvokeDetached
	public void deleteDocument(@GraphQLName("document-id") Long documentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.deleteDocument(documentId));
	}

	@GraphQLInvokeDetached
	@GraphQLName("patchDocumentDocumentIdMultipartBody")
	public Document patchDocument(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.patchDocument(
				documentId, multipartBody));
	}

	@GraphQLInvokeDetached
	@GraphQLName("putDocumentDocumentIdMultipartBody")
	public Document putDocument(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.putDocument(
				documentId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Rating postDocumentRating(
			@GraphQLName("document-id") Long documentId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postDocumentRating(
				documentId, rating));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	@GraphQLName("postFolderDocumentFolderIdMultipartBody")
	public Document postFolderDocument(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.postFolderDocument(
				folderId, multipartBody));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postContentSpaceFolder(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.postContentSpaceFolder(
				contentSpaceId, folder));
	}

	@GraphQLInvokeDetached
	public void deleteFolder(@GraphQLName("folder-id") Long folderId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.deleteFolder(folderId));
	}

	@GraphQLInvokeDetached
	public Folder patchFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.patchFolder(folderId, folder));
	}

	@GraphQLInvokeDetached
	public Folder putFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.putFolder(folderId, folder));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder postFolderFolder(
			@GraphQLName("folder-id") Long folderId,
			@GraphQLName("Folder") Folder folder)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.postFolderFolder(
				folderId, folder));
	}

	@GraphQLInvokeDetached
	public void deleteRating(@GraphQLName("rating-id") Long ratingId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.deleteRating(ratingId));
	}

	@GraphQLInvokeDetached
	public Rating putRating(
			@GraphQLName("rating-id") Long ratingId,
			@GraphQLName("Rating") Rating rating)
		throws Exception {

		return _applyComponentServiceObjects(
			_ratingResourceComponentServiceObjects,
			this::_populateResourceContext,
			ratingResource -> ratingResource.putRating(ratingId, rating));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(CommentResource commentResource)
		throws Exception {

		commentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(FolderResource folderResource)
		throws Exception {

		folderResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(RatingResource ratingResource)
		throws Exception {

		ratingResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<CommentResource>
		_commentResourceComponentServiceObjects;
	private static ComponentServiceObjects<DocumentResource>
		_documentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FolderResource>
		_folderResourceComponentServiceObjects;
	private static ComponentServiceObjects<RatingResource>
		_ratingResourceComponentServiceObjects;

}