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

package com.liferay.portal.configuration.plugin.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MapUtil;
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
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class WebIdToCompanyConfigurationPluginImplTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDefaultVirtualInstanceId() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"test.pid");

		ConfigurationTestUtil.saveConfiguration(
			configuration,
			MapUtil.singletonDictionary(
				"dxp.lxc.liferay.com.virtualInstanceId", "default"));

		configuration = _configurationAdmin.getConfiguration("test.pid");

		Dictionary<String, Object> properties =
			configuration.getProcessedProperties(null);

		Object companyIdProp = properties.get("companyId");

		Assert.assertNotNull(companyIdProp);

		Assert.assertEquals(TestPropsValues.getCompanyId(), companyIdProp);

		ConfigurationTestUtil.deleteConfiguration(configuration);
	}

	@Test
	public void testVirtualInstanceId() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"test.pid");

		ConfigurationTestUtil.saveConfiguration(
			configuration,
			MapUtil.singletonDictionary(
				"dxp.lxc.liferay.com.virtualInstanceId",
				TestPropsValues.COMPANY_WEB_ID));

		configuration = _configurationAdmin.getConfiguration("test.pid");

		Dictionary<String, Object> properties =
			configuration.getProcessedProperties(null);

		Object companyIdProp = properties.get("companyId");

		Assert.assertNotNull(companyIdProp);

		Assert.assertEquals(TestPropsValues.getCompanyId(), companyIdProp);

		ConfigurationTestUtil.deleteConfiguration(configuration);
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

}