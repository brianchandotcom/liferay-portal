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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.UnsupportedEncodingException;

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

		try {
			byte[] saltBytes = null;

			if (Validator.isNull(currentEncryptedPassword)) {
				String salt = BCrypt.gensalt();

				saltBytes = salt.getBytes(StringPool.UTF8);
			}
			else {
				String salt = currentEncryptedPassword.substring(0, 29);

				saltBytes = salt.getBytes(StringPool.UTF8);
			}

			String salt = new String(saltBytes);

			return BCrypt.hashpw(clearTextPassword, salt);
		}
		catch (UnsupportedEncodingException uee) {
			throw new PwdEncryptorException(
				"Unable to extract salt from encrypted password: " +
					uee.getMessage());
		}
	}

}