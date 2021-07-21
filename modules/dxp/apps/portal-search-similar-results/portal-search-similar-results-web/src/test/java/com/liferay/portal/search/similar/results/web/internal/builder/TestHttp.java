/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

/**
 * @author André de Oliveira
 */
public class TestHttp {

	public static String addParameter(String url, String name, String value) {
		if (url == null) {
			return null;
		}

		String[] urlArray = stripURLAnchor(url, StringPool.POUND);

		url = urlArray[0];

		String anchor = urlArray[1];

		StringBundler sb = new StringBundler(6);

		sb.append(url);

		if (url.indexOf(CharPool.QUESTION) == -1) {
			sb.append(StringPool.QUESTION);
		}
		else if (!url.endsWith(StringPool.QUESTION) &&
				 !url.endsWith(StringPool.AMPERSAND)) {

			sb.append(StringPool.AMPERSAND);
		}

		sb.append(name);
		sb.append(StringPool.EQUAL);
		sb.append(URLCodec.encodeURL(value));
		sb.append(anchor);

		return shortenURL(sb.toString());
	}

	public static String decodeURL(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		try {
			return URLCodec.decodeURL(url, StringPool.UTF8);
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		return StringPool.BLANK;
	}

	public static Http getInstance() {
		Http http = Mockito.mock(Http.class);

		Mockito.doAnswer(
			invocationOnMock -> decodeURL(getArg(invocationOnMock))
		).when(
			http
		).decodeURL(
			Mockito.anyString()
		);

		Mockito.doAnswer(
			invocationOnMock -> getPath(getArg(invocationOnMock))
		).when(
			http
		).getPath(
			Mockito.anyString()
		);

		Mockito.doAnswer(
			invocationOnMock -> getQueryString(getArg(invocationOnMock))
		).when(
			http
		).getQueryString(
			Mockito.anyString()
		);

		Mockito.doAnswer(
			invocationOnMock -> parameterMapFromString(getArg(invocationOnMock))
		).when(
			http
		).parameterMapFromString(
			Mockito.anyString()
		);

		Mockito.doAnswer(
			invocationOnMock -> setParameter(
				getArg(invocationOnMock, 0), getArg(invocationOnMock, 1),
				getArg(invocationOnMock, 2))
		).when(
			http
		).setParameter(
			Mockito.anyString(), Mockito.anyString(), Mockito.anyString()
		);

		return http;
	}

	public static String getPath(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		if (url.startsWith(Http.HTTP)) {
			int pos = url.indexOf(
				CharPool.SLASH, Http.HTTPS_WITH_SLASH.length());

			url = url.substring(pos);
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		return url.substring(0, pos);
	}

	public static String getQueryString(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return StringPool.BLANK;
		}

		return url.substring(pos + 1);
	}

	public static Map<String, String[]> parameterMapFromString(
		String queryString) {

		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		if (Validator.isNull(queryString)) {
			return parameterMap;
		}

		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() == 0) {
				continue;
			}

			String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

			if (kvp.length == 0) {
				continue;
			}

			String value = StringPool.BLANK;

			if (kvp.length > 1) {
				try {
					value = decodeURL(kvp[1]);
				}
				catch (IllegalArgumentException illegalArgumentException) {
					continue;
				}
			}

			String key = kvp[0];

			String[] values = parameterMap.get(key);

			if (values == null) {
				parameterMap.put(key, new String[] {value});
			}
			else {
				parameterMap.put(key, ArrayUtil.append(values, value));
			}
		}

		return parameterMap;
	}

	public static String removeParameter(String url, String name) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return url;
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		String[] array = stripURLAnchor(url, StringPool.POUND);

		url = array[0];

		String anchor = array[1];

		StringBundler sb = new StringBundler();

		sb.append(url.substring(0, pos + 1));

		String[] parameters = StringUtil.split(
			url.substring(pos + 1), CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				if (!key.equals(name)) {
					sb.append(key);
					sb.append(StringPool.EQUAL);
					sb.append(value);
					sb.append(StringPool.AMPERSAND);
				}
			}
		}

		url = StringUtil.replace(
			sb.toString(), StringPool.AMPERSAND + StringPool.AMPERSAND,
			StringPool.AMPERSAND);

		if (url.endsWith(StringPool.AMPERSAND)) {
			url = url.substring(0, url.length() - 1);
		}

		if (url.endsWith(StringPool.QUESTION)) {
			url = url.substring(0, url.length() - 1);
		}

		return url + anchor;
	}

	public static String setParameter(String url, String name, String value) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return url;
		}

		url = removeParameter(url, name);

		return addParameter(url, name, value);
	}

	public static String shortenURL(String url) {
		return url;
	}

	public static String[] stripURLAnchor(String url, String separator) {
		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	protected static String getArg(InvocationOnMock invocationOnMock) {
		return getArg(invocationOnMock, 0);
	}

	protected static String getArg(
		InvocationOnMock invocationOnMock, int index) {

		return invocationOnMock.getArgumentAt(index, String.class);
	}

}