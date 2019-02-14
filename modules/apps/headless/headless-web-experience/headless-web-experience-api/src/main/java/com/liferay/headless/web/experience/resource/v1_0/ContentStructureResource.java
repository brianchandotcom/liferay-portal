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

package com.liferay.headless.web.experience.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-web-experience/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface ContentStructureResource {

	@GET
	@Path("/content-spaces/{content-space-id}/content-structures")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<ContentStructure> getContentSpaceContentStructuresPage( @PathParam("content-space-id") Long contentSpaceId , @Context Filter filter , @Context Pagination pagination , @Context Sort[] sorts ) throws Exception;

	@GET
	@Path("/content-structures/{content-structure-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public ContentStructure getContentStructure( @PathParam("content-structure-id") Long contentStructureId ) throws Exception;

}