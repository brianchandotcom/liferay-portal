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
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterExcludes {

	public SourceFormatterExcludes(List<String> defaultExcludes) {
		_defaultExcludes = defaultExcludes;
	}

	public void addExcludes(
		String propertiesFileLocation, List<String> exludes) {

		_excludesMap.put(propertiesFileLocation, exludes);
	}

	public List<String> getDefaultExcludes() {
		return _defaultExcludes;
	}

	public Map<String, List<String>> getExcludesMap() {
		return _excludesMap;
	}

	private final List<String> _defaultExcludes;
	private Map<String, List<String>> _excludesMap = new HashMap<>();

}