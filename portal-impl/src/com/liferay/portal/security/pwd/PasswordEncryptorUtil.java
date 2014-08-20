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

import com.liferay.portal.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Tomas Polesovsky
 * @author Michael C. Han
 */
public class PasswordEncryptorUtil {

	public static final String PASSWORDS_ENCRYPTION_ALGORITHM =
		StringUtil.toUpperCase(
			GetterUtil.getString(
				PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)));

	public static final String TYPE_BCRYPT = "BCRYPT";

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #TYPE_UFC_CRYPT}
	 */
	@Deprecated
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

	public static String encrypt(String plainTextPassword)
		throws PwdEncryptorException {

		return encrypt(plainTextPassword, null);
	}

	public static String encrypt(
			String plainTextPassword, String encryptedPassword)
		throws PwdEncryptorException {

		long startTime = 0;

		if (_log.isDebugEnabled()) {
			startTime = System.currentTimeMillis();
		}

		try {
			return encrypt(
				PASSWORDS_ENCRYPTION_ALGORITHM, plainTextPassword,
				encryptedPassword);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Password encrypted in " +
						(System.currentTimeMillis() - startTime) + "ms");
			}
		}
	}

	public static String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		return _passwordEncryptor.encrypt(
			algorithm, plainTextPassword, encryptedPassword);
	}

	public PasswordEncryptor getPasswordEncryptor() {
		return _passwordEncryptor;
	}

	public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
		_passwordEncryptor = passwordEncryptor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PasswordEncryptorUtil.class);

	private static PasswordEncryptor _passwordEncryptor;

}