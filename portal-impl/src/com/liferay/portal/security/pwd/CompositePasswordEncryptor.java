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

package com.liferay.portal.security.pwd;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class CompositePasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(plainTextPassword)) {
			throw new PwdEncryptorException("Unable to encrypt blank password");
		}

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

		boolean prependAlgorithm = true;

		if (Validator.isNotNull(encryptedPassword) &&
			(encryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

			algorithm = legacyAlgorithm;

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

		if (Validator.isNull(algorithm)) {
			algorithm = getDefaultPasswordAlgorithmType();
		}

		PasswordEncryptor passwordEncryptor = _select(algorithm);

		String newEncryptedPassword = passwordEncryptor.encrypt(
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

		return StringBundler.concat(
			StringPool.OPEN_CURLY_BRACE, _getAlgorithmName(algorithm),
			StringPool.CLOSE_CURLY_BRACE, newEncryptedPassword);
	}

	@Override
	public String getAlgorithmType() {
		throw new UnsupportedOperationException();
	}

	public void setDefaultPasswordEncryptor(
		PasswordEncryptor defaultPasswordEncryptor) {

		_defaultPasswordEncryptor = defaultPasswordEncryptor;
	}

	public void setPasswordEncryptors(
		List<PasswordEncryptor> passwordEncryptors) {

		for (PasswordEncryptor passwordEncryptor : passwordEncryptors) {
			if (_log.isDebugEnabled()) {
				_log.debug("Registering " + passwordEncryptor);
			}

			_passwordEncryptors.put(
				passwordEncryptor.getAlgorithmType(), passwordEncryptor);
		}
	}

	private String _getAlgorithmName(String algorithm) {
		int index = algorithm.indexOf(CharPool.SLASH);

		if (index > 0) {
			return algorithm.substring(0, index);
		}

		return algorithm;
	}

	private PasswordEncryptor _select(String algorithm) {
		if (Validator.isNull(algorithm)) {
			throw new IllegalArgumentException("Invalid algorithm");
		}

		PasswordEncryptor passwordEncryptor = null;

		if (algorithm.startsWith(PasswordEncryptorUtil.TYPE_BCRYPT)) {
			passwordEncryptor = _passwordEncryptors.get(
				PasswordEncryptorUtil.TYPE_BCRYPT);
		}
		else if (algorithm.startsWith(PasswordEncryptorUtil.TYPE_PBKDF2)) {
			passwordEncryptor = _passwordEncryptors.get(
				PasswordEncryptorUtil.TYPE_PBKDF2);
		}
		else {
			passwordEncryptor = _passwordEncryptors.get(algorithm);
		}

		if (passwordEncryptor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No password encryptor found for " + algorithm);
			}

			passwordEncryptor = _defaultPasswordEncryptor;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Found ", ClassUtil.getClassName(passwordEncryptor),
					" to encrypt password using ", algorithm));
		}

		return passwordEncryptor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompositePasswordEncryptor.class);

	private PasswordEncryptor _defaultPasswordEncryptor;
	private final Map<String, PasswordEncryptor> _passwordEncryptors =
		new HashMap<>();

}