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

package com.liferay.data.engine.rest.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollectionPermission;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/data-engine/v1.0
 *
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface DataRecordCollectionResource {

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/data-definitions/{dataDefinitionId}/data-record-collections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				@NotNull @PathParam("dataDefinitionId") Long dataDefinitionId,
				@QueryParam("keywords") String keywords,
				@Context Pagination pagination)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/data-definitions/{dataDefinitionId}/data-record-collections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection postDataDefinitionDataRecordCollection(
			@NotNull @PathParam("dataDefinitionId") Long dataDefinitionId,
			DataRecordCollection dataRecordCollection)
		throws Exception;

	@DELETE
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void deleteDataRecordCollection(
			@NotNull @PathParam("dataRecordCollectionId") Long
				dataRecordCollectionId)
		throws Exception;

	@GET
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection getDataRecordCollection(
			@NotNull @PathParam("dataRecordCollectionId") Long
				dataRecordCollectionId)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/data-record-collections/{dataRecordCollectionId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public DataRecordCollection putDataRecordCollection(
			@NotNull @PathParam("dataRecordCollectionId") Long
				dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path(
		"/data-record-collections/{dataRecordCollectionId}/data-record-collection-permissions"
	)
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void postDataRecordCollectionDataRecordCollectionPermission(
			@NotNull @PathParam("dataRecordCollectionId") Long
				dataRecordCollectionId,
			@NotNull @QueryParam("operation") String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/sites/{siteId}/data-record-collection-permissions")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public void postSiteDataRecordCollectionPermission(
			@NotNull @PathParam("siteId") Long siteId,
			@NotNull @QueryParam("operation") String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/sites/{siteId}/data-record-collections")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "DataRecordCollection")})
	public Page<DataRecordCollection> getSiteDataRecordCollectionsPage(
			@NotNull @PathParam("siteId") Long siteId,
			@QueryParam("keywords") String keywords,
			@Context Pagination pagination)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}