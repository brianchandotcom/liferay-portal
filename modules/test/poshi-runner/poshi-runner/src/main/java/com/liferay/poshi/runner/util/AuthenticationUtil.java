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

package com.liferay.poshi.runner.util;

import java.math.BigInteger;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import jodd.util.Base32;

/**
 * @author Della Wang
 */
public class AuthenticationUtil {

	public static String generateTimeBasedOTP(String secretKey) {
		long time = (System.currentTimeMillis() - 3000) / 30000;

		String timeCountHex = StringUtil.toUpperCase(Long.toHexString(time));

		if (timeCountHex.length() > 16) {
			return timeCountHex;
		}

		timeCountHex = StringUtil.replace(
			String.format("%16s", timeCountHex), CharPool.SPACE,
			CharPool.NUMBER_0);

		try {
			Mac mac = Mac.getInstance(_HMAC_SHA1_ALGORITHM);

			mac.init(new SecretKeySpec(Base32.decode(secretKey), "RAW"));

			BigInteger bigInteger = new BigInteger("10" + timeCountHex, 16);

			byte[] byteArray = bigInteger.toByteArray();

			byte[] hash = mac.doFinal(
				Arrays.copyOfRange(byteArray, 1, byteArray.length));

			int offset = hash[hash.length - 1] & 0xf;

			int binary =
				((hash[offset] & 0x7f) << 24) |
				((hash[offset + 1] & 0xff) << 16) |
				((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

			int timeBasedOTP =
				binary % (int)Math.pow(10, _TIME_BASED_OTP_DIGITS);

			return String.format(
				"%0" + _TIME_BASED_OTP_DIGITS + "d", timeBasedOTP);
		}
		catch (InvalidKeyException invalidKeyException) {
			throw new IllegalArgumentException(
				"Invalid secret key for algorithm " + _HMAC_SHA1_ALGORITHM,
				invalidKeyException);
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new IllegalArgumentException(
				"Invalid algorithm " + _HMAC_SHA1_ALGORITHM,
				noSuchAlgorithmException);
		}
	}

	private static final String _HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private static final int _TIME_BASED_OTP_DIGITS = 6;

}