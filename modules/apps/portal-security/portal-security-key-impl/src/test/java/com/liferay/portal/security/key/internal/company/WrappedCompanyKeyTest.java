/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.company;

import com.liferay.portal.kernel.encryptor.CompanyKeyUtil;
import com.liferay.portal.kernel.exception.CompanyKeyResolutionException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christopher Kian
 */
public class WrappedCompanyKeyTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConstructor() {
		_testConstructor(
			_CIPHERTEXT, "alias/ke|k", RandomTestUtil.randomString());
		_testConstructor(
			_CIPHERTEXT, "alias/ke}k", RandomTestUtil.randomString());
		_testConstructor(_CIPHERTEXT, null, RandomTestUtil.randomString());
		_testConstructor(
			_CIPHERTEXT, RandomTestUtil.randomString(), "provider:id");
		_testConstructor(
			_CIPHERTEXT, RandomTestUtil.randomString(), "provider|id");
		_testConstructor(
			_CIPHERTEXT, RandomTestUtil.randomString(), "provider}id");
		_testConstructor(_CIPHERTEXT, RandomTestUtil.randomString(), null);
		_testConstructor(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Test
	public void testParse() {
		String identifier = "arn:aws:kms:us-east-1:123456789012:key/abc";
		String providerId = RandomTestUtil.randomString();

		WrappedCompanyKey wrappedCompanyKey = new WrappedCompanyKey(
			_CIPHERTEXT, identifier, providerId);

		WrappedCompanyKey parsedWrappedCompanyKey = WrappedCompanyKey.parse(
			_COMPANY_ID, wrappedCompanyKey.serialize());

		Assert.assertArrayEquals(
			_CIPHERTEXT, parsedWrappedCompanyKey.getCiphertext());
		Assert.assertEquals(
			identifier, parsedWrappedCompanyKey.getIdentifier());
		Assert.assertEquals(
			providerId, parsedWrappedCompanyKey.getProviderId());

		_testParse(_VERSION_PREFIX);
		_testParse(_VERSION_PREFIX + ":alias/kek|Y2lwaGVy}");
		_testParse(_VERSION_PREFIX + "provider:alias/kek|Y2lwaGVy");
		_testParse(_VERSION_PREFIX + "provider:alias/kek|}");
		_testParse(_VERSION_PREFIX + "provider:alias/kek|=}");
		_testParse(_VERSION_PREFIX + "provider:alias/kek|not base64}");
		_testParse(_VERSION_PREFIX + "provider:alias/kek}");
		_testParse(_VERSION_PREFIX + "provider:|Y2lwaGVy}");
		_testParse(CompanyKeyUtil.WRAPPED_KEY_PREFIX);
		_testParse(CompanyKeyUtil.WRAPPED_KEY_PREFIX + "}");
		_testParse(
			CompanyKeyUtil.WRAPPED_KEY_PREFIX +
				"v2:provider:alias/kek|Y2lwaGVy}");
		_testParse(RandomTestUtil.randomString());
	}

	@Test
	public void testSerialize() {
		String identifier = RandomTestUtil.randomString();
		String providerId = RandomTestUtil.randomString();

		WrappedCompanyKey wrappedCompanyKey = new WrappedCompanyKey(
			_CIPHERTEXT, identifier, providerId);

		String serializedKey = wrappedCompanyKey.serialize();

		Assert.assertTrue(CompanyKeyUtil.isWrappedKey(serializedKey));

		WrappedCompanyKey parsedWrappedCompanyKey = WrappedCompanyKey.parse(
			_COMPANY_ID, serializedKey);

		Assert.assertArrayEquals(
			_CIPHERTEXT, parsedWrappedCompanyKey.getCiphertext());
		Assert.assertEquals(
			identifier, parsedWrappedCompanyKey.getIdentifier());
		Assert.assertEquals(
			providerId, parsedWrappedCompanyKey.getProviderId());
		Assert.assertEquals(serializedKey, parsedWrappedCompanyKey.serialize());
	}

	private void _testConstructor(
		byte[] ciphertext, String identifier, String providerId) {

		try {
			new WrappedCompanyKey(ciphertext, identifier, providerId);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	private void _testParse(String serializedKey) {
		try {
			WrappedCompanyKey.parse(_COMPANY_ID, serializedKey);

			Assert.fail();
		}
		catch (CompanyKeyResolutionException companyKeyResolutionException) {
		}
	}

	private static final byte[] _CIPHERTEXT = RandomTestUtil.randomBytes();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _VERSION_PREFIX =
		CompanyKeyUtil.WRAPPED_KEY_PREFIX + CompanyKeyUtil.WRAPPED_KEY_VERSION +
			":";

}