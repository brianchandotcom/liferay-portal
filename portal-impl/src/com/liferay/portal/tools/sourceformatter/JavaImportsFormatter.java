/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 */
public class JavaImportsFormatter extends ImportsFormatter {

	public String format(String imports) throws IOException {
		return format(imports, 7);
	}

	@Override
	protected ImportPackage createImportPackage(String line) {

		Matcher javaMatcher = _javaImportPattern.matcher(line);

		if (javaMatcher.find()) {
			return new ImportPackage(javaMatcher.group(1), line);
		}

		return null;
	}

	private static final Pattern _javaImportPattern = Pattern.compile(
		"import ([^\\s;]+)");

}