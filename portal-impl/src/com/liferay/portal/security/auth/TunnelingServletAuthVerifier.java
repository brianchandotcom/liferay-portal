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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.http.TunnelUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zsolt Berentey
 */
@OSGiBeanProperties(
	portalPropertyPrefix = "auth.verifier.TunnelingServletAuthVerifier."
)
public class TunnelingServletAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		return HttpServletRequest.BASIC_AUTH;
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		try {
			String[] credentials = verify(accessControlContext.getRequest());

			if (credentials != null) {
				authVerifierResult.setPassword(credentials[1]);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));
			}
		}
		catch (AuthException ae) {
			if (_log.isDebugEnabled()) {
				_log.debug(ae);
			}

			HttpServletResponse response = accessControlContext.getResponse();

			try (ObjectOutputStream objectOutputStream =
					new ObjectOutputStream(response.getOutputStream())) {

				objectOutputStream.writeObject(ae);

				authVerifierResult.setState(
					AuthVerifierResult.State.INVALID_CREDENTIALS);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				throw ae;
			}
		}

		return authVerifierResult;
	}

	protected String[] verify(HttpServletRequest request) throws AuthException {
		String authorization = request.getHeader("Authorization");

		if (authorization == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(authorization);

		if (!st.hasMoreTokens()) {
			return null;
		}

		String basic = st.nextToken();

		if (!StringUtil.equalsIgnoreCase(
				basic, HttpServletRequest.BASIC_AUTH)) {

			return null;
		}

		String encodedCredentials = st.nextToken();

		if (_log.isDebugEnabled()) {
			_log.debug("Encoded credentials " + encodedCredentials);
		}

		String decodedCredentials = new String(
			Base64.decode(encodedCredentials));

		if (_log.isDebugEnabled()) {
			_log.debug("Decoded credentials " + decodedCredentials);
		}

		int index = decodedCredentials.indexOf(CharPool.COLON);

		if (index == -1) {
			return null;
		}

		String login = GetterUtil.getString(
			decodedCredentials.substring(0, index));
		String password = decodedCredentials.substring(index + 1);

		String expectedPassword = null;

		try {
			expectedPassword = Encryptor.encrypt(
				TunnelUtil.getSharedSecretKey(), login);
		}
		catch (EncryptorException ee) {
			AuthException authException = new RemoteAuthException(ee);

			authException.setType(AuthException.INTERNAL_SERVER_ERROR);

			throw authException;
		}
		catch (AuthException ae) {
			AuthException authException = new RemoteAuthException();

			authException.setType(ae.getType());

			throw authException;
		}

		if (!password.equals(expectedPassword)) {
			AuthException authException = new RemoteAuthException();

			authException.setType(RemoteAuthException.WRONG_SHARED_SECRET);

			throw authException;
		}

		User user = null;

		try {
			user = UserLocalServiceUtil.fetchUser(GetterUtil.getLong(login));

			if (user == null) {
				Company company = PortalUtil.getCompany(request);

				user = UserLocalServiceUtil.fetchUserByEmailAddress(
					company.getCompanyId(), login);

				if (user == null) {
					user = UserLocalServiceUtil.fetchUserByScreenName(
						company.getCompanyId(), login);
				}
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find company", pe);
			}
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find user", se);
			}
		}

		if (user == null) {
			AuthException authException = new RemoteAuthException();

			authException.setType(AuthException.INTERNAL_SERVER_ERROR);

			throw authException;
		}

		String[] credentials = new String[2];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = password;

		return credentials;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TunnelingServletAuthVerifier.class);

}