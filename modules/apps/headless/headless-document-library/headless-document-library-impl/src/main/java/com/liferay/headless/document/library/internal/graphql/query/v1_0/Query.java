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

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.CommentResource;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
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
	public Collection<Comment> getDocumentCommentsPage( @GraphQLName("document-id") Long documentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getCommentResource().getDocumentCommentsPage(

					documentId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Document getDocument( @GraphQLName("document-id") Long documentId ) throws Exception {
return _getDocumentResource().getDocument( documentId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Long> getDocumentCategoriesPage( @GraphQLName("document-id") Long documentId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getDocumentResource().getDocumentCategoriesPage(

					documentId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Document> getDocumentsRepositoryDocumentsPage( @GraphQLName("documents-repository-id") Long documentsRepositoryId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getDocumentResource().getDocumentsRepositoryDocumentsPage(

					documentsRepositoryId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Document> getFolderDocumentsPage( @GraphQLName("folder-id") Long folderId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getDocumentResource().getFolderDocumentsPage(

					folderId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder getDocumentsRepository( @GraphQLName("documents-repository-id") Long documentsRepositoryId ) throws Exception {
return _getFolderResource().getDocumentsRepository( documentsRepositoryId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Folder> getDocumentsRepositoryFoldersPage( @GraphQLName("documents-repository-id") Long documentsRepositoryId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getFolderResource().getDocumentsRepositoryFoldersPage(

					documentsRepositoryId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Folder getFolder( @GraphQLName("folder-id") Long folderId ) throws Exception {
return _getFolderResource().getFolder( folderId );
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Folder> getFolderFoldersPage( @GraphQLName("folder-id") Long folderId , @GraphQLName("per_page") int perPage , @GraphQLName("page") int page ) throws Exception {
				Page paginationPage = _getFolderResource().getFolderFoldersPage(

					folderId , Pagination.of(perPage, page)
				);

				return paginationPage.getItems();

	}

	private static CommentResource _getCommentResource() {
			return _commentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CommentResource, CommentResource> _commentResourceServiceTracker;
	private static DocumentResource _getDocumentResource() {
			return _documentResourceServiceTracker.getService();
	}

	private static final ServiceTracker<DocumentResource, DocumentResource> _documentResourceServiceTracker;
	private static FolderResource _getFolderResource() {
			return _folderResourceServiceTracker.getService();
	}

	private static final ServiceTracker<FolderResource, FolderResource> _folderResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Query.class);

			ServiceTracker<CommentResource, CommentResource> commentResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), CommentResource.class, null);

			commentResourceServiceTracker.open();

			_commentResourceServiceTracker = commentResourceServiceTracker;
			ServiceTracker<DocumentResource, DocumentResource> documentResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), DocumentResource.class, null);

			documentResourceServiceTracker.open();

			_documentResourceServiceTracker = documentResourceServiceTracker;
			ServiceTracker<FolderResource, FolderResource> folderResourceServiceTracker =
				new ServiceTracker<>(bundle.getBundleContext(), FolderResource.class, null);

			folderResourceServiceTracker.open();

			_folderResourceServiceTracker = folderResourceServiceTracker;
	}

}