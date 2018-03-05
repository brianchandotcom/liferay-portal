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

package com.liferay.source.formatter.checks.configuration;

import com.liferay.source.formatter.util.CheckType;

import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.filters.SuppressElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterSuppressions {

	public void addSuppression(
		CheckType checkType, String suppressionsFileLocation, String checkName,
		String fileName) {

		if (checkType.equals(CheckType.SOURCECHECK)) {
			_addSourceCheckSuppression(
				suppressionsFileLocation, checkName, fileName);
		}
		else {
			_addCheckstyleSuppression(checkName, fileName);
		}
	}

	public FilterSet getCheckstyleFilterSet() {
		return _checkstyleFilterSet;
	}

	public boolean isSuppressed(String sourceCheckName, String absolutePath) {
		Map<String, List<String>> sourceCheckSuppressionsMap =
			_sourceChecksSuppressionsMap.get(sourceCheckName);

		if (sourceCheckSuppressionsMap == null) {
			return false;
		}

		for (Map.Entry<String, List<String>> entry :
				sourceCheckSuppressionsMap.entrySet()) {

			String suppressionsFileLocation = entry.getKey();

			if (!absolutePath.startsWith(suppressionsFileLocation)) {
				continue;
			}

			List<String> fileNames = entry.getValue();

			for (String fileName : fileNames) {
				if (absolutePath.matches(".*" + fileName)) {
					return true;
				}
			}
		}

		return false;
	}

	private void _addCheckstyleSuppression(String checkName, String fileName) {
		_checkstyleFilterSet.addFilter(
			new SuppressElement(fileName, checkName, null, null, null));
	}

	private void _addSourceCheckSuppression(
		String suppressionsFileLocation, String checkName, String fileName) {

		Map<String, List<String>> sourceCheckSuppressionsMap =
			_sourceChecksSuppressionsMap.get(checkName);

		if (sourceCheckSuppressionsMap == null) {
			sourceCheckSuppressionsMap = new HashMap<>();
		}

		List<String> fileNames = sourceCheckSuppressionsMap.get(
			suppressionsFileLocation);

		if (fileNames == null) {
			fileNames = new ArrayList<>();
		}

		fileNames.add(fileName);

		sourceCheckSuppressionsMap.put(suppressionsFileLocation, fileNames);

		_sourceChecksSuppressionsMap.put(checkName, sourceCheckSuppressionsMap);
	}

	private final FilterSet _checkstyleFilterSet = new FilterSet();
	private final Map<String, Map<String, List<String>>>
		_sourceChecksSuppressionsMap = new HashMap<>();

}