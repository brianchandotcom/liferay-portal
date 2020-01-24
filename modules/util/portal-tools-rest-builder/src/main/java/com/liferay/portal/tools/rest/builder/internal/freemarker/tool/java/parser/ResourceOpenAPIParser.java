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

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.Get;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * @author Peter Shin
 */
public class ResourceOpenAPIParser {

	public static List<JavaMethodSignature> getJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return Collections.emptyList();
		}

		Map<String, Parameter> parameterMap = OpenAPIParserUtil.getParameterMap(
			openAPIYAML);
		Map<String, String> javaDataTypeMap =
			OpenAPIParserUtil.getJavaDataTypeMap(configYAML, openAPIYAML);
		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();
			PathItem pathItem = entry.getValue();

			_visitOperations(
				pathItem,
				operation -> {
					List<Parameter> parameters = operation.getParameters();

					for (int i = 0; i < parameters.size(); i++) {
						Parameter parameter = parameters.get(i);

						if (Validator.isNotNull(parameter.getReference())) {
							parameters.set(
								i,
								parameterMap.get(
									OpenAPIParserUtil.getReferenceName(
										parameter.getReference())));
						}
					}

					String returnType = _getReturnType(
						javaDataTypeMap, operation, path);

					if (!_isSchemaMethod(
							javaDataTypeMap, returnType, schemaName,
							operation.getTags())) {

						return;
					}

					_visitRequestBodyMediaTypes(
						operation.getRequestBody(),
						requestBodyMediaTypes -> {
							List<JavaMethodParameter> javaMethodParameters =
								_getJavaMethodParameters(
									javaDataTypeMap, operation,
									requestBodyMediaTypes);
							String methodName = _getMethodName(
								operation, path, returnType, schemaName);

							javaMethodSignatures.add(
								new JavaMethodSignature(
									path, pathItem, operation,
									requestBodyMediaTypes, schemaName,
									javaMethodParameters, methodName,
									returnType));
						});
				});
		}

		return javaMethodSignatures;
	}

	public static String getMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		String path = javaMethodSignature.getPath();
		Operation operation = javaMethodSignature.getOperation();

		Set<String> methodAnnotations = new TreeSet<>();

		if (operation.getDescription() != null) {
			methodAnnotations.add(
				"@Operation(description=\"" + operation.getDescription() +
					"\")");
		}

		if (operation.getTags() != null) {
			StringBuilder sb = new StringBuilder("");

			for (String tag : operation.getTags()) {
				sb.append("@Tag(name=\"");
				sb.append(tag);
				sb.append("\"),");
			}

			methodAnnotations.add("@Tags(value={" + sb.toString() + "})");
		}

		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();

		StringBuilder sb = new StringBuilder("");

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			String parameterName = javaMethodParameter.getParameterName();

			if (parameterName.equals("pagination")) {
				sb.append(_addParameter(_findParameter(operation, "page")));
				sb.append(_addParameter(_findParameter(operation, "pageSize")));
			}
			else if (parameterName.equals("sorts")) {
				sb.append(_addParameter(_findParameter(operation, "sort")));
			}
			else {
				sb.append(
					_addParameter(_findParameter(operation, parameterName)));
			}
		}

		if (sb.length() > 0) {
			methodAnnotations.add("@Parameters(value={" + sb + "})");
		}

		methodAnnotations.add("@Path(\"" + path + "\")");

		String annotationString = StringUtil.toUpperCase(
			OpenAPIParserUtil.getHTTPMethod(operation));

		methodAnnotations.add("@" + annotationString);

		String methodAnnotation = _getMethodAnnotationConsumes(
			javaMethodSignature.getRequestBodyMediaTypes());

		if (Validator.isNotNull(methodAnnotation)) {
			methodAnnotations.add(methodAnnotation);
		}

		methodAnnotation = _getMethodAnnotationProduces(operation);

		if (Validator.isNotNull(methodAnnotation)) {
			methodAnnotations.add(methodAnnotation);
		}

		return StringUtil.merge(methodAnnotations, "\n");
	}

	public static String getParameters(
		List<JavaMethodParameter> javaMethodParameters, OpenAPIYAML openAPIYAML,
		Operation operation, boolean annotation) {

		StringBuilder sb = new StringBuilder();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			String parameterAnnotation = null;

			if (annotation) {
				parameterAnnotation = _getParameterAnnotation(
					javaMethodParameter, openAPIYAML, operation);
			}

			String parameter = OpenAPIParserUtil.getParameter(
				javaMethodParameter, parameterAnnotation);

			sb.append(parameter);

			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private static String _addParameter(Parameter parameter) {
		if (parameter == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(
			String.format(
				"@Parameter(in = ParameterIn.%s, name = \"%s\"",
				StringUtil.toUpperCase(parameter.getIn()),
				parameter.getName()));

		if (parameter.getExample() != null) {
			sb.append(
				String.format(", example = \"%s\"", parameter.getExample()));
		}

		sb.append("),");

		return sb.toString();
	}

	private static Parameter _findParameter(
		Operation operation, String parameterName) {

		for (Parameter parameter : operation.getParameters()) {
			if (parameterName.equals(parameter.getName())) {
				return parameter;
			}
		}

		return null;
	}

	private static String _getDefaultValue(
		OpenAPIYAML openAPIYAML, Schema schema) {

		if (schema.getDefault() != null) {
			return schema.getDefault();
		}
		else if (schema.getReference() != null) {
			Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(
				openAPIYAML);

			String referenceName = OpenAPIParserUtil.getReferenceName(
				schema.getReference());

			Schema referenceSchema = schemas.get(referenceName);

			if (referenceSchema == null) {
				Map<String, Schema> enumSchemas =
					OpenAPIUtil.getGlobalEnumSchemas(openAPIYAML);

				referenceSchema = enumSchemas.get(referenceName);
			}

			return referenceSchema.getDefault();
		}

		return null;
	}

	private static List<JavaMethodParameter> _getJavaMethodParameters(
		Map<String, String> javaDataTypeMap, Operation operation,
		Set<String> requestBodyMediaTypes) {

		if ((operation == null) || (operation.getParameters() == null)) {
			return Collections.emptyList();
		}

		List<JavaMethodParameter> javaMethodParameters = new ArrayList<>();

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

			if ((StringUtil.equals(parameterName, "page") ||
				 StringUtil.equals(parameterName, "pageSize")) &&
				parameterNames.contains("page") &&
				parameterNames.contains("pageSize")) {

				continue;
			}

			javaMethodParameters.add(
				new JavaMethodParameter(
					CamelCaseUtil.toCamelCase(parameterName),
					OpenAPIParserUtil.getJavaDataType(
						javaDataTypeMap, parameter.getSchema())));
		}

		String operationId = operation.getOperationId();

		if ((operationId != null) && operationId.endsWith("Permission") &&
			operationId.startsWith("put") && requestBodyMediaTypes.isEmpty()) {

			javaMethodParameters.add(
				new JavaMethodParameter(
					"permissions", Permission[].class.getName()));
		}

		if (parameterNames.contains("filter")) {
			JavaMethodParameter javaMethodParameter = new JavaMethodParameter(
				"filter", Filter.class.getName());

			javaMethodParameters.add(javaMethodParameter);
		}

		if (parameterNames.contains("page") &&
			parameterNames.contains("pageSize")) {

			JavaMethodParameter javaMethodParameter = new JavaMethodParameter(
				"pagination", Pagination.class.getName());

			javaMethodParameters.add(javaMethodParameter);
		}

		if (parameterNames.contains("sort")) {
			JavaMethodParameter javaMethodParameter = new JavaMethodParameter(
				"sorts", Sort[].class.getName());

			javaMethodParameters.add(javaMethodParameter);
		}

		if (!requestBodyMediaTypes.isEmpty()) {
			if (!requestBodyMediaTypes.contains("multipart/form-data")) {
				RequestBody requestBody = operation.getRequestBody();

				Map<String, Content> contents = requestBody.getContent();

				Iterator<String> iterator = requestBodyMediaTypes.iterator();

				Content content = contents.get(iterator.next());

				String parameterType = OpenAPIParserUtil.getJavaDataType(
					javaDataTypeMap, content.getSchema());

				if (Long.class.isInstance(parameterType)) {
					javaMethodParameters.add(
						new JavaMethodParameter("referenceId", parameterType));
				}
				else if (parameterType != null) {
					String simpleClassName = parameterType.substring(
						parameterType.lastIndexOf(".") + 1);

					String parameterName = TextFormatter.format(
						simpleClassName, TextFormatter.I);

					if (parameterType.startsWith("[")) {
						String elementClassName =
							OpenAPIParserUtil.getElementClassName(
								parameterType);

						simpleClassName = elementClassName.substring(
							elementClassName.lastIndexOf(".") + 1);

						parameterName = TextFormatter.formatPlural(
							TextFormatter.format(
								simpleClassName, TextFormatter.I));
					}

					javaMethodParameters.add(
						new JavaMethodParameter(parameterName, parameterType));
				}
			}
			else {
				javaMethodParameters.add(
					new JavaMethodParameter(
						"multipartBody", MultipartBody.class.getName()));
			}
		}

		return javaMethodParameters;
	}

	private static String _getMethodAnnotationConsumes(
		Set<String> requestBodyMediaTypes) {

		if (requestBodyMediaTypes.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (String requestBodyMediaType : requestBodyMediaTypes) {
			sb.append(StringUtil.quote(requestBodyMediaType, "\""));
			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		if (requestBodyMediaTypes.size() > 1) {
			return "@Consumes({" + sb.toString() + "})";
		}

		return "@Consumes(" + sb.toString() + ")";
	}

	private static String _getMethodAnnotationProduces(Operation operation) {
		Map<Integer, Response> responses = operation.getResponses();

		if ((responses == null) || responses.isEmpty()) {
			return null;
		}

		Set<String> mediaTypes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		for (Response response : responses.values()) {
			Map<String, Content> contents = response.getContent();

			if ((contents == null) || contents.isEmpty()) {
				continue;
			}

			mediaTypes.addAll(new ArrayList<>(contents.keySet()));
		}

		if (mediaTypes.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (String mediaType : mediaTypes) {
			sb.append(StringUtil.quote(mediaType, "\""));

			sb.append(", ");
		}

		sb.setLength(sb.length() - 2);

		if (mediaTypes.size() > 1) {
			return "@Produces({" + sb.toString() + "})";
		}

		return "@Produces(" + sb.toString() + ")";
	}

	private static String _getMethodName(
		Operation operation, String path, String returnType,
		String schemaName) {

		if (operation.getOperationId() != null) {
			return operation.getOperationId();
		}

		List<String> methodNameSegments = new ArrayList<>();

		methodNameSegments.add(OpenAPIParserUtil.getHTTPMethod(operation));

		String[] pathSegments = path.split("/");
		String pluralSchemaName = TextFormatter.formatPlural(schemaName);

		for (int i = 0; i < pathSegments.length; i++) {
			String pathSegment = pathSegments[i];

			if (pathSegment.isEmpty()) {
				continue;
			}

			String pathName = CamelCaseUtil.toCamelCase(
				pathSegment.replaceAll("\\{|-id|}|Id}", ""));

			if (StringUtil.equalsIgnoreCase(pathName, schemaName)) {
				pathName = schemaName;
			}
			else if (StringUtil.equalsIgnoreCase(pathName, pluralSchemaName)) {
				pathName = pluralSchemaName;
			}
			else {
				pathName = StringUtil.upperCaseFirstLetter(pathName);
			}

			if ((i == (pathSegments.length - 1)) &&
				StringUtil.startsWith(returnType, Page.class.getName() + "<")) {

				String previousMethodNameSegment = methodNameSegments.get(
					methodNameSegments.size() - 1);

				String pageClassName = Page.class.getName();

				String elementClassName = returnType.substring(
					pageClassName.length() + 1, returnType.length() - 1);

				String elementSimpleClassName = elementClassName.substring(
					elementClassName.lastIndexOf(".") + 1);

				if (Objects.equals(elementSimpleClassName, schemaName) &&
					!pathName.endsWith(pluralSchemaName) &&
					previousMethodNameSegment.endsWith(schemaName)) {

					String string = StringUtil.replaceLast(
						previousMethodNameSegment, schemaName,
						pluralSchemaName);

					methodNameSegments.set(
						methodNameSegments.size() - 1, string);
				}

				methodNameSegments.add(pathName + "Page");
			}
			else if (pathSegment.contains("{")) {
				String previousMethodNameSegment = methodNameSegments.get(
					methodNameSegments.size() - 1);

				if (!previousMethodNameSegment.endsWith(pathName) &&
					!previousMethodNameSegment.endsWith(schemaName)) {

					methodNameSegments.add(pathName);
				}
			}
			else if (Objects.equals(pathName, schemaName)) {
				methodNameSegments.add(pathName);
			}
			else if ((i != (pathSegments.length - 1)) ||
					 !Objects.equals(returnType, String.class.getName())) {

				methodNameSegments.add(OpenAPIUtil.formatSingular(pathName));
			}
			else {
				methodNameSegments.add(pathName);
			}
		}

		return StringUtil.merge(methodNameSegments, "");
	}

	private static String _getPageClassName(String returnType) {
		StringBuilder sb = new StringBuilder();

		sb.append(Page.class.getName());
		sb.append("<");
		sb.append(OpenAPIParserUtil.getElementClassName(returnType));
		sb.append(">");

		return sb.toString();
	}

	private static String _getParameterAnnotation(
		JavaMethodParameter javaMethodParameter, OpenAPIYAML openAPIYAML,
		Operation operation) {

		List<Parameter> parameters = operation.getParameters();

		Set<String> parameterNames = new HashSet<>();

		for (Parameter parameter : parameters) {
			parameterNames.add(parameter.getName());
		}

		String parameterType = javaMethodParameter.getParameterType();

		if (Objects.equals(parameterType, Filter.class.getName()) &&
			parameterNames.contains("filter")) {

			return "@Context";
		}

		if (Objects.equals(parameterType, Pagination.class.getName()) &&
			parameterNames.contains("page") &&
			parameterNames.contains("pageSize")) {

			return "@Context";
		}

		if (Objects.equals(parameterType, Sort[].class.getName()) &&
			parameterNames.contains("sort")) {

			return "@Context";
		}

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = CamelCaseUtil.toCamelCase(
				parameter.getName());

			if (!Objects.equals(
					parameterName, javaMethodParameter.getParameterName())) {

				continue;
			}

			StringBuilder sb = new StringBuilder();

			String defaultValue = _getDefaultValue(
				openAPIYAML, parameter.getSchema());

			if (defaultValue != null) {
				sb.append("@DefaultValue(\"");
				sb.append(defaultValue);
				sb.append("\")");
			}

			if (parameter.isRequired()) {
				sb.append("@NotNull");
			}

			sb.append("@Parameter(hidden=true)");
			sb.append("@");
			sb.append(StringUtil.upperCaseFirstLetter(parameter.getIn()));
			sb.append("Param(\"");
			sb.append(parameter.getName());
			sb.append("\")");

			return sb.toString();
		}

		return "";
	}

	private static String _getReturnType(
		Map<String, String> javaDataTypeMap, Operation operation, String path) {

		Map<Integer, Response> responses = operation.getResponses();

		if ((responses == null) || responses.isEmpty()) {
			return void.class.getName();
		}

		Integer httpStatusCode = null;
		Response response = null;

		for (Map.Entry<Integer, Response> entry : responses.entrySet()) {
			Integer curHttpStatusCode = entry.getKey();

			javax.ws.rs.core.Response.Status.Family family =
				javax.ws.rs.core.Response.Status.Family.familyOf(
					curHttpStatusCode);

			if (family != _FAMILY_SUCCESSFUL) {
				continue;
			}

			if ((httpStatusCode == null) ||
				(httpStatusCode > curHttpStatusCode)) {

				httpStatusCode = curHttpStatusCode;
				response = entry.getValue();
			}
		}

		if ((response != null) && (response.getContent() != null)) {
			Map<String, Content> sortedContents = new TreeMap<>();

			sortedContents.putAll(response.getContent());

			if (sortedContents.isEmpty()) {
				return void.class.getName();
			}

			if ((operation instanceof Get) && path.endsWith("permissions")) {
				return _getPageClassName(
					"[L" + Permission.class.getName() + ";");
			}

			for (Content content : sortedContents.values()) {
				Schema schema = content.getSchema();

				if (schema == null) {
					return void.class.getName();
				}

				String format = schema.getFormat();

				if ((format != null) && format.equals("binary")) {
					return javax.ws.rs.core.Response.class.getName();
				}

				String returnType = OpenAPIParserUtil.getJavaDataType(
					javaDataTypeMap, schema);

				if (returnType.startsWith("[")) {
					return _getPageClassName(returnType);
				}

				String schemaReference = schema.getReference();

				if ((schemaReference == null) ||
					!schemaReference.startsWith("#/components/schemas/")) {

					continue;
				}

				return javaDataTypeMap.get(
					OpenAPIParserUtil.getReferenceName(schemaReference));
			}
		}

		if (Get.class.isInstance(operation)) {
			return String.class.getName();
		}

		return javax.ws.rs.core.Response.class.getName();
	}

	private static boolean _isSchemaMethod(
		Map<String, String> javaDataTypeMap, String returnType,
		String schemaName, List<String> tags) {

		if (!tags.isEmpty()) {
			if (tags.contains(schemaName)) {
				return true;
			}

			return false;
		}

		if (returnType.equals(javaDataTypeMap.get(schemaName))) {
			return true;
		}

		if (returnType.startsWith(Page.class.getName() + "<") &&
			returnType.endsWith(">")) {

			String pageClassName = Page.class.getName();

			String className = returnType.substring(
				pageClassName.length() + 1, returnType.length() - 1);

			if (className.equals(javaDataTypeMap.get(schemaName))) {
				return true;
			}
		}

		return false;
	}

	private static void _visitOperations(
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

	private static void _visitRequestBodyMediaTypes(
		RequestBody requestBody, Consumer<Set<String>> consumer) {

		if (requestBody != null) {
			boolean multipartFormData = false;
			Set<String> requestBodyMediaTypes = new TreeSet<>();

			Map<String, Content> contents = requestBody.getContent();

			for (String requestBodyMediaType : contents.keySet()) {
				if (Objects.equals(
						requestBodyMediaType, "multipart/form-data")) {

					multipartFormData = true;
				}
				else {
					requestBodyMediaTypes.add(requestBodyMediaType);
				}
			}

			if (!requestBodyMediaTypes.isEmpty()) {
				consumer.accept(requestBodyMediaTypes);
			}

			if (multipartFormData) {
				consumer.accept(Collections.singleton("multipart/form-data"));
			}
		}
		else {
			consumer.accept(Collections.emptySet());
		}
	}

	private static final javax.ws.rs.core.Response.Status.Family
		_FAMILY_SUCCESSFUL = javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

}