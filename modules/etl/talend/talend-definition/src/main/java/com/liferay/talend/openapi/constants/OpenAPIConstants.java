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

package com.liferay.talend.openapi.constants;

/**
 * @author Zoltán Takács
 */
public class OpenAPIConstants {

	public static final String ADDITIONAL_PROPERTIES = "additionalProperties";

	public static final String APPLICATION_JSON = "application/json";

	public static final String COMPONENTS = "components";

	public static final String CONTENT = "content";

	public static final String DEFAULT = "default";

	public static final String FORMAT = "format";

	public static final String ITEMS = "items";

	public static final String PATH_RESPONSE_SCHEMA_ITEMS_REFERENCE =
		"responses>default>content>application/json>schema>items>$ref";

	public static final String PATH_RESPONSE_SCHEMA_REFERENCE =
		"responses>default>content>application/json>schema>$ref";

	public static final String PATH_SCHEMA_REFERENCE = "#/components/schemas/";

	public static final String PATHS = "paths";

	public static final String PROPERTIES = "properties";

	public static final String REF = "$ref";

	public static final String REQUEST_BODY = "requestBody";

	public static final String REQUIRED = "required";

	public static final String RESPONSES = "responses";

	public static final String SCHEMA = "schema";

	public static final String SCHEMAS = "schemas";

	public static final String TYPE = "type";

	private OpenAPIConstants() {
	}

}