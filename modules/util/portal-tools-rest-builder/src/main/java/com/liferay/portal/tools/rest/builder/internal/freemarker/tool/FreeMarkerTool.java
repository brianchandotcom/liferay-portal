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

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.DTOOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.GraphQLOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceTestCaseOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.Get;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.RequestBody;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Peter Shin
 */
public class FreeMarkerTool {

	public static FreeMarkerTool getInstance() {
		return _instance;
	}

	public Map<String, Schema> getDTOEnumSchemas(
		OpenAPIYAML openAPIYAML, Schema schema) {

		return DTOOpenAPIParser.getEnumSchemas(openAPIYAML, schema);
	}

	public String getDTOParentClassName(
		OpenAPIYAML openAPIYAML, String schemaName) {

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			Schema schema = entry.getValue();

			if (schema.getOneOfSchemas() == null) {
				continue;
			}

			for (Schema oneOfSchema : schema.getOneOfSchemas()) {
				Map<String, Schema> propertySchemas =
					oneOfSchema.getPropertySchemas();

				Set<String> keys = propertySchemas.keySet();

				Iterator<String> iterator = keys.iterator();

				if (StringUtil.equalsIgnoreCase(schemaName, iterator.next())) {
					return entry.getKey();
				}
			}
		}

		return null;
	}

	public Map<String, String> getDTOProperties(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Schema schema) {

		return DTOOpenAPIParser.getProperties(configYAML, openAPIYAML, schema);
	}

	public Map<String, String> getDTOProperties(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		return DTOOpenAPIParser.getProperties(
			configYAML, openAPIYAML, schemaName);
	}

	public Schema getDTOPropertySchema(String propertyName, Schema schema) {
		return DTOOpenAPIParser.getPropertySchema(propertyName, schema);
	}

	public String getEnumFieldName(String value) {
		String fieldName = TextFormatter.format(value, TextFormatter.H);

		return StringUtil.toUpperCase(fieldName.replace(' ', '_'));
	}

	public String getGraphQLArguments(
		List<JavaMethodParameter> javaMethodParameters) {

		return OpenAPIParserUtil.getArguments(javaMethodParameters);
	}

	public List<JavaMethodSignature> getGraphQLJavaMethodSignatures(
		ConfigYAML configYAML, final String graphQLType,
		OpenAPIYAML openAPIYAML) {

		return GraphQLOpenAPIParser.getJavaMethodSignatures(
			configYAML, openAPIYAML,
			operation -> {
				String requiredType = "mutation";

				if (operation instanceof Get) {
					requiredType = "query";
				}

				if (requiredType.equals(graphQLType)) {
					return true;
				}

				return false;
			});
	}

	public String getGraphQLMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		return GraphQLOpenAPIParser.getMethodAnnotations(javaMethodSignature);
	}

	public String getGraphQLParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		return GraphQLOpenAPIParser.getParameters(
			javaMethodParameters, operation, annotation);
	}

	public Set<String> getGraphQLSchemaNames(
		List<JavaMethodSignature> javaMethodSignatures) {

		return OpenAPIParserUtil.getSchemaNames(javaMethodSignatures);
	}

	public String getHTTPMethod(Operation operation) {
		return OpenAPIParserUtil.getHTTPMethod(operation);
	}

	public String getJavaDataType(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Schema schema) {

		return OpenAPIParserUtil.getJavaDataType(
			OpenAPIParserUtil.getJavaDataTypeMap(configYAML, openAPIYAML),
			schema);
	}

	public JavaMethodSignature getPostSchemaJavaMethodSignature(
		List<JavaMethodSignature> javaMethodSignatures, String parameterName,
		String schemaName) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			Operation operation = javaMethodSignature.getOperation();

			if (!Objects.equals("post", getHTTPMethod(operation))) {
				continue;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(getHTTPMethod(operation));

			if (parameterName.startsWith("parent")) {
				parameterName = parameterName.substring(6);
			}

			if (parameterName.endsWith("Id")) {
				parameterName = parameterName.substring(
					0, parameterName.length() - 2);
			}

			sb.append(StringUtil.upperCaseFirstLetter(parameterName));

			sb.append(StringUtil.upperCaseFirstLetter(schemaName));

			String methodName = javaMethodSignature.getMethodName();

			if (!Objects.equals(methodName, sb.toString())) {
				continue;
			}

			List<JavaMethodParameter> javaMethodParameters =
				javaMethodSignature.getJavaMethodParameters();

			if (javaMethodParameters.size() != 2) {
				continue;
			}

			return javaMethodSignature;
		}

		return null;
	}

	public String getResourceArguments(
		List<JavaMethodParameter> javaMethodParameters) {

		return OpenAPIParserUtil.getArguments(javaMethodParameters);
	}

	public List<JavaMethodSignature> getResourceJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		return ResourceOpenAPIParser.getJavaMethodSignatures(
			configYAML, openAPIYAML, schemaName);
	}

	public String getResourceMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		return ResourceOpenAPIParser.getMethodAnnotations(javaMethodSignature);
	}

	public String getResourceParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		return ResourceOpenAPIParser.getParameters(
			javaMethodParameters, operation, annotation);
	}

	public String getResourceTestCaseArguments(
		List<JavaMethodParameter> javaMethodParameters) {

		return OpenAPIParserUtil.getArguments(javaMethodParameters);
	}

	public List<JavaMethodSignature> getResourceTestCaseJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		return ResourceTestCaseOpenAPIParser.getJavaMethodSignatures(
			configYAML, openAPIYAML, schemaName);
	}

	public String getResourceTestCaseParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		return ResourceTestCaseOpenAPIParser.getParameters(
			javaMethodParameters, operation, annotation);
	}

	public String getSchemaVarName(String schemaName) {
		return OpenAPIParserUtil.getSchemaVarName(schemaName);
	}

	public boolean hasHTTPMethod(
		JavaMethodSignature javaMethodSignature, String... httpMethods) {

		return OpenAPIParserUtil.hasHTTPMethod(
			javaMethodSignature, httpMethods);
	}

	public boolean hasJavaMethodSignature(
		List<JavaMethodSignature> javaMethodSignatures, String methodName) {

		return javaMethodSignatures.stream(
		).map(
			JavaMethodSignature::getMethodName
		).anyMatch(
			name -> name.equals(methodName)
		);
	}

	public boolean hasPathParameter(JavaMethodSignature javaMethodSignature) {
		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();
		Operation operation = javaMethodSignature.getOperation();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			if (isPathParameter(javaMethodParameter, operation)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasPostSchemaJavaMethodSignature(
		List<JavaMethodSignature> javaMethodSignatures, String parameterName,
		String schemaName) {

		JavaMethodSignature javaMethodSignature =
			getPostSchemaJavaMethodSignature(
				javaMethodSignatures, parameterName, schemaName);

		if (javaMethodSignature != null) {
			return true;
		}

		return false;
	}

	public boolean hasRequestBodyMediaType(
		JavaMethodSignature javaMethodSignature, String mediaType) {

		Operation operation = javaMethodSignature.getOperation();

		if (operation.getRequestBody() == null) {
			return false;
		}

		RequestBody requestBody = operation.getRequestBody();

		if (requestBody.getContent() == null) {
			return false;
		}

		Map<String, Content> contents = requestBody.getContent();

		Set<String> mediaTypes = contents.keySet();

		if (!mediaTypes.contains(mediaType)) {
			return false;
		}

		return true;
	}

	public boolean isDTOSchemaProperty(
		OpenAPIYAML openAPIYAML, String propertyName, Schema schema) {

		return DTOOpenAPIParser.isSchemaProperty(
			openAPIYAML, propertyName, schema);
	}

	public boolean isParameter(
		JavaMethodParameter javaMethodParameter, Operation operation,
		String type) {

		String name = javaMethodParameter.getParameterName();

		for (Parameter parameter : operation.getParameters()) {
			if (Objects.equals(parameter.getName(), name) &&
				Objects.equals(parameter.getIn(), type)) {

				return true;
			}
		}

		return false;
	}

	public boolean isPathParameter(
		JavaMethodParameter javaMethodParameter, Operation operation) {

		return isParameter(javaMethodParameter, operation, "path");
	}

	public boolean isQueryParameter(
		JavaMethodParameter javaMethodParameter, Operation operation) {

		return isParameter(javaMethodParameter, operation, "query");
	}

	private FreeMarkerTool() {
	}

	private static FreeMarkerTool _instance = new FreeMarkerTool();

}