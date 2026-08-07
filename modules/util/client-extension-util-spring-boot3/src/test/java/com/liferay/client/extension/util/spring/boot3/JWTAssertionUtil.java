/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot3;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Scanner;

import org.apache.cxf.rs.security.jose.common.JoseConstants;
import org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm;
import org.apache.cxf.rs.security.jose.jwk.JsonWebKeys;
import org.apache.cxf.rs.security.jose.jwk.JwkUtils;
import org.apache.cxf.rs.security.jose.jws.JwsHeaders;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactProducer;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

/**
 * @author Arthur Chan
 * @author Gregory Amerson
 */
public class JWTAssertionUtil {

	public static final String JWKS = _readJWKS();

	public static String getJWTWithClientId(String clientId)
		throws URISyntaxException {

		JwsHeaders jwsHeaders = new JwsHeaders(SignatureAlgorithm.RS256);

		jwsHeaders.setHeader(JoseConstants.HEADER_KEY_ID, _KEY_ID);
		jwsHeaders.setHeader(JoseConstants.HEADER_TYPE, "at+jwt");
		jwsHeaders.setKeyId(_KEY_ID);

		JwtClaims jwtClaims = _getJWTClaims(
			new URI("localhost"), "localhost", "none");

		jwtClaims.setProperty("client_id", clientId);

		JwsJwtCompactProducer jwsJwtCompactProducer = new JwsJwtCompactProducer(
			new JwtToken(jwsHeaders, jwtClaims));

		JsonWebKeys jsonWebKeys = JwkUtils.readJwkSet(JWKS);

		return jwsJwtCompactProducer.signWith(
			jsonWebKeys.getKey(jwsHeaders.getKeyId()));
	}

	private static JwtClaims _getJWTClaims(
		URI audienceURI, String issuer, String subject) {

		JwtClaims jwtClaims = new JwtClaims();

		jwtClaims.setAudience(audienceURI.toString());
		jwtClaims.setIssuedAt(OAuthUtils.getIssuedAt());
		jwtClaims.setExpiryTime(jwtClaims.getIssuedAt() + 3600L);
		jwtClaims.setIssuer(issuer);
		jwtClaims.setSubject(subject);

		return jwtClaims;
	}

	private static String _readJWKS() {
		try (Scanner scanner = new Scanner(
				JWTAssertionUtil.class.getResourceAsStream(
					"dependencies/jwks.json"),
				"UTF-8")) {

			scanner.useDelimiter("\\A");

			return scanner.next();
		}
	}

	private JWTAssertionUtil() {
	}

	private static final String _KEY_ID = "_createTestRSAKeyPairJSONWebKey01";

}