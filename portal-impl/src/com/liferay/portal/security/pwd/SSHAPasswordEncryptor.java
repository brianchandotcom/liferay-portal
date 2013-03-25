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
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.Validator;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Random;

/**
 * @author Michael C. Han
 * @author Tomas Polesovsky
 */
public class SSHAPasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	public String[] getSupportedAlgorithmTypes() {
		return new String[] { PasswordEncryptorUtil.TYPE_SSHA };
	}

	@Override
	protected String doEncrypt(
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		byte[] saltBytes = getSaltBytes(currentEncryptedPassword);

		try {
			byte[] clearTextPasswordBytes = clearTextPassword.getBytes(
				Digester.ENCODING);

			// Create a byte array of salt bytes appended to password bytes

			byte[] pwdPlusSalt =
				new byte[clearTextPasswordBytes.length + saltBytes.length];

			System.arraycopy(
				clearTextPasswordBytes, 0, pwdPlusSalt, 0,
				clearTextPasswordBytes.length);

			System.arraycopy(
				saltBytes, 0, pwdPlusSalt, clearTextPasswordBytes.length,
				saltBytes.length);

			// Digest byte array

			MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");

			byte[] pwdPlusSaltHash = sha1Digest.digest(pwdPlusSalt);

			// Appends salt bytes to the SHA-1 digest.

			byte[] digestPlusSalt =
				new byte[pwdPlusSaltHash.length + saltBytes.length];

			System.arraycopy(
				pwdPlusSaltHash, 0, digestPlusSalt, 0, pwdPlusSaltHash.length);

			System.arraycopy(
				saltBytes, 0, digestPlusSalt, pwdPlusSaltHash.length,
				saltBytes.length);

			// Base64 encode and format string

			return Base64.encode(digestPlusSalt);
		}
		catch (NoSuchAlgorithmException nsae) {
			throw new PwdEncryptorException(nsae.getMessage());
		}
		catch (UnsupportedEncodingException uee) {
			throw new PwdEncryptorException(uee.getMessage());
		}
	}

	protected byte[] getSaltBytes(String sshaString)
		throws PwdEncryptorException {

		byte[] saltBytes = new byte[8];

		if (Validator.isNull(sshaString)) {

			// Generate random salt

			Random random = new SecureRandom();

			random.nextBytes(saltBytes);
		}
		else {

			// Extract salt from encrypted password

			try {
				byte[] digestPlusSalt = Base64.decode(sshaString);
				byte[] digestBytes = new byte[digestPlusSalt.length - 8];

				System.arraycopy(
					digestPlusSalt, 0, digestBytes, 0, digestBytes.length);

				System.arraycopy(
					digestPlusSalt, digestBytes.length, saltBytes, 0,
					saltBytes.length);
			}
			catch (Exception e) {
				throw new PwdEncryptorException(
					"Unable to extract salt from encrypted password: " +
						e.getMessage());
			}
		}

		return saltBytes;
	}

}