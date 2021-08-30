/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.Arrays;

import org.apache.logging.log4j.core.Appender;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sam Ziemer
 */
@RunWith(Arquillian.class)
public class UpgradeReportLogAppenderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		File reportsDir = new File(".", "reports");

		if ((reportsDir != null) && reportsDir.exists()) {
			File reportFile = new File(reportsDir, "upgrade_report.info");

			if (reportFile.exists()) {
				reportFile.delete();
			}

			reportsDir.delete();
		}
	}

	@Test
	public void testLogEvents() throws Exception {
		_appender.start();

		Log log = LogFactoryUtil.getLog(UpgradeReportLogAppenderTest.class);

		log.warn("Warning");
		log.warn("Warning");

		log = LogFactoryUtil.getLog(UpgradeProcess.class);

		log.info(
			"Completed upgrade process " +
				"com.liferay.portal.upgrade.PortalUpgradeProcess in 20401 ms");

		_appender.stop();

		_testReport("2 occurrences of the following warnings: Warning");

		_testReport(
			"com.liferay.portal.upgrade.PortalUpgradeProcess took 20401 ms " +
				"to complete");
	}

	@Test
	public void testNoLogEvents() throws Exception {
		_appender.start();

		_appender.stop();

		_testReport("Unable to get upgrade process times");

		_testReport("No errors thrown during upgrade.");

		_testReport("No warnings thrown during upgrade.");
	}

	@Test
	public void testProperties() throws Exception {
		StringBuffer sb = new StringBuffer(15);

		sb.append(PropsKeys.LIFERAY_HOME);
		sb.append(StringPool.EQUAL);
		sb.append(PropsValues.LIFERAY_HOME);
		sb.append(StringPool.NEW_LINE);
		sb.append(PropsKeys.LOCALES);
		sb.append(StringPool.EQUAL);
		sb.append(Arrays.toString(PropsValues.LOCALES));
		sb.append(StringPool.NEW_LINE);
		sb.append(PropsKeys.LOCALES_ENABLED);
		sb.append(StringPool.EQUAL);
		sb.append(Arrays.toString(PropsValues.LOCALES_ENABLED));
		sb.append(StringPool.NEW_LINE);
		sb.append(PropsKeys.DL_STORE_IMPL);
		sb.append(StringPool.EQUAL);
		sb.append(PropsValues.DL_STORE_IMPL);

		_appender.start();

		_appender.stop();

		_testReport(sb.toString());
	}

	@Test
	public void testSchemaVersion() throws Exception {
		Release release = _releaseLocalService.getRelease(1);

		int initialBuildNumber = release.getBuildNumber();
		String initialSchemaVersion = release.getSchemaVersion();

		release = _releaseLocalService.getRelease(1);

		release.setBuildNumber(ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER);
		release.setSchemaVersion("1.0.0");

		_releaseLocalService.updateRelease(release);

		_appender.start();

		release = _releaseLocalService.getRelease(1);

		release.setBuildNumber(initialBuildNumber);
		release.setSchemaVersion(initialSchemaVersion);

		_releaseLocalService.updateRelease(release);

		_appender.stop();

		Version latestSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		String testString = StringBundler.concat(
			"Initial portal build number: 7100\n",
			"Initial portal schema version: 1.0.0\n",
			"Final portal build number: ", ReleaseInfo.getBuildNumber(),
			StringPool.NEW_LINE, "Final portal schema version: ",
			latestSchemaVersion.toString(), StringPool.NEW_LINE,
			"Expected portal build number: ", ReleaseInfo.getBuildNumber(),
			StringPool.NEW_LINE, "Expected portal schema version: ",
			latestSchemaVersion.toString(), StringPool.NEW_LINE);

		_testReport(testString);
	}

	private void _testReport(String testString) throws Exception {
		File reportsDir = new File(".", "reports");

		Assert.assertTrue(reportsDir.exists());

		File reportFile = new File(reportsDir, "upgrade_report.info");

		Assert.assertTrue(reportFile.exists());

		String report = FileUtil.read(reportFile);

		Assert.assertTrue(
			StringUtil.contains(report, testString, StringPool.BLANK));
	}

	@Inject
	private Appender _appender;

	@Inject
	private ReleaseLocalService _releaseLocalService;

}