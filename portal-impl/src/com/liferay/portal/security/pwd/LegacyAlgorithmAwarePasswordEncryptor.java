/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pwd;

import com.liferay.portal.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

/**
 * @author Tomas Polesovsky
 */
public class LegacyAlgorithmAwarePasswordEncryptor
	extends BasePasswordEncryptor {

	public String[] getSupportedAlgorithmTypes() {
		return _parentPasswordEncryptor.getSupportedAlgorithmTypes();
	}

	public void setParentPasswordEncryptor(
		PasswordEncryptor defaultPasswordEncryptor) {

		_parentPasswordEncryptor = defaultPasswordEncryptor;
	}

	@Override
	protected String doEncrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(
				PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping passwords upgrade scheme because " +
						PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY +
							" is blank");
			}

			try {
				return _parentPasswordEncryptor.encrypt(
					algorithm, plainTextPassword, encryptedPassword);
			}
			catch (Exception e) {
				StringBundler sb = new StringBundler(5);

				sb.append("Password upgrade was not successfully configured. ");
				sb.append("Please set the property ");
				sb.append("\"passwords.encryption.algorithm.legacy\" with ");
				sb.append("the previous password encryption algorithm and ");
				sb.append("restart.");

				throw new PwdEncryptorException(sb.toString(), e);
			}
		}

		if (_log.isDebugEnabled()) {
			String message =
				"Using legacy detection scheme for algorithm " + algorithm +
					" with current password ";

			if (Validator.isNull(encryptedPassword)) {
				message += "empty";
			}
			else {
				message += "provided";
			}

			_log.debug(message);
		}

		boolean prependAlgorithm = true;

		if (Validator.isNotNull(encryptedPassword) &&
			(encryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

			algorithm = PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

			prependAlgorithm = false;

			if (_log.isDebugEnabled()) {
				_log.debug("Using legacy algorithm " + algorithm);
			}
		}
		else if (Validator.isNotNull(encryptedPassword) &&
				 (encryptedPassword.charAt(0) == CharPool.OPEN_CURLY_BRACE)) {

			int index = encryptedPassword.indexOf(CharPool.CLOSE_CURLY_BRACE);

			if (index > 0) {
				algorithm = encryptedPassword.substring(1, index);

				encryptedPassword = encryptedPassword.substring(index + 1);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Upgraded password to use algorithm " + algorithm);
			}
		}

		String newEncryptedPassword = _parentPasswordEncryptor.encrypt(
			algorithm, plainTextPassword, encryptedPassword);

		if (!prependAlgorithm) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Generated password without algorithm prefix using " +
						algorithm);
			}

			return newEncryptedPassword;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Generated password with algorithm prefix using " + algorithm);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append(getAlgorithmName(algorithm));
		sb.append(StringPool.CLOSE_CURLY_BRACE);
		sb.append(newEncryptedPassword);

		return sb.toString();
	}

	protected String getAlgorithmName(String algorithm) {
		int index = algorithm.indexOf(CharPool.SLASH);

		if (index > 0) {
			return algorithm.substring(0, index);
		}

		return algorithm;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LegacyAlgorithmAwarePasswordEncryptor.class);

	private PasswordEncryptor _parentPasswordEncryptor;

}