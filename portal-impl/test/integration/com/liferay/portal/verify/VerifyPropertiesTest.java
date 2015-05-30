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
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testMigratedPortalProperty() throws Exception {
		VerifyProperties verifyProperties = getVerifyProcess();

		String[][] originalMigratedPortalKeys =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_MIGRATED_PORTAL_KEYS");

		Properties properties = verifyProperties.loadPortalProperties();

		Set<Object> migratedPortalKeys = properties.keySet();

		String migratedPortalKey = (String)migratedPortalKeys.iterator().next();

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
	public void testMigratedSystemProperty() throws Exception {
		String[][] originalMigratedSystemKeys =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_MIGRATED_SYSTEM_KEYS");

		Set<Object> migratedSystemKeys =
			SystemProperties.getProperties().keySet();

		String migratedSystemKey = (String)migratedSystemKeys.iterator().next();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_MIGRATED_SYSTEM_KEYS",
			new String[][] {
				new String[] {migratedSystemKey,migratedSystemKey}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"System property \"" + migratedSystemKey +
					"\" was migrated to the " + "portal property \"" +
						migratedSystemKey + "\"",
				loggingEvent.getMessage().toString());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_MIGRATED_SYSTEM_KEYS",
				originalMigratedSystemKeys);
		}
	}

	@Test
	public void testModularizedPortalProperty() throws Exception {
		VerifyProperties verifyProperties = getVerifyProcess();

		String[][] originalModularizedPortalKeys =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_MODULARIZED_PORTAL_KEYS");

		Properties properties = verifyProperties.loadPortalProperties();

		Set<Object> modularizedPortalKeys = properties.keySet();
		String modularizedPortalKey =
			(String)modularizedPortalKeys.iterator().next();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_MODULARIZED_PORTAL_KEYS",
			new String[][] {
				new String[] {
					modularizedPortalKey,modularizedPortalKey,
					modularizedPortalKey
				}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + modularizedPortalKey +
					"\" was modularized to " + modularizedPortalKey +
						" as \"" + modularizedPortalKey,
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_MODULARIZED_PORTAL_KEYS",
				originalModularizedPortalKeys);
		}
	}

	@Test
	public void testObsoletePortalProperty() throws Exception {
		VerifyProperties verifyProperties = getVerifyProcess();

		String[] originalObsoletePortalProperty =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_OBSOLETE_PORTAL_KEYS");

		Properties properties = verifyProperties.loadPortalProperties();

		Set<Object> obsoletePortalPropertyKeys = properties.keySet();

		String obsoletePortalPropertyKey =
			(String)obsoletePortalPropertyKeys.iterator().next();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_OBSOLETE_PORTAL_KEYS",
			new String[] {obsoletePortalPropertyKey});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + obsoletePortalPropertyKey +
					"\" is obsolete",
				loggingEvent.getMessage().toString());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_OBSOLETE_PORTAL_KEYS",
				originalObsoletePortalProperty);
		}
	}

	@Test
	public void testObsoleteSystemProperty() throws Exception {
		String[] originalObsoleteSystemProperty =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_OBSOLETE_SYSTEM_KEYS");

		Set<Object> obsoleteSystemPropertyKeys =
			SystemProperties.getProperties().keySet();

		String obsoleteSystemPropertyKey =
			(String)obsoleteSystemPropertyKeys.iterator().next();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_OBSOLETE_SYSTEM_KEYS",
			new String[] {obsoleteSystemPropertyKey});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"System property \"" + obsoleteSystemPropertyKey +
					"\" is obsolete",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_OBSOLETE_SYSTEM_KEYS",
				originalObsoleteSystemProperty);
		}
	}

	@Test
	public void testRenamedPortalProperty() throws Exception {
		VerifyProperties verifyProperties = getVerifyProcess();

		String[][] originalRenamedPortalProperty =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_RENAMED_PORTAL_KEYS");

		Properties properties = verifyProperties.loadPortalProperties();

		Set<Object> renamedPortalPropertyKeys = properties.keySet();

		String renamedPortalPropertyKey =
			(String)renamedPortalPropertyKeys.iterator().next();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_RENAMED_PORTAL_KEYS",
			new String[][] {
				new String[] {
					renamedPortalPropertyKey,renamedPortalPropertyKey}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Portal property \"" + renamedPortalPropertyKey +
					"\" was renamed to \"" + renamedPortalPropertyKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_RENAMED_PORTAL_KEYS",
				originalRenamedPortalProperty);
		}
	}

	@Test
	public void testRenamedSystemProperty() throws Exception {
		String[][] originalRenamedSystemProperty =
			ReflectionTestUtil.getFieldValue(
				VerifyProperties.class, "_RENAMED_SYSTEM_KEYS");

		Set<Object> renamedSystemPropertyKeys =
			SystemProperties.getProperties().keySet();
		String renamedSystemPropertyKey =
			(String)renamedSystemPropertyKeys.iterator().next();

		ReflectionTestUtil.setFieldValue(
			VerifyProperties.class, "_RENAMED_SYSTEM_KEYS",
			new String[][] {
				new String[] {
					renamedSystemPropertyKey,renamedSystemPropertyKey}
			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					VerifyProperties.class.getName(), Level.ERROR)) {

			doVerify();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"System property \"" + renamedSystemPropertyKey +
					"\" was renamed to \"" + renamedSystemPropertyKey + "\"",
				loggingEvent.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				VerifyProperties.class, "_RENAMED_SYSTEM_KEYS",
				originalRenamedSystemProperty);
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

	@Override
	protected VerifyProperties getVerifyProcess() {
		return new VerifyProperties();
	}

}