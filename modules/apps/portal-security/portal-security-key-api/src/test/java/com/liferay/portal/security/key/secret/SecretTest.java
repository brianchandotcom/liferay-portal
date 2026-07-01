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
public class SecretTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCharsRoundTripPreservesUnicodeContent() {
		String data = "héllo 世界";

		Secret secret = new Secret(_createKeyReference(), data);

		Assert.assertArrayEquals(
			data.getBytes(StandardCharsets.UTF_8), secret.getBytes());
		Assert.assertArrayEquals(data.toCharArray(), secret.getChars());
	}

	@Test
	public void testCharsZeroedOnDestroy() {
		Secret secret = new Secret(
			_createKeyReference(), RandomTestUtil.randomString());

		char[] chars = secret.getChars();

		Assert.assertTrue(chars.length > 0);

		secret.close();

		for (char c : chars) {
			Assert.assertEquals('\0', c);
		}
	}

	@Test
	public void testDestroyIsIdempotent() {
		Secret secret = new Secret(
			RandomTestUtil.randomBytes(), _createKeyReference());

		secret.destroy();
		secret.destroy();

		Assert.assertTrue(secret.isDestroyed());
	}

	@Test
	public void testGetCharsCachesResult() {
		Secret secret = new Secret(
			_createKeyReference(), RandomTestUtil.randomString());

		char[] chars1 = secret.getChars();
		char[] chars2 = secret.getChars();

		Assert.assertSame(chars1, chars2);
	}

	@Test
	public void testGetCharsFromBytes() {
		String data = RandomTestUtil.randomString();

		Secret secret = new Secret(
			data.getBytes(StandardCharsets.UTF_8), _createKeyReference());

		Assert.assertArrayEquals(data.toCharArray(), secret.getChars());
	}

	@Test
	public void testGetThrowsAfterDestroy() {
		Secret secret = new Secret(
			RandomTestUtil.randomBytes(), _createKeyReference());

		secret.close();

		Assert.assertThrows(IllegalStateException.class, secret::getBytes);
		Assert.assertThrows(IllegalStateException.class, secret::getChars);
	}

	@Test
	public void testRejectsInvalidUTF8WhenDecoding() {
		Secret secret = new Secret(
			new byte[] {(byte)0xC0, (byte)0xC0}, _createKeyReference());

		Assert.assertThrows(IllegalArgumentException.class, secret::getChars);
	}

	@Test
	public void testRejectsLoneSurrogateChar() {
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> new Secret(
				_createKeyReference(), new String(new char[] {'\uD800'})));
	}

	@Test
	public void testRejectsNullKeyReference() {
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> new Secret(null, RandomTestUtil.randomString()));
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> new Secret(RandomTestUtil.randomBytes(), null));
	}

	@Test
	public void testSecretFromString() {
		String data = RandomTestUtil.randomString();

		Secret secret = new Secret(_createKeyReference(), data);

		Assert.assertArrayEquals(data.toCharArray(), secret.getChars());
		Assert.assertTrue(secret.getBytes().length > 0);
	}

	@Test
	public void testSecretImmutable() {
		byte[] data = RandomTestUtil.randomBytes();

		byte originalFirstByte = data[0];
		Secret secret = new Secret(data, _createKeyReference());

		// Constructor must copy the input

		data[0] = (byte)~originalFirstByte;

		Assert.assertEquals(originalFirstByte, secret.getBytes()[0]);
	}

	@Test
	public void testSecretReturnsSameInstance() {
		Secret secret = new Secret(
			RandomTestUtil.randomBytes(), _createKeyReference());

		byte[] internalBytes1 = secret.getBytes();
		byte[] internalBytes2 = secret.getBytes();

		Assert.assertSame(internalBytes1, internalBytes2);
	}

	@Test
	public void testSecretZeroing() {
		byte[] data = RandomTestUtil.randomBytes();

		Secret secret = new Secret(data, _createKeyReference());

		byte[] internalBytes = secret.getBytes();

		Assert.assertArrayEquals(data, internalBytes);

		secret.close();

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