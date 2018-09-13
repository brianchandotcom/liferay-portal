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

package com.liferay.portal.security.sso.opensso.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.sso.OpenSSO;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Marta Medio
 */
@Component(
	immediate = true, property = "version=openam-12", service = OpenSSO.class
)
public class OpenSSOV12Impl extends OpenSSOImpl {

	@Override
	public boolean isAuthenticated(
			HttpServletRequest request, String serviceUrl)
		throws IOException {

		boolean authenticated = false;

		boolean hasCookieNames = false;

		String[] cookieNames = getCookieNames(serviceUrl);

		for (String cookieName : cookieNames) {
			if (CookieKeys.getCookie(request, cookieName) != null) {
				hasCookieNames = true;

				break;
			}
		}

		if (!hasCookieNames) {
			if (_log.isInfoEnabled()) {
				_log.info(
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

		setCookieProperty(request, httpURLConnection, cookieNames);

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
			httpURLConnection.getOutputStream());

		outputStreamWriter.write("dummy");

		outputStreamWriter.flush();

		int responseCode = httpURLConnection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			String data = StringUtil.toLowerCase(
				StringUtil.read(httpURLConnection.getInputStream()));

			if (data.contains("boolean=true")) {
				authenticated = true;
			}
		}
		else if (_log.isDebugEnabled()) {
			_log.debug("Authentication response code " + responseCode);
		}

		return authenticated;
	}

	private static final String _VALIDATE_TOKEN = "/identity/isTokenValid";

	private static final Log _log = LogFactoryUtil.getLog(OpenSSOV12Impl.class);

}