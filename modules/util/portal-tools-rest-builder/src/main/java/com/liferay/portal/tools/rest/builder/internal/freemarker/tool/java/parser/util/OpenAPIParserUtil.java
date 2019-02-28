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

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class OpenAPIParserUtil {

	public static String getArguments(
		List<JavaMethodParameter> javaMethodParameters) {

		StringBuilder sb = new StringBuilder();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			sb.append(javaMethodParameter.getParameterName());
			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	public static Schema getComponentSchema(
		OpenAPIYAML openAPIYAML, String reference) {

		if ((reference == null) ||
			!reference.startsWith("#/components/schemas/")) {

			return null;
		}

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		return schemas.get(getComponentType(reference));
	}

	public static String getComponentType(String reference) {
		int index = reference.lastIndexOf('/');

		if (index == -1) {
			return reference;
		}

		return reference.substring(index + 1);
	}

	public static String getHTTPMethod(Operation operation) {
		Class<? extends Operation> clazz = operation.getClass();

		return StringUtil.lowerCase(clazz.getSimpleName());
	}

	public static String getJavaMethodParameterType(
		String propertySchemaName, Schema schema) {

		Items items = schema.getItems();
		String type = schema.getType();

		if (StringUtil.equals(type, "array") && (items != null)) {
			if (items.getType() != null) {
				String itemsFormat = items.getFormat();
				String itemsType = items.getType();

				String javaDataType = _getJavaDataType(
					itemsType, itemsFormat, propertySchemaName);

				return javaDataType + "[]";
			}

			if (items.getReference() != null) {
				return getComponentType(items.getReference()) + "[]";
			}

			if (items.getPropertySchemas() != null) {
				return StringUtil.upperCaseFirstLetter(propertySchemaName);
			}
		}

		if (type != null) {
			return _getJavaDataType(
				type, schema.getFormat(), propertySchemaName);
		}

		List<Schema> allOfSchemas = schema.getAllOfSchemas();

		if (allOfSchemas != null) {
			for (Schema allOfSchema : allOfSchemas) {
				if (Validator.isNotNull(allOfSchema.getReference())) {
					return getComponentType(allOfSchema.getReference());
				}
			}
		}

		if ((schema.getAnyOfSchemas() != null) ||
			(schema.getOneOfSchemas() != null)) {

			return "Object";
		}

		return getComponentType(schema.getReference());
	}

	public static String getParameter(
		JavaMethodParameter javaMethodParameter, String parameterAnnotation) {

		StringBuilder sb = new StringBuilder();

		if (Validator.isNotNull(parameterAnnotation)) {
			sb.append(parameterAnnotation);
			sb.append(' ');
		}

		sb.append(javaMethodParameter.getParameterType());
		sb.append(' ');
		sb.append(javaMethodParameter.getParameterName());

		return sb.toString();
	}

	public static Set<String> getSchemaNames(
		List<JavaMethodSignature> javaMethodSignatures) {

		Set<String> schemaNames = new TreeSet<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			schemaNames.add(javaMethodSignature.getSchemaName());
		}

		return schemaNames;
	}

	public static String getSimpleClassName(String type) {
		if (type.endsWith("[]")) {
			return type.substring(0, type.length() - 2);
		}

		if (type.endsWith(">")) {
			return type.substring(0, type.indexOf("<"));
		}

		return type;
	}

	public static boolean hasHTTPMethod(
		JavaMethodSignature javaMethodSignature, String... httpMethods) {

		Operation operation = javaMethodSignature.getOperation();

		for (String httpMethod : httpMethods) {
			if (Objects.equals(httpMethod, getHTTPMethod(operation))) {
				return true;
			}
		}

		return false;
	}

	public static boolean isSchemaParameter(
		JavaMethodParameter javaMethodParameter, OpenAPIYAML openAPIYAML) {

		String simpleClassName = getSimpleClassName(
			javaMethodParameter.getParameterType());

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);

		if (schemas.containsKey(simpleClassName)) {
			return true;
		}

		return false;
	}

	public static String merge(Collection<String> c, Character delimiter) {
		StringBuilder sb = new StringBuilder();

		for (String s : c) {
			sb.append(s);
			sb.append(delimiter);
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	public static List<JavaMethodParameter>
		toFullyQualifiedJavaMethodParameters(
			ConfigYAML configYAML,
			List<JavaMethodParameter> javaMethodParameters,
			OpenAPIYAML openAPIYAML) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);
		List<JavaMethodParameter> newJavaMethodParameters = new ArrayList<>();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			String parameterType = _toFullyQualifiedType(
				configYAML, openAPIYAML, schemas.keySet(),
				javaMethodParameter.getParameterType());

			JavaMethodParameter newJavaMethodParameter =
				new JavaMethodParameter(
					javaMethodParameter.getParameterName(), parameterType);

			newJavaMethodParameters.add(newJavaMethodParameter);
		}

		return newJavaMethodParameters;
	}

	public static List<JavaMethodSignature>
		toFullyQualifiedJavaMethodSignatures(
			ConfigYAML configYAML,
			List<JavaMethodSignature> javaMethodSignatures,
			OpenAPIYAML openAPIYAML) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);
		List<JavaMethodSignature> newJavaMethodSignatures = new ArrayList<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			List<JavaMethodParameter> javaMethodParameters =
				toFullyQualifiedJavaMethodParameters(
					configYAML, javaMethodSignature.getJavaMethodParameters(),
					openAPIYAML);
			String returnType = _toFullyQualifiedType(
				configYAML, openAPIYAML, schemas.keySet(),
				javaMethodSignature.getReturnType());

			JavaMethodSignature newJavaMethodSignature =
				new JavaMethodSignature(
					javaMethodSignature.getPath(),
					javaMethodSignature.getPathItem(),
					javaMethodSignature.getOperation(),
					javaMethodSignature.getSchemaName(), javaMethodParameters,
					javaMethodSignature.getMethodName(), returnType);

			newJavaMethodSignatures.add(newJavaMethodSignature);
		}

		return newJavaMethodSignatures;
	}

	private static String _getJavaDataType(
		String type, String format, String propertySchemaName) {

		if (StringUtil.equals(format, "date-time") &&
			StringUtil.equals(type, "string")) {

			return "Date";
		}

		if (StringUtil.equals(format, "int64") &&
			StringUtil.equals(type, "integer")) {

			return "Long";
		}

		if (StringUtil.equalsIgnoreCase(type, "object") &&
			(propertySchemaName != null)) {

			return StringUtil.upperCaseFirstLetter(propertySchemaName);
		}

		return StringUtil.upperCaseFirstLetter(type);
	}

	private static String _toFullyQualifiedClassName(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Set<String> schemaNames,
		String simpleClassName) {

		String apiPackagePath = configYAML.getApiPackagePath();
		String escapedVersion = OpenAPIUtil.escapeVersion(openAPIYAML);

		for (String schemaName : schemaNames) {
			if (Objects.equals(simpleClassName, schemaName)) {
				StringBuilder sb = new StringBuilder();

				sb.append(apiPackagePath);
				sb.append(".dto.");
				sb.append(escapedVersion);
				sb.append(".");
				sb.append(schemaName);

				return sb.toString();
			}

			if (Objects.equals(simpleClassName, schemaName + "Impl")) {
				StringBuilder sb = new StringBuilder();

				sb.append(apiPackagePath);
				sb.append(".dto.");
				sb.append(escapedVersion);
				sb.append(".");
				sb.append(schemaName);
				sb.append("Impl");

				return sb.toString();
			}
		}

		for (String portalClassName : _CLASS_NAMES) {
			if (portalClassName.endsWith("." + simpleClassName)) {
				return portalClassName;
			}
		}

		for (String packageName : new String[] {"java.lang", "java.util"}) {
			try {
				Class.forName(packageName + "." + simpleClassName);
			}
			catch (ClassNotFoundException cnfe) {
				continue;
			}

			return packageName + "." + simpleClassName;
		}

		return simpleClassName;
	}

	private static String _toFullyQualifiedType(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Set<String> schemaNames,
		String type) {

		if (type.endsWith("[]")) {
			String className = _toFullyQualifiedClassName(
				configYAML, openAPIYAML, schemaNames,
				type.substring(0, type.length() - 2));

			return className + "[]";
		}
		else if (type.endsWith(">")) {
			StringBuilder sb = new StringBuilder();

			String dataClassName = _toFullyQualifiedClassName(
				configYAML, openAPIYAML, schemaNames,
				type.substring(0, type.indexOf("<")));

			sb.append(dataClassName);

			sb.append("<");

			String genericClassName = _toFullyQualifiedClassName(
				configYAML, openAPIYAML, schemaNames,
				type.substring(type.indexOf("<") + 1, type.indexOf(">")));

			sb.append(genericClassName);

			sb.append(">");

			return sb.toString();
		}

		return _toFullyQualifiedClassName(
			configYAML, openAPIYAML, schemaNames, type);
	}

	private static final String[] _CLASS_NAMES = {
		"com.liferay.portal.vulcan.pagination.Page",
		"com.liferay.portal.vulcan.pagination.Pagination"
	};

	private static final Map<Map.Entry<String, String>, String>
		_openAPIDataTypeMap = new HashMap<Map.Entry<String, String>, String>() {
			{

				// https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md#dataTypes

				put(
					new AbstractMap.SimpleImmutableEntry<>("boolean", null),
					Boolean.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("integer", "int32"),
					Integer.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("integer", "int64"),
					Long.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("number", "float"),
					Float.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("number", "double"),
					Double.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", null),
					String.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", "byte"),
					String.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", "binary"),
					String.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", "date"),
					Date.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>(
						"string", "date-time"),
					Date.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>(
						"string", "password"),
					String.class.getName());

				// Liferay

				put(
					new AbstractMap.SimpleImmutableEntry<>("number", null),
					Double.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", "uri"),
					String.class.getName());
			}
		};

}