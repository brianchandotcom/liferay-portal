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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LogAssertionTestRule;

import java.util.Arrays;

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

		try {
			InitUtil.initWithSpring(
				Arrays.asList("META-INF/util-spring.xml"), true);
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