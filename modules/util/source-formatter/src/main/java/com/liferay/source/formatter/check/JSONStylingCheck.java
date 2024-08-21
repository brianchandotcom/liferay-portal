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
			if (content.endsWith("\n") && fileName.endsWith("/package.json")) {
				return JSONUtil.toString(new JSONObjectImpl(content)) + "\n";
			}

			if (StringUtil.startsWith(
					StringUtil.trim(content), StringPool.OPEN_BRACKET)) {

				content = JSONUtil.toString(new JSONArrayImpl(content));
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

		if (!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/")) {

			content = _formatEscapedJSON(content);
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

	private String _formatEscapedJSON(String content) {
		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler(lines.length * 2);

		for (String line : lines) {
			if (Validator.isNull(line)) {
				continue;
			}

			int x = line.indexOf("\": \"{\\\"");

			if (x == -1) {
				x = line.indexOf("\": \"[{\\\"");
			}

			if (x == -1) {
				sb.append(line);
				sb.append("\n");

				continue;
			}

			String s = null;

			if (line.endsWith("}\"") || line.endsWith("}]\"")) {
				s = line.substring(x + 4, line.length() - 1);
			}
			else if (line.endsWith("}\",") || line.endsWith("}]\",")) {
				s = line.substring(x + 4, line.length() - 2);
			}
			else {
				sb.append(line);
				sb.append("\n");

				continue;
			}

			s = s.replaceAll("\\\\\"", "\"");

			try {
				if (StringUtil.startsWith(
						StringUtil.trim(s), StringPool.OPEN_BRACKET)) {

					s = JSONUtil.toString(new JSONArrayImpl(s));
				}
				else {
					s = JSONUtil.toString(new JSONObjectImpl(s));
				}
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(jsonException);
				}

				sb.append(line);
				sb.append("\n");

				continue;
			}

			s = s.replaceAll("\t*", "");
			s = s.replaceAll(",\n", ", ");

			s = s.replaceAll("\n", "");
			s = s.replaceAll("\"", "\\\\\"");

			String newLine = line.substring(0, x + 4) + s + "\"";

			if (line.endsWith(",")) {
				newLine = newLine + ",";
			}

			sb.append(newLine);
			sb.append("\n");
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

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