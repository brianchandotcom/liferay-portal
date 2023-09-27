/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyProperties;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class VerifyPropertiesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMigratedPortalKeys() throws Exception {
		String migratedPortalKey = getFirstPortalPropertyKey();

		String[][] originalMigratedPortalKeys = _setPropertyKeys(
			"_MIGRATED_PORTAL_KEYS",
			new String[][] {{migratedPortalKey, migratedPortalKey}});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			SecurityException securityException1 = new SecurityException();

			SwappableSecurityManager swappableSecurityManager =
				new SwappableSecurityManager() {

					@Override
					public void checkExit(int status) {
						throw securityException1;
					}

				};

			swappableSecurityManager.install();

			try {
				VerifyProperties.verify();
			}
			catch (SecurityException securityException2) {
				Assert.assertSame(securityException1, securityException2);
			}

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Portal property \"", migratedPortalKey,
					"\" was migrated to the system property \"",
					migratedPortalKey, "\""),
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				StringBundler.concat(
					"Stopping the server due to incorrect use of migrated ",
					"portal properties [", migratedPortalKey, "]"),
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys(
				"_MIGRATED_PORTAL_KEYS", originalMigratedPortalKeys);
		}
	}

	@Test
	public void testMigratedSystemKeys() throws Exception {
		String migratedSystemKey = getFirstSystemPropertyKey();

		String[][] originalMigratedSystemKeys = _setPropertyKeys(
			"_MIGRATED_SYSTEM_KEYS",
			new String[][] {{migratedSystemKey, migratedSystemKey}});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"System property \"", migratedSystemKey,
					"\" was migrated to the portal property \"",
					migratedSystemKey, "\""),
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys(
				"_MIGRATED_SYSTEM_KEYS", originalMigratedSystemKeys);
		}
	}

	@Test
	public void testModularizedPortalKeys() throws Exception {
		String modularizedPortalKey = getFirstPortalPropertyKey();

		String[][] originalModularizedPortalKeys = _setPropertyKeys(
			"_MODULARIZED_PORTAL_KEYS",
			new String[][] {
				{
					modularizedPortalKey, modularizedPortalKey,
					modularizedPortalKey
				}
			});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Portal property \"", modularizedPortalKey,
					"\" was modularized to ", modularizedPortalKey, " as \"",
					modularizedPortalKey, "\""),
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys(
				"_MODULARIZED_PORTAL_KEYS", originalModularizedPortalKeys);
		}
	}

	@Test
	public void testObsoletePortalKeys() throws Exception {
		String obsoletePortalKey = getFirstPortalPropertyKey();

		String[] originalObsoletePortalKeys = _setPropertyKeys(
			"_OBSOLETE_PORTAL_KEYS", new String[] {obsoletePortalKey});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Portal property \"" + obsoletePortalKey + "\" is obsolete",
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys(
				"_OBSOLETE_PORTAL_KEYS", originalObsoletePortalKeys);
		}
	}

	@Test
	public void testObsoleteSystemKeys() throws Exception {
		String obsoleteSystemKey = getFirstSystemPropertyKey();

		String[] originalObsoleteSystemKeys = _setPropertyKeys(
			"_OBSOLETE_SYSTEM_KEYS", new String[] {obsoleteSystemKey});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"System property \"" + obsoleteSystemKey + "\" is obsolete",
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys(
				"_OBSOLETE_SYSTEM_KEYS", originalObsoleteSystemKeys);
		}
	}

	@Test
	public void testRenamedPortalKeys() throws Exception {
		String renamedPortalKey = getFirstPortalPropertyKey();

		String[][] originalRenamedPortalKeys = _setPropertyKeys(
			"_RENAMED_PORTAL_KEYS",
			new String[][] {new String[] {renamedPortalKey, renamedPortalKey}});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Portal property \"", renamedPortalKey,
					"\" was renamed to \"", renamedPortalKey, "\""),
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys("_RENAMED_PORTAL_KEYS", originalRenamedPortalKeys);
		}
	}

	@Test
	public void testRenamedSystemKeys() throws Exception {
		String renamedSystemKey = getFirstSystemPropertyKey();

		String[][] originalRenamedSystemKeys = _setPropertyKeys(
			"_RENAMED_SYSTEM_KEYS",
			new String[][] {new String[] {renamedSystemKey, renamedSystemKey}});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"System property \"", renamedSystemKey,
					"\" was renamed to \"", renamedSystemKey, "\""),
				logEntry.getMessage());
		}
		finally {
			_setPropertyKeys("_RENAMED_SYSTEM_KEYS", originalRenamedSystemKeys);
		}
	}

	@Test
	public void testVerify() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				VerifyProperties.class.getName(), LoggerTestUtil.ERROR)) {

			VerifyProperties.verify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	protected String getFirstPortalPropertyKey() {
		Properties portalProperties = ReflectionTestUtil.invoke(
			VerifyProperties.class, "loadPortalProperties", new Class<?>[0]);

		Set<String> propertyNames = portalProperties.stringPropertyNames();

		Assert.assertFalse(propertyNames.toString(), propertyNames.isEmpty());

		Iterator<String> iterator = propertyNames.iterator();

		return iterator.next();
	}

	protected String getFirstSystemPropertyKey() {
		Set<String> propertyNames = SystemProperties.getPropertyNames();

		Assert.assertFalse(propertyNames.toString(), propertyNames.isEmpty());

		Iterator<String> iterator = propertyNames.iterator();

		return iterator.next();
	}

	private <T> T _setPropertyKeys(String fieldName, T value) {
		T orignalValue = ReflectionTestUtil.getFieldValue(
			VerifyProperties.class, fieldName);

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, fieldName, value);

		return orignalValue;
	}

}