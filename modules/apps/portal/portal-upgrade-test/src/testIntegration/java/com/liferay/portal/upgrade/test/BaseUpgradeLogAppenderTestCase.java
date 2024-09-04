/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.SimpleMessage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Sam Ziemer
 */
public abstract class BaseUpgradeLogAppenderTestCase {

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

		_restoreRelease();
	}

	@Before
	public void setUp() throws Exception {
		PatternLayout.Builder builder = PatternLayout.newBuilder();

		builder.withPattern("%level - %m%n %X");

		_logContextAppender = WriterAppender.createAppender(
			builder.build(), null, _unsyncStringWriter,
			"logContextWriterAppender", false, false);

		_logContextAppender.start();

		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_newRelease", true);

		_updatePortalRelease(
			new Version(1, 0, 0), ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER);

		_upgradeReportLogger = (Logger)LogManager.getLogger(
			"com.liferay.portal.upgrade.internal.report.UpgradeReport");

		_upgradeReportLogger.addAppender(_logContextAppender);
	}

	@After
	public void tearDown() throws Exception {
		_appender.stop();

		File reportsDir = null;

		if (_upgradeReportDir.isEmpty()) {
			reportsDir = new File(getFilePath(), "reports");
		}
		else {
			reportsDir = new File(_upgradeReportDir);

			_upgradeReportDir = "";
		}

		if (reportsDir.exists()) {
			File reportFile = new File(reportsDir, "upgrade_report.info");

			if (reportFile.exists()) {
				reportFile.delete();
			}

			reportsDir.delete();
		}

		_upgradeReportLogger.removeAppender(_logContextAppender);

		_logContextAppender.stop();
	}

	@Test
	public void testDatabaseTablesAreSorted() throws Exception {
		_appender.start();

		_appender.stop();

		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		_assertTablesAreSortedByInitialRows(
			_logContextTablesInitialFinalRowsPattern.matcher(
				_getLogContextValue(
					"upgrade.report.tables.initial.final.rows")));
		_assertTablesAreSortedByInitialRows(_pattern.matcher(_reportContent));
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

			int initialTableCount = GetterUtil.getInteger(matcher.group(2), -1);
			int finalTableCount = GetterUtil.getInteger(matcher.group(3), -1);

			if (StringUtil.equalsIgnoreCase(tableName, "UpgradeReportTable1")) {
				table1Exists = true;

				Assert.assertEquals(0, initialTableCount);
				Assert.assertEquals(1, finalTableCount);
			}
			else if (StringUtil.equalsIgnoreCase(
						tableName, "UpgradeReportTable2")) {

				table2Exists = true;

				Assert.assertEquals(1, initialTableCount);
				Assert.assertEquals(0, finalTableCount);
			}
			else {
				Assert.assertTrue(
					(initialTableCount > 0) || (finalTableCount > 0));
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
	public void testDatabaseTablesEmpty() throws Exception {
		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_newRelease", false);

		_appender.start();

		_appender.stop();

		Assert.assertFalse(
			StringUtil.contains(
				_getReportContent(), "Table Name", StringPool.BLANK));
	}

	@Test
	public void testFailedSQLStatements() throws Exception {
		_appender.start();

		UpgradeProcess upgradeProcess = UpgradeProcessFactory.runSQL(
			"update NonexistingTable");

		try {
			upgradeProcess.upgrade();
		}
		catch (UpgradeException upgradeException) {
		}

		_appender.stop();

		_assertLogContextContains(
			"upgrade.report.failed.sqls", "SQL: update NonexistingTable;");
		_assertReport("SQL: update NonexistingTable;");
	}

	@Test
	public void testGetDLStorageSizeAfterTimeout() throws Exception {
		_appender.start();

		try (SafeCloseable safeCloseable =
				_setUpgradeReportDLStorageSizeTimeout(1)) {

			Object upgradeReport = ReflectionTestUtil.getFieldValue(
				_appender, "_upgradeReport");

			ReflectionTestUtil.setFieldValue(
				upgradeReport, "_dlSizeThread",
				new Thread() {

					@Override
					public void run() {
						try {
							sleep(5 * Time.SECOND);
						}
						catch (InterruptedException interruptedException) {
							throw new RuntimeException(interruptedException);
						}
					}

				});

			_appender.stop();

			Assert.assertTrue(
				_getLogContent().contains(
					"INFO - Unable to determine the document library size. " +
						"Increase the timeout or check it manually."));
			_assertLogContextContains(
				"upgrade.report.document.library.storage.size",
				"Unable to determine");
			_assertReport("Document library storage size: Unable to determine");
		}
	}

	@Test
	public void testGetDLStorageSizeDisabled() throws Exception {
		_appender.start();

		try (SafeCloseable safeCloseable =
				_setUpgradeReportDLStorageSizeTimeout(0)) {

			_appender.stop();

			_assertLogContextContains(
				"upgrade.report.document.library.storage.size", "Disabled");
			_assertReport("Document library storage size: Disabled");
		}
	}

	@Test
	public void testGetDLStorageSizeInGb() throws Exception {
		_appender.start();

		Object upgradeReport = ReflectionTestUtil.getFieldValue(
			_appender, "_upgradeReport");

		ReflectionTestUtil.setFieldValue(
			upgradeReport, "_dlSizeThread",
			new Thread() {

				@Override
				public void run() {
					ReflectionTestUtil.setFieldValue(
						upgradeReport, "_dlSize", 1073742000);
				}

			});

		_appender.stop();

		String size = LanguageUtil.formatStorageSize(1073742000, LocaleUtil.US);

		_assertLogContextContains(
			"upgrade.report.document.library.storage.size", size);
		_assertReport("Document library storage size: " + size);
	}

	@Test
	public void testGetDLStorageSizeInMb() throws Exception {
		_appender.start();

		Object upgradeReport = ReflectionTestUtil.getFieldValue(
			_appender, "_upgradeReport");

		ReflectionTestUtil.setFieldValue(
			upgradeReport, "_dlSizeThread",
			new Thread() {

				@Override
				public void run() {
					ReflectionTestUtil.setFieldValue(
						upgradeReport, "_dlSize", 1048576);
				}

			});

		_appender.stop();

		String size = LanguageUtil.formatStorageSize(1048576, LocaleUtil.US);

		_assertLogContextContains(
			"upgrade.report.document.library.storage.size", size);
		_assertReport("Document library storage size: " + size);
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

		String longestUpgradeProcessesValue = _getLogContextValue(
			"upgrade.report.longest.upgrade.processes");

		Assert.assertTrue(
			longestUpgradeProcessesValue.indexOf(slowerUpgradeProcessName) <
				longestUpgradeProcessesValue.indexOf(fasterUpgradeProcessName));
	}

	@Test
	public void testLogEvents() throws Exception {
		_appender.start();

		LogEvent logEvent = Log4jLogEvent.newBuilder(
		).setLoggerName(
			"Warn"
		).setLevel(
			Level.WARN
		).setMessage(
			new SimpleMessage("Warning")
		).build();

		_appender.append(logEvent);
		_appender.append(logEvent);

		Log log = LogFactoryUtil.getLog(UpgradeProcess.class);

		log.info(
			"Completed upgrade process com.liferay.portal.UpgradeTest in " +
				"20401 ms");

		_appender.stop();

		_assertLogContextContains(
			"upgrade.report.longest.upgrade.processes",
			"com.liferay.portal.UpgradeTest:20401 ms");
		_assertLogContextContains("upgrade.report.warnings", "2:Warning");
		_assertReport("2 occurrences of the following event: Warning");
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

		_assertLogContextContains(
			"upgrade.report.status",
			StringBundler.concat(
				"There are upgrade processes available for ",
				bundleSymbolicName, " from 0.0.1 to ", currentSchemaVersion));
		_assertReport(
			StringBundler.concat(
				"There are upgrade processes available for ",
				bundleSymbolicName, " from 0.0.1 to ", currentSchemaVersion));
	}

	@Test
	public void testNoLogEvents() throws Exception {
		_appender.start();

		_appender.stop();

		_assertLogContextContains("upgrade.report.errors", "[]");
		_assertLogContextContains(
			"upgrade.report.longest.upgrade.processes", "[]");
		_assertLogContextContains("upgrade.report.warnings", "[]");
		_assertReport("Errors: Nothing registered");
		_assertReport("Longest upgrade processes: Nothing registered");
		_assertReport("Warnings: Nothing registered");
	}

	@Test
	public void testNoUpgrade() throws Exception {
		_restoreRelease();

		_appender.start();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.upgrade.internal.report.UpgradeReport",
				LoggerTestUtil.INFO)) {

			_appender.stop();

			Assert.assertTrue(
				StringUtil.contains(
					String.valueOf(logCapture.getLogEntries()),
					"Upgrade report was not generated because no upgrade " +
						"processes were executed",
					StringPool.BLANK));
		}

		File file = new File(
			new File(getFilePath(), "reports"), "upgrade_report.info");

		Assert.assertTrue(!file.exists());
	}

	@Test
	public void testProperties() throws Exception {
		_appender.start();

		_appender.stop();

		_assertLogContextContains(
			"upgrade.report.property." + PropsKeys.DL_STORE_IMPL,
			PropsValues.DL_STORE_IMPL);
		_assertLogContextContains(
			"upgrade.report.property.liferay.home", PropsValues.LIFERAY_HOME);
		_assertLogContextContains(
			"upgrade.report.property.locales",
			Arrays.toString(PropsValues.LOCALES));
		_assertLogContextContains(
			"upgrade.report.property.locales.enabled",
			Arrays.toString(PropsValues.LOCALES_ENABLED));
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
	}

	@Test
	public void testPropertiesSetByUserWithEnvVariable() throws Exception {
		_setEnv(
			"LIFERAY_MY_PERIOD_ENVIRONMENT_PERIOD_PROPERTY",
			"my environment property value");

		_appender.start();

		_appender.stop();

		_assertLogContextContains(
			"upgrade.report.properties.set.by.user",
			"Properties set with environment variables");
		_assertLogContextContains(
			"upgrade.report.properties.set.by.user",
			"my.environment.property: my environment property value");
		_assertReport("Properties set with environment variables");
		_assertReport("my.environment.property: my environment property value");
	}

	@Test
	public void testPropertiesSetByUserWithFile() throws Exception {
		File propertiesFile = temporaryFolder.newFile("test.properties");

		String[] originalIncludeAndOverride = PropsUtil.getArray(
			"include-and-override");

		String[] includeAndOverride = ArrayUtil.append(
			originalIncludeAndOverride, propertiesFile.getAbsolutePath());

		PropsUtil.set(
			"include-and-override", StringUtil.merge(includeAndOverride));

		Properties properties = new Properties();

		properties.setProperty("my.property", "my property value");

		try (Writer writer = new FileWriter(propertiesFile)) {
			properties.store(writer, null);

			_appender.start();

			_appender.stop();

			_assertLogContextContains(
				"upgrade.report.properties.set.by.user",
				propertiesFile.getAbsolutePath());
			_assertLogContextContains(
				"upgrade.report.properties.set.by.user",
				"my.property: my property value");
			_assertReport(propertiesFile.getAbsolutePath());
			_assertReport("my.property: my property value");
		}
		finally {
			PropsUtil.set(
				"include-and-override",
				StringUtil.merge(originalIncludeAndOverride));
		}
	}

	@Test
	public void testRenameUpgradeReport() throws Exception {
		_appender.start();

		_appender.stop();

		File reportFile1 = _getReportFile("upgrade_report.info");

		Assert.assertTrue(reportFile1.exists());

		long reportFile1LastModified = reportFile1.lastModified();

		_appender.start();

		_appender.stop();

		File reportFile2 = _getReportFile("upgrade_report.info");

		Assert.assertTrue(
			_getReportFile(
				"upgrade_report.info." + reportFile1LastModified
			).exists());
		Assert.assertTrue(reportFile2.exists());
		Assert.assertTrue(
			reportFile2.lastModified() != reportFile1LastModified);
	}

	@Test
	public void testSchemaVersion() throws Exception {
		_appender.start();

		_restoreRelease();

		_appender.stop();

		_assertLogContextContains(
			"upgrade.report.portal.expected.build.number",
			String.valueOf(ReleaseInfo.getBuildNumber()));

		Version latestSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		_assertLogContextContains(
			"upgrade.report.portal.expected.schema.version",
			latestSchemaVersion.toString());

		_assertLogContextContains(
			"upgrade.report.portal.final.build.number",
			String.valueOf(ReleaseInfo.getBuildNumber()));
		_assertLogContextContains(
			"upgrade.report.portal.final.schema.version",
			latestSchemaVersion.toString());
		_assertLogContextContains(
			"upgrade.report.portal.initial.build.number", "7100");
		_assertLogContextContains(
			"upgrade.report.portal.initial.schema.version", "1.0.0");
		_assertReport(
			StringBundler.concat(
				"Portal initial build number: 7100\n",
				"Portal initial schema version: 1.0.0\n",
				"Portal final build number: ", ReleaseInfo.getBuildNumber(),
				StringPool.NEW_LINE, "Portal final schema version: ",
				latestSchemaVersion, StringPool.NEW_LINE,
				"Portal expected build number: ", ReleaseInfo.getBuildNumber(),
				StringPool.NEW_LINE, "Portal expected schema version: ",
				latestSchemaVersion, StringPool.NEW_LINE));
	}

	@Test
	public void testSQLStatementsWithClassNameAndDuration() throws Exception {
		String statement1 = "insert into UpgradeReportTable1 (id_) values (2)";
		String statement2 = "delete from UpgradeReportTable1 where id_ = 2";

		UpgradeProcess upgradeProcess1 = UpgradeProcessFactory.runSQL(
			statement1);

		UpgradeProcess upgradeProcess2 = UpgradeProcessFactory.runSQL(
			statement2);

		String upgradeProcess1ClassName = upgradeProcess1.getClass(
		).getName();

		String upgradeProcess2ClassName = upgradeProcess2.getClass(
		).getName();

		_appender.start();

		upgradeProcess1.upgrade();

		upgradeProcess2.upgrade();

		_appender.stop();

		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		String recordEntry1 = String.format(
			"Upgrade Process: %s\nSQL: %s", upgradeProcess1ClassName,
			statement1);

		String recordEntry2 = String.format(
			"Upgrade Process: %s\nSQL: %s", upgradeProcess2ClassName,
			statement2);

		_assertReport(recordEntry1);

		_assertReport(recordEntry2);

		String logContextRecordEntry1 = String.format(
			"%s:%s", upgradeProcess1ClassName, statement1);

		String logContextRecordEntry2 = String.format(
			"%s:%s", upgradeProcess2ClassName, statement2);

		_assertLogContextContains(
			"upgrade.report.longest.running.sqls", logContextRecordEntry1);

		_assertLogContextContains(
			"upgrade.report.longest.running.sqls", logContextRecordEntry2);

		Map<String, Long> sqlExecutionTimes = ReflectionTestUtil.invoke(
			_upgradeRecorder, "getSQLExecutionTimes", new Class<?>[0]);

		for (Map.Entry<String, Long> entry : sqlExecutionTimes.entrySet()) {
			String sql = entry.getKey();
			Long duration = entry.getValue();

			String[] splitKey = sql.split("\\|");

			String storedUpgradeProcessClassName = splitKey[0];

			String storedSql = splitKey[1];

			String recordEntry = String.format(
				"Upgrade Process: %s\nSQL: %s\nDuration: %d ms",
				storedUpgradeProcessClassName, storedSql, duration);

			_assertReport(recordEntry);

			String logContextRecordEntry = String.format(
				"%s:%s:%d ms", storedUpgradeProcessClassName, storedSql,
				duration);

			_assertLogContextContains(
				"upgrade.report.longest.running.sqls", logContextRecordEntry);
		}
	}

	@Test
	public void testUpgradeReportDirectory() throws Exception {
		String originalUpgradeReportDir =
			ReflectionTestUtil.getAndSetFieldValue(
				PropsValues.class, "UPGRADE_REPORT_DIR", "./test_reports");

		try {
			_upgradeReportDir = PropsValues.UPGRADE_REPORT_DIR;

			_appender.start();

			LogEvent logEvent = Log4jLogEvent.newBuilder(
			).setLoggerName(
				"Warn"
			).setLevel(
				Level.WARN
			).setMessage(
				new SimpleMessage(
					"Upgrade report generated in " + _upgradeReportDir)
			).build();

			_appender.append(logEvent);

			_appender.stop();

			_assertReport("Upgrade report generated in " + _upgradeReportDir);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "UPGRADE_REPORT_DIR",
				originalUpgradeReportDir);
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected static void setUpClass(boolean upgradeClient) throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table UpgradeReportTable1 (id_ LONG not null primary key)");
		_db.runSQL(
			"create table UpgradeReportTable2 (id_ LONG not null primary key)");

		_originalNewRelease = ReflectionTestUtil.getFieldValue(
			StartupHelperUtil.class, "_newRelease");

		_originalUpgradeClient = ReflectionTestUtil.getAndSetFieldValue(
			DBUpgrader.class, "_upgradeClient", upgradeClient);

		_originalUpgradeLogContextEnabled =
			ReflectionTestUtil.getAndSetFieldValue(
				PropsValues.class, "UPGRADE_LOG_CONTEXT_ENABLED", true);
	}

	protected abstract String getFilePath();

	private static void _restoreRelease() throws Exception {
		ReflectionTestUtil.setFieldValue(
			StartupHelperUtil.class, "_newRelease", _originalNewRelease);

		_updatePortalRelease(
			PortalUpgradeProcess.getLatestSchemaVersion(),
			ReleaseInfo.getBuildNumber());
	}

	private static void _updatePortalRelease(
			Version schemaVersion, int buildNumber)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Release_ set schemaVersion = ?, buildNumber = ? " +
					"where releaseId = ?")) {

			preparedStatement.setString(1, schemaVersion.toString());
			preparedStatement.setInt(2, buildNumber);
			preparedStatement.setLong(3, ReleaseConstants.DEFAULT_ID);

			preparedStatement.executeUpdate();
		}

		DCLSingleton<?> dclSingleton = ReflectionTestUtil.getFieldValue(
			PortalUpgradeProcess.class, "_currentPortalReleaseDTODCLSingleton");

		dclSingleton.destroy(null);
	}

	private void _assertLogContextContains(String key, String text) {
		Assert.assertTrue(
			StringUtil.containsIgnoreCase(
				_getLogContextValue(key), text, StringPool.BLANK));
	}

	private void _assertReport(String testString) throws Exception {
		if (_reportContent == null) {
			_reportContent = _getReportContent();
		}

		Assert.assertTrue(
			StringUtil.contains(_reportContent, testString, StringPool.BLANK));
	}

	private void _assertTablesAreSortedByInitialRows(Matcher matcher) {
		int previousInitialTableCount = Integer.MAX_VALUE;
		String previousTableName = null;

		while (matcher.find()) {
			int initialTableCount = GetterUtil.getInteger(matcher.group(2), -1);

			String tableName = matcher.group(1);

			if (initialTableCount == previousInitialTableCount) {
				Assert.assertTrue(previousTableName.compareTo(tableName) < 0);
			}
			else {
				Assert.assertTrue(
					initialTableCount < previousInitialTableCount);
			}

			previousInitialTableCount = initialTableCount;
			previousTableName = tableName;
		}
	}

	private String _getLogContent() {
		return _unsyncStringWriter.toString();
	}

	private String _getLogContextValue(String key) {
		File file = new File(
			new File(getFilePath(), "reports"), "upgrade_report.info");

		Pattern pattern = Pattern.compile(
			"(?s)INFO - Upgrade report generated in " +
				Pattern.quote(file.getAbsolutePath()) + "\\s*\\{(.+?)\\}");

		int index = _getLogContent().indexOf(
			"INFO - Upgrade report generated in " + file.getAbsolutePath());

		String substringLogContextContent = _getLogContent().substring(index);

		Matcher matcher = pattern.matcher(substringLogContextContent);

		Assert.assertTrue(matcher.matches());

		Map<String, String> logContextValues = _getLogContextValues(
			matcher.group(1));

		Assert.assertTrue(logContextValues.containsKey(key));

		return logContextValues.get(key);
	}

	private Map<String, String> _getLogContextValues(String logContextContent) {
		HashMap<String, String> values = new HashMap<>();

		String[] keys = logContextContent.split(",\\s*(?=upgrade.report.)");

		for (String key : keys) {
			String[] keyParts = key.split("=", 2);

			values.put(keyParts[0], keyParts[1]);
		}

		return values;
	}

	private String _getReportContent() throws Exception {
		File reportFile = _getReportFile("upgrade_report.info");

		Assert.assertTrue(reportFile.exists());

		return FileUtil.read(reportFile);
	}

	private File _getReportFile(String fileName) throws Exception {
		File reportsDir = null;

		if (Validator.isBlank(_upgradeReportDir)) {
			reportsDir = new File(getFilePath(), "reports");
		}
		else {
			reportsDir = new File(_upgradeReportDir);
		}

		Assert.assertTrue(reportsDir.exists());

		return new File(reportsDir, fileName);
	}

	private void _setEnv(String key, String value) throws Exception {
		Map<String, String> env = System.getenv();

		Class<?> clazz = env.getClass();

		Field field = clazz.getDeclaredField("m");

		field.setAccessible(true);

		@SuppressWarnings("unchecked")
		Map<String, String> writableEnv = (Map<String, String>)field.get(env);

		writableEnv.put(key, value);
	}

	private SafeCloseable _setUpgradeReportDLStorageSizeTimeout(long timeout) {
		long originalUpgradeReportDLStorageSizeTimeout =
			ReflectionTestUtil.getAndSetFieldValue(
				PropsValues.class, "UPGRADE_REPORT_DL_STORAGE_SIZE_TIMEOUT",
				timeout);

		return () -> ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, "UPGRADE_REPORT_DL_STORAGE_SIZE_TIMEOUT",
			originalUpgradeReportDLStorageSizeTimeout);
	}

	private static DB _db;
	private static Appender _logContextAppender;
	private static final Pattern _logContextTablesInitialFinalRowsPattern =
		Pattern.compile("(\\w+_?):(\\d+|-):(\\d+|-)");
	private static boolean _originalNewRelease;
	private static boolean _originalUpgradeClient;
	private static boolean _originalUpgradeLogContextEnabled;
	private static final Pattern _pattern = Pattern.compile(
		"(\\w+_?)\\s+(\\d+|-)\\s+(\\d+|-)\n");

	@Inject(
		filter = "component.name=com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder",
		type = Inject.NoType.class
	)
	private static Object _upgradeRecorder;

	private static Logger _upgradeReportLogger;

	@Inject(filter = "appender.name=UpgradeLogAppender")
	private Appender _appender;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	private String _reportContent;
	private final UnsyncStringWriter _unsyncStringWriter =
		new UnsyncStringWriter();
	private String _upgradeReportDir = "";

}