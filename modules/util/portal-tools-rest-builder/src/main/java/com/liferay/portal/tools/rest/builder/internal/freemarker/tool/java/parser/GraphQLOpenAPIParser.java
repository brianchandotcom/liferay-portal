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

import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class GraphQLOpenAPIParser extends BaseOpenAPIParser {

	public static List<JavaMethodSignature> getJavaMethodSignatures(
		OpenAPIYAML openAPIYAML, String type) {

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (String schemaName : schemas.keySet()) {
			javaMethodSignatures.addAll(
				_getJavaMethodSignatures(openAPIYAML, schemaName, type));
		}

		return javaMethodSignatures;
	}

	public static String getMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		Set<String> methodAnnotations = new TreeSet<>();

		methodAnnotations.add("@GraphQLInvokeDetached");

		String httpMethod = getHTTPMethod(javaMethodSignature.getOperation());

		if (Objects.equals(httpMethod, "get") ||
			Objects.equals(httpMethod, "post")) {

			methodAnnotations.add("@GraphQLField");
		}

		return merge(methodAnnotations, '\n');
	}

	public static String getParameters(
		List<JavaParameter> javaParameters, boolean annotation) {

		StringBuilder sb = new StringBuilder();

		for (JavaParameter javaParameter : javaParameters) {
			String parameterAnnotation = null;

			if (annotation) {
				parameterAnnotation = _getParameterAnnotation(javaParameter);
			}

			sb.append(getParameter(javaParameter, parameterAnnotation));
			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private static List<JavaMethodSignature> _getJavaMethodSignatures(
		OpenAPIYAML openAPIYAML, String schemaName, String type) {

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();
			PathItem pathItem = entry.getValue();

			visitOperations(
				pathItem,
				operation -> {
					if (!_isTypeMethod(operation, type)) {
						return;
					}

					String returnType = getReturnType(openAPIYAML, operation);
					List<String> tags = operation.getTags();

					if (!isSchemaMethod(schemaName, tags, returnType)) {
						return;
					}

					String methodName = getMethodName(
						operation, path, returnType, schemaName);

					if (returnType.startsWith("Page<")) {
						returnType =
							"Collection<".concat(returnType.substring(5));
					}

					javaMethodSignatures.add(
						new JavaMethodSignature(
							path, pathItem, operation, schemaName,
							_getJavaParameters(operation), methodName,
							returnType));
				});
		}

		return javaMethodSignatures;
	}

	private static List<JavaParameter> _getJavaParameters(Operation operation) {
		List<JavaParameter> javaParameters = new ArrayList<>();

		for (JavaParameter javaParameter : getJavaParameters(operation)) {
			String parameterType = javaParameter.getParameterType();

			if (Objects.equals(parameterType, "Pagination")) {
				javaParameters.add(
					new JavaParameter(operation, "pageSize", "int"));

				javaParameters.add(new JavaParameter(operation, "page", "int"));
			}
			else {
				javaParameters.add(javaParameter);
			}
		}

		return javaParameters;
	}

	private static String _getParameterAnnotation(JavaParameter javaParameter) {
		Operation operation = javaParameter.getOperation();

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = CamelCaseUtil.toCamelCase(
				parameter.getName(), false);

			if (!Objects.equals(
					parameterName, javaParameter.getParameterName())) {

				continue;
			}

			Schema schema = parameter.getSchema();

			if (schema.getType() != null) {
				StringBuilder sb = new StringBuilder();

				sb.append("@GraphQLName(\"");
				sb.append(parameter.getName());
				sb.append("\")");

				return sb.toString();
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("@GraphQLName(\"");
		sb.append(javaParameter.getParameterType());
		sb.append("\")");

		return sb.toString();
	}

	private static boolean _isTypeMethod(Operation operation, String type) {
		String httpMethod = getHTTPMethod(operation);

		if (Objects.equals(type, "mutation") &&
			!Objects.equals(httpMethod, "get")) {

			return true;
		}

		if (Objects.equals(type, "query") &&
			Objects.equals(httpMethod, "get")) {

			return true;
		}

		return false;
	}

}