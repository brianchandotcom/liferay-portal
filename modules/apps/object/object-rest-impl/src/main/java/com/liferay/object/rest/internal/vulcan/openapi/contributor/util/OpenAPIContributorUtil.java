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

package com.liferay.object.rest.internal.vulcan.openapi.contributor.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Map;

import javax.ws.rs.core.Response;

/**
 * @author Carlos Correa
 */
public class OpenAPIContributorUtil {

	public static void copySchemas(
		ObjectDefinition objectDefinition, OpenAPI sourceOpenAPI,
		OpenAPI targetOpenAPI) {

		_copySchema(
			getSchemaName(objectDefinition), sourceOpenAPI, targetOpenAPI);
		_copySchema(
			getPageSchemaName(objectDefinition), sourceOpenAPI, targetOpenAPI);
	}

	public static OpenAPI getObjectEntryOpenAPI(
			ObjectDefinition objectDefinition,
			ObjectEntryOpenAPIResource objectEntryOpenAPIResource)
		throws Exception {

		Response response = objectEntryOpenAPIResource.getOpenAPI(
			objectDefinition, "json", null);

		return (OpenAPI)response.getEntity();
	}

	public static String getPageSchemaName(ObjectDefinition objectDefinition) {
		return "Page" + getSchemaName(objectDefinition);
	}

	public static String getSchemaName(ObjectDefinition objectDefinition) {
		return objectDefinition.getShortName();
	}

	private static void _copySchema(
		String schemaName, OpenAPI sourceOpenAPI, OpenAPI targetOpenAPI) {

		Components components = sourceOpenAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		targetOpenAPI.schema(schemaName, schemas.get(schemaName));
	}

}