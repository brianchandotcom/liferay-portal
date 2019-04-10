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

package com.liferay.headless.delivery.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-delivery/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface DocumentResource {

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/document-folders/{documentFolderId}/documents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Page<Document> getDocumentFolderDocumentsPage(
			@NotNull @PathParam("documentFolderId") Long documentFolderId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("multipart/form-data")
	@POST
	@Path("/document-folders/{documentFolderId}/documents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Document postDocumentFolderDocument(
			@NotNull @PathParam("documentFolderId") Long documentFolderId,
			MultipartBody multipartBody)
		throws Exception;

	@DELETE
	@Path("/documents/{documentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public void deleteDocument(
			@NotNull @PathParam("documentId") Long documentId)
		throws Exception;

	@GET
	@Path("/documents/{documentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Document getDocument(
			@NotNull @PathParam("documentId") Long documentId)
		throws Exception;

	@Consumes("multipart/form-data")
	@PATCH
	@Path("/documents/{documentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Document patchDocument(
			@NotNull @PathParam("documentId") Long documentId,
			MultipartBody multipartBody)
		throws Exception;

	@Consumes("multipart/form-data")
	@PUT
	@Path("/documents/{documentId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Document putDocument(
			@NotNull @PathParam("documentId") Long documentId,
			MultipartBody multipartBody)
		throws Exception;

	@DELETE
	@Path("/documents/{documentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public void deleteDocumentMyRating(
			@NotNull @PathParam("documentId") Long documentId)
		throws Exception;

	@GET
	@Path("/documents/{documentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Rating getDocumentMyRating(
			@NotNull @PathParam("documentId") Long documentId)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/documents/{documentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Rating postDocumentMyRating(
			@NotNull @PathParam("documentId") Long documentId, Rating rating)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/documents/{documentId}/my-rating")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Rating putDocumentMyRating(
			@NotNull @PathParam("documentId") Long documentId, Rating rating)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/sites/{siteId}/documents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Page<Document> getSiteDocumentsPage(
			@NotNull @PathParam("siteId") Long siteId,
			@QueryParam("flatten") Boolean flatten,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("multipart/form-data")
	@POST
	@Path("/sites/{siteId}/documents")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Document")})
	public Document postSiteDocument(
			@NotNull @PathParam("siteId") Long siteId,
			MultipartBody multipartBody)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}