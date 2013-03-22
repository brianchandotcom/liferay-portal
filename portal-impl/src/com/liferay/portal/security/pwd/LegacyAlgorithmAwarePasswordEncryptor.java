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
import com.liferay.portal.kernel.util.CharPool;
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
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(
				PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY)) {

			return _parentPasswordEncryptor.encrypt(
				algorithm, clearTextPassword, currentEncryptedPassword);
		}

		boolean prependAlgorithm = true;

		if (Validator.isNotNull(currentEncryptedPassword) &&
			(currentEncryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

			algorithm = PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

			prependAlgorithm = false;
		}
		else if (Validator.isNotNull(currentEncryptedPassword) &&
			(currentEncryptedPassword.charAt(0) == CharPool.OPEN_CURLY_BRACE)) {

			int endPos = currentEncryptedPassword.indexOf(
				CharPool.CLOSE_CURLY_BRACE);

			if (endPos > 0) {
				algorithm = currentEncryptedPassword.substring(1, endPos);

				currentEncryptedPassword = currentEncryptedPassword.substring(
					endPos + 1);
			}
		}

		String encryptedPassword = _parentPasswordEncryptor.encrypt(
			algorithm, clearTextPassword, currentEncryptedPassword);

		if (!prependAlgorithm) {
			return encryptedPassword;
		}

		StringBuilder result = new StringBuilder(4);

		result.append(StringPool.OPEN_CURLY_BRACE);

		result.append(getAlgorithmName(algorithm));

		result.append(StringPool.CLOSE_CURLY_BRACE);

		result.append(encryptedPassword);

		return result.toString();
	}

	protected String getAlgorithmName(String algorithm) {
		int slashIndex = algorithm.indexOf(CharPool.SLASH);

		if (slashIndex > 0) {
			return algorithm.substring(0, slashIndex);
		}

		return algorithm;
	}

	private PasswordEncryptor _parentPasswordEncryptor;

}