/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class XMLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _xmlDeclarationPattern.matcher(content);

		if (matcher.find()) {
			String oldXmlDeclaration = matcher.group();

			String xmlDeclaration = StringUtil.replace(
				oldXmlDeclaration, " = ", "=");

			xmlDeclaration = xmlDeclaration.replaceAll(
				" encoding=\"[^\"]*\"", "");

			if (!oldXmlDeclaration.equals(xmlDeclaration)) {
				return StringUtil.replaceFirst(
					content, oldXmlDeclaration, xmlDeclaration);
			}
		}

		return content;
	}

	private static final Pattern _xmlDeclarationPattern = Pattern.compile(
		"(\\A)<\\?xml .+?(?=\\Z|\n)");

}