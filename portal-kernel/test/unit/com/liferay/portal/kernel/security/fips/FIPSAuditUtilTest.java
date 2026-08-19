/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.internal.log4j.FIPSLog4jUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.Provider;
import java.security.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge García Jiménez
 * @author Rafael Praxedes
 */
public class FIPSAuditUtilTest {

	@BeforeClass
	public static void setUpClass() {
		_logger = Mockito.mock(Logger.class);

		_logManagerMockedStatic = Mockito.mockStatic(
			LogManager.class, Mockito.CALLS_REAL_METHODS);

		_logManagerMockedStatic.when(
			() -> LogManager.getLogger(FIPSLog4jUtil.class)
		).thenReturn(
			_logger
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_logManagerMockedStatic.close();
	}

	@Before
	public void setUp() {
		Mockito.reset(_logger);

		Security.insertProviderAt(_testProvider, 1);

		DCLSingleton<String> dclSingleton = ReflectionTestUtil.getFieldValue(
			FIPSAuditUtil.class, "_deploymentInstanceIdDCLSingleton");

		dclSingleton.destroy(null);

		_safeCloseable = PropsValuesTestUtil.swapWithSafeCloseable(
			"FIPS_AUDIT_DEPLOYMENT_INSTANCE_ID", RandomTestUtil.randomString());

		_mockLogManager(null);
	}

	@After
	public void tearDown() {
		Security.removeProvider(_TEST_PROVIDER_NAME);

		_safeCloseable.close();
	}

	@Test
	public void testWrite() {
		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_AUDIT_DEPLOYMENT_INSTANCE_ID", "instance-1");
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_AUDIT_PROVIDER_CMVP_CERTIFICATE_ID", "4743")) {

			String eventType = RandomTestUtil.randomString();

			FIPSAuditEvent fipsAuditEvent = new FIPSAuditEvent(
				eventType, FIPSAuditEvent.Severity.CRITICAL);

			String fromState = RandomTestUtil.randomString();

			fipsAuditEvent.put("from-state", fromState);

			String toState = RandomTestUtil.randomString();

			fipsAuditEvent.put("to-state", toState);

			FIPSAuditUtil.write(fipsAuditEvent);

			Map<String, Object> record = _getLastRecord(Level.ERROR);

			Assert.assertEquals("4743", record.get("cmvp-certificate-id"));
			Assert.assertEquals(
				"instance-1", record.get("deployment-instance-id"));
			Assert.assertEquals("1.0", record.get("event-schema-version"));
			Assert.assertEquals(eventType, record.get("event-type"));

			Map<?, ?> fields = (Map<?, ?>)record.get("fields");

			Assert.assertEquals(fromState, fields.get("from-state"));
			Assert.assertEquals(toState, fields.get("to-state"));

			Assert.assertEquals(
				_TEST_PROVIDER_NAME, record.get("provider-name"));
			Assert.assertEquals(
				_TEST_PROVIDER_VERSION, record.get("provider-version"));

			Assert.assertEquals("CRITICAL", record.get("severity"));

			String timestamp = String.valueOf(record.get("timestamp"));

			Assert.assertTrue(
				timestamp.matches(
					"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
		}
	}

	@Test
	public void testWriteDerivesStableDeploymentInstanceIdWhenUnset()
		throws Exception {

		Path liferayHome = Files.createTempDirectory(
			FIPSAuditUtilTest.class.getName());

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"LIFERAY_HOME", liferayHome.toString());
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_AUDIT_DEPLOYMENT_INSTANCE_ID", "");
			SafeCloseable safeCloseable3 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_AUDIT_PROVIDER_CMVP_CERTIFICATE_ID", "")) {

			FIPSAuditUtil.write(
				new FIPSAuditEvent(
					RandomTestUtil.randomString(),
					FIPSAuditEvent.Severity.INFO));
			FIPSAuditUtil.write(
				new FIPSAuditEvent(
					RandomTestUtil.randomString(),
					FIPSAuditEvent.Severity.INFO));

			List<Map<String, Object>> records = _getRecords(Level.INFO);

			Assert.assertEquals(records.toString(), 2, records.size());

			Map<String, Object> record1 = records.get(0);

			Object deploymentInstanceId = record1.get("deployment-instance-id");

			Assert.assertTrue(
				Validator.isNotNull(String.valueOf(deploymentInstanceId)));

			Map<String, Object> record2 = records.get(1);

			Assert.assertEquals(
				deploymentInstanceId, record2.get("deployment-instance-id"));

			Assert.assertTrue(
				Files.exists(
					liferayHome.resolve(
						"data/fips-audit-deployment-instance-id")));
		}
		finally {
			_delete(liferayHome);
		}
	}

	@Test
	public void testWriteFormatsTimestampInUTC() {
		TimeZone timeZone = TimeZone.getDefault();

		try {
			TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));

			FIPSAuditUtil.write(
				new FIPSAuditEvent(
					RandomTestUtil.randomString(),
					FIPSAuditEvent.Severity.INFO));

			Map<String, Object> record = _getLastRecord(Level.INFO);

			String timestamp = String.valueOf(record.get("timestamp"));

			Instant instant = Instant.parse(timestamp);

			Assert.assertTrue(
				Math.abs(System.currentTimeMillis() - instant.toEpochMilli()) <
					Time.MINUTE);
		}
		finally {
			TimeZone.setDefault(timeZone);
		}
	}

	@Test
	public void testWriteLogsRecordAtTheSeverityLevel() {
		_testWriteLogsRecordAtTheSeverityLevel(
			Level.ERROR, FIPSAuditEvent.Severity.CRITICAL);
		_testWriteLogsRecordAtTheSeverityLevel(
			Level.INFO, FIPSAuditEvent.Severity.INFO);
	}

	@Test
	public void testWriteNestsFields() {
		FIPSAuditEvent fipsAuditEvent = new FIPSAuditEvent(
			RandomTestUtil.randomString(), FIPSAuditEvent.Severity.INFO);

		String spoofedProviderName = RandomTestUtil.randomString();

		fipsAuditEvent.put("provider-name", spoofedProviderName);

		FIPSAuditUtil.write(fipsAuditEvent);

		Map<String, Object> record = _getLastRecord(Level.INFO);

		Assert.assertEquals(_TEST_PROVIDER_NAME, record.get("provider-name"));

		Map<?, ?> fields = (Map<?, ?>)record.get("fields");

		Assert.assertEquals(spoofedProviderName, fields.get("provider-name"));
	}

	@Test
	public void testWriteNormalizesFieldTimestamps() {
		_testWriteNormalizesFieldTimestamp(
			"2025-05-06T14:19:23.471Z",
			Date.from(Instant.parse("2025-05-06T14:19:23.471Z")));
		_testWriteNormalizesFieldTimestamp(
			"2026-05-06T14:19:23.000Z", Instant.parse("2026-05-06T14:19:23Z"));
		_testWriteNormalizesFieldTimestamp(
			"2026-05-06T14:19:23.471Z",
			Instant.parse("2026-05-06T14:19:23.471999999Z"));
		_testWriteNormalizesFieldTimestamp(
			"2026-05-06T14:19:23.471Z",
			OffsetDateTime.parse("2026-05-06T16:19:23.471+02:00"));
	}

	@Test
	public void testWriteNormalizesNestedFieldTimestamps() {
		FIPSAuditEvent fipsAuditEvent = new FIPSAuditEvent(
			RandomTestUtil.randomString(), FIPSAuditEvent.Severity.INFO);

		fipsAuditEvent.put(
			"provider-self-test",
			Collections.singletonMap(
				"completed", Instant.parse("2026-05-06T14:19:23.471Z")));
		fipsAuditEvent.put(
			"provider-timestamps",
			Arrays.asList(Instant.parse("2026-05-06T14:19:23Z")));

		FIPSAuditUtil.write(fipsAuditEvent);

		Map<String, Object> record = _getLastRecord(Level.INFO);

		Map<?, ?> fields = (Map<?, ?>)record.get("fields");

		Map<?, ?> providerSelfTestMap = (Map<?, ?>)fields.get(
			"provider-self-test");

		Assert.assertEquals(
			"2026-05-06T14:19:23.471Z", providerSelfTestMap.get("completed"));

		Assert.assertEquals(
			Arrays.asList("2026-05-06T14:19:23.000Z"),
			fields.get("provider-timestamps"));
	}

	@Test
	public void testWriteRejectsAFieldTimestampWithoutATimeZone() {
		FIPSAuditEvent fipsAuditEvent = new FIPSAuditEvent(
			RandomTestUtil.randomString(), FIPSAuditEvent.Severity.INFO);

		fipsAuditEvent.put(
			"provider-timestamp", LocalDateTime.parse("2026-05-06T14:19:23"));

		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> FIPSAuditUtil.write(fipsAuditEvent));
	}

	@Test
	public void testWriteSequencesRecordsMonotonically() {
		FIPSAuditUtil.write(
			new FIPSAuditEvent(
				RandomTestUtil.randomString(), FIPSAuditEvent.Severity.INFO));
		FIPSAuditUtil.write(
			new FIPSAuditEvent(
				RandomTestUtil.randomString(), FIPSAuditEvent.Severity.INFO));

		List<Map<String, Object>> records = _getRecords(Level.INFO);

		Assert.assertEquals(records.toString(), 2, records.size());

		Map<String, Object> record1 = records.get(0);
		Map<String, Object> record2 = records.get(1);

		long eventSequence1 = (Long)record1.get("event-sequence");
		long eventSequence2 = (Long)record2.get("event-sequence");

		Assert.assertEquals(eventSequence1 + 1, eventSequence2);
	}

	@Test
	public void testWriteThrowsWhenAppenderIsMissing() {
		Mockito.when(
			_logger.isEnabled(Level.INFO)
		).thenReturn(
			true
		);

		_testWriteThrows();
	}

	@Test
	public void testWriteThrowsWhenLayoutIsNotTheNDJSONLayout() {
		Mockito.when(
			_logger.isEnabled(Level.INFO)
		).thenReturn(
			true
		);

		RollingFileAppender rollingFileAppender = Mockito.mock(
			RollingFileAppender.class);

		Mockito.doReturn(
			Mockito.mock(Layout.class)
		).when(
			rollingFileAppender
		).getLayout();

		_mockLogManager(rollingFileAppender);

		_testWriteThrows();
	}

	@Test
	public void testWriteThrowsWhenLoggerIsDisabled() {
		Mockito.when(
			_logger.isEnabled(Level.INFO)
		).thenReturn(
			false
		);

		_testWriteThrows();
	}

	private void _delete(Path path) throws IOException {
		Files.walkFileTree(
			path,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioException)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private Map<String, Object> _getLastRecord(Level level) {
		List<Map<String, Object>> records = _getRecords(level);

		return records.get(records.size() - 1);
	}

	private List<Map<String, Object>> _getRecords(Level level) {
		List<Map<String, Object>> records = new ArrayList<>();

		ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(
			Message.class);

		Mockito.verify(
			_logger, Mockito.atLeastOnce()
		).log(
			Mockito.eq(level), Mockito.eq(FIPSLog4jUtil.getMarker()),
			argumentCaptor.capture()
		);

		for (Message message : argumentCaptor.getAllValues()) {
			ObjectMessage objectMessage = (ObjectMessage)message;

			records.add((Map<String, Object>)objectMessage.getParameter());
		}

		return records;
	}

	private void _mockLogManager(RollingFileAppender rollingFileAppender) {
		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getAppender("FIPS_AUDIT_FILE")
		).thenReturn(
			rollingFileAppender
		);

		LoggerContext loggerContext = Mockito.mock(LoggerContext.class);

		Mockito.when(
			loggerContext.getConfiguration()
		).thenReturn(
			configuration
		);

		_logManagerMockedStatic.when(
			() -> LogManager.getContext(false)
		).thenReturn(
			loggerContext
		);
	}

	private void _testWriteLogsRecordAtTheSeverityLevel(
		Level level, FIPSAuditEvent.Severity severity) {

		FIPSAuditUtil.write(
			new FIPSAuditEvent(RandomTestUtil.randomString(), severity));

		Map<String, Object> record = _getLastRecord(level);

		Assert.assertEquals(severity.name(), record.get("severity"));
	}

	private void _testWriteNormalizesFieldTimestamp(
		String expectedTimestamp, Object value) {

		FIPSAuditEvent fipsAuditEvent = new FIPSAuditEvent(
			RandomTestUtil.randomString(), FIPSAuditEvent.Severity.INFO);

		fipsAuditEvent.put("provider-timestamp", value);

		FIPSAuditUtil.write(fipsAuditEvent);

		Map<String, Object> record = _getLastRecord(Level.INFO);

		Map<?, ?> fields = (Map<?, ?>)record.get("fields");

		Assert.assertEquals(
			expectedTimestamp, fields.get("provider-timestamp"));
	}

	private void _testWriteThrows() {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable("FIPS_ENABLED", true);
			MockedStatic<ServerDetector> serverDetectorMockedStatic =
				Mockito.mockStatic(
					ServerDetector.class, Mockito.CALLS_REAL_METHODS)) {

			serverDetectorMockedStatic.when(
				ServerDetector::getServerId
			).thenReturn(
				RandomTestUtil.randomString()
			);

			Assert.assertThrows(
				IllegalStateException.class,
				() -> FIPSAuditUtil.write(
					new FIPSAuditEvent(
						RandomTestUtil.randomString(),
						FIPSAuditEvent.Severity.INFO)));
		}
	}

	private static final String _TEST_PROVIDER_NAME = "TestProvider";

	private static final String _TEST_PROVIDER_VERSION = "9.9";

	private static Logger _logger;

	private static MockedStatic<LogManager> _logManagerMockedStatic;
	private static final Provider _testProvider = new TestProvider();

	private SafeCloseable _safeCloseable;

	private static class TestProvider extends Provider {

		private TestProvider() {
			super(
				_TEST_PROVIDER_NAME, _TEST_PROVIDER_VERSION,
				"Test provider for the FIPS audit envelope");
		}

	}

}