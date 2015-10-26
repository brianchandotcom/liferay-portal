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

package com.liferay.source.formatter;

import com.liferay.portal.tools.ImportPackage;
import com.liferay.portal.tools.ImportsFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 */
public class JSPImportsFormatter extends ImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _jspImportPattern.matcher(line);

		if (matcher.find()) {
			return new ImportPackage(matcher.group(1), false, line);
		}

		matcher = _jspTaglibPattern.matcher(line);

		if (matcher.find()) {
			return new ImportPackage(matcher.group(1), false, line);
		}

		return null;
	}

	private static final Pattern _jspImportPattern = Pattern.compile(
		"import=\"([^\\s\"]+)\"");
	private static final Pattern _jspTaglibPattern = Pattern.compile(
		"uri=\"http://([^\\s\"]+)\"");

}