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
import com.liferay.headless.document.library.internal.dto.v1_0.DocumentImpl;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseDocumentResourceImpl implements DocumentResource {

	@GET
	@Path("/content-spaces/{content-space-id}/documents")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public Page<Document> getContentSpaceDocumentsPage( @PathParam("content-space-id") Long contentSpaceId , @Context Filter filter , @Context Pagination pagination , @Context Sort[] sorts ) throws Exception {
			return Page.of(Collections.emptyList());

	}
	@Consumes("multipart/form-data")
	@POST
	@Path("/content-spaces/{content-space-id}/documents")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public Document postContentSpaceDocument( @PathParam("content-space-id") Long contentSpaceId , MultipartBody multipartBody ) throws Exception {
			return new DocumentImpl();

	}
	@DELETE
	@Path("/documents/{document-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public boolean deleteDocument( @PathParam("document-id") Long documentId ) throws Exception {
			return false;

	}
	@GET
	@Path("/documents/{document-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public Document getDocument( @PathParam("document-id") Long documentId ) throws Exception {
			return new DocumentImpl();

	}
	@GET
	@Path("/folders/{folder-id}/documents")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public Page<Document> getFolderDocumentsPage( @PathParam("folder-id") Long folderId , @Context Filter filter , @Context Pagination pagination , @Context Sort[] sorts ) throws Exception {
			return Page.of(Collections.emptyList());

	}
	@Consumes("multipart/form-data")
	@POST
	@Path("/folders/{folder-id}/documents")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public Document postFolderDocument( @PathParam("folder-id") Long folderId , MultipartBody multipartBody ) throws Exception {
			return new DocumentImpl();

	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

}