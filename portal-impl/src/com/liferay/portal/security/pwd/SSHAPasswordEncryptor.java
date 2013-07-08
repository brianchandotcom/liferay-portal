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
import com.liferay.portal.kernel.util.ArrayUtil;
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

	@Override
	public String[] getSupportedAlgorithmTypes() {
		return new String[] {PasswordEncryptorUtil.TYPE_SSHA};
	}

	@Override
	protected String doEncrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		byte[] saltBytes = getSaltBytes(encryptedPassword);

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

			byte[] plainTextPasswordBytes = plainTextPassword.getBytes(
				Digester.ENCODING);

			byte[] messageDigestBytes = messageDigest.digest(
				ArrayUtil.append(plainTextPasswordBytes, saltBytes));

			return Base64.encode(
				ArrayUtil.append(messageDigestBytes, saltBytes));
		}
		catch (NoSuchAlgorithmException nsae) {
			throw new PwdEncryptorException(nsae.getMessage(), nsae);
		}
		catch (UnsupportedEncodingException uee) {
			throw new PwdEncryptorException(uee.getMessage(), uee);
		}
	}

	protected byte[] getSaltBytes(String encryptedPassword)
		throws PwdEncryptorException {

		byte[] saltBytes = new byte[8];

		if (Validator.isNull(encryptedPassword)) {
			Random random = new SecureRandom();

			random.nextBytes(saltBytes);
		}
		else {
			try {
				byte[] encryptedPasswordBytes = Base64.decode(
					encryptedPassword);

				System.arraycopy(
					encryptedPasswordBytes, encryptedPasswordBytes.length - 8,
					saltBytes, 0, saltBytes.length);
			}
			catch (Exception e) {
				throw new PwdEncryptorException(
					"Unable to extract salt from encrypted password " +
						e.getMessage(),
					e);
			}
		}

		return saltBytes;
	}

}