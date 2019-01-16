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

package com.liferay.oauth2.provider.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.security.SecureRandomUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2SecureRandomGenerator {

	public static String generateClientId() {
		Matcher matcher = _baseIdPattern.matcher(generateSecureRandomString());

		return matcher.replaceFirst("id-$1-$2-$3-$4-$5");
	}

	public static String generateRandomSecret() {
		Matcher matcher = _baseIdPattern.matcher(generateSecureRandomString());

		return matcher.replaceFirst("secret-$1-$2-$3-$4-$5");
	}

	protected static String generateSecureRandomString() {
		int size = 16;

		int count = (int)Math.ceil((double)size / 8);

		byte[] buffer = new byte[count * 8];

		for (int i = 0; i < count; i++) {
			BigEndianCodec.putLong(buffer, i * 8, SecureRandomUtil.nextLong());
		}

		StringBundler sb = new StringBundler(size);

		for (int i = 0; i < size; i++) {
			sb.append(Integer.toHexString(0xFF & buffer[i]));
		}

		return sb.toString();
	}

	private static final Pattern _baseIdPattern = Pattern.compile(
		"(.{8})(.{4})(.{4})(.{4})(.*)");

}