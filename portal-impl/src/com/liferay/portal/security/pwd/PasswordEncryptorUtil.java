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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Tomas Polesovsky
 * @author Michael C. Han
 */
public class PasswordEncryptorUtil {

	public static final String PASSWORDS_ENCRYPTION_ALGORITHM =
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)).toUpperCase();

	public static final char[] SALT_CHARS =
		"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789./"
			.toCharArray();

	public static final String TYPE_BCRYPT = "BCRYPT";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #TYPE_UFC_CRYPT}
	 */
	public static final String TYPE_CRYPT = "CRYPT";

	public static final String TYPE_MD2 = "MD2";

	public static final String TYPE_MD5 = "MD5";

	public static final String TYPE_NONE = "NONE";

	public static final String TYPE_PBKDF2 = "PBKDF2";

	public static final String TYPE_SHA = "SHA";

	public static final String TYPE_SHA_256 = "SHA-256";

	public static final String TYPE_SHA_384 = "SHA-384";

	public static final String TYPE_SSHA = "SSHA";

	public static final String TYPE_UFC_CRYPT = "UFC-CRYPT";

	public static String encrypt(String clearTextPassword)
		throws PwdEncryptorException {

		return encrypt(PASSWORDS_ENCRYPTION_ALGORITHM, clearTextPassword, null);
	}

	public static String encrypt(
			String clearTextPassword, String currentEncryptedPassword)
		throws PwdEncryptorException {

		return encrypt(
			PASSWORDS_ENCRYPTION_ALGORITHM, clearTextPassword,
			currentEncryptedPassword);
	}

	public static String encrypt(
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		return _passwordEncryptor.encrypt(
			algorithm, clearTextPassword, currentEncryptedPassword);
	}

	public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
		_passwordEncryptor = passwordEncryptor;
	}

	private static PasswordEncryptor _passwordEncryptor;

}