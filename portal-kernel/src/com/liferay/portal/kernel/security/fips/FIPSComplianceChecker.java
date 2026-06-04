/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.security.Provider;
import java.security.Security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Caio Farias
 */
public class FIPSComplianceChecker {

	public static void check() {
		Provider[] providers = Security.getProviders();

		_checkProviders(providers);

		_checkFIPSProvider(providers[0]);

		if (_log.isInfoEnabled()) {
			_log.info("FIPS check has passed");
		}
	}

	private static void _checkFIPSProvider(Provider provider) {
		String providerName = provider.getName();

		if (!_allowedProviders.containsKey(providerName)) {
			throw new SecurityException(
				"Invalid FIPS provider: " + providerName);
		}

		try {
			if (providerName.equals("BCFIPS")) {
				ClassLoader classLoader = provider.getClass().getClassLoader();

				Class<?> fipsStatusClass = Class.forName(
					"org.bouncycastle.crypto.fips.FipsStatus", true,
					classLoader);

				Method isReadyMethod = ReflectionUtil.getDeclaredMethod(
					fipsStatusClass, "isReady");

				if (!GetterUtil.getBoolean(isReadyMethod.invoke(null))) {
					Method getStatusMessageMethod =
						ReflectionUtil.getDeclaredMethod(
							fipsStatusClass, "getStatusMessage");

					throw new SecurityException(
						"BCFIPS integrity check failed: " +
							getStatusMessageMethod.invoke(null));
				}

				Class<?> cryptoServicesRegistrarClass = Class.forName(
					"org.bouncycastle.crypto.CryptoServicesRegistrar", true,
					classLoader);

				Method isInApprovedOnlyModeMethod =
					ReflectionUtil.getDeclaredMethod(
						cryptoServicesRegistrarClass, "isInApprovedOnlyMode");

				if (!GetterUtil.getBoolean(
						isInApprovedOnlyModeMethod.invoke(null))) {

					throw new SecurityException(
						"BCFIPS is not in approved-only mode");
				}
			}
			else if (providerName.equals("AmazonCorrettoCryptoProvider")) {
				Class<?> amazonCorrettoCryptoProviderClass =
					provider.getClass();

				Field instanceField = ReflectionUtil.getDeclaredField(
					amazonCorrettoCryptoProviderClass, "INSTANCE");

				Method getLoadingErrorMethod = ReflectionUtil.getDeclaredMethod(
					amazonCorrettoCryptoProviderClass, "getLoadingError");

				Object instance = instanceField.get(null);

				if (instance == null) {
					throw new SecurityException(
						"AmazonCorrettoCryptoProvider INSTANCE is null");
				}

				Throwable loadingErrorThrowable =
					(Throwable)getLoadingErrorMethod.invoke(instance);

				if (loadingErrorThrowable != null) {
					throw new SecurityException(
						StringBundler.concat(
							"AmazonCorrettoCryptoProvider integrity check ",
							"failed: ", loadingErrorThrowable.getMessage()),
						loadingErrorThrowable);
				}

				Method isFipsMethod = ReflectionUtil.getDeclaredMethod(
					amazonCorrettoCryptoProviderClass, "isFips");

				if (!GetterUtil.getBoolean(isFipsMethod.invoke(instance))) {
					throw new SecurityException(
						"AmazonCorrettoCryptoProvider is not a FIPS build");
				}

				Method isExperimentalFipsMethod =
					ReflectionUtil.getDeclaredMethod(
						amazonCorrettoCryptoProviderClass,
						"isExperimentalFips");

				if (GetterUtil.getBoolean(
						isExperimentalFipsMethod.invoke(instance))) {

					throw new SecurityException(
						"AmazonCorrettoCryptoProvider is an experimental " +
							"FIPS build");
				}

				Method runSelfTestsMethod = ReflectionUtil.getDeclaredMethod(
					amazonCorrettoCryptoProviderClass, "runSelfTests");

				Object result = runSelfTestsMethod.invoke(instance);

				if (!Objects.equals(String.valueOf(result), "PASSED")) {
					throw new SecurityException(
						StringBundler.concat(
							"AmazonCorrettoCryptoProvider integrity check ",
							"failed: ", result));
				}
			}
		}
		catch (SecurityException securityException) {
			throw securityException;
		}
		catch (Throwable throwable) {
			Throwable causeThrowable = throwable.getCause();

			if (causeThrowable == null) {
				causeThrowable = throwable;
			}

			throw new SecurityException(
				"FIPS provider integrity failed: " +
					causeThrowable.getMessage(),
				causeThrowable);
		}
	}

	private static void _checkProviders(Provider[] providers) {
		if (ArrayUtil.isEmpty(providers)) {
			throw new SecurityException("There are no providers registered");
		}

		Provider firstProvider = providers[0];

		if (!_allowedProviders.containsKey(firstProvider.getName())) {
			throw new SecurityException(
				"The first provider must be an allowed FIPS provider");
		}

		List<String> allowedProviders = _allowedProviders.get(
			firstProvider.getName());

		Provider[] notAllowedProviders = ArrayUtil.filter(
			providers,
			provider -> !allowedProviders.contains(provider.getName()));

		if (ArrayUtil.isEmpty(notAllowedProviders)) {
			if (_log.isInfoEnabled()) {
				_log.info("All registered providers are allowed for FIPS mode");
			}

			return;
		}

		throw new SecurityException(
			StringBundler.concat(
				"The providers ", Arrays.toString(notAllowedProviders),
				" are not allowed in FIPS mode for ", firstProvider.getName()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FIPSComplianceChecker.class);

	private static final Map<String, List<String>> _allowedProviders = Map.of(
		"AmazonCorrettoCryptoProvider",
		List.of(
			"AmazonCorrettoCryptoProvider", "SUN", "SunRsaSign", "SunEC",
			"SunJSSE", "SunJCE", "SunJGSS", "SunSASL", "XMLDSig", "JdkLDAP",
			"JdkSASL"),
		"BCFIPS",
		List.of(
			"BCFIPS", "BCJSSE", "SUN", "SunJCE", "XMLDSig", "SunJGSS",
			"SunSASL", "JdkLDAP", "JdkSASL"));

}