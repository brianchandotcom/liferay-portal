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
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.DTOOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.GraphQLOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

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

	public List<JavaParameter> getDTOJavaParameters(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Schema schema,
		boolean fullyQualifiedNames) {

		return DTOOpenAPIParser.getJavaParameters(
			configYAML, openAPIYAML, schema, fullyQualifiedNames);
	}

	public List<JavaParameter> getDTOJavaParameters(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName,
		boolean fullyQualifiedNames) {

		return DTOOpenAPIParser.getJavaParameters(
			configYAML, openAPIYAML, schemaName, fullyQualifiedNames);
	}

	public String getGraphQLArguments(List<JavaParameter> javaParameters) {
		return GraphQLOpenAPIParser.getArguments(javaParameters);
	}

	public List<JavaMethodSignature> getGraphQLJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String type,
		boolean fullyQualifiedNames) {

		return GraphQLOpenAPIParser.getJavaMethodSignatures(
			configYAML, openAPIYAML, type, fullyQualifiedNames);
	}

	public String getGraphQLMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		return GraphQLOpenAPIParser.getMethodAnnotations(javaMethodSignature);
	}

	public String getGraphQLParameters(
		List<JavaParameter> javaParameters, boolean annotation) {

		return GraphQLOpenAPIParser.getParameters(javaParameters, annotation);
	}

	public Set<String> getGraphQLSchemaNames(
		List<JavaMethodSignature> javaMethodSignatures) {

		return GraphQLOpenAPIParser.getSchemaNames(javaMethodSignatures);
	}

	public String getHTTPMethod(Operation operation) {
		return _getHTTPMethod(operation);
	}

	public String getResourceArguments(List<JavaParameter> javaParameters) {
		return ResourceOpenAPIParser.getArguments(javaParameters);
	}

	public List<JavaMethodSignature> getResourceJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName,
		boolean fullyQualifiedNames) {

		return ResourceOpenAPIParser.getJavaMethodSignatures(
			configYAML, openAPIYAML, schemaName, fullyQualifiedNames);
	}

	public String getResourceMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		return ResourceOpenAPIParser.getMethodAnnotations(javaMethodSignature);
	}

	public String getResourceParameters(
		List<JavaParameter> javaParameters, boolean annotation) {

		return ResourceOpenAPIParser.getParameters(javaParameters, annotation);
	}

	public boolean hasHTTPMethod(
		JavaMethodSignature javaMethodSignature, String... httpMethods) {

		return _hasHTTPMethod(javaMethodSignature, httpMethods);
	}

	public boolean isSchemaParameter(
		JavaParameter javaParameter, OpenAPIYAML openAPIYAML) {

		return _isSchemaParameter(javaParameter, openAPIYAML);
	}

	private FreeMarkerTool() {
	}

	private String _getHTTPMethod(Operation operation) {
		Class<? extends Operation> clazz = operation.getClass();

		return StringUtil.lowerCase(clazz.getSimpleName());
	}

	private String _getSimpleClassName(String type) {
		if (type.endsWith("[]")) {
			return type.substring(0, type.length() - 2);
		}

		if (type.endsWith(">")) {
			return type.substring(0, type.indexOf("<"));
		}

		return type;
	}

	private boolean _hasHTTPMethod(
		JavaMethodSignature javaMethodSignature, String... httpMethods) {

		Operation operation = javaMethodSignature.getOperation();

		for (String httpMethod : httpMethods) {
			if (Objects.equals(httpMethod, _getHTTPMethod(operation))) {
				return true;
			}
		}

		return false;
	}

	private boolean _isSchemaParameter(
		JavaParameter javaParameter, OpenAPIYAML openAPIYAML) {

		String simpleClassName = _getSimpleClassName(
			javaParameter.getParameterType());

		Map<String, Schema> schemas = OpenAPIUtil.getAllSchemas(openAPIYAML);

		if (schemas.containsKey(simpleClassName)) {
			return true;
		}

		return false;
	}

	private static FreeMarkerTool _instance = new FreeMarkerTool();

}