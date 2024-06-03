/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.http;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.net.URLDecoder;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tina Tian
 */
public class HttpResponse {

	public HttpResponse(
		long duration, Map<String, List<String>> headers, int statusCode,
		String statusLine, String text) {

		_duration = duration;
		_headers = headers;
		_statusCode = statusCode;
		_statusLine = statusLine;
		_text = text;
	}

	public String getCSRFToken() {
		Matcher matcher = _csrfTokenPattern.matcher(_text);

		if (matcher.find()) {
			return matcher.group(1);
		}

		throw new IllegalStateException("Unable to parse CSRF token");
	}

	public long getDuration() {
		return _duration;
	}

	public String getRedirect() throws Exception {
		String location = _getHeaderValue("Location");

		if (location.contains(StringPool.PERCENT)) {
			location = URLDecoder.decode(location, StringPool.UTF8);
		}

		return location;
	}

	public int getStatusCode() {
		return _statusCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append(_statusCode);
		sb.append(CharPool.SPACE);
		sb.append(_statusLine);
		sb.append(StringPool.NEW_LINE);

		for (Map.Entry<String, List<String>> entry : _headers.entrySet()) {
			String key = entry.getKey();

			if (key != null) {
				sb.append(key);
				sb.append(StringPool.COLON);
			}

			for (String value : entry.getValue()) {
				sb.append(value);
				sb.append(StringPool.COMMA);
			}

			sb.setStringAt(StringPool.NEW_LINE, sb.index() - 1);
		}

		sb.append(_text);

		return sb.toString();
	}

	private String _getHeaderValue(String name) {
		List<String> values = _headers.get(name);

		if (ListUtil.isEmpty(values)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(values.size() * 2);

		for (String value : values) {
			sb.append(value);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final Pattern _csrfTokenPattern = Pattern.compile(
		"Liferay\\.authToken = '(.*)';");

	private final long _duration;
	private final Map<String, List<String>> _headers;
	private final int _statusCode;
	private final String _statusLine;
	private final String _text;

}