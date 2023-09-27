/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
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
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OrganizationTypeConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpdateConfigurationName() throws Exception {
		Configuration configuration = _addOrganizationTypeConfiguration();

		Dictionary<String, Object> properties = configuration.getProperties();

		String name = (String)properties.get("name");

		properties.put("name", RandomTestUtil.randomString());

		try {
			configuration.update(properties);
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			Assert.assertEquals(
				"Organization type name cannot be changed.",
				configurationModelListenerException.causeMessage);
		}
		finally {
			properties = configuration.getProperties();

			Assert.assertEquals(name, properties.get("name"));

			configuration.delete();
		}
	}

	private Configuration _addOrganizationTypeConfiguration() throws Exception {
		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"name", RandomTestUtil.randomString()
			).build();

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				_ORGANIZATION_TYPE_CONFIGURATION_PID, StringPool.QUESTION);

		configuration.update(properties);

		return configuration;
	}

	private static final String _ORGANIZATION_TYPE_CONFIGURATION_PID =
		"com.liferay.organizations.internal.configuration." +
			"OrganizationTypeConfiguration";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

}