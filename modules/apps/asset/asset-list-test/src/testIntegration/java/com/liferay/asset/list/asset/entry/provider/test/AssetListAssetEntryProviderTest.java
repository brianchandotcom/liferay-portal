/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.asset.list.test.util.AssetListTestUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.pagination.InfoPage;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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
	public void testCombineSegmentsEntriesOfDynamicCollection()
		throws Exception {

		_setCombinedAssetForDynamicCollections(true);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, null,
				_serviceContext);

		User userTest = TestPropsValues.getUser();

		String userName = "RandomName";

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringPool.BLANK, userName + "@liferay.com", userName,
			LocaleUtil.getDefault(), userName, RandomTestUtil.randomString(),
			null, ServiceContextTestUtil.getServiceContext());

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), userTest.getFirstName());
		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());

		JournalArticle journalArticle = _addJournalArticle(
			new long[0], TestPropsValues.getUserId());

		_addJournalArticle(new long[0], TestPropsValues.getUserId());
		_addJournalArticle(new long[0], user.getUserId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry1.getSegmentsEntryId(),
			_getTypeSettings(userTest.getFirstName()));

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry2.getSegmentsEntryId(), _getTypeSettings(userName));

		long[] segmentsEntryIds = {
			segmentsEntry1.getSegmentsEntryId(),
			segmentsEntry2.getSegmentsEntryId()
		};

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, segmentsEntryIds, null, null, StringPool.BLANK,
				StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)infoPage.getPageItems();

		AssetEntry assetEntry = assetEntries.get(0);

		Assert.assertEquals(
			assetEntry.getTitle(LocaleUtil.US),
			journalArticle.getTitle(LocaleUtil.US));
	}

	@Test
	public void testCombineSegmentsEntriesOfDynamicCollectionWithCategoryFilter()
		throws Exception {

		_setCombinedAssetForDynamicCollections(true);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group globalGroup = company.getGroup();

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			globalGroup.getGroupId(),
			_portal.getClassNameId(JournalArticle.class), "BASIC-WEB-CONTENT");

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"anyAssetType",
					String.valueOf(_portal.getClassNameId(JournalArticle.class))
				).put(
					"anyClassTypeJournalArticleAssetRendererFactory",
					ddmStructure.getStructureId()
				).buildString(),
				_serviceContext);

		User user = TestPropsValues.getUser();

		AssetVocabulary globalAssetVocabulary = AssetTestUtil.addVocabulary(
			globalGroup.getGroupId());

		AssetCategory globalAssetCategory = AssetTestUtil.addCategory(
			globalGroup.getGroupId(), globalAssetVocabulary.getVocabularyId());

		long[] assetCategoryIds = {globalAssetCategory.getCategoryId()};

		_userLocalService.updateAsset(
			user.getUserId(), user, assetCategoryIds, null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());
		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByCategoryId(
			_group.getGroupId(), globalAssetCategory.getCategoryId());

		JournalArticle journalArticle = _addJournalArticle(
			assetCategoryIds, TestPropsValues.getUserId());

		_addJournalArticle(new long[0], TestPropsValues.getUserId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry1.getSegmentsEntryId(),
			_getTypeSettings(user.getFirstName()));

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry2.getSegmentsEntryId(),
			_getTypeSettings(user.getFirstName()));

		long[] segmentsEntryIds = {
			segmentsEntry1.getSegmentsEntryId(),
			segmentsEntry2.getSegmentsEntryId()
		};

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, segmentsEntryIds,
				new long[][] {{globalAssetCategory.getCategoryId()}}, null,
				StringPool.BLANK, StringPool.BLANK, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(1, infoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)infoPage.getPageItems();

		AssetEntry assetEntry = assetEntries.get(0);

		Assert.assertEquals(
			assetEntry.getTitle(LocaleUtil.US),
			journalArticle.getTitle(LocaleUtil.US));
	}

	@Test
	public void testCombineSegmentsEntriesOfDynamicCollectionWithoutDuplications()
		throws Exception {

		_setCombinedAssetForDynamicCollections(true);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, null,
				_serviceContext);

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());
		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());

		JournalArticle journalArticle = _addJournalArticle(
			new long[0], TestPropsValues.getUserId());

		_addJournalArticle(new long[0], TestPropsValues.getUserId());
		_addJournalArticle(new long[0], TestPropsValues.getUserId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry1.getSegmentsEntryId(),
			_getTypeSettings(user.getFirstName()));

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry2.getSegmentsEntryId(),
			_getTypeSettings(user.getFirstName()));

		long[] segmentsEntryIds = {
			segmentsEntry1.getSegmentsEntryId(),
			segmentsEntry2.getSegmentsEntryId()
		};

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, segmentsEntryIds, null, null, StringPool.BLANK,
				StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)infoPage.getPageItems();

		AssetEntry assetEntry = assetEntries.get(0);

		Assert.assertEquals(
			assetEntry.getTitle(LocaleUtil.US),
			journalArticle.getTitle(LocaleUtil.US));
	}

	@Test
	public void testCombineSegmentsEntriesOfManualCollection()
		throws Exception {

		_setCombinedAssetForManualCollections(true);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry1.getSegmentsEntryId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry1, assetListEntry,
			segmentsEntry1.getSegmentsEntryId(), 0);

		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByLastName(
			_group.getGroupId(), user.getLastName());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry2.getSegmentsEntryId());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry2, assetListEntry,
			segmentsEntry2.getSegmentsEntryId(), 0);

		JournalArticle journalArticle3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry3, assetListEntry,
			segmentsEntry2.getSegmentsEntryId(), 1);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry,
				new long[] {
					segmentsEntry1.getSegmentsEntryId(),
					segmentsEntry2.getSegmentsEntryId()
				},
				null, null, StringPool.BLANK, StringPool.BLANK, 0, 2);

		Assert.assertEquals(3, infoPage.getTotalCount());
		Assert.assertTrue(
			ListUtil.exists(
				infoPage.getPageItems(),
				assetEntry ->
					assetEntry.getEntryId() == assetEntry1.getEntryId()));
		Assert.assertTrue(
			ListUtil.exists(
				infoPage.getPageItems(),
				assetEntry ->
					assetEntry.getEntryId() == assetEntry2.getEntryId()));
		Assert.assertFalse(
			ListUtil.exists(
				infoPage.getPageItems(),
				assetEntry ->
					assetEntry.getEntryId() == assetEntry3.getEntryId()));
	}

	@Test
	public void testCombineSegmentsEntriesOfManualCollectionWithoutDuplications()
		throws Exception {

		_setCombinedAssetForManualCollections(true);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry1.getSegmentsEntryId());

		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByLastName(
			_group.getGroupId(), user.getLastName());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry2.getSegmentsEntryId());

		for (int i = 0; i < 4; i++) {
			JournalArticle article = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntry1.getSegmentsEntryId());
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntry2.getSegmentsEntryId());
		}

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry,
				new long[] {
					segmentsEntry1.getSegmentsEntryId(),
					segmentsEntry2.getSegmentsEntryId()
				},
				null, null, StringPool.BLANK, StringPool.BLANK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(4, infoPage.getTotalCount());
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
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null, null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());

		infoPage = _assetListAssetEntryProvider.getAssetEntriesInfoPage(
			assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
			new long[0][], null, "title1",
			String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(1, infoPage.getTotalCount());
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
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{assetCategory1.getCategoryId()},
					{assetCategory2.getCategoryId()}
				},
				null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(2, infoPage.getTotalCount());
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
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{
						assetCategory1.getCategoryId(),
						assetCategory2.getCategoryId()
					}
				},
				null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(2, infoPage.getTotalCount());
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
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory1.getCategoryId()}}, null,
				StringPool.BLANK, String.valueOf(TestPropsValues.getUserId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, infoPage.getTotalCount());
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
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, _serviceContext);

		AssetCategory assetCategory4 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory4.getCategoryId()}}, null,
				StringPool.BLANK, String.valueOf(TestPropsValues.getUserId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(0, infoPage.getTotalCount());
	}

	@Test
	public void testGetDynamicAssetEntriesWithAnyClassNameIds()
		throws Exception {

		_blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			StringPool.BLANK, RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"anyAssetType", true
				).put(
					"groupIds", String.valueOf(_group.getGroupId())
				).buildString(),
				_serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null, null, StringPool.BLANK, StringPool.BLANK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());
	}

	@Test
	public void testGetDynamicAssetEntriesWithMultipleClassNameIds()
		throws Exception {

		_blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			StringPool.BLANK, RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"anyAssetType", false
				).put(
					"classNameIds",
					StringUtil.merge(
						new long[] {
							_portal.getClassNameId(BlogsEntry.class.getName()),
							_portal.getClassNameId(DLFileEntry.class.getName()),
							_portal.getClassNameId(
								JournalArticle.class.getName())
						})
				).put(
					"classTypeIdsDLFileEntryAssetRendererFactory",
					String.valueOf(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"classTypeIdsJournalArticleAssetRendererFactory",
					String.valueOf(journalArticle.getDDMStructureId())
				).put(
					"groupIds", String.valueOf(_group.getGroupId())
				).buildString(),
				_serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null, null, StringPool.BLANK, StringPool.BLANK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());
	}

	@Test
	public void testGetManualAssetEntries() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		JournalArticle journalArticle3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _serviceContext);

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null, null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());

		for (AssetEntry assetEntry : infoPage.getPageItems()) {
			Assert.assertTrue(
				ArrayUtil.contains(assetEntryIds, assetEntry.getEntryId()));
		}
	}

	@Test
	public void testGetManualAssetEntriesByKeywords() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title1",
			RandomTestUtil.randomString());

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title2",
			RandomTestUtil.randomString());

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		JournalArticle journalArticle3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title3",
			RandomTestUtil.randomString());

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null, null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(3, infoPage.getTotalCount());

		infoPage = _assetListAssetEntryProvider.getAssetEntriesInfoPage(
			assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
			new long[0][], null, "title1",
			String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(1, infoPage.getTotalCount());
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

		JournalArticle journalArticle1 = _addJournalArticle(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		JournalArticle journalArticle2 = _addJournalArticle(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{assetCategory1.getCategoryId()},
					{assetCategory2.getCategoryId()}
				},
				null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(2, infoPage.getTotalCount());

		for (AssetEntry assetEntry : infoPage.getPageItems()) {
			Assert.assertTrue(
				ArrayUtil.contains(assetEntryIds, assetEntry.getEntryId()));
		}
	}

	@Test
	public void testGetManualAssetEntriesMatchingAnyAssetCategories()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle1 = _addJournalArticle(
			new long[] {assetCategory1.getCategoryId()});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle2 = _addJournalArticle(
			new long[] {assetCategory2.getCategoryId()});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {
					{
						assetCategory1.getCategoryId(),
						assetCategory2.getCategoryId()
					}
				},
				null, StringPool.BLANK,
				String.valueOf(TestPropsValues.getUserId()), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(2, infoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)infoPage.getPageItems();

		for (AssetEntry assetEntry : assetEntries) {
			Assert.assertTrue(
				ArrayUtil.contains(assetEntryIds, assetEntry.getEntryId()));
		}
	}

	@Test
	public void testGetManualAssetEntriesMatchingOneAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle1 = _addJournalArticle(
			new long[] {assetCategory1.getCategoryId()});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle2 = _addJournalArticle(
			new long[] {assetCategory2.getCategoryId()});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL, _serviceContext);

		long[] assetEntryIds = {
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			assetEntry3.getEntryId()
		};

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(), assetEntryIds,
			SegmentsEntryConstants.ID_DEFAULT, _serviceContext);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory1.getCategoryId()}}, null,
				StringPool.BLANK, String.valueOf(TestPropsValues.getUserId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, infoPage.getTotalCount());
	}

	@Test
	public void testGetManualAssetEntriesNonmatchingAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle1 = _addJournalArticle(
			new long[] {assetCategory1.getCategoryId()});

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle2 = _addJournalArticle(
			new long[] {assetCategory2.getCategoryId()});

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		AssetCategory assetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		JournalArticle journalArticle3 = _addJournalArticle(
			new long[] {assetCategory3.getCategoryId()});

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
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

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				new long[][] {{assetCategory4.getCategoryId()}}, null,
				StringPool.BLANK, String.valueOf(TestPropsValues.getUserId()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(0, infoPage.getTotalCount());
	}

	@Test
	public void testNotCombineSegmentsEntriesOfDynamicCollection()
		throws Exception {

		_setCombinedAssetForDynamicCollections(false);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, null,
				_serviceContext);

		User userTest = TestPropsValues.getUser();

		String userName = "RandomName";

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringPool.BLANK, userName + "@liferay.com", userName,
			LocaleUtil.getDefault(), userName, RandomTestUtil.randomString(),
			null, ServiceContextTestUtil.getServiceContext());

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), userTest.getFirstName());
		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());

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
			segmentsEntry2.getSegmentsEntryId(), _getTypeSettings(userName));

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry, segmentsEntryIds, null, null, StringPool.BLANK,
				StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(2, infoPage.getTotalCount());

		List<AssetEntry> assetEntries =
			(List<AssetEntry>)infoPage.getPageItems();

		AssetEntry assetEntry = assetEntries.get(0);

		Assert.assertEquals(
			assetEntry.getTitle(LocaleUtil.US),
			journalArticle.getTitle(LocaleUtil.US));
	}

	@Test
	public void testNotCombineSegmentsEntriesOfManualCollection()
		throws Exception {

		_setCombinedAssetForManualCollections(false);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_MANUAL,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		User user = TestPropsValues.getUser();

		SegmentsEntry segmentsEntry1 = _addSegmentsEntryByFirstName(
			_group.getGroupId(), user.getFirstName());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry1.getSegmentsEntryId());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle1.getResourcePrimKey());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry1, assetListEntry,
			segmentsEntry1.getSegmentsEntryId(), 0);

		SegmentsEntry segmentsEntry2 = _addSegmentsEntryByLastName(
			_group.getGroupId(), user.getLastName());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry,
			segmentsEntry2.getSegmentsEntryId());

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry2, assetListEntry,
			segmentsEntry2.getSegmentsEntryId(), 0);

		JournalArticle journalArticle3 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle3.getResourcePrimKey());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry3, assetListEntry,
			segmentsEntry2.getSegmentsEntryId(), 1);

		InfoPage<AssetEntry> infoPage =
			_assetListAssetEntryProvider.getAssetEntriesInfoPage(
				assetListEntry,
				new long[] {
					segmentsEntry1.getSegmentsEntryId(),
					segmentsEntry2.getSegmentsEntryId()
				},
				null, null, StringPool.BLANK, StringPool.BLANK, 0, 2);

		Assert.assertEquals(1, infoPage.getTotalCount());
		Assert.assertTrue(
			ListUtil.exists(
				infoPage.getPageItems(),
				assetEntry ->
					assetEntry.getEntryId() == assetEntry1.getEntryId()));
		Assert.assertFalse(
			ListUtil.exists(
				infoPage.getPageItems(),
				assetEntry ->
					assetEntry.getEntryId() == assetEntry2.getEntryId()));
		Assert.assertFalse(
			ListUtil.exists(
				infoPage.getPageItems(),
				assetEntry ->
					assetEntry.getEntryId() == assetEntry3.getEntryId()));
	}

	private JournalArticle _addJournalArticle(long[] assetCategoryIds)
		throws Exception {

		return _addJournalArticle(
			assetCategoryIds, TestPropsValues.getUserId());
	}

	private JournalArticle _addJournalArticle(
			long[] assetCategoryIds, long userId)
		throws Exception {

		return JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId, assetCategoryIds));
	}

	private SegmentsEntry _addSegmentsEntry(long groupId, String filterString)
		throws Exception {

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, filterString, Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			groupId, CriteriaSerializer.serialize(criteria));
	}

	private SegmentsEntry _addSegmentsEntryByCategoryId(
			long groupId, long categoryId)
		throws Exception {

		return _addSegmentsEntry(
			groupId, String.format("(assetCategoryIds eq '%s')", categoryId));
	}

	private SegmentsEntry _addSegmentsEntryByFirstName(
			long groupId, String firstName)
		throws Exception {

		return _addSegmentsEntry(
			groupId, String.format("(firstName eq '%s')", firstName));
	}

	private SegmentsEntry _addSegmentsEntryByLastName(
			long groupId, String lastName)
		throws Exception {

		return _addSegmentsEntry(
			groupId, String.format("(lastName eq '%s')", lastName));
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

	private void _setCombinedAssetForManualCollections(boolean active)
		throws Exception {

		_assetListAssetEntryConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.asset.list.internal.configuration." +
					"AssetListConfiguration",
				StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_assetListAssetEntryConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"combineAssetsFromAllSegmentsManual", active
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

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

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

	@Inject
	private UserLocalService _userLocalService;

}