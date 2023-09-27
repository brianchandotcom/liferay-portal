/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return encrypt(plainTextPassword, (String)null);
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
			String plainTextPassword, String encryptedPassword,
			Boolean upgradeHashSecurity)
		throws PwdEncryptorException {

		if (upgradeHashSecurity) {
			encryptedPassword = null;
		}

		return _passwordEncryptor.encrypt(
			null, plainTextPassword, encryptedPassword, upgradeHashSecurity);
	}

	public static String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		return _passwordEncryptor.encrypt(
			algorithm, plainTextPassword, encryptedPassword);
	}

	public static String getDefaultPasswordEncryptionAlgorithm() {
		return _passwordEncryptor.getDefaultPasswordEncryptionAlgorithm();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PasswordEncryptorUtil.class);

	private static volatile PasswordEncryptor _passwordEncryptor =
		ServiceProxyFactory.newServiceTrackedInstance(
			PasswordEncryptor.class, PasswordEncryptorUtil.class,
			"_passwordEncryptor", "(composite=true)", true);

}