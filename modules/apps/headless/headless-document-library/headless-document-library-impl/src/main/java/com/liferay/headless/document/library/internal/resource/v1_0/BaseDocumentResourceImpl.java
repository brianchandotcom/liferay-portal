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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseDocumentResourceImpl implements DocumentResource {

	@Override
	public Response deleteDocuments(Long documentId) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Document getDocuments(Long documentId) throws Exception {
		return new Document();
	}

	@Override
	public Page<Long> getDocumentsCategoriesPage(
			Long documentId, Pagination pagination)
		throws Exception {

		return null;
	}

	@Override
	public Page<Document> getDocumentsRepositoriesDocumentsPage(
			Long documentsRepositoryId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<Document> getFoldersDocumentsPage(
			Long folderId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Response postDocumentsCategories(Long documentId, Long referenceId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response postDocumentsCategoriesBatchCreate(
			Long documentId, Long referenceId)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Document postDocumentsRepositoriesDocuments(
			Long documentsRepositoryId, Document document)
		throws Exception {

		return new Document();
	}

	@Override
	public Document postDocumentsRepositoriesDocumentsBatchCreate(
			Long documentsRepositoryId, Document document)
		throws Exception {

		return new Document();
	}

	@Override
	public Document postFoldersDocuments(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	@Override
	public Document postFoldersDocumentsBatchCreate(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		return new Document();
	}

	protected Response buildNoContentResponse() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}