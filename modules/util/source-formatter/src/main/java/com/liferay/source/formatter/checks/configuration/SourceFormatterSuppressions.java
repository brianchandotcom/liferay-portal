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
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.filters.SuppressFilterElement;

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
		String fileNameRegex) {

		if (checkType.equals(CheckType.SOURCE_CHECK)) {
			_addSourceCheckSuppression(
				suppressionsFileLocation, checkName, fileNameRegex);
		}
		else {
			_addCheckstyleSuppression(checkName, fileNameRegex);
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

			if (!absolutePath.startsWith(suppressionsFileLocation) &&
				!absolutePath.contains(
					SourceFormatterUtil.SOURCE_FORMATTER_TEST_PATH)) {

				continue;
			}

			List<String> fileNameRegexes = entry.getValue();

			for (String fileNameRegex : fileNameRegexes) {
				if (absolutePath.matches(".*" + fileNameRegex)) {
					return true;
				}
			}
		}

		return false;
	}

	private void _addCheckstyleSuppression(
		String checkName, String fileNameRegex) {

		_checkstyleFilterSet.addFilter(
			new SuppressFilterElement(
				fileNameRegex, checkName, null, null, null, null));
	}

	private void _addSourceCheckSuppression(
		String suppressionsFileLocation, String checkName,
		String fileNameRegex) {

		Map<String, List<String>> sourceCheckSuppressionsMap =
			_sourceChecksSuppressionsMap.get(checkName);

		if (sourceCheckSuppressionsMap == null) {
			sourceCheckSuppressionsMap = new HashMap<>();
		}

		List<String> fileNameRegexes = sourceCheckSuppressionsMap.get(
			suppressionsFileLocation);

		if (fileNameRegexes == null) {
			fileNameRegexes = new ArrayList<>();
		}

		fileNameRegexes.add(fileNameRegex);

		sourceCheckSuppressionsMap.put(
			suppressionsFileLocation, fileNameRegexes);

		_sourceChecksSuppressionsMap.put(checkName, sourceCheckSuppressionsMap);
	}

	private final FilterSet _checkstyleFilterSet = new FilterSet();
	private final Map<String, Map<String, List<String>>>
		_sourceChecksSuppressionsMap = new HashMap<>();

}