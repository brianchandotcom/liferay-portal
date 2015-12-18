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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.tunnel.TunnelAuthenticationManagerUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProtectedClassLoaderObjectInputStream;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author Brian Wing Shun Chan
 */
public class TunnelUtil {

	public static Object invoke(
			HttpPrincipal httpPrincipal, MethodHandler methodHandler)
		throws Exception {

		HttpURLConnection httpURLConnection = _getConnection(httpPrincipal);

		TunnelAuthenticationManagerUtil.setCredentials(
			httpPrincipal.getLogin(), httpURLConnection);

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				httpURLConnection.getOutputStream())) {

			objectOutputStream.writeObject(
				new ObjectValuePair<HttpPrincipal, MethodHandler>(
					httpPrincipal, methodHandler));
		}

		Object returnObject = null;

		Thread thread = Thread.currentThread();

		try (ObjectInputStream objectInputStream =
				new ProtectedClassLoaderObjectInputStream(
					httpURLConnection.getInputStream(),
					thread.getContextClassLoader())) {

			returnObject = objectInputStream.readObject();
		}
		catch (EOFException eofe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read object", eofe);
			}
		}
		catch (IOException ioe) {
			String ioeMessage = ioe.getMessage();

			if ((ioeMessage != null) &&
				ioeMessage.contains("HTTP response code: 401")) {

				throw new PrincipalException.MustBeAuthenticated(
					httpPrincipal.getLogin());
			}
			else {
				throw ioe;
			}
		}

		if ((returnObject != null) && returnObject instanceof Exception) {
			throw (Exception)returnObject;
		}

		return returnObject;
	}

	private static HttpURLConnection _getConnection(HttpPrincipal httpPrincipal)
		throws IOException {

		if ((httpPrincipal == null) || (httpPrincipal.getUrl() == null)) {
			return null;
		}

		URL url = new URL(httpPrincipal.getUrl() + "/api/liferay/do");

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);

		if (!_VERIFY_SSL_HOSTNAME &&
			(httpURLConnection instanceof HttpsURLConnection)) {

			HttpsURLConnection httpsURLConnection =
				(HttpsURLConnection)httpURLConnection;

			httpsURLConnection.setHostnameVerifier(
				new HostnameVerifier() {

					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}

				}
			);
		}

		httpURLConnection.setRequestProperty(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.APPLICATION_X_JAVA_SERIALIZED_OBJECT);
		httpURLConnection.setUseCaches(false);

		httpURLConnection.setRequestMethod(HttpMethods.POST);

		return httpURLConnection;
	}

	private static final boolean _VERIFY_SSL_HOSTNAME = GetterUtil.getBoolean(
		PropsUtil.get(TunnelUtil.class.getName() + ".verify.ssl.hostname"));

	private static final Log _log = LogFactoryUtil.getLog(TunnelUtil.class);

}