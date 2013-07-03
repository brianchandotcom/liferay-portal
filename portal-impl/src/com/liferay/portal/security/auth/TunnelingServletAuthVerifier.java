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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.http.TunnelUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.security.InvalidKeyException;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zsolt Berentey
 */
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

		String[] credentials = doVerify(accessControlContext.getRequest());

		if (credentials != null) {
			authVerifierResult.setPassword(credentials[1]);
			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(Long.valueOf(credentials[0]));
		}

		return authVerifierResult;
	}

	protected String[] doVerify(HttpServletRequest request)
		throws AuthException {

		// Get the Authorization header, if one was supplied

		String authorization = request.getHeader("Authorization");

		if (authorization == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(authorization);

		if (!st.hasMoreTokens()) {
			return null;
		}

		String basic = st.nextToken();

		// We only handle HTTP Basic authentication

		if (!basic.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
			return null;
		}

		String encodedCredentials = st.nextToken();

		if (_log.isDebugEnabled()) {
			_log.debug("Encoded credentials are " + encodedCredentials);
		}

		String decodedCredentials = new String(
			Base64.decode(encodedCredentials));

		if (_log.isDebugEnabled()) {
			_log.debug("Decoded credentials are " + decodedCredentials);
		}

		int pos = decodedCredentials.indexOf(CharPool.COLON);

		if (pos == -1) {
			return null;
		}

		String login = GetterUtil.getString(
			decodedCredentials.substring(0, pos));
		String password = decodedCredentials.substring(pos + 1);

		String expectedPassword = null;

		try {
			expectedPassword = Encryptor.encrypt(
				TunnelUtil.getPresharedKey(), login);
		}
		catch (EncryptorException e) {
			throw new AuthException("Unable to decrypt login.", e);
		}
		catch (InvalidKeyException e) {
			throw new AuthException(e);
		}

		if (!password.equals(expectedPassword)) {
			throw new AuthException(
				"Tunneling servlet preshared keys do not match. " +
					"Please check your configurations");
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
			_log.error("Unable to fetch company", pe);
		}
		catch (SystemException se) {
			_log.error("Unable to fetch userId", se);
		}

		if (user == null) {
			throw new AuthException();
		}

		String[] credentials = new String[2];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = password;

		return credentials;
	}

	private static Log _log = LogFactoryUtil.getLog(
		TunnelingServletAuthVerifier.class);

}