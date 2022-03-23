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

package com.liferay.portal.kernel.security.pwd;

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Tomas Polesovsky
 * @author Michael C. Han
 */
public class PasswordEncryptorUtil {

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
			return _passwordEncryptor.encrypt(
				plainTextPassword, encryptedPassword);
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

	public static String getDefaultPasswordAlgorithmType() {
		return _passwordEncryptor.getDefaultPasswordAlgorithmType();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PasswordEncryptorUtil.class);

	private static volatile PasswordEncryptor _passwordEncryptor =
		ServiceProxyFactory.newServiceTrackedInstance(
			PasswordEncryptor.class, PasswordEncryptorUtil.class,
			"_passwordEncryptor", "(composite=true)", true);

}