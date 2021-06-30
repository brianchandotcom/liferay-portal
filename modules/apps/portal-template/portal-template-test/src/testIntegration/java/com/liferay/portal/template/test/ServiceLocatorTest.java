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

package com.liferay.portal.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.persistence.MBMessagePersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletService;
import com.liferay.portal.kernel.service.persistence.PortletPersistence;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.template.ServiceLocator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class ServiceLocatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_templateEngineServiceLocatorRestrict =
			ReflectionTestUtil.getFieldValue(
				PropsValues.class, "TEMPLATE_ENGINE_SERVICE_LOCATOR_RESTRICT");
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "TEMPLATE_ENGINE_SERVICE_LOCATOR_RESTRICT",
			_templateEngineServiceLocatorRestrict);
	}

	@Test
	public void testFindServiceWithRestrictDisabled() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "TEMPLATE_ENGINE_SERVICE_LOCATOR_RESTRICT",
			false);

		ServiceLocator serviceLocator = ServiceLocator.getInstance();

		Assert.assertNotNull(
			serviceLocator.findService(PortletLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletService.class.getName()));

		Assert.assertNotNull(
			serviceLocator.findService(MBMessageLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(MBMessageService.class.getName()));

		Assert.assertNotNull(
			serviceLocator.findService(PortletPersistence.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(MBMessagePersistence.class.getName()));
	}

	@Test
	public void testFindServiceWithRestrictEnabled() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "TEMPLATE_ENGINE_SERVICE_LOCATOR_RESTRICT",
			true);

		ServiceLocator serviceLocator = ServiceLocator.getInstance();

		Assert.assertNotNull(
			serviceLocator.findService(PortletLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletService.class.getName()));

		Assert.assertNotNull(
			serviceLocator.findService(MBMessageLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(MBMessageService.class.getName()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ServiceLocator.class.getName(), LoggerTestUtil.WARN)) {

			Assert.assertNull(
				serviceLocator.findService(PortletPersistence.class.getName()));
			Assert.assertNull(
				serviceLocator.findService(
					MBMessagePersistence.class.getName()));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Denied access to service \"",
					PortletPersistence.class.getName(),
					"\" as it is not a service builder generated service"),
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				StringBundler.concat(
					"Denied access to service \"",
					MBMessagePersistence.class.getName(),
					"\" as it is not a service builder generated service"),
				logEntry.getMessage());
		}
	}

	private static boolean _templateEngineServiceLocatorRestrict;

}