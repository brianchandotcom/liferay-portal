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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.util.BCrypt;

/**
 * @author Michael C. Han
 * @author Tomas Polesovsky
 */
public class BCryptPasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	public String[] getSupportedAlgorithmTypes() {
		return new String[] { PasswordEncryptorUtil.TYPE_BCRYPT };
	}

	@Override
	protected String doEncrypt(
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		String salt = null;

		if (Validator.isNull(currentEncryptedPassword)) {
			int rounds = _DEFAULT_BCRYPT_ROUNDS;

			Matcher algorithmRoundsMatcher = _BCRYPT_PATTERN.matcher(algorithm);

			if (algorithmRoundsMatcher.matches()) {
				rounds = GetterUtil.getInteger(
					algorithmRoundsMatcher.group(1), rounds);
			}

			salt = BCrypt.gensalt(rounds);
		}
		else {
			salt = currentEncryptedPassword.substring(0, 29);
		}

		return BCrypt.hashpw(clearTextPassword, salt);
	}

	private static final Pattern _BCRYPT_PATTERN = Pattern.compile(
		"^BCrypt/([0-9]+)$", Pattern.CASE_INSENSITIVE);

	private static final int _DEFAULT_BCRYPT_ROUNDS = 10;

}