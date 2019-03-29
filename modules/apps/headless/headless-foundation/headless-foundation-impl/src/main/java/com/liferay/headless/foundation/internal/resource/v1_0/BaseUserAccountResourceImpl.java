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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseUserAccountResourceImpl
	implements UserAccountResource {

	@Override
	@GET
	@Path("/my-user-accounts/{user-account-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getMyUserAccount(
			@NotNull @PathParam("user-account-id") Long userAccountId)
		throws Exception {

		return new UserAccount();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/organizations/{organization-id}/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getOrganizationUserAccountsPage(
			@NotNull @PathParam("organization-id") Long organizationId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
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
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	@Override
	@Consumes("multipart/form-data")
	@POST
	@Path("/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount postUserAccount(MultipartBody multipartBody)
		throws Exception {

		return new UserAccount();
	}

	@Override
	@DELETE
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public void deleteUserAccount(
			@NotNull @PathParam("user-account-id") Long userAccountId)
		throws Exception {
	}

	@Override
	@GET
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount getUserAccount(
			@NotNull @PathParam("user-account-id") Long userAccountId)
		throws Exception {

		return new UserAccount();
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public UserAccount putUserAccount(
			@NotNull @PathParam("user-account-id") Long userAccountId,
			UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/web-sites/{web-site-id}/user-accounts")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "UserAccount")})
	public Page<UserAccount> getWebSiteUserAccountsPage(
			@NotNull @PathParam("web-site-id") Long webSiteId,
			@Context Filter filter, @Context Pagination pagination,
			@Context Sort[] sorts)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(UserAccount userAccount) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}