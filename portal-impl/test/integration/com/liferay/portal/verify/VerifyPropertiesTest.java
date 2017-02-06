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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class VerifyPropertiesTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMigratedPortalKeys() throws Exception {
		String[][] originalMigratedPortalKeys =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_MIGRATED_PORTAL_KEYS");

		String migratedPortalKey = getFirstPortalPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_MIGRATED_PORTAL_KEYS",
			new String[][] {
				new String[] {migratedPortalKey, migratedPortalKey}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + migratedPortalKey +
					"\" was migrated to the system property \"" +
						migratedPortalKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_MIGRATED_PORTAL_KEYS",
				originalMigratedPortalKeys);
		}
	}

	@Test
	public void testMigratedSystemKeys() throws Exception {
		String[][] originalMigratedSystemKeys =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_MIGRATED_SYSTEM_KEYS");

		String migratedSystemKey = getFirstSystemPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_MIGRATED_SYSTEM_KEYS",
			new String[][] {
				new String[] {migratedSystemKey, migratedSystemKey}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"System property \"" + migratedSystemKey +
					"\" was migrated to the portal property \"" +
						migratedSystemKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_MIGRATED_SYSTEM_KEYS",
				originalMigratedSystemKeys);
		}
	}

	@Test
	public void testModularizedPortalKeys() throws Exception {
		String[][] originalModularizedPortalKeys =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_MODULARIZED_PORTAL_KEYS");

		String modularizedPortalKey = getFirstPortalPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_MODULARIZED_PORTAL_KEYS",
			new String[][] {
				new String[] {
					modularizedPortalKey, modularizedPortalKey,
					modularizedPortalKey
				}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + modularizedPortalKey +
					"\" was modularized to " + modularizedPortalKey + " as \"" +
						modularizedPortalKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_MODULARIZED_PORTAL_KEYS",
				originalModularizedPortalKeys);
		}
	}

	@Test
	public void testObsoletePortalKeys() throws Exception {
		String[] originalObsoletePortalKeys = ReflectionTestUtil.getFieldValue(
			VerifyProperties.class, "_OBSOLETE_PORTAL_KEYS");

		String obsoletePortalKey = getFirstPortalPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_OBSOLETE_PORTAL_KEYS",
			new String[] {obsoletePortalKey});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + obsoletePortalKey +
					"\" is obsolete",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_OBSOLETE_PORTAL_KEYS",
				originalObsoletePortalKeys);
		}
	}

	@Test
	public void testObsoleteSystemKeys() throws Exception {
		String[] originalObsoleteSystemKeys = ReflectionTestUtil.getFieldValue(
			VerifyProperties.class, "_OBSOLETE_SYSTEM_KEYS");

		String obsoleteSystemKey = getFirstSystemPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_OBSOLETE_SYSTEM_KEYS",
			new String[] {obsoleteSystemKey});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"System property \"" + obsoleteSystemKey +
					"\" is obsolete",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_OBSOLETE_SYSTEM_KEYS",
				originalObsoleteSystemKeys);
		}
	}

	@Test
	public void testRenamedPortalKeys() throws Exception {
		String[][] originalRenamedPortalKeys = ReflectionTestUtil.getFieldValue(
			VerifyProperties.class, "_RENAMED_PORTAL_KEYS");

		String renamedPortalKey = getFirstPortalPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_RENAMED_PORTAL_KEYS",
			new String[][] {new String[] {renamedPortalKey, renamedPortalKey}});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + renamedPortalKey +
					"\" was renamed to \"" + renamedPortalKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_RENAMED_PORTAL_KEYS",
				originalRenamedPortalKeys);
		}
	}

	@Test
	public void testRenamedSystemKeys() throws Exception {
		String[][] originalRenamedSystemKeys = ReflectionTestUtil.getFieldValue(
			VerifyProperties.class, "_RENAMED_SYSTEM_KEYS");

		String renamedSystemKey = getFirstSystemPropertyKey();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_RENAMED_SYSTEM_KEYS",
			new String[][] {new String[] {renamedSystemKey, renamedSystemKey}});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"System property \"" + renamedSystemKey +
					"\" was renamed to \"" + renamedSystemKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_RENAMED_SYSTEM_KEYS",
				originalRenamedSystemKeys);
		}
	}

	@Override
	@Test
	public void testVerify() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertTrue(loggingEvents.isEmpty());
		}
	}

	protected String getFirstPortalPropertyKey() {
		VerifyProperties verifyProperties = getVerifyProcess();

		Properties portalProperties = verifyProperties.loadPortalProperties();

		Set<String> propertyNames = portalProperties.stringPropertyNames();

		Assert.assertFalse(propertyNames.isEmpty());

		Iterator<String> iterator = propertyNames.iterator();

		return iterator.next();
	}

	protected String getFirstSystemPropertyKey() {
		Properties systemProperties = SystemProperties.getProperties();

		Set<String> propertyNames = systemProperties.stringPropertyNames();

		Assert.assertFalse(propertyNames.isEmpty());

		Iterator<String> iterator = propertyNames.iterator();

		return iterator.next();
	}

	@Override
	protected VerifyProperties getVerifyProcess() {
		return new VerifyProperties();
	}

}