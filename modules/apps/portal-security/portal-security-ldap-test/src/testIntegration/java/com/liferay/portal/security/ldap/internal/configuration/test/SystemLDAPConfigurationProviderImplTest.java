/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class SystemLDAPConfigurationProviderImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_defaultSystemLDAPConfiguration = ConfigurableUtil.createConfigurable(
			SystemLDAPConfiguration.class, Collections.emptyMap());
	}

	@Test
	public void testGetDefaultSettings() throws Exception {
		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(_companyId);

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.factoryInitial(),
			systemLDAPConfiguration.factoryInitial());

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.referral(),
			systemLDAPConfiguration.referral());

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.pageSize(),
			systemLDAPConfiguration.pageSize());
		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.rangeSize(),
			systemLDAPConfiguration.rangeSize());
	}

	@Test
	public void testGetInstanceSettings() throws Exception {
		int randomValue = RandomTestUtil.randomInt(0, 1000);

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			SystemLDAPConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", _companyId
			).put(
				"pageSize", randomValue
			).put(
				"rangeSize", randomValue
			).build());

		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteFactoryConfiguration(
			pid, SystemLDAPConfiguration.class.getName());

		Assert.assertEquals(systemLDAPConfiguration.pageSize(), randomValue);
		Assert.assertEquals(systemLDAPConfiguration.rangeSize(), randomValue);

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.factoryInitial(),
			systemLDAPConfiguration.factoryInitial());

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.referral(),
			systemLDAPConfiguration.referral());
	}

	@Test
	public void testGetInstanceSettingsWithSystemSettings() throws Exception {
		int randomValueSystem = RandomTestUtil.randomInt(0, 1000);

		ConfigurationTestUtil.saveConfiguration(
			SystemLDAPConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", 0
			).put(
				"pageSize", randomValueSystem
			).put(
				"rangeSize", randomValueSystem
			).build());

		int randomValueInstance = RandomTestUtil.randomInt(0, 1000);

		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			SystemLDAPConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", _companyId
			).put(
				"pageSize", randomValueInstance
			).put(
				"rangeSize", randomValueInstance
			).build());

		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteFactoryConfiguration(
			pid, SystemLDAPConfiguration.class.getName());

		Assert.assertEquals(systemLDAPConfiguration.pageSize(), randomValueInstance);
		Assert.assertEquals(systemLDAPConfiguration.rangeSize(), randomValueInstance);

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.factoryInitial(),
			systemLDAPConfiguration.factoryInitial());

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.referral(),
			systemLDAPConfiguration.referral());
	}

	@Test
	public void testGetSystemSettings() throws Exception {
		int randomValue = RandomTestUtil.randomInt(0, 1000);

		ConfigurationTestUtil.saveConfiguration(
			SystemLDAPConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", 0
			).put(
				"pageSize", randomValue
			).put(
				"rangeSize", randomValue
			).build());

		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteConfiguration(
			SystemLDAPConfiguration.class.getName());

		Assert.assertEquals(systemLDAPConfiguration.pageSize(), randomValue);
		Assert.assertEquals(systemLDAPConfiguration.rangeSize(), randomValue);

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.factoryInitial(),
			systemLDAPConfiguration.factoryInitial());

		Assert.assertEquals(
			_defaultSystemLDAPConfiguration.referral(),
			systemLDAPConfiguration.referral());
	}

	private static long _companyId;
	private static SystemLDAPConfiguration _defaultSystemLDAPConfiguration;

	@Inject(
		filter = "factoryPid=com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration"
	)
	private ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;

}