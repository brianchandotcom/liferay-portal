/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class TunnelUtil {

	public static Key getPresharedKey() throws InvalidKeyException {
		String presharedKey = PropsValues.TUNNELING_SERVLET_PRESHARED_SECRET;

		if (Validator.isNull(presharedKey)) {
			throw new InvalidKeyException(
				"The tunneling servlet preshared key is not set");
		}

		if ((presharedKey.length() != 16) && (presharedKey.length() != 32) &&
			(presharedKey.length() != 64)) {

			throw new InvalidKeyException(
				"The configured preshared key must be either 16, 32 or 64 " +
					"characters in length.");
		}

		return new SecretKeySpec(
			presharedKey.getBytes(), _TUNNEL_ENCRYPTION_ALGORITHM);
	}

	public static Object invoke(
			HttpPrincipal httpPrincipal, MethodHandler methodHandler)
		throws Exception {

		String password = Encryptor.encrypt(
			getPresharedKey(), httpPrincipal.getLogin());

		httpPrincipal.setPassword(password);

		HttpURLConnection urlc = _getConnection(httpPrincipal);

		ObjectOutputStream oos = new ObjectOutputStream(urlc.getOutputStream());

		oos.writeObject(
			new ObjectValuePair<HttpPrincipal, MethodHandler>(
				httpPrincipal, methodHandler));

		oos.flush();
		oos.close();

		Object returnObj = null;

		try {
			ObjectInputStream ois = new ObjectInputStream(
				urlc.getInputStream());

			returnObj = ois.readObject();

			ois.close();
		}
		catch (EOFException eofe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Error while reading object", eofe);
			}
		}
		catch (IOException ioe) {
			String ioeMessage = ioe.getMessage();

			if ((ioeMessage != null) &&
				ioeMessage.contains("HTTP response code: 401")) {

				throw new PrincipalException(ioeMessage);
			}
			else {
				throw ioe;
			}
		}

		if ((returnObj != null) && returnObj instanceof Exception) {
			throw (Exception)returnObj;
		}

		return returnObj;
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

		httpURLConnection.setRequestMethod("POST");

		if (Validator.isNotNull(httpPrincipal.getLogin()) &&
			Validator.isNotNull(httpPrincipal.getPassword())) {

			String userNameAndPassword =
				httpPrincipal.getLogin() + StringPool.COLON +
					httpPrincipal.getPassword();

			httpURLConnection.setRequestProperty(
				HttpHeaders.AUTHORIZATION,
				HttpServletRequest.BASIC_AUTH + StringPool.SPACE +
					Base64.encode(userNameAndPassword.getBytes()));
		}

		return httpURLConnection;
	}

	private static final String _TUNNEL_ENCRYPTION_ALGORITHM = "AES";

	private static final boolean _VERIFY_SSL_HOSTNAME = GetterUtil.getBoolean(
		PropsUtil.get(TunnelUtil.class.getName() + ".verify.ssl.hostname"));

	private static Log _log = LogFactoryUtil.getLog(TunnelUtil.class);

}