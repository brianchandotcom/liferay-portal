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

package com.liferay.portal.kernel.resiliency.spi.remote;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SystemPropertiesProcessCallableTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testSystemPropertiesProcessCallable() {
		Properties oldProperties = System.getProperties();

		Properties newProperties = new Properties();

		System.setProperties(newProperties);

		Map<String, String> propertiesMap = new HashMap<>();

		propertiesMap.put("key1", "value1");
		propertiesMap.put("key2", "value2");
		propertiesMap.put("key3", "value3");

		SystemPropertiesProcessCallable systemPropertiesProcessCallable =
			new SystemPropertiesProcessCallable(propertiesMap);

		systemPropertiesProcessCallable.call();

		Assert.assertEquals(3, newProperties.size());

		Assert.assertEquals("value1", newProperties.getProperty("key1"));
		Assert.assertEquals("value2", newProperties.getProperty("key2"));
		Assert.assertEquals("value3", newProperties.getProperty("key3"));

		System.setProperties(oldProperties);
	}

}