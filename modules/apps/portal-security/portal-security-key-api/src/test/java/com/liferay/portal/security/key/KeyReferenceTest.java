/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public class KeyReferenceTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorRejectsClosingCurlyBraceInProviderId() {
		new KeyReference(
			RandomTestUtil.randomString(), "pro}vider",
			KeyReference.Type.CRYPTO);
	}

	@Test
	public void testEqualsAndHashCode() {
		String identifier = RandomTestUtil.randomString();
		String providerId = RandomTestUtil.randomString();

		KeyReference keyReference1 = KeyReference.fromString(
			StringBundler.concat(
				"${keyRef:", providerId, ":", identifier, "}"));

		Assert.assertNotEquals(RandomTestUtil.randomString(), keyReference1);

		KeyReference keyReference2 = KeyReference.fromString(
			StringBundler.concat(
				"${keyRef:", providerId, ":", identifier, "}"));

		Assert.assertEquals(keyReference1, keyReference2);
		Assert.assertEquals(keyReference1.hashCode(), keyReference2.hashCode());

		KeyReference keyReference3 = KeyReference.fromString(
			StringBundler.concat(
				"${secretRef:", providerId, ":", identifier, "}"));
		KeyReference keyReference4 = KeyReference.fromString(
			StringBundler.concat(
				"${keyRef:", RandomTestUtil.randomString(), ":", identifier,
				"}"));

		Assert.assertNotEquals(keyReference1, keyReference3);
		Assert.assertNotEquals(keyReference1, keyReference4);
	}

	@Test
	public void testFromStringAnyProvider() {
		String keyReferenceString = "${secretRef:*:my-secret}";

		KeyReference keyReference = KeyReference.fromString(keyReferenceString);

		Assert.assertEquals("my-secret", keyReference.getIdentifier());
		Assert.assertEquals(
			KeyReference.ANY_PROVIDER, keyReference.getProviderId());
	}

	@Test
	public void testFromStringCrypto() {
		String identifier = RandomTestUtil.randomString();
		String providerId = RandomTestUtil.randomString();

		KeyReference keyReference = KeyReference.fromString(
			StringBundler.concat(
				"${keyRef:", providerId, ":", identifier, "}"));

		Assert.assertEquals(identifier, keyReference.getIdentifier());
		Assert.assertEquals(providerId, keyReference.getProviderId());
		Assert.assertEquals(KeyReference.Type.CRYPTO, keyReference.getType());

		// Identifier may contain colons — the third capture group is greedy

		keyReference = KeyReference.fromString("${keyRef:gcp:my.key:v1}");

		Assert.assertEquals("my.key:v1", keyReference.getIdentifier());
		Assert.assertEquals("gcp", keyReference.getProviderId());
	}

	@Test
	public void testFromStringInvalid() {
		Assert.assertNull(KeyReference.fromString("${keyRef: :id}"));
		Assert.assertNull(KeyReference.fromString("${keyRef:onlyone}"));
		Assert.assertNull(KeyReference.fromString("${keyRef:pro}vider:id}"));
		Assert.assertNull(KeyReference.fromString("${unknownRef:provider:id}"));
		Assert.assertNull(
			KeyReference.fromString(RandomTestUtil.randomString()));
		Assert.assertNull(KeyReference.fromString(null));
	}

	@Test
	public void testFromStringSecret() {
		String identifier = RandomTestUtil.randomString();
		String providerId = RandomTestUtil.randomString();

		KeyReference keyReference = KeyReference.fromString(
			StringBundler.concat(
				"${secretRef:", providerId, ":", identifier, "}"));

		Assert.assertEquals(identifier, keyReference.getIdentifier());
		Assert.assertEquals(providerId, keyReference.getProviderId());
		Assert.assertEquals(KeyReference.Type.SECRET, keyReference.getType());
	}

	@Test
	public void testIsKeyReference() {
		Assert.assertFalse(
			KeyReference.isKeyReference(RandomTestUtil.randomString()));
		Assert.assertFalse(KeyReference.isKeyReference(null));
		Assert.assertTrue(
			KeyReference.isKeyReference(
				StringBundler.concat(
					"${keyRef:", RandomTestUtil.randomString(), ":",
					RandomTestUtil.randomString(), "}")));
		Assert.assertTrue(
			KeyReference.isKeyReference(
				StringBundler.concat(
					"${secretRef:", RandomTestUtil.randomString(), ":",
					RandomTestUtil.randomString(), "}")));
	}

	@Test
	public void testToString() {
		String identifier = RandomTestUtil.randomString();
		String providerId = RandomTestUtil.randomString();

		KeyReference keyReference = new KeyReference(
			identifier, providerId, KeyReference.Type.CRYPTO);

		Assert.assertEquals(
			StringBundler.concat(
				"{identifier=", identifier, ", providerId=", providerId,
				", type=keyRef}"),
			keyReference.toString());
	}

}