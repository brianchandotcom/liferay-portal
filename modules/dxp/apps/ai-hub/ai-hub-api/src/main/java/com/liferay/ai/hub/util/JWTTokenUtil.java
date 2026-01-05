/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

/**
 * @author Rafael Praxedes
 */
public class JWTTokenUtil {

	public static String generateToken(
		long companyId, long expirationTime, String issuer, long userId) {

		Date now = new Date();

		SignedJWT signedJWT = new SignedJWT(
			new JWSHeader(JWSAlgorithm.HS256),
			new JWTClaimsSet.Builder(
			).claim(
				"companyId", companyId
			).expirationTime(
				new Date(now.getTime() + expirationTime)
			).issuer(
				issuer
			).issueTime(
				now
			).subject(
				String.valueOf(userId)
			).build());

		try {
			signedJWT.sign(new MACSigner(_SECRET));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new SystemException("Unable to generate a signed token");
		}

		return signedJWT.serialize();
	}

	public static long getUserId(String token) {
		JWTClaimsSet jwtClaimsSet = null;

		try {
			SignedJWT signedJWT = SignedJWT.parse(token);

			if (!signedJWT.verify(new MACVerifier(_SECRET))) {
				throw new SystemException("Invalid JWT signature");
			}

			jwtClaimsSet = signedJWT.getJWTClaimsSet();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (exception instanceof SystemException systemException) {
				throw systemException;
			}

			throw new SystemException(
				"Unable to parse and verify the JWT token", exception);
		}

		Date expirationTime = jwtClaimsSet.getExpirationTime();

		if ((expirationTime == null) || expirationTime.before(new Date())) {
			throw new SystemException("The JWT token has been expired");
		}

		return GetterUtil.getLong(jwtClaimsSet.getSubject());
	}

	private static final byte[] _SECRET;

	private static final Log _log = LogFactoryUtil.getLog(JWTTokenUtil.class);

	static {
		int sha256BlockSize = 64;

		byte[] secret = new byte[sha256BlockSize];

		for (int i = 0; i < secret.length; i++) {
			secret[i] = SecureRandomUtil.nextByte();
		}

		_SECRET = secret;
	}

}