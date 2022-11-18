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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Correa
 */
public class OpenAPIContributorUtil {

	public static void copySchemas(
		String schemaName, OpenAPI sourceOpenAPI, boolean system,
		OpenAPI targetOpenAPI) {

		if (system) {
			Components sourceComponents = sourceOpenAPI.getComponents();

			Map<String, Schema> sourceSchemas = sourceComponents.getSchemas();

			for (String sourceSchemaName : sourceSchemas.keySet()) {
				_copySchema(
					false, sourceSchemaName, sourceOpenAPI, targetOpenAPI);
			}
		}
		else {
			_copySchema(true, schemaName, sourceOpenAPI, targetOpenAPI);
			_copySchema(
				true, getPageSchemaName(schemaName), sourceOpenAPI,
				targetOpenAPI);
		}
	}

	public static OpenAPI getObjectEntryOpenAPI(
			ObjectDefinition objectDefinition,
			ObjectEntryOpenAPIResource objectEntryOpenAPIResource)
		throws Exception {

		Response response = objectEntryOpenAPIResource.getOpenAPI(
			objectDefinition, "json", null);

		return (OpenAPI)response.getEntity();
	}

	public static String getPageSchemaName(String schemaName) {
		return "Page" + schemaName;
	}

	public static OpenAPI getSystemObjectOpenAPI(
			BundleContext bundleContext, String externalDTOClassName,
			OpenAPIResource openAPIResource)
		throws Exception {

		ServiceReference[] serviceReferences =
			bundleContext.getServiceReferences(
				(String)null,
				"(&(entity.class.name=" + externalDTOClassName +
					")(osgi.jaxrs.resource=true))");

		if (ArrayUtil.isEmpty(serviceReferences)) {
			throw new IllegalStateException();
		}

		Object object = bundleContext.getService(serviceReferences[0]);

		Response response = openAPIResource.getOpenAPI(
			null, SetUtil.fromArray(object.getClass()), "json", null);

		return (OpenAPI)response.getEntity();
	}

	private static void _copySchema(
		boolean force, String schemaName, OpenAPI sourceOpenAPI,
		OpenAPI targetOpenAPI) {

		Components targetComponents = targetOpenAPI.getComponents();

		Map<String, Schema> targetSchemas = targetComponents.getSchemas();

		if (!force && targetSchemas.containsKey(schemaName)) {
			return;
		}

		Components components = sourceOpenAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		targetSchemas.put(schemaName, schemas.get(schemaName));
	}

}