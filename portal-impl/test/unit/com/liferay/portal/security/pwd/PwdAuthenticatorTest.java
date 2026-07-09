/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.pwd;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lucas Miranda
 */
public class PwdAuthenticatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAuthenticate() throws Exception {
		try (MockedStatic<PasswordEncryptorUtil> mockedStatic =
				Mockito.mockStatic(PasswordEncryptorUtil.class)) {

			mockedStatic.when(
				() -> PasswordEncryptorUtil.encrypt(Mockito.anyString())
			).thenReturn(
				"encrypted"
			);

			mockedStatic.when(
				() -> PasswordEncryptorUtil.encrypt(
					Mockito.anyString(), Mockito.anyString())
			).thenReturn(
				"encrypted"
			);

			PropsUtil.set(PropsKeys.AUTH_MAC_ALLOW, "true");

			try (SafeCloseable safeCloseable =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"FIPS_ENABLED", false)) {

				PropsUtil.set(PropsKeys.AUTH_MAC_ALGORITHM, "MD5");

				PwdAuthenticator.authenticate(
					"login", "password", "currentEncryptedPassword");
			}

			try (SafeCloseable safeCloseable =
					PropsValuesTestUtil.swapWithSafeCloseable(
						"FIPS_ENABLED", true)) {

				PropsUtil.set(PropsKeys.AUTH_MAC_ALGORITHM, "MD5");

				Assert.assertThrows(
					SecurityException.class,
					() -> PwdAuthenticator.authenticate(
						"login", "password", "currentEncryptedPassword"));

				PropsUtil.set(PropsKeys.AUTH_MAC_ALGORITHM, "SHA-256");

				PwdAuthenticator.authenticate(
					"login", "password", "currentEncryptedPassword");
			}
		}
	}

}