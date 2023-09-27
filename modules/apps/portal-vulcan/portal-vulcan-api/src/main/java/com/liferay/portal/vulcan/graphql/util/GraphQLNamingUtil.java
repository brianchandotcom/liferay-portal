/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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