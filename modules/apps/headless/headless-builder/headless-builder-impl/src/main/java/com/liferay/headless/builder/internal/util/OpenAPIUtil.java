/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Sergio Jiménez del Coso
 */
public class OpenAPIUtil {

	public static String getOperationId(
		Http.Method method, String path,
		APIApplication.Endpoint.RetrieveType retrieveType, String schemaName) {

		List<String> methodNameParts = new ArrayList<>();

		methodNameParts.add(StringUtil.toLowerCase(method.name()));

		String pluralSchemaName = TextFormatter.formatPlural(schemaName);

		String[] pathParts = path.split("/");

		for (int i = 0; i < pathParts.length; i++) {
			String pathPart = pathParts[i];

			String pathName = _toCamelCase(
				pathPart.replaceAll("\\{|-id|}|Id}", ""));

			if (StringUtil.equalsIgnoreCase(pathName, pluralSchemaName)) {
				pathName = pluralSchemaName;
			}
			else {
				pathName = StringUtil.upperCaseFirstLetter(pathName);
			}

			if ((i == (pathParts.length - 1)) &&
				Objects.equals(
					retrieveType,
					APIApplication.Endpoint.RetrieveType.COLLECTION)) {

				String previousMethodNamePart = methodNameParts.get(
					methodNameParts.size() - 1);

				if ((schemaName != null) &&
					!pathName.endsWith(pluralSchemaName) &&
					previousMethodNamePart.endsWith(schemaName)) {

					String methodNamePart = StringUtil.replaceLast(
						previousMethodNamePart, schemaName, pluralSchemaName);

					methodNameParts.set(
						methodNameParts.size() - 1, methodNamePart);
				}

				methodNameParts.add(pathName + "Page");
			}
			else if (pathPart.contains("{") && (schemaName != null)) {
				String previousMethodNameSegment = methodNameParts.get(
					methodNameParts.size() - 1);

				if (!previousMethodNameSegment.endsWith(pathName) &&
					!previousMethodNameSegment.endsWith(schemaName)) {

					methodNameParts.add(pathName);
				}
			}
			else {
				String methodNamePart = _formatSingular(pathName);

				String methodNamePartLowerCase = StringUtil.toLowerCase(
					methodNamePart);

				if ((schemaName != null) &&
					methodNamePartLowerCase.endsWith(
						StringUtil.toLowerCase(schemaName))) {

					char c = methodNamePart.charAt(
						methodNamePart.length() - schemaName.length());

					if (Character.isUpperCase(c)) {
						String substring = methodNamePart.substring(
							0, methodNamePart.length() - schemaName.length());

						methodNamePart = substring + schemaName;
					}
				}

				methodNameParts.add(methodNamePart);
			}
		}

		return StringUtil.merge(methodNameParts, "");
	}

	public static String getPathParameter(String path) {
		String pathParameter = path.substring(path.lastIndexOf("/") + 1);

		pathParameter = pathParameter.replaceAll("\\{", StringPool.BLANK);

		return pathParameter.replaceAll("\\}", StringPool.BLANK);
	}

	private static String _formatSingular(String s) {
		if (s.endsWith("ases")) {

			// bases to base

			s = s.substring(0, s.length() - 1);
		}
		else if (s.endsWith("auses")) {

			// clauses to clause

			s = s.substring(0, s.length() - 1);
		}
		else if (s.endsWith("ses") || s.endsWith("xes")) {
			s = s.substring(0, s.length() - 2);
		}
		else if (s.endsWith("ies")) {
			s = s.substring(0, s.length() - 3) + "y";
		}
		else if (s.endsWith("s")) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	private static String _toCamelCase(String path) {
		path = path.replaceAll("[{}]", StringPool.BLANK);

		return CamelCaseUtil.toCamelCase(
			path.replaceAll(StringPool.MINUS, StringPool.SLASH),
			CharPool.SLASH);
	}

}