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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaSignature;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Application;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Content;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Delete;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Get;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Head;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Items;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Options;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Post;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Properties;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Put;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.RequestBody;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Response;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class JavaTool {

	public static JavaTool getInstance() {
		return _instance;
	}

	public Object getComponent(OpenAPIYAML openAPIYAML, String reference) {
		if (reference == null) {
			return null;
		}

		Components components = openAPIYAML.getComponents();
		String referenceType = getComponentType(reference);

		if (reference.startsWith("#/components/parameters/")) {
			Map<String, Parameter> parameters = components.getParameters();

			for (Map.Entry<String, Parameter> entry : parameters.entrySet()) {
				if (referenceType.equals(entry.getKey())) {
					return entry.getValue();
				}
			}
		}
		else if (reference.startsWith("#/components/schemas/")) {
			Map<String, Schema> schemas = components.getSchemas();

			for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
				if (referenceType.equals(entry.getKey())) {
					return entry.getValue();
				}
			}
		}

		return null;
	}

	public String getComponentType(String reference) {
		int index = reference.lastIndexOf('/');

		if (index == -1) {
			return reference;
		}

		return reference.substring(index + 1);
	}

	public String getHTTPMethod(Operation operation) {
		if (Delete.class.isInstance(operation)) {
			return "delete";
		}
		else if (Get.class.isInstance(operation)) {
			return "get";
		}
		else if (Head.class.isInstance(operation)) {
			return "head";
		}
		else if (Options.class.isInstance(operation)) {
			return "options";
		}
		else if (Post.class.isInstance(operation)) {
			return "post";
		}
		else if (Put.class.isInstance(operation)) {
			return "put";
		}

		return null;
	}

	public JavaParameter getJavaParameter(Parameter parameter) {
		List<String> parameterAnnotations = new ArrayList<>();

		Schema schema = parameter.getSchema();

		if (schema.getType() != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("@");
			sb.append(StringUtil.upperCaseFirstLetter(parameter.getIn()));
			sb.append("Param(\"");
			sb.append(parameter.getName());
			sb.append("\")");

			parameterAnnotations.add(sb.toString());
		}

		String parameterName = CamelCaseUtil.toCamelCase(
			parameter.getName(), false);
		String parameterType = getJavaType(
			schema.getFormat(), schema.getItems(), schema.getReference(),
			schema.getType());

		return new JavaParameter(
			parameterAnnotations, parameterName, parameterType);
	}

	public JavaParameter getJavaParameter(
		Properties properties, String propertyName) {

		String parameterName = CamelCaseUtil.toCamelCase(propertyName, false);
		String parameterType = getJavaType(
			properties.getFormat(), properties.getItems(),
			properties.getReference(), properties.getType());

		return new JavaParameter(null, parameterName, parameterType);
	}

	public JavaSignature getJavaSignature(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Operation operation,
		String path, PathItem pathItem, String schemaName) {

		String returnType = _getReturnType(openAPIYAML, operation);

		return new JavaSignature(
			_getJavaParameters(operation),
			_getMethodAnnotations(configYAML, operation, pathItem, path),
			_getMethodName(operation, path, returnType, schemaName),
			returnType);
	}

	public String getJavaType(
		String format, Items items, String reference, String type) {

		if (StringUtil.equals(type, "array") && (items != null)) {
			if (items.getType() != null) {
				return StringUtil.upperCaseFirstLetter(items.getType()) + "[]";
			}

			if (items.getReference() != null) {
				return getComponentType(items.getReference()) + "[]";
			}
		}

		if (type != null) {
			if (StringUtil.equals(format, "date-time") &&
				StringUtil.equals(type, "string")) {

				return "Date";
			}
			else if (StringUtil.equals(format, "int64") &&
					 StringUtil.equals(type, "integer")) {

				return "Long";
			}

			return StringUtil.upperCaseFirstLetter(type);
		}

		return getComponentType(reference);
	}

	public List<String> getMediaTypes(Map<String, Content> contents) {
		if (contents.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> mediaTypes = new ArrayList<>(contents.keySet());

		Collections.sort(mediaTypes);

		return mediaTypes;
	}

	public List<Operation> getOperations(PathItem pathItem) {
		List<Operation> operations = new ArrayList<>();

		if (pathItem.getDelete() != null) {
			operations.add(pathItem.getDelete());
		}

		if (pathItem.getGet() != null) {
			operations.add(pathItem.getGet());
		}

		if (pathItem.getHead() != null) {
			operations.add(pathItem.getHead());
		}

		if (pathItem.getOptions() != null) {
			operations.add(pathItem.getOptions());
		}

		if (pathItem.getPost() != null) {
			operations.add(pathItem.getPost());
		}

		if (pathItem.getPut() != null) {
			operations.add(pathItem.getPut());
		}

		return operations;
	}

	public boolean hasJavaParameterAcceptLanguage(OpenAPIYAML openAPIYAML) {
		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return false;
		}

		for (PathItem pathItem : pathItems.values()) {
			for (Operation operation : getOperations(pathItem)) {
				if (_hasJavaParameterAcceptLanguage(operation)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasJavaParameterPagination(OpenAPIYAML openAPIYAML) {
		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return false;
		}

		for (PathItem pathItem : pathItems.values()) {
			for (Operation operation : getOperations(pathItem)) {
				if (_hasJavaParameterPagination(operation)) {
					return true;
				}
			}
		}

		return false;
	}

	private JavaTool() {
	}

	private List<JavaParameter> _getJavaParameters(Operation operation) {
		List<JavaParameter> javaParameters = new ArrayList<>();

		boolean pagination = _hasJavaParameterPagination(operation);

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = parameter.getName();

			if (StringUtil.equals(parameterName, "Accept-Language")) {
				continue;
			}

			if (pagination) {
				if (StringUtil.equals(parameterName, "page") ||
					StringUtil.equals(parameterName, "per_page")) {

					continue;
				}
			}

			javaParameters.add(getJavaParameter(parameter));
		}

		if (_hasJavaParameterAcceptLanguage(operation)) {
			JavaParameter javaParameter = new JavaParameter(
				Collections.singletonList("@Context"), "acceptLanguage",
				"AcceptLanguage");

			javaParameters.add(javaParameter);
		}

		if (pagination) {
			JavaParameter javaParameter = new JavaParameter(
				Collections.singletonList("@Context"), "pagination",
				"Pagination");

			javaParameters.add(javaParameter);
		}

		return javaParameters;
	}

	private String _getMethodAnnotationConsumes(Operation operation) {
		RequestBody requestBody = operation.getRequestBody();

		if (requestBody == null) {
			return null;
		}

		Map<String, Content> contents = requestBody.getContent();

		List<String> mediaTypes = getMediaTypes(contents);

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
			mediaTypes.addAll(getMediaTypes(response.getContent()));
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

	private List<String> _getMethodAnnotations(
		ConfigYAML configYAML, Operation operation, PathItem pathItem,
		String path) {

		List<String> methodAnnotations = new ArrayList<>();

		methodAnnotations.add("@Path(\"" + path + "\")");

		String httpMethod = getHTTPMethod(operation);

		methodAnnotations.add("@" + StringUtil.toUpperCase(httpMethod));

		Application application = configYAML.getApplication();

		String name = application.getName();

		if (pathItem.getGet() != null) {
			methodAnnotations.add("@RequiresScope(\"" + name + ".read\")");
		}
		else {
			methodAnnotations.add("@RequiresScope(\"" + name + ".write\")");
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

	private String _getMethodName(
		Operation operation, String path, String returnType,
		String schemaName) {

		String httpMethod = getHTTPMethod(operation);

		String s = path.replaceAll("(?s)\\{.*?\\}", "");

		String name = httpMethod + CamelCaseUtil.toCamelCase(s, true);

		if (StringUtil.startsWith(returnType, "Page<") &&
			!StringUtil.endsWith(name, "Page")) {

			return name + "Page";
		}

		if (StringUtil.equals(returnType, schemaName) &&
			StringUtil.endsWith(name, schemaName + "s")) {

			return name.substring(0, name.length() - 1);
		}

		return name;
	}

	private String _getReturnType(
		OpenAPIYAML openAPIYAML, Operation operation) {

		Map<String, Response> responses = operation.getResponses();

		if (responses.isEmpty()) {
			return "Response";
		}

		for (Response response : responses.values()) {
			Map<String, Content> contents = response.getContent();

			for (Content content : contents.values()) {
				Schema schema = content.getSchema();

				if (schema == null) {
					continue;
				}

				String javaType = getJavaType(
					schema.getFormat(), schema.getItems(),
					schema.getReference(), schema.getType());

				if (javaType.endsWith("[]")) {
					String s = javaType.substring(0, javaType.length() - 2);

					return "Page<" + s + ">";
				}

				Object component = getComponent(
					openAPIYAML, schema.getReference());

				if (component instanceof Schema) {
					return getComponentType(schema.getReference());
				}
			}
		}

		return "Response";
	}

	private boolean _hasJavaParameterAcceptLanguage(Operation operation) {
		for (Parameter parameter : operation.getParameters()) {
			String parameterName = parameter.getName();

			if (StringUtil.equals(parameterName, "Accept-Language")) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasJavaParameterPagination(Operation operation) {
		List<String> parameterNames = new ArrayList<>();

		for (Parameter parameter : operation.getParameters()) {
			parameterNames.add(parameter.getName());
		}

		if (parameterNames.contains("page") &&
			parameterNames.contains("per_page")) {

			return true;
		}

		return false;
	}

	private static JavaTool _instance = new JavaTool();

}