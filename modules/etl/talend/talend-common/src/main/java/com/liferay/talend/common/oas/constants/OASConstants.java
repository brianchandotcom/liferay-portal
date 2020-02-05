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

package com.liferay.talend.common.oas.constants;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class OASConstants {

	public static final String ADDITIONAL_PROPERTIES = "additionalProperties";

	public static final String FORMAT = "format";

	public static final String INFO = "info";

	public static final String LOCATOR_COMPONENTS_SCHEMAS =
		"components>schemas";

	public static final String LOCATOR_COMPONENTS_SCHEMAS_CLASS_NAME_PATTERN =
		LOCATOR_COMPONENTS_SCHEMAS +
			">SCHEMA_TPL>properties>x-class-name>default";

	public static final String LOCATOR_COMPONENTS_SCHEMAS_PATTERN =
		LOCATOR_COMPONENTS_SCHEMAS + ">SCHEMA_TPL";

	public static final String LOCATOR_ENDPOINT_OPERATION_PARAMETERS_PATTERN =
		"paths>ENDPOINT_TPL>OPERATION_TPL>parameters";

	public static final String LOCATOR_PATHS_PATTERN = "paths>ENDPOINT_TPL";

	public static final String LOCATOR_PROPERTIES_ITEMS_ITEMS =
		"properties>items>items";

	public static final String
		LOCATOR_REQUEST_BODY_CONTENT_APPLICATION_JSON_SCHEMA_PATTERN =
			"paths>ENDPOINT_TPL>OPERATION_TPL>requestBody>content>" +
				"application/json>schema";

	public static final String LOCATOR_RESPONSE_SCHEMA_ITEMS_REFERENCE =
		"responses>default>content>application/json>schema>items>$ref";

	public static final String LOCATOR_RESPONSE_SCHEMA_REFERENCE =
		"responses>default>content>application/json>schema>$ref";

	public static final String
		LOCATOR_RESPONSES_CONTENT_APPLICATION_JSON_SCHEMA_PATTERN =
			"paths>ENDPOINT_TPL>OPERATION_TPL>responses>default>content>" +
				"application/json>schema";

	public static final String OPERATION_DELETE = "delete";

	public static final String OPERATION_GET = "get";

	public static final String OPERATION_PATCH = "patch";

	public static final String OPERATION_POST = "post";

	public static final String PATH_SCHEMA_REFERENCE = "#/components/schemas/";

	public static final String PATHS = "paths";

	public static final String PROPERTIES = "properties";

	public static final String REF = "$ref";

	public static final String REQUIRED = "required";

	public static final String TYPE = "type";

	public static final String VERSION = "version";

	private OASConstants() {
	}

}