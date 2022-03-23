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

/**
 * @author Tomas Polesovsky
 */
public interface PasswordEncryptor {

	public String encrypt(String plainTextPassword, String encryptedPassword)
		throws PwdEncryptorException;

	public String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException;

	public String getAlgorithmType();

	public String getDefaultPasswordAlgorithmType();

}