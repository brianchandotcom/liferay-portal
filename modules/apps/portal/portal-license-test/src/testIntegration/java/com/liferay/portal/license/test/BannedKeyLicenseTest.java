/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
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
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() {
		Class<?> clazz = getValidateClass();

		for (Method method : clazz.getDeclaredMethods()) {
			Class<?>[] parameterTypes = method.getParameterTypes();

			if ((parameterTypes.length == 1) &&
				Map.class.isAssignableFrom(parameterTypes[0])) {

				method.setAccessible(true);

				_encryptMethod = method;

				break;
			}
		}

		for (Field field : clazz.getDeclaredFields()) {
			if (Set.class.isAssignableFrom(field.getType())) {
				field.setAccessible(true);

				_bannedKeysField = field;

				break;
			}
		}
	}

	@Test
	public void testBannedKeys() throws Exception {
		String[] bannedKeys = ArrayUtil.toStringArray(
			(Set<String>)_bannedKeysField.get(getValidateClass()));

		Assert.assertEquals(bannedKeys.toString(), 80, bannedKeys.length);

		String bannedKey = bannedKeys[bannedKeys.length - 1];

		try (SafeCloseable safeCloseable1 = setVersionWithSafeCloseable(
				"2026.Q1.0 LTS");
			SafeCloseable safeCloseable2 = resetLicenseDataWithSafeCloseble();
			SafeCloseable safeCloseable3 = setReturnValueWithSafeCloseable(
				_encryptMethod, bannedKey)) {

			deployFreeTierPortalLicense(
				RandomTestUtil.randomString(), bannedKey, Time.HOUR);

			Assert.fail(
				"Unable to see error message \'Corrupt license file. License " +
					"was not registered\'");
		}
		catch (LogEntriesException logEntriesException) {
			List<LogEntry> logEntries = logEntriesException.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message = logEntry.getMessage();

			Assert.assertTrue(
				message,
				message.startsWith(
					"Corrupt license file. License was not registered"));
		}
	}

	@Test
	public void testNonbannedKeys() throws Exception {
		String domain = RandomTestUtil.randomString();
		String key = RandomTestUtil.randomString(8);

		Set<String> bnnedKeys = (Set<String>)_bannedKeysField.get(
			getValidateClass());

		Assert.assertFalse(bnnedKeys.toString(), bnnedKeys.contains(key));

		try (SafeCloseable safeCloseable1 = setVersionWithSafeCloseable(
				"2026.Q1.0 LTS");
			SafeCloseable safeCloseable2 = resetLicenseDataWithSafeCloseble()) {

			deployFreeTierPortalLicense(domain, key, Time.HOUR);

			Assert.fail(
				"Unable to see error message \'Corrupt license file. License " +
					"was not registered\'");
		}
		catch (LogEntriesException logEntriesException) {
			List<LogEntry> logEntries = logEntriesException.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message = logEntry.getMessage();

			Assert.assertTrue(
				message,
				message.startsWith(
					"Corrupt license file. License was not registered"));
		}

		try (SafeCloseable safeCloseable1 = setVersionWithSafeCloseable(
				"2026.Q1.0 LTS");
			SafeCloseable safeCloseable2 = resetLicenseDataWithSafeCloseble();
			SafeCloseable safeCloseable3 = setReturnValueWithSafeCloseable(
				_encryptMethod, key)) {

			deployFreeTierPortalLicense(domain, key, Time.HOUR);

			assertPortalLicenseRegistered();
		}
	}

	private static Field _bannedKeysField;
	private static Method _encryptMethod;

}