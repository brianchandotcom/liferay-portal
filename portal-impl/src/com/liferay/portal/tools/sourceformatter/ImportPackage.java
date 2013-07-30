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
public class ImportPackage implements Comparable<ImportPackage> {

	public ImportPackage(String line) {
		if (Validator.isNull(line)) {
			return;
		}

		_line = line;

		Matcher jspMatcher = _jspImportPattern.matcher(line);

		if (jspMatcher.find()) {
			_import = jspMatcher.group(1);
		}

		Matcher javaMatcher = _javaImportPattern.matcher(line);

		if (javaMatcher.find()) {
			_import = javaMatcher.group(1);
		}
	}

	@Override
	public int compareTo(ImportPackage importPackage) {
		return _import.compareTo(importPackage._import);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof ImportPackage) {
			ImportPackage importPackage = (ImportPackage)obj;

			return _import.equals(importPackage._import);
		}

		return false;
	}

	public String getImport() {
		return _import;
	}

	public String getLine() {
		return _line;
	}

	@Override
	public int hashCode() {
		return _import.hashCode();
	}

	private static final Pattern _javaImportPattern = Pattern.compile(
		"import ([^\\s;]+)");
	private static final Pattern _jspImportPattern = Pattern.compile(
		"import=\"([^\\s\"]+)\"");

	private String _import;
	private String _line;

}