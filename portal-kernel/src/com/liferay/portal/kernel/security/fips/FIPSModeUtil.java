/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.Key;

import java.util.Set;

/**
 * @author Lucas Miranda
 */
public class FIPSModeUtil {

	public static boolean isNotAllowedAlgorithm(String algorithm) {
		if (!PropsValues.FIPS_ENABLED) {
			return false;
		}

		if (Validator.isNull(algorithm)) {
			return true;
		}

		for (String allowedAlgorithm : _allowedAlgorithms) {
			if (algorithm.startsWith(allowedAlgorithm)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isNotAllowedKeyAlgorithm(String algorithm) {
		if (!PropsValues.FIPS_ENABLED) {
			return false;
		}

		return !StringUtil.equalsIgnoreCase("AES", algorithm);
	}

	public static void validateKey(Key key) {
		if (!PropsValues.FIPS_ENABLED) {
			return;
		}

		if (isNotAllowedKeyAlgorithm(key.getAlgorithm())) {
			throw new SecurityException(
				"Algorithm \"" + key.getAlgorithm() +
					"\" is not allowed in FIPS mode");
		}

		byte[] encodedKey = key.getEncoded();

		if ((encodedKey == null) ||
			!_allowedKeySizes.contains(encodedKey.length * 8)) {

			throw new SecurityException(
				"AES key must be 128, 192, or 256 bits");
		}
	}

	private static final Set<String> _allowedAlgorithms = Set.of(
		"PBKDF2WithHmacSHA256", "PBKDF2WithHmacSHA384", "PBKDF2WithHmacSHA512",
		"SHA-256", "SHA-384", "SHA-512");
	private static final Set<Integer> _allowedKeySizes = Set.of(128, 192, 256);

}