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

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTCollectionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection1 = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			CTCollectionLocalServiceTest.class.getSimpleName(), null);
		_group = GroupTestUtil.addGroup();
		_journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class);
		_journalFolderClassNameId = _classNameLocalService.getClassNameId(
			JournalFolder.class);
		_layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);
	}

	@Test
	public void testCheckConflictsWithJournalArticles() throws Exception {
		Map<Long, List<ConflictInfo>> conflictInfoMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection1);

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, StringPool.BLANK,
			true,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			null, LocaleUtil.getSiteDefault(), null, false, false,
			serviceContext);

		JournalArticle ctJournalArticle1 = null;
		JournalArticle ctJournalArticle2 = null;

		serviceContext.setScopeGroupId(_group.getGroupId());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			ctJournalArticle1 = _journalArticleLocalService.updateArticle(
				journalArticle.getUserId(), journalArticle.getGroupId(),
				journalArticle.getFolderId(), journalArticle.getArticleId(),
				journalArticle.getVersion(), journalArticle.getContent(),
				serviceContext);

			ctJournalArticle2 = _journalArticleLocalService.updateArticle(
				ctJournalArticle1.getUserId(), ctJournalArticle1.getGroupId(),
				ctJournalArticle1.getFolderId(),
				ctJournalArticle1.getArticleId(),
				ctJournalArticle1.getVersion(), ctJournalArticle1.getContent(),
				serviceContext);
		}

		JournalArticle productionJournalArticle1 =
			_journalArticleLocalService.updateArticle(
				journalArticle.getUserId(), journalArticle.getGroupId(),
				journalArticle.getFolderId(), journalArticle.getArticleId(),
				journalArticle.getVersion(), journalArticle.getContent(),
				serviceContext);

		JournalArticle productionJournalArticle2 =
			_journalArticleLocalService.updateArticle(
				productionJournalArticle1.getUserId(),
				productionJournalArticle1.getGroupId(),
				productionJournalArticle1.getFolderId(),
				productionJournalArticle1.getArticleId(),
				productionJournalArticle1.getVersion(),
				productionJournalArticle1.getContent(), serviceContext);

		Assert.assertNotEquals(productionJournalArticle1, ctJournalArticle1);

		Assert.assertNotEquals(productionJournalArticle2, ctJournalArticle2);

		Assert.assertEquals(1.0, journalArticle.getVersion(), 0.01);

		Assert.assertEquals(1.1, productionJournalArticle1.getVersion(), 0.01);
		Assert.assertEquals(1.2, productionJournalArticle2.getVersion(), 0.01);

		Assert.assertEquals(1.1, ctJournalArticle1.getVersion(), 0.01);
		Assert.assertEquals(1.2, ctJournalArticle2.getVersion(), 0.01);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			List<JournalArticle> journalArticles =
				_journalArticleLocalService.getArticlesByResourcePrimKey(
					journalArticle.getResourcePrimKey());

			Assert.assertEquals(
				journalArticles.toString(), 3, journalArticles.size());

			Assert.assertEquals(ctJournalArticle2, journalArticles.get(0));
			Assert.assertEquals(ctJournalArticle1, journalArticles.get(1));
			Assert.assertEquals(journalArticle, journalArticles.get(2));
		}

		List<JournalArticle> journalArticles =
			_journalArticleLocalService.getArticlesByResourcePrimKey(
				journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			journalArticles.toString(), 3, journalArticles.size());

		Assert.assertEquals(productionJournalArticle2, journalArticles.get(0));
		Assert.assertEquals(productionJournalArticle1, journalArticles.get(1));
		Assert.assertEquals(journalArticle, journalArticles.get(2));

		conflictInfoMap = _ctCollectionLocalService.checkConflicts(
			_ctCollection1);

		List<ConflictInfo> conflictInfos = conflictInfoMap.remove(
			_journalArticleClassNameId);

		Assert.assertEquals(conflictInfos.toString(), 2, conflictInfos.size());

		conflictInfos.sort(
			Comparator.comparing(ConflictInfo::getTargetPrimaryKey));

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertTrue(conflictInfo.isResolved());
		Assert.assertEquals(
			productionJournalArticle1.getPrimaryKey(),
			conflictInfo.getTargetPrimaryKey());
		Assert.assertEquals(
			ctJournalArticle1.getPrimaryKey(),
			conflictInfo.getSourcePrimaryKey());

		conflictInfo = conflictInfos.get(1);

		Assert.assertTrue(conflictInfo.isResolved());
		Assert.assertEquals(
			productionJournalArticle2.getPrimaryKey(),
			conflictInfo.getTargetPrimaryKey());
		Assert.assertEquals(
			ctJournalArticle2.getPrimaryKey(),
			conflictInfo.getSourcePrimaryKey());

		conflictInfos = conflictInfoMap.remove(
			_classNameLocalService.getClassNameId(AssetEntry.class));

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

		conflictInfo = conflictInfos.get(0);

		Assert.assertTrue(conflictInfo.isResolved());
	}

	@Test
	public void testCheckConflictsWithJournalFolders() throws Exception {
		Map<Long, List<ConflictInfo>> conflictInfoMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection1);

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

		String conflictingFolderName = "conflictingFolderName";

		JournalFolder ctJournalFolder = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			ctJournalFolder = JournalTestUtil.addFolder(
				_group.getGroupId(), conflictingFolderName);
		}

		JournalFolder productionJournalFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), conflictingFolderName);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			List<JournalFolder> journalFolders =
				_journalFolderLocalService.getFolders(
					_group.getGroupId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			Assert.assertEquals(
				journalFolders.toString(), 1, journalFolders.size());

			Assert.assertEquals(ctJournalFolder, journalFolders.get(0));
		}

		List<JournalFolder> journalFolders =
			_journalFolderLocalService.getFolders(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(
			journalFolders.toString(), 1, journalFolders.size());

		Assert.assertEquals(productionJournalFolder, journalFolders.get(0));

		conflictInfoMap = _ctCollectionLocalService.checkConflicts(
			_ctCollection1);

		Assert.assertEquals(
			conflictInfoMap.toString(), 1, conflictInfoMap.size());

		List<ConflictInfo> conflictInfos = conflictInfoMap.remove(
			_journalFolderClassNameId);

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

		Assert.assertEquals(conflictInfos.toString(), 1, conflictInfos.size());

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertFalse(conflictInfo.isResolved());
		Assert.assertEquals(
			productionJournalFolder.getPrimaryKey(),
			conflictInfo.getTargetPrimaryKey());
		Assert.assertEquals(
			ctJournalFolder.getPrimaryKey(),
			conflictInfo.getSourcePrimaryKey());
	}

	@Test
	public void testDeletePreDeletedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout);

			Assert.assertNull(
				_layoutLocalService.fetchLayout(layout.getPlid()));
		}

		_layoutLocalService.deleteLayout(layout.getPlid());

		Assert.assertNull(_layoutLocalService.fetchLayout(layout.getPlid()));

		_ctProcessLocalService.addCTProcess(
			_ctCollection1.getUserId(), _ctCollection1.getCtCollectionId());

		_ctCollection2 = _ctCollectionLocalService.undoCTCollection(
			_ctCollection1.getCtCollectionId(), _ctCollection1.getUserId(),
			_ctCollection1.getName() + " (undo)", StringPool.BLANK);

		_ctProcessLocalService.addCTProcess(
			_ctCollection2.getUserId(), _ctCollection2.getCtCollectionId());

		Assert.assertNull(_layoutLocalService.fetchLayout(layout.getPlid()));
	}

	@Test
	public void testDeletePreDeletedLayoutWithTwoCollections()
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout);

			Assert.assertNull(
				_layoutLocalService.fetchLayout(layout.getPlid()));
		}

		Assert.assertEquals(
			layout, _layoutLocalService.getLayout(layout.getPlid()));

		_ctCollection2 = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomString(), StringUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout);

			Assert.assertNull(
				_layoutLocalService.fetchLayout(layout.getPlid()));
		}

		Assert.assertEquals(
			layout, _layoutLocalService.getLayout(layout.getPlid()));

		_ctProcessLocalService.addCTProcess(
			_ctCollection1.getUserId(), _ctCollection1.getCtCollectionId());

		Assert.assertNull(_layoutLocalService.fetchLayout(layout.getPlid()));

		_ctProcessLocalService.addCTProcess(
			_ctCollection2.getUserId(), _ctCollection2.getCtCollectionId());

		_ctCollection3 = _ctCollectionLocalService.undoCTCollection(
			_ctCollection1.getCtCollectionId(), _ctCollection1.getUserId(),
			_ctCollection1.getName() + " (undo)", StringPool.BLANK);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection3.getCtCollectionId())) {

			Assert.assertEquals(
				layout, _layoutLocalService.getLayout(layout.getPlid()));
		}

		_ctCollection4 = _ctCollectionLocalService.undoCTCollection(
			_ctCollection2.getCtCollectionId(), _ctCollection2.getUserId(),
			_ctCollection2.getName() + " (undo)", StringPool.BLANK);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection4.getCtCollectionId())) {

			Assert.assertNull(
				_layoutLocalService.fetchLayout(layout.getPlid()));
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection3.getUserId(), _ctCollection3.getCtCollectionId());

		Assert.assertEquals(
			layout, _layoutLocalService.getLayout(layout.getPlid()));

		Map<Long, List<ConflictInfo>> conflictInfosMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection4);

		Assert.assertFalse(conflictInfosMap.isEmpty());
	}

	@Test
	public void testUndoCTCollection() throws Exception {
		Layout addedLayout = null;

		Layout deletedLayout = LayoutTestUtil.addLayout(_group);

		Layout modifiedLayout = LayoutTestUtil.addLayout(_group);

		String tagName1 = "layoutcttesttag1";
		String tagName2 = "layoutcttesttag2";

		_layoutLocalService.updateAsset(
			modifiedLayout.getUserId(), modifiedLayout, null,
			new String[] {tagName1});

		String originalFriendlyURL = modifiedLayout.getFriendlyURL();

		String newFriendlyURL = "/testModifyLayout";

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			addedLayout = LayoutTestUtil.addLayout(_group);

			_layoutLocalService.deleteLayout(deletedLayout);

			modifiedLayout.setFriendlyURL(newFriendlyURL);

			modifiedLayout = _layoutLocalService.updateLayout(modifiedLayout);

			_layoutLocalService.updateAsset(
				modifiedLayout.getUserId(), modifiedLayout, null,
				new String[] {tagName2});
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection1.getUserId(), _ctCollection1.getCtCollectionId());

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			Layout.class.getName(), modifiedLayout.getPlid());

		List<AssetTag> assetTags = _assetTagLocalService.getEntryTags(
			assetEntry.getEntryId());

		Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

		AssetTag assetTag = assetTags.get(0);

		Assert.assertEquals(tagName2, assetTag.getName());
		Assert.assertEquals(1, assetTag.getAssetCount());

		Assert.assertEquals(
			addedLayout,
			_layoutLocalService.fetchLayout(addedLayout.getPlid()));

		Assert.assertNull(
			_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

		modifiedLayout = _layoutLocalService.fetchLayout(
			modifiedLayout.getPlid());

		Assert.assertEquals(newFriendlyURL, modifiedLayout.getFriendlyURL());

		_ctCollection2 = _ctCollectionLocalService.undoCTCollection(
			_ctCollection1.getCtCollectionId(), _ctCollection1.getUserId(),
			_ctCollection1.getName() + " (undo)", StringPool.BLANK);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			Assert.assertNull(
				_layoutLocalService.fetchLayout(addedLayout.getPlid()));

			Assert.assertEquals(
				deletedLayout,
				_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

			modifiedLayout = _layoutLocalService.fetchLayout(
				modifiedLayout.getPlid());

			Assert.assertEquals(
				originalFriendlyURL, modifiedLayout.getFriendlyURL());

			assetTags = _assetTagLocalService.getEntryTags(
				assetEntry.getEntryId());

			Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

			assetTag = assetTags.get(0);

			Assert.assertEquals(tagName1, assetTag.getName());
			Assert.assertEquals(1, assetTag.getAssetCount());
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection2.getUserId(), _ctCollection2.getCtCollectionId());

		Assert.assertNull(
			_layoutLocalService.fetchLayout(addedLayout.getPlid()));

		Assert.assertEquals(
			deletedLayout,
			_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

		modifiedLayout = _layoutLocalService.fetchLayout(
			modifiedLayout.getPlid());

		Assert.assertEquals(
			originalFriendlyURL, modifiedLayout.getFriendlyURL());
	}

	@Inject
	private static AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private static AssetTagLocalService _assetTagLocalService;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	private static long _journalArticleClassNameId;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	private static long _journalFolderClassNameId;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	private static long _layoutClassNameId;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection1;

	@DeleteAfterTestRun
	private CTCollection _ctCollection2;

	@DeleteAfterTestRun
	private CTCollection _ctCollection3;

	@DeleteAfterTestRun
	private CTCollection _ctCollection4;

	@DeleteAfterTestRun
	private Group _group;

}