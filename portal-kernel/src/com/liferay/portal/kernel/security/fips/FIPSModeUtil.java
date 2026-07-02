/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;

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

	private static final Set<String> _allowedAlgorithms = Set.of(
		"AES", "AES/GCM/NoPadding", "PBKDF2WithHmacSHA256",
		"PBKDF2WithHmacSHA384", "PBKDF2WithHmacSHA512", "SHA-256", "SHA-384",
		"SHA-512");

}