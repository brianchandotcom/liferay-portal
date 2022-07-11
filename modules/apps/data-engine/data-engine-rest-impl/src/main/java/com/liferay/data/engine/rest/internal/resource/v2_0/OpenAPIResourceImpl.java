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

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionFieldLink;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashMap;
import java.util.Map;

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
 * @author Jeyvison Nascimento
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.data.engine.rest.client', and version '3.0.15'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Data Engine", version = "v2.0")
)
@Path("/v2.0")
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
				"getOpenAPI", long.class, Map.class, String.class,
				UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses.keySet(), type);
		}

		return _openAPIResource.getOpenAPI(
			_company.getCompanyId(), _resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Map<Class<?>, Class<?>> _resourceClasses =
		new HashMap<Class<?>, Class<?>>() {
			{
				put(
					DataDefinitionFieldLinkResourceImpl.class,
					DataDefinitionFieldLink.class);
				put(DataDefinitionResourceImpl.class, DataDefinition.class);
				put(DataLayoutResourceImpl.class, DataLayout.class);
				put(DataListViewResourceImpl.class, DataListView.class);
				put(
					DataRecordCollectionResourceImpl.class,
					DataRecordCollection.class);
				put(DataRecordResourceImpl.class, DataRecord.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}