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

package com.liferay.portal.servlet.filters.sso.opensso;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * See https://issues.liferay.com/browse/LEP-5943.
 * </p>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class OpenSSOUtil {

	public static Map<String, String> getAttributes(
		HttpServletRequest request, String serviceUrl) {

		return _instance._getAttributes(request, serviceUrl);
	}

	public static String getSubjectId(
		HttpServletRequest request, String serviceUrl) {

		return _instance._getSubjectId(request, serviceUrl);
	}

	public static boolean isAuthenticated(
			HttpServletRequest request, String serviceUrl)
		throws IOException {

		return _instance._isAuthenticated(request, serviceUrl);
	}

	public static boolean isValidServiceUrl(String serviceUrl) {
		return _instance._isValidServiceUrl(serviceUrl);
	}

	public static boolean isValidUrl(String url) {
		return _instance._isValidUrl(url);
	}

	public static boolean isValidUrls(String[] urls) {
		return _instance._isValidUrls(urls);
	}

	private OpenSSOUtil() {
	}

	private Map<String, String> _getAttributes(
		HttpServletRequest request, String serviceUrl) {

		Map<String, String> nameValues = new HashMap<String, String>();

		String url = serviceUrl.concat(_GET_ATTRIBUTES);

		try {
			URL urlObj = new URL(url);

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObj.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty(
				"Content-type", "application/x-www-form-urlencoded");

			String[] cookieNames = _getCookieNames(serviceUrl);

			_setCookieProperty(request, httpURLConnection, cookieNames);

			OutputStreamWriter osw = new OutputStreamWriter(
				httpURLConnection.getOutputStream());

			osw.write("dummy");

			osw.flush();

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream =
					(InputStream)httpURLConnection.getContent();

				UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(
						new InputStreamReader(inputStream));

				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (line.startsWith("userdetails.attribute.name=")) {
						String name = line.replaceFirst(
							"userdetails.attribute.name=", "");

						line = unsyncBufferedReader.readLine();

						if (line.startsWith("userdetails.attribute.value=")) {
							String value = line.replaceFirst(
								"userdetails.attribute.value=", "");

							nameValues.put(name, value);
						}
					}
				}
			}
			else if (_log.isDebugEnabled()) {
				_log.debug("Attributes response code " + responseCode);
			}
		}
		catch (MalformedURLException murle) {
			_log.error(murle.getMessage());

			if (_log.isDebugEnabled()) {
				_log.debug(murle, murle);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());

			if (_log.isDebugEnabled()) {
				_log.debug(ioe, ioe);
			}
		}

		return nameValues;
	}

	private String[] _getCookieNames(String serviceUrl) {
		String[] cookieNames = _cookieNamesMap.get(serviceUrl);

		if (cookieNames != null) {
			return cookieNames;
		}

		List<String> cookieNamesList = new ArrayList<String>();

		try {
			String cookieName = null;

			String url = serviceUrl.concat(_GET_COOKIE_NAME);

			URL urlObj = new URL(url);

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObj.openConnection();

			InputStream inputStream =
				(InputStream)httpURLConnection.getContent();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new InputStreamReader(inputStream));

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(url + " has response code " + responseCode);
				}
			}
			else {
				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (line.startsWith("string=")) {
						line = line.replaceFirst("string=", "");

						cookieName = line;
					}
				}
			}

			url = serviceUrl.concat(_GET_COOKIE_NAMES);

			urlObj = new URL(url);

			httpURLConnection = (HttpURLConnection)urlObj.openConnection();

			inputStream = (InputStream)httpURLConnection.getContent();

			unsyncBufferedReader = new UnsyncBufferedReader(
				new InputStreamReader(inputStream));

			if (httpURLConnection.getResponseCode() !=
					HttpURLConnection.HTTP_OK) {

				if (_log.isDebugEnabled()) {
					_log.debug(url + " has response code " + responseCode);
				}
			}
			else {
				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (line.startsWith("string=")) {
						line = line.replaceFirst("string=", "");

						if (cookieName.equals(line)) {
							cookieNamesList.add(0, cookieName);
						}
						else {
							cookieNamesList.add(line);
						}
					}
				}
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		cookieNames = cookieNamesList.toArray(
			new String[cookieNamesList.size()]);

		if (cookieNames.length > 0) {
			_cookieNamesMap.put(serviceUrl, cookieNames);
		}

		return cookieNames;
	}

	private String _getSubjectId(
		HttpServletRequest request, String serviceUrl) {

		String cookieName = _getCookieNames(serviceUrl)[0];

		return CookieKeys.getCookie(request, cookieName);
	}

	private boolean _isAuthenticated(
			HttpServletRequest request, String serviceUrl)
		throws IOException {

		boolean authenticated = false;

		boolean hasCookieNames = false;

		String[] cookieNames = _getCookieNames(serviceUrl);

		for (String cookieName : cookieNames) {
			if (CookieKeys.getCookie(request, cookieName) != null) {
				hasCookieNames = true;

				break;
			}
		}

		if (!hasCookieNames) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"User is not logged in because he has no OpenSSO cookies");
			}

			return false;
		}

		String url = serviceUrl.concat(_VALIDATE_TOKEN);

		URL urlObj = new URL(url);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)urlObj.openConnection();

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty(
			"Content-type", "application/x-www-form-urlencoded");

		_setCookieProperty(request, httpURLConnection, cookieNames);

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			httpURLConnection.getOutputStream());

		outputStreamWriter.write("dummy");

		outputStreamWriter.flush();

		int responseCode = httpURLConnection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			String data = StringUtil.read(httpURLConnection.getInputStream());

			if (StringUtil.toLowerCase(data).contains("boolean=true")) {
				authenticated = true;
			}
		}
		else if (_log.isDebugEnabled()) {
			_log.debug("Authentication response code " + responseCode);
		}

		return authenticated;
	}

	private boolean _isValidServiceUrl(String serviceUrl) {
		if (Validator.isNull(serviceUrl)) {
			return false;
		}

		String[] cookieNames = _instance._getCookieNames(serviceUrl);

		if (cookieNames.length == 0) {
			return false;
		}

		return true;
	}

	private boolean _isValidUrl(String url) {
		if (Validator.isNull(url)) {
			return false;
		}

		try {
			URL urlObj = new URL(url);

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObj.openConnection();

			int responseCode = httpURLConnection.getResponseCode();

			if (!((responseCode == HttpURLConnection.HTTP_OK) ||
				 ((responseCode >= HttpURLConnection.HTTP_MULT_CHOICE) &&
				  (responseCode <= HttpURLConnection.HTTP_NOT_MODIFIED)))) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"URL " + url + " is invalid with response code " +
							responseCode);
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"URL " + url + " is valid with response code " +
						responseCode);
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}

			return false;
		}

		return true;
	}

	private boolean _isValidUrls(String[] urls) {
		for (String url : urls) {
			if (!_isValidUrl(url)) {
				return false;
			}
		}

		return true;
	}

	private void _setCookieProperty(
		HttpServletRequest request, HttpURLConnection urlc,
		String[] cookieNames) {

		if (cookieNames.length == 0) {
			return;
		}

		StringBundler sb = new StringBundler(cookieNames.length * 4);

		for (String cookieName : cookieNames) {
			String cookieValue = CookieKeys.getCookie(request, cookieName);

			sb.append(cookieName);
			sb.append(StringPool.EQUAL);
			sb.append(StringPool.QUOTE);
			sb.append(cookieValue);
			sb.append(StringPool.QUOTE);
			sb.append(StringPool.SEMICOLON);
		}

		urlc.setRequestProperty("Cookie", sb.toString());
	}

	private static final String _GET_ATTRIBUTES = "/identity/attributes";

	private static final String _GET_COOKIE_NAME =
		"/identity/getCookieNameForToken";

	private static final String _GET_COOKIE_NAMES =
		"/identity/getCookieNamesToForward";

	private static final String _VALIDATE_TOKEN = "/identity/isTokenValid";

	private static final Log _log = LogFactoryUtil.getLog(OpenSSOUtil.class);

	private static final OpenSSOUtil _instance = new OpenSSOUtil();

	private final Map<String, String[]> _cookieNamesMap =
		new ConcurrentHashMap<String, String[]>();

}