/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class ConfigurationAdminTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		if (_configuration != null) {
			_configuration.delete();
		}
	}

	@Test
	public void testConfigurationUpdate() throws Exception {
		_configuration = _configurationAdmin.getConfiguration(
			ConfigurationAdminTest.class.getName());

		_configuration.update(MapUtil.singletonDictionary("key1", "value1"));

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + ConfigurationAdminTest.class.getName() + ")");

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		Configuration configuration = configurations[0];

		Assert.assertEquals(_configuration, configuration);

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertEquals("value1", properties.get("key1"));

		configuration.update(MapUtil.singletonDictionary("key1", "value2"));

		properties = configuration.getProperties();

		Assert.assertEquals("value2", properties.get("key1"));

		configuration = _configurationAdmin.getConfiguration(
			ConfigurationAdminTest.class.getName());

		properties = configuration.getProperties();

		Assert.assertEquals("value2", properties.get("key1"));
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private Configuration _configuration;

}