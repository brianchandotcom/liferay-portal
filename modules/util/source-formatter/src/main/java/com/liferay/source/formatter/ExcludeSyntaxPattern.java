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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class ExcludeSyntaxPattern {

	public ExcludeSyntaxPattern(
		ExcludeSyntax excludeSyntax, String excludePattern) {

		_excludeSyntax = excludeSyntax;

		if (_excludeSyntax == ExcludeSyntax.REGEX) {
			_excludePattern = StringUtil.replace(
				excludePattern, CharPool.SLASH, Pattern.quote(File.separator));
		}
		else {
			_excludePattern = excludePattern;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExcludeSyntaxPattern)) {
			return false;
		}

		ExcludeSyntaxPattern excludeSyntaxPattern = (ExcludeSyntaxPattern)obj;

		ExcludeSyntax excludeSyntax = excludeSyntaxPattern.getExcludeSyntax();

		if (!excludeSyntax.equals(_excludeSyntax)) {
			return false;
		}

		String excludePattern = excludeSyntaxPattern.getExcludePattern();

		if (!excludePattern.equals(_excludePattern)) {
			return false;
		}

		return true;
	}

	public String getExcludePattern() {
		return _excludePattern;
	}

	public ExcludeSyntax getExcludeSyntax() {
		return _excludeSyntax;
	}

	@Override
	public int hashCode() {
		String s = _excludeSyntax.getValue();

		s = s.concat(_excludePattern);

		return s.hashCode();
	}

	private final String _excludePattern;
	private final ExcludeSyntax _excludeSyntax;

}