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
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Items;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author Peter Shin
 */
public class DTOOpenAPIParser {

	public static Map<String, Schema> getEnumSchemas(
		OpenAPIYAML openAPIYAML, Schema schema) {

		Map<String, Schema> propertySchemas = schema.getPropertySchemas();

		if (propertySchemas == null) {
			return Collections.emptyMap();
		}

		Map<String, Schema> enumSchemas = new TreeMap<>();

		for (Map.Entry<String, Schema> entry : propertySchemas.entrySet()) {
			Schema propertySchema = entry.getValue();

			List<String> enumValues = propertySchema.getEnumValues();

			if ((enumValues != null) && !enumValues.isEmpty()) {
				String propertySchemaName = entry.getKey();

				enumSchemas.put(
					_getEnumName(openAPIYAML, propertySchemaName),
					propertySchema);
			}
		}

		return enumSchemas;
	}

	public static Map<String, String> getProperties(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Schema schema) {

		Map<String, String> javaDataTypeMap =
			OpenAPIParserUtil.getJavaDataTypeMap(configYAML, openAPIYAML);
		Map<String, String> properties = new TreeMap<>();

		Map<String, Schema> propertySchemas = _getPropertySchemas(schema);

		for (Map.Entry<String, Schema> entry : propertySchemas.entrySet()) {
			String propertySchemaName = entry.getKey();
			Schema propertySchema = entry.getValue();

			String propertyName = _getPropertyName(
				propertySchema, propertySchemaName);
			String propertyType = _getPropertyType(
				javaDataTypeMap, openAPIYAML, propertySchema,
				propertySchemaName);

			properties.put(propertyName, propertyType);
		}

		return properties;
	}

	public static Map<String, String> getProperties(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);

		Schema schema = schemas.get(schemaName);

