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
import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
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
	public void testAddCategory() throws PortalException {
		String expectedCategoryName = "Título";

		AssetCategory category = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			expectedCategoryName, _assetVocabulary.getVocabularyId(),
			new ServiceContext());

		Assert.assertEquals(
			"Expected name does not match", expectedCategoryName,
			category.getName());

		Map<Locale, String> titleMap = category.getTitleMap();

		Assert.assertTrue(
			"Title map does not contains site default locale",
			titleMap.containsKey(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title does not match", expectedCategoryName,
			titleMap.get(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map lenght does not match", 1, titleMap.size());
	}

	@Test
	public void testAddCategoryExistsTranslationInDefaultSiteLocale()
		throws PortalException {

		String expectedCategoryName = "Título";

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).put(
			LocaleUtil.SPAIN, expectedCategoryName
		).build();

		AssetCategory category = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "La description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build(),
			_assetVocabulary.getVocabularyId(), null, new ServiceContext());

		Assert.assertEquals(
			"Expected name does not match", expectedCategoryName,
			category.getName());

		Assert.assertEquals(
			"Expected title map does not match", titleMap,
			category.getTitleMap());
	}

	@Test
	public void testAddCategoryMissingTranslationInDefaultSiteLocale()
		throws PortalException {

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "La description"
		).build();

		expectedException.expect(AssetCategoryNameException.class);
		expectedException.expectMessage(
			"Category name cannot be null for category 0 and vocabulary " +
				_assetVocabulary.getVocabularyId());

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
			descriptionMap, _assetVocabulary.getVocabularyId(), null,
			new ServiceContext());
	}

	@Test
	public void testUpdateCategoryExistsTranslationInDefaultSiteLocale()
		throws PortalException {

		AssetCategory category = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "Título",
			_assetVocabulary.getVocabularyId(), new ServiceContext());

		String expectedCategoryName = "Título";

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).put(
			LocaleUtil.SPAIN, expectedCategoryName
		).build();

		AssetCategory updatedCategory =
			_assetCategoryLocalService.updateCategory(
				TestPropsValues.getUserId(), category.getCategoryId(),
				category.getParentCategoryId(), titleMap,
				HashMapBuilder.put(
					LocaleUtil.FRANCE, "La description"
				).put(
					LocaleUtil.SPAIN, "Descripción"
				).build(),
				category.getVocabularyId(), null, new ServiceContext());

		Assert.assertEquals(
			"Expected name does not match", expectedCategoryName,
			updatedCategory.getName());

		Assert.assertEquals(
			"Expected title map does not match", titleMap,
			updatedCategory.getTitleMap());
	}

	@Test
	public void testUpdateCategoryMissingTranslationInDefaultSiteLocale()
		throws PortalException {

		AssetCategory category = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "Título",
			_assetVocabulary.getVocabularyId(), new ServiceContext());

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "La description"
		).build();

		String expectedExceptionMessage = StringBundler.concat(
			"Category name cannot be null for category ",
			category.getCategoryId(), " and vocabulary ",
			category.getVocabularyId());

		expectedException.expect(AssetCategoryNameException.class);
		expectedException.expectMessage(expectedExceptionMessage);

		_assetCategoryLocalService.updateCategory(
			TestPropsValues.getUserId(), category.getCategoryId(),
			category.getParentCategoryId(), titleMap, descriptionMap,
			category.getVocabularyId(), null, new ServiceContext());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private Group _group;

}