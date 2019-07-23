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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

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

		fieldName = fieldName.replace(' ', '_');
		fieldName = fieldName.replace('-', '_');
		fieldName = fieldName.replace(".", "");

		return StringUtil.toUpperCase(fieldName);
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

	public String getGraphQLMutationName(String methodName) {
		methodName = methodName.replaceFirst("post", "create");

		return methodName.replaceFirst("put", "update");
	}

	public String getGraphQLParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		return GraphQLOpenAPIParser.getParameters(
			javaMethodParameters, operation, annotation);
	}

	public String getGraphQLPropertyName(String methodName) {
		methodName = methodName.replaceFirst("get", "");

		int index = methodName.lastIndexOf("Page");

		if (index != -1) {
			methodName = methodName.substring(0, index);
		}

		methodName = methodName.replaceFirst("Site", "");

		return StringUtil.lowerCaseFirstLetter(methodName);
	}

	public List<JavaMethodSignature> getGraphQLRelationJavaMethodSignatures(
		ConfigYAML configYAML, final String graphQLType,
		OpenAPIYAML openAPIYAML) {

		List<JavaMethodSignature> javaMethodSignatures =
			getGraphQLJavaMethodSignatures(
				configYAML, graphQLType, openAPIYAML);

		Map<String, JavaMethodSignature> javaMethodSignatureMap =
			new HashMap<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			List<JavaMethodParameter> javaMethodParameters =
				javaMethodSignature.getJavaMethodParameters();

			if (!javaMethodParameters.isEmpty()) {
				JavaMethodParameter javaMethodParameter =
					javaMethodParameters.get(0);

				String parameterName = javaMethodParameter.getParameterName();

				String methodName = javaMethodSignature.getMethodName();

				for (JavaMethodSignature relationJavaMethodSignature :
						javaMethodSignatures) {

					if ((javaMethodSignature != relationJavaMethodSignature) &&
						_isGraphQLPathRelation(
							relationJavaMethodSignature, parameterName)) {

						javaMethodSignatureMap.put(
							methodName,
							_getJavaMethodSignature(
								javaMethodSignature,
								relationJavaMethodSignature.getSchemaName()));
					}
				}

				Components components = openAPIYAML.getComponents();

				Map<String, Schema> schemas = components.getSchemas();

				for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
					Schema schema = entry.getValue();

					Map<String, Schema> propertySchemas =
						schema.getPropertySchemas();

					if (propertySchemas != null) {
						for (String propertyName : propertySchemas.keySet()) {
							if (_isGraphQLPropertyRelation(
									javaMethodSignature, parameterName,
									propertyName)) {

								javaMethodSignatureMap.put(
									methodName,
									_getJavaMethodSignature(
										javaMethodSignature, entry.getKey()));
							}
						}
					}
				}
			}
		}

		return new ArrayList<>(javaMethodSignatureMap.values());
	}

	public String getGraphQLRelationName(
		String methodName, String parentSchemaName) {

		methodName = getGraphQLPropertyName(methodName);

		return StringUtil.lowerCaseFirstLetter(
			methodName.replaceFirst(
				StringUtil.lowerCaseFirstLetter(parentSchemaName), ""));
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

	public JavaMethodSignature getJavaMethodSignature(
		List<JavaMethodSignature> javaMethodSignatures, String methodName) {

		Stream<JavaMethodSignature> stream = javaMethodSignatures.stream();

		return stream.filter(
			javaMethodSignature -> methodName.equals(
				javaMethodSignature.getMethodName())
		).findFirst(
		).orElse(
			null
		);
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

			if (!Objects.equals(
					javaMethodSignature.getMethodName(), sb.toString())) {

				continue;
			}

			List<JavaMethodParameter> javaMethodParameters =
				javaMethodSignature.getJavaMethodParameters();

			if ((javaMethodParameters.size() != 2) ||
				CollectionUtils.isEmpty(
					javaMethodSignature.getRequestBodyMediaTypes())) {

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
		List<JavaMethodParameter> javaMethodParameters, OpenAPIYAML openAPIYAML,
		Operation operation, boolean annotation) {

		return ResourceOpenAPIParser.getParameters(
			javaMethodParameters, openAPIYAML, operation, annotation);
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
		List<JavaMethodParameter> javaMethodParameters, OpenAPIYAML openAPIYAML,
		Operation operation, boolean annotation) {

		return ResourceTestCaseOpenAPIParser.getParameters(
			javaMethodParameters, openAPIYAML, operation, annotation);
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

		Stream<JavaMethodSignature> stream = javaMethodSignatures.stream();

		return stream.map(
			JavaMethodSignature::getMethodName
		).anyMatch(
			javaMethodSignatureMethodName ->
				javaMethodSignatureMethodName.equals(methodName)
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

	public boolean hasQueryParameter(JavaMethodSignature javaMethodSignature) {
		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();
		Operation operation = javaMethodSignature.getOperation();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			if (isQueryParameter(javaMethodParameter, operation)) {
				return true;
			}
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

	public boolean isReturnTypeRelatedSchema(
		JavaMethodSignature javaMethodSignature,
		List<String> relatedSchemaNames) {

		String returnType = javaMethodSignature.getReturnType();

		String[] returnTypeParts = returnType.split("\\.");

		if (returnTypeParts.length > 0) {
			String string = returnTypeParts[returnTypeParts.length - 1];

			return relatedSchemaNames.contains(string);
		}

		return false;
	}

	private FreeMarkerTool() {
	}

	private JavaMethodSignature _getJavaMethodSignature(
		JavaMethodSignature javaMethodSignature, String parentSchemaName) {

		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();

		return new JavaMethodSignature(
			javaMethodSignature.getPath(), javaMethodSignature.getPathItem(),
			javaMethodSignature.getOperation(),
			javaMethodSignature.getRequestBodyMediaTypes(),
			javaMethodSignature.getSchemaName(),
			javaMethodParameters.subList(1, javaMethodParameters.size()),
			javaMethodSignature.getMethodName(),
			javaMethodSignature.getReturnType(), parentSchemaName);
	}

	private boolean _isGraphQLPathRelation(
		JavaMethodSignature javaMethodSignature, String parameterName) {

		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();

		if (javaMethodParameters.size() != 1) {
			return false;
		}

		JavaMethodParameter javaMethodParameter = javaMethodParameters.get(0);

		String propertyName = StringUtil.upperCaseFirstLetter(
			javaMethodParameter.getParameterName());

		String returnType = javaMethodSignature.getReturnType();

		if ((returnType.endsWith(javaMethodSignature.getSchemaName()) &&
			 parameterName.equals(javaMethodParameter.getParameterName())) ||
			parameterName.equals("parent" + propertyName)) {

			return true;
		}

		return false;
	}

	private boolean _isGraphQLPropertyRelation(
		JavaMethodSignature javaMethodSignature, String parameterName,
		String propertyName) {

		String returnType = StringUtil.toLowerCase(
			javaMethodSignature.getReturnType());
		String schemaName = parameterName.replace("Id", "");

		if (propertyName.equals(parameterName) &&
			returnType.endsWith(StringUtil.toLowerCase(schemaName))) {

			return true;
		}

		return false;
	}

	private static FreeMarkerTool _instance = new FreeMarkerTool();

}