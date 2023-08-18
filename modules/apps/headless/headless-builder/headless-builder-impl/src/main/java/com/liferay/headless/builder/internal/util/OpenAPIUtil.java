/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio Jiménez del Coso
 */
public class OpenAPIUtil {

	public static String getOperationId(
		Http.Method method, String path, String schemaName) {

		String operationId =
			StringUtil.toLowerCase(method.name()) + _toCamelCase(path) + "Page";

		if (schemaName == null) {
			return operationId;
		}

		List<String> methodNameSegments = new ArrayList<>();

		methodNameSegments.add(StringUtil.toLowerCase(method.name()));

		String[] pathSegments = path.split("/");
		String pluralSchemaName = TextFormatter.formatPlural(schemaName);

		for (int i = 0; i < pathSegments.length; i++) {
			String pathSegment = pathSegments[i];

			String pathName = CamelCaseUtil.toCamelCase(pathSegment);

			if (StringUtil.equalsIgnoreCase(pathName, pluralSchemaName)) {
				pathName = pluralSchemaName;
			}
			else {
				pathName = StringUtil.upperCaseFirstLetter(pathName);
			}

			if (i == (pathSegments.length - 1)) {
				String previousMethodNameSegment = methodNameSegments.get(
					methodNameSegments.size() - 1);

				if (!pathName.endsWith(pluralSchemaName) &&
					previousMethodNameSegment.endsWith(schemaName)) {

					String string = StringUtil.replaceLast(
						previousMethodNameSegment, schemaName,
						pluralSchemaName);

					methodNameSegments.set(
						methodNameSegments.size() - 1, string);
				}

				methodNameSegments.add(pathName + "Page");
			}
			else {
				String segment = _formatSingular(pathName);

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
		}

		return StringUtil.merge(methodNameSegments, "");
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
		path = path.replaceAll("/\\{.*\\}", StringPool.BLANK);

		return CamelCaseUtil.toCamelCase(
			path.replaceAll(StringPool.MINUS, StringPool.SLASH),
			CharPool.SLASH);
	}

}