		return getProperties(configYAML, openAPIYAML, schema);
	}

	public static Schema getPropertySchema(String propertyName, Schema schema) {
		Map<String, Schema> propertySchemas = _getPropertySchemas(schema);

		for (Map.Entry<String, Schema> entry : propertySchemas.entrySet()) {
			String propertySchemaName = entry.getKey();
			Schema propertySchema = entry.getValue();

			String curPropertyName = _getPropertyName(
				propertySchema, propertySchemaName);

			if (StringUtil.equalsIgnoreCase(curPropertyName, propertyName)) {
				return propertySchema;
			}
		}

		return null;
	}

	public static boolean isSchemaProperty(
		OpenAPIYAML openAPIYAML, String propertyName, Schema schema) {

		Map<String, Schema> propertySchemas = _getPropertySchemas(schema);

		for (Map.Entry<String, Schema> entry : propertySchemas.entrySet()) {
			String propertySchemaName = entry.getKey();

			if (propertySchemaName.equals(propertyName)) {
				if (_isSchema(entry.getValue())) {
					return true;
				}

				return false;
			}
		}

		return false;
	}

	private static String _getEnumName(
		OpenAPIYAML openAPIYAML, String propertySchemaName) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);

		for (String schemaName : schemas.keySet()) {
			if (propertySchemaName.length() <= schemaName.length()) {
				continue;
			}

			if (StringUtil.startsWith(
					StringUtil.toLowerCase(propertySchemaName),
					StringUtil.toLowerCase(schemaName))) {

				String suffix = propertySchemaName.substring(
					schemaName.length());

				if (Character.isUpperCase(suffix.charAt(0))) {
					return schemaName + suffix;
				}
			}
		}

		return StringUtil.upperCaseFirstLetter(propertySchemaName);
	}

	private static String _getPropertyName(
		Schema propertySchema, String propertySchemaName) {

		String name = CamelCaseUtil.toCamelCase(propertySchemaName);

		if (StringUtil.equalsIgnoreCase(propertySchema.getType(), "object") &&
			(propertySchema.getItems() != null)) {

			return OpenAPIUtil.formatSingular(name);
		}

		return name;
	}

	private static Map<String, Schema> _getPropertySchemas(Schema schema) {
		Map<String, Schema> propertySchemas = null;

		Items items = schema.getItems();

		if (items != null) {
			propertySchemas = items.getPropertySchemas();
		}
		else if (schema.getAllOfSchemas() != null) {
			propertySchemas = OpenAPIParserUtil.getAllOfPropertySchemas(schema);
		}
		else {
			propertySchemas = schema.getPropertySchemas();
		}

		if (propertySchemas == null) {
			return Collections.emptyMap();
		}

		return propertySchemas;
	}

	private static String _getPropertyType(
		Map<String, String> javaDataTypeMap, OpenAPIYAML openAPIYAML,
		Schema propertySchema, String propertySchemaName) {

		List<String> enumValues = propertySchema.getEnumValues();

		if ((enumValues != null) && !enumValues.isEmpty()) {
			return _getEnumName(openAPIYAML, propertySchemaName);
		}

		Items items = propertySchema.getItems();
		String type = propertySchema.getType();

		if (StringUtil.equals(type, "array") && (items != null) &&
			StringUtil.equalsIgnoreCase(items.getType(), "object")) {

			String name = OpenAPIUtil.formatSingular(
				StringUtil.upperCaseFirstLetter(propertySchemaName));

			if (javaDataTypeMap.containsKey(name)) {
				return name + "[]";
			}
		}

		if (StringUtil.equalsIgnoreCase(type, "object") &&
			((propertySchema.getAdditionalPropertySchema() == null) ||
			 _isEmpty(propertySchema.getAdditionalPropertySchema()))) {

			String name = StringUtil.upperCaseFirstLetter(propertySchemaName);

			if (items != null) {
				name = OpenAPIUtil.formatSingular(name);
			}

			if (javaDataTypeMap.containsKey(name)) {
				return name;
			}
		}

		String javaDataType = OpenAPIParserUtil.getJavaDataType(
			javaDataTypeMap, propertySchema);

		if (javaDataType.startsWith("[")) {
			String name = OpenAPIParserUtil.getElementClassName(javaDataType);

			if (name.lastIndexOf('.') != -1) {
				name = name.substring(name.lastIndexOf(".") + 1);
			}

			return name + "[]";
		}

		if (javaDataType.startsWith("Map")) {
			int index = javaDataType.lastIndexOf(".");

			if (index != -1) {
				String mapType = javaDataType.substring(
					0, javaDataType.lastIndexOf(" "));

				return mapType + javaDataType.substring(index + 1);
			}

			return "Map<String, ?>";
		}

		String propertyType = javaDataType;

		if (propertyType.lastIndexOf('.') != -1) {
			propertyType = propertyType.substring(
				propertyType.lastIndexOf(".") + 1);
		}

		return propertyType;
	}

	private static boolean _isEmpty(Schema schema) {
		if ((schema.getAdditionalPropertySchema() == null) &&
			(schema.getAllOfSchemas() == null) &&
			(schema.getAnyOfSchemas() == null) && (schema.getItems() == null) &&
			(schema.getOneOfSchemas() == null) &&
			(schema.getPropertySchemas() == null) &&
			(schema.getReference() == null) && (schema.getType() == null)) {

			return true;
		}

		return false;
	}

	private static boolean _isObject(Schema schema, String type) {
		if (Objects.equals("object", type) &&
			(schema.getAdditionalPropertySchema() == null)) {

			return true;
		}

		return false;
	}

	private static boolean _isSchema(Schema schema) {
		Items items = schema.getItems();

		if (_isObject(schema, schema.getType()) ||
			(schema.getAllOfSchemas() != null) ||
			(schema.getReference() != null) ||
			((items != null) &&
			 (_isObject(schema, items.getType()) ||
			  (items.getReference() != null)))) {

			return true;
		}

		return false;
	}

}