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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Content;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Delete;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Get;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Post;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Put;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.RequestBody;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Response;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.ResponseCode;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.Permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

		Map<String, String> javaDataTypeMap =
			OpenAPIParserUtil.getJavaDataTypeMap(configYAML, openAPIYAML);
		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();
			PathItem pathItem = entry.getValue();

			_visitOperations(
				pathItem,
				operation -> {
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
								operation, path, returnType, schemaName,
								configYAML.isForcePredictableOperationId());

							JavaMethodSignature javaMethodSignature =
								new JavaMethodSignature(
									path, pathItem, operation,
									requestBodyMediaTypes, schemaName,
									javaMethodParameters, methodName,
									returnType,
									_getParentSchema(
										path, pathItems, schemaName));

							javaMethodSignatures.add(javaMethodSignature);

							if (configYAML.isGenerateBatch()) {
								_addBatchJavaMethodSignature(
									javaMethodSignature, javaMethodSignatures);
							}
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

		if ((operation.getDescription() != null) || operation.isDeprecated()) {
			StringBundler sb = new StringBundler(
				"@io.swagger.v3.oas.annotations.Operation(");

			if (operation.isDeprecated()) {
				methodAnnotations.add("@Deprecated");
				sb.append("deprecated=true");
			}

			if (operation.getDescription() != null) {
				if (operation.isDeprecated()) {
					sb.append(", ");
				}

				sb.append("description=\"");
				sb.append(operation.getDescription());
				sb.append("\"");
			}

			sb.append(")");

			methodAnnotations.add(sb.toString());
		}

		if (operation.getTags() != null) {
			StringBundler sb = new StringBundler("");

			for (String tag : operation.getTags()) {
				sb.append("@io.swagger.v3.oas.annotations.tags.Tag(name=\"");
				sb.append(tag);
				sb.append("\"),");
			}

			methodAnnotations.add(
				"@io.swagger.v3.oas.annotations.tags.Tags(value={" +
					sb.toString() + "})");
		}

		StringBundler sb = new StringBundler("");

		for (Parameter parameter : operation.getParameters()) {
			if (StringUtil.equals(parameter.getIn(), "header")) {
				continue;
			}

			sb.append(
				_addParameter(_findParameter(operation, parameter.getName())));
		}

		if (sb.length() > 0) {
			methodAnnotations.add(
				"@io.swagger.v3.oas.annotations.Parameters(value={" + sb +
					"})");
		}

		methodAnnotations.add("@javax.ws.rs.Path(\"" + path + "\")");

		String annotationString = StringUtil.toUpperCase(
			OpenAPIParserUtil.getHTTPMethod(operation));

		methodAnnotations.add("@javax.ws.rs." + annotationString);

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

			sb.append(
				OpenAPIParserUtil.getParameter(
					javaMethodParameter, parameterAnnotation));
			sb.append(',');
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	public static Set<String> getVulcanBatchImplementationCreateStrategies(
		List<JavaMethodSignature> javaMethodSignatures,
		Map<String, String> properties) {

		Set<String> createStrategies = new HashSet<>();

		Set<String> propertyNames = properties.keySet();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			String methodName = javaMethodSignature.getMethodName();
			String parentSchemaName = javaMethodSignature.getParentSchemaName();
			String schemaName = javaMethodSignature.getSchemaName();

			if (parentSchemaName == null) {
				parentSchemaName = "";
			}

			if (methodName.equals("post" + parentSchemaName + schemaName)) {
				createStrategies.add("INSERT");
			}
			else if ((methodName.equals("putByExternalReferenceCode") ||
					  methodName.equals(
						  StringBundler.concat(
							  "put", parentSchemaName, schemaName,
							  "ByExternalReferenceCode"))) &&
					 propertyNames.contains("externalReferenceCode")) {

				createStrategies.add("UPSERT");
			}
		}

		return createStrategies;
	}

	public static Set<String> getVulcanBatchImplementationUpdateStrategies(
		List<JavaMethodSignature> javaMethodSignatures) {

		Set<String> updateStrategies = new HashSet<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			String methodName = javaMethodSignature.getMethodName();
			String schemaName = javaMethodSignature.getSchemaName();

			if (methodName.equals("patch" + schemaName)) {
				updateStrategies.add("PARTIAL_UPDATE");
			}
			else if (methodName.equals("put" + schemaName)) {
				updateStrategies.add("UPDATE");
			}
		}

		return updateStrategies;
	}

	public static boolean hasReadVulcanBatchImplementation(
		List<JavaMethodSignature> javaMethodSignatures) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			String methodName = javaMethodSignature.getMethodName();
			String parentSchemaName = javaMethodSignature.getParentSchemaName();
			String schemaName = javaMethodSignature.getSchemaName();

			if (parentSchemaName == null) {
				parentSchemaName = "";
			}

			if (methodName.equals(
					StringBundler.concat(
						"get", parentSchemaName, schemaName, "sPage"))) {

				return true;
			}
		}

		return false;
	}

	public static boolean hasResourceBatchJavaMethodSignatures(
		List<JavaMethodSignature> javaMethodSignatures) {

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			String methodName = javaMethodSignature.getMethodName();

			if (methodName.endsWith("Batch")) {
				return true;
			}
		}

		return false;
	}

	public static boolean hasResourceGetPageJavaMethodSignature(
		String javaDataType, List<JavaMethodSignature> javaMethodSignatures) {

		String pageJavaDataType = StringBundler.concat(
			Page.class.getName(), "<", javaDataType, ">");

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			if (StringUtil.equals(
					pageJavaDataType, javaMethodSignature.getReturnType())) {

				return true;
			}
		}

		return false;
	}

	private static void _addBatchJavaMethodSignature(
		JavaMethodSignature javaMethodSignature,
		List<JavaMethodSignature> javaMethodSignatures) {

		String parentSchemaName = javaMethodSignature.getParentSchemaName();

		if (parentSchemaName == null) {
			parentSchemaName = "";
		}

		String methodName = javaMethodSignature.getMethodName();

		String schemaName = javaMethodSignature.getSchemaName();

		if (methodName.equals("delete" + schemaName) ||
			methodName.equals("post" + parentSchemaName + schemaName) ||
			methodName.equals(
				StringBundler.concat(
					"post", parentSchemaName, "Id", schemaName)) ||
			methodName.equals("put" + schemaName)) {

			String batchPath = StringUtil.removeSubstring(
				javaMethodSignature.getPath(),
				"/{" + StringUtil.lowerCaseFirstLetter(schemaName) + "Id}");

			batchPath = StringUtil.removeSubstring(batchPath, "/{id}");

			Operation batchOperation = _getBatchOperation(
				javaMethodSignature, methodName, schemaName);

			for (JavaMethodSignature existingJavaMethodSignature :
					javaMethodSignatures) {

				String httpMethod = OpenAPIParserUtil.getHTTPMethod(
					existingJavaMethodSignature.getOperation());

				if (Objects.equals(
						existingJavaMethodSignature.getPath(),
						batchPath + "/batch") &&
					httpMethod.equals(
						OpenAPIParserUtil.getHTTPMethod(batchOperation))) {

					return;
				}
			}

			List<JavaMethodParameter> javaMethodParameters = new ArrayList<>();

			for (JavaMethodParameter javaMethodParameter :
					javaMethodSignature.getJavaMethodParameters()) {

				if (_isValidParameter(
						javaMethodParameter.getParameterName(), schemaName)) {

					javaMethodParameters.add(javaMethodParameter);
				}
			}

			javaMethodParameters.add(
				new JavaMethodParameter("callbackURL", "String"));
			javaMethodParameters.add(
				new JavaMethodParameter("object", "Object"));

			javaMethodSignatures.add(
				new JavaMethodSignature(
					batchPath + "/batch", javaMethodSignature.getPathItem(),
					batchOperation,
					Collections.singleton(ContentTypes.APPLICATION_JSON),
					schemaName, javaMethodParameters, methodName + "Batch",
					"javax.ws.rs.core.Response", parentSchemaName));
		}
	}

	private static String _addParameter(Parameter parameter) {
		if (parameter == null) {
			return "";
		}

		StringBundler sb = new StringBundler(4);

		sb.append(
			StringBundler.concat(
				"@io.swagger.v3.oas.annotations.Parameter(in = ",
				"io.swagger.v3.oas.annotations.enums.ParameterIn.",
				StringUtil.toUpperCase(parameter.getIn()), ", name = \"",
				parameter.getName(), "\""));

		if (parameter.isDeprecated()) {
			sb.append(
				String.format(", deprecated = %s", parameter.isDeprecated()));
		}

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

	private static Operation _getBatchOperation(
		JavaMethodSignature javaMethodSignature, String methodName,
		String schemaName) {

		Operation batchOperation = null;

		if (methodName.startsWith("delete")) {
			batchOperation = new Delete();
		}
		else if (methodName.startsWith("post")) {
			batchOperation = new Post();
		}
		else {
			batchOperation = new Put();
		}

		Operation operation = javaMethodSignature.getOperation();

		if (batchOperation.getOperationId() != null) {
			batchOperation.setOperationId(operation.getOperationId() + "Batch");
		}

		if (operation.isDeprecated()) {
			batchOperation.setDeprecated(true);
		}

		batchOperation.setParameters(
			_getBatchParameters(operation, schemaName));
		batchOperation.setTags(operation.getTags());

		Response response = new Response();

		Content content = new Content();

		content.setSchema(new Schema());

		response.setContent(
			Collections.singletonMap("application/json", content));

		batchOperation.setResponses(
			HashMapBuilder.put(
				new ResponseCode("200"), response
			).build());

		return batchOperation;
	}

	private static List<Parameter> _getBatchParameters(
		Operation operation, String schemaName) {

		List<Parameter> parameters = new ArrayList<>();

		for (Parameter parameter : operation.getParameters()) {
			if (_isValidParameter(parameter.getName(), schemaName)) {
				parameters.add(parameter);
			}
		}

		parameters.add(_getCallbackURLParameter());

		return parameters;
	}

	private static Parameter _getCallbackURLParameter() {
		Parameter parameter = new Parameter();

		parameter.setIn("query");
		parameter.setName("callbackURL");

		Schema schema = new Schema();

		schema.setType("String");

		parameter.setSchema(schema);

		return parameter;
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
				StringUtil.equals(parameterName, "aggregationTerms") ||
				StringUtil.equals(parameterName, "fields") ||
				StringUtil.equals(parameterName, "filter") ||
				StringUtil.equals(parameterName, "nestedFields") ||
				StringUtil.equals(parameterName, "restrictFields") ||
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

		if ((operationId != null) && operationId.endsWith("PermissionsPage") &&
			operationId.startsWith("put") && requestBodyMediaTypes.isEmpty()) {

			javaMethodParameters.add(
				new JavaMethodParameter(
					"permissions", Permission[].class.getName()));
		}

		if (parameterNames.contains("aggregationTerms")) {
			JavaMethodParameter javaMethodParameter = new JavaMethodParameter(
				"aggregation", Aggregation.class.getName());

			javaMethodParameters.add(javaMethodParameter);
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
			if (requestBodyMediaTypes.contains(
					"application/x-www-form-urlencoded")) {

				throw new RuntimeException(
					"application/x-www-form-urlencoded is not supported");
			}
			else if (!requestBodyMediaTypes.contains("multipart/form-data")) {
				RequestBody requestBody = operation.getRequestBody();

				Map<String, Content> contents = requestBody.getContent();

				Iterator<String> iterator = requestBodyMediaTypes.iterator();

				Content content = contents.get(iterator.next());

				String parameterType = OpenAPIParserUtil.getJavaDataType(
					javaDataTypeMap, content.getSchema());

				String simpleClassName = parameterType.substring(
					parameterType.lastIndexOf(".") + 1);

				String parameterName = TextFormatter.format(
					simpleClassName, TextFormatter.I);

				if (parameterType.startsWith("[")) {
					String elementClassName =
						OpenAPIParserUtil.getElementClassName(parameterType);

					simpleClassName = elementClassName.substring(
						elementClassName.lastIndexOf(".") + 1);

					parameterName = TextFormatter.formatPlural(
						TextFormatter.format(simpleClassName, TextFormatter.I));
				}

				javaMethodParameters.add(
					new JavaMethodParameter(parameterName, parameterType));
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
			return "@javax.ws.rs.Consumes({" + sb.toString() + "})";
		}

		return "@javax.ws.rs.Consumes(" + sb.toString() + ")";
	}

	private static String _getMethodAnnotationProduces(Operation operation) {
		Map<ResponseCode, Response> responses = operation.getResponses();

		if ((responses == null) || responses.isEmpty()) {
			return null;
		}

		Set<String> mediaTypes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		for (Response response : responses.values()) {
			if (response == null) {
				continue;
			}

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
			return "@javax.ws.rs.Produces({" + sb.toString() + "})";
		}

		return "@javax.ws.rs.Produces(" + sb.toString() + ")";
	}

	private static String _getMethodName(
		Operation operation, String path, String returnType, String schemaName,
		boolean forcePredictableOperationId) {

		if (!forcePredictableOperationId &&
			(operation.getOperationId() != null)) {

			return operation.getOperationId();
		}

		boolean collection = StringUtil.startsWith(
			returnType, Page.class.getName() + "<");

		List<String> methodNameSegments = new ArrayList<>();

		methodNameSegments.add(OpenAPIParserUtil.getHTTPMethod(operation));

		String[] pathSegments = path.split("/");
		String pluralSchemaName = TextFormatter.formatPlural(schemaName);

		for (int i = 0; i < pathSegments.length; i++) {
			String pathSegment = pathSegments[i];

			if (pathSegment.isEmpty()) {
				if (pathSegments.length != 1) {
					continue;
				}

				if (collection) {
					pathSegment = pluralSchemaName;
				}
				else {
					pathSegment = schemaName;
				}
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

			if ((i == (pathSegments.length - 1)) && collection) {
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

				String segment = OpenAPIUtil.formatSingular(pathName);

				String s = StringUtil.toLowerCase(segment);

				if (s.endsWith(StringUtil.toLowerCase(schemaName))) {
					char c = segment.charAt(
						segment.length() - schemaName.length());

					if (Character.isUpperCase(c)) {
						String substring = segment.substring(
							0, segment.length() - schemaName.length());

						segment = substring + schemaName;
					}
				}

				methodNameSegments.add(segment);
			}
			else {
				methodNameSegments.add(pathName);
			}
		}

		return StringUtil.merge(methodNameSegments, "");
	}

	private static String _getPageClassName(String returnType) {
		return StringBundler.concat(
			Page.class.getName(), "<",
			OpenAPIParserUtil.getElementClassName(returnType), ">");
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

		if (Objects.equals(parameterType, Aggregation.class.getName()) &&
			parameterNames.contains("aggregationTerms")) {

			return "@javax.ws.rs.core.Context";
		}

		if (Objects.equals(parameterType, Filter.class.getName()) &&
			parameterNames.contains("filter")) {

			return "@javax.ws.rs.core.Context";
		}

		if (Objects.equals(parameterType, Pagination.class.getName()) &&
			parameterNames.contains("page") &&
			parameterNames.contains("pageSize")) {

			return "@javax.ws.rs.core.Context";
		}

		if (Objects.equals(parameterType, Sort[].class.getName()) &&
			parameterNames.contains("sort")) {

			return "@javax.ws.rs.core.Context";
		}

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = CamelCaseUtil.toCamelCase(
				parameter.getName());

			if (!Objects.equals(
					parameterName, javaMethodParameter.getParameterName())) {

				continue;
			}

			StringBundler sb = new StringBundler(11);

			String defaultValue = _getDefaultValue(
				openAPIYAML, parameter.getSchema());

			if (defaultValue != null) {
				sb.append("@javax.ws.rs.DefaultValue(\"");
				sb.append(defaultValue);
				sb.append("\")");
			}

			if (parameter.isDeprecated()) {
				sb.append("@Deprecated");
			}

			if (parameter.isRequired()) {
				sb.append("@javax.validation.constraints.NotNull");
			}

			sb.append("@io.swagger.v3.oas.annotations.Parameter(hidden=true)");
			sb.append("@javax.ws.rs.");
			sb.append(StringUtil.upperCaseFirstLetter(parameter.getIn()));
			sb.append("Param(\"");
			sb.append(parameter.getName());
			sb.append("\")");

			return sb.toString();
		}

		return "";
	}

	private static String _getParentSchema(
		String path, Map<String, PathItem> pathItems, String schemaName) {

		String basePath = path;

		if (basePath.endsWith(
				"/by-external-reference-code/{externalReferenceCode}")) {

			basePath = StringUtil.removeLast(
				path, "/by-external-reference-code/{externalReferenceCode}");
		}

		int lastIndexOfSlash = basePath.lastIndexOf("/");

		if (lastIndexOfSlash < 1) {
			return null;
		}

		basePath = basePath.substring(0, lastIndexOfSlash);

		if (basePath.equals("/asset-libraries/{assetLibraryId}")) {
			return "AssetLibrary";
		}
		else if (basePath.equals("/sites/{siteId}")) {
			return "Site";
		}

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			PathItem pathItem = entry.getValue();

			Get get = pathItem.getGet();

			if ((get != null) && basePath.equals(entry.getKey())) {
				List<String> tags = get.getTags();

				if (!tags.isEmpty()) {
					String tag = tags.get(0);

					if (!tag.equals(schemaName)) {
						return tag;
					}
				}
			}
		}

		return null;
	}

	private static String _getReturnType(
		Map<String, String> javaDataTypeMap, Operation operation, String path) {

		Map<ResponseCode, Response> responses = operation.getResponses();

		if ((responses == null) || responses.isEmpty()) {
			return void.class.getName();
		}

		Integer httpStatusCode = null;

		Set<Map.Entry<ResponseCode, Response>> responseEntrySet =
			responses.entrySet();

		Stream<Map.Entry<ResponseCode, Response>> responseEntryStream =
			responseEntrySet.stream();

		Response response = responseEntryStream.filter(
			responseEntry -> {
				ResponseCode responseCode = responseEntry.getKey();

				return responseCode.isDefaultResponse();
			}
		).findFirst(
		).map(
			Map.Entry::getValue
		).orElse(
			null
		);

		for (Map.Entry<ResponseCode, Response> entry : responses.entrySet()) {
			ResponseCode responseCode = entry.getKey();

			Integer curHttpStatusCode = responseCode.getHttpCode();

			if (responseCode.isDefaultResponse() ||
				(_FAMILY_SUCCESSFUL !=
					javax.ws.rs.core.Response.Status.Family.familyOf(
						curHttpStatusCode))) {

				continue;
			}

			if ((httpStatusCode == null) ||
				(httpStatusCode > curHttpStatusCode)) {

				httpStatusCode = curHttpStatusCode;
				response = entry.getValue();
			}
		}

		String returnType = String.class.getName();

		if ((response != null) && (response.getContent() != null)) {
			Map<String, Content> sortedContents =
				TreeMapBuilder.<String, Content>putAll(
					response.getContent()
				).build();

			if (sortedContents.isEmpty()) {
				return void.class.getName();
			}

			if ((operation instanceof Get || operation instanceof Put) &&
				path.endsWith("/permissions")) {

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

				returnType = OpenAPIParserUtil.getJavaDataType(
					javaDataTypeMap, schema);

				if (returnType.startsWith("[")) {
					return _getPageClassName(returnType);
				}

				String schemaReference = schema.getReference();

				if ((schemaReference == null) ||
					!schemaReference.contains("#")) {

					continue;
				}

				return returnType;
			}
		}

		if (operation instanceof Get) {
			return returnType;
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

	private static boolean _isValidParameter(String name, String schemaName) {
		String schemaVarName = StringUtil.lowerCaseFirstLetter(schemaName);

		if (!name.equals(schemaVarName + "Id") && !name.equals(schemaVarName)) {
			return true;
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