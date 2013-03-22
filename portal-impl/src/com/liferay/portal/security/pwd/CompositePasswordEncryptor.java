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
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class CompositePasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

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
			String[] supportedAlgorithmTypes =
				passwordEncryptor.getSupportedAlgorithmTypes();

			for (String supportedAlgorithmType : supportedAlgorithmTypes) {
				_passwordEncryptors.put(
					supportedAlgorithmType, passwordEncryptor);
			}
		}
	}

	@Override
	protected String doEncrypt(
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(algorithm)) {
			throw new IllegalArgumentException(
				"Must specify an encryption algorithm.");
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
			passwordEncryptor = _defaultPasswordEncryptor;
		}

		return passwordEncryptor.encrypt(
			algorithm, clearTextPassword, currentEncryptedPassword);
	}

	private PasswordEncryptor _defaultPasswordEncryptor;
	private Map<String, PasswordEncryptor> _passwordEncryptors =
		new HashMap<String, PasswordEncryptor>();

}