/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.security.Provider;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

/**
 * @author Caio Farias
 */
public class FIPSComplianceCheckerTest {

	@Test
	public void testCheckFIPSProvider() {
		_assertSecurityException(
			"Invalid FIPS provider:",
			() -> ReflectionTestUtil.invoke(
				FIPSComplianceChecker.class, "_checkFIPSProvider",
				new Class<?>[] {Provider.class},
				_createProvider(RandomTestUtil.randomString())));

		_assertSecurityException(
			"FIPS provider integrity failed:",
			() -> ReflectionTestUtil.invoke(
				FIPSComplianceChecker.class, "_checkFIPSProvider",
				new Class<?>[] {Provider.class}, _createProvider("BCFIPS")));
	}

	@Test
	public void testCheckProviders() {
		_assertSecurityException(
			"There are no providers registered",
			() -> ReflectionTestUtil.invoke(
				FIPSComplianceChecker.class, "_checkProviders",
				new Class<?>[] {Provider[].class}, (Object)new Provider[0]));

		_assertSecurityException(
			"The first provider must be an allowed FIPS provider",
			() -> ReflectionTestUtil.invoke(
				FIPSComplianceChecker.class, "_checkProviders",
				new Class<?>[] {Provider[].class},
				(Object)new Provider[] {
					_createProvider(RandomTestUtil.randomString())
				}));

		Map<String, List<String>> allowedProviders =
			ReflectionTestUtil.getFieldValue(
				FIPSComplianceChecker.class, "_allowedProviders");

		for (String allowedProvider : allowedProviders.keySet()) {
			_assertSecurityException(
				"are not allowed in FIPS mode for",
				() -> ReflectionTestUtil.invoke(
					FIPSComplianceChecker.class, "_checkProviders",
					new Class<?>[] {Provider[].class},
					(Object)new Provider[] {
						_createProvider(allowedProvider),
						_createProvider(RandomTestUtil.randomString())
					}));
		}
	}

	private void _assertSecurityException(
		String expectedMessage, ThrowingRunnable throwingRunnable) {

		SecurityException securityException = Assert.assertThrows(
			SecurityException.class, throwingRunnable);

		String message = securityException.getMessage();

		Assert.assertTrue(message, message.contains(expectedMessage));
	}

	private Provider _createProvider(String name) {
		return new Provider(name, "1.0", "") {
		};
	}

}