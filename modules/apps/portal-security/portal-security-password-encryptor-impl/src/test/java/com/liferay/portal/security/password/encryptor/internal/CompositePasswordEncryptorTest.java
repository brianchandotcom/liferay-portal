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

package com.liferay.portal.security.password.encryptor.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;

/**
 * @author Tomas Polesovsky
 */
public class CompositePasswordEncryptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		CompositePasswordEncryptor compositePasswordEncryptor =
			new CompositePasswordEncryptor();

		ReflectionTestUtil.invoke(
			compositePasswordEncryptor, "activate",
			new Class<?>[] {BundleContext.class}, bundleContext);

		bundleContext.registerService(
			PasswordEncryptor.class, compositePasswordEncryptor,
			MapUtil.singletonDictionary("composite", "true"));

		bundleContext.registerService(
			PasswordEncryptor.class, new DefaultPasswordEncryptor(),
			MapUtil.singletonDictionary(
				"type", PasswordEncryptor.TYPE_DEFAULT));
		bundleContext.registerService(
			PasswordEncryptor.class, new BCryptPasswordEncryptor(),
			MapUtil.singletonDictionary("type", PasswordEncryptor.TYPE_BCRYPT));
		bundleContext.registerService(
			PasswordEncryptor.class, new CryptPasswordEncryptor(),
			MapUtil.singletonDictionary(
				"type", PasswordEncryptor.TYPE_UFC_CRYPT));
		bundleContext.registerService(
			PasswordEncryptor.class, new NullPasswordEncryptor(),
			MapUtil.singletonDictionary("type", PasswordEncryptor.TYPE_NONE));
		bundleContext.registerService(
			PasswordEncryptor.class, new PBKDF2PasswordEncryptor(),
			MapUtil.singletonDictionary("type", PasswordEncryptor.TYPE_PBKDF2));
		bundleContext.registerService(
			PasswordEncryptor.class, new SSHAPasswordEncryptor(),
			MapUtil.singletonDictionary("type", PasswordEncryptor.TYPE_SSHA));
	}

	@Test
	public void testEncryptBCrypt() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_BCRYPT, "password",
			"$2a$10$/ST7LsB.7AAHsn/tlK6hr.nudQaBbJhPX9KfRSSzsn.1ij45lVzaK",
			PasswordEncryptor.TYPE_BCRYPT);
	}

	@Test
	public void testEncryptBCryptWith10Rounds() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_BCRYPT + "/10", "password",
			"$2a$10$JX0uYSs6pSrp05TlQxkmz.hkKGK6Av.KkNCzAYOFugO3qxjAiZleO",
			PasswordEncryptor.TYPE_BCRYPT);
	}

	@Test
	public void testEncryptBCryptWith12Rounds() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_BCRYPT + "/12", "password",
			"$2a$12$2dD/NrqCEBlVgFEkkFCbzOll2a9vrdl8tTTqGosm26wJK1eCtsjnO",
			PasswordEncryptor.TYPE_BCRYPT);
	}

	@Test
	public void testEncryptCRYPT() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_UFC_CRYPT, "password", "SNbUMVY9kKQpY",
			PasswordEncryptor.TYPE_UFC_CRYPT);
	}

	@Test
	public void testEncryptFailure() throws Exception {
		testEncryptFailure(
			"Some Nonexistent Algorithm", StringPool.BLANK, StringPool.BLANK);

		testEncryptFailure(null, null, null);

		testEncryptFailure(null, null, StringPool.BLANK);

		testEncryptFailure(null, StringPool.BLANK, null);

		testEncryptFailure(StringPool.BLANK, null, null);

		testEncryptFailure(StringPool.BLANK, null, StringPool.BLANK);

		testEncryptFailure(StringPool.BLANK, StringPool.BLANK, null);

		testEncryptFailure(null, StringPool.BLANK, StringPool.BLANK);

		testEncryptFailure(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);

		testEncryptFailure(
			PasswordEncryptor.TYPE_SHA, "password",
			"W6ph5Mm5Pz8GgiULbPgzG37mj9g=");
	}

	@Test
	public void testEncryptMD2() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_MD2, "password", "8DiBqIxuORNfDsxg79YJuQ==",
			PasswordEncryptor.TYPE_MD2);
	}

	@Test
	public void testEncryptMD5() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_MD5, "password", "X03MO1qnZdYdgyfeuILPmQ==",
			PasswordEncryptor.TYPE_MD5);
	}

	@Test
	public void testEncryptNONE() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_NONE, "password", "password",
			PasswordEncryptor.TYPE_NONE);
	}

	@Test
	public void testEncryptPBKDF2() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1", "password",
			"AAAAoAAB9ADJZ16OuMAPPHe2CUbP0HPyXvagoKHumh7iHU3c",
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1");
	}

	@Test
	public void testEncryptPBKDF2With50000Rounds() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1/50000", "password",
			"AAAAoAAAw1B+jxO3UiVsWdBk4B9xGd/Ko3GKHW2afYhuit49",
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1");
	}

	@Test
	public void testEncryptPBKDF2With50000RoundsAnd128Key() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1/128/50000",
			"password", "AAAAoAAAw1AbW1e1Str9wSLWIX5X9swLn+j5/5+m6auSPdva",
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1");
	}

	@Test
	public void testEncryptPBKDF2With720000RoundsAnd128Key() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1/128/720000",
			"password", "AAAAoAAB9ADyaBP3fTtsBh8YlRn1CU7VLYR/mnH7ADMNMz2o",
			PasswordEncryptor.TYPE_PBKDF2 + "WithHmacSHA1");
	}

	@Test
	public void testEncryptSHA() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_SHA, "password",
			"W6ph5Mm5Pz8GgiULbPgzG37mj9g=", PasswordEncryptor.TYPE_SHA);
	}

	@Test
	public void testEncryptSHA1() throws Exception {
		runTests("SHA-1", "password", "W6ph5Mm5Pz8GgiULbPgzG37mj9g=", "SHA-1");
	}

	@Test
	public void testEncryptSHA256() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_SHA_256, "password",
			"XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=",
			PasswordEncryptor.TYPE_SHA_256);
	}

	@Test
	public void testEncryptSHA384() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_SHA_384, "password",
			"qLZLq9CsqRpZvbt3YbQh1PK7OCgNOnW6DyHyvrxFWD1EbFmGYMlM5oDEfRnDB4On",
			PasswordEncryptor.TYPE_SHA_384);
	}

	@Test
	public void testEncryptSSHA() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_SSHA, "password",
			"2EWEKeVpSdd79PkTX5vaGXH5uQ028Smy/H1NmA==",
			PasswordEncryptor.TYPE_SSHA);
	}

	@Test
	public void testEncryptUFCCRYPT() throws Exception {
		runTests(
			PasswordEncryptor.TYPE_UFC_CRYPT, "password", "2lrTlR/pWPUOQ",
			PasswordEncryptor.TYPE_UFC_CRYPT);
	}

	protected void runTests(
			String algorithm, String plainPassword, String encryptedPassword,
			String prependedAlgorithm)
		throws Exception {

		testEncrypt(algorithm);

		testEncrypt(
			plainPassword,
			StringBundler.concat(
				CharPool.OPEN_CURLY_BRACE, prependedAlgorithm,
				CharPool.CLOSE_CURLY_BRACE, encryptedPassword));

		testLegacyEncrypt(algorithm, plainPassword, encryptedPassword);
	}

	protected void testEncrypt(String algorithm) throws Exception {
		String plainPassword = "password";

		String expectedPassword = PasswordEncryptorUtil.encrypt(
			algorithm, plainPassword, null);

		testEncrypt(plainPassword, expectedPassword);
	}

	protected void testEncrypt(String plainPassword, String expectedPassword)
		throws Exception {

		Assert.assertEquals(
			expectedPassword,
			PasswordEncryptorUtil.encrypt(plainPassword, expectedPassword));
	}

	protected void testEncryptFailure(
		String algorithm, String plainTextPassword, String encryptedPassword) {

		try {
			PasswordEncryptorUtil.encrypt(
				algorithm, plainTextPassword, encryptedPassword);

			Assert.fail();
		}
		catch (Exception exception) {
		}
	}

	protected void testLegacyEncrypt(
			String legacyAlgorithm, String plainPassword,
			String expectedPassword)
		throws Exception {

		String originalLegacyAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		try {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY = legacyAlgorithm;

			Assert.assertEquals(
				expectedPassword,
				PasswordEncryptorUtil.encrypt(plainPassword, expectedPassword));
		}
		finally {
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY =
				originalLegacyAlgorithm;
		}
	}

}