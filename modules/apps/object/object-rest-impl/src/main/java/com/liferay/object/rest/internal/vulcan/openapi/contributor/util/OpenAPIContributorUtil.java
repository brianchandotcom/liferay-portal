/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.vulcan.openapi.contributor.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Correa
 */
public class OpenAPIContributorUtil {

	public static void copySchemas(
		String schemaName, Map<String, Schema> sourceSchemas, boolean system,
		OpenAPI targetOpenAPI) {

		if (system) {
			for (String sourceSchemaName : sourceSchemas.keySet()) {
				_copySchema(
					false, sourceSchemaName, sourceSchemas, targetOpenAPI);
			}
		}
		else {
			_copySchema(true, schemaName, sourceSchemas, targetOpenAPI);
			_copySchema(
				true, getPageSchemaName(schemaName), sourceSchemas,
				targetOpenAPI);
		}
	}

	public static String getPageSchemaName(String schemaName) {
		return "Page" + schemaName;
	}

	public static Map<String, Schema> getSystemObjectSchemas(
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

		return openAPIResource.getSchemas(SetUtil.fromArray(object.getClass()));
	}

	private static void _copySchema(
		boolean force, String schemaName, Map<String, Schema> sourceSchemas,
		OpenAPI targetOpenAPI) {

		Components targetComponents = targetOpenAPI.getComponents();

		Map<String, Schema> targetSchemas = targetComponents.getSchemas();

		if (!force && targetSchemas.containsKey(schemaName)) {
			return;
		}

		targetSchemas.put(schemaName, sourceSchemas.get(schemaName));
	}

}