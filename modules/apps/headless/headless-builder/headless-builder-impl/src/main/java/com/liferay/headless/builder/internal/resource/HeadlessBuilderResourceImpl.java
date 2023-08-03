/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.resource;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.internal.helper.EndpointHelper;
import com.liferay.headless.builder.internal.util.PathUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Objects;

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
public class HeadlessBuilderResourceImpl {

	public HeadlessBuilderResourceImpl(EndpointHelper endpointHelper) {
		_endpointHelper = endpointHelper;
	}

	@GET
	@Path("{any: .*}")
	@Produces({"application/json", "application/xml"})
	public Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination)
		throws Exception {

		String endpointPath = StringUtil.removeSubstring(
			PathUtil.removeBasePath(contextHttpServletRequest.getRequestURI()),
			contextAPIApplication.getBaseURL());

		for (APIApplication.Endpoint endpoint :
				contextAPIApplication.getEndpoints()) {

			if ((endpoint.getScope() ==
					APIApplication.Endpoint.Scope.COMPANY) &&
				Objects.equals(endpoint.getPath(), endpointPath)) {

				if (endpoint.getResponseSchema() == null) {
					return Response.noContent(
					).build();
				}

				return Response.ok(
					_endpointHelper.getResponseEntityMapsPage(
						contextCompany.getCompanyId(), endpoint, filterString,
						pagination, null)
				).build();
			}
		}

		throw new NoSuchModelException(
			String.format(
				"Endpoint %s does not exist for %s", endpointPath,
				contextAPIApplication.getTitle()));
	}

	@Context
	protected APIApplication contextAPIApplication;

	@Context
	protected Company contextCompany;

	@Context
	protected HttpServletRequest contextHttpServletRequest;

	private final EndpointHelper _endpointHelper;

}