/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.task;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.benchmarks.http.HttpResponse;
import com.liferay.portal.tools.benchmarks.http.HttpUtil;

import java.net.URL;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tina Tian
 */
public class LoginBenchmarksTask implements BenchmarksTask {

	public LoginBenchmarksTask(
		String hostName, int port, String email, String password) {

		_hostName = hostName;
		_port = port;
		_email = email;
		_password = password;
	}

	public List<Map.Entry<String, Long>> execute() throws Exception {
		HttpResponse httpResponse = _homePage();

		List<Map.Entry<String, Long>> results = new ArrayList<>();

		results.add(
			new AbstractMap.SimpleEntry<>(
				"homePage", httpResponse.getDuration()));
		results.add(
			new AbstractMap.SimpleEntry<>(
				"viewLoginPage", _viewLoginPage(httpResponse.getCSRFToken())));
		results.add(
			new AbstractMap.SimpleEntry<>(
				"login",
				_login(_email, _password, httpResponse.getCSRFToken())));
		results.add(new AbstractMap.SimpleEntry<>("logout", _logout()));

		return results;
	}

	private void _assertRedirect(
			HttpResponse httpResponse, String expectedRedirect)
		throws Exception {

		if (httpResponse.getStatusCode() != 302) {
			throw new IllegalStateException(
				"Response status code is wrong! Expect 302 but actual is " +
					httpResponse.getStatusCode());
		}

		URL url = _newURL(expectedRedirect);

		if (!Objects.equals(url.toString(), httpResponse.getRedirect())) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Redirect URL is wrong! Expect ", url, " but actual is ",
					httpResponse.getRedirect()));
		}
	}

	private void _assertResult(HttpResponse httpResponse, String key) {
		if (httpResponse.getStatusCode() != 200) {
			throw new IllegalStateException(
				"Response status code is wrong! Expect 200 but actual is " +
					httpResponse.getStatusCode());
		}

		if (key != null) {
			String httpResponseString = httpResponse.toString();

			if (!httpResponseString.contains(key)) {
				throw new IllegalStateException(
					StringBundler.concat(
						"Unable to find key ", key, " in response : \\n",
						httpResponseString));
			}
		}
	}

	private long _calculateDuration(HttpResponse... httpResponses) {
		long duration = 0;

		for (HttpResponse httpResponse : httpResponses) {
			duration += httpResponse.getDuration();
		}

		return duration;
	}

	private HttpResponse _homePage() throws Exception {
		HttpResponse httpResponse = HttpUtil.doGet(
			_newURL(StringPool.FORWARD_SLASH), null);

		_assertResult(httpResponse, _KEY_HOME_PAGE);

		return httpResponse;
	}

	private long _login(String userEmail, String password, String csrfToken)
		throws Exception {

		HttpResponse httpResponse1 = HttpUtil.doPost(
			_newURL(_URL_LOGIN_POST),
			new String[][] {
				{
					_P_P_ID_PREFIX + "_formDate",
					String.valueOf(System.currentTimeMillis())
				},
				{_P_P_ID_PREFIX + "_saveLastPath", StringPool.FALSE},
				{_P_P_ID_PREFIX + "_redirect", StringPool.BLANK},
				{_P_P_ID_PREFIX + "_doActionAfterLogin", StringPool.FALSE},
				{_P_P_ID_PREFIX + "_login", userEmail},
				{_P_P_ID_PREFIX + "_password", password},
				{_P_P_ID_PREFIX + "_checkboxNames", "rememberMe"}
			},
			csrfToken, null);

		_assertRedirect(httpResponse1, _URL_REDIRECT);

		HttpResponse httpResponse2 = HttpUtil.doGet(
			_newURL(_URL_REDIRECT), csrfToken);

		_assertRedirect(httpResponse2, _URL_LOGIN_REDIRECT);

		HttpResponse httpResponse3 = HttpUtil.doGet(
			_newURL(_URL_LOGIN_REDIRECT), csrfToken);

		_assertResult(httpResponse3, _KEY_LOGIN);

		return _calculateDuration(httpResponse1, httpResponse2, httpResponse3);
	}

	private long _logout() throws Exception {
		HttpResponse httpResponse = HttpUtil.doGet(_newURL(_URL_LOGOUT), null);

		return httpResponse.getDuration();
	}

	private URL _newURL(String path) throws Exception {
		return new URL("http", _hostName, _port, path);
	}

	private long _viewLoginPage(String csrfToken) throws Exception {
		HttpResponse httpResponse1 = HttpUtil.doGet(
			_newURL(_URL_LOGIN_POPUP), csrfToken);

		_assertRedirect(httpResponse1, _URL_LOGIN_POPUP_REDIRECT);

		HttpResponse httpResponse2 = HttpUtil.doGet(
			_newURL(_URL_LOGIN_POPUP_REDIRECT), csrfToken);

		_assertResult(httpResponse2, _KEY_LOGIN_POPUP);

		return _calculateDuration(httpResponse1, httpResponse2);
	}

	private static final String _KEY_HOME_PAGE = "Liferay.currentURL";

	private static final String _KEY_LOGIN =
		"ProductNavigationUserPersonalBarPortlet";

	private static final String _KEY_LOGIN_POPUP = "Remember Me";

	private static final String _P_P_ID =
		"com_liferay_login_web_portlet_LoginPortlet";

	private static final String _P_P_ID_PREFIX = StringPool.UNDERLINE + _P_P_ID;

	private static final String _URL_LOGIN_POPUP =
		"/c/portal/login?windowState=exclusive";

	private static final String _URL_LOGIN_POPUP_REDIRECT =
		StringBundler.concat(
			"/home?p_p_id=", _P_P_ID, "&p_p_lifecycle=0&",
			"p_p_state=exclusive&p_p_mode=view&", _P_P_ID_PREFIX,
			"_mvcRenderCommandName=/login/login&saveLastPath=false");

	private static final String _URL_LOGIN_POST = StringBundler.concat(
		"/home?p_p_id=", _P_P_ID, "&p_p_lifecycle=1&",
		"p_p_state=normal&p_p_mode=view&", _P_P_ID_PREFIX,
		"_javax.portlet.action=/login/login&", _P_P_ID_PREFIX,
		"_mvcRenderCommandName=/login/login");

	private static final String _URL_LOGIN_REDIRECT = StringPool.SLASH;

	private static final String _URL_LOGOUT = "/c/portal/logout";

	private static final String _URL_REDIRECT = "/c";

	private final String _email;
	private final String _hostName;
	private final String _password;
	private final int _port;

}