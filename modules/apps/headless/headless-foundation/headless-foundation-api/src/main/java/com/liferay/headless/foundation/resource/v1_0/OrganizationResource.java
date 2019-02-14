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

import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.oauth2.provider.scope.RequiresScope;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface OrganizationResource {

	@GET
	@Path("/my-user-accounts/{my-user-account-id}/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getMyUserAccountOrganizationsPage( @PathParam("my-user-account-id") Long myUserAccountId , @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getOrganizationsPage( @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/organizations/{organization-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Organization getOrganization( @PathParam("organization-id") Long organizationId ) throws Exception;

	@GET
	@Path("/organizations/{organization-id}/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getOrganizationOrganizationsPage( @PathParam("organization-id") Long organizationId , @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/user-accounts/{user-account-id}/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getUserAccountOrganizationsPage( @PathParam("user-account-id") Long userAccountId , @Context Pagination pagination ) throws Exception;

}