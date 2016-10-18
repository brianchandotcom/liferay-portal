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

package com.liferay.sync.engine.lan.util;

import java.nio.charset.Charset;

import java.security.SecureRandom;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Dennis Ju
 */
public class LanTokenUtil {

	public static boolean containsLanToken(String lanToken) {
		return _lanTokens.contains(lanToken);
	}

	public static String createEncryptedToken(String lanTokenKey)
		throws Exception {

		String lanToken = RandomStringUtils.random(
			32, 0, 0, true, true, null, _secureRandom);

		byte[] bytes = DigestUtils.sha1(lanTokenKey);

		bytes = Arrays.copyOf(bytes, 16);

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(bytes, "AES"));

		String encryptedToken = Base64.encodeBase64String(
			cipher.doFinal(lanToken.getBytes(Charset.forName("UTF-8"))));

		_lanTokens.add(lanToken);

		return encryptedToken;
	}

	public static String decryptLanToken(
			String lanTokenKey, String encryptedToken)
		throws Exception {

		byte[] bytes = DigestUtils.sha1(lanTokenKey);

		bytes = Arrays.copyOf(bytes, 16);

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(bytes, "AES"));

		return new String(
			cipher.doFinal(Base64.decodeBase64(encryptedToken)),
			Charset.forName("UTF-8"));
	}

	public static boolean removeLanToken(String lanToken) {
		return _lanTokens.remove(lanToken);
	}

	private static final Set<String> _lanTokens = Collections.newSetFromMap(
		new PassiveExpiringMap(
			3600, TimeUnit.SECONDS, new ConcurrentHashMap()));
	private static final SecureRandom _secureRandom = new SecureRandom();

}