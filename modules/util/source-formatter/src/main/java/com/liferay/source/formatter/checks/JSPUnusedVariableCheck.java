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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JSPUnusedVariableCheck extends JSPUnusedTermCheck {

	@Override
	public void init() throws Exception {
		_contentsMap = getContentsMap();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (_contentsMap.isEmpty()) {
			return content;
		}

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			boolean javaSource = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
				}

				if (!javaSource ||
					isExcludedPath(
						_UNUSED_VARIABLES_EXCLUDES, absolutePath, lineCount) ||
					!_hasUnusedVariable(
						fileName, trimmedLine, checkedFileNames,
						includeFileNames)) {

					sb.append(line);
					sb.append("\n");
				}
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private String _getVariableName(String line) {
		if (!line.endsWith(";") || line.startsWith("//")) {
			return null;
		}

		String variableName = null;

		int x = line.indexOf(" = ");

		if (x == -1) {
			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1, line.length() - 1);
			}
		}
		else {
			line = line.substring(0, x);

			int y = line.lastIndexOf(CharPool.SPACE);

			if (y != -1) {
				variableName = line.substring(y + 1);
			}
		}

		if (Validator.isVariableName(variableName)) {
			return variableName;
		}

		return null;
	}

	private boolean _hasUnusedVariable(
		String fileName, String line, Set<String> checkedFileNames,
		Set<String> includeFileNames) {

		if (line.contains(": ")) {
			return false;
		}

		String variableName = _getVariableName(line);

		if (Validator.isNull(variableName) || variableName.equals("false") ||
			variableName.equals("true")) {

			return false;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("((/)|(\\*)|(\\+(\\+)?)|(-(-)?)|\\(|=)?( )?");
		sb.append(variableName);
		sb.append("( )?(\\.");
		sb.append("|(((\\+)|(-)|(\\*)|(/)|(%)|(\\|)|(&)|(\\^))?(=))");
		sb.append("|(\\+(\\+)?)|(-(-)?)");
		sb.append("|(\\)))?");

		return hasUnusedJSPTerm(
			fileName, sb.toString(), "variable", checkedFileNames,
			includeFileNames, _contentsMap);
	}

	private static final String _UNUSED_VARIABLES_EXCLUDES =
		"jsp.unused.variables.excludes";

	private Map<String, String> _contentsMap;

}