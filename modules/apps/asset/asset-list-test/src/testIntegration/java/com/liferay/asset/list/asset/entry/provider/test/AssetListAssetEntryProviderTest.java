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

package com.liferay.asset.list.asset.entry.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class AssetListAssetEntryProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	@Test
	public void testCombineSegmentsOfDynamicCollection() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.asset.list.web.internal.configuration." +
						"FFCollectionsVariationsPrioritizationConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			_setCombinedAssetForDynamicCollections(true);

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.addAssetListEntry(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Dynamic title", AssetListEntryTypeConstants.TYPE_DYNAMIC,
					null, _serviceContext);

			User userTest = TestPropsValues.getUser();

			String userName = "RandomName";

			User user = UserTestUtil.addUser(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, userName + "@liferay.com", userName,
				LocaleUtil.getDefault(), userName,
				RandomTestUtil.randomString(), null,
				ServiceContextTestUtil.getServiceContext());

			SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
				_group.getGroupId(), userTest);
			SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName(
				_group.getGroupId(), user);

			JournalArticle journalArticle = _addJournalArticle(
				new long[0], TestPropsValues.getUserId());

			_addJournalArticle(new long[0], TestPropsValues.getUserId());
			_addJournalArticle(new long[0], user.getUserId());

			long[] segmentsEntryIds = {
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()
			};

			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry,
				segmentsEntry1.getSegmentsEntryId(),
				_getTypeSettings(userTest.getFirstName()));

			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry,
				segmentsEntry2.getSegmentsEntryId(),
				_getTypeSettings(userName));

			List<AssetEntry> assetEntries =
				_assetListAssetEntryProvider.getAssetEntries(
					assetListEntry, segmentsEntryIds);

			Assert.assertEquals(
				assetEntries.toString(), 3, assetEntries.size());

			AssetEntry firstAssetEntry = assetEntries.get(0);

			Assert.assertEquals(
				firstAssetEntry.getTitle(LocaleUtil.US),
				journalArticle.getTitle(LocaleUtil.US));
		}
	}

	@Test
	public void testCombineSegmentsOfDynamicCollectionWithoutDuplications()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.asset.list.web.internal.configuration." +
						"FFCollectionsVariationsPrioritizationConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			_setCombinedAssetForDynamicCollections(true);

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.addAssetListEntry(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Dynamic title", AssetListEntryTypeConstants.TYPE_DYNAMIC,
					null, _serviceContext);

			User userTest = TestPropsValues.getUser();

			SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
				_group.getGroupId(), userTest);
			SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName(
				_group.getGroupId(), userTest);

			JournalArticle journalArticle = _addJournalArticle(
				new long[0], TestPropsValues.getUserId());

			_addJournalArticle(new long[0], TestPropsValues.getUserId());
			_addJournalArticle(new long[0], TestPropsValues.getUserId());

			long[] segmentsEntryIds = {
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()
			};

			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry,
				segmentsEntry1.getSegmentsEntryId(),
				_getTypeSettings(userTest.getFirstName()));

			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry,
				segmentsEntry2.getSegmentsEntryId(),
				_getTypeSettings(userTest.getFirstName()));

			List<AssetEntry> assetEntries =
				_assetListAssetEntryProvider.getAssetEntries(
					assetListEntry, segmentsEntryIds);

			Assert.assertEquals(
				assetEntries.toString(), 3, assetEntries.size());

			AssetEntry firstAssetEntry = assetEntries.get(0);

			Assert.assertEquals(
				firstAssetEntry.getTitle(LocaleUtil.US),
				journalArticle.getTitle(LocaleUtil.US));
		}
	}

	@Test
	public void testGetDynamicAssetEntriesByKeywords() throws Exception {
		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title1",
			RandomTestUtil.randomString());
		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title2",
			RandomTestUtil.randomString());
		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title3",
			RandomTestUtil.randomString());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 3, assetEntries.size());

		assetEntries = _assetListAssetEntryProvider.getAssetEntries(
			assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
			new long[0][], "title1",
			String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 1, assetEntries.size());
	}

	@Test
	public void testGetDynamicAssetEntriesMatchingAllAssetCategories()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());
		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});
		_addJournalArticle(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory3.getCategoryId()});

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{assetCategory1.getCategoryId()},
					{assetCategory2.getCategoryId()}
				},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 2, assetEntries.size());
	}

	@Test
	public void testGetDynamicAssetEntriesMatchingAnyAssetCategories()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory1.getCategoryId()});

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory2.getCategoryId()});

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory3.getCategoryId()});

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{
						assetCategory1.getCategoryId(),
						assetCategory2.getCategoryId()
					}
				},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 2, assetEntries.size());
	}

	@Test
	public void testGetDynamicAssetEntriesMatchingOneAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory1.getCategoryId()});

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory2.getCategoryId()});

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory3.getCategoryId()});

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory1.getCategoryId()}},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 1, assetEntries.size());
	}

	@Test
	public void testGetDynamicAssetEntriesNonmatchingAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory1.getCategoryId()});

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory2.getCategoryId()});

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_addJournalArticle(new long[] {assetCategory3.getCategoryId()});

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		AssetCategory assetCategory4 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory4.getCategoryId()}},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 0, assetEntries.size());
	}

	@Test
	public void testGetManualAssetEntries() throws Exception {
		JournalArticle article1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		JournalArticle article2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		JournalArticle article3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 3, assetEntries.size());

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			Assert.assertEquals(assetEntry.getEntryId(), assetEntryIds[i]);
		}
	}

	@Test
	public void testGetManualAssetEntriesByKeywords() throws Exception {
		JournalArticle article1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title1",
			RandomTestUtil.randomString());

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		JournalArticle article2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title2",
			RandomTestUtil.randomString());

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		JournalArticle article3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title3",
			RandomTestUtil.randomString());

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 3, assetEntries.size());

		assetEntries = _assetListAssetEntryProvider.getAssetEntries(
			assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
			new long[0][], "title1",
			String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 1, assetEntries.size());
	}

	@Test
	public void testGetManualAssetEntriesMatchingAllAssetCategories()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());
		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article1 = _addJournalArticle(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		JournalArticle article2 = _addJournalArticle(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{assetCategory1.getCategoryId()},
					{assetCategory2.getCategoryId()}
				},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 2, assetEntries.size());

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			Assert.assertEquals(assetEntry.getEntryId(), assetEntryIds[i]);
		}
	}

	@Test
	public void testGetManualAssetEntriesMatchingAnyAssetCategories()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article1 = _addJournalArticle(
			new long[] {assetCategory1.getCategoryId()});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article2 = _addJournalArticle(
			new long[] {assetCategory2.getCategoryId()});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{
						assetCategory1.getCategoryId(),
						assetCategory2.getCategoryId()
					}
				},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 2, assetEntries.size());

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			Assert.assertEquals(assetEntry.getEntryId(), assetEntryIds[i]);
		}
	}

	@Test
	public void testGetManualAssetEntriesMatchingOneAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article1 = _addJournalArticle(
			new long[] {assetCategory1.getCategoryId()});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article2 = _addJournalArticle(
			new long[] {assetCategory2.getCategoryId()});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory1.getCategoryId()}},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 1, assetEntries.size());
	}

	@Test
	public void testGetManualAssetEntriesNonmatchingAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article1 = _addJournalArticle(
			new long[] {assetCategory1.getCategoryId()});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article1.getResourcePrimKey());

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article2 = _addJournalArticle(
			new long[] {assetCategory2.getCategoryId()});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle article3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		AssetCategory assetCategory4 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory4.getCategoryId()}},
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(assetEntries.toString(), 0, assetEntries.size());
	}

	@Test
	public void testNotCombineSegmentsOfDynamicCollection() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.asset.list.web.internal.configuration." +
						"FFCollectionsVariationsPrioritizationConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			_setCombinedAssetForDynamicCollections(false);

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.addAssetListEntry(
					TestPropsValues.getUserId(), _group.getGroupId(),
					"Dynamic title", AssetListEntryTypeConstants.TYPE_DYNAMIC,
					null, _serviceContext);

			User userTest = TestPropsValues.getUser();

			String userName = "RandomName";

			User user = UserTestUtil.addUser(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, userName + "@liferay.com", userName,
				LocaleUtil.getDefault(), userName,
				RandomTestUtil.randomString(), null,
				ServiceContextTestUtil.getServiceContext());

			SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
				_group.getGroupId(), userTest);
			SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName(
				_group.getGroupId(), user);

			JournalArticle journalArticle = _addJournalArticle(
				new long[0], TestPropsValues.getUserId());

			_addJournalArticle(new long[0], TestPropsValues.getUserId());
			_addJournalArticle(new long[0], user.getUserId());

			long[] segmentsEntryIds = {
				segmentsEntry1.getSegmentsEntryId(),
				segmentsEntry2.getSegmentsEntryId()
			};

			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry,
				segmentsEntry1.getSegmentsEntryId(),
				_getTypeSettings(userTest.getFirstName()));

			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry,
				segmentsEntry2.getSegmentsEntryId(),
				_getTypeSettings(userName));

			List<AssetEntry> assetEntries =
				_assetListAssetEntryProvider.getAssetEntries(
					assetListEntry, segmentsEntryIds);

			Assert.assertEquals(
				assetEntries.toString(), 2, assetEntries.size());

			AssetEntry firstAssetEntry = assetEntries.get(0);

			Assert.assertEquals(
				firstAssetEntry.getTitle(LocaleUtil.US),
				journalArticle.getTitle(LocaleUtil.US));
		}
	}

	private JournalArticle _addJournalArticle(long[] assetCategories)
		throws Exception {

		return _addJournalArticle(assetCategories, TestPropsValues.getUserId());
	}

	private JournalArticle _addJournalArticle(
			long[] assetCategories, long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId, assetCategories);

		return JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	private SegmentsEntry _addSegmentsEntryByFirstName(long groupId, User user)
		throws Exception {

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			groupId, CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private String _getTypeSettings(String queryValue) {
		return UnicodePropertiesBuilder.create(
			true
		).put(
			"anyAssetType",
			String.valueOf(_portal.getClassNameId(JournalArticle.class))
		).put(
			"classNameIds", JournalArticle.class.getName()
		).put(
			"groupIds", String.valueOf(_group.getGroupId())
		).put(
			"orderByColumn1", "modifiedDate"
		).put(
			"orderByColumn2", "title"
		).put(
			"orderByType1", "ASC"
		).put(
			"orderByType2", "ASC"
		).put(
			"queryContains0", "true"
		).put(
			"queryName0", "keywords"
		).put(
			"queryValues0", queryValue
		).buildString();
	}

	private void _setCombinedAssetForDynamicCollections(boolean active)
		throws Exception {

		_assetListAssetEntryConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.asset.list.internal.configuration." +
					"AssetListConfiguration",
				StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_assetListAssetEntryConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"combineAssetsFromAllSegmentsDynamic", active
			).build());
	}

	private static Configuration _assetListAssetEntryConfiguration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	private ServiceContext _serviceContext;

}