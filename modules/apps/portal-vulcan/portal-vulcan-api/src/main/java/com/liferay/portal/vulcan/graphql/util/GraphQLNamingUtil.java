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

package com.liferay.portal.vulcan.graphql.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class GraphQLNamingUtil {

	public static String getGraphQLMutationName(String methodName) {
		methodName = methodName.replaceFirst("post", "create");

		return methodName.replaceFirst("put", "update");
	}

	public static String getGraphQLPropertyName(
		String methodName, String returnType, List<String> methodNames) {

		if (!methodName.equals("getSite") &&
			!methodNames.contains(methodName.replaceFirst("Site", "")) &&
			!methodName.endsWith("SitesPage")) {

			methodName = methodName.replaceFirst("Site", "");
		}

		methodName = methodName.replaceFirst("get", "");

		if ((returnType.contains("Collection<") ||
			 (returnType.contains("Page<") &&
			  (methodName.lastIndexOf("Page") != -1))) &&
			methodName.contains("Page")) {

			methodName = methodName.substring(
				0, methodName.lastIndexOf("Page"));
		}

		return StringUtil.lowerCaseFirstLetter(methodName);
	}

}