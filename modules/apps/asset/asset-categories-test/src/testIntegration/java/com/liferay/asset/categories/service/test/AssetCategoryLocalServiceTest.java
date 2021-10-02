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

package com.liferay.asset.categories.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
import com.liferay.asset.kernel.exception.AssetCategoryLimitException;
import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class AssetCategoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		_assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(), "Vocabulary",
			new ServiceContext());
	}

	@Test
	public void testAddAssetCategory() throws PortalException {
		String expectedAssetCategoryTitle = "Título";

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			expectedAssetCategoryTitle, _assetVocabulary.getVocabularyId(),
			new ServiceContext());

		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			assetCategory.getTitle(LocaleUtil.SPAIN));

		Map<Locale, String> titleMap = assetCategory.getTitleMap();

		Assert.assertTrue(
			"Title map does not contains site default locale",
			titleMap.containsKey(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			titleMap.get(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map length does not match", 1, titleMap.size());
	}

	@Test
	public void testAddAssetCategoryWithMissingTranslationInSiteDefaultLocale()
		throws PortalException {

		expectedException.expect(AssetCategoryNameException.class);
		expectedException.expectMessage(
			"Category name cannot be null for category 0 and vocabulary " +
				_assetVocabulary.getVocabularyId());

		_assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			Collections.singletonMap(LocaleUtil.FRANCE, "Qualification"),
			Collections.singletonMap(LocaleUtil.FRANCE, "La description"),
			_assetVocabulary.getVocabularyId(), null, new ServiceContext());
	}

	@Test
	public void testAddAssetCategoryWithTranslationInSiteDefaultLocale()
		throws PortalException {

		String expectedAssetCategoryTitle = "Título";

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).put(
			LocaleUtil.SPAIN, expectedAssetCategoryTitle
		).build();

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "La description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build(),
			_assetVocabulary.getVocabularyId(), null, new ServiceContext());

		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			assetCategory.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map does not match", titleMap,
			assetCategory.getTitleMap());
	}

	@Test(expected = AssetCategoryLimitException.class)
	public void testAssetCategoryLimitExceeded() throws PortalException {
		try {
			_configurationProvider.saveCompanyConfiguration(
				AssetCategoriesCompanyConfiguration.class,
				_group.getCompanyId(),
				HashMapDictionaryBuilder.<String, Object>put(
					"maximumNumberOfCategoriesPerVocabulary", 3
				).build());

			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
		}
		finally {
			_configurationProvider.deleteCompanyConfiguration(
				AssetCategoriesCompanyConfiguration.class,
				_group.getCompanyId());
		}
	}

	@Test
	public void testUpdateAssetCategoryWithMissingTranslationInSiteDefaultLocale()
		throws PortalException {

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "Título",
			_assetVocabulary.getVocabularyId(), new ServiceContext());

		expectedException.expect(AssetCategoryNameException.class);
		expectedException.expectMessage(
			StringBundler.concat(
				"Category name cannot be null for category ",
				assetCategory.getCategoryId(), " and vocabulary ",
				assetCategory.getVocabularyId()));

		_assetCategoryLocalService.updateCategory(
			TestPropsValues.getUserId(), assetCategory.getCategoryId(),
			assetCategory.getParentCategoryId(),
			Collections.singletonMap(LocaleUtil.FRANCE, "Qualification"),
			Collections.singletonMap(LocaleUtil.FRANCE, "La description"),
			assetCategory.getVocabularyId(), null, new ServiceContext());
	}

	@Test
	public void testUpdateAssetCategoryWithTranslationInSiteDefaultLocale()
		throws PortalException {

		String expectedAssetCategoryTitle = "Título";

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			expectedAssetCategoryTitle, _assetVocabulary.getVocabularyId(),
			new ServiceContext());

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).put(
			LocaleUtil.SPAIN, expectedAssetCategoryTitle
		).build();

		assetCategory = _assetCategoryLocalService.updateCategory(
			TestPropsValues.getUserId(), assetCategory.getCategoryId(),
			assetCategory.getParentCategoryId(), titleMap,
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "La description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build(),
			assetCategory.getVocabularyId(), null, new ServiceContext());

		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			assetCategory.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map does not match", titleMap,
			assetCategory.getTitleMap());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@DeleteAfterTestRun
	private Group _group;

}