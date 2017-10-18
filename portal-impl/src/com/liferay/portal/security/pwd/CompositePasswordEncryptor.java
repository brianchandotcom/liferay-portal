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

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class CompositePasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	public void afterPropertiesSet() {
		if (_defaultAlgorithmPasswordEncryptor == null) {
			_defaultAlgorithmPasswordEncryptor = _select(
				getDefaultPasswordAlgorithmType());
		}
	}

	@Override
	public String encrypt(String plainTextPassword, String encryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(plainTextPassword)) {
			throw new PwdEncryptorException("Unable to encrypt blank password");
		}

		return _defaultAlgorithmPasswordEncryptor.encrypt(
			getDefaultPasswordAlgorithmType(), plainTextPassword,
			encryptedPassword);
	}

	@Override
	public String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(plainTextPassword)) {
			throw new PwdEncryptorException("Unable to encrypt blank password");
		}

		PasswordEncryptor passwordEncryptor = _select(algorithm);

		return passwordEncryptor.encrypt(
			algorithm, plainTextPassword, encryptedPassword);
	}

	@Override
	public String[] getSupportedAlgorithmTypes() {
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

			String[] supportedAlgorithmTypes =
				passwordEncryptor.getSupportedAlgorithmTypes();

			if (_log.isDebugEnabled()) {
				Class<?> clazz = passwordEncryptor.getClass();

				_log.debug(
					StringBundler.concat(
						"Registering ",
						StringUtil.merge(supportedAlgorithmTypes), " for ",
						clazz.getName()));
			}

			for (String supportedAlgorithmType : supportedAlgorithmTypes) {
				_passwordEncryptors.put(
					supportedAlgorithmType, passwordEncryptor);
			}
		}
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

	private PasswordEncryptor _defaultAlgorithmPasswordEncryptor;
	private PasswordEncryptor _defaultPasswordEncryptor;
	private final Map<String, PasswordEncryptor> _passwordEncryptors =
		new HashMap<>();

}