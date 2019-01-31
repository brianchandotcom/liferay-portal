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

package com.liferay.headless.foundation.resource;

import com.liferay.headless.foundation.dto.WebSite;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/1.0.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0")
public interface WebSiteResource {

	@GET
	@Path("/my-user-account/{parent-id}/web-site")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebSite> getMyUserAccountWebSitePage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/user-account/{parent-id}/web-site")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebSite> getUserAccountWebSitePage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/web-site/{id}")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public WebSite getWebSite(@PathParam("id") Integer id) throws Exception;

	@GET
	@Path("/web-site/{parent-id}/web-site")
	@Produces({"*/*"})
	@RequiresScope("headless-foundation-application.read")
	public Page<WebSite> getWebSiteWebSitePage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

}