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

package com.liferay.headless.foundation.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface KeywordResource {

	@GET
	@Path("/content-spaces/{content-space-id}/keywords")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Keyword> getContentSpaceKeywordsPage( @PathParam("content-space-id") Long contentSpaceId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/keywords")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Keyword postContentSpaceKeyword( @PathParam("content-space-id") Long contentSpaceId , Keyword keyword ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/keywords/batch-create")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Keyword postContentSpaceKeywordBatchCreate( @PathParam("content-space-id") Long contentSpaceId , Keyword keyword ) throws Exception;

	@DELETE
	@Path("/keywords/{keyword-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Response deleteKeyword( @PathParam("keyword-id") Long keywordId ) throws Exception;

	@GET
	@Path("/keywords/{keyword-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Keyword getKeyword( @PathParam("keyword-id") Long keywordId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/keywords/{keyword-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Keyword putKeyword( @PathParam("keyword-id") Long keywordId , Keyword keyword ) throws Exception;

}