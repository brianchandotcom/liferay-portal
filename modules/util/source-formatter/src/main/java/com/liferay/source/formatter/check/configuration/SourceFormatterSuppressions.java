/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.configuration;

import com.liferay.source.formatter.util.CheckType;

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

		if (fileNameRegex == null) {
			fileNameRegex = ".*";
		}

		if (!suppressionsFileLocation.endsWith(
				"/test/resources/com/liferay/source/formatter/")) {

			fileNameRegex = suppressionsFileLocation + fileNameRegex;
		}

		if (checkType.equals(CheckType.SOURCE_CHECK)) {
			_addSourceCheckSuppression(checkName, fileNameRegex);
		}
		else {
			_addCheckstyleSuppression(checkName, fileNameRegex);
		}
	}

	public FilterSet getCheckstyleFilterSet() {
		return _checkstyleFilterSet;
	}

	public boolean isSuppressed(String sourceCheckName, String absolutePath) {
		List<String> fileNameRegexes = _sourceChecksSuppressionsMap.get(
			sourceCheckName);

		if (fileNameRegexes == null) {
			return false;
		}

		for (String fileNameRegex : fileNameRegexes) {
			if (absolutePath.matches(".*" + fileNameRegex)) {
				return true;
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
		String checkName, String fileNameRegex) {

		List<String> fileNameRegexes = _sourceChecksSuppressionsMap.get(
			checkName);

		if (fileNameRegexes == null) {
			fileNameRegexes = new ArrayList<>();
		}

		fileNameRegexes.add(fileNameRegex);

		_sourceChecksSuppressionsMap.put(checkName, fileNameRegexes);
	}

	private final FilterSet _checkstyleFilterSet = new FilterSet();
	private final Map<String, List<String>> _sourceChecksSuppressionsMap =
		new HashMap<>();

}