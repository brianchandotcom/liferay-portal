/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Objects;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class FreeTierVersionsLicenseTest extends BaseLicenseTestCase {

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
		_disableKeyValidatorResettableClassFileTransformer = disableValidate();
	}

	@AfterClass
	public static void tearDownClass() {
		resetClassFileTransformer(
			_disableKeyValidatorResettableClassFileTransformer);
	}

	@Test
	public void testIgnoredVersion() throws Exception {
		ResettableClassFileTransformer resettableClassFileTransformer =
			setVersion(_getIgnoredVersion());

		try {
			assertLicensePropertiesNotExisted(getPortalProductId());

			assertPortalLicenseNotRegistered();

			deployFreeTierPortalLicense(Time.HOUR);

			assertLicensePropertiesExisted(getPortalProductId());

			assertPortalLicenseRegistered();
		}
		finally {
			resetLicenseData();

			resetLifecycleAction();

			resetClassFileTransformer(resettableClassFileTransformer);
		}
	}

	@Test
	public void testNonignoredVersions() throws Exception {
		_testNonignoredVersions(true);
		_testNonignoredVersions(false);
	}

	private String _generateRandomVersion(boolean ltsVersion) {
		int year = RandomTestUtil.randomInt(1000, 9999);
		int patchVersison = RandomTestUtil.randomInt(1, 9999);

		if (ltsVersion) {
			return StringBundler.concat(year, ".Q1.", patchVersison, " LTS");
		}

		return StringBundler.concat(
			year, ".Q", RandomTestUtil.randomInt(1, 4), ".", patchVersison);
	}

	private String _getIgnoredVersion() throws Exception {
		Field field = getIgnoredVersionField();

		return (String)field.get(null);
	}

	private String _getLicensePackageName() {
		Field field = getIgnoredVersionField();

		Class<?> clazz = field.getDeclaringClass();

		String className = clazz.getName();

		int index = className.lastIndexOf(StringPool.PERIOD);

		String packageName = className.substring(0, index);

		index = packageName.lastIndexOf(StringPool.PERIOD);

		return packageName.substring(0, index);
	}

	private void _testNonignoredVersions(boolean ltsVersion) throws Exception {
		String ignoredVersion = _getIgnoredVersion();

		String nonignoredRandomVersion = null;

		while (true) {
			nonignoredRandomVersion = _generateRandomVersion(ltsVersion);

			if (!Objects.equals(ignoredVersion, nonignoredRandomVersion)) {
				break;
			}
		}

		ResettableClassFileTransformer resettableClassFileTransformer =
			setVersion(nonignoredRandomVersion);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_getLicensePackageName(), LoggerTestUtil.ERROR)) {

			assertLicensePropertiesNotExisted(getPortalProductId());

			assertPortalLicenseNotRegistered();

			deployFreeTierPortalLicense(Time.HOUR);

			logCapture.getLogEntries();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(LoggerTestUtil.ERROR, logEntry.getPriority());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertEquals(
				"License is not suppported in " + nonignoredRandomVersion,
				throwable.getMessage());

			assertLicensePropertiesExisted(getPortalProductId());

			assertPortalLicenseInvalid();
		}
		finally {
			resetLicenseData();

			resetLifecycleAction();

			resetClassFileTransformer(resettableClassFileTransformer);
		}
	}

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;

}