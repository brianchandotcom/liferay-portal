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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringPool;

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
@PowerMockIgnore({"javax.crypto.*" })
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class LegacyAlgorithmAwarePasswordEncryptorTest {

	@Before
	public void setUp() {
		new DigesterUtil().setDigester(new DigesterImpl());

		CompositePasswordEncryptor compositePasswordEncryptor =
			new CompositePasswordEncryptor();

		compositePasswordEncryptor.setDefaultPasswordEncryptor(
			new DefaultPasswordEncryptor());

		List<PasswordEncryptor> passwordEncryptors =
			new ArrayList<PasswordEncryptor>();

		passwordEncryptors.add(new BCryptPasswordEncryptor());

		passwordEncryptors.add(new CryptPasswordEncryptor());

		passwordEncryptors.add(new NullPasswordEncryptor());

		passwordEncryptors.add(new PBKDF2PasswordEncryptor());

		passwordEncryptors.add(new SSHAPasswordEncryptor());

		compositePasswordEncryptor.setPasswordEncryptors(passwordEncryptors);

		LegacyAlgorithmAwarePasswordEncryptor
			legacyAlgorithmAwarePasswordEncryptor =
			new LegacyAlgorithmAwarePasswordEncryptor();

		legacyAlgorithmAwarePasswordEncryptor.setParentPasswordEncryptor(
			compositePasswordEncryptor);

		PasswordEncryptorUtil passwordEncryptorUtil =
			new PasswordEncryptorUtil();

		passwordEncryptorUtil.setPasswordEncryptor(
			legacyAlgorithmAwarePasswordEncryptor);
	}

	@Test
	public void testLegacyEncryptionBCrypt() throws Exception {
		String expectedHash =
			"$2a$10$/ST7LsB.7AAHsn/tlK6hr.nudQaBbJhPX9KfRSSzsn.1ij45lVzaK";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_BCRYPT, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_BCRYPT);
	}

	@Test
	public void testLegacyEncryptionBCryptWith10Rounds() throws Exception {
		String expectedHash =
			"$2a$10$AHEC063zO5wHcovp1JteTukrB5jSWa2OTBkoUx79ItxqKzSBp/Sem";

		testLegacyEncryption(
			PasswordEncryptorUtil.TYPE_BCRYPT + "/10", expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_BCRYPT + "/10");
	}

	@Test
	public void testLegacyEncryptionBCryptWith12Rounds() throws Exception {
		String expectedHash =
			"$2a$12$2dD/NrqCEBlVgFEkkFCbzOll2a9vrdl8tTTqGosm26wJK1eCtsjnO";

		testLegacyEncryption(
			PasswordEncryptorUtil.TYPE_BCRYPT + "/12", expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_BCRYPT + "/12");
	}

	@Test
	public void testLegacyEncryptionCrypt() throws Exception {
		String expectedHash = "SNbUMVY9kKQpY";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_CRYPT, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_CRYPT);
	}

	@Test
	public void testLegacyEncryptionMD2() throws Exception {
		String expectedHash = "8DiBqIxuORNfDsxg79YJuQ==";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_MD2, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_MD2);
	}

	@Test
	public void testLegacyEncryptionMD5() throws Exception {
		String expectedHash = "X03MO1qnZdYdgyfeuILPmQ==";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_MD5, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_MD5);
	}

	@Test
	public void testLegacyEncryptionNONE() throws Exception {
		String expectedHash = "password";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_NONE, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_NONE);
	}

	@Test
	public void testLegacyEncryptionPBKDF2() throws Exception {
		String expectedHash =
			"AAAAoAAB9ADJZ16OuMAPPHe2CUbP0HPyXvagoKHumh7iHU3c";

		testLegacyEncryption(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1", expectedHash);

		testLegacyEncryptionDisabled(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1");
	}

	@Test
	public void testLegacyEncryptionPBKDF2With50000Rounds() throws Exception {
		String expectedHash =
			"AAAAoAAAw1B+jxO3UiVsWdBk4B9xGd/Ko3GKHW2afYhuit49";

		testLegacyEncryption(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1/50000",
			expectedHash);

		testLegacyEncryptionDisabled(
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1/50000");
	}

	@Test
	public void testLegacyEncryptionPBKDF2With50000RoundsAnd128Key()
		throws Exception {

		String expectedHash =
			"AAAAoAAAw1AbW1e1Str9wSLWIX5X9swLn+j5/5+m6auSPdva";

		testLegacyEncryption(
			PasswordEncryptorUtil.TYPE_PBKDF2+ "WithHmacSHA1/128/50000",
			expectedHash);

		testLegacyEncryptionDisabled(
			PasswordEncryptorUtil.TYPE_PBKDF2+ "WithHmacSHA1/128/50000");
	}

	@Test
	public void testLegacyEncryptionSHA() throws Exception {
		String expectedHash = "W6ph5Mm5Pz8GgiULbPgzG37mj9g=";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_SHA, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_NONE);
	}

	@Test
	public void testLegacyEncryptionSHA1() throws Exception {
		String expectedHash = "W6ph5Mm5Pz8GgiULbPgzG37mj9g=";

		testLegacyEncryption("SHA-1", expectedHash);

		testLegacyEncryptionDisabled("SHA-1");
	}

	@Test
	public void testLegacyEncryptionSHA256() throws Exception {
		String expectedHash = "XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_SHA_256, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_SHA_256);
	}

	@Test
	public void testLegacyEncryptionSHA384() throws Exception {
		String expectedHash =
			"qLZLq9CsqRpZvbt3YbQh1PK7OCgNOnW6DyHyvrxFWD1EbFmGYMlM5oDEfRnDB4On";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_SHA_384, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_SHA_384);
	}

	@Test
	public void testLegacyEncryptionSSHA() throws Exception {
		String expectedHash = "2EWEKeVpSdd79PkTX5vaGXH5uQ028Smy/H1NmA==";

		testLegacyEncryption(PasswordEncryptorUtil.TYPE_SSHA, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_SSHA);
	}

	@Test
	public void testLegacyEncryptionUFCCRYPT() throws Exception {
		String expectedHash = "2lrTlR/pWPUOQ";

		testLegacyEncryption(
			PasswordEncryptorUtil.TYPE_UFC_CRYPT, expectedHash);

		testLegacyEncryptionDisabled(PasswordEncryptorUtil.TYPE_UFC_CRYPT);
	}

	protected String getAlgorithmHeader(String encryptedPassword) {
		int endPos = encryptedPassword.indexOf(CharPool.CLOSE_CURLY_BRACE);

		if (endPos > 0) {
			return encryptedPassword.substring(1, endPos);
		}

		return StringPool.EMPTY;
	}

	protected void testLegacyEncryption(
			String algorithm, String currentEncryptedPassword)
		throws PwdEncryptorException {

		String legacyEncryptionAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		try {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY = algorithm;

			String newPassword = PasswordEncryptorUtil.encrypt(
				"password", currentEncryptedPassword);

			Assert.assertEquals(currentEncryptedPassword, newPassword);

			newPassword = PasswordEncryptorUtil.encrypt("password");

			Assert.assertNotEquals(
				-1,
				newPassword.indexOf("{" + PasswordEncryptorUtil.TYPE_PBKDF2));

			String expectedResults = PasswordEncryptorUtil.encrypt(
				algorithm, "password", null);

			Assert.assertTrue(
				algorithm.contains(getAlgorithmHeader(expectedResults)));

			String newPassword2 = PasswordEncryptorUtil.encrypt(
				"password", expectedResults);

			Assert.assertEquals(expectedResults, newPassword2);
		}
		finally {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY =
				legacyEncryptionAlgorithm;
		}
	}

	protected void testLegacyEncryptionDisabled(String algorithm)
		throws PwdEncryptorException {

		String legacyEncryptionAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		try {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY = null;

			String expectedResults = PasswordEncryptorUtil.encrypt(
				algorithm, "password", null);

			Assert.assertEquals(
				-1,
				expectedResults.indexOf(
					"{" + PasswordEncryptorUtil.TYPE_PBKDF2));

			Assert.assertEquals(-1, expectedResults.indexOf("{" + algorithm));

			String newPassword = PasswordEncryptorUtil.encrypt(
				algorithm, "password", expectedResults);

			Assert.assertEquals(expectedResults, newPassword);

		}
		finally {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY =
				legacyEncryptionAlgorithm;
		}
	}

}