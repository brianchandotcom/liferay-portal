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
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.RequestBody;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class OpenAPIParserUtil {

	public static Map<String, Schema> getAllOfPropertySchemas(Schema schema) {
		List<Schema> allOfSchemas = schema.getAllOfSchemas();

		if (allOfSchemas.size() == 1) {
			return schema.getPropertySchemas();
		}

		Map<String, Schema> propertySchemas = new HashMap<>();

		for (Schema allOfSchema : allOfSchemas) {
			if (allOfSchema.getReference() != null) {
				Schema itemSchema = new Schema();

				String reference = allOfSchema.getReference();

				itemSchema.setReference(reference);

				propertySchemas.put(
					StringUtil.lowerCaseFirstLetter(
						getReferenceName(reference)),
					itemSchema);
			}
			else {
				propertySchemas.putAll(allOfSchema.getPropertySchemas());
			}
		}

		return propertySchemas;
	}

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

	public static String getArrayClassName(String name) {
		if (name.equals(boolean.class.getName())) {
			return "[Z";
		}
		else if (name.equals(double.class.getName())) {
			return "[D";
		}
		else if (name.equals(float.class.getName())) {
			return "[F";
		}
		else if (name.equals(int.class.getName())) {
			return "[I";
		}
		else if (name.equals(long.class.getName())) {
			return "[J";
		}
		else {
			return "[L" + name + ";";
		}
	}

	public static String getElementClassName(String name) {
		if (name.equals("[Z")) {
			return boolean.class.getName();
		}
		else if (name.equals("[D")) {
			return double.class.getName();
		}
		else if (name.equals("[F")) {
			return float.class.getName();
		}
		else if (name.equals("[I")) {
			return int.class.getName();
		}
		else if (name.equals("[J")) {
			return long.class.getName();
		}
		else if (name.startsWith("[L") && name.endsWith(";")) {
			return name.substring(2, name.length() - 1);
		}

		return name;
	}

	public static List<String> getExternalReferences(OpenAPIYAML openAPIYAML) {
		Set<String> externalReferences = new HashSet<>();

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		for (Map.Entry<String, PathItem> entry1 : pathItems.entrySet()) {
			List<Operation> operations = getOperations(entry1.getValue());

			for (Operation operation : operations) {
				RequestBody requestBody = operation.getRequestBody();

				if (requestBody != null) {
					Map<String, Content> contentMap = requestBody.getContent();

					for (Map.Entry<String, Content> entry2 :
							contentMap.entrySet()) {

						Content content = entry2.getValue();

						Schema schema = content.getSchema();

						String reference = schema.getReference();

						if ((reference != null) &&
							!reference.contains("#/components/schemas/")) {

							externalReferences.add(reference);
						}
					}
				}
			}
		}

		return new ArrayList<>(externalReferences);
	}

	public static Map<String, Schema> getExternalSchemas(
			OpenAPIYAML openAPIYAML)
		throws Exception {

		Map<String, Schema> externalReferencesMap = new HashMap<>();

		List<String> externalReferences = getExternalReferences(openAPIYAML);

		for (String reference : externalReferences) {
			String path = reference.substring(0, reference.indexOf("#"));

			File openAPIFile = new File(path);

			externalReferencesMap.putAll(
				OpenAPIUtil.getAllSchemas(
					YAMLUtil.loadOpenAPIYAML(FileUtil.read(openAPIFile))));
		}

		return externalReferencesMap;
	}

	public static String getHTTPMethod(Operation operation) {
		Class<? extends Operation> clazz = operation.getClass();

		return StringUtil.lowerCase(clazz.getSimpleName());
	}

	public static String getJavaDataType(
		Map<String, String> javaDataTypeMap, Schema schema) {

		if (schema.getAllOfSchemas() != null) {
			for (Schema allOfSchema : schema.getAllOfSchemas()) {
				if (Validator.isNotNull(allOfSchema.getReference())) {
					return javaDataTypeMap.get(
						getReferenceName(allOfSchema.getReference()));
				}
			}
		}

		if ((schema.getAnyOfSchemas() != null) ||
			(schema.getOneOfSchemas() != null)) {

			return Object.class.getName();
		}

		if (schema.getItems() != null) {
			Items items = schema.getItems();

			String javaDataType = _openAPIDataTypeMap.get(
				new AbstractMap.SimpleImmutableEntry<>(
					items.getType(), items.getFormat()));

			if (items.getAdditionalPropertySchema() != null) {
				javaDataType = Map.class.getName();
			}

			if (items.getReference() != null) {
				javaDataType = javaDataTypeMap.get(
					getReferenceName(items.getReference()));
			}

			return getArrayClassName(javaDataType);
		}

		if (Objects.equals(schema.getType(), "object")) {
			return _getMapType(
				javaDataTypeMap, schema.getAdditionalPropertySchema());
		}

		if (schema.getReference() != null) {
			return javaDataTypeMap.get(getReferenceName(schema.getReference()));
		}

		return _openAPIDataTypeMap.get(
			new AbstractMap.SimpleImmutableEntry<>(
				schema.getType(), schema.getFormat()));
	}

	public static Map<String, String> getJavaDataTypeMap(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML) {

		Map<String, Schema> allSchemas = OpenAPIUtil.getAllSchemas(openAPIYAML);
		Map<String, String> javaDataTypeMap = new HashMap<>();

		List<String> externalReferences = getExternalReferences(openAPIYAML);

		try {
			for (String externalReference : externalReferences) {
				String openAPIPath = externalReference.substring(
					0, externalReference.indexOf("#"));

				String configPath = StringUtil.replace(
					openAPIPath, "rest-openapi.yaml", "rest-config.yaml");

				ConfigYAML externalConfigYAML = YAMLUtil.loadConfigYAML(
					FileUtil.read(new File(configPath)));

				OpenAPIYAML externalOpenAPIYAML = YAMLUtil.loadOpenAPIYAML(
					FileUtil.read(new File(openAPIPath)));

				Map<String, String> externalJavaDataTypeMap =
					getJavaDataTypeMap(externalConfigYAML, externalOpenAPIYAML);

				javaDataTypeMap.putAll(externalJavaDataTypeMap);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		for (String schemaName : allSchemas.keySet()) {
			StringBuilder sb = new StringBuilder();

			sb.append(configYAML.getApiPackagePath());
			sb.append(".dto.");
			sb.append(OpenAPIUtil.escapeVersion(openAPIYAML));
			sb.append('.');
			sb.append(schemaName);

			javaDataTypeMap.put(schemaName, sb.toString());

			sb.setLength(0);

			sb.append(configYAML.getApiPackagePath());
			sb.append(".resource.");
			sb.append(OpenAPIUtil.escapeVersion(openAPIYAML));
			sb.append('.');
			sb.append(schemaName);
			sb.append("Resource");

			javaDataTypeMap.put(schemaName + "Resource", sb.toString());

			sb.setLength(0);

			sb.append(configYAML.getApiPackagePath());
			sb.append(".internal.resource.");
			sb.append(OpenAPIUtil.escapeVersion(openAPIYAML));
			sb.append('.');
			sb.append(schemaName);
			sb.append("ResourceImpl");

			javaDataTypeMap.put(schemaName + "ResourceImpl", sb.toString());
		}

		Map<String, Schema> globalEnumSchemas =
			OpenAPIUtil.getGlobalEnumSchemas(openAPIYAML);

		for (String schemaName : globalEnumSchemas.keySet()) {
			StringBuilder sb = new StringBuilder();

			sb.append(configYAML.getApiPackagePath());
			sb.append(".constant.");
			sb.append(OpenAPIUtil.escapeVersion(openAPIYAML));
			sb.append('.');
			sb.append(schemaName);

			javaDataTypeMap.put(schemaName, sb.toString());
		}

		return javaDataTypeMap;
	}

	public static List<Operation> getOperations(PathItem pathItem) {
		List<Operation> operations = new ArrayList<>();

		if (pathItem.getDelete() != null) {
			operations.add(pathItem.getDelete());
		}

		if (pathItem.getGet() != null) {
			operations.add(pathItem.getGet());
		}

		if (pathItem.getHead() != null) {
			operations.add(pathItem.getHead());
		}

		if (pathItem.getOptions() != null) {
			operations.add(pathItem.getOptions());
		}

		if (pathItem.getPatch() != null) {
			operations.add(pathItem.getPatch());
		}

		if (pathItem.getPost() != null) {
			operations.add(pathItem.getPost());
		}

		if (pathItem.getPut() != null) {
			operations.add(pathItem.getPut());
		}

		return operations;
	}

	public static String getParameter(
		JavaMethodParameter javaMethodParameter, String parameterAnnotation) {

		StringBuilder sb = new StringBuilder();

		if (Validator.isNotNull(parameterAnnotation)) {
			sb.append(parameterAnnotation);
			sb.append(' ');
		}

		String parameterType = javaMethodParameter.getParameterType();

		if (parameterType.startsWith("[")) {
			sb.append(getElementClassName(parameterType) + "[]");
		}
		else {
			sb.append(parameterType);
		}

		sb.append(' ');
		sb.append(javaMethodParameter.getParameterName());

		return sb.toString();
	}

	public static String getReferenceName(String reference) {
		if (!reference.contains("#/components/schemas")) {
			return reference.substring(reference.lastIndexOf("#") + 1);
		}

		int index = reference.lastIndexOf('/');

		if (index == -1) {
			return reference;
		}

		return reference.substring(index + 1);
	}

	public static Set<String> getSchemaNames(
		List<JavaMethodSignature> javaMethodSignatures) {

		Set<String> schemaNames = new TreeSet<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			schemaNames.add(javaMethodSignature.getSchemaName());
		}

		return schemaNames;
	}

	public static String getSchemaVarName(String schemaName) {
		return TextFormatter.format(schemaName, TextFormatter.I);
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

	private static String _getMapType(
		Map<String, String> javaDataTypeMap, Schema schema) {

		if (schema != null) {
			if (schema.getReference() != null) {
				String referenceType = javaDataTypeMap.get(
					getReferenceName(schema.getReference()));

				return "Map<String, " + referenceType + ">";
			}

			AbstractMap.SimpleImmutableEntry<String, String> key =
				new AbstractMap.SimpleImmutableEntry<>(
					schema.getType(), schema.getFormat());

			if (_openAPIDataTypeMap.containsKey(key)) {
				String additionalJavaDataType = getJavaDataType(
					javaDataTypeMap, schema);

				return "Map<String, " + additionalJavaDataType + ">";
			}
		}

		return Object.class.getName();
	}

	private static final Map<Map.Entry<String, String>, String>
		_openAPIDataTypeMap = new HashMap<Map.Entry<String, String>, String>() {
			{
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
					new AbstractMap.SimpleImmutableEntry<>("object", null),
					Object.class.getName());
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

				put(new AbstractMap.SimpleImmutableEntry<>("?", null), "?");
				put(
					new AbstractMap.SimpleImmutableEntry<>("integer", null),
					Integer.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("number", null),
					Number.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>(
						"number", "bigdecimal"),
					BigDecimal.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", "email"),
					String.class.getName());
				put(
					new AbstractMap.SimpleImmutableEntry<>("string", "uri"),
					String.class.getName());
			}
		};

}