/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLTagCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatTags(content);

		return _formatAssignTags(content);
	}

	private String _formatAssignTags(String content) {
		Matcher matcher = _incorrectAssignTagPattern.matcher(content);

		content = matcher.replaceAll("$1 />\n");

		matcher = _assignTagsBlockPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String tabs = matcher.group(2);

			String replacement = StringUtil.removeSubstrings(
				match, "<#assign ", "<#assign\n", "/>");

			replacement = StringUtil.removeChar(replacement, CharPool.TAB);

			String[] lines = StringUtil.splitLines(replacement);

			StringBundler sb = new StringBundler((3 * lines.length) + 5);

			sb.append(tabs);
			sb.append("<#assign");

			for (String line : lines) {
				sb.append("\n\t");
				sb.append(tabs);
				sb.append(line);
			}

			sb.append(StringPool.NEW_LINE);
			sb.append(tabs);
			sb.append("/>\n\n");

			content = StringUtil.replace(content, match, sb.toString());
		}

		return content;
	}

	private String _formatTags(String content) {
		Matcher matcher = _tagPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group(3);

			Map<String, String> attributesMap = _getAttributesMap(match);

			if (attributesMap.isEmpty()) {
				continue;
			}

			String tabs = matcher.group(2);

			String delimeter = StringPool.SPACE;

			if (attributesMap.size() > 1) {
				delimeter = "\n" + tabs + "\t";
			}

			StringBundler sb = new StringBundler(
				(attributesMap.size() * 4) + 4);

			sb.append(_getTagName(match));
			sb.append(delimeter);

			for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
				sb.append(entry.getKey());
				sb.append(StringPool.EQUAL);
				sb.append(entry.getValue());
				sb.append(delimeter);
			}

			sb.setIndex(sb.index() - 1);

			String closingTag = matcher.group(4);

			if (attributesMap.size() > 1) {
				sb.append("\n");
				sb.append(tabs);
			}
			else if (closingTag.equals("/>")) {
				sb.append(StringPool.SPACE);
			}

			String replacement = sb.toString();

			if (!replacement.equals(match)) {
				return StringUtil.replaceFirst(
					content, match, replacement, matcher.start());
			}
		}

		return content;
	}

	private Map<String, String> _getAttributesMap(String s) {
		Map<String, String> attributesMap = new TreeMap<>();

		String attributeName = null;

		while (true) {
			boolean match = false;

			Matcher matcher = _tagAttributePattern.matcher(s);

			while (matcher.find()) {
				if (ToolsUtil.isInsideQuotes(s, matcher.end() - 1)) {
					continue;
				}

				match = true;

				break;
			}

			if (!match) {
				break;
			}

			if (attributeName != null) {
				attributesMap.put(
					attributeName,
					StringUtil.trim(s.substring(0, matcher.start())));
			}

			attributeName = matcher.group(1);

			s = s.substring(matcher.end());
		}

		if (attributeName != null) {
			attributesMap.put(attributeName, StringUtil.trim(s));
		}

		return attributesMap;
	}

	private String _getTagName(String s) {
		StringBundler sb = new StringBundler();

		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				return sb.toString();
			}

			sb.append(c);
		}

		return sb.toString();
	}

	private static final Pattern _assignTagsBlockPattern = Pattern.compile(
		"((\t*)<#assign(.(?!<[#@]))+?/>(\n|$)+){2,}",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _incorrectAssignTagPattern = Pattern.compile(
		"(<#assign .*=.*[^/])>(\n|$)");
	private static final Pattern _tagAttributePattern = Pattern.compile(
		"\\s([^\\s=]+)\\s*?=");
	private static final Pattern _tagPattern = Pattern.compile(
		"(\\A|\n)(\t*)<@(\\S[^>]*?)(/?>)(\n|\\Z)", Pattern.DOTALL);

}