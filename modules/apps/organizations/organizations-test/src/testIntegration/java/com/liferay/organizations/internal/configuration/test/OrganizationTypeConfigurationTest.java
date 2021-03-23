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

package com.liferay.organizations.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
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
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("name", RandomTestUtil.randomString());

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