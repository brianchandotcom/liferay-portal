/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.authenticator.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
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
public class LDAPAuthConfigurationProviderImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_defaultLDAPAuthConfiguration = ConfigurableUtil.createConfigurable(
			LDAPAuthConfiguration.class, Collections.emptyMap());
	}

	@Test
	public void testGetDefaultSettings() throws Exception {
		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(_companyId);

		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.enabled(),
			ldapAuthConfiguration.enabled());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.method(),
			ldapAuthConfiguration.method());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.passwordEncryptionAlgorithm(),
			ldapAuthConfiguration.passwordEncryptionAlgorithm());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.passwordPolicyEnabled(),
			ldapAuthConfiguration.passwordPolicyEnabled());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.required(),
			ldapAuthConfiguration.required());
	}

	@Test
	public void testGetInstanceSettings() throws Exception {
		String pid = ConfigurationTestUtil.createFactoryConfiguration(
			LDAPAuthConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", _companyId
			).put(
				"enabled", true
			).put(
				"required", true
			).build());

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteFactoryConfiguration(
			pid, LDAPAuthConfiguration.class.getName());

		Assert.assertTrue(ldapAuthConfiguration.enabled());
		Assert.assertTrue(ldapAuthConfiguration.required());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.method(),
			ldapAuthConfiguration.method());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.passwordEncryptionAlgorithm(),
			ldapAuthConfiguration.passwordEncryptionAlgorithm());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.passwordPolicyEnabled(),
			ldapAuthConfiguration.passwordPolicyEnabled());
	}

	@Test
	public void testGetSystemSettings() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			LDAPAuthConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", 0
			).put(
				"enabled", true
			).put(
				"required", false
			).build());

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(_companyId);

		ConfigurationTestUtil.deleteConfiguration(
			LDAPAuthConfiguration.class.getName());

		Assert.assertTrue(ldapAuthConfiguration.enabled());
		Assert.assertFalse(ldapAuthConfiguration.required());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.method(),
			ldapAuthConfiguration.method());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.passwordEncryptionAlgorithm(),
			ldapAuthConfiguration.passwordEncryptionAlgorithm());
		Assert.assertEquals(
			_defaultLDAPAuthConfiguration.passwordPolicyEnabled(),
			ldapAuthConfiguration.passwordPolicyEnabled());
	}

	private static long _companyId;
	private static LDAPAuthConfiguration _defaultLDAPAuthConfiguration;

	@Inject(
		filter = "factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration"
	)
	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;

}