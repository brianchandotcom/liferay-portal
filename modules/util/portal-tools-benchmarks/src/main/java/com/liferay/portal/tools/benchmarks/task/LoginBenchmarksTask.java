/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.task;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.tools.benchmarks.http.HttpResponse;
import com.liferay.portal.tools.benchmarks.http.HttpUtil;

import java.net.URL;

import java.util.List;

import org.junit.Assert;

/**
 * @author Tina Tian
 */
public class LoginBenchmarksTask implements BenchmarksTask {

	public LoginBenchmarksTask(
		String emailAddress, String hostname, String password, int port) {

		_emailAddress = emailAddress;
		_hostname = hostname;
		_password = password;
		_port = port;
	}

	public List<ObjectValuePair<String, Long>> execute() throws Exception {
		HttpResponse httpResponse = HttpUtil.doGet(
			null, _creatURL(StringPool.FORWARD_SLASH));

		_assertResult(httpResponse, "Liferay.currentURL");

		return ListUtil.fromArray(
			new ObjectValuePair<>("homePage", httpResponse.getDuration()),
			new ObjectValuePair<>(
				"viewLoginPage", _viewLoginPage(httpResponse.getCSRFToken())),
			new ObjectValuePair<>(
				"login",
				_login(httpResponse.getCSRFToken(), _emailAddress, _password)),
			new ObjectValuePair<>("logout", _logout()));
	}

	private void _assertRedirect(
			String expectedRedirect, HttpResponse httpResponse)
		throws Exception {

		Assert.assertEquals(httpResponse.getStatusCode(), 302);

		URL url = _creatURL(expectedRedirect);

		Assert.assertEquals(url.toString(), httpResponse.getRedirect());
	}

	private void _assertResult(HttpResponse httpResponse, String key) {
		Assert.assertEquals(httpResponse.getStatusCode(), 200);

		String httpResponseString = httpResponse.toString();

		Assert.assertTrue(httpResponseString.contains(key));
	}

	private URL _creatURL(String path) throws Exception {
		return new URL("http", _hostname, _port, path);
	}

	private long _getDuration(HttpResponse... httpResponses) {
		long duration = 0;

		for (HttpResponse httpResponse : httpResponses) {
			duration += httpResponse.getDuration();
		}

		return duration;
	}

	private long _login(String csrfToken, String emailAddress, String password)
		throws Exception {

		HttpResponse httpResponse1 = HttpUtil.doPost(
			null, csrfToken,
			new String[][] {
				{
					_P_P_ID_NAMESPACE + "_formDate",
					String.valueOf(System.currentTimeMillis())
				},
				{_P_P_ID_NAMESPACE + "_saveLastPath", StringPool.FALSE},
				{_P_P_ID_NAMESPACE + "_redirect", StringPool.BLANK},
				{_P_P_ID_NAMESPACE + "_doActionAfterLogin", StringPool.FALSE},
				{_P_P_ID_NAMESPACE + "_login", emailAddress},
				{_P_P_ID_NAMESPACE + "_password", password},
				{_P_P_ID_NAMESPACE + "_checkboxNames", "rememberMe"}
			},
			_creatURL(
				StringBundler.concat(
					"/home?p_p_id=", _P_P_ID, "&p_p_lifecycle=1&",
					"p_p_state=normal&p_p_mode=view&", _P_P_ID_NAMESPACE,
					"_javax.portlet.action=/login/login&", _P_P_ID_NAMESPACE,
					"_mvcRenderCommandName=/login/login")));

		_assertRedirect("/c", httpResponse1);

		HttpResponse httpResponse2 = HttpUtil.doGet(csrfToken, _creatURL("/c"));

		_assertRedirect(StringPool.SLASH, httpResponse2);

		HttpResponse httpResponse3 = HttpUtil.doGet(
			csrfToken, _creatURL(StringPool.SLASH));

		_assertResult(httpResponse3, "ProductNavigationUserPersonalBarPortlet");

		return _getDuration(httpResponse1, httpResponse2, httpResponse3);
	}

	private long _logout() throws Exception {
		HttpResponse httpResponse = HttpUtil.doGet(
			null, _creatURL("/c/portal/logout"));

		return httpResponse.getDuration();
	}

	private long _viewLoginPage(String csrfToken) throws Exception {
		HttpResponse httpResponse1 = HttpUtil.doGet(
			csrfToken, _creatURL("/c/portal/login?windowState=exclusive"));
		String redirect = StringBundler.concat(
			"/home?p_p_id=", _P_P_ID, "&p_p_lifecycle=0&",
			"p_p_state=exclusive&p_p_mode=view&", _P_P_ID_NAMESPACE,
			"_mvcRenderCommandName=/login/login&saveLastPath=false");

		_assertRedirect(redirect, httpResponse1);

		HttpResponse httpResponse2 = HttpUtil.doGet(
			csrfToken, _creatURL(redirect));

		_assertResult(httpResponse2, "Remember Me");

		return _getDuration(httpResponse1, httpResponse2);
	}

	private static final String _P_P_ID =
		"com_liferay_login_web_portlet_LoginPortlet";

	private static final String _P_P_ID_NAMESPACE =
		StringPool.UNDERLINE + _P_P_ID;

	private final String _emailAddress;
	private final String _hostname;
	private final String _password;
	private final int _port;

}