/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.bom.internal.resource.v1_0;

import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "Liferay Commerce BOM API", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Liferay Commerce BOM API", version = "1.0")
)
@Path("/1.0")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		try {
			Class<? extends OpenAPIResource> clazz =
				_openAPIResource.getClass();

			clazz.getMethod(
				"getOpenAPI", Set.class, String.class, UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses, type);
		}

		return _openAPIResource.getOpenAPI(_resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Set<Class<?>> _resourceClasses = new HashSet<Class<?>>() {
		{
			add(AreaResourceImpl.class);

			add(FolderResourceImpl.class);

			add(ProductResourceImpl.class);

			add(SpotResourceImpl.class);

			add(OpenAPIResourceImpl.class);
		}
	};

}