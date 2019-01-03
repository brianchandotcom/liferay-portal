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

package com.liferay.document.library.document.conversion.internal;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.Date;
import java.util.List;

import net.oauth.jsontoken.Checker;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Signer;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

import org.joda.time.Instant;

/**
 * @author Daniel Sanz
 * @author István András Dézsi
 * @author Tomas Polesovsky
 */
public class ImageRequestTokenUtil {

	public static String createToken(long userId) {
		Signer signer = null;

		try {
			signer = new HmacSHA256Signer(null, null, _SECRET);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to create signer", e);
			}

			return null;
		}

		JsonToken jsonToken = new JsonToken(signer);

		Instant instant = new Instant();

		jsonToken.setExpiration(instant.plus(_EXPIRATION));
		jsonToken.setIssuedAt(instant);

		JsonObject payloadJsonObject = jsonToken.getPayloadAsJsonObject();

		payloadJsonObject.addProperty("userId", userId);

		try {
			return jsonToken.serializeAndSign();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to sign payload", e);
			}

			return null;
		}
	}

	public static long getUserId(String tokenString) {
		try {
			JsonTokenParser jsonTokenParser = getJsonTokenParser();

			JsonToken jsonToken = jsonTokenParser.verifyAndDeserialize(
				tokenString);

			JsonPrimitive userIdJsonPrimitive = jsonToken.getParamAsPrimitive(
				"userId");

			if (userIdJsonPrimitive == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find \"userId\" parameter in payload " +
							tokenString);
				}

				return 0;
			}

			long userId = userIdJsonPrimitive.getAsLong();

			User user = UserLocalServiceUtil.fetchUser(userId);

			Date passwordModifiedDate = user.getPasswordModifiedDate();

			if (passwordModifiedDate != null) {
				Instant instant = jsonToken.getIssuedAt();

				if (instant.isBefore(passwordModifiedDate.getTime())) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to accept token because the password was " +
								"changed");
					}

					return 0;
				}
			}

			return userId;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse and verify token " + tokenString, e);
			}

			return 0;
		}
	}

	protected static JsonTokenParser getJsonTokenParser() throws Exception {
		final Verifier verifier = new HmacSHA256Verifier(_SECRET);

		VerifierProvider verifierProvider = new VerifierProvider() {

			@Override
			public List<Verifier> findVerifier(String signerId, String keyId) {
				return Lists.newArrayList(verifier);
			}

		};

		VerifierProviders verifyProviders = new VerifierProviders();

		verifyProviders.setVerifierProvider(
			SignatureAlgorithm.HS256, verifierProvider);

		Checker checker = new Checker() {

			@Override
			public void check(JsonObject jsonObject) {
			}

		};

		return new JsonTokenParser(verifyProviders, checker);
	}

	private static final long _EXPIRATION = 10 * 60 * 1000;

	private static final byte[] _SECRET;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageRequestTokenUtil.class);

	static {
		int sha256BlockSize = 64;

		byte[] secret = new byte[sha256BlockSize];

		for (int i = 0; i < secret.length; i++) {
			secret[i] = SecureRandomUtil.nextByte();
		}

		_SECRET = secret;
	}

}