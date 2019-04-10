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

import com.liferay.headless.foundation.dto.v1_0.UserAccount;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface UserAccountResource {

	@GET
	@Path("/my-user-accounts/{userAccountId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getMyUserAccount(
			@NotNull @PathParam("userAccountId") Long userAccountId)
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
	@Path("/organizations/{organizationId}/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getOrganizationUserAccountsPage(
			@NotNull @PathParam("organizationId") Long organizationId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
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
	@Path("/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getUserAccountsPage(
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception;

	@Consumes("multipart/form-data")
	@POST
	@Path("/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postUserAccount(MultipartBody multipartBody)
		throws Exception;

	@DELETE
	@Path("/user-accounts/{userAccountId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteUserAccount(
			@NotNull @PathParam("userAccountId") Long userAccountId)
		throws Exception;

	@GET
	@Path("/user-accounts/{userAccountId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getUserAccount(
			@NotNull @PathParam("userAccountId") Long userAccountId)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/user-accounts/{userAccountId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount putUserAccount(
			@NotNull @PathParam("userAccountId") Long userAccountId,
			UserAccount userAccount)
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
	@Path("/web-sites/{webSiteId}/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getWebSiteUserAccountsPage(
			@NotNull @PathParam("webSiteId") Long webSiteId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}