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

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

/**
 * @author Michael C. Han
 */
public abstract class BasePasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(String plainTextPassword, String encryptedPassword)
		throws PwdEncryptorException {

		return encrypt(
			getDefaultPasswordAlgorithmType(), plainTextPassword,
			encryptedPassword);
	}

	@Override
	public String getDefaultPasswordAlgorithmType() {
		return _PASSWORDS_ENCRYPTION_ALGORITHM;
	}

	private static final String _PASSWORDS_ENCRYPTION_ALGORITHM =
		StringUtil.toUpperCase(
			GetterUtil.getString(
				PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)));

}