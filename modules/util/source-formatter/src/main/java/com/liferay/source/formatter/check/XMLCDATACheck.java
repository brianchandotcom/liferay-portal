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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLCDATACheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _cdataPattern1.matcher(content);

		while (matcher.find()) {
			String cdataValue = matcher.group(5);

			JSONObject jsonObject = _getJSONObject(cdataValue);

			if (jsonObject == null) {
				continue;
			}

			String indent = matcher.group(2);

			StringBundler sb = new StringBundler(9);

			sb.append(matcher.group(1));
			sb.append("\n");
			sb.append(indent);
			sb.append("\t<![CDATA[\n");
			sb.append(_toString(jsonObject, indent + "\t\t"));
			sb.append(indent);
			sb.append("\t]]>\n");
			sb.append(indent);
			sb.append(matcher.group(6));

			return StringUtil.replace(content, matcher.group(), sb.toString());
		}

		matcher = _cdataPattern2.matcher(content);

		while (matcher.find()) {
			String cdataValue = matcher.group(3);

			JSONObject jsonObject = _getJSONObject(cdataValue);

			if (jsonObject == null) {
				continue;
			}

			String match = matcher.group();

			String indent = matcher.group(2);

			StringBundler sb = new StringBundler(5);

			sb.append(matcher.group(1));
			sb.append("\n");
			sb.append(_toString(jsonObject, indent + "\t"));
			sb.append(indent);
			sb.append("]]>\n");

			String replacement = sb.toString();

			if (!match.equals(replacement)) {
				return StringUtil.replace(content, match, replacement);
			}
		}

		return content;
	}

	private JSONObject _getJSONObject(String s) {
		s = StringUtil.trim(s);

		if (Validator.isNull(s) || s.equals("{}")) {
			return null;
		}

		try {
			return new JSONObjectImpl(s);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	private String _toString(JSONObject jsonObject, String indent) {
		String s = JSONUtil.toString(jsonObject);

		String[] lines = StringUtil.splitLines(s);

		StringBundler sb = new StringBundler(lines.length * 3);

		for (String line : StringUtil.splitLines(s)) {
			sb.append(indent);
			sb.append(line);
			sb.append("\n");
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(XMLCDATACheck.class);

	private static final Pattern _cdataPattern1 = Pattern.compile(
		"(\n(\t*)<([\\w-]+)( .+)?>)<\\!\\[CDATA\\[(.*?)\\]\\]>(</\\3>\n)");
	private static final Pattern _cdataPattern2 = Pattern.compile(
		"(\n(\t*)<\\!\\[CDATA\\[)(.*?)\\]\\]>\n", Pattern.DOTALL);

}