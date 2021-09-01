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
		_appender.stop();

		_reportContent = null;

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
	public void testInfoEventsInOrder() throws Exception {
		_appender.start();

		Log log = LogFactoryUtil.getLog(UpgradeProcess.class);

		String fasterUpgradeProcessName =
			"com.liferay.portal.FasterUpgradeTest";

		log.info(
			"Completed upgrade process " + fasterUpgradeProcessName +
				" in 10 ms");

		String slowerUpgradeProcessName =
			"com.liferay.portal.SlowerUpgradeTest";

		log.info(
			"Completed upgrade process " + slowerUpgradeProcessName +
				" in 20401 ms");

		_appender.stop();

		String reportContent = _getReportContent();

		Assert.assertTrue(
			reportContent.indexOf(slowerUpgradeProcessName) <
				reportContent.indexOf(fasterUpgradeProcessName));
	}

	@Test
	public void testLogEvents() throws Exception {
		_appender.start();

		Log log = LogFactoryUtil.getLog(UpgradeReportLogAppenderTest.class);

		log.warn("Warning");
		log.warn("Warning");

		log = LogFactoryUtil.getLog(UpgradeProcess.class);

		log.info(
			"Completed upgrade process com.liferay.portal.UpgradeTest in " +
				"20401 ms");

		_appender.stop();

		_assertReport("2 occurrences of the following warnings: Warning");
		_assertReport(
			"com.liferay.portal.UpgradeTest took 20401 ms to complete");
	}

	@Test
	public void testModuleUpgrades() throws Exception {
		String bundleSymbolicName = "com.liferay.asset.service";

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		String initialSchemaVersion = release.getSchemaVersion();

		release.setSchemaVersion("0.0.1");

		_releaseLocalService.updateRelease(release);

		_appender.start();

		_appender.stop();

		release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		release.setSchemaVersion(initialSchemaVersion);

		_releaseLocalService.updateRelease(release);

		_assertReport(
			"There are upgrade processes available for " +
				"com.liferay.asset.service from 0.0.1 to 2.1.0");
	}

	@Test
	public void testNoLogEvents() throws Exception {
		_appender.start();

		_appender.stop();

		_assertReport("No errors thrown during upgrade");
		_assertReport("No upgrade processes registered");
		_assertReport("No warnings thrown during upgrade");
	}

	@Test
	public void testProperties() throws Exception {
		_appender.start();

		_appender.stop();

		_assertReport(
			StringBundler.concat(
				PropsKeys.LIFERAY_HOME, StringPool.EQUAL,
				PropsValues.LIFERAY_HOME, StringPool.NEW_LINE,
				PropsKeys.LOCALES, StringPool.EQUAL,
				Arrays.toString(PropsValues.LOCALES), StringPool.NEW_LINE,
				PropsKeys.LOCALES_ENABLED, StringPool.EQUAL,
				Arrays.toString(PropsValues.LOCALES_ENABLED),
				StringPool.NEW_LINE, PropsKeys.DL_STORE_IMPL, StringPool.EQUAL,
				PropsValues.DL_STORE_IMPL));
	}

	@Test
	public void testSchemaVersion() throws Exception {
		Release release = _releaseLocalService.getRelease(1);

		int initialBuildNumber = release.getBuildNumber();
		String initialSchemaVersion = release.getSchemaVersion();

		release = _releaseLocalService.getRelease(1);

		release.setSchemaVersion("1.0.0");
		release.setBuildNumber(ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER);

		_releaseLocalService.updateRelease(release);

		_appender.start();

		release = _releaseLocalService.getRelease(1);

		release.setSchemaVersion(initialSchemaVersion);
		release.setBuildNumber(initialBuildNumber);

		_releaseLocalService.updateRelease(release);

		_appender.stop();

		Version latestSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		_assertReport(
			StringBundler.concat(
				"Initial portal build number: 7100\n",
				"Initial portal schema version: 1.0.0\n",
				"Final portal build number: ", ReleaseInfo.getBuildNumber(),
				StringPool.NEW_LINE, "Final portal schema version: ",
				latestSchemaVersion.toString(), StringPool.NEW_LINE,
				"Expected portal build number: ", ReleaseInfo.getBuildNumber(),
				StringPool.NEW_LINE, "Expected portal schema version: ",
				latestSchemaVersion.toString(), StringPool.NEW_LINE));
	}

	private void _assertReport(String testString) throws Exception {
		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		Assert.assertTrue(
			StringUtil.contains(_reportContent, testString, StringPool.BLANK));
	}

	private String _getReportContent() throws Exception {
		File reportsDir = new File(".", "reports");

		Assert.assertTrue(reportsDir.exists());

		File reportFile = new File(reportsDir, "upgrade_report.info");

		Assert.assertTrue(reportFile.exists());

		return FileUtil.read(reportFile);
	}

	@Inject
	private Appender _appender;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private String _reportContent;

}