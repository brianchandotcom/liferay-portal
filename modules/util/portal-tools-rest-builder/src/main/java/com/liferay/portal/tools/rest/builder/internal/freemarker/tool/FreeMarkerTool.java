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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.DTOOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.GraphQLOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceTestCaseOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Application;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Content;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Get;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Info;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.RequestBody;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;
import com.liferay.portal.vulcan.graphql.util.GraphQLNamingUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Peter Shin
 */
public class FreeMarkerTool {

	public static FreeMarkerTool getInstance() {
		return _freeMarkerTool;
	}

	public boolean containsAggregationFunction(
		List<JavaMethodSignature> javaMethodSignatures) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			for (JavaMethodParameter javaMethodParameter :
					javaMethodSignature.getJavaMethodParameters()) {

				if (StringUtil.equals(
						javaMethodParameter.getParameterType(),
						"com.liferay.portal.vulcan.aggregation.Aggregation")) {

					return true;
				}
			}
		}

		return false;
	}

	public boolean containsJavaMethodSignature(
		List<JavaMethodSignature> javaMethodSignatures, String text) {

		Stream<JavaMethodSignature> stream = javaMethodSignatures.stream();

		return stream.map(
			JavaMethodSignature::getMethodName
		).anyMatch(
			javaMethodSignatureMethodName ->
				javaMethodSignatureMethodName.contains(text)
		);
	}

	public boolean generateBatch(
		ConfigYAML configYAML, String javaDataType,
		List<JavaMethodSignature> javaMethodSignatures, String schemaName) {

		List<String> disabledBatchSchemaNames =
			configYAML.getDisabledBatchSchemaNames();

		if (!configYAML.isGenerateBatch() ||
			disabledBatchSchemaNames.contains(schemaName) ||
			(javaDataType == null) || javaDataType.isEmpty()) {

			return false;
		}

		if (ResourceOpenAPIParser.hasResourceBatchJavaMethodSignatures(
				javaMethodSignatures) ||
			ResourceOpenAPIParser.hasResourceGetPageJavaMethodSignature(
				javaDataType, javaMethodSignatures)) {

			return true;
		}

		return false;
	}

	public Map<String, Schema> getAllSchemas(
		Map<String, Schema> allExternalSchemas, OpenAPIYAML openAPIYAML,
		Map<String, Schema> schemas) {

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return Collections.emptyMap();
		}

		Set<Map.Entry<String, PathItem>> entries = pathItems.entrySet();

		for (Map.Entry<String, PathItem> entry : entries) {
			List<Operation> operations = OpenAPIParserUtil.getOperations(
				entry.getValue());

			for (Operation operation : operations) {
				List<String> tags = operation.getTags();

				for (String tag : tags) {
					if (!schemas.containsKey(tag)) {
						if ((allExternalSchemas != null) &&
							allExternalSchemas.containsKey(tag)) {

							schemas.put(tag, allExternalSchemas.get(tag));
						}
						else {
							schemas.put(tag, new Schema());
						}
					}
				}
			}
		}

		return schemas;
	}

	public List<JavaMethodParameter> getBodyJavaMethodParameters(
		JavaMethodSignature javaMethodSignature) {

		List<JavaMethodParameter> javaMethodParameters = new ArrayList<>();

		for (JavaMethodParameter javaMethodParameter :
				javaMethodSignature.getJavaMethodParameters()) {

			boolean exists = false;

			for (JavaMethodParameter pathJavaMethodParameter :
					javaMethodSignature.getPathJavaMethodParameters()) {

				if (Objects.equals(
						pathJavaMethodParameter.getParameterName(),
						javaMethodParameter.getParameterName()) &&
					Objects.equals(
						pathJavaMethodParameter.getParameterType(),
						javaMethodParameter.getParameterType())) {

					exists = true;

					break;
				}
			}

			if (!exists) {
				javaMethodParameters.add(javaMethodParameter);
			}
		}

		return javaMethodParameters;
	}

	public String getClientParameters(
		List<JavaMethodParameter> javaMethodParameters, String schemaName,
		String schemaVarName) {

		StringBuilder sb = new StringBuilder();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			String parameterAnnotation = null;

			String parameter = OpenAPIParserUtil.getParameter(
				javaMethodParameter, parameterAnnotation);

			if (parameter.contains("dto")) {
				sb.append(parameter.substring(parameter.lastIndexOf(".") + 1));
			}
			else {
				sb.append(parameter);
			}

			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		String parameter = sb.toString();

		parameter = StringUtil.replace(
			parameter, ".constant.", ".client.constant.");
		parameter = StringUtil.replace(
			parameter, "com.liferay.portal.kernel.search.filter.Filter filter",
			"String filterString");
		parameter = StringUtil.replace(
			parameter, "com.liferay.portal.kernel.search.Sort[] sorts",
			"String sortString");
		parameter = StringUtil.replace(
			parameter,
			"com.liferay.portal.vulcan.aggregation.Aggregation aggregation",
			"List<String> aggregations");
		parameter = StringUtil.replace(
			parameter,
			"com.liferay.portal.vulcan.multipart.MultipartBody multipartBody",
			StringBundler.concat(
				schemaName, " ", schemaVarName,
				", Map<String, File> multipartFiles"));
		parameter = StringUtil.removeSubstring(
			parameter, "com.liferay.portal.vulcan.pagination.");
		parameter = StringUtil.removeSubstring(
			parameter, "com.liferay.portal.vulcan.permission.");

		return parameter;
	}

	public Map<String, Schema> getDTOEnumSchemas(
		OpenAPIYAML openAPIYAML, Schema schema) {

		return DTOOpenAPIParser.getEnumSchemas(openAPIYAML, schema);
	}

	public String getDTOParentClassName(
		OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, Schema> schemas = getSchemas(openAPIYAML);

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

		Map<String, String> substitutions = HashMapBuilder.put(
			"aggregation",
			"_aggregationBiFunction.apply(" + schemaVarName +
				"Resource, aggregations)"
		).put(
			"assetLibraryId", "Long.valueOf(assetLibraryId)"
		).put(
			"filter",
			"_filterBiFunction.apply(" + schemaVarName +
				"Resource, filterString)"
		).put(
			"siteId", "Long.valueOf(siteKey)"
		).put(
			"sorts",
			"_sortsBiFunction.apply(" + schemaVarName + "Resource, sortsString)"
		).build();

		StringBuilder sb = new StringBuilder();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			sb.append(
				_replaceGraphQLParameter(
					javaMethodParameter.getParameterName(), substitutions));
			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return StringUtil.replace(
			sb.toString(), "pageSize,page", "Pagination.of(page, pageSize)");
	}

	public List<JavaMethodSignature> getGraphQLJavaMethodSignatures(
			ConfigYAML configYAML, String graphQLType, OpenAPIYAML openAPIYAML)
		throws Exception {

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

		return _getParentProperty(
			schemaName, javaMethodParameter, properties.keySet());
	}

	public String getGraphQLMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		return GraphQLOpenAPIParser.getMethodAnnotations(javaMethodSignature);
	}

	public String getGraphQLMethodJavadoc(
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures,
		OpenAPIYAML openAPIYAML) {

		return StringBundler.concat(
			"Invoke this method with the command line:\n*\n* curl -H ",
			"'Content-Type: text/plain; charset=utf-8' -X 'POST' ",
			"'http://localhost:8080/o/graphql' -d $'",
			_getGraphQLBody(
				javaMethodSignature, javaMethodSignatures, openAPIYAML),
			"' -u 'test@liferay.com:test'");
	}

	public String getGraphQLMutationName(String methodName) {
		return GraphQLNamingUtil.getGraphQLMutationName(methodName);
	}

	public String getGraphQLParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		String parameters = GraphQLOpenAPIParser.getParameters(
			javaMethodParameters, operation, annotation);

		parameters = StringUtil.replace(
			parameters,
			"@GraphQLName(\"assetLibraryId\") java.lang.Long assetLibraryId",
			"@GraphQLName(\"assetLibraryId\") @NotEmpty String assetLibraryId");
		parameters = StringUtil.replace(
			parameters, "@GraphQLName(\"siteId\") java.lang.Long siteId",
			"@GraphQLName(\"siteKey\") @NotEmpty String siteKey");
		parameters = StringUtil.replace(
			parameters, "com.liferay.portal.kernel.search.filter.Filter filter",
			"String filterString");
		parameters = StringUtil.replace(
			parameters, "com.liferay.portal.kernel.search.Sort[] sorts",
			"String sortsString");
		parameters = StringUtil.replace(
			parameters,
			"com.liferay.portal.vulcan.aggregation.Aggregation aggregation",
			"List<String> aggregations");

		return parameters;
	}

	public String getGraphQLPropertyName(
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures) {

		Stream<JavaMethodSignature> stream = javaMethodSignatures.stream();

		return GraphQLNamingUtil.getGraphQLPropertyName(
			javaMethodSignature.getMethodName(),
			javaMethodSignature.getReturnType(),
			stream.map(
				JavaMethodSignature::getMethodName
			).collect(
				Collectors.toList()
			));
	}

	public List<JavaMethodSignature> getGraphQLRelationJavaMethodSignatures(
			ConfigYAML configYAML, String graphQLType, OpenAPIYAML openAPIYAML)
		throws Exception {

		List<JavaMethodSignature> javaMethodSignatures =
			_getSortedJavaMethodSignatures(
				configYAML, graphQLType, openAPIYAML);

		Map<String, Schema> schemas = getSchemas(openAPIYAML);

		Map<String, JavaMethodSignature> javaMethodSignatureMap =
			new HashMap<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			List<JavaMethodParameter> javaMethodParameters =
				javaMethodSignature.getJavaMethodParameters();

			if (javaMethodParameters.isEmpty()) {
				continue;
			}

			JavaMethodParameter javaMethodParameter = javaMethodParameters.get(
				0);

			String parameterName = javaMethodParameter.getParameterName();

			String methodName = javaMethodSignature.getMethodName();

			JavaMethodSignature relationJavaMethodSignature =
				_getGraphQLPathRelation(
					javaMethodSignature, javaMethodSignatures, parameterName,
					schemas);

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
								propertyName, entry.getKey())) {

							javaMethodSignatureMap.put(
								methodName,
								_getJavaMethodSignature(
									javaMethodSignature, entry.getKey()));
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

	public String getJavaDataType(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, String> javaDataTypeMap =
			OpenAPIParserUtil.getJavaDataTypeMap(configYAML, openAPIYAML);

		return javaDataTypeMap.get(schemaName);
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

	public List<JavaMethodSignature>
			getParentGraphQLRelationJavaMethodSignatures(
				ConfigYAML configYAML, String graphQLType,
				OpenAPIYAML openAPIYAML)
		throws Exception {

		Map<String, Schema> schemas = getSchemas(openAPIYAML);

		List<JavaMethodSignature> javaMethodSignatures =
			_getSortedJavaMethodSignatures(
				configYAML, graphQLType, openAPIYAML);

		List<JavaMethodSignature> parentJavaMethodSignatures =
			new ArrayList<>();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			Schema schema = entry.getValue();

			Map<String, Schema> propertySchemas = schema.getPropertySchemas();

			if (propertySchemas == null) {
				continue;
			}

			Set<String> propertyNames = propertySchemas.keySet();

			for (String propertyName : propertyNames) {
				if (!propertyName.startsWith("parent") ||
					propertyNames.contains(
						StringUtil.removeSubstring(propertyName, "Id"))) {

					continue;
				}

				propertyName = StringUtil.lowerCaseFirstLetter(
					StringUtil.removeSubstring(propertyName, "parent"));

				for (JavaMethodSignature javaMethodSignature :
						javaMethodSignatures) {

					List<JavaMethodParameter> javaMethodParameters =
						javaMethodSignature.getJavaMethodParameters();

					if (javaMethodParameters.isEmpty()) {
						continue;
					}

					JavaMethodParameter javaMethodParameter =
						javaMethodParameters.get(0);

					if (!propertyName.equals(
							javaMethodParameter.getParameterName())) {

						continue;
					}

					parentJavaMethodSignatures.add(
						_getJavaMethodSignature(
							javaMethodSignature, entry.getKey()));

					break;
				}
			}
		}

		return parentJavaMethodSignatures;
	}

	public JavaMethodSignature getPostSchemaJavaMethodSignature(
		List<JavaMethodSignature> javaMethodSignatures, String parameterName,
		String schemaName) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			Operation operation = javaMethodSignature.getOperation();

			if (!Objects.equals("post", getHTTPMethod(operation))) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

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

		StringBundler sb = new StringBundler(10);

		sb.append("Invoke this method with the command line:\n*\n* curl -X '");
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

	public Map<String, Schema> getSchemas(OpenAPIYAML openAPIYAML) {
		Components components = openAPIYAML.getComponents();

		if (components == null) {
			return new HashMap<>();
		}

		return new TreeMap<>(components.getSchemas());
	}

	public String getSchemaVarName(String schemaName) {
		return OpenAPIParserUtil.getSchemaVarName(schemaName);
	}

	public String getVersion(OpenAPIYAML openAPIYAML) {
		return OpenAPIParserUtil.getVersion(openAPIYAML);
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

	public boolean hasParameter(
		JavaMethodSignature javaMethodSignature, String parameterName) {

		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			if (parameterName.equals(javaMethodParameter.getParameterName())) {
				return true;
			}
		}

		return false;
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

		StringBundler sb = new StringBundler("{\"query\": \"query {");

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
				String javaMethodParameterName = iterator.next();

				javaMethodParameterName = StringUtil.replace(
					javaMethodParameterName, "siteId", "siteKey");

				sb.append(javaMethodParameterName);

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
			sb.append("items {__}, page, pageSize, totalCount");
		}
		else {
			Map<String, Schema> schemas = getSchemas(openAPIYAML);

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
				 !parameterName.equals("id") &&
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

				if ((relationJavaMethodSignature.getParentSchemaName() !=
						null) &&
					!returnType.contains("Collection<")) {

					Schema parentSchema = schemas.get(
						relationJavaMethodSignature.getParentSchemaName());

					Map<String, Schema> parentPropertySchemas =
						parentSchema.getPropertySchemas();

					String parentProperty = _getParentProperty(
						relationJavaMethodSignature.getParentSchemaName(),
						javaMethodParameter, parentPropertySchemas.keySet());

					if (parentProperty == null) {
						continue;
					}
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

	private String _getParentProperty(
		String schemaName, JavaMethodParameter javaMethodParameter,
		Set<String> properties) {

		String parameterName = StringUtil.toLowerCase(
			javaMethodParameter.getParameterName());

		schemaName = StringUtil.toLowerCase(schemaName);

		String shortParameterName = StringUtil.removeSubstring(
			parameterName, schemaName);

		shortParameterName = StringUtil.removeSubstring(
			shortParameterName, "parent");

		for (String propertyKey : properties) {
			if (StringUtil.equalsIgnoreCase(parameterName, propertyKey) ||
				StringUtil.equalsIgnoreCase(shortParameterName, propertyKey)) {

				return StringUtil.upperCaseFirstLetter(propertyKey);
			}
		}

		return null;
	}

	private String _getRESTBody(
		JavaMethodSignature javaMethodSignature, OpenAPIYAML openAPIYAML) {

		StringBundler sb = new StringBundler();

		Set<String> properties = new TreeSet<>();

		Map<String, Schema> schemas = getSchemas(openAPIYAML);

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

	private List<JavaMethodSignature> _getSortedJavaMethodSignatures(
			ConfigYAML configYAML, String graphQLType, OpenAPIYAML openAPIYAML)
		throws Exception {

		List<JavaMethodSignature> javaMethodSignatures =
			getGraphQLJavaMethodSignatures(
				configYAML, graphQLType, openAPIYAML);

		javaMethodSignatures.sort(
			Comparator.comparingInt(
				javaMethodSignature -> StringUtil.count(
					javaMethodSignature.getPath(), "/")));

		return javaMethodSignatures;
	}

	private boolean _isGraphQLPropertyRelation(
		JavaMethodSignature javaMethodSignature, String parameterName,
		String propertyName, String schemaName) {

		String returnType = StringUtil.toLowerCase(
			javaMethodSignature.getReturnType());
		String parameterSchemaName = StringUtil.removeSubstring(
			parameterName, "Id");

		if (!StringUtil.equalsIgnoreCase(schemaName, parameterSchemaName) &&
			StringUtil.equals(propertyName, parameterName) &&
			returnType.endsWith(StringUtil.toLowerCase(parameterSchemaName))) {

			return true;
		}

		return false;
	}

	private String _replaceGraphQLParameter(
		String parameterName, Map<String, String> substitutions) {

		for (Map.Entry<String, String> entry : substitutions.entrySet()) {
			if (parameterName.equals(entry.getKey())) {
				return entry.getValue();
			}
		}

		return parameterName;
	}

	private static final FreeMarkerTool _freeMarkerTool = new FreeMarkerTool();

}