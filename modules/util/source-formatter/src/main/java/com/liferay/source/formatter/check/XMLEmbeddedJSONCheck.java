/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JsonSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLEmbeddedJSONCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatJSONInCDATA(content);

		return _formatJSONInFencedCodeBlock(content);
	}

	private String _formatJSONInCDATA(String content) {
		Matcher matcher = _cdataPattern1.matcher(content);

		while (matcher.find()) {
			String cdataValue = matcher.group(5);

			cdataValue = JsonSourceUtil.removeJSONComments(cdataValue);

			JSONObject jsonObject = JsonSourceUtil.getJSONObject(cdataValue);

			if (jsonObject == null) {
				continue;
			}

			String indent = matcher.group(2);

			StringBundler sb = new StringBundler(9);

			sb.append(matcher.group(1));
			sb.append("\n");
			sb.append(indent);
			sb.append("\t<![CDATA[\n");
			sb.append(
				JsonSourceUtil.fixIndentation(jsonObject, indent + "\t\t"));
			sb.append(indent);
			sb.append("\t]]>\n");
			sb.append(indent);
			sb.append(matcher.group(6));

			return StringUtil.replace(content, matcher.group(), sb.toString());
		}

		matcher = _cdataPattern2.matcher(content);

		while (matcher.find()) {
			String cdataValue = matcher.group(3);

			cdataValue = JsonSourceUtil.removeJSONComments(cdataValue);

			JSONObject jsonObject = JsonSourceUtil.getJSONObject(cdataValue);

			if (jsonObject == null) {
				continue;
			}

			String indent = matcher.group(2);
			String match = matcher.group();

			StringBundler sb = new StringBundler(5);

			sb.append(matcher.group(1));
			sb.append("\n");
			sb.append(JsonSourceUtil.fixIndentation(jsonObject, indent + "\t"));
			sb.append(indent);
			sb.append("]]>\n");

			String replacement = sb.toString();

			if (!match.equals(replacement)) {
				return StringUtil.replace(content, match, replacement);
			}
		}

		return content;
	}

	private String _formatJSONInFencedCodeBlock(String content) {
		Matcher matcher = _fencedCodeBlockPattern.matcher(content);

		while (matcher.find()) {
			String jsonString = matcher.group(1);

			String newJsonString = JsonSourceUtil.removeJSONComments(
				jsonString);

			JSONObject jsonObject = JsonSourceUtil.getJSONObject(newJsonString);

			if (jsonObject == null) {
				continue;
			}

			newJsonString = JsonSourceUtil.fixIndentation(jsonObject, "");

			if (jsonString.equals(newJsonString)) {
				continue;
			}

			return StringUtil.replaceFirst(
				content, jsonString, newJsonString, matcher.start());
		}

		return content;
	}

	private static final Pattern _cdataPattern1 = Pattern.compile(
		"(\n(\t*)<([\\w-]+)( .+)?>)<\\!\\[CDATA\\[(.*?)\\]\\]>(</\\3>\n)");
	private static final Pattern _cdataPattern2 = Pattern.compile(
		"(\n(\t*)<\\!\\[CDATA\\[)(.*?)\\]\\]>\n", Pattern.DOTALL);
	private static final Pattern _fencedCodeBlockPattern = Pattern.compile(
		"\n```json\n(.+?\n)```", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

}