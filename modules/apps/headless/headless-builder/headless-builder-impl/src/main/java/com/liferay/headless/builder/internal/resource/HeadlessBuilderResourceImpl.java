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
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Objects;

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
	@Path("/{path: .*}")
	@Produces({"application/json", "application/xml"})
	public Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination, @PathParam("path") String path,
			@QueryParam("sort") String sortString)
		throws Exception {

		return _get(
			filterString, pagination, path,
			APIApplication.Endpoint.Scope.COMPANY, null, sortString);
	}

	@GET
	@Path(HeadlessBuilderConstants.BASE_PATH_SCOPES_SUFFIX + "/{path: .*}")
	@Produces({"application/json", "application/xml"})
	public Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination, @PathParam("path") String path,
			@PathParam("scopeKey") String scopeKey,
			@QueryParam("sort") String sortString)
		throws Exception {

		return _get(
			filterString, pagination, path, APIApplication.Endpoint.Scope.GROUP,
			scopeKey, sortString);
	}

	private Response _get(
			String filterString, Pagination pagination, String path,
			APIApplication.Endpoint.Scope scope, String scopeKey,
			String sortString)
		throws Exception {

		for (APIApplication.Endpoint endpoint :
				_apiApplication.getEndpoints()) {

			if ((endpoint.getScope() != scope) ||
				!Objects.equals(endpoint.getPath(), "/" + path)) {

				continue;
			}

			if (endpoint.getResponseSchema() == null) {
				return Response.noContent(
				).build();
			}

			return Response.ok(
				_endpointHelper.getResponseEntityMapsPage(
					_acceptLanguage, _company.getCompanyId(), endpoint,
					filterString, pagination, scopeKey, sortString)
			).build();
		}

		throw new NoSuchModelException(
			String.format(
				"Endpoint /%s does not exist for %s", path,
				_apiApplication.getTitle()));
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Context
	private APIApplication _apiApplication;

	@Context
	private Company _company;

	private final EndpointHelper _endpointHelper;

}