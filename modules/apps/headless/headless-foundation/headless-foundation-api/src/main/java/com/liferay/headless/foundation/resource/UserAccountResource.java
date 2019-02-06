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

import com.liferay.headless.foundation.dto.Category;
import com.liferay.headless.foundation.dto.Email;
import com.liferay.headless.foundation.dto.Keyword;
import com.liferay.headless.foundation.dto.Organization;
import com.liferay.headless.foundation.dto.Phone;
import com.liferay.headless.foundation.dto.PostalAddress;
import com.liferay.headless.foundation.dto.Role;
import com.liferay.headless.foundation.dto.UserAccount;
import com.liferay.headless.foundation.dto.Vocabulary;
import com.liferay.headless.foundation.dto.WebUrl;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

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
public interface UserAccountResource {

	@GET
	@Path("/my-user-account")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getMyUserAccountPage( @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/my-user-account/{my-user-account-id}")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public UserAccount getMyUserAccount( @PathParam("my-user-account-id") Long myUserAccountId ) throws Exception;

	@GET
	@Path("/organization/{organization-id}/user-account")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getOrganizationUserAccountPage( @PathParam("organization-id") Long organizationId , @Context Pagination pagination ) throws Exception;

	@GET
	@Path("/user-account")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getUserAccountPage( @QueryParam("fullnamequery") String fullnamequery , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/user-account")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public UserAccount postUserAccount( ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/user-account/batch-create")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.write")
	public UserAccount postUserAccountBatchCreate( ) throws Exception;

	@DELETE
	@Path("/user-account/{user-account-id}")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public Response deleteUserAccount( @PathParam("user-account-id") Long userAccountId ) throws Exception;

	@GET
	@Path("/user-account/{user-account-id}")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public UserAccount getUserAccount( @PathParam("user-account-id") Long userAccountId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/user-account/{user-account-id}")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public UserAccount putUserAccount( @PathParam("user-account-id") Long userAccountId ) throws Exception;

	@GET
	@Path("/web-site/{web-site-id}/user-account")
	@Produces("application/json")
	@RequiresScope("headless-foundation-application.read")
	public Page<UserAccount> getWebSiteUserAccountPage( @PathParam("web-site-id") Long webSiteId , @Context Pagination pagination ) throws Exception;

}