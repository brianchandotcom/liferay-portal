/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JsonSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLStylingCheck extends BaseStylingCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatAssignBlock(content);

		return formatStyling(content);
	}

	private String _formatAssignBlock(String content) {
		int x = -1;

		while (true) {
			x = content.indexOf("<#assign\n", x + 1);

			if (x == -1) {
				break;
			}

			int y = x;

			while (true) {
				y = content.indexOf("/>", x + 1);

				if (y == -1) {
					break;
				}

				if (ToolsUtil.isInsideQuotes(content, y)) {
					continue;
				}

				x = x + 8;

				String assignContent = content.substring(x, y);

				int level = getLevel(
					assignContent,
					new String[] {
						StringPool.OPEN_CURLY_BRACE, StringPool.OPEN_PARENTHESIS
					},
					new String[] {
						StringPool.CLOSE_CURLY_BRACE,
						StringPool.CLOSE_PARENTHESIS
					});

				if (level != 0) {
					continue;
				}

				String newAssignContent = _formatJsonAssign(assignContent);

				if (assignContent.equals(newAssignContent)) {
					break;
				}

				return StringUtil.replace(
					content, assignContent, newAssignContent, x);
			}
		}

		return content;
	}

	private String _formatJsonAssign(String content) {
		Matcher matcher = _assignPattern.matcher(content);

		while (matcher.find()) {
			int x = matcher.end();

			while (true) {
				x = content.indexOf("}\n", x + 1);

				if ((x == -1) || ToolsUtil.isInsideQuotes(content, x)) {
					continue;
				}

				String s = content.substring(matcher.start(2), x + 2);

				int level = getLevel(
					s, StringPool.OPEN_CURLY_BRACE,
					StringPool.CLOSE_CURLY_BRACE);

				if (level != 0) {
					continue;
				}

				JSONObject jsonObject = JsonSourceUtil.getJSONObject(s);

				if (jsonObject == null) {
					break;
				}

				String indent = matcher.group(1);

				StringBundler sb = new StringBundler(2);

				sb.append("\n");
				sb.append(
					JsonSourceUtil.fixIndentation(jsonObject, indent + "\t"));

				String replacement = sb.toString();

				if (s.equals(replacement)) {
					break;
				}

				return StringUtil.replace(
					content, s, replacement, matcher.start(2));
			}
		}

		return content;
	}

	private static final Pattern _assignPattern = Pattern.compile(
		"\n(\t*)\\w+ =(\\s*\\{)");

}