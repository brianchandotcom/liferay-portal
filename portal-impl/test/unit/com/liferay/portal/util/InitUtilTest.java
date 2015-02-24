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
		String property = SystemProperties.get("log4j.configure.on.startup");

		SystemProperties.set("log4j.configure.on.startup", StringPool.FALSE);

		try {
			InitUtil.initWithSpring(
				Arrays.asList(
					"META-INF/management-spring.xml",
					"META-INF/util-spring.xml"),
				true);
		}
		finally {
			if (property == null) {
				SystemProperties.clear("log4j.configure.on.startup");
			}
			else {
				SystemProperties.set("log4j.configure.on.startup", property);
			}
		}
	}

	@Rule
	public LogAssertionTestRule logAssertionTestRule =
		LogAssertionTestRule.INSTANCE;

}