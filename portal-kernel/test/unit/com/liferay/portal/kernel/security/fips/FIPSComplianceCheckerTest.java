/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.security.Provider;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Caio Farias
 */
public class FIPSComplianceCheckerTest {

	@Test
	public void testFIPSCompliance() {
		_assertCheckFails("No security providers are registered");

		_assertCheckFails(
			"FIPS provider is not supported",
			_createProvider(RandomTestUtil.randomString()));

		Map<?, ?> fipsSecurityProvidersMap;

		try {
			fipsSecurityProvidersMap =
				(Map<?, ?>)ReflectionUtil.getDeclaredField(
					FIPSComplianceChecker.class, "_fipsSecurityProvidersMap"
				).get(
					null
				);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		for (Object fipsProviderName : fipsSecurityProvidersMap.keySet()) {
			_assertCheckFails(
				"Unapproved security providers registered in FIPS mode",
				_createProvider((String)fipsProviderName),
				_createProvider(RandomTestUtil.randomString()));
		}

		SecurityException securityException =
			_invokeAndCaptureSecurityException(
				"_checkFIPSProviderIntegrity", Provider.class,
				_createProvider(RandomTestUtil.randomString()));

		Assert.assertTrue(
			securityException.getMessage(),
			securityException.getMessage(
			).contains(
				"No integrity check implemented for:"
			));

		UnsafeRunnable<Throwable> unsafeRunnable = () -> {
			throw new RuntimeException(RandomTestUtil.randomString());
		};

		securityException = _invokeAndCaptureSecurityException(
			"_executeFIPSProviderIntegrityCheck", UnsafeRunnable.class,
			unsafeRunnable);

		Assert.assertTrue(
			securityException.getMessage(),
			securityException.getMessage(
			).contains(
				"FIPS provider integrity failure:"
			));
	}

	private void _assertCheckFails(
		String expectedMessage, Provider... providers) {

		SecurityException securityException =
			_invokeAndCaptureSecurityException(
				"_check", Provider[].class, providers);

		Assert.assertTrue(
			securityException.getMessage(),
			securityException.getMessage(
			).contains(
				expectedMessage
			));
	}

	private Provider _createProvider(String name) {
		return new Provider(name, "1.0", "") {
		};
	}

	private SecurityException _invokeAndCaptureSecurityException(
		String methodName, Class<?> parameterType, Object argument) {

		try {
			ReflectionUtil.getDeclaredMethod(
				FIPSComplianceChecker.class, methodName, parameterType
			).invoke(
				null, argument
			);
		}
		catch (Exception exception) {
			Throwable throwable = exception.getCause();

			if (throwable instanceof SecurityException) {
				return (SecurityException)throwable;
			}

			throw new RuntimeException(exception);
		}

		throw new AssertionError("SecurityException expected");
	}

}