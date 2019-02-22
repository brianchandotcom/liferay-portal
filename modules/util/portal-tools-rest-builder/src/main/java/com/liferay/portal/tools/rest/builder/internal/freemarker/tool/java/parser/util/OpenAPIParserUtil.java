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
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.tools.rest.builder.internal.util.PathUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.RequestBody;
import com.liferay.portal.vulcan.yaml.openapi.Response;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Peter Shin
 */
public class OpenAPIParserUtil {

	public static String getArguments(List<JavaParameter> javaParameters) {
		StringBuilder sb = new StringBuilder();

		for (JavaParameter javaParameter : javaParameters) {
			sb.append(javaParameter.getParameterName());
			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

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

	protected static String getHTTPMethod(Operation operation) {
		Class<? extends Operation> clazz = operation.getClass();

		return StringUtil.lowerCase(clazz.getSimpleName());
	}

	protected static List<JavaParameter> getJavaParameters(
		Operation operation) {

		if ((operation == null) || (operation.getParameters() == null)) {
			return Collections.emptyList();
		}

		List<JavaParameter> javaParameters = new ArrayList<>();

		List<Parameter> parameters = operation.getParameters();

		Set<String> parameterNames = new HashSet<>();

		for (Parameter parameter : parameters) {
			parameterNames.add(parameter.getName());
		}

		for (Parameter parameter : parameters) {
			String parameterName = parameter.getName();

			if (StringUtil.equals(parameterName, "Accept-Language") ||
				StringUtil.equals(parameterName, "filter") ||
				StringUtil.equals(parameterName, "sort")) {

				continue;
			}

			if (StringUtil.equals(parameterName, "page") ||
				StringUtil.equals(parameterName, "pageSize")) {

				if (parameterNames.contains("page") &&
					parameterNames.contains("pageSize")) {

					continue;
				}
			}

			javaParameters.add(_getJavaParameter(operation, parameter));
		}

		if (parameterNames.contains("filter")) {
			JavaParameter javaParameter = new JavaParameter(
				operation, "filter", "Filter");

			javaParameters.add(javaParameter);
		}

		if (parameterNames.contains("page") &&
			parameterNames.contains("pageSize")) {

			JavaParameter javaParameter = new JavaParameter(
				operation, "pagination", "Pagination");

			javaParameters.add(javaParameter);
		}

		if (parameterNames.contains("sort")) {
			JavaParameter javaParameter = new JavaParameter(
				operation, "sorts", "Sort[]");

			javaParameters.add(javaParameter);
		}

		RequestBody requestBody = operation.getRequestBody();

		if (requestBody != null) {
			JavaParameter multipartBodyJavaParameter = null;

			Map<String, Content> contents = requestBody.getContent();

			for (Map.Entry<String, Content> entry : contents.entrySet()) {
				if (Objects.equals(entry.getKey(), "multipart/form-data")) {
					multipartBodyJavaParameter = new JavaParameter(
						operation, "multipartBody", "MultipartBody");
				}
			}

			if (multipartBodyJavaParameter == null) {
				for (Content content : contents.values()) {
					String schemaName = getJavaParameterType(
						null, content.getSchema());

					String parameterName = StringUtil.lowerCaseFirstLetter(
						schemaName);

					if (StringUtil.equals(schemaName, "Long")) {
						parameterName = "referenceId";
					}

					javaParameters.add(
						new JavaParameter(
							operation, parameterName, schemaName));
				}
			}
			else {
				javaParameters.add(multipartBodyJavaParameter);
			}
		}

		return javaParameters;
	}

	protected static String getJavaParameterType(
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
				return _getComponentType(items.getReference()) + "[]";
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
					return _getComponentType(allOfSchema.getReference());
				}
			}
		}

		if ((schema.getAnyOfSchemas() != null) ||
			(schema.getOneOfSchemas() != null)) {

			return "Object";
		}

		return _getComponentType(schema.getReference());
	}

	protected static String getMethodName(
		Operation operation, String path, String returnType,
		String schemaName) {

		List<String> urls = new ArrayList<>();

		String httpMethod = getHTTPMethod(operation);

		urls.add(httpMethod);

		List<Parameter> parameters = _getPathParameters(
			operation.getParameters());

		for (Parameter parameter : parameters) {
			String name = parameter.getName();

			urls.add(CamelCaseUtil.toCamelCase(name.replace("-id", ""), true));

			urls.add("");
		}

		if (returnType.startsWith("Page<" + schemaName)) {
			urls.add(TextFormatter.formatPlural(schemaName));
		}

		if (_isPostToSameSchema(httpMethod, path, schemaName, urls.size())) {
			urls.add(schemaName);
		}

		urls.add(PathUtil.getLastSegment(path, urls.size()));

		if (StringUtil.startsWith(returnType, "Page<")) {
			urls.add("Page");
		}

		return String.join("", urls);
	}

	protected static String getParameter(
		JavaParameter javaParameter, String parameterAnnotation) {

		StringBuilder sb = new StringBuilder();

		if (Validator.isNotNull(parameterAnnotation)) {
			sb.append(parameterAnnotation);
			sb.append(' ');
		}

		sb.append(javaParameter.getParameterType());
		sb.append(' ');
		sb.append(javaParameter.getParameterName());

		return sb.toString();
	}

	protected static String getReturnType(
		OpenAPIYAML openAPIYAML, Operation operation) {

		Map<String, Response> responses = operation.getResponses();

		if (responses.isEmpty()) {
			return "boolean";
		}

		for (Response response : responses.values()) {
			Map<String, Content> contents = response.getContent();

			if ((contents == null) || (contents.values() == null)) {
				continue;
			}

			for (Content content : contents.values()) {
				Schema schema = content.getSchema();

				if (schema == null) {
					continue;
				}

				String javaParameterType = getJavaParameterType(null, schema);

				if (javaParameterType.endsWith("[]")) {
					String s = javaParameterType.substring(
						0, javaParameterType.length() - 2);

					return "Page<" + s + ">";
				}

				Schema componentSchema = _getComponentSchema(
					openAPIYAML, schema.getReference());

				if (componentSchema != null) {
					return _getComponentType(schema.getReference());
				}
			}
		}

		if (Objects.equals(getHTTPMethod(operation), "get")) {
			return "String";
		}

		return "boolean";
	}

	protected static boolean isSchemaMethod(
		String schemaName, List<String> tags, String returnType) {

		if (!tags.isEmpty()) {
			if (tags.contains(schemaName)) {
				return true;
			}

			return false;
		}

		if (returnType.equals(schemaName) ||
			((returnType.length() == schemaName.length() + 6) &&
			 returnType.startsWith("Page<") && returnType.endsWith(">") &&
			 returnType.regionMatches(5, schemaName, 0, schemaName.length()))) {

			return true;
		}

		return false;
	}

	protected static String merge(Collection<String> c, Character delimiter) {
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

	protected static List<JavaMethodSignature>
		toFullyQualifiedJavaMethodSignatures(
			ConfigYAML configYAML,
			List<JavaMethodSignature> javaMethodSignatures,
			OpenAPIYAML openAPIYAML) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);
		List<JavaMethodSignature> newJavaMethodSignatures = new ArrayList<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			List<JavaParameter> javaParameters = toFullyQualifiedJavaParameters(
				configYAML, javaMethodSignature.getJavaParameters(),
				openAPIYAML);
			String returnType = _toFullyQualifiedType(
				configYAML, openAPIYAML, schemas.keySet(),
				javaMethodSignature.getReturnType());

			JavaMethodSignature newJavaMethodSignature =
				new JavaMethodSignature(
					javaMethodSignature.getPath(),
					javaMethodSignature.getPathItem(),
					javaMethodSignature.getOperation(),
					javaMethodSignature.getSchemaName(), javaParameters,
					javaMethodSignature.getMethodName(), returnType);

			newJavaMethodSignatures.add(newJavaMethodSignature);
		}

		return newJavaMethodSignatures;
	}

	protected static List<JavaParameter> toFullyQualifiedJavaParameters(
		ConfigYAML configYAML, List<JavaParameter> javaParameters,
		OpenAPIYAML openAPIYAML) {

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);
		List<JavaParameter> newJavaParameters = new ArrayList<>();

		for (JavaParameter javaParameter : javaParameters) {
			String parameterType = _toFullyQualifiedType(
				configYAML, openAPIYAML, schemas.keySet(),
				javaParameter.getParameterType());

			JavaParameter newJavaParameter = new JavaParameter(
				javaParameter.getOperation(), javaParameter.getParameterName(),
				parameterType);

			newJavaParameters.add(newJavaParameter);
		}

		return newJavaParameters;
	}

	protected static void visitOperations(
		PathItem pathItem, Consumer<Operation> consumer) {

		if (pathItem.getDelete() != null) {
			consumer.accept(pathItem.getDelete());
		}

		if (pathItem.getGet() != null) {
			consumer.accept(pathItem.getGet());
		}

		if (pathItem.getHead() != null) {
			consumer.accept(pathItem.getHead());
		}

		if (pathItem.getOptions() != null) {
			consumer.accept(pathItem.getOptions());
		}

		if (pathItem.getPatch() != null) {
			consumer.accept(pathItem.getPatch());
		}

		if (pathItem.getPost() != null) {
			consumer.accept(pathItem.getPost());
		}

		if (pathItem.getPut() != null) {
			consumer.accept(pathItem.getPut());
		}
	}

	private static Schema _getComponentSchema(
		OpenAPIYAML openAPIYAML, String reference) {

		if ((reference == null) ||
			!reference.startsWith("#/components/schemas/")) {

			return null;
		}

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		return schemas.get(_getComponentType(reference));
	}

	private static String _getComponentType(String reference) {
		int index = reference.lastIndexOf('/');

		if (index == -1) {
			return reference;
		}

		return reference.substring(index + 1);
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

	private static JavaParameter _getJavaParameter(
		Operation operation, Parameter parameter) {

		String parameterName = CamelCaseUtil.toCamelCase(
			parameter.getName(), false);
		String parameterType = getJavaParameterType(
			null, parameter.getSchema());

		return new JavaParameter(operation, parameterName, parameterType);
	}

	private static List<Parameter> _getPathParameters(
		List<Parameter> parameters) {

		Stream<Parameter> stream = parameters.stream();

		return stream.filter(
			parameter -> StringUtil.equals(parameter.getIn(), "path")
		).collect(
			Collectors.toList()
		);
	}

	private static boolean _isPostToSameSchema(
		String httpMethod, String path, String schemaName, int segmentNumber) {

		String lastSegment = PathUtil.getLastSegment(path, segmentNumber);

		if (httpMethod.equals("post") &&
			lastSegment.startsWith(TextFormatter.formatPlural(schemaName))) {

			return true;
		}

		return false;
	}

	private static String _toFullyQualifiedClassName(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Set<String> schemaNames,
		String simpleClassName) {

		String apiPackagePath = configYAML.getApiPackagePath();
		String versionDirName = OpenAPIUtil.getVersionDirName(openAPIYAML);

		for (String schemaName : schemaNames) {
			if (Objects.equals(simpleClassName, schemaName)) {
				StringBuilder sb = new StringBuilder();

				sb.append(apiPackagePath);
				sb.append(".dto.");
				sb.append(versionDirName);
				sb.append(".");
				sb.append(schemaName);

				return sb.toString();
			}

			if (Objects.equals(simpleClassName, schemaName + "Impl")) {
				StringBuilder sb = new StringBuilder();

				sb.append(apiPackagePath);
				sb.append(".dto.");
				sb.append(versionDirName);
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

}