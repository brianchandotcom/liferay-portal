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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterExcludes {

	public SourceFormatterExcludes() {
	}

	public SourceFormatterExcludes(
		Set<ExcludeSyntaxPattern> defaultExcludeSyntaxPatterns) {

		_defaultExcludeSyntaxPatterns = defaultExcludeSyntaxPatterns;
	}

	public void addDefaultExcludeSyntaxPatterns(
		List<ExcludeSyntaxPattern> defaultExcludeSyntaxPatterns) {

		_defaultExcludeSyntaxPatterns.addAll(defaultExcludeSyntaxPatterns);
	}

	public void addExcludeSyntaxPatterns(
		String propertiesFileLocation,
		List<ExcludeSyntaxPattern> excludeSyntaxPatterns) {

		_excludeSyntaxPatternsMap.put(
			propertiesFileLocation, excludeSyntaxPatterns);
	}

	public Set<ExcludeSyntaxPattern> getDefaultExcludeSyntaxPatterns() {
		return _defaultExcludeSyntaxPatterns;
	}

	public Map<String, List<ExcludeSyntaxPattern>>
		getExcludeSyntaxPatternsMap() {

		return _excludeSyntaxPatternsMap;
	}

	private Set<ExcludeSyntaxPattern> _defaultExcludeSyntaxPatterns =
		new HashSet<>();
	private Map<String, List<ExcludeSyntaxPattern>> _excludeSyntaxPatternsMap =
		new HashMap<>();

}