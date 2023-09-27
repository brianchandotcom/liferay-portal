/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testModifyConfiguration() throws Exception {
		_testModifyConfiguration(TestPropsValues.COMPANY_WEB_ID);
		_testModifyConfiguration("default");
	}

	private void _testModifyConfiguration(String webId) throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"test.pid");

		ConfigurationTestUtil.saveConfiguration(
			configuration,
			MapUtil.singletonDictionary(
				"dxp.lxc.liferay.com.virtualInstanceId", webId));

		configuration = _configurationAdmin.getConfiguration("test.pid");

		Dictionary<String, Object> properties =
			configuration.getProcessedProperties(null);

		Object companyIdObject = properties.get("companyId");

		Assert.assertNotNull(companyIdObject);

		Assert.assertEquals(TestPropsValues.getCompanyId(), companyIdObject);

		ConfigurationTestUtil.deleteConfiguration(configuration);
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

}