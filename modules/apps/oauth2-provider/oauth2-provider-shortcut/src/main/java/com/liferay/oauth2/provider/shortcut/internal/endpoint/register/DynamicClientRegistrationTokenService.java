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

package com.liferay.oauth2.provider.shortcut.internal.endpoint.register;

import com.google.gson.JsonPrimitive;

import com.liferay.portal.kernel.util.PwdGenerator;

import java.security.SignatureException;

import java.util.Collections;

import net.oauth.jsontoken.Checker;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Signer;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProviders;

import org.joda.time.Instant;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true, service = DynamicClientRegistrationTokenService.class
)
public class DynamicClientRegistrationTokenService {

	public RegistrationToken parse(String jwt) throws Exception {
		JsonTokenParser jsonTokenParser = getJsonTokenParser();

		JsonToken jsonToken = jsonTokenParser.verifyAndDeserialize(jwt);

		Instant now = new Instant();

		if (!jsonTokenParser.expirationIsValid(jsonToken, now)) {
			throw new SecurityException("Token already expired");
		}

		if (!jsonTokenParser.issuedAtIsValid(jsonToken, now)) {
			throw new SecurityException("Invalid issued_at token parameter");
		}

		RegistrationToken registrationToken = new RegistrationToken();

		JsonPrimitive sub = jsonToken.getParamAsPrimitive("sub");

		registrationToken.setUserId(sub.getAsLong());

		return registrationToken;
	}

	public String toJWT(RegistrationToken registrationToken)
		throws SignatureException {

		JsonToken jsonToken = new JsonToken(getSigner());

		Instant now = new Instant();

		jsonToken.setExpiration(now.plus(_EXPIRATION));
		jsonToken.setIssuedAt(now);

		jsonToken.setParam("sub", registrationToken.getUserId());

		return jsonToken.serializeAndSign();
	}

	public static class RegistrationToken {

		public long getUserId() {
			return _userId;
		}

		public void setUserId(long userId) {
			_userId = userId;
		}

		private long _userId;

	}

	protected JsonTokenParser getJsonTokenParser() throws Exception {
		if (_jsonTokenParser != null) {
			return _jsonTokenParser;
		}

		Verifier verifier = new HmacSHA256Verifier(_SECRET.getBytes());

		VerifierProviders verifyProviders = new VerifierProviders();

		verifyProviders.setVerifierProvider(
			SignatureAlgorithm.HS256,
			(signerId, keyId) -> Collections.singletonList(verifier));

		Checker checker = __ -> {
		};

		_jsonTokenParser = new JsonTokenParser(verifyProviders, checker);

		return _jsonTokenParser;
	}

	protected Signer getSigner() {
		if (_signer != null) {
			return _signer;
		}

		try {
			_signer = new HmacSHA256Signer(null, null, _SECRET.getBytes());

			return _signer;
		}
		catch (Exception e) {
			return null;
		}
	}

	private static final long _EXPIRATION = 1800000;

	private static final String _SECRET = PwdGenerator.getPassword(32);

	private static JsonTokenParser _jsonTokenParser;
	private static Signer _signer;

}