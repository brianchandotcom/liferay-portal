/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.configuration.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class CookiesConfigurationProviderImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(_group);
	}

	@Test
	public void testIsCookiesPreferenceHandlingActiveFallsBackFromCompanyToSystem()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CookiesPreferenceHandlingConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"active", true
					).put(
						"enabled", true
					).build())) {

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.COMPANY, companyId));
		}
	}

	@Test
	public void testIsCookiesPreferenceHandlingActiveFallsBackFromGroupToCompany()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long groupId = _group.getGroupId();

		try (ScopedFactoryConfiguration scopedFactoryConfiguration =
				new ScopedFactoryConfiguration(
					_createCompanyDictionary(companyId, true))) {

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.GROUP, groupId));
		}
	}

	@Test
	public void testIsCookiesPreferenceHandlingActivePrioritizesCompanyOverSystem()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CookiesPreferenceHandlingConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"active", true
					).put(
						"enabled", true
					).build());
			ScopedFactoryConfiguration scopedFactoryConfiguration =
				new ScopedFactoryConfiguration(
					_createCompanyDictionary(companyId, false))) {

			Assert.assertFalse(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.COMPANY, companyId));

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.SYSTEM, 0));
		}
	}

	@Test
	public void testIsCookiesPreferenceHandlingActivePrioritizesGroupOverCompany()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long groupId = _group.getGroupId();

		try (ScopedFactoryConfiguration companyScopedFactoryConfiguration =
				new ScopedFactoryConfiguration(
					_createCompanyDictionary(companyId, false));
			ScopedFactoryConfiguration groupScopedFactoryConfiguration =
				new ScopedFactoryConfiguration(
					_createGroupDictionary(companyId, groupId, true))) {

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.GROUP, groupId));

			Assert.assertFalse(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.COMPANY, companyId));
		}
	}

	@Test
	public void testIsCookiesPreferenceHandlingActiveWithCompanyScope()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		try (ScopedFactoryConfiguration scopedFactoryConfiguration =
				new ScopedFactoryConfiguration(
					_createCompanyDictionary(companyId, true))) {

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.COMPANY, companyId));
		}
	}

	@Test
	public void testIsCookiesPreferenceHandlingActiveWithGroupScope()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long groupId = _group.getGroupId();

		try (ScopedFactoryConfiguration scopedFactoryConfiguration =
				new ScopedFactoryConfiguration(
					_createGroupDictionary(companyId, groupId, true))) {

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.GROUP, groupId));
		}
	}

	@Test
	public void testIsCookiesPreferenceHandlingActiveWithSystemScope()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CookiesPreferenceHandlingConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"active", true
					).put(
						"enabled", true
					).build())) {

			Assert.assertTrue(
				_cookiesConfigurationProvider.isCookiesPreferenceHandlingActive(
					ExtendedObjectClassDefinition.Scope.SYSTEM, 0));
		}
	}

	private Dictionary<String, Object> _createCompanyDictionary(
		long companyId, boolean active) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"active", active
		).put(
			"companyId", companyId
		).put(
			"enabled", true
		).build();
	}

	private Dictionary<String, Object> _createGroupDictionary(
		long companyId, long groupId, boolean active) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"active", active
		).put(
			"companyId", companyId
		).put(
			"enabled", true
		).put(
			"groupId", groupId
		).build();
	}

	private static final String _SCOPED_FACTORY_PID =
		CookiesPreferenceHandlingConfiguration.class.getName() + ".scoped";

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private CookiesConfigurationProvider _cookiesConfigurationProvider;

	private Group _group;

	private class ScopedFactoryConfiguration implements AutoCloseable {

		public ScopedFactoryConfiguration(Dictionary<String, Object> properties)
			throws Exception {

			_configuration = _configurationAdmin.createFactoryConfiguration(
				_SCOPED_FACTORY_PID, StringPool.QUESTION);

			ConfigurationTestUtil.saveConfiguration(_configuration, properties);
		}

		@Override
		public void close() throws Exception {
			ConfigurationTestUtil.deleteConfiguration(_configuration);
		}

		private final Configuration _configuration;

	}

}
