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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.Appender;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table UpgradeReportTable1 (id_ LONG not null primary key)");
		_db.runSQL(
			"create table UpgradeReportTable2 (id_ LONG not null primary key)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(UpgradeReportTable1)");
		_db.runSQL("DROP_TABLE_IF_EXISTS(UpgradeReportTable2)");
	}

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
	public void testDatabaseTablesCounts() throws Exception {
		_db.runSQL("insert into UpgradeReportTable2 (id_) values (1)");

		_appender.start();

		_db.runSQL("insert into UpgradeReportTable1 (id_) values (1)");

		_db.runSQL("delete from UpgradeReportTable2 where id_ = 1");

		_appender.stop();

		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		Matcher matcher = _pattern.matcher(_reportContent);

		boolean table1Exists = false;
		boolean table2Exists = false;

		while (matcher.find()) {
			String tableName = matcher.group(1);

			int initialCount = GetterUtil.getInteger(matcher.group(2), -1);
			int finalCount = GetterUtil.getInteger(matcher.group(3), -1);

			if (StringUtil.equalsIgnoreCase(tableName, "UpgradeReportTable1")) {
				table1Exists = true;

				Assert.assertEquals(0, initialCount);
				Assert.assertEquals(1, finalCount);
			}
			else if (StringUtil.equalsIgnoreCase(
						tableName, "UpgradeReportTable2")) {

				table2Exists = true;

				Assert.assertEquals(1, initialCount);
				Assert.assertEquals(0, finalCount);
			}
			else {
				Assert.assertTrue((initialCount > 0) || (finalCount > 0));
			}
		}

		Assert.assertTrue(table1Exists && table2Exists);
	}

	@Test
	public void testDatabaseTablesIsSorted() throws Exception {
		_appender.start();

		_appender.stop();

		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		Matcher matcher = _pattern.matcher(_reportContent);

		int previousInitialCount = Integer.MAX_VALUE;
		String previousTableName = null;

		while (matcher.find()) {
			int initialCount = GetterUtil.getInteger(matcher.group(2), -1);

			String tableName = matcher.group(1);

			if (initialCount == previousInitialCount) {
				Assert.assertTrue(previousTableName.compareTo(tableName) < 0);
			}
			else {
				Assert.assertTrue(initialCount < previousInitialCount);
			}

			previousInitialCount = initialCount;
			previousTableName = tableName;
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

		String currentSchemaVersion = release.getSchemaVersion();

		release.setSchemaVersion("0.0.1");

		_releaseLocalService.updateRelease(release);

		_appender.start();

		_appender.stop();

		release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		release.setSchemaVersion(currentSchemaVersion);

		_releaseLocalService.updateRelease(release);

		_assertReport(
			StringBundler.concat(
				"There are upgrade processes available for ",
				bundleSymbolicName, " from 0.0.1 to ", currentSchemaVersion));
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

	private static DB _db;
	private static final Pattern _pattern = Pattern.compile(
		"(\\w+_?)\\s+(\\d+|-)\\s+(\\d+|-)\n");

	@Inject
	private Appender _appender;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private String _reportContent;

}