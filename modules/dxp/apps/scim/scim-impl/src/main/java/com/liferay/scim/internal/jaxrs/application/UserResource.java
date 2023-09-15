/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.jaxrs.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	service = Object.class
)
@OpenAPIDefinition(
	info = @Info(description = "SCIM 2.0 /Users endpoint", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"), title = "/Users Endpoint Swagger Definition", version = "1.0")
)
@Path("/v2/Users")
public class UserResource {

	@Consumes("application/scim+json")
	@POST
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response createUser() {
		return null;
	}

	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response deleteUser() {
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response getUser() {
		return null;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response listUsers() {
		return null;
	}

	@Consumes("application/scim+json")
	@Path("/.search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response searchUser() {
		return null;
	}

	@Consumes("application/scim+json")
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	@PUT
	public Response updateUser() {
		return null;
	}

}