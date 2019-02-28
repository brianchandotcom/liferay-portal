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

import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.DTOOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.GraphQLOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.ResourceOpenAPIParser;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.List;
import java.util.Set;

/**
 * @author Peter Shin
 */
public class FreeMarkerTool {

	public static FreeMarkerTool getInstance() {
		return _instance;
	}

	public List<JavaMethodParameter> getDTOJavaMethodParameters(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, Schema schema) {

		return DTOOpenAPIParser.getJavaMethodParameters(
			configYAML, openAPIYAML, schema);
	}

	public List<JavaMethodParameter> getDTOJavaMethodParameters(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		return DTOOpenAPIParser.getJavaMethodParameters(
			configYAML, openAPIYAML, schemaName);
	}

	public String getGraphQLArguments(
		List<JavaMethodParameter> javaMethodParameters) {

		return OpenAPIParserUtil.getArguments(javaMethodParameters);
	}

	public List<JavaMethodSignature> getGraphQLJavaMethodSignatures(
		ConfigYAML configYAML, String graphQLType, OpenAPIYAML openAPIYAML) {

		return GraphQLOpenAPIParser.getJavaMethodSignatures(
			configYAML, graphQLType, openAPIYAML);
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

	public boolean hasHTTPMethod(
		JavaMethodSignature javaMethodSignature, String... httpMethods) {

		return OpenAPIParserUtil.hasHTTPMethod(
			javaMethodSignature, httpMethods);
	}

	public boolean isSchemaParameter(
		JavaMethodParameter javaMethodParameter, OpenAPIYAML openAPIYAML) {

		return OpenAPIParserUtil.isSchemaParameter(
			javaMethodParameter, openAPIYAML);
	}

	private FreeMarkerTool() {
	}

	private static FreeMarkerTool _instance = new FreeMarkerTool();

}