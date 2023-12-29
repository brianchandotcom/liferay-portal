/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.exportimport.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
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
public class LDAPExportConfigurationProviderImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_defaultLDAPExportConfiguration = ConfigurableUtil.createConfigurable(
			LDAPExportConfiguration.class, Collections.emptyMap());
	}

	@Test
	public void testGetDefaultSettings() throws Exception {
		LDAPExportConfiguration ldapExportConfiguration =
			_ldapExportConfigurationProvider.getConfiguration(_companyId);

		Assert.assertEquals(
			_defaultLDAPExportConfiguration.exportEnabled(),
			ldapExportConfiguration.exportEnabled());

		Assert.assertEquals(
			_defaultLDAPExportConfiguration.exportGroupEnabled(),
			ldapExportConfiguration.exportGroupEnabled());
	}

	@Test
	public void testGetInstanceSettings() throws Exception {
		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			LDAPExportConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", _companyId
			).put(
				"exportEnabled", false
			).put(
				"exportGroupEnabled", false
			).build());

		LDAPExportConfiguration ldapExportConfiguration =
			_ldapExportConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteFactoryConfiguration(
			pid, LDAPExportConfiguration.class.getName());

		Assert.assertFalse(ldapExportConfiguration.exportEnabled());
		Assert.assertFalse(ldapExportConfiguration.exportGroupEnabled());
	}

	@Test
	public void testGetSystemSettings() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			LDAPExportConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", 0
			).put(
				"exportEnabled", true
			).put(
				"exportGroupEnabled", true
			).build());

		LDAPExportConfiguration ldapExportConfiguration =
			_ldapExportConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteConfiguration(
			LDAPExportConfiguration.class.getName());

		Assert.assertTrue(ldapExportConfiguration.exportEnabled());
		Assert.assertTrue(ldapExportConfiguration.exportGroupEnabled());
	}

	private static long _companyId;
	private static LDAPExportConfiguration _defaultLDAPExportConfiguration;

	@Inject(
		filter = "factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration"
	)
	private ConfigurationProvider<LDAPExportConfiguration>
		_ldapExportConfigurationProvider;

}