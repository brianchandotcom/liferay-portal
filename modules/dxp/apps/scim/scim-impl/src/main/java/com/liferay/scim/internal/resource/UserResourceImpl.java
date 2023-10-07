/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.resource;

import com.liferay.scim.resource.UserResource;

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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.SCIM.Application)",
		"osgi.jaxrs.resource=true"
	},
	service = UserResource.class
)
@OpenAPIDefinition(
	info = @Info(description = "SCIM 2.0 /Users endpoint", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"), title = "/Users Endpoint Swagger Definition", version = "1.0")
)
@Path("/v2/Users")
public class UserResourceImpl implements UserResource {

	@Consumes("application/scim+json")
	@POST
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response createUser(String resourceString) {
		return _userResource.createUser(resourceString);
	}

	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response deleteUser(@PathParam("id") String id) {
		return _userResource.deleteUser(id);
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response getUser(@PathParam("id") String id) {
		return _userResource.getUser(id);
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response listUsers(
		@QueryParam("count") int count,
		@QueryParam("startIndex") int startIndex) {

		return _userResource.listUsers(count, startIndex);
	}

	@Consumes("application/scim+json")
	@Path("/.search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	public Response searchUser(String resourceString) {
		return _userResource.searchUser(resourceString);
	}

	@Consumes("application/scim+json")
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, "application/scim+json"})
	@PUT
	public Response updateUser(
		@PathParam("id") String id, String resourceString) {

		return _userResource.updateUser(id, resourceString);
	}

	@Reference
	private UserResource _userResource;

}