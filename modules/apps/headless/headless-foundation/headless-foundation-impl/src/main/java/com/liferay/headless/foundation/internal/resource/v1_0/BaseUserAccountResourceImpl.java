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
import com.liferay.headless.foundation.internal.dto.v1_0.UserAccountImpl;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

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

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseUserAccountResourceImpl implements UserAccountResource {

	@Override
	@GET
	@Path("/my-user-accounts/{my-user-account-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public UserAccount getMyUserAccount(
	@PathParam("my-user-account-id") Long myUserAccountId)
			throws Exception {

				return new UserAccountImpl();
	}
	@Override
	@GET
	@Path("/organizations/{organization-id}/user-accounts")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<UserAccount> getOrganizationUserAccountsPage(
	@PathParam("organization-id") Long organizationId,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@GET
	@Path("/user-accounts")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<UserAccount> getUserAccountsPage(
	@QueryParam("fullnamequery") String fullnamequery,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@Consumes("application/json")
	@POST
	@Path("/user-accounts")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public UserAccount postUserAccount(
				UserAccount userAccount)
			throws Exception {

				return new UserAccountImpl();
	}
	@Override
	@DELETE
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public boolean deleteUserAccount(
	@PathParam("user-account-id") Long userAccountId)
			throws Exception {

				return false;
	}
	@Override
	@GET
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public UserAccount getUserAccount(
	@PathParam("user-account-id") Long userAccountId)
			throws Exception {

				return new UserAccountImpl();
	}
	@Override
	@Consumes("application/json")
	@PUT
	@Path("/user-accounts/{user-account-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public UserAccount putUserAccount(
	@PathParam("user-account-id") Long userAccountId,UserAccount userAccount)
			throws Exception {

				return new UserAccountImpl();
	}
	@Override
	@GET
	@Path("/web-sites/{web-site-id}/user-accounts")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<UserAccount> getWebSiteUserAccountsPage(
	@PathParam("web-site-id") Long webSiteId,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

}