/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.security;

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
		long expirationTime, String issuer, long userId) {

		Date now = new Date();

		SignedJWT signedJWT = new SignedJWT(
			new JWSHeader(JWSAlgorithm.HS256),
			new JWTClaimsSet.Builder(
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
				_log.debug("Unable to generate a signed token", exception);
			}

			return null;
		}

		return signedJWT.serialize();
	}

	public static long getUserId(String token) {
		JWTClaimsSet jwtClaimsSet = null;

		try {
			SignedJWT signedJWT = SignedJWT.parse(token);

			if (!signedJWT.verify(new MACVerifier(_SECRET))) {
				if (_log.isDebugEnabled()) {
					_log.debug("Invalid JWT signature");
				}

				return 0;
			}

			jwtClaimsSet = signedJWT.getJWTClaimsSet();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse and verify the JWT token", exception);
			}

			return 0;
		}

		Date expirationDate = jwtClaimsSet.getExpirationTime();

		if ((expirationDate == null) || expirationDate.before(new Date())) {
			if (_log.isDebugEnabled()) {
				_log.debug("The JWT token is expired");
			}

			return 0;
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