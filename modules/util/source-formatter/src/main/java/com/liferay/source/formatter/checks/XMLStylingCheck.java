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

package com.liferay.source.formatter.checks;

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