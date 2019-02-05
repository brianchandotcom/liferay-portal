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

package com.liferay.headless.document.library.internal.resource;

import com.liferay.headless.document.library.dto.Document;
import com.liferay.headless.document.library.resource.DocumentResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseDocumentResourceImpl implements DocumentResource {

	@Override
	public Response deleteDocument(Long id) throws Exception {
		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Document getDocument(Long id) throws Exception {
		return new Document();
	}

	@Override
	public Page<Document> getDocumentsRepositoryDocumentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Page<Document> getFolderDocumentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Document postDocumentsRepositoryDocument(Long parentId)
		throws Exception {

		return new Document();
	}

	@Override
	public Document postDocumentsRepositoryDocumentBatchCreate(Long parentId)
		throws Exception {

		return new Document();
	}

	@Override
	public Document postFolderDocument(Long parentId) throws Exception {
		return new Document();
	}

	@Override
	public Document postFolderDocumentBatchCreate(Long parentId)
		throws Exception {

		return new Document();
	}

}