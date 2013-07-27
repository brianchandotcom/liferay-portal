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
 *
 * @author Carlos Sierra Andrés
 *
 */
public class ImportRepr implements Comparable<ImportRepr> {

	public static ImportRepr create(String line) {
		if (Validator.isNull(line)) {
			return null;
		}

		Matcher jspMatcher = _jspImportPattern.matcher(line);

		if (jspMatcher.find()) {
			return new ImportRepr(jspMatcher.group(1), line);
		}

		Matcher javaMatcher = _javaImportPattern.matcher(line);

		if (javaMatcher.find()) {
			return new ImportRepr(javaMatcher.group(1), line);
		}

		return null;
	}

	@Override
	public int compareTo(ImportRepr o) {
		return _plainImport.compareTo(o._plainImport);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof ImportRepr) {
			ImportRepr otherRepr = (ImportRepr)o;
			return _plainImport.equals(otherRepr._plainImport);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _plainImport.hashCode();
	}

	@Override
	public String toString() {
		return _originalLine;
	}

	protected ImportRepr(String plainImport, String originalLine) {
		_originalLine = originalLine;
		_plainImport = plainImport;
	}

	private static final Pattern _javaImportPattern = Pattern.compile(
			"import ([^\\s;]+)");
	private static final Pattern _jspImportPattern = Pattern.compile(
			"import=\"([^\\s\"]+)\"");

	private String _originalLine;
	private String _plainImport;

}