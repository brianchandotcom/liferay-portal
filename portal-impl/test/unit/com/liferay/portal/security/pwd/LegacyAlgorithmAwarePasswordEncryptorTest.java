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

package com.liferay.portal.security.pwd;

import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Michael C. Han
 */
@PowerMockIgnore("javax.crypto.*")
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class LegacyAlgorithmAwarePasswordEncryptorTest {

	@Before
	public void setUp() {
		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());

		PasswordEncryptorUtil passwordEncryptorUtil =
			new PasswordEncryptorUtil();

		LegacyAlgorithmAwarePasswordEncryptor
			legacyAlgorithmAwarePasswordEncryptor =
				new LegacyAlgorithmAwarePasswordEncryptor();

		CompositePasswordEncryptor compositePasswordEncryptor =
			new CompositePasswordEncryptor();

		compositePasswordEncryptor.setDefaultPasswordEncryptor(
			new DefaultPasswordEncryptor());

		List<PasswordEncryptor> passwordEncryptors = new ArrayList<>();

		passwordEncryptors.add(new BCryptPasswordEncryptor());
		passwordEncryptors.add(new CryptPasswordEncryptor());
		passwordEncryptors.add(new NullPasswordEncryptor());
		passwordEncryptors.add(new PBKDF2PasswordEncryptor());
		passwordEncryptors.add(new SSHAPasswordEncryptor());

		compositePasswordEncryptor.setPasswordEncryptors(passwordEncryptors);

		legacyAlgorithmAwarePasswordEncryptor.setParentPasswordEncryptor(
			compositePasswordEncryptor);

		passwordEncryptorUtil.setPasswordEncryptor(
			legacyAlgorithmAwarePasswordEncryptor);
	}

	@Test
	public void testEncryptBCrypt() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_BCRYPT,
			"$2a$10$/ST7LsB.7AAHsn/tlK6hr.nudQaBbJhPX9KfRSSzsn.1ij45lVzaK");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_BCRYPT);
	}

	@Test
	public void testEncryptBCryptWith10Rounds() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_BCRYPT + "/10",
			"$2a$10$AHEC063zO5wHcovp1JteTukrB5jSWa2OTBkoUx79ItxqKzSBp/Sem");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_BCRYPT + "/10");
	}

	@Test
	public void testEncryptBCryptWith12Rounds() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_BCRYPT + "/12",
			"$2a$12$2dD/NrqCEBlVgFEkkFCbzOll2a9vrdl8tTTqGosm26wJK1eCtsjnO");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_BCRYPT + "/12");
	}

	@Test
	public void testEncryptCrypt() throws Exception {
		testEncrypt(PasswordEncryptorUtil.TYPE_UFC_CRYPT, "SNbUMVY9kKQpY");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_UFC_CRYPT);
	}

	@Test
	public void testEncryptMD2() throws Exception {
		testEncrypt(PasswordEncryptorUtil.TYPE_MD2, "8DiBqIxuORNfDsxg79YJuQ==");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_MD2);
	}

	@Test
	public void testEncryptMD5() throws Exception {
		testEncrypt(PasswordEncryptorUtil.TYPE_MD5, "X03MO1qnZdYdgyfeuILPmQ==");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_MD5);
	}

	@Test
	public void testEncryptNONE() throws Exception {
		testEncrypt(PasswordEncryptorUtil.TYPE_NONE, "password");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_NONE);
	}

	@Test
	public void testEncryptPBKDF2() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1",
			"AAAAoAAB9ADJZ16OuMAPPHe2CUbP0HPyXvagoKHumh7iHU3c");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1");
	}

	@Test
	public void testEncryptPBKDF2With50000Rounds() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1/50000",
			"AAAAoAAAw1B+jxO3UiVsWdBk4B9xGd/Ko3GKHW2afYhuit49");

		testEncryptDisabled(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1/50000");
	}

	@Test
	public void testEncryptPBKDF2With50000RoundsAnd128Key() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_PBKDF2+ "WithHmacSHA1/128/50000",
			"AAAAoAAAw1AbW1e1Str9wSLWIX5X9swLn+j5/5+m6auSPdva");

		testEncryptDisabled(
			PasswordEncryptorUtil.TYPE_PBKDF2+ "WithHmacSHA1/128/50000");
	}

	@Test
	public void testEncryptSHA() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_SHA, "W6ph5Mm5Pz8GgiULbPgzG37mj9g=");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_NONE);
	}

	@Test
	public void testEncryptSHA1() throws Exception {
		testEncrypt("SHA-1", "W6ph5Mm5Pz8GgiULbPgzG37mj9g=");

		testEncryptDisabled("SHA-1");
	}

	@Test
	public void testEncryptSHA256() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_SHA_256,
			"XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_SHA_256);
	}

	@Test
	public void testEncryptSHA384() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_SHA_384,
			"qLZLq9CsqRpZvbt3YbQh1PK7OCgNOnW6DyHyvrxFWD1EbFmGYMlM5oDEfRnDB4On");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_SHA_384);
	}

	@Test
	public void testEncryptSSHA() throws Exception {
		testEncrypt(
			PasswordEncryptorUtil.TYPE_SSHA,
			"2EWEKeVpSdd79PkTX5vaGXH5uQ028Smy/H1NmA==");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_SSHA);
	}

	@Test
	public void testEncryptUFCCRYPT() throws Exception {
		testEncrypt(PasswordEncryptorUtil.TYPE_UFC_CRYPT, "2lrTlR/pWPUOQ");

		testEncryptDisabled(PasswordEncryptorUtil.TYPE_UFC_CRYPT);
	}

	protected String getAlgorithmHeader(String encryptedPassword) {
		int index = encryptedPassword.indexOf(CharPool.CLOSE_CURLY_BRACE);

		if (index > 0) {
			return encryptedPassword.substring(1, index);
		}

		return StringPool.BLANK;
	}

	protected void testEncrypt(String algorithm, String encryptedPassword)
		throws PwdEncryptorException {

		String legacyEncryptionAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		try {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY = algorithm;

			Assert.assertEquals(
				encryptedPassword,
				PasswordEncryptorUtil.encrypt("password", encryptedPassword));

			String newEncryptedPassword = PasswordEncryptorUtil.encrypt(
				"password");

			Assert.assertNotEquals(
				-1,
				newEncryptedPassword.indexOf(
					"{" + PasswordEncryptorUtil.TYPE_PBKDF2));

			newEncryptedPassword = PasswordEncryptorUtil.encrypt(
				algorithm, "password", null);

			Assert.assertTrue(
				algorithm.contains(getAlgorithmHeader(newEncryptedPassword)));
			Assert.assertEquals(
				newEncryptedPassword,
				PasswordEncryptorUtil.encrypt(
					"password", newEncryptedPassword));
		}
		finally {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY =
				legacyEncryptionAlgorithm;
		}
	}

	protected void testEncryptDisabled(String algorithm)
		throws PwdEncryptorException {

		String legacyEncryptionAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		try {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY = null;

			String encryptedPassword = PasswordEncryptorUtil.encrypt(
				algorithm, "password", null);

			Assert.assertEquals(
				-1,
				encryptedPassword.indexOf(
					"{" + PasswordEncryptorUtil.TYPE_PBKDF2));
			Assert.assertEquals(-1, encryptedPassword.indexOf("{" + algorithm));
			Assert.assertEquals(
				encryptedPassword,
				PasswordEncryptorUtil.encrypt(
					algorithm, "password", encryptedPassword));
		}
		finally {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY =
				legacyEncryptionAlgorithm;
		}
	}

}