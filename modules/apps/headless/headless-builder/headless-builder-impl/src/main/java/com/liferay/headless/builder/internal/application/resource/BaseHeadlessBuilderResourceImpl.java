/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.application.resource;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Luis Miguel Barcos
 */
public abstract class BaseHeadlessBuilderResourceImpl {

	@GET
	@Path("{any: .*}")
	@Produces({"application/json", "application/xml"})
	public abstract Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination)
		throws Exception;

	@Context
	protected APIApplication contextAPIApplication;

	@Context
	protected Company contextCompany;

	@Context
	protected HttpServletRequest contextHttpServletRequest;

}