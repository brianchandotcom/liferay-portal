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

import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Tomas Polesovsky
 */
@PowerMockIgnore({"javax.crypto.*" })
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class CompositePasswordEncryptorTest extends PowerMockito {

	@Before
	public void setUp() {
		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());

		PasswordEncryptorUtil passwordEncryptorUtil =
			new PasswordEncryptorUtil();

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

		passwordEncryptorUtil.setPasswordEncryptor(compositePasswordEncryptor);
	}

	@Test
	public void testEncryptBCrypt() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_BCRYPT;

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"$2a$10$/ST7LsB.7AAHsn/tlK6hr.nudQaBbJhPX9KfRSSzsn.1ij45lVzaK");
	}

	@Test
	public void testEncryptBCryptWith10Rounds() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_BCRYPT + "/10";

		encrypt(algorithm);
	}

	@Test
	public void testEncryptBCryptWith12Rounds() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_BCRYPT + "/12";

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"$2a$12$2dD/NrqCEBlVgFEkkFCbzOll2a9vrdl8tTTqGosm26wJK1eCtsjnO");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testEncryptCRYPT() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_CRYPT;

		encrypt(algorithm);

		encrypt(algorithm, "password", "SNbUMVY9kKQpY");
	}

	@Test
	public void testEncryptFailure() throws Exception {
		encryptFailure(
			"Some Nonexistent Algorithm", StringPool.BLANK, StringPool.BLANK);

		encryptFailure(null, null, null);

		encryptFailure(null, null, StringPool.BLANK);

		encryptFailure(null, StringPool.BLANK, null);

		encryptFailure(StringPool.BLANK, null, null);

		encryptFailure(StringPool.BLANK, null, StringPool.BLANK);

		encryptFailure(StringPool.BLANK, StringPool.BLANK, null);

		encryptFailure(null, StringPool.BLANK, StringPool.BLANK);

		encryptFailure(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);
	}

	@Test
	public void testEncryptMD2() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_MD2;

		encrypt(algorithm);

		encrypt(algorithm, "password", "8DiBqIxuORNfDsxg79YJuQ==");
	}

	@Test
	public void testEncryptMD5() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_MD5;

		encrypt(algorithm);

		encrypt(algorithm, "password", "X03MO1qnZdYdgyfeuILPmQ==");
	}

	@Test
	public void testEncryptNONE() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_NONE;

		encrypt(algorithm);

		encrypt(algorithm, "password", "password");
	}

	@Test
	public void testEncryptPBKDF2() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1";

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"AAAAoAAB9ADJZ16OuMAPPHe2CUbP0HPyXvagoKHumh7iHU3c");
	}

	@Test
	public void testEncryptPBKDF2With50000Rounds() throws Exception {
		String algorithm =
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1/50000";

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"AAAAoAAAw1B+jxO3UiVsWdBk4B9xGd/Ko3GKHW2afYhuit49");
	}

	@Test
	public void testEncryptPBKDF2With50000RoundsAnd128Key() throws Exception {
		String algorithm =
			PasswordEncryptorUtil.TYPE_PBKDF2 + "WithHmacSHA1/128/50000";

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"AAAAoAAAw1AbW1e1Str9wSLWIX5X9swLn+j5/5+m6auSPdva");
	}

	@Test
	public void testEncryptSHA() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_SHA;

		encrypt(algorithm);

		encrypt(algorithm, "password", "W6ph5Mm5Pz8GgiULbPgzG37mj9g=");
	}

	@Test
	public void testEncryptSHA1() throws Exception {
		String algorithm = "SHA-1";

		encrypt(algorithm);

		encrypt(algorithm, "password", "W6ph5Mm5Pz8GgiULbPgzG37mj9g=");
	}

	@Test
	public void testEncryptSHA256() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_SHA_256;

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=");
	}

	@Test
	public void testEncryptSHA384() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_SHA_384;

		encrypt(algorithm);

		encrypt(
			algorithm, "password",
			"qLZLq9CsqRpZvbt3YbQh1PK7OCgNOnW6DyHyvrxFWD1EbFmGYMl" +
				"M5oDEfRnDB4On");
	}

	@Test
	public void testEncryptSSHA() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_SSHA;

		encrypt(algorithm);

		encrypt(
			algorithm, "password", "2EWEKeVpSdd79PkTX5vaGXH5uQ028Smy/H1NmA==");
	}

	@Test
	public void testEncryptUFCCRYPT() throws Exception {
		String algorithm = PasswordEncryptorUtil.TYPE_UFC_CRYPT;

		encrypt(algorithm);

		encrypt(algorithm, "password", "2lrTlR/pWPUOQ");
	}

	protected void encrypt(String algorithm) throws Exception {
		String password = "password";

		String encrypted = PasswordEncryptorUtil.encrypt(
			algorithm, password, null);

		encrypt(algorithm, password, encrypted);
	}

	protected void encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws Exception {

		Assert.assertEquals(
			encryptedPassword,
			PasswordEncryptorUtil.encrypt(
				algorithm, plainTextPassword, encryptedPassword));
	}

	protected void encryptFailure(
		String algorithm, String plainTextPassword, String encryptedPassword) {

		try {
			PasswordEncryptorUtil.encrypt(
				algorithm, plainTextPassword, encryptedPassword);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

}