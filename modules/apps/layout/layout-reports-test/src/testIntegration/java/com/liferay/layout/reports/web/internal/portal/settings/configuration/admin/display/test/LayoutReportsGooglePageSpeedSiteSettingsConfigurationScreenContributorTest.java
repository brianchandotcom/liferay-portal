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

package com.liferay.layout.reports.web.internal.portal.settings.configuration.admin.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class
	LayoutReportsGooglePageSpeedSiteSettingsConfigurationScreenContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testIsVisible() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"apiKey", RandomTestUtil.randomString()
					).put(
						"enabled", true
					).build())) {

			Assert.assertTrue(
				_siteSettingsConfigurationScreenContributor.isVisible(_group));
		}
	}

	@Test
	public void testIsVisibleWithDisabledCompanyConfiguration()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			try (CompanyConfigurationTemporarySwapper
					companyConfigurationTemporarySwapper =
						new CompanyConfigurationTemporarySwapper(
							_group.getCompanyId(),
							"com.liferay.layout.reports.web.internal." +
								"configuration.LayoutReportsGooglePageSpeed" +
									"CompanyConfiguration",
							HashMapDictionaryBuilder.<String, Object>put(
								"enabled", false
							).build(),
							_settingsFactory)) {

				Assert.assertFalse(
					_siteSettingsConfigurationScreenContributor.isVisible(
						_group));
			}
		}
	}

	@Test
	public void testIsVisibleWithDisabledConfiguration() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", false
					).build())) {

			Assert.assertFalse(
				_siteSettingsConfigurationScreenContributor.isVisible(_group));
		}
	}

	@Test
	public void testIsVisibleWithEnabledCompanyConfiguration()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			try (CompanyConfigurationTemporarySwapper
					companyConfigurationTemporarySwapper =
						new CompanyConfigurationTemporarySwapper(
							_group.getCompanyId(),
							"com.liferay.layout.reports.web.internal." +
								"configuration.LayoutReportsGooglePageSpeed" +
									"CompanyConfiguration",
							HashMapDictionaryBuilder.<String, Object>put(
								"enabled", true
							).build(),
							_settingsFactory)) {

				Assert.assertTrue(
					_siteSettingsConfigurationScreenContributor.isVisible(
						_group));
			}
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private SettingsFactory _settingsFactory;

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.portal.settings.configuration.admin.display.LayoutReportsGooglePageSpeedSiteSettingsConfigurationScreenContributor"
	)
	private SiteSettingsConfigurationScreenContributor
		_siteSettingsConfigurationScreenContributor;

}