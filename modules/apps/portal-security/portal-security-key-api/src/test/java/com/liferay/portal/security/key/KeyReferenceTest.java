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

		KeyReference keyReference1 = new KeyReference(
			identifier, providerId, KeyReference.Type.CRYPTO);

		Assert.assertNotEquals(keyReference1, RandomTestUtil.randomString());

		KeyReference keyReference2 = new KeyReference(
			identifier, providerId, KeyReference.Type.CRYPTO);

		Assert.assertEquals(keyReference1, keyReference2);
		Assert.assertEquals(keyReference1.hashCode(), keyReference2.hashCode());

		KeyReference keyReference3 = new KeyReference(
			identifier, providerId, KeyReference.Type.SECRET);
		KeyReference keyReference4 = new KeyReference(
			identifier, RandomTestUtil.randomString(),
			KeyReference.Type.CRYPTO);

		Assert.assertNotEquals(keyReference1, keyReference3);
		Assert.assertNotEquals(keyReference1, keyReference4);
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