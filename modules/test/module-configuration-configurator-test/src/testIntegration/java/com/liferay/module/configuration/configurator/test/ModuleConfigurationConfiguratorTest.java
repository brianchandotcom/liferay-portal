/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.module.configuration.configurator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class ModuleConfigurationConfiguratorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testConfigurationConfigurator() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=com.liferay.module.configuration." +
				"configurator.internal.TestFactoryConfiguration)");

		Assert.assertEquals(
			configurations.toString(), 2, configurations.length);

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String type = (String)properties.get("type");

			Assert.assertEquals(
				configuration.getFactoryPid() + "~" + type,
				configuration.getPid());

			if (type.equals("enabled")) {
				Assert.assertTrue((boolean)properties.get("enabled"));
			}
			else {
				Assert.assertFalse((boolean)properties.get("enabled"));
			}
		}
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

}