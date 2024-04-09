/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

/**
 * @author Hugo Huijser
 */
public class JSONStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws JSONException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		try {
			if (StringUtil.startsWith(
					StringUtil.trim(content), StringPool.OPEN_BRACKET)) {

				content = JSONUtil.toString(new JSONArrayImpl(content));
			}
			else if (content.endsWith("\n") &&
					 fileName.endsWith("/package.json")) {

				content = JSONUtil.toString(new JSONObjectImpl(content)) + "\n";
			}
			else {
				content = JSONUtil.toString(new JSONObjectImpl(content));
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return content;
		}

		return _formatQuotedJSON(content, "#cdata-value");
	}

	private String _fixIndentation(String content, String indent) {
		String[] lines = content.split("\n");

		if (lines.length <= 3) {
			return content;
		}

		StringBundler sb = new StringBundler(lines.length * 2);

		for (String line : lines) {
			String trimmedLine = StringUtil.trimLeading(line);

			if (!trimmedLine.equals("[") && !trimmedLine.equals("]")) {
				if (!trimmedLine.startsWith("\"")) {
					return content;
				}

				trimmedLine = StringUtil.trimLeading(trimmedLine.substring(1));

				if (trimmedLine.endsWith("\"")) {
					trimmedLine = StringUtil.replaceLast(trimmedLine, "\"", "");
				}
				else if (trimmedLine.endsWith("\",")) {
					trimmedLine = StringUtil.replaceLast(
						trimmedLine, "\",", "");
				}

				trimmedLine = trimmedLine.replaceAll("\\\\\"", "\"");

				if (Validator.isNull(trimmedLine)) {
					continue;
				}
			}

			sb.append(trimmedLine);
			sb.append("\n");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		String replacement = StringPool.BLANK;

		try {
			replacement = JSONUtil.toString(new JSONArrayImpl(sb.toString()));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return content;
		}

		lines = replacement.split("\n");

		sb = new StringBundler((lines.length * 5) + 3);

		sb.append(StringPool.OPEN_BRACKET);
		sb.append("\n");

		for (int i = 1; i < (lines.length - 1); i++) {
			String line = lines[i];

			if (line.startsWith(StringPool.TAB)) {
				line = line.substring(1);
			}

			line = StringUtil.replace(line, "\"", "\\\"");
			line = StringUtil.replace(
				line, CharPool.TAB, StringPool.FOUR_SPACES);

			sb.append(indent);
			sb.append(StringPool.TAB);
			sb.append(StringPool.QUOTE + line + StringPool.QUOTE);
			sb.append(StringPool.COMMA);
			sb.append("\n");
		}

		sb.setIndex(sb.index() - 2);

		sb.append("\n");
		sb.append(indent);
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private String _formatQuotedJSON(String content, String key) {
		key = StringUtil.quote(key, StringPool.QUOTE) + ": [";

		int x = -1;

		while (true) {
			x = content.indexOf(key, x + 1);

			if (x == -1) {
				return content;
			}

			int y = x;

			while (true) {
				y = content.indexOf("]", y + 1);

				if (y == -1) {
					continue;
				}

				String line = getLine(content, getLineNumber(content, y));

				String trimmedLine = line.trim();

				if (trimmedLine.equals("]") || trimmedLine.equals("],")) {
					String quotedJSON = content.substring(
						content.indexOf("[", x), y + 1);

					String newQuotedJSON = _fixIndentation(
						quotedJSON,
						SourceUtil.getIndent(
							getLine(content, getLineNumber(content, x))));

					if (quotedJSON.equals(newQuotedJSON)) {
						break;
					}

					return StringUtil.replaceFirst(
						content, quotedJSON, newQuotedJSON, x);
				}

				y = y + 1;
			}

			x = x + 1;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONStylingCheck.class);

}