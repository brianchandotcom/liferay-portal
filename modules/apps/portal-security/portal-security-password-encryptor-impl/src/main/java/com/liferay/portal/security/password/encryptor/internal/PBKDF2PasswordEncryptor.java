/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.password.encryptor.internal;

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.util.StringUtil;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.util.DigestFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Tomas Polesovsky
 */
@Component(
	property = "type=" + PasswordEncryptor.TYPE_PBKDF2,
	service = PasswordEncryptor.class
)
public class PBKDF2PasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword, boolean upgradeHashSecurity)
		throws PwdEncryptorException {

		try {
			if (upgradeHashSecurity) {
				encryptedPassword = null;
			}

			PBKDF2EncryptionConfiguration pbkdf2EncryptionConfiguration =
				new PBKDF2EncryptionConfiguration();

			pbkdf2EncryptionConfiguration.configure(
				algorithm, encryptedPassword);

			PKCS5S2ParametersGenerator pkcs5S2ParametersGenerator =
				new PKCS5S2ParametersGenerator(
					pbkdf2EncryptionConfiguration.getDigest());

			pkcs5S2ParametersGenerator.init(
				plainTextPassword.getBytes(),
				pbkdf2EncryptionConfiguration.getSaltBytes(),
				pbkdf2EncryptionConfiguration.getRounds());

			byte[] saltBytes = pbkdf2EncryptionConfiguration.getSaltBytes();

			KeyParameter keyParameter =
				(KeyParameter)
					pkcs5S2ParametersGenerator.generateDerivedMacParameters(
						pbkdf2EncryptionConfiguration.getKeySize());

			byte[] secretKeyBytes = keyParameter.getKey();

			ByteBuffer byteBuffer = ByteBuffer.allocate(
				(2 * 4) + saltBytes.length + secretKeyBytes.length);

			byteBuffer.putInt(pbkdf2EncryptionConfiguration.getKeySize());
			byteBuffer.putInt(pbkdf2EncryptionConfiguration.getRounds());
			byteBuffer.put(saltBytes);
			byteBuffer.put(secretKeyBytes);

			return Base64.encode(byteBuffer.array());
		}
		catch (Exception exception) {
			throw new PwdEncryptorException(exception.getMessage(), exception);
		}
	}

	private static final int _KEY_SIZE = 160;

	private static final int _ROUNDS = 1300000;

	private static final int _SALT_BYTES_LENGTH = 16;

	private static final Pattern _pattern = Pattern.compile(
		"^.*/?([0-9]+)?/([0-9]+)$");

	private static class PBKDF2EncryptionConfiguration {

		public void configure(String algorithm, String encryptedPassword)
			throws PwdEncryptorException {

			if (Validator.isNull(encryptedPassword)) {
				_saltBytes = new byte[_SALT_BYTES_LENGTH];

				Matcher matcher = _pattern.matcher(algorithm);

				if (matcher.matches()) {
					_keySize = GetterUtil.getInteger(
						matcher.group(1), _KEY_SIZE);

					_rounds = GetterUtil.getInteger(matcher.group(2), _ROUNDS);
				}

				BigEndianCodec.putLong(
					_saltBytes, 0, SecureRandomUtil.nextLong());
			}
			else {
				ByteBuffer byteBuffer = ByteBuffer.wrap(
					Base64.decode(encryptedPassword));

				try {
					int length = byteBuffer.remaining();

					_keySize = byteBuffer.getInt();
					_rounds = byteBuffer.getInt();

					_saltBytes = new byte
						[length - (2 * 4) -
							(int)Math.ceil((double)_keySize / 8)];

					byteBuffer.get(_saltBytes);
				}
				catch (BufferUnderflowException bufferUnderflowException) {
					throw new PwdEncryptorException(
						"Unable to extract salt from encrypted password",
						bufferUnderflowException);
				}
			}

			int indexOf = StringUtil.indexOfIgnoreCase(algorithm, "SHA");

			if (indexOf < 0) {
				return;
			}

			String digest = algorithm.substring(indexOf);

			_digest = DigestFactory.getDigest(digest.split("/")[0]);
		}

		public Digest getDigest() {
			return _digest;
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

		private Digest _digest = DigestFactory.getDigest("SHA1");
		private int _keySize = _KEY_SIZE;
		private int _rounds = _ROUNDS;
		private byte[] _saltBytes;

	}

}