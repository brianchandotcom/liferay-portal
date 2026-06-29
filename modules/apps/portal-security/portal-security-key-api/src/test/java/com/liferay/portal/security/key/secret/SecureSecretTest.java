/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.secret;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public class SecureSecretTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCharsRoundTripPreservesUnicodeContent() {
		String data = "héllo 世界";

		SecureSecret secureSecret = new SecureSecret(
			_createKeyReference(), data);

		Assert.assertArrayEquals(
			data.getBytes(StandardCharsets.UTF_8), secureSecret.getBytes());
		Assert.assertArrayEquals(data.toCharArray(), secureSecret.getChars());
	}

	@Test
	public void testCharsZeroedOnDestroy() {
		SecureSecret secureSecret = new SecureSecret(
			_createKeyReference(), RandomTestUtil.randomString());

		char[] chars = secureSecret.getChars();

		Assert.assertTrue(chars.length > 0);

		secureSecret.close();

		for (char c : chars) {
			Assert.assertEquals('\0', c);
		}
	}

	@Test
	public void testDestroyIsIdempotent() {
		SecureSecret secureSecret = new SecureSecret(
			RandomTestUtil.randomBytes(), _createKeyReference());

		secureSecret.destroy();
		secureSecret.destroy();

		Assert.assertTrue(secureSecret.isDestroyed());
	}

	@Test
	public void testGetCharsCachesResult() {
		SecureSecret secureSecret = new SecureSecret(
			_createKeyReference(), RandomTestUtil.randomString());

		char[] chars1 = secureSecret.getChars();
		char[] chars2 = secureSecret.getChars();

		Assert.assertSame(chars1, chars2);
	}

	@Test
	public void testGetCharsFromBytes() {
		String data = RandomTestUtil.randomString();

		SecureSecret secureSecret = new SecureSecret(
			data.getBytes(StandardCharsets.UTF_8), _createKeyReference());

		Assert.assertArrayEquals(data.toCharArray(), secureSecret.getChars());
	}

	@Test
	public void testGetThrowsAfterDestroy() {
		SecureSecret secureSecret = new SecureSecret(
			RandomTestUtil.randomBytes(), _createKeyReference());

		secureSecret.close();

		Assert.assertThrows(
			IllegalStateException.class, secureSecret::getBytes);
		Assert.assertThrows(
			IllegalStateException.class, secureSecret::getChars);
	}

	@Test
	public void testRejectsInvalidUTF8WhenDecoding() {
		SecureSecret secureSecret = new SecureSecret(
			new byte[] {(byte)0xC0, (byte)0xC0}, _createKeyReference());

		Assert.assertThrows(
			IllegalArgumentException.class, secureSecret::getChars);
	}

	@Test
	public void testRejectsLoneSurrogateChar() {
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> new SecureSecret(
				_createKeyReference(), new String(new char[] {'\uD800'})));
	}

	@Test
	public void testRejectsNullKeyReference() {
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> new SecureSecret(null, RandomTestUtil.randomString()));
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> new SecureSecret(RandomTestUtil.randomBytes(), null));
	}

	@Test
	public void testSecureSecretFromString() {
		String data = RandomTestUtil.randomString();

		SecureSecret secureSecret = new SecureSecret(
			_createKeyReference(), data);

		Assert.assertArrayEquals(data.toCharArray(), secureSecret.getChars());
		Assert.assertTrue(secureSecret.getBytes().length > 0);
	}

	@Test
	public void testSecureSecretImmutable() {
		byte[] data = RandomTestUtil.randomBytes();

		byte originalFirstByte = data[0];
		SecureSecret secureSecret = new SecureSecret(
			data, _createKeyReference());

		// Constructor must copy the input

		data[0] = (byte)~originalFirstByte;

		Assert.assertEquals(originalFirstByte, secureSecret.getBytes()[0]);
	}

	@Test
	public void testSecureSecretReturnsSameInstance() {
		SecureSecret secureSecret = new SecureSecret(
			RandomTestUtil.randomBytes(), _createKeyReference());

		byte[] internalBytes1 = secureSecret.getBytes();
		byte[] internalBytes2 = secureSecret.getBytes();

		Assert.assertSame(internalBytes1, internalBytes2);
	}

	@Test
	public void testSecureSecretZeroing() {
		byte[] data = RandomTestUtil.randomBytes();

		SecureSecret secureSecret = new SecureSecret(
			data, _createKeyReference());

		byte[] internalBytes = secureSecret.getBytes();

		Assert.assertArrayEquals(data, internalBytes);

		secureSecret.close();

		for (byte b : internalBytes) {
			Assert.assertEquals(0, b);
		}
	}

	private KeyReference _createKeyReference() {
		return new KeyReference(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			KeyReference.Type.SECRET);
	}

}