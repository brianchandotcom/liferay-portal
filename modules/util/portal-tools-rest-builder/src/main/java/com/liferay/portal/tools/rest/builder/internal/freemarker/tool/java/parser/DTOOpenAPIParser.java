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

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser;

import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class DTOOpenAPIParser {

	public static List<JavaMethodParameter> getJavaMethodParameters(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Schema schema) {

		Map<String, Schema> propertySchemas = null;

		Items items = schema.getItems();

		if (items != null) {
			propertySchemas = items.getPropertySchemas();
		}
		else {
			propertySchemas = schema.getPropertySchemas();
		}

		if (propertySchemas == null) {
			return Collections.emptyList();
		}

		Map<String, String> javaDataTypeMap =
			OpenAPIParserUtil.getJavaDataTypeMap(configYAML, openAPIYAML);
		List<JavaMethodParameter> javaMethodParameters = new ArrayList<>(
			propertySchemas.size());

		for (Map.Entry<String, Schema> entry : propertySchemas.entrySet()) {
			String propertySchemaName = entry.getKey();
			Schema propertySchema = entry.getValue();

			String parameterName = CamelCaseUtil.toCamelCase(
				propertySchemaName);
			String parameterType = _getParameterType(
				javaDataTypeMap, propertySchema, propertySchemaName);

			javaMethodParameters.add(
				new JavaMethodParameter(parameterName, parameterType));
		}

		return javaMethodParameters;
	}

	public static List<JavaMethodParameter> getJavaMethodParameters(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);

		return getJavaMethodParameters(
			configYAML, openAPIYAML, schemas.get(schemaName));
	}

	private static String _getParameterType(
		Map<String, String> javaDataTypeMap, Schema propertySchema,
		String propertySchemaName) {

		Items items = propertySchema.getItems();
		String type = propertySchema.getType();

		if (StringUtil.equals(type, "array") && (items != null) &&
			StringUtil.equalsIgnoreCase(items.getType(), "object")) {

			return StringUtil.upperCaseFirstLetter(propertySchemaName) + "[]";
		}

		if (StringUtil.equalsIgnoreCase(type, "object")) {
			return StringUtil.upperCaseFirstLetter(propertySchemaName);
		}

		return OpenAPIParserUtil.getJavaDataType(
			javaDataTypeMap, propertySchema);
	}

}