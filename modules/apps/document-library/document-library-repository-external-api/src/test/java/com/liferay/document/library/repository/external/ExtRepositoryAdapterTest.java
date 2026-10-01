/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.SecretResolver;
import com.liferay.portal.security.key.secret.SecretResolverUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Farias
 */
public class ExtRepositoryAdapterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_dlRepositoryGuestPassword = GetterUtil.getString(
			PropsUtil.get(PropsKeys.DL_REPOSITORY_GUEST_PASSWORD));
		_secretResolverSnapshot = ReflectionTestUtil.getAndSetFieldValue(
			SecretResolverUtil.class, "_secretResolverSnapshot",
			new Snapshot<SecretResolver>(
				SecretResolverUtil.class, SecretResolver.class) {

				@Override
				public SecretResolver get() {
					return _secretResolver;
				}

			});
	}

	@After
	public void tearDown() {
		PropsUtil.set(
			PropsKeys.DL_REPOSITORY_GUEST_PASSWORD, _dlRepositoryGuestPassword);
		ReflectionTestUtil.setFieldValue(
			SecretResolverUtil.class, "_secretResolverSnapshot",
			_secretResolverSnapshot);
	}

	@Test
	public void testGetPassword() {
		String keyReferenceString = KeyReferenceUtil.toKeyReferenceString(
			new KeyReference(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				KeyReference.Type.SECRET));
		String password = RandomTestUtil.randomString();

		_secretResolver = Mockito.mock(SecretResolver.class);

		Mockito.when(
			_secretResolver.resolve(CompanyConstants.SYSTEM, keyReferenceString)
		).thenReturn(
			password
		);

		_testGetPassword(password, keyReferenceString);
		_testGetPassword(password, password);
	}

	private void _testGetPassword(String expectedPassword, String password) {
		PropsUtil.set(PropsKeys.DL_REPOSITORY_GUEST_PASSWORD, password);

		ExtRepositoryAdapter extRepositoryAdapter = new ExtRepositoryAdapter(
			Mockito.mock(ExtRepository.class));

		Assert.assertEquals(
			expectedPassword,
			ReflectionTestUtil.invoke(
				extRepositoryAdapter, "_getPassword", new Class<?>[0]));
	}

	private String _dlRepositoryGuestPassword;
	private SecretResolver _secretResolver;
	private Snapshot<SecretResolver> _secretResolverSnapshot;

}