/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
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

		try (SafeCloseable safeCloseable = setVersionWithSafeCloseable(
				"2026.Q1.0 LTS")) {

			String domain = RandomTestUtil.randomString();

			String key = _getLicenseKey(domain, startTimeMillis);

			deployFreeTierPortalLicense(
				domain, key, startTimeMillis, Time.HOUR);

			assertPortalLicenseRegistered();

			resetLicenseData();

			try (AutoCloseable autoCloseable = _setBannedKeys(
					SetUtil.fromArray(key));
				LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					getLicenseManagerClassName(), LoggerTestUtil.ERROR)) {

				deployFreeTierPortalLicense(
					RandomTestUtil.randomString(), key, startTimeMillis,
					Time.HOUR);

				assertPortalLicenseNotRegistered();

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 1, logEntries.size());

				LogEntry logEntry = logEntries.get(0);

				String message = logEntry.getMessage();

				Assert.assertTrue(
					message.contains(
						"Corrupt license file. License was not registered"));
			}
		}
	}

	private String _getLicenseKey(String domain, long startTimeMillis)
		throws Exception {

		try (SafeCloseable safeCloseable = disableValidateWithSafeCloseable()) {
			deployFreeTierPortalLicense(
				domain, StringPool.BLANK, startTimeMillis, Time.HOUR);

			return encryptLicenseProperties(
				LicenseManagerUtil.getLicenseProperties(getPortalProductId()));
		}
		finally {
			resetLicenseData();
		}
	}

	private AutoCloseable _setBannedKeys(Set<String> keys) throws Exception {
		String bannedKeysFieldString = getProperty("banned.keys.field");

		String bannedFieldClassName = bannedKeysFieldString.substring(
			0, bannedKeysFieldString.lastIndexOf(CharPool.PERIOD));
		String bannedFieldFieldName = bannedKeysFieldString.substring(
			bannedKeysFieldString.lastIndexOf(CharPool.PERIOD) + 1);

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		return ReflectionTestUtil.setFieldValueWithAutoCloseable(
			classLoader.loadClass(bannedFieldClassName), bannedFieldFieldName,
			keys);
	}

}