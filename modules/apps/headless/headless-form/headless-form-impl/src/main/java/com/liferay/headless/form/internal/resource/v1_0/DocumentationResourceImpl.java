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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.portal.vulcan.resource.DocumentationResource;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/documentation.properties",
	scope = ServiceScope.PROTOTYPE, service = DocumentationResourceImpl.class
)
@Path("/v1.0")
public class DocumentationResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenApi(
			@Context HttpHeaders headers, @Context UriInfo uriInfo,
			@PathParam("type") String type)
		throws Exception {

		Set<Class<?>> resourceClasses = new HashSet<>();

		resourceClasses.add(FormResourceImpl.class);
		resourceClasses.add(FormDocumentResourceImpl.class);
		resourceClasses.add(FormRecordResourceImpl.class);
		resourceClasses.add(FormStructureResourceImpl.class);

		return _documentationResource.getOpenApi(
			resourceClasses, headers, _servletConfig, _application, uriInfo,
			type);
	}

	@Context
	private Application _application;

	@Reference
	private DocumentationResource _documentationResource;

	@Context
	private ServletConfig _servletConfig;

}