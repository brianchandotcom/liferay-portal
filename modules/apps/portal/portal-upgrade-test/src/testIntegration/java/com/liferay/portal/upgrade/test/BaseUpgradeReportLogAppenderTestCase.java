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

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sam Ziemer
 */
public abstract class BaseUpgradeReportLogAppenderTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(UpgradeReportTable1)");
		_db.runSQL("DROP_TABLE_IF_EXISTS(UpgradeReportTable2)");

		ReflectionTestUtil.setFieldValue(
			DBUpgrader.class, "_upgradeClient", _originalUpgradeClient);

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_LOG_CONTEXT_ENABLED",
			_originalUpgradeLogContextEnabled);
	}

	@Before
	public void setUp() {
		PatternLayout.Builder builder = PatternLayout.newBuilder();

		builder.withPattern("%level - %m%n %X");

		_unsyncStringWriter = new UnsyncStringWriter();

		_logContextAppender = WriterAppender.createAppender(
			builder.build(), null, _unsyncStringWriter,
			"logContextWriterAppender", false, false);

		_logContextAppender.start();

		_upgradeReportLogger = (Logger)LogManager.getLogger(
			"com.liferay.portal.upgrade.internal.report.UpgradeReport");

		_upgradeReportLogger.addAppender(_logContextAppender);
	}

	@After
	public void tearDown() {
		_appender.stop();

		_reportContent = null;

		File reportsDir = new File(getFilePath(), "reports");

		if ((reportsDir != null) && reportsDir.exists()) {
			File reportFile = new File(reportsDir, "upgrade_report.info");

			if (reportFile.exists()) {
				reportFile.delete();
			}

			reportsDir.delete();
		}

		_upgradeReportLogger.removeAppender(_logContextAppender);

		_logContextAppender.stop();

		_logContextAppender = null;
	}

	@Test
	public void testDatabaseTablesAreSorted() throws Exception {
		_appender.start();

		_appender.stop();

		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		Matcher matcher = _pattern.matcher(_reportContent);

		_assertDatabaseTablesAreSorted(matcher);

		String databaseTables = _getLogContextKey(
			"upgrade.report.tables.initial.final.rows");

		matcher = _logContextDatabaseTablePattern.matcher(databaseTables);

		_assertDatabaseTablesAreSorted(matcher);
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

		_assertLogContextContains(
			"upgrade.report.tables.initial.final.rows",
			"UpgradeReportTable1:0:1");
		_assertLogContextContains(
			"upgrade.report.tables.initial.final.rows",
			"UpgradeReportTable2:1:0");
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

		String upgradeProcesses = _getLogContextKey(
			"upgrade.report.longest.upgrade.processes");

		Assert.assertTrue(
			upgradeProcesses.indexOf(slowerUpgradeProcessName) <
				upgradeProcesses.indexOf(fasterUpgradeProcessName));
	}

	@Test
	public void testLogEvents() throws Exception {
		_appender.start();

		Log log = LogFactoryUtil.getLog(
			BaseUpgradeReportLogAppenderTestCase.class);

		log.warn("Warning");
		log.warn("Warning");

		log = LogFactoryUtil.getLog(UpgradeProcess.class);

		log.info(
			"Completed upgrade process com.liferay.portal.UpgradeTest in " +
				"20401 ms");

		_appender.stop();

		_assertReport("2 occurrences of the following event: Warning");
		_assertReport(
			"com.liferay.portal.UpgradeTest took 20401 ms to complete");

		_assertLogContextContains("upgrade.report.warnings", "2:Warning");
		_assertLogContextContains(
			"upgrade.report.longest.upgrade.processes",
			"com.liferay.portal.UpgradeTest:20401 ms");
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

		_assertLogContextContains(
			"upgrade.report.osgi.status",
			StringBundler.concat(
				"There are upgrade processes available for ",
				bundleSymbolicName, " from 0.0.1 to ", currentSchemaVersion));
	}

	@Test
	public void testNoLogEvents() throws Exception {
		_appender.start();

		_appender.stop();

		_assertReport("Errors: Nothing registered");
		_assertReport("Longest upgrade processes: Nothing registered");
		_assertReport("Warnings: Nothing registered");

		_assertLogContextContains("upgrade.report.warnings", "[]");
		_assertLogContextContains("upgrade.report.errors", "[]");
		_assertLogContextContains(
			"upgrade.report.longest.upgrade.processes", "[]");
	}

	@Test
	public void testProperties() throws Exception {
		_appender.start();

		_appender.stop();

		_assertReport(
			StringBundler.concat(
				"Property liferay.home: ", PropsValues.LIFERAY_HOME,
				StringPool.NEW_LINE, "Property locales: ",
				Arrays.toString(PropsValues.LOCALES), StringPool.NEW_LINE,
				"Property locales.enabled: ",
				Arrays.toString(PropsValues.LOCALES_ENABLED),
				StringPool.NEW_LINE, "Property ", PropsKeys.DL_STORE_IMPL,
				StringPool.COLON, StringPool.SPACE, PropsValues.DL_STORE_IMPL,
				StringPool.NEW_LINE));

		_assertLogContextContains(
			"upgrade.report.property.liferay.home", PropsValues.LIFERAY_HOME);
		_assertLogContextContains(
			"upgrade.report.property.locales",
			Arrays.toString(PropsValues.LOCALES));
		_assertLogContextContains(
			"upgrade.report.property.locales.enabled",
			Arrays.toString(PropsValues.LOCALES_ENABLED));
		_assertLogContextContains(
			"upgrade.report.property." + PropsKeys.DL_STORE_IMPL,
			PropsValues.DL_STORE_IMPL);
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
				"Portal initial build number: 7100\n",
				"Portal initial schema version: 1.0.0\n",
				"Portal final build number: ", ReleaseInfo.getBuildNumber(),
				StringPool.NEW_LINE, "Portal final schema version: ",
				latestSchemaVersion.toString(), StringPool.NEW_LINE,
				"Portal expected build number: ", ReleaseInfo.getBuildNumber(),
				StringPool.NEW_LINE, "Portal expected schema version: ",
				latestSchemaVersion.toString(), StringPool.NEW_LINE));

		_assertLogContextContains(
			"upgrade.report.portal.initial.build.number", "7100");
		_assertLogContextContains(
			"upgrade.report.portal.initial.schema.version", "1.0.0");
		_assertLogContextContains(
			"upgrade.report.portal.final.build.number",
			String.valueOf(ReleaseInfo.getBuildNumber()));
		_assertLogContextContains(
			"upgrade.report.portal.final.schema.version",
			latestSchemaVersion.toString());
		_assertLogContextContains(
			"upgrade.report.portal.expected.build.number",
			String.valueOf(ReleaseInfo.getBuildNumber()));
		_assertLogContextContains(
			"upgrade.report.portal.expected.schema.version",
			latestSchemaVersion.toString());
	}

	protected static void setUpClass(boolean upgradeClient) throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table UpgradeReportTable1 (id_ LONG not null primary key)");
		_db.runSQL(
			"create table UpgradeReportTable2 (id_ LONG not null primary key)");

		_originalUpgradeClient = ReflectionTestUtil.getFieldValue(
			DBUpgrader.class, "_upgradeClient");

		ReflectionTestUtil.setFieldValue(
			DBUpgrader.class, "_upgradeClient", upgradeClient);

		_originalUpgradeLogContextEnabled = ReflectionTestUtil.getFieldValue(
			PropsValues.class, "UPGRADE_LOG_CONTEXT_ENABLED");

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_LOG_CONTEXT_ENABLED", true);
	}

	protected abstract String getFilePath();

	private void _assertDatabaseTablesAreSorted(Matcher matcher) {
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

	private void _assertLogContextContains(String key, String testString) {
		String values = _getLogContextKey(key);

		Assert.assertTrue(
			StringUtil.containsIgnoreCase(
				values, testString, StringPool.BLANK));
	}

	private void _assertReport(String testString) throws Exception {
		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		Assert.assertTrue(
			StringUtil.contains(_reportContent, testString, StringPool.BLANK));
	}

	private String _getLogContextContent() {
		return _unsyncStringWriter.toString();
	}

	private String _getLogContextKey(String key) {
		String logContext = _getLogContextContent();

		Pattern pattern = Pattern.compile(
			"(?s)INFO - Upgrade report generated in " +
				_getReportFileAbsolutePath() + "\\n\\s+\\{(.+)\\}");

		Matcher logContextMatcher = pattern.matcher(logContext);

		Assert.assertTrue(logContextMatcher.matches());

		Map<String, String> logContextValues = _getLogContextValues(
			logContextMatcher.group(1));

		Assert.assertTrue(logContextValues.containsKey(key));

		return logContextValues.get(key);
	}

	private Map<String, String> _getLogContextValues(String logContext) {
		HashMap<String, String> values = new HashMap<>();

		String[] keys = logContext.split(",\\s*(?=upgrade.report.)");

		for (String key : keys) {
			String[] parts = key.split("=", 2);

			values.put(parts[0], parts[1]);
		}

		return values;
	}

	private String _getReportContent() throws Exception {
		File reportsDir = new File(getFilePath(), "reports");

		Assert.assertTrue(reportsDir.exists());

		File reportFile = new File(reportsDir, "upgrade_report.info");

		Assert.assertTrue(reportFile.exists());

		return FileUtil.read(reportFile);
	}

	private String _getReportFileAbsolutePath() {
		File folder = new File(getFilePath(), "reports");

		File report = new File(folder, "upgrade_report.info");

		return report.getAbsolutePath();
	}

	private static DB _db;
	private static Appender _logContextAppender;
	private static final Pattern _logContextDatabaseTablePattern =
		Pattern.compile("(\\w+_?):(\\d+|-):(\\d+|-)");
	private static boolean _originalUpgradeClient;
	private static boolean _originalUpgradeLogContextEnabled;
	private static final Pattern _pattern = Pattern.compile(
		"(\\w+_?)\\s+(\\d+|-)\\s+(\\d+|-)\n");
	private static UnsyncStringWriter _unsyncStringWriter;
	private static Logger _upgradeReportLogger;

	@Inject(filter = "appender.name=UpgradeReportLogAppender")
	private Appender _appender;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private String _reportContent;

}