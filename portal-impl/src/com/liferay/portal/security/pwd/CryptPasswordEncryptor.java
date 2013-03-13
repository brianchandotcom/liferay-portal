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
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.Validator;

import java.io.UnsupportedEncodingException;

import java.util.Random;

import org.vps.crypt.Crypt;

/**
 * @author Michael C. Han
 * @author Tomas Polesovsky
 */
public class CryptPasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	public String[] getSupportedAlgorithmTypes() {
		return new String[] {
			PasswordEncryptorUtil.TYPE_CRYPT,
			PasswordEncryptorUtil.TYPE_UFC_CRYPT };
	}

	@Override
	protected String doEncrypt(
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		byte[] saltBytes = getSalt(currentEncryptedPassword);

		try {
			return Crypt.crypt(
				saltBytes, clearTextPassword.getBytes(Digester.ENCODING));
		}
		catch (UnsupportedEncodingException uee) {
			throw new PwdEncryptorException(uee.getMessage());
		}
	}

	protected byte[] getSalt(String cryptString) throws PwdEncryptorException {
		byte[] saltBytes = null;

		try {
			if (Validator.isNull(cryptString)) {

				// Generate random salt

				Random random = new Random();

				int numSaltChars = PasswordEncryptorUtil.SALT_CHARS.length;

				StringBuilder sb = new StringBuilder();

				int x = random.nextInt(Integer.MAX_VALUE) % numSaltChars;
				int y = random.nextInt(Integer.MAX_VALUE) % numSaltChars;

				sb.append(PasswordEncryptorUtil.SALT_CHARS[x]);
				sb.append(PasswordEncryptorUtil.SALT_CHARS[y]);

				String salt = sb.toString();

				saltBytes = salt.getBytes(Digester.ENCODING);
			}
			else {

				// Extract salt from encrypted password

				String salt = cryptString.substring(0, 2);

				saltBytes = salt.getBytes(Digester.ENCODING);
			}
		}
		catch (UnsupportedEncodingException uee) {
			throw new PwdEncryptorException(
				"Unable to extract salt from encrypted password: " +
					uee.getMessage());
		}

		return saltBytes;
	}

}