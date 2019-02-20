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

import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.internal.dto.v1_0.OrganizationImpl;
import com.liferay.headless.foundation.resource.v1_0.OrganizationResource;
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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseOrganizationResourceImpl implements OrganizationResource {

	@Override
	@GET
	@Path("/my-user-accounts/{my-user-account-id}/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getMyUserAccountOrganizationsPage(
	@PathParam("my-user-account-id") Long myUserAccountId,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@GET
	@Path("/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getOrganizationsPage(
	@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@GET
	@Path("/organizations/{organization-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Organization getOrganization(
	@PathParam("organization-id") Long organizationId)
			throws Exception {

				return new OrganizationImpl();
	}
	@Override
	@GET
	@Path("/organizations/{organization-id}/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getOrganizationOrganizationsPage(
	@PathParam("organization-id") Long organizationId,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@GET
	@Path("/user-accounts/{user-account-id}/organizations")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Organization> getUserAccountOrganizationsPage(
	@PathParam("user-account-id") Long userAccountId,@Context Pagination pagination)
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