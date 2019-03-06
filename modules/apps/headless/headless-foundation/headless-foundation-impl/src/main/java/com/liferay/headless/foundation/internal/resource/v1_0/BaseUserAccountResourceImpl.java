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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseUserAccountResourceImpl
	implements UserAccountResource {

	@DELETE
	@Override
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	public boolean deleteUserAccount(
			@PathParam("user-account-id") Long userAccountId)
		throws Exception {

		return false;
	}

	@GET
	@Override
	@Path("/my-user-accounts/{my-user-account-id}")
	@Produces("application/json")
	public UserAccount getMyUserAccount(
			@PathParam("my-user-account-id") Long myUserAccountId)
		throws Exception {

		return new UserAccount();
	}

	@GET
	@Override
	@Path("/organizations/{organization-id}/user-accounts")
	@Produces("application/json")
	public Page<UserAccount> getOrganizationUserAccountsPage(
			@PathParam("organization-id") Long organizationId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	public UserAccount getUserAccount(
			@PathParam("user-account-id") Long userAccountId)
		throws Exception {

		return new UserAccount();
	}

	@GET
	@Override
	@Path("/user-accounts")
	@Produces("application/json")
	public Page<UserAccount> getUserAccountsPage(
			@QueryParam("fullnamequery") String fullnamequery,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@GET
	@Override
	@Path("/web-sites/{web-site-id}/user-accounts")
	@Produces("application/json")
	public Page<UserAccount> getWebSiteUserAccountsPage(
			@PathParam("web-site-id") Long webSiteId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Consumes("application/json")
	@Override
	@Path("/user-accounts")
	@POST
	@Produces("application/json")
	public UserAccount postUserAccount(UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	@Consumes("application/json")
	@Override
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@PUT
	public UserAccount putUserAccount(
			@PathParam("user-account-id") Long userAccountId,
			UserAccount userAccount)
		throws Exception {

		return new UserAccount();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		String baseURIString = String.valueOf(contextUriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			BaseUserAccountResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			BaseUserAccountResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected void preparePatch(UserAccount userAccount) {
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(list, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transformToArray(list, unsafeFunction, clazz);
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