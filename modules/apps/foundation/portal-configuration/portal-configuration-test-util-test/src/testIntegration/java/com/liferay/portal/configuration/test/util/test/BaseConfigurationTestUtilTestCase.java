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

package com.liferay.portal.configuration.test.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public abstract class BaseConfigurationTestUtilTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		configurationPid = "TestPID_" + RandomTestUtil.randomString();
	}

	@After
	public void tearDown() throws Exception {
		if (testConfigurationExists()) {
			Configuration configuration = getConfiguration();

			configuration.delete();
		}
	}

	protected Configuration getConfiguration() throws Exception {
		return _configurationAdmin.getConfiguration(
			configurationPid, StringPool.QUESTION);
	}

	protected boolean testConfigurationExists() {
		return persistenceManager.exists(configurationPid);
	}

	@Inject
	protected static PersistenceManager persistenceManager;

	protected String configurationPid;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

}