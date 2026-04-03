/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class BannedKeyLicenseTest extends BaseLicenseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(isReleaseBundle());
	}

	@Test
	public void testLicenseValidateKey() throws Exception {
		long startTimeMillis = System.currentTimeMillis();

		try (SafeCloseable safeCloseable1 = setVersionWithSafeCloseable(
				"2026.Q1.0 LTS")) {

			String domain = RandomTestUtil.randomString();

			String key = _getLicenseKey(domain, startTimeMillis);

			try (AutoCloseable autoCloseable = _setBannedKeys(
					SetUtil.fromArray(key))) {

				deployFreeTierPortalLicense(
					domain, key, startTimeMillis, Time.HOUR);

				Assert.fail(
					"Unable to see error message \'Corrupt license file. " +
						"License was not registered\'");
			}
			catch (LogEntriesException logEntriesException) {
				List<LogEntry> logEntries = logEntriesException.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				String message = logEntry.getMessage();

				Assert.assertTrue(
					message,
					message.startsWith(
						"Corrupt license file. License was not registered"));
			}

			try (SafeCloseable safeCloseable =
					resetLicenseDataWithSafeCloseble()) {

				deployFreeTierPortalLicense(
					domain, key, startTimeMillis, Time.HOUR);

				assertPortalLicenseRegistered();
			}
		}
	}

	private String _getLicenseKey(String domain, long startTimeMillis)
		throws Exception {

		try (SafeCloseable safeCloseable1 = disableValidateWithSafeCloseable();
			SafeCloseable safeCloseable2 = resetLicenseDataWithSafeCloseble()) {

			deployFreeTierPortalLicense(
				domain, StringPool.BLANK, startTimeMillis, Time.HOUR);

			Class<?> clazz = getValidateClass();

			Method encryptMethod = null;

			for (Method method : clazz.getDeclaredMethods()) {
				Class<?>[] parameterTypes = method.getParameterTypes();

				if ((parameterTypes.length == 1) &&
					Map.class.isAssignableFrom(parameterTypes[0])) {

					method.setAccessible(true);

					encryptMethod = method;

					break;
				}
			}

			return (String)encryptMethod.invoke(
				null,
				LicenseManagerUtil.getLicenseProperties(getPortalProductId()));
		}
	}

	private AutoCloseable _setBannedKeys(Set<String> keys) {
		Class<?> clazz = getValidateClass();

		String bannedKeysFieldName = null;

		for (Field field : clazz.getDeclaredFields()) {
			if (Set.class.isAssignableFrom(field.getType())) {
				bannedKeysFieldName = field.getName();

				break;
			}
		}

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			clazz, bannedKeysFieldName, keys);
	}

}