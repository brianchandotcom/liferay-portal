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

import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 */
public class ImportPackageFactoryUtil {

	public static ImportPackage create(String line) {
		if (Validator.isNull(line)) {
			return null;
		}

		Matcher javaMatcher = _javaImportPattern.matcher(line);

		if (javaMatcher.find()) {
			return new ImportPackage(javaMatcher.group(1), line);
		}

		Matcher jspMatcher = _jspImportPattern.matcher(line);

		if (jspMatcher.find()) {
			return new ImportPackage(jspMatcher.group(1), line);
		}

		return null;
	}

	private static final Pattern _javaImportPattern = Pattern.compile(
		"import ([^\\s;]+)");
	private static final Pattern _jspImportPattern = Pattern.compile(
		"import=\"([^\\s\"]+)\"");

}