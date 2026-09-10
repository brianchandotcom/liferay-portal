/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.encryptor;

import com.liferay.portal.kernel.exception.CompanyKeyResolutionException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.security.Key;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Christopher Kian
 */
public class CompanyKeyUtilTest {

	@Test
	public void testDeserializeKey() throws Exception {
		Key key = Mockito.mock(Key.class);

		String serializedKey = RandomTestUtil.randomString();

		Encryptor encryptor = Mockito.mock(Encryptor.class);

		Mockito.when(
			encryptor.deserializeKey(serializedKey)
		).thenReturn(
			key
		);

		try (AutoCloseable autoCloseable1 = _setEncryptor(encryptor);
			AutoCloseable autoCloseable2 = _setCompanyKeyResolver(null)) {

			Assert.assertSame(
				key, CompanyKeyUtil.deserializeKey(_COMPANY_ID, serializedKey));
		}

		String wrappedKey =
			CompanyKeyUtil.WRAPPED_KEY_PREFIX + RandomTestUtil.randomString();

		try (AutoCloseable autoCloseable = _setCompanyKeyResolver(null)) {
			CompanyKeyUtil.deserializeKey(_COMPANY_ID, wrappedKey);

			Assert.fail();
		}
		catch (CompanyKeyResolutionException companyKeyResolutionException) {
		}

		CompanyKeyResolver companyKeyResolver = Mockito.mock(
			CompanyKeyResolver.class);

		Mockito.when(
			companyKeyResolver.deserializeKey(_COMPANY_ID, wrappedKey)
		).thenReturn(
			key
		);

		try (AutoCloseable autoCloseable = _setCompanyKeyResolver(
				companyKeyResolver)) {

			Assert.assertSame(
				key, CompanyKeyUtil.deserializeKey(_COMPANY_ID, wrappedKey));
		}
	}

	@Test
	public void testIsWrappedKey() {
		Assert.assertFalse(CompanyKeyUtil.isWrappedKey(null));
		Assert.assertFalse(
			CompanyKeyUtil.isWrappedKey(RandomTestUtil.randomString()));
		Assert.assertTrue(
			CompanyKeyUtil.isWrappedKey(CompanyKeyUtil.WRAPPED_KEY_PREFIX));
	}

	@Test
	public void testSerializeKey() throws Exception {
		Key key = Mockito.mock(Key.class);

		String serializedKey = RandomTestUtil.randomString();

		Encryptor encryptor = Mockito.mock(Encryptor.class);

		Mockito.when(
			encryptor.serializeKey(key)
		).thenReturn(
			serializedKey
		);

		try (AutoCloseable autoCloseable1 = _setEncryptor(encryptor);
			AutoCloseable autoCloseable2 = _setCompanyKeyResolver(null)) {

			Assert.assertEquals(
				serializedKey, CompanyKeyUtil.serializeKey(_COMPANY_ID, key));
		}

		CompanyKeyResolver companyKeyResolver = Mockito.mock(
			CompanyKeyResolver.class);

		Mockito.when(
			companyKeyResolver.isEnabled(_COMPANY_ID)
		).thenReturn(
			false
		);

		try (AutoCloseable autoCloseable1 = _setEncryptor(encryptor);
			AutoCloseable autoCloseable2 = _setCompanyKeyResolver(
				companyKeyResolver)) {

			Assert.assertEquals(
				serializedKey, CompanyKeyUtil.serializeKey(_COMPANY_ID, key));
		}

		String wrappedKey =
			CompanyKeyUtil.WRAPPED_KEY_PREFIX + RandomTestUtil.randomString();

		Mockito.when(
			companyKeyResolver.isEnabled(_COMPANY_ID)
		).thenReturn(
			true
		);

		Mockito.when(
			companyKeyResolver.serializeKey(_COMPANY_ID, key)
		).thenReturn(
			wrappedKey
		);

		try (AutoCloseable autoCloseable = _setCompanyKeyResolver(
				companyKeyResolver)) {

			Assert.assertEquals(
				wrappedKey, CompanyKeyUtil.serializeKey(_COMPANY_ID, key));
		}
	}

	private AutoCloseable _setCompanyKeyResolver(
		CompanyKeyResolver companyKeyResolver) {

		Snapshot<CompanyKeyResolver> snapshot = Mockito.mock(Snapshot.class);

		Mockito.when(
			snapshot.get()
		).thenReturn(
			companyKeyResolver
		);

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			CompanyKeyUtil.class, "_companyKeyResolverSnapshot", snapshot);
	}

	private AutoCloseable _setEncryptor(Encryptor encryptor) {
		Snapshot<Encryptor> snapshot = Mockito.mock(Snapshot.class);

		Mockito.when(
			snapshot.get()
		).thenReturn(
			encryptor
		);

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			EncryptorUtil.class, "_encryptorSnapshot", snapshot);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

}