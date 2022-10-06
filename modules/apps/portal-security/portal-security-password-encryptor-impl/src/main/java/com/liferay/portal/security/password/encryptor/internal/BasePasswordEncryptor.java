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

package com.liferay.portal.security.password.encryptor.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Michael C. Han
 */
public abstract class BasePasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(String plainTextPassword, String encryptedPassword)
		throws PwdEncryptorException {

		return encrypt(
			getDefaultPasswordAlgorithmType(), plainTextPassword,
			encryptedPassword);
	}

	@Override
	public String getDefaultPasswordAlgorithmType() {
		return _PASSWORDS_ENCRYPTION_ALGORITHM;
	}

	@Override
	public String getPasswordAlgorithmType(String encryptedPassword) {
		String legacyAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		if (_log.isDebugEnabled() && Validator.isNotNull(legacyAlgorithm)) {
			if (Validator.isNull(encryptedPassword)) {
				_log.debug(
					StringBundler.concat(
						"Using legacy detection scheme for algorithm ",
						legacyAlgorithm, " with empty current password"));
			}
			else {
				_log.debug(
					StringBundler.concat(
						"Using legacy detection scheme for algorithm ",
						legacyAlgorithm, " with provided current password"));
			}
		}

		if (Validator.isNotNull(encryptedPassword) &&
			(encryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Using legacy algorithm " + legacyAlgorithm);
			}

			if (Validator.isNotNull(legacyAlgorithm)) {
				return legacyAlgorithm;
			}

			return getDefaultPasswordAlgorithmType();
		}
		else if (Validator.isNotNull(encryptedPassword) &&
				 (encryptedPassword.charAt(0) == CharPool.OPEN_CURLY_BRACE)) {

			int index = encryptedPassword.indexOf(CharPool.CLOSE_CURLY_BRACE);

			if (index > 0) {
				String algorithm = encryptedPassword.substring(1, index);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Upgraded password to use algorithm " + algorithm);
				}

				return algorithm;
			}
		}

		return null;
	}

	private static final String _PASSWORDS_ENCRYPTION_ALGORITHM =
		StringUtil.toUpperCase(
			GetterUtil.getString(
				PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)));

	private static final Log _log = LogFactoryUtil.getLog(
		BasePasswordEncryptor.class);

}