/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.task;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.tools.benchmarks.http.HttpResponse;
import com.liferay.portal.tools.benchmarks.http.HttpUtil;

import java.net.URL;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tina Tian
 */
public class LoginBenchmarksTask implements BenchmarksTask {

	public LoginBenchmarksTask(
		String hostName, int port, String emailAddress, String password) {

		_hostName = hostName;
		_port = port;
		_emailAddress = emailAddress;
		_password = password;
	}

	public List<Map.Entry<String, Long>> execute() throws Exception {
		HttpResponse httpResponse = _homePage();

		return ListUtil.fromArray(
			new AbstractMap.SimpleEntry<>(
				"homePage", httpResponse.getDuration()),
			new AbstractMap.SimpleEntry<>(
				"viewLoginPage", _viewLoginPage(httpResponse.getCSRFToken())),
			new AbstractMap.SimpleEntry<>(
				"login",
				_login(httpResponse.getCSRFToken(), _emailAddress, _password)),
			new AbstractMap.SimpleEntry<>("logout", _logout()));
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
			null, _newURL(StringPool.FORWARD_SLASH));

		_assertResult(httpResponse, "Liferay.currentURL");

		return httpResponse;
	}

	private long _login(String csrfToken, String emailAddress, String password)
		throws Exception {

		HttpResponse httpResponse1 = HttpUtil.doPost(
			null, csrfToken,
			new String[][] {
				{
					_P_P_ID_PREFIX + "_formDate",
					String.valueOf(System.currentTimeMillis())
				},
				{_P_P_ID_PREFIX + "_saveLastPath", StringPool.FALSE},
				{_P_P_ID_PREFIX + "_redirect", StringPool.BLANK},
				{_P_P_ID_PREFIX + "_doActionAfterLogin", StringPool.FALSE},
				{_P_P_ID_PREFIX + "_login", emailAddress},
				{_P_P_ID_PREFIX + "_password", password},
				{_P_P_ID_PREFIX + "_checkboxNames", "rememberMe"}
			},
			_newURL(_URL_LOGIN_POST));

		_assertRedirect(httpResponse1, _URL_REDIRECT);

		HttpResponse httpResponse2 = HttpUtil.doGet(
			csrfToken, _newURL(_URL_REDIRECT));

		_assertRedirect(httpResponse2, _URL_LOGIN_REDIRECT);

		HttpResponse httpResponse3 = HttpUtil.doGet(
			csrfToken, _newURL(_URL_LOGIN_REDIRECT));

		_assertResult(httpResponse3, "ProductNavigationUserPersonalBarPortlet");

		return _calculateDuration(httpResponse1, httpResponse2, httpResponse3);
	}

	private long _logout() throws Exception {
		HttpResponse httpResponse = HttpUtil.doGet(null, _newURL("/c/portal/logout"));

		return httpResponse.getDuration();
	}

	private URL _newURL(String path) throws Exception {
		return new URL("http", _hostName, _port, path);
	}

	private long _viewLoginPage(String csrfToken) throws Exception {
		HttpResponse httpResponse1 = HttpUtil.doGet(
			csrfToken, _newURL("/c/portal/login?windowState=exclusive"));

		_assertRedirect(httpResponse1, _URL_LOGIN_POPUP_REDIRECT);

		HttpResponse httpResponse2 = HttpUtil.doGet(
			csrfToken, _newURL(_URL_LOGIN_POPUP_REDIRECT));

		_assertResult(httpResponse2, "Remember Me");

		return _calculateDuration(httpResponse1, httpResponse2);
	}

	private static final String _P_P_ID =
		"com_liferay_login_web_portlet_LoginPortlet";

	private static final String _P_P_ID_PREFIX = StringPool.UNDERLINE + _P_P_ID;

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

	private static final String _URL_REDIRECT = "/c";

	private final String _emailAddress;
	private final String _hostName;
	private final String _password;
	private final int _port;

}