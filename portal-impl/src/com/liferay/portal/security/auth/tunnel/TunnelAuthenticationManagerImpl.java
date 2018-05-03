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

package com.liferay.portal.security.auth.tunnel;

import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.encryptor.EncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.RemoteAuthException;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthorizationHeader;
import com.liferay.portal.kernel.security.auth.tunnel.TunnelAuthenticationManager;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.net.HttpURLConnection;

import java.security.Key;

import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * @author Tomas Polesovsky
 */
public class TunnelAuthenticationManagerImpl
	implements TunnelAuthenticationManager {

	@Override
	public long getUserId(HttpServletRequest httpServletRequest)
		throws AuthException {

		HttpAuthorizationHeader httpAuthorizationHeader =
			HttpAuthManagerUtil.parse(httpServletRequest);

		if (httpAuthorizationHeader == null) {
			return 0;
		}

		String scheme = httpAuthorizationHeader.getScheme();

		if (!StringUtil.equalsIgnoreCase(
				scheme, HttpAuthorizationHeader.SCHEME_BASIC)) {

			AuthException authException = new RemoteAuthException(
				"Invalid scheme " + scheme);

			authException.setType(AuthException.INTERNAL_SERVER_ERROR);

			throw authException;
		}

		String expectedPassword = null;

		String login = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME);

		try {
			expectedPassword = Encryptor.encrypt(getSharedSecretKey(), login);
		}
		catch (EncryptorException ee) {
			AuthException authException = new RemoteAuthException(ee);

			authException.setType(AuthException.INTERNAL_SERVER_ERROR);

			throw authException;
		}
		catch (AuthException ae) {
			AuthException authException = new RemoteAuthException(ae);

			authException.setType(ae.getType());

			throw authException;
		}

		String password = httpAuthorizationHeader.getAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD);

		if (!Objects.equals(expectedPassword, password)) {
			AuthException authException = new RemoteAuthException();

			authException.setType(RemoteAuthException.WRONG_SHARED_SECRET);

			throw authException;
		}

		User user = UserLocalServiceUtil.fetchUser(GetterUtil.getLong(login));

		if (user == null) {
			long companyId = PortalInstances.getCompanyId(httpServletRequest);

			user = UserLocalServiceUtil.fetchUserByEmailAddress(
				companyId, login);

			if (user == null) {
				user = UserLocalServiceUtil.fetchUserByScreenName(
					companyId, login);
			}
		}

		if (user == null) {
			AuthException authException = new RemoteAuthException(
				"Unable to find user " + login);

			authException.setType(AuthException.INTERNAL_SERVER_ERROR);

			throw authException;
		}

		return user.getUserId();
	}

	@Override
	public void setCredentials(
			String login, HttpURLConnection httpURLConnection)
		throws Exception {

		if (Validator.isBlank(login)) {
			throw new IllegalArgumentException("Login is null");
		}

		HttpAuthorizationHeader httpAuthorizationHeader =
			new HttpAuthorizationHeader(HttpAuthorizationHeader.SCHEME_BASIC);

		String password = Encryptor.encrypt(getSharedSecretKey(), login);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_PASSWORD, password);

		httpAuthorizationHeader.setAuthParameter(
			HttpAuthorizationHeader.AUTH_PARAMETER_NAME_USERNAME, login);

		httpURLConnection.setRequestProperty(
			HttpHeaders.AUTHORIZATION, httpAuthorizationHeader.toString());
	}

	protected Key getSharedSecretKey() throws AuthException {
		String sharedSecret = PropsValues.TUNNELING_SERVLET_SHARED_SECRET;

		if (Validator.isNull(sharedSecret)) {
			String message =
				"Please configure " + PropsKeys.TUNNELING_SERVLET_SHARED_SECRET;

			if (_log.isWarnEnabled()) {
				_log.warn(message);
			}

			AuthException authException = new AuthException(message);

			authException.setType(AuthException.NO_SHARED_SECRET);

			throw authException;
		}

		byte[] key = null;

		if (PropsValues.TUNNELING_SERVLET_SHARED_SECRET_HEX) {
			try {
				key = Hex.decodeHex(sharedSecret.toCharArray());
			}
			catch (DecoderException de) {
				if (_log.isWarnEnabled()) {
					_log.warn(de, de);
				}

				AuthException authException = new AuthException();

				authException.setType(AuthException.INVALID_SHARED_SECRET);

				throw authException;
			}
		}
		else {
			key = sharedSecret.getBytes();
		}

		if (key.length < 8) {
			String message =
				PropsKeys.TUNNELING_SERVLET_SHARED_SECRET + " is too short";

			if (_log.isWarnEnabled()) {
				_log.warn(message);
			}

			AuthException authException = new AuthException(message);

			authException.setType(AuthException.INVALID_SHARED_SECRET);

			throw authException;
		}

		if (StringUtil.equalsIgnoreCase(
				PropsValues.TUNNELING_SERVLET_ENCRYPTION_ALGORITHM, "AES") &&
			(key.length != 16) && (key.length != 32)) {

			String message =
				PropsKeys.TUNNELING_SERVLET_SHARED_SECRET +
					" must have 16 or 32 bytes when used with AES";

			if (_log.isWarnEnabled()) {
				_log.warn(message);
			}

			AuthException authException = new AuthException(message);

			authException.setType(AuthException.INVALID_SHARED_SECRET);

			throw authException;
		}

		return new SecretKeySpec(
			key, PropsValues.TUNNELING_SERVLET_ENCRYPTION_ALGORITHM);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TunnelAuthenticationManagerImpl.class);

}