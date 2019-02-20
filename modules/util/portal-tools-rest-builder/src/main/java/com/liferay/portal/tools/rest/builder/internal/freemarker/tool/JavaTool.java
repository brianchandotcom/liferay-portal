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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.tools.rest.builder.internal.util.PathUtil;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Peter Shin
 */
public class JavaTool {

	public static JavaTool getInstance() {
		return _instance;
	}

	public Map<String, List<JavaMethodSignature>>
		getGraphQLJavaMethodSignatures(
			OpenAPIYAML openAPIYAML, boolean graphQLQuery) {

		Map<String, List<JavaMethodSignature>> javaMethodSignaturesMap =
			new TreeMap<>();

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (String schemaName : schemas.keySet()) {
			List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

			for (JavaMethodSignature javaMethodSignature :
					getJavaMethodSignatures(openAPIYAML, schemaName)) {

				Operation operation = javaMethodSignature.getOperation();

				String httpMethod = getHTTPMethod(operation);

				if (graphQLQuery) {
					if (!Objects.equals(httpMethod, "get")) {
						continue;
					}
				}
				else if (Objects.equals(httpMethod, "get")) {
					continue;
				}

				List<JavaParameter> javaParameters = new ArrayList<>();

				for (JavaParameter javaParameter :
						javaMethodSignature.getJavaParameters()) {

					String parameterType = javaParameter.getParameterType();

					if (StringUtil.equals(parameterType, "Pagination")) {
						javaParameters.add(
							new JavaParameter(
								javaParameter.getOperation(), "pageSize",
								"int"));

						javaParameters.add(
							new JavaParameter(
								javaParameter.getOperation(), "page", "int"));
					}
					else {
						javaParameters.add(javaParameter);
					}
				}

				String returnType = javaMethodSignature.getReturnType();

				if (returnType.startsWith("Page<")) {
					returnType = "Collection<".concat(returnType.substring(5));
				}

				javaMethodSignatures.add(
					new JavaMethodSignature(
						javaMethodSignature.getPath(),
						javaMethodSignature.getPathItem(),
						javaMethodSignature.getOperation(), javaParameters,
						javaMethodSignature.getMethodName(), returnType));
			}

			if (!javaMethodSignatures.isEmpty()) {
				javaMethodSignaturesMap.put(schemaName, javaMethodSignatures);
			}
		}

		return javaMethodSignaturesMap;
	}

	public Set<String> getGraphQLMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		Set<String> methodAnnotations = new TreeSet<>();

		methodAnnotations.add("@GraphQLInvokeDetached");

		String httpMethod = getHTTPMethod(javaMethodSignature.getOperation());

		if (Objects.equals(httpMethod, "get") ||
			Objects.equals(httpMethod, "post")) {

			methodAnnotations.add("@GraphQLField");
		}

		return methodAnnotations;
	}

	public String getGraphQLParameterAnnotation(JavaParameter javaParameter) {
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

	public String getHTTPMethod(Operation operation) {
		Class<? extends Operation> clazz = operation.getClass();

		return StringUtil.lowerCase(clazz.getSimpleName());
	}

	public List<JavaParameter> getJavaParameters(Schema schema) {
		Map<String, Schema> propertySchemas = null;

		Items items = schema.getItems();

		if (items != null) {
			propertySchemas = items.getPropertySchemas();
		}
		else {
			propertySchemas = schema.getPropertySchemas();
		}

		if (propertySchemas == null) {
			return Collections.emptyList();
		}

		List<JavaParameter> javaParameters = new ArrayList<>(
			propertySchemas.size());

		for (Map.Entry<String, Schema> entry : propertySchemas.entrySet()) {
			String propertySchemaName = entry.getKey();
			Schema propertySchema = entry.getValue();

			String parameterName = CamelCaseUtil.toCamelCase(
				propertySchemaName, false);
			String parameterType = _getJavaParameterType(
				propertySchemaName, propertySchema);

			javaParameters.add(
				new JavaParameter(null, parameterName, parameterType));
		}

		return javaParameters;
	}

	public List<JavaMethodSignature> getJavaMethodSignatures(
		OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return Collections.emptyList();
		}

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();
			PathItem pathItem = entry.getValue();

			_visitOperations(
				pathItem,
				operation -> {
					String returnType = _getReturnType(openAPIYAML, operation);

					if (_isSchemaMethod(
							schemaName, operation.getTags(), returnType)) {

						String methodName = _getMethodName(
							operation, path, returnType, schemaName);

						JavaMethodSignature javaMethodSignature =
							new JavaMethodSignature(
								path, pathItem, operation,
								_getJavaParameters(operation), methodName,
								returnType);

						javaMethodSignatures.add(javaMethodSignature);
					}
				});
		}

		return javaMethodSignatures;
	}

	public Set<String> getMethodAnnotations(
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

		return methodAnnotations;
	}

	public String getParameterAnnotation(JavaParameter javaParameter) {
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

	public boolean hasHTTPMethod(
		JavaMethodSignature javaMethodSignature, String... httpMethods) {

		Operation operation = javaMethodSignature.getOperation();

		for (String httpMethod : httpMethods) {
			if (Objects.equals(httpMethod, getHTTPMethod(operation))) {
				return true;
			}
		}

		return false;
	}

	private JavaTool() {
	}

	private Schema _getComponentSchema(
		OpenAPIYAML openAPIYAML, String reference) {

		if ((reference == null) ||
			!reference.startsWith("#/components/schemas/")) {

			return null;
		}

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		return schemas.get(_getComponentType(reference));
	}

	private String _getComponentType(String reference) {
		int index = reference.lastIndexOf('/');

		if (index == -1) {
			return reference;
		}

		return reference.substring(index + 1);
	}

	private String _getJavaDataType(
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

	private JavaParameter _getJavaParameter(
		Operation operation, Parameter parameter) {

		String parameterName = CamelCaseUtil.toCamelCase(
			parameter.getName(), false);
		String parameterType = _getJavaParameterType(
			null, parameter.getSchema());

		return new JavaParameter(operation, parameterName, parameterType);
	}

	private List<JavaParameter> _getJavaParameters(Operation operation) {
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
					String schemaName = _getJavaParameterType(
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

	private String _getJavaParameterType(
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

	private List<String> _getMediaTypes(Map<String, Content> contents) {
		if ((contents == null) || contents.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> mediaTypes = new ArrayList<>(contents.keySet());

		Collections.sort(mediaTypes);

		return mediaTypes;
	}

	private String _getMethodAnnotationConsumes(Operation operation) {
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

	private String _getMethodAnnotationProduces(Operation operation) {
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

	private String _getMethodName(
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

	private List<Parameter> _getPathParameters(List<Parameter> parameters) {
		Stream<Parameter> stream = parameters.stream();

		return stream.filter(
			parameter -> StringUtil.equals(parameter.getIn(), "path")
		).collect(
			Collectors.toList()
		);
	}

	private String _getReturnType(
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

				String javaParameterType = _getJavaParameterType(null, schema);

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

		return "boolean";
	}

	private boolean _isPostToSameSchema(
		String httpMethod, String path, String schemaName, int segmentNumber) {

		String lastSegment = PathUtil.getLastSegment(path, segmentNumber);

		if (httpMethod.equals("post") &&
			lastSegment.startsWith(TextFormatter.formatPlural(schemaName))) {

			return true;
		}

		return false;
	}

	private boolean _isSchemaMethod(
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

	private void _visitOperations(
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

	private static JavaTool _instance = new JavaTool();

}