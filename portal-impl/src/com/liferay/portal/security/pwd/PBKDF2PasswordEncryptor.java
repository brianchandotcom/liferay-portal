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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.nio.ByteBuffer;

import java.security.SecureRandom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author Michael C. Han
 * @author Tomas Polesovsky
 */
public class PBKDF2PasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	public String[] getSupportedAlgorithmTypes() {
		return new String[] { PasswordEncryptorUtil.TYPE_PBKDF2 };
	}

	@Override
	protected String doEncrypt(
			String algorithm, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		try {
			PBKDF2EncryptionConfiguration pbkdf2EncryptionConfiguration =
				new PBKDF2EncryptionConfiguration();

			pbkdf2EncryptionConfiguration.configure(
				algorithm, currentEncryptedPassword);

			byte[] saltBytes = pbkdf2EncryptionConfiguration.getSaltBytes();

			PBEKeySpec keySpec = new PBEKeySpec(
				clearTextPassword.toCharArray(), saltBytes,
				pbkdf2EncryptionConfiguration.getRounds(),
				pbkdf2EncryptionConfiguration.getKeySize());

			String algorithmName = algorithm;

			int slashIndex = algorithm.indexOf(CharPool.SLASH);

			if (slashIndex > -1) {
				algorithmName = algorithm.substring(0, slashIndex);
			}

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(
				algorithmName);

			byte[] key = keyFactory.generateSecret(keySpec).getEncoded();

			ByteBuffer result = ByteBuffer.allocate(
				2 * 4 + saltBytes.length + key.length);

			result.putInt(pbkdf2EncryptionConfiguration.getKeySize());

			result.putInt(pbkdf2EncryptionConfiguration.getRounds());

			result.put(saltBytes);

			result.put(key);

			return Base64.encode(result.array());
		}
		catch (Exception e) {
			throw new PwdEncryptorException(
				"Unable to generate hash: " + e.getMessage(), e);
		}
	}

	private static final int _DEFAULT_PBKDF2_KEY_SIZE = 160;

	private static final int _DEFAULT_PBKDF2_ROUNDS = 128000;

	private static final int _DEFAULT_PBKDF2_SALT_SIZE = 8;

	private static final Pattern _PBKDF2_PATTERN = Pattern.compile(
		"^.*/?([0-9]+)?/([0-9]+)$");

	private class PBKDF2EncryptionConfiguration {

		public void configure(String algorithm, String currentEncryptedPassword)
			throws PwdEncryptorException {

			if (Validator.isNull(currentEncryptedPassword)) {
				Matcher pbkdf2Matcher = _PBKDF2_PATTERN.matcher(algorithm);

				if (pbkdf2Matcher.matches()) {
					_keySize = GetterUtil.getInteger(
						pbkdf2Matcher.group(1), _DEFAULT_PBKDF2_KEY_SIZE);

					_rounds = GetterUtil.getInteger(
						pbkdf2Matcher.group(2), _DEFAULT_PBKDF2_ROUNDS);
				}

				SecureRandom random = new SecureRandom();

				random.nextBytes(_saltBytes);
			}
			else {
				byte[] configAndSalt = new byte[16];

				try {
					byte[] passwordBytes = Base64.decode(
						currentEncryptedPassword);

					System.arraycopy(
						passwordBytes, 0, configAndSalt, 0,
						configAndSalt.length);
				}
				catch (Exception e) {
					throw new PwdEncryptorException(
						"Unable to extract salt from encrypted password: " +
							e.getMessage());
				}

				ByteBuffer buff = ByteBuffer.wrap(configAndSalt);

				_keySize = buff.getInt();

				_rounds = buff.getInt();

				buff.get(_saltBytes);
			}
		}

		public int getKeySize() {
			return _keySize;
		}

		public int getRounds() {
			return _rounds;
		}

		public byte[] getSaltBytes() {
			return _saltBytes;
		}

		private int _keySize = _DEFAULT_PBKDF2_KEY_SIZE;

		private int _rounds = _DEFAULT_PBKDF2_ROUNDS;

		private byte[] _saltBytes = new byte[_DEFAULT_PBKDF2_SALT_SIZE];
	}

}