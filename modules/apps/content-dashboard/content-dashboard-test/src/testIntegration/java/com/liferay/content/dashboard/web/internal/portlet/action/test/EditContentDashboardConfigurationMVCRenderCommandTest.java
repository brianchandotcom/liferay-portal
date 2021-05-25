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

package com.liferay.content.dashboard.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
@Sync
public class EditContentDashboardConfigurationMVCRenderCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = _companyLocalService.fetchCompany(
			TestPropsValues.getCompanyId());

		_audienceAssetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				_company.getGroupId(), "audience");

		_stageAssetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				_company.getGroupId(), "stage");
	}

	@Test
	public void testGetAvailableVocabularyNames() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest(
				_audienceAssetVocabulary.getName(),
				_stageAssetVocabulary.getName());

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		List<KeyValuePair> keyValuePairs = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT"),
			"getAvailableVocabularyNames", new Class<?>[0]);

		Assert.assertFalse(
			keyValuePairs.contains(
				new KeyValuePair(
					_audienceAssetVocabulary.getName(),
					_audienceAssetVocabulary.getTitle(_locale))));

		Assert.assertFalse(
			keyValuePairs.contains(
				new KeyValuePair(
					_stageAssetVocabulary.getName(),
					_stageAssetVocabulary.getTitle(_locale))));
	}

	@Test
	public void testGetAvailableVocabularyNamesWithAudienceAssetVocabulary()
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest(
				_audienceAssetVocabulary.getName());

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		List<KeyValuePair> keyValuePairs = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT"),
			"getAvailableVocabularyNames", new Class<?>[0]);

		Assert.assertFalse(
			keyValuePairs.contains(
				new KeyValuePair(
					_audienceAssetVocabulary.getName(),
					_audienceAssetVocabulary.getTitle(_locale))));

		Assert.assertTrue(
			keyValuePairs.contains(
				new KeyValuePair(
					_stageAssetVocabulary.getName(),
					_stageAssetVocabulary.getTitle(_locale))));
	}

	@Test
	public void testGetCurrentVocabularyNames() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest(
				_audienceAssetVocabulary.getName(),
				_stageAssetVocabulary.getName());

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		List<KeyValuePair> keyValuePairs = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT"),
			"getCurrentVocabularyNames", new Class<?>[0]);

		Assert.assertEquals(keyValuePairs.toString(), 2, keyValuePairs.size());

		Assert.assertEquals(
			keyValuePairs.get(0),
			new KeyValuePair(
				_audienceAssetVocabulary.getName(),
				_audienceAssetVocabulary.getTitle(_locale)));

		Assert.assertEquals(
			keyValuePairs.get(1),
			new KeyValuePair(
				_stageAssetVocabulary.getName(),
				_stageAssetVocabulary.getTitle(_locale)));
	}

	@Test
	public void testGetCurrentVocabularyNamesWithAudienceAssetVocabulary()
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest(
				_audienceAssetVocabulary.getName());

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		List<KeyValuePair> keyValuePairs = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT"),
			"getCurrentVocabularyNames", new Class<?>[0]);

		Assert.assertEquals(keyValuePairs.toString(), 1, keyValuePairs.size());

		Assert.assertEquals(
			keyValuePairs.get(0),
			new KeyValuePair(
				_audienceAssetVocabulary.getName(),
				_audienceAssetVocabulary.getTitle(_locale)));
	}

	private MockLiferayPortletRenderRequest _getMockLiferayPortletRenderRequest(
			String... assetVocabularyNames)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		PortletPreferences portletPreferences =
			mockLiferayPortletRenderRequest.getPreferences();

		portletPreferences.setValues(
			"assetVocabularyNames", assetVocabularyNames);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletRenderRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLanguageId(LanguageUtil.getLanguageId(_locale));
		themeDisplay.setLocale(_locale);

		return themeDisplay;
	}

	@Inject
	private static AssetVocabularyLocalService _assetVocabularyLocalService;

	private static AssetVocabulary _audienceAssetVocabulary;
	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static final Locale _locale = LocaleUtil.US;
	private static AssetVocabulary _stageAssetVocabulary;

	@Inject(
		filter = "mvc.command.name=/content_dashboard/edit_content_dashboard_configuration",
		type = MVCRenderCommand.class
	)
	private MVCRenderCommand _mvcRenderCommand;

}