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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Peter Shin
 */
public class GradleIndentationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		return _checkIndentation(content);
	}

	private String _checkIndentation(String content) throws Exception {
		boolean insideQuotes = false;
		int tabCount = 0;

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (!insideQuotes &&
					!line.matches("^\\s*['\"].*['\"].*[\\)\\+]$")) {

					line = _checkIndentation(line, tabCount);
				}

				sb.append(line);

				sb.append("\n");

				tabCount = _getTabCount(line, insideQuotes, tabCount);

				if (line.indexOf(_getQuoteString(line)) != -1) {
					insideQuotes = !insideQuotes;
				}
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _checkIndentation(String line, int expectedTabCount) {
		if (Validator.isNull(line)) {
			return line;
		}

		int leadingTabCount = getLeadingTabCount(line);

		if (line.matches("\t*[\\}\\]\\)].*")) {
			expectedTabCount = expectedTabCount - 1;
		}

		if (leadingTabCount == expectedTabCount) {
			return line;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < expectedTabCount; i++) {
			sb.append(StringPool.TAB);
		}

		sb.append(StringUtil.trimLeading(line));

		return sb.toString();
	}

	private String _getQuoteString(String line) {
		if (line.indexOf("'''") != -1) {
			return "'''";
		}

		return "\"\"\"";
	}

	private int _getTabCount(String line, boolean insideQuotes, int tabCount) {
		String quoteString = _getQuoteString(line);
		String text = line;

		if (line.indexOf(quoteString) != -1) {
			if (insideQuotes) {
				int x = quoteString.length();

				text = text.substring(line.indexOf(quoteString) + x);
			}
			else {
				text = text.substring(0, line.indexOf(quoteString));
			}
		}

		if (insideQuotes && line.equals(text)) {
			return tabCount;
		}

		if (text.matches("^\\s*Matcher\\s+.*") ||
			text.matches("^\\s*Pattern\\s+.*")) {

			return tabCount;
		}

		tabCount += getLevel(text, "([{", "}])");

		text = StringUtil.removeSubstrings(text, "([{", "}])");

		tabCount += getLevel(text, "[{", "}]");

		text = StringUtil.removeSubstrings(text, "[{", "}]");

		return getLevel(
			text, new String[] {"{", "[", "("}, new String[] {"}", "]", ")"},
			tabCount);
	}

}