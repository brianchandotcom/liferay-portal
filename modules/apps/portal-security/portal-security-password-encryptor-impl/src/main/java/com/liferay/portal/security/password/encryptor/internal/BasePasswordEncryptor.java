/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.password.encryptor.internal;

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;

/**
 * @author Michael C. Han
 */
public abstract class BasePasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		return encrypt(algorithm, plainTextPassword, encryptedPassword, false);
	}

}