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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodParameter;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Content;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Info;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Peter Shin
 */
public class ResourceTestCaseOpenAPIParser {

	public static List<JavaMethodSignature> getJavaMethodSignatures(
		ConfigYAML configYAML, OpenAPIYAML openAPIYAML, String schemaName) {

		List<JavaMethodSignature> javaMethodSignatures = new ArrayList<>();

		List<JavaMethodSignature> resourceJavaMethodSignatures =
			ResourceOpenAPIParser.getJavaMethodSignatures(
				configYAML, openAPIYAML, schemaName);

		String version = _getVersion(openAPIYAML);

		for (JavaMethodSignature resourceJavaMethodSignature :
				resourceJavaMethodSignatures) {

			javaMethodSignatures.add(
				new JavaMethodSignature(
					resourceJavaMethodSignature.getPath(),
					resourceJavaMethodSignature.getPathItem(),
					resourceJavaMethodSignature.getOperation(),
					resourceJavaMethodSignature.getRequestBodyMediaTypes(),
					resourceJavaMethodSignature.getSchemaName(),
					resourceJavaMethodSignature.getJavaMethodParameters(),
					_getMethodName(resourceJavaMethodSignature),
					_getReturnType(
						configYAML.getApiPackagePath(),
						resourceJavaMethodSignature.getReturnType(), version)));
		}

		return javaMethodSignatures;
	}

	public static String getParameters(
		List<JavaMethodParameter> javaMethodParameters, OpenAPIYAML openAPIYAML,
		Operation operation, boolean annotation) {

		return ResourceOpenAPIParser.getParameters(
			javaMethodParameters, openAPIYAML, operation, annotation);
	}

	private static String _getMethodName(
		JavaMethodSignature javaMethodSignature) {

		Operation operation = javaMethodSignature.getOperation();
		Set<String> requestBodyMediaTypes =
			javaMethodSignature.getRequestBodyMediaTypes();

		List<String> mediaTypes = new ArrayList<>();

		if (operation.getRequestBody() != null) {
			RequestBody requestBody = operation.getRequestBody();

			if (requestBody.getContent() != null) {
				Map<String, Content> contents = requestBody.getContent();

				mediaTypes.addAll(contents.keySet());
			}
		}

		if (operation.getOperationId() != null) {
			String operationId = operation.getOperationId();

			if (!requestBodyMediaTypes.contains("multipart/form-data") ||
				(mediaTypes.size() < 2)) {

				return operationId;
			}

			int index = 0;

			for (int i = 0; i < operationId.length(); i++) {
				if (Character.isUpperCase(operationId.charAt(i))) {
					index = i;

					break;
				}
			}

			StringBuilder sb = new StringBuilder();

			sb.append(operationId.substring(0, index));
			sb.append("FormData");
			sb.append(operationId.substring(index));

			return sb.toString();
		}

		String methodName = javaMethodSignature.getMethodName();

		if (requestBodyMediaTypes.contains("multipart/form-data") &&
			(mediaTypes.size() > 1)) {

			String httpMethod = OpenAPIParserUtil.getHTTPMethod(operation);

			return StringUtil.replaceFirst(
				methodName, httpMethod, httpMethod + "FormData");
		}

		return methodName;
	}

	private static String _getReturnType(
		String apiPackage, String returnType, String version) {

		String versionPackage = StringUtil.replace(version, '.', '_');

		if (returnType.startsWith(
				"com.liferay.portal.vulcan.pagination.Page<")) {

			String itemType = returnType.substring(
				returnType.indexOf("<") + 1, returnType.indexOf(">"));

			if (itemType.contains(".") && !itemType.startsWith("java.lang") &&
				!itemType.startsWith("com.liferay.portal.vulcan") &&
				!itemType.startsWith(apiPackage)) {

				return StringBundler.concat(
					"com.liferay.portal.vulcan.pagination.Page<", apiPackage,
					".dto.", versionPackage, ".",
					itemType.substring(itemType.lastIndexOf(".") + 1), ">");
			}
		}
		else if (returnType.contains(".") &&
				 !returnType.startsWith("java.lang") &&
				 !returnType.equals("javax.ws.rs.core.Response") &&
				 !returnType.equals("com.liferay.portal.vulcan") &&
				 !returnType.startsWith(apiPackage)) {

			return StringBundler.concat(
				apiPackage, ".dto.", versionPackage, ".",
				returnType.substring(returnType.lastIndexOf(".") + 1));
		}

		return returnType;
	}

	private static String _getVersion(OpenAPIYAML openAPIYAML) {
		Info info = openAPIYAML.getInfo();

		return info.getVersion();
	}

}