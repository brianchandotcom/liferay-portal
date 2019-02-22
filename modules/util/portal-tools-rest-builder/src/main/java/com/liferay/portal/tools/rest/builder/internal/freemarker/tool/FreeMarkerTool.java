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

import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaParameter;
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
		return OpenAPIParserUtil.getArguments(javaParameters);
	}

	public List<JavaMethodSignature> getGraphQLJavaMethodSignatures(
		ConfigYAML configYAML, String graphQLType, OpenAPIYAML openAPIYAML,
		boolean fullyQualifiedNames) {

		return GraphQLOpenAPIParser.getJavaMethodSignatures(
			configYAML, graphQLType, openAPIYAML, fullyQualifiedNames);
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

		return OpenAPIParserUtil.getSchemaNames(javaMethodSignatures);
	}

	public String getHTTPMethod(Operation operation) {
		return OpenAPIParserUtil.getHTTPMethod(operation);
	}

	public String getResourceArguments(List<JavaParameter> javaParameters) {
		return OpenAPIParserUtil.getArguments(javaParameters);
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

		return OpenAPIParserUtil.hasHTTPMethod(
			javaMethodSignature, httpMethods);
	}

	public boolean isSchemaParameter(
		JavaParameter javaParameter, OpenAPIYAML openAPIYAML) {

		return OpenAPIParserUtil.isSchemaParameter(javaParameter, openAPIYAML);
	}

	private FreeMarkerTool() {
	}

	private static FreeMarkerTool _instance = new FreeMarkerTool();

}