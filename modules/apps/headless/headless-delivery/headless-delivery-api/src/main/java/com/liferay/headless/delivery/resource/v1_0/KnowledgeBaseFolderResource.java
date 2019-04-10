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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
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
public interface KnowledgeBaseFolderResource {

	@DELETE
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public void deleteKnowledgeBaseFolder(
			@NotNull @PathParam("knowledgeBaseFolderId") Long
				knowledgeBaseFolderId)
		throws Exception;

	@GET
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			@NotNull @PathParam("knowledgeBaseFolderId") Long
				knowledgeBaseFolderId)
		throws Exception;

	@Consumes("application/json")
	@PATCH
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			@NotNull @PathParam("knowledgeBaseFolderId") Long
				knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/knowledge-base-folders/{knowledgeBaseFolderId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			@NotNull @PathParam("knowledgeBaseFolderId") Long
				knowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				@NotNull @PathParam("parentKnowledgeBaseFolderId") Long
					parentKnowledgeBaseFolderId,
				@Context Pagination pagination)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path(
		"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			@NotNull @PathParam("parentKnowledgeBaseFolderId") Long
				parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/sites/{siteId}/knowledge-base-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			@NotNull @PathParam("siteId") Long siteId,
			@Context Pagination pagination)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/sites/{siteId}/knowledge-base-folders")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "KnowledgeBaseFolder")})
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			@NotNull @PathParam("siteId") Long siteId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}