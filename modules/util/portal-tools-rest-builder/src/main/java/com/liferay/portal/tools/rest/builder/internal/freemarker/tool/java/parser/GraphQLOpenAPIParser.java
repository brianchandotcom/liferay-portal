/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * @author Peter Shin
 */
public class GraphQLOpenAPIParser {

	public static List<JavaMethodSignature> getJavaMethodSignatures(
			ConfigYAML configYAML, OpenAPIYAML openAPIYAML,
			Predicate<Operation> predicate)
		throws Exception {

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		Map<String, Schema> schemas = new TreeMap<>();

		Components components = openAPIYAML.getComponents();

		if (components != null) {
			schemas.putAll(components.getSchemas());
		}

		schemas.putAll(OpenAPIUtil.getAllExternalSchemas(openAPIYAML));

		for (String schemaName : schemas.keySet()) {
			javaMethodSignatures.addAll(
				_getJavaMethodSignatures(
					configYAML, openAPIYAML, predicate, schemaName));
		}

		return javaMethodSignatures;
	}

	public static String getMethodAnnotations(
		JavaMethodSignature javaMethodSignature) {

		Set<String> methodAnnotations = new TreeSet<>();

		Operation operation = javaMethodSignature.getOperation();

		String httpMethod = OpenAPIParserUtil.getHTTPMethod(operation);

		if (httpMethod != null) {
			StringBundler sb = new StringBundler("@GraphQLField(");

			if (operation.getDescription() != null) {
				sb.append("description=\"");
				sb.append(operation.getDescription());
				sb.append("\"");
			}

			sb.append(")");

			methodAnnotations.add(sb.toString());
		}

		String methodAnnotation = _getMethodAnnotationGraphQLName(
			javaMethodSignature);

		if (methodAnnotation != null) {
			methodAnnotations.add(methodAnnotation);
		}

		return StringUtil.merge(methodAnnotations, "\n");
	}

	public static String getParameters(
		List<JavaMethodParameter> javaMethodParameters, Operation operation,
		boolean annotation) {

		StringBuilder sb = new StringBuilder();

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			String parameterAnnotation = null;

			if (annotation) {
				parameterAnnotation = _getParameterAnnotation(
					javaMethodParameter, operation);
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

	private static List<JavaMethodParameter> _getJavaMethodParameters(
		JavaMethodSignature resourceJavaMethodSignature) {

		List<JavaMethodParameter> javaMethodParameters = new ArrayList<>();

		for (JavaMethodParameter javaMethodParameter :
				resourceJavaMethodSignature.getJavaMethodParameters()) {

			if (Objects.equals(
					javaMethodParameter.getParameterType(),
					Pagination.class.getName())) {

				javaMethodParameters.add(
					new JavaMethodParameter("pageSize", int.class.getName()));

				javaMethodParameters.add(
					new JavaMethodParameter("page", int.class.getName()));
			}
			else {
				javaMethodParameters.add(javaMethodParameter);
			}
		}

		return javaMethodParameters;
	}

	private static List<JavaMethodSignature> _getJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML,
		Predicate<Operation> predicate, String schemaName) {

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		List<JavaMethodSignature> resourceJavaMethodSignatures =
			ResourceOpenAPIParser.getJavaMethodSignatures(
				configYAML, openAPIYAML, schemaName);

		for (JavaMethodSignature resourceJavaMethodSignature :
				resourceJavaMethodSignatures) {

			Operation operation = resourceJavaMethodSignature.getOperation();

			if (!predicate.test(operation)) {
				continue;
			}

			String returnType = resourceJavaMethodSignature.getReturnType();

			if (returnType.startsWith(Page.class.getName() + "<")) {
				String pageClassName = Page.class.getName();

				String className = returnType.substring(
					pageClassName.length() + 1, returnType.length() - 1);

				returnType = StringBundler.concat(
					Collection.class.getName(), "<", className, ">");
			}

			List<JavaMethodParameter> javaMethodParameters =
				_getJavaMethodParameters(resourceJavaMethodSignature);

			javaMethodSignatures.add(
				new JavaMethodSignature(
					resourceJavaMethodSignature.getPath(),
					resourceJavaMethodSignature.getPathItem(), operation,
					resourceJavaMethodSignature.getRequestBodyMediaTypes(),
					resourceJavaMethodSignature.getSchemaName(),
					javaMethodParameters,
					resourceJavaMethodSignature.getMethodName(), returnType));
		}

		return javaMethodSignatures;
	}

	private static String _getMethodAnnotationGraphQLName(
		JavaMethodSignature javaMethodSignature) {

		Set<String> requestBodyMediaTypes =
			javaMethodSignature.getRequestBodyMediaTypes();

		if (requestBodyMediaTypes.isEmpty() ||
			requestBodyMediaTypes.contains("application/json")) {

			return null;
		}

		List<JavaMethodParameter> javaMethodParameters =
			javaMethodSignature.getJavaMethodParameters();

		StringBundler sb = new StringBundler("@GraphQLName(value=\"");

		sb.append(javaMethodSignature.getMethodName());

		for (JavaMethodParameter javaMethodParameter : javaMethodParameters) {
			sb.append(
				StringUtil.upperCaseFirstLetter(
					javaMethodParameter.getParameterName()));
		}

		Operation operation = javaMethodSignature.getOperation();

		sb.append("\", description=\"");
		sb.append(operation.getDescription());

		sb.append("\")");

		return sb.toString();
	}

	private static String _getParameterAnnotation(
		JavaMethodParameter javaMethodParameter, Operation operation) {

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = CamelCaseUtil.toCamelCase(
				parameter.getName());

			if (!Objects.equals(
					parameterName, javaMethodParameter.getParameterName())) {

				continue;
			}

			Schema schema = parameter.getSchema();

			if (schema.getType() != null) {
				return StringBundler.concat(
					"@GraphQLName(\"", parameter.getName(), "\")");
			}
		}

		StringBundler sb = new StringBundler(3);

		sb.append("@GraphQLName(\"");

		String parameterName = javaMethodParameter.getParameterName();

		if (parameterName.equals("sorts")) {
			sb.append("sort");
		}
		else {
			sb.append(javaMethodParameter.getParameterName());
		}

		sb.append("\")");

		return sb.toString();
	}

}