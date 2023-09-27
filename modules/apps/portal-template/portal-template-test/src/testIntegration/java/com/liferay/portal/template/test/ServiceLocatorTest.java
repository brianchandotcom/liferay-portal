/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			serviceLocator.findService(MBMessageLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(MBMessagePersistence.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(MBMessageService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletPersistence.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletService.class.getName()));
	}

	@Test
	public void testFindServiceWithRestrictEnabled() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "TEMPLATE_ENGINE_SERVICE_LOCATOR_RESTRICT",
			true);

		ServiceLocator serviceLocator = ServiceLocator.getInstance();

		Assert.assertNotNull(
			serviceLocator.findService(MBMessageLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(MBMessageService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletLocalService.class.getName()));
		Assert.assertNotNull(
			serviceLocator.findService(PortletService.class.getName()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ServiceLocator.class.getName(), LoggerTestUtil.WARN)) {

			Assert.assertNull(
				serviceLocator.findService(
					MBMessagePersistence.class.getName()));
			Assert.assertNull(
				serviceLocator.findService(PortletPersistence.class.getName()));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"Denied access to service \"",
					MBMessagePersistence.class.getName(),
					"\" because it is not a Service Builder generated service"),
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				StringBundler.concat(
					"Denied access to service \"",
					PortletPersistence.class.getName(),
					"\" because it is not a Service Builder generated service"),
				logEntry.getMessage());
		}
	}

	private static boolean _templateEngineServiceLocatorRestrict;

}