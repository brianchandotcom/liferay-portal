/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	private final Set<ExcludeSyntaxPattern> _defaultExcludeSyntaxPatterns =
		new HashSet<>();
	private final Map<String, List<ExcludeSyntaxPattern>>
		_excludeSyntaxPatternsMap = new HashMap<>();

}