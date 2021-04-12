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

package com.liferay.layout.reports.web.internal.frontend.taglib.form.navigator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.portal.configuration.test.util.ConfigurationCompanyTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
public class LayoutReportsGooglePageSpeedSiteFormNavigatorEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testIsVisible() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("apiKey", RandomTestUtil.randomString());
							put("enabled", true);
						}
					})) {

			Assert.assertTrue(
				_formNavigatorEntry.isVisible(
					TestPropsValues.getUser(), _group));
		}
	}

	@Test
	public void testIsVisibleWithDisabledCompanyConfiguration()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("enabled", true);
						}
					})) {

			try (ConfigurationCompanyTemporarySwapper
					configurationCompanyTemporarySwapper =
						new ConfigurationCompanyTemporarySwapper(
							_group.getCompanyId(),
							"com.liferay.layout.reports.web.internal." +
								"configuration.LayoutReportsGooglePageSpeed" +
									"CompanyConfiguration",
							new HashMapDictionary<String, Object>() {
								{
									put("enabled", false);
								}
							},
							_settingsFactory)) {

				Assert.assertFalse(
					_formNavigatorEntry.isVisible(
						TestPropsValues.getUser(), _group));
			}
		}
	}

	@Test
	public void testIsVisibleWithDisabledConfiguration() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("enabled", false);
						}
					})) {

			Assert.assertFalse(
				_formNavigatorEntry.isVisible(
					TestPropsValues.getUser(), _group));
		}
	}

	@Test
	public void testIsVisibleWithEnabledCompanyConfiguration()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("enabled", true);
						}
					})) {

			try (ConfigurationCompanyTemporarySwapper
					configurationCompanyTemporarySwapper =
						new ConfigurationCompanyTemporarySwapper(
							_group.getCompanyId(),
							"com.liferay.layout.reports.web.internal." +
								"configuration.LayoutReportsGooglePageSpeed" +
									"CompanyConfiguration",
							new HashMapDictionary<String, Object>() {
								{
									put("enabled", true);
								}
							},
							_settingsFactory)) {

				Assert.assertTrue(
					_formNavigatorEntry.isVisible(
						TestPropsValues.getUser(), _group));
			}
		}
	}

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.frontend.taglib.form.navigator.LayoutReportsGooglePageSpeedSiteFormNavigatorEntry"
	)
	private FormNavigatorEntry _formNavigatorEntry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SettingsFactory _settingsFactory;

}