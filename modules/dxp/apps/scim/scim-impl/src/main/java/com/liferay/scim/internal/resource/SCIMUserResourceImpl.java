/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.resource;

import com.liferay.scim.resource.SCIMUserResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.SCIM.Application)",
		"osgi.jaxrs.resource=true"
	},
	service = SCIMUserResource.class
)
@OpenAPIDefinition(
	info = @Info(description = "SCIM 2.0 /Users endpoint", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"), title = "/Users Endpoint Swagger Definition", version = "1.0")
)
@Path("/v2/Users")
public class SCIMUserResourceImpl implements SCIMUserResource {

	@Consumes("application/scim+json")
	@POST
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response addSCIMUser(String resourceString) {
		return Response.accepted(
		).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response deleteSCIMUser(@PathParam("id") String id) {
		return Response.accepted(
		).build();
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response getSCIMUser(@PathParam("id") String id) {
		return Response.accepted(
		).build();
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response getSCIMUsers(
		@QueryParam("count") int count,
		@QueryParam("startIndex") int startIndex) {

		return Response.accepted(
		).build();
	}

	@Consumes("application/scim+json")
	@Path("/.search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response getSCIMUsers(String resourceString) {
		return Response.accepted(
		).build();
	}

	@Consumes("application/scim+json")
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	@PUT
	public Response updateSCIMUser(
		@PathParam("id") String id, String resourceString) {

		return Response.accepted(
		).build();
	}

}