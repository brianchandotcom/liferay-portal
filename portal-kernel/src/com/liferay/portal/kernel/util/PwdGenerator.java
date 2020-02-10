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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 * @author Shuyang Zhou
 */
public class PwdGenerator {

	public static final String KEY1 = "0123456789";

	public static final String KEY2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String KEY3 = "abcdefghijklmnopqrstuvwxyz";

	public static String getPassword() {
		return getPassword(8, _KEYS);
	}

	public static String getPassword(int length) {
		return getPassword(length, _KEYS);
	}

	public static String getPassword(int length, String... keys) {
		if (keys == null) {
			throw new IllegalArgumentException("Keys are null");
		}

		StringBundler fullKeySB = new StringBundler(keys);

		String fullKey = fullKeySB.toString();

		int fullKeyLength = fullKey.length();

		int refreshPeriod = (int)(_MULTIPLIER / Math.log(fullKeyLength));

		long secureLong = 0;

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			if ((i % refreshPeriod) == 0) {
				secureLong = SecureRandomUtil.nextLong();
			}

			int pos = Math.abs((int)(secureLong % fullKeyLength));

			secureLong = secureLong / fullKeyLength;

			sb.append(fullKey.charAt(pos));
		}

		return sb.toString();
	}

	public static String getPassword(String key, int length) {
		int keysCount = 0;

		if (key.contains(KEY1)) {
			keysCount++;
		}

		if (key.contains(KEY2)) {
			keysCount++;
		}

		if (key.contains(KEY3)) {
			keysCount++;
		}

		if (keysCount > length) {
			if (_log.isWarnEnabled()) {
				_log.warn("Length is too short");
			}

			length = keysCount;
		}

		while (true) {
			String password = getPassword(length, key);

			if (key.contains(KEY1) &&
				Validator.isNull(StringUtil.extractDigits(password))) {

				continue;
			}

			if (key.contains(KEY2) &&
				password.equals(StringUtil.toLowerCase(password))) {

				continue;
			}

			if (key.contains(KEY3) &&
				password.equals(StringUtil.toUpperCase(password))) {

				continue;
			}

			return password;
		}
	}

	public static String getPassword(
		String key, int length, boolean useAllKeys) {

		if (useAllKeys) {
			return getPassword(key, length);
		}

		return getPassword(length, key);
	}

	public static String getPinNumber() {
		return getPassword(4, KEY1);
	}

	private static final String[] _KEYS = {KEY1, KEY2, KEY3};

	private static final double _MULTIPLIER = Long.SIZE * Math.log(2);

	private static final Log _log = LogFactoryUtil.getLog(PwdGenerator.class);

}