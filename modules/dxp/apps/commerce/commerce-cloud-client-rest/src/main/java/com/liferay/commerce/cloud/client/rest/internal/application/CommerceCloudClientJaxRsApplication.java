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

package com.liferay.commerce.cloud.client.rest.internal.application;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@ApplicationPath("/commerce-cloud-client")
@Component(
	configurationPid = CommerceCloudClientConstants.CONFIGURATION_PID,
	immediate = true, service = Application.class
)
public class CommerceCloudClientJaxRsApplication extends Application {

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/{projectId}/forecasts")
	@POST
	public Response addCommerceForecastEntries(
			@PathParam("projectId") String projectId, final String json)
		throws IOException {

		if (Validator.isNull(_projectId)) {
			_log.error("Project ID is not configured properly");

			return Response.serverError().build();
		}

		if (!_projectId.equals(projectId)) {
			_log.error("Project ID \"" + projectId + "\" does not match");

			return Response.status(Status.FORBIDDEN).build();
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_commerceCloudClient.addCommerceForecastEntries(json);

						return null;
					}

				});
		}
		catch (Throwable t) {
			_log.error("Unable to add forecast entries from JSON " + json, t);

			ResponseBuilder responseBuilder = null;

			if (t instanceof CommerceCloudClientException) {
				responseBuilder = Response.status(Status.BAD_REQUEST);
			}
			else {
				responseBuilder = Response.serverError();
			}

			return responseBuilder.build();
		}

		return Response.ok().build();
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CommerceCloudClientConfiguration commerceCloudClientConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceCloudClientConfiguration.class, properties);

		_projectId = commerceCloudClientConfiguration.projectId();
	}

	@Deactivate
	protected void deactivate() {
		_projectId = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCloudClientJaxRsApplication.class);

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Reference
	private CommerceCloudClient _commerceCloudClient;

	private volatile String _projectId;

}