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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.RequestBody;
import com.liferay.portal.vulcan.yaml.openapi.Response;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Shin
 */
public class ResourceOpenAPIParser extends BaseOpenAPIParser {

	public static List<JavaMethodSignature> getJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName,
		boolean fullyQualifiedNames) {

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return Collections.emptyList();
		}

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();
			PathItem pathItem = entry.getValue();

			visitOperations(
				pathItem,
				operation -> {
					String returnType = getReturnType(openAPIYAML, operation);
					List<String> tags = operation.getTags();

					if (!isSchemaMethod(schemaName, tags, returnType)) {
						return;
					}

					String methodName = getMethodName(
						operation, path, returnType, schemaName);

					javaMethodSignatures.add(
						new JavaMethodSignature(
							path, pathItem, operation, schemaName,
							getJavaParameters(operation), methodName,
							returnType));
				});
		}

		if (!fullyQualifiedNames) {
			return javaMethodSignatures;
		}

		return toFullyQualifiedJavaMethodSignatures(
			configYAML, javaMethodSignatures, openAPIYAML);
	}

	public static String getMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		String path = javaMethodSignature.getPath();
		PathItem pathItem = javaMethodSignature.getPathItem();
		Operation operation = javaMethodSignature.getOperation();

		Set<String> methodAnnotations = new TreeSet<>();

		methodAnnotations.add("@Path(\"" + path + "\")");

		String httpMethod = getHTTPMethod(operation);

		methodAnnotations.add("@" + StringUtil.toUpperCase(httpMethod));

		if (pathItem.getGet() != null) {
			methodAnnotations.add("@RequiresScope(\"everything.read\")");
		}
		else {
			methodAnnotations.add("@RequiresScope(\"everything.write\")");
		}

		String methodAnnotation = _getMethodAnnotationConsumes(operation);

		if (Validator.isNotNull(methodAnnotation)) {
			methodAnnotations.add(methodAnnotation);
		}

		methodAnnotation = _getMethodAnnotationProduces(operation);

		if (Validator.isNotNull(methodAnnotation)) {
			methodAnnotations.add(methodAnnotation);
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

	private static List<String> _getMediaTypes(Map<String, Content> contents) {
		if ((contents == null) || contents.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> mediaTypes = new ArrayList<>(contents.keySet());

		Collections.sort(mediaTypes);

		return mediaTypes;
	}

	private static String _getMethodAnnotationConsumes(Operation operation) {
		RequestBody requestBody = operation.getRequestBody();

		if (requestBody == null) {
			return null;
		}

		Map<String, Content> contents = requestBody.getContent();

		List<String> mediaTypes = _getMediaTypes(contents);

		if (mediaTypes.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < mediaTypes.size(); i++) {
			sb.append(StringUtil.quote(mediaTypes.get(i), "\""));

			if (i < (mediaTypes.size() - 1)) {
				sb.append(",");
			}
		}

		if (mediaTypes.size() > 1) {
			return "@Consumes({" + sb.toString() + "})";
		}

		return "@Consumes(" + sb.toString() + ")";
	}

	private static String _getMethodAnnotationProduces(Operation operation) {
		Map<String, Response> responses = operation.getResponses();

		if (responses.isEmpty()) {
			return null;
		}

		List<String> mediaTypes = new ArrayList<>();

		for (Response response : responses.values()) {
			mediaTypes.addAll(_getMediaTypes(response.getContent()));
		}

		if (mediaTypes.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < mediaTypes.size(); i++) {
			sb.append(StringUtil.quote(mediaTypes.get(i), "\""));

			if (i < (mediaTypes.size() - 1)) {
				sb.append(",");
			}
		}

		if (mediaTypes.size() > 1) {
			return "@Produces({" + sb.toString() + "})";
		}

		return "@Produces(" + sb.toString() + ")";
	}

	private static String _getParameterAnnotation(JavaParameter javaParameter) {
		Operation operation = javaParameter.getOperation();

		List<Parameter> parameters = operation.getParameters();

		Set<String> parameterNames = new HashSet<>();

		for (Parameter parameter : parameters) {
			parameterNames.add(parameter.getName());
		}

		String parameterType = javaParameter.getParameterType();

		if (Objects.equals(parameterType, "Filter") &&
			parameterNames.contains("filter")) {

			return "@Context";
		}

		if (Objects.equals(parameterType, "Pagination") &&
			parameterNames.contains("page") &&
			parameterNames.contains("pageSize")) {

			return "@Context";
		}

		if (Objects.equals(parameterType, "Sort[]") &&
			parameterNames.contains("sort")) {

			return "@Context";
		}

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

				sb.append("@");
				sb.append(StringUtil.upperCaseFirstLetter(parameter.getIn()));
				sb.append("Param(\"");
				sb.append(parameter.getName());
				sb.append("\")");

				return sb.toString();
			}
		}

		return "";
	}

}