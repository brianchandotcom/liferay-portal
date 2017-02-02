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

package com.liferay.portal.util;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LogAssertionTestRule;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class InitUtilTest {

	@Test
	public void testBaseSeleniumTestCaseSpringConfigs() {
		String log4jConfigureOnStartup = SystemProperties.get(
			_LOG4J_CONFIGURE_ON_STARTUP);

		SystemProperties.set(_LOG4J_CONFIGURE_ON_STARTUP, StringPool.FALSE);

		String resourceActionsReadPortletResources = SystemProperties.get(
			_RESOURCE_ACTIONS_READ_PORTLET_RESOURCES);

		SystemProperties.set(
			_RESOURCE_ACTIONS_READ_PORTLET_RESOURCES, StringPool.FALSE);

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "SPRING_INFRASTRUCTURE_CONFIGS", new String[0]);

		_fileImpl.deltree(PropsValues.MODULE_FRAMEWORK_STATE_DIR);

		_fileImpl.mkdirs(PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/static");

		ToolDependencies.wireCaches();

		Registry registry = RegistryUtil.getRegistry();

		final MultiVMPool testMulitVMPool = registry.getService(
			MultiVMPool.class);
		final SingleVMPool testSingleVMPool = registry.getService(
			SingleVMPool.class);

		RegistryUtil.setRegistry(
			new BasicRegistryImpl() {

				@Override
				public Registry setRegistry(Registry registry) {
					registry.registerService(
						MultiVMPool.class, testMulitVMPool);

					registry.registerService(
						SingleVMPool.class, testSingleVMPool);

					return registry;
				}

			});

		InitUtil.init();

		ReflectionTestUtil.setFieldValue(InitUtil.class, "_initialized", false);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.bootstrap.ModuleFrameworkImpl",
					Level.ERROR)) {

			InitUtil.initWithSpring(
				Arrays.asList("META-INF/util-spring.xml"), true, true);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Missing " +
					Paths.get(
						PropsValues.LIFERAY_LIB_PORTAL_DIR, "util-taglib.jar"),
				loggingEvent.getRenderedMessage());
		}
		finally {
			if (resourceActionsReadPortletResources == null) {
				SystemProperties.clear(
					_RESOURCE_ACTIONS_READ_PORTLET_RESOURCES);
			}
			else {
				SystemProperties.set(
					_RESOURCE_ACTIONS_READ_PORTLET_RESOURCES,
					resourceActionsReadPortletResources);
			}

			if (log4jConfigureOnStartup == null) {
				SystemProperties.clear(_LOG4J_CONFIGURE_ON_STARTUP);
			}
			else {
				SystemProperties.set(
					_LOG4J_CONFIGURE_ON_STARTUP, log4jConfigureOnStartup);
			}

			InitUtil.stopRuntime();

			InitUtil.stopModuleFramework();
		}
	}

	@Rule
	public LogAssertionTestRule logAssertionTestRule =
		LogAssertionTestRule.INSTANCE;

	private static final String _LOG4J_CONFIGURE_ON_STARTUP =
		"log4j.configure.on.startup";

	private static final String _RESOURCE_ACTIONS_READ_PORTLET_RESOURCES =
		PropsFiles.PORTAL + StringPool.COLON +
			PropsKeys.RESOURCE_ACTIONS_READ_PORTLET_RESOURCES;

	private final FileImpl _fileImpl = new FileImpl();

}