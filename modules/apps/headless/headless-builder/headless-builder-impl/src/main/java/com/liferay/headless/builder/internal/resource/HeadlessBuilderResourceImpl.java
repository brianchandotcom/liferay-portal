/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.resource;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.application.endpoint.EndpointMatcher;
import com.liferay.headless.builder.internal.helper.EndpointHelper;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Pagination;

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

	public HeadlessBuilderResourceImpl(
		APIApplication apiApplication, EndpointHelper endpointHelper) {

		_endpointHelper = endpointHelper;

		setApiApplication(apiApplication);
	}

	@GET
	@Path("/{path: .*}")
	@Produces({"application/json", "application/xml"})
	public Response get(
			@QueryParam("filter") String filterString,
			@Context Pagination pagination, @PathParam("path") String path,
			@QueryParam("sort") String sortString)
		throws Exception {

		return _executeEndpoint(
			path, APIApplication.Endpoint.Scope.COMPANY,
			endpoint -> _endpointHelper.getResponseEntityMapsPage(
				_acceptLanguage, _company.getCompanyId(), endpoint,
				filterString, pagination, null, sortString));
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

		return _executeEndpoint(
			path, APIApplication.Endpoint.Scope.GROUP,
			endpoint -> _endpointHelper.getResponseEntityMapsPage(
				_acceptLanguage, _company.getCompanyId(), endpoint,
				filterString, pagination, scopeKey, sortString));
	}

	@GET
	@Path("/{path: .*}/{parameter}")
	@Produces({"application/json", "application/xml"})
	public Response get(
			@PathParam("path") String path,
			@PathParam("parameter") String pathParameterValue)
		throws Exception {

		return _executeEndpoint(
			path + "/" + pathParameterValue,
			APIApplication.Endpoint.Scope.COMPANY,
			endpoint -> _endpointHelper.getResponseEntityMap(
				_company.getCompanyId(), endpoint.getResponseSchema(),
				pathParameterValue));
	}

	public void setApiApplication(APIApplication apiApplication) {
		_apiApplication = apiApplication;
		_endpointMatcher = new EndpointMatcher(apiApplication.getEndpoints());
	}

	private <T> Response _executeEndpoint(
			String path, APIApplication.Endpoint.Scope scope,
			UnsafeFunction<APIApplication.Endpoint, T, Exception>
				successUnsafeFunction)
		throws Exception {

		APIApplication.Endpoint endpoint = _endpointMatcher.getEndpoint(
			"/" + path);

		if ((endpoint == null) || (endpoint.getScope() != scope)) {
			throw new NoSuchModelException(
				String.format(
					"Endpoint /%s does not exist for %s", path,
					_apiApplication.getTitle()));
		}

		if (endpoint.getResponseSchema() == null) {
			return Response.noContent(
			).build();
		}

		return Response.ok(
			successUnsafeFunction.apply(endpoint)
		).build();
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	private APIApplication _apiApplication;

	@Context
	private Company _company;

	private final EndpointHelper _endpointHelper;
	private EndpointMatcher _endpointMatcher;

}