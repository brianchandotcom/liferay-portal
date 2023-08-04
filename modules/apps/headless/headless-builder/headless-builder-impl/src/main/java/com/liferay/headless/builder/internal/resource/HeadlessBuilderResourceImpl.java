/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.resource;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.helper.EndpointHelper;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	@Path(_COMPANY_SCOPED_PATH_REGEX)
	@Produces({"application/json", "application/xml"})
	public Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination, @PathParam("path") String path)
		throws Exception {

		return _get(
			filterString, pagination, path,
			APIApplication.Endpoint.Scope.COMPANY, null);
	}

	@GET
	@Path(_SITE_SCOPED_PATH_REGEX)
	@Produces({"application/json", "application/xml"})
	public Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination, @PathParam("path") String path,
			@PathParam("scopeKey") String scopeKey)
		throws Exception {

		return _get(
			filterString, pagination, path, APIApplication.Endpoint.Scope.GROUP,
			scopeKey);
	}

	@Context
	protected APIApplication contextAPIApplication;

	@Context
	protected Company contextCompany;

	@Context
	protected HttpServletRequest contextHttpServletRequest;

	private Response _get(
			String filterString, Pagination pagination, String path,
			APIApplication.Endpoint.Scope scope, String scopeKey)
		throws Exception {

		for (APIApplication.Endpoint endpoint :
				contextAPIApplication.getEndpoints()) {

			if ((endpoint.getScope() == scope) &&
				Objects.equals(endpoint.getPath(), "/" + path)) {

				if (endpoint.getResponseSchema() == null) {
					return Response.noContent(
					).build();
				}

				return Response.ok(
					_endpointHelper.getResponseEntityMapsPage(
						contextCompany.getCompanyId(), endpoint, filterString,
						pagination, scopeKey)
				).build();
			}
		}

		throw new NoSuchModelException(
			String.format(
				"Endpoint /%s does not exist for %s", path,
				contextAPIApplication.getTitle()));
	}

	private static final String _COMPANY_SCOPED_PATH_REGEX = "/{path: .*}";

	private static final String _SITE_SCOPED_PATH_REGEX =
		HeadlessBuilderConstants.SITE_SCOPED_BASE_PATH + "/{path: .*}";

	private final EndpointHelper _endpointHelper;

}