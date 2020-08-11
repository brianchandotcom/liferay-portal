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

package com.liferay.asset.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetEntryInfoItemFieldSetProviderTest {

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
	public void testGetInfoFieldSetInternalEmptyVocabulary() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), titleMap, null, null,
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL, serviceContext);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		InfoFieldSet infoFieldSet =
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(assetEntry);

		Assert.assertNull(
			infoFieldSet.getInfoFieldSetEntry(vocabulary.getName()));
	}

	@Test
	public void testGetInfoFieldSetJournalArticlePublicEmptyVocabulary()
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.journal.model.JournalArticle");

		Group group = GroupLocalServiceUtil.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			group.getGroupId(), classNameId, "BASIC-WEB-CONTENT");

		long classTypeId = ddmStructure.getStructureId();

		AssetVocabulary vocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId(), classNameId, classTypeId, false);

		InfoFieldSet infoFieldSet =
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(), classTypeId,
				_group.getGroupId());

		InfoFieldSetEntry infoFieldSetEntry = infoFieldSet.getInfoFieldSetEntry(
			vocabulary.getName());

		Assert.assertEquals(vocabulary.getName(), infoFieldSetEntry.getName());
	}

	@Test
	public void testGetInfoFieldSetPublicEmptyVocabulary() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		InfoFieldSet infoFieldSet =
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(assetEntry);

		InfoFieldSetEntry infoFieldSetEntry = infoFieldSet.getInfoFieldSetEntry(
			vocabulary.getName());

		Assert.assertEquals(vocabulary.getName(), infoFieldSetEntry.getName());
	}

	@Test
	public void testGetInfoFieldSetPublicVocabularyWithCategory()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory category = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			RandomTestUtil.randomString(), vocabulary.getVocabularyId(),
			serviceContext);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		_assetEntryLocalService.addAssetCategoryAssetEntry(
			category.getCategoryId(), assetEntry);

		InfoFieldSet infoFieldSet =
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(assetEntry);

		InfoFieldSetEntry infoFieldSetEntry = infoFieldSet.getInfoFieldSetEntry(
			vocabulary.getName());

		Assert.assertEquals(vocabulary.getName(), infoFieldSetEntry.getName());
	}

	@Inject
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group;

}