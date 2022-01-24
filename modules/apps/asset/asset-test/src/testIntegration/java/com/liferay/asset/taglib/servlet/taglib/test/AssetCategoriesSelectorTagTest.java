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

package com.liferay.asset.taglib.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.taglib.servlet.taglib.AssetCategoriesSelectorTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class AssetCategoriesSelectorTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_internalAssetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.getSiteDefault()),
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.getSiteDefault()),
			StringPool.BLANK, AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL,
			serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(),
			_internalAssetVocabulary.getVocabularyId(), serviceContext);

		_publicAssetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.getSiteDefault()),
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.getSiteDefault()),
			StringPool.BLANK, AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC,
			serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(),
			_publicAssetVocabulary.getVocabularyId(), serviceContext);
	}

	@Test
	public void testDefaultAssetVocabulariesVisibility() throws Exception {
		AssetCategoriesSelectorTag assetCategoriesSelectorTag =
			new AssetCategoriesSelectorTag();

		assetCategoriesSelectorTag.setClassName(AssetEntry.class.getName());
		assetCategoriesSelectorTag.setGroupIds(
			new long[] {_group.getGroupId()});

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		assetCategoriesSelectorTag.doTag(
			_getMockHttpServletRequest(), mockHttpServletResponse);

		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.containsString(
				_publicAssetVocabulary.getTitle(LocaleUtil.getSiteDefault())));

		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.not(
				CoreMatchers.containsString(
					_internalAssetVocabulary.getTitle(
						LocaleUtil.getSiteDefault()))));
	}

	@Test
	public void testInternalAndPublicAssetVocabulariesVisibility()
		throws Exception {

		AssetCategoriesSelectorTag assetCategoriesSelectorTag =
			new AssetCategoriesSelectorTag();

		assetCategoriesSelectorTag.setClassName(AssetEntry.class.getName());
		assetCategoriesSelectorTag.setGroupIds(
			new long[] {_group.getGroupId()});

		assetCategoriesSelectorTag.setVisibilityTypes(
			AssetVocabularyConstants.VISIBILITY_TYPES);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		assetCategoriesSelectorTag.doTag(
			_getMockHttpServletRequest(), mockHttpServletResponse);

		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.containsString(
				_internalAssetVocabulary.getTitle(
					LocaleUtil.getSiteDefault())));
		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.containsString(
				_publicAssetVocabulary.getTitle(LocaleUtil.getSiteDefault())));
	}

	@Test
	public void testInternalAssetVocabularyVisibility() throws Exception {
		AssetCategoriesSelectorTag assetCategoriesSelectorTag =
			new AssetCategoriesSelectorTag();

		assetCategoriesSelectorTag.setClassName(AssetEntry.class.getName());
		assetCategoriesSelectorTag.setGroupIds(
			new long[] {_group.getGroupId()});

		assetCategoriesSelectorTag.setVisibilityTypes(
			new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL});

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		assetCategoriesSelectorTag.doTag(
			_getMockHttpServletRequest(), mockHttpServletResponse);

		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.containsString(
				_internalAssetVocabulary.getTitle(
					LocaleUtil.getSiteDefault())));
		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.not(
				CoreMatchers.containsString(
					_publicAssetVocabulary.getTitle(
						LocaleUtil.getSiteDefault()))));
	}

	@Test
	public void testPublicAssetVocabularyVisibility() throws Exception {
		AssetCategoriesSelectorTag assetCategoriesSelectorTag =
			new AssetCategoriesSelectorTag();

		assetCategoriesSelectorTag.setClassName(AssetEntry.class.getName());
		assetCategoriesSelectorTag.setGroupIds(
			new long[] {_group.getGroupId()});

		assetCategoriesSelectorTag.setVisibilityTypes(
			new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC});

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		assetCategoriesSelectorTag.doTag(
			_getMockHttpServletRequest(), mockHttpServletResponse);

		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.containsString(
				_publicAssetVocabulary.getTitle(LocaleUtil.getSiteDefault())));
		Assert.assertThat(
			mockHttpServletResponse.getContentAsString(),
			CoreMatchers.not(
				CoreMatchers.containsString(
					_internalAssetVocabulary.getTitle(
						LocaleUtil.getSiteDefault()))));
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		return mockHttpServletRequest;
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private AssetVocabulary _internalAssetVocabulary;
	private AssetVocabulary _publicAssetVocabulary;

}