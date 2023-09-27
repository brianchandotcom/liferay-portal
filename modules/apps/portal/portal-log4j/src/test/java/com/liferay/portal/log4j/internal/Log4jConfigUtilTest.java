/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.log4j.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogContextRegistryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Hai Yu
 */
public class Log4jConfigUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testCompanyWebIdLogContextDisabled() {
		Set<LogContext> logContexts = LogContextRegistryUtil.getLogContexts();

		Assert.assertTrue(logContexts.isEmpty());

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				StringUtil.randomString(), _ALL, _CONSOLE));

		Assert.assertTrue(logContexts.isEmpty());
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testCompanyWebIdLogContextEnabled() {
		Set<LogContext> logContexts = LogContextRegistryUtil.getLogContexts();

		Assert.assertTrue(logContexts.isEmpty());

		Log4jConfigUtil.configureLog4J(
			_generateCompanyWebIdLogContextConfigurationContent());

		Assert.assertFalse(logContexts.isEmpty());

		for (LogContext logContext : logContexts) {
			LogContextRegistryUtil.unregisterLogContext(logContext);

			Assert.assertTrue(logContext instanceof CompanyWebIdLogContext);
			Assert.assertEquals("company", logContext.getName());
		}
	}

	@Test
	public void testConfigureLog4J() {
		String loggerName = StringUtil.randomString();

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		Map<String, String> priorities = Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ALL));

		Assert.assertEquals(
			priorities, Collections.singletonMap(loggerName, _ALL));

		_assertPriority(logger, _ALL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _DEBUG));

		_assertPriority(logger, _DEBUG);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		_assertPriority(logger, _ERROR);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _FATAL));

		_assertPriority(logger, _FATAL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _INFO));

		_assertPriority(logger, _INFO);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _OFF));

		_assertPriority(logger, _OFF);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _TRACE));

		_assertPriority(logger, _TRACE);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertPriority(logger, _WARN);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, "FAKE_LEVEL"));

		_assertPriority(logger, _ERROR);
	}

	@Test
	public void testConfigureLog4JWithAppender() {
		String loggerName = StringUtil.randomString();

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR));

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		_assertAppenders(logger);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR, _CONSOLE));

		_assertAppenders(logger, _CONSOLE);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR, _NULL));

		_assertAppenders(logger, _CONSOLE, _NULL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _ERROR, _CONSOLE));

		_assertAppenders(logger, _CONSOLE, _NULL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, _CONSOLE, _NULL));

		_assertAppenders(logger, _CONSOLE, _NULL);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(
				loggerName, _ERROR, _CONSOLE, _NULL),
			_NULL);

		_assertAppenders(logger, _CONSOLE, _NULL);
	}

	@Test
	public void testConfigureLog4JWithException() {
		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				Log4jConfigUtil.class.getName(), Level.SEVERE)) {

			Log4jConfigUtil.configureLog4J(null);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertNull(logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(NullPointerException.class, throwable.getClass());

			String xmlContent = _generateXMLConfigurationContent(
				StringUtil.randomString(), _INFO);

			xmlContent = StringUtil.removeSubstring(
				xmlContent, "strict=\"true\"");

			Log4jConfigUtil.configureLog4J(xmlContent);

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"<Configuration> strict attribute requires true",
				logEntry.getMessage());

			Log4jConfigUtil.configureLog4J(
				_generateLog4j1XMLConfigurationContent());

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			logEntry = logEntries.get(2);

			Assert.assertEquals(
				"Log4J 2 <Configuration> is required", logEntry.getMessage());
		}
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGetCompanyLogDirectoryWithCompanyLogDisabled()
		throws Exception {

		PropsTestUtil.setProps(PropsKeys.COMPANY_LOG_ENABLED, "false");

		_testGetCompanyLogDirectory(false);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGetCompanyLogDirectoryWithCompanyLogEnabled()
		throws Exception {

		PropsTestUtil.setProps(PropsKeys.COMPANY_LOG_ENABLED, "true");

		_testGetCompanyLogDirectory(true);
	}

	@Test
	public void testGetJDKLevel() {
		Assert.assertEquals(
			"FINE", String.valueOf(Log4jConfigUtil.getJDKLevel(_DEBUG)));
		Assert.assertEquals(
			"SEVERE", String.valueOf(Log4jConfigUtil.getJDKLevel(_ERROR)));
		Assert.assertEquals(
			"INFO", String.valueOf(Log4jConfigUtil.getJDKLevel(_INFO)));
		Assert.assertEquals(
			"WARNING", String.valueOf(Log4jConfigUtil.getJDKLevel(_WARN)));
	}

	@Test
	public void testGetPriorities() {
		String loggerName = StringUtil.randomString();

		Map<String, String> priorities = Log4jConfigUtil.getPriorities();

		Assert.assertFalse(priorities.containsKey(loggerName));

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		priorities = Log4jConfigUtil.getPriorities();

		Assert.assertEquals(
			"The priority should be WARN by configuration", _WARN,
			priorities.get(loggerName));

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, null));

		priorities = Log4jConfigUtil.getPriorities();

		Assert.assertEquals(
			"The level should use its parent level(root logger level is Error)",
			_ERROR, priorities.get(loggerName));
	}

	@Test
	public void testMisc() {
		new Log4jConfigUtil();
	}

	@Test
	public void testSetLevel() {
		String loggerName = StringUtil.randomString();

		Logger logger = (Logger)LogManager.getLogger(loggerName);

		_assertPriority(logger, _ERROR);

		String childLoggerName = loggerName + ".child";

		Logger childLogger = (Logger)LogManager.getLogger(childLoggerName);

		_assertPriority(childLogger, _ERROR);

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _WARN));

		_assertPriority(logger, _WARN);
		_assertPriority(childLogger, _WARN);

		Log4jConfigUtil.setLevel(loggerName, _DEBUG);

		_assertPriority(logger, _DEBUG);
		_assertPriority(childLogger, _DEBUG);

		Log4jConfigUtil.setLevel(childLoggerName, _ERROR);

		_assertPriority(logger, _DEBUG);
		_assertPriority(childLogger, _ERROR);
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testShutdownLog4J() {
		PropsTestUtil.setProps(Collections.emptyMap());

		Logger logger = (Logger)LogManager.getRootLogger();

		Map<String, Appender> appenders = logger.getAppenders();

		Assert.assertTrue(
			"The root logger should include appenders", !appenders.isEmpty());

		Log4jConfigUtil.shutdownLog4J();

		Assert.assertFalse(
			"The root logger should not own appenders after shutting down",
			appenders.isEmpty());
	}

	private void _assertAppenders(Logger logger, String... appenderTypes) {
		Map<String, Appender> appenders = logger.getAppenders();

		List<String> targetAppenderNames = new ArrayList<>();

		for (String appenderName : appenders.keySet()) {
			targetAppenderNames.add(appenderName);
		}

		Assert.assertEquals(targetAppenderNames.size(), appenderTypes.length);

		for (String appenderType : appenderTypes) {
			Assert.assertTrue(
				"Missing appender " + appenderType,
				targetAppenderNames.contains(appenderType));
		}
	}

	private void _assertPriority(Logger logger, String priority) {
		if (priority.equals(_ALL)) {
			Assert.assertTrue(
				"TRACE should be enabled if logging priority is ALL",
				logger.isTraceEnabled());

			return;
		}

		if (logger.isTraceEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _TRACE);
		}
		else if (logger.isDebugEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _DEBUG);
		}
		else if (logger.isInfoEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _INFO);
		}
		else if (logger.isWarnEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _WARN);
		}
		else if (logger.isErrorEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _ERROR);
		}
		else if (logger.isFatalEnabled()) {
			Assert.assertEquals("Logging priority is wrong", priority, _FATAL);
		}
		else {
			Assert.assertEquals("Logging priority is wrong", priority, _OFF);
		}
	}

	private String _generateCompanyLogRoutingAppenderConfigurationContent(
		String appenderName, String dirPattern, String fileNamePattern,
		String loggerName, String priority) {

		StringBundler sb = new StringBundler(17);

		sb.append("<?xml version=\"1.0\"?><Configuration strict=\"true\">");
		sb.append("<Appenders><Appender name=\"");
		sb.append(appenderName);
		sb.append("\" dirPattern=\"");
		sb.append(dirPattern);
		sb.append("\" type=\"CompanyLogRouting\">");
		sb.append("<FilePattern fileNamePattern=\"");
		sb.append(fileNamePattern);
		sb.append("\"><Log4j1XmlLayout locationInfo=\"true\" /></FilePattern>");
		sb.append("<TimeBasedTriggeringPolicy /><DirectWriteRolloverStrategy ");
		sb.append("/></Appender></Appenders><Loggers><Logger level= \"");
		sb.append(priority);
		sb.append("\" name=\"");
		sb.append(loggerName);
		sb.append("\"><AppenderRef ref=\"");
		sb.append(appenderName);
		sb.append("\" /></Logger></Loggers></Configuration>");

		return sb.toString();
	}

	private String _generateCompanyWebIdLogContextConfigurationContent() {
		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?><Configuration strict=\"true\">");
		sb.append("<Appenders><Appender name=\"");
		sb.append(StringUtil.randomString());
		sb.append("\" type=\"CompanyWebIdConsole\"><Layout type=\"");
		sb.append("PatternLayout\"/></Appender></Appenders></Configuration>");

		return sb.toString();
	}

	private String _generateLog4j1XMLConfigurationContent() {
		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">");
		sb.append("<log4j:configuration xmlns:log4j=");
		sb.append("\"http://jakarta.apache.org/log4j/\">");
		sb.append("</log4j:configuration>");

		return sb.toString();
	}

	private String _generateXMLConfigurationContent(
		String loggerName, String priority, String... appenderTypes) {

		int initialCapacity =
			(appenderTypes.length == 0) ? 7 : (9 + (9 * appenderTypes.length));

		if (ArrayUtil.contains(appenderTypes, _CONSOLE, false)) {
			initialCapacity = initialCapacity + 1;
		}

		StringBundler sb = new StringBundler(initialCapacity);

		sb.append("<?xml version=\"1.0\"?><Configuration strict=\"true\">");

		if (appenderTypes.length > 0) {
			sb.append("<Appenders>");

			for (String appenderType : appenderTypes) {
				sb.append("<Appender name=\"");
				sb.append(appenderType);
				sb.append("\" type=\"");
				sb.append(appenderType);
				sb.append("\">");

				if (appenderType.equals(_CONSOLE)) {
					sb.append("<Layout type=\"PatternLayout\"/>");
				}

				sb.append("</Appender>");
			}

			sb.append("</Appenders>");
		}

		sb.append("<Loggers><Logger level= \"");
		sb.append(priority);
		sb.append("\" name=\"");
		sb.append(loggerName);
		sb.append("\">");

		for (String appenderType : appenderTypes) {
			sb.append("<AppenderRef ref=\"");
			sb.append(appenderType);
			sb.append("\" />");
		}

		sb.append("</Logger></Loggers></Configuration>");

		return sb.toString();
	}

	private void _testGetCompanyLogDirectory(boolean enabled) throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		String loggerName = StringUtil.randomString();

		Log4jConfigUtil.configureLog4J(
			_generateXMLConfigurationContent(loggerName, _INFO));

		try {
			Log4jConfigUtil.getCompanyLogDirectory(companyId);

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"No company log routing appender defined",
				illegalStateException.getMessage());
		}

		File tempLogFileDir = null;

		try {
			Path path = Files.createTempDirectory(
				Log4jConfigUtilTest.class.getName());

			tempLogFileDir = path.toFile();

			String tempLogFileDirPathString = tempLogFileDir.getPath();

			Log4jConfigUtil.configureLog4J(
				_generateCompanyLogRoutingAppenderConfigurationContent(
					"COMPANY_LOG_ROUTING_TEXT_FILE",
					tempLogFileDirPathString + "/@company.id@",
					"liferay-@company.id@.%d{yyyy-MM-dd}.xml.log", loggerName,
					_INFO));

			Logger logger = (Logger)LogManager.getLogger(loggerName);

			logger.info("Test message");

			File companyLogDirectory = Log4jConfigUtil.getCompanyLogDirectory(
				companyId);

			File expectedCompanyLogDirectory = new File(
				tempLogFileDirPathString, String.valueOf(companyId));

			String expectedCompanyLogDirectoryPathString =
				expectedCompanyLogDirectory.getPath();

			Assert.assertEquals(
				"Company log directory should be " +
					expectedCompanyLogDirectoryPathString,
				expectedCompanyLogDirectoryPathString,
				companyLogDirectory.getPath());

			if (enabled) {
				Assert.assertTrue(companyLogDirectory.exists());
			}
			else {
				Assert.assertFalse(companyLogDirectory.exists());
			}
		}
		finally {
			if (tempLogFileDir != null) {
				for (File file : tempLogFileDir.listFiles()) {
					file.delete();
				}

				tempLogFileDir.delete();
			}
		}
	}

	private static final String _ALL = "ALL";

	private static final String _CONSOLE = "CONSOLE";

	private static final String _DEBUG = "DEBUG";

	private static final String _ERROR = "ERROR";

	private static final String _FATAL = "FATAL";

	private static final String _INFO = "INFO";

	private static final String _NULL = "NULL";

	private static final String _OFF = "OFF";

	private static final String _TRACE = "TRACE";

	private static final String _WARN = "WARN";

}