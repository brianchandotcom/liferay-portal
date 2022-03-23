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

import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.util.BCrypt;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Tomas Polesovsky
 */
@Component(
	property = "type=" + PasswordEncryptorUtil.TYPE_BCRYPT,
	service = PasswordEncryptor.class
)
public class BCryptPasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(
		String algorithm, String plainTextPassword, String encryptedPassword) {

		String salt = null;

		if (Validator.isNull(encryptedPassword)) {
			int rounds = _ROUNDS;

			Matcher matcher = _pattern.matcher(algorithm);

			if (matcher.matches()) {
				rounds = GetterUtil.getInteger(matcher.group(1), rounds);
			}

			salt = BCrypt.gensalt(rounds);
		}
		else {
			salt = encryptedPassword.substring(0, 29);
		}

		return BCrypt.hashpw(plainTextPassword, salt);
	}

	@Override
	public String getAlgorithmType() {
		return PasswordEncryptorUtil.TYPE_BCRYPT;
	}

	private static final int _ROUNDS = 10;

	private static final Pattern _pattern = Pattern.compile(
		"^BCrypt/([0-9]+)$", Pattern.CASE_INSENSITIVE);

}