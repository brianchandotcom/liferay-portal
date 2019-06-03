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

package com.liferay.portal.configuration.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.apache.felix.cm.PersistenceManager;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class ConfigurationPersistenceManagerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testConfigurationPersistenceManager() {
		Class<?> clazz = _persistenceManager.getClass();

		Assert.assertEquals(
			"com.liferay.portal.configuration.persistence.internal." +
				"ConfigurationPersistenceManager",
			clazz.getName());
	}

	@Test
	public void testCreateFactoryConfiguration() throws Exception {
		_assertConfiguration(true);
	}

	@Test
	public void testGetConfiguration() throws Exception {
		_assertConfiguration(false);
	}

	private void _assertConfiguration(boolean factory) throws Exception {
		Configuration configuration = null;

		if (factory) {
			configuration = _configurationAdmin.getConfiguration("test.pid");

			Assert.assertTrue(_persistenceManager.exists("test.pid"));
		}
		else {
			configuration = _configurationAdmin.createFactoryConfiguration(
				"test.pid", StringPool.QUESTION);
		}

		String pid = configuration.getPid();

		ConfigurationTestUtil.saveConfiguration(
			configuration, MapUtil.singletonDictionary("foo", "bar"));

		Assert.assertTrue(_persistenceManager.exists(pid));

		Dictionary<String, Object> properties = _persistenceManager.load(pid);

		Assert.assertEquals("bar", properties.get("foo"));

		properties.put("fee", "fum");

		ConfigurationTestUtil.saveConfiguration(configuration, properties);

		properties = _persistenceManager.load(pid);

		Assert.assertEquals("bar", properties.get("foo"));
		Assert.assertEquals("fum", properties.get("fee"));

		ConfigurationTestUtil.deleteConfiguration(configuration);

		Assert.assertFalse(_persistenceManager.exists(pid));
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private static PersistenceManager _persistenceManager;

}