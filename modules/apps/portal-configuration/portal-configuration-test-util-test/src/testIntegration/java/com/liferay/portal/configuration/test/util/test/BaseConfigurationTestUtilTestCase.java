/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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