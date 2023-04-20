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

package com.liferay.headless.builder.internal.resource;

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.operation.Operation;
import com.liferay.headless.builder.internal.operation.OperationRegistry;
import com.liferay.headless.builder.internal.operation.handler.OperationHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carlos Correa
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Builder.Application)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = HeadlessBuilderResource.class
)
public class HeadlessBuilderResource extends BaseHeadlessBuilderResource {

	@GET
	@Path("{any: .*}")
	@Produces({"application/json", "application/xml"})
	public Response get() throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-171047")) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		Operation operation = _getOperation(
			_portal.getCompanyId(contextHttpServletRequest),
			contextHttpServletRequest.getMethod(),
			contextHttpServletRequest.getRequestURI());

		if (operation == null) {
			return Response.status(
				Response.Status.NOT_FOUND
			).entity(
				new Problem(
					Response.Status.NOT_FOUND,
					"The operation could not be found")
			).build();
		}

		OperationHandler operationHandler = _serviceTrackerMap.getService(
			operation.getOperationType());

		return operationHandler.handle(contextHttpServletRequest, operation);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, OperationHandler.class,
			HeadlessBuilderConstants.OPERATION_NAME);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Operation _getOperation(
		long companyId, String method, String path) {

		for (Operation operation : _operationRegistry.getOperations()) {
			if ((companyId != operation.getCompanyId()) ||
				!Objects.equals(operation.getMethod(), method)) {

				continue;
			}

			Operation.PathConfiguration pathConfiguration =
				operation.getPathConfiguration();

			Pattern pattern = pathConfiguration.getPattern();

			Matcher matcher = pattern.matcher(path);

			if (matcher.matches()) {
				return operation;
			}
		}

		return null;
	}

	@Reference
	private OperationRegistry _operationRegistry;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, OperationHandler> _serviceTrackerMap;

}