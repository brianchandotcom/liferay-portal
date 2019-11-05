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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.DTOOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.GraphQLOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceTestCaseOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.vulcan.yaml.config.Application;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.Get;
import com.liferay.portal.vulcan.yaml.openapi.Info;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.RequestBody;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Peter Shin
 */
public class FreeMarkerTool {

	public static FreeMarkerTool getInstance() {
		return _freeMarkerTool;
	}

	public Map<String, Schema> getDTOEnumSchemas(
		OpenAPIYAML openAPIYAML, Schema schema) {

		return DTOOpenAPIParser.getEnumSchemas(openAPIYAML, schema);
	}

	public String getDTOParentClassName(
		OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, Schema> schemas = _getSchemas(openAPIYAML);

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

		fieldName = fieldName.replaceAll("[ \\-\\/]", "_");

		fieldName = fieldName.replaceAll("[^a-zA-Z0-9_]", "");

		fieldName = fieldName.replaceAll("_+", "_");

		return StringUtil.toUpperCase(fieldName);
	}

	public String getGraphQLArguments(
		List<JavaMethodParameter> javaMethodParameters, String schemaVarName) {

		String arguments = OpenAPIParserUtil.getArguments(javaMethodParameters);

		arguments = StringUtil.replace(
			arguments, "filter",
			"_filterBiFunction.apply(" + schemaVarName +
				"Resource, filterString)");
		arguments = StringUtil.replace(
			arguments, "pageSize,page", "Pagination.of(page, pageSize)");
		arguments = StringUtil.replace(
			arguments, "sorts",
			"_sortsBiFunction.apply(" + schemaVarName +
				"Resource, sortsString)");

		return arguments;
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

	public String getGraphQLJavaParameterName(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName,
		JavaMethodParameter javaMethodParameter) {

		Map<String, String> properties = getDTOProperties(
			configYAML, openAPIYAML, schemaName);

		String parameterName = StringUtil.toLowerCase(
			javaMethodParameter.getParameterName());

		schemaName = StringUtil.toLowerCase(schemaName);

		String shortParameterName = StringUtil.replace(
			parameterName, schemaName, StringPool.BLANK);

		for (String propertyKey : properties.keySet()) {
			if (StringUtil.equalsIgnoreCase(parameterName, propertyKey) ||
				StringUtil.equalsIgnoreCase(shortParameterName, propertyKey)) {

				return StringUtil.upperCaseFirstLetter(propertyKey);
			}
		}

		return null;
	}

	public String getGraphQLMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		return GraphQLOpenAPIParser.getMethodAnnotations(javaMethodSignature);
	}

	public String getGraphQLMethodJavadoc(
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures,
		OpenAPIYAML openAPIYAML) {

		StringBuilder sb = new StringBuilder();

		sb.append("Invoke this method with the command line:\n*\n* ");
		sb.append("curl -H 'Content-Type: text/plain; charset=utf-8' ");
		sb.append("-X 'POST' 'http://localhost:8080/o/graphql' ");
		sb.append("-d $'");
		sb.append(
			_getGraphQLBody(
				javaMethodSignature, javaMethodSignatures, openAPIYAML));
		sb.append("' -u 'test@liferay.com:test'");

		return sb.toString();
	}

	public String getGraphQLMutationName(String methodName) {
		methodName = methodName.replaceFirst("post", "create");

		return methodName.replaceFirst("put", "update");
	}

	public String getGraphQLParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		String parameters = GraphQLOpenAPIParser.getParameters(
			javaMethodParameters, operation, annotation);

		parameters = StringUtil.replace(
			parameters, "com.liferay.portal.kernel.search.filter.Filter filter",
			"String filterString");

		parameters = StringUtil.replace(
			parameters, "com.liferay.portal.kernel.search.Sort[] sorts",
			"String sortsString");

		parameters = StringUtil.replace(
			parameters, "Long siteId",
			"Long siteId, @GraphQLName(\"siteKey\") String siteKey");

		return parameters;
	}

	public String getGraphQLPropertyName(
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures) {

		String methodName = javaMethodSignature.getMethodName();

		if (!methodName.equals("getSite") &&
			!_hasMethodWithSameName(
				methodName.replaceFirst("Site", ""), javaMethodSignatures)) {

			methodName = methodName.replaceFirst("Site", "");
		}

		methodName = methodName.replaceFirst("get", "");

		String returnType = javaMethodSignature.getReturnType();

		if (returnType.contains("Collection<") ||
			returnType.contains("Page<")) {

			methodName = methodName.substring(
				0, methodName.lastIndexOf("Page"));
		}

		return StringUtil.lowerCaseFirstLetter(methodName);
	}

	public List<JavaMethodSignature> getGraphQLRelationJavaMethodSignatures(
		ConfigYAML configYAML, final String graphQLType,
		OpenAPIYAML openAPIYAML) {

		List<JavaMethodSignature> javaMethodSignatures =
			getGraphQLJavaMethodSignatures(
				configYAML, graphQLType, openAPIYAML);

		javaMethodSignatures.sort(
			Comparator.comparingInt(
				javaMethodSignature -> StringUtil.count(
					javaMethodSignature.getPath(), "/")));

		Map<String, Schema> schemas = _getSchemas(openAPIYAML);

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

				JavaMethodSignature relationJavaMethodSignature =
					_getGraphQLPathRelation(
						javaMethodSignature, javaMethodSignatures,
						parameterName, schemas);

				if (relationJavaMethodSignature != null) {
					javaMethodSignatureMap.put(
						methodName, relationJavaMethodSignature);
				}

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
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures) {

		String methodName = getGraphQLPropertyName(
			javaMethodSignature, javaMethodSignatures);

		return StringUtil.lowerCaseFirstLetter(
			methodName.replaceFirst(
				StringUtil.lowerCaseFirstLetter(
					javaMethodSignature.getParentSchemaName()),
				""));
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

	public String getRESTMethodJavadoc(
		ConfigYAML configYAML, JavaMethodSignature javaMethodSignature,
		OpenAPIYAML openAPIYAML) {

		StringBuilder sb = new StringBuilder();

		sb.append("Invoke this method with the command line:\n*\n* ");
		sb.append("curl -X '");
		sb.append(
			StringUtil.toUpperCase(
				OpenAPIParserUtil.getHTTPMethod(
					javaMethodSignature.getOperation())));
		sb.append("' 'http://localhost:8080/o");

		Application application = configYAML.getApplication();

		sb.append(application.getBaseURI());

		sb.append("/");

		Info info = openAPIYAML.getInfo();

		sb.append(info.getVersion());

		sb.append(javaMethodSignature.getPath());
		sb.append("' ");
		sb.append(_getRESTBody(javaMethodSignature, openAPIYAML));
		sb.append(" -u 'test@liferay.com:test'");

		return sb.toString();
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

	private String _getGraphQLBody(
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures,
		OpenAPIYAML openAPIYAML) {

		StringBuilder sb = new StringBuilder("{\"query\": \"query {");

		sb.append(
			getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures));

		Set<String> javaMethodParameterNames = new TreeSet<>();

		for (JavaMethodParameter javaMethodParameter :
				javaMethodSignature.getJavaMethodParameters()) {

			javaMethodParameterNames.add(
				javaMethodParameter.getParameterName());
		}

		if (!javaMethodParameterNames.isEmpty()) {
			sb.append("(");

			Iterator<String> iterator = javaMethodParameterNames.iterator();

			while (iterator.hasNext()) {
				sb.append(iterator.next());
				sb.append(": ___");

				if (iterator.hasNext()) {
					sb.append(", ");
				}
			}

			sb.append(")");
		}

		sb.append("{");

		String returnType = javaMethodSignature.getReturnType();

		if (returnType.startsWith("java.util.Collection<")) {
			sb.append("items {__}, ");
			sb.append("page, ");
			sb.append("pageSize, ");
			sb.append("totalCount");
		}
		else {
			Map<String, Schema> schemas = _getSchemas(openAPIYAML);

			String returnSchema = returnType.substring(
				returnType.lastIndexOf(".") + 1);

			if (schemas.containsKey(returnSchema)) {
				Schema schema = schemas.get(returnSchema);

				Map<String, Schema> propertySchemas =
					schema.getPropertySchemas();

				Set<String> strings = propertySchemas.keySet();

				Iterator<String> iterator = strings.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					sb.append(key);

					if (iterator.hasNext()) {
						sb.append(", ");
					}
				}
			}
		}

		sb.append("}}\"}");

		return sb.toString();
	}

	private JavaMethodSignature _getGraphQLPathRelation(
		JavaMethodSignature parentJavaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures, String parameterName,
		Map<String, Schema> schemas) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			if (parentJavaMethodSignature == javaMethodSignature) {
				continue;
			}

			List<JavaMethodParameter> javaMethodParameters =
				javaMethodSignature.getJavaMethodParameters();

			if (javaMethodParameters.size() != 1) {
				continue;
			}

			JavaMethodParameter javaMethodParameter = javaMethodParameters.get(
				0);

			String propertyName = StringUtil.upperCaseFirstLetter(
				javaMethodParameter.getParameterName());

			String returnType = javaMethodSignature.getReturnType();

			String schemaName = javaMethodSignature.getSchemaName();

			if ((returnType.endsWith(schemaName) &&
				 parameterName.equals(
					 javaMethodParameter.getParameterName())) ||
				parameterName.equals("parent" + propertyName)) {

				JavaMethodSignature relationJavaMethodSignature =
					_getJavaMethodSignature(
						parentJavaMethodSignature, schemaName);

				Schema schema = schemas.get(schemaName);

				Map<String, Schema> propertySchemas =
					schema.getPropertySchemas();

				if (propertySchemas.containsKey(
						getGraphQLRelationName(
							relationJavaMethodSignature,
							javaMethodSignatures))) {

					return null;
				}

				return relationJavaMethodSignature;
			}
		}

		return null;
	}

	private JavaMethodSignature _getJavaMethodSignature(
		JavaMethodSignature javaMethodSignature, String parentSchemaName) {

		return new JavaMethodSignature(
			javaMethodSignature.getPath(), javaMethodSignature.getPathItem(),
			javaMethodSignature.getOperation(),
			javaMethodSignature.getRequestBodyMediaTypes(),
			javaMethodSignature.getSchemaName(),
			javaMethodSignature.getJavaMethodParameters(),
			javaMethodSignature.getMethodName(),
			javaMethodSignature.getReturnType(), parentSchemaName);
	}

	private String _getRESTBody(
		JavaMethodSignature javaMethodSignature, OpenAPIYAML openAPIYAML) {

		StringBuilder sb = new StringBuilder();

		Set<String> properties = new TreeSet<>();

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			String parameterType = javaMethodParameter.getParameterType();

			String schemaName = parameterType.substring(
				parameterType.lastIndexOf(".") + 1);

			if (schemas.containsKey(schemaName)) {
				Schema schema = schemas.get(schemaName);

				Map<String, Schema> propertySchemas =
					schema.getPropertySchemas();

				if (propertySchemas == null) {
					continue;
				}

				for (Map.Entry<String, Schema> entry :
						propertySchemas.entrySet()) {

					Schema propertySchema = entry.getValue();

					if (!propertySchema.isReadOnly()) {
						properties.add(entry.getKey());
					}
				}
			}
		}

		if (!properties.isEmpty()) {
			sb.append("-d $'{");

			Iterator<String> iterator = properties.iterator();

			while (iterator.hasNext()) {
				sb.append("\"");
				sb.append(iterator.next());
				sb.append("\": ___");

				if (iterator.hasNext()) {
					sb.append(", ");
				}
			}

			sb.append("}' --header 'Content-Type: application/json'");
		}

		return sb.toString();
	}

	private Map<String, Schema> _getSchemas(OpenAPIYAML openAPIYAML) {
		Components components = openAPIYAML.getComponents();

		return components.getSchemas();
	}

	private boolean _hasMethodWithSameName(
		String methodName, List<JavaMethodSignature> javaMethodSignatures) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			if (methodName.equals(javaMethodSignature.getMethodName())) {
				return true;
			}
		}

		return false;
	}

	private boolean _isGraphQLPropertyRelation(
		JavaMethodSignature javaMethodSignature, String parameterName,
		String propertyName) {

		String returnType = StringUtil.toLowerCase(
			javaMethodSignature.getReturnType());
		String schemaName = StringUtil.replace(parameterName, "Id", "");

		if (propertyName.equals(parameterName) &&
			returnType.endsWith(StringUtil.toLowerCase(schemaName))) {

			return true;
		}

		return false;
	}

	private static FreeMarkerTool _freeMarkerTool = new FreeMarkerTool();

}