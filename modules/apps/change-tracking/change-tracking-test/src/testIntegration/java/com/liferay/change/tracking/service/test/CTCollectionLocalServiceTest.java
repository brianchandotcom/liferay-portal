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
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeClosable;
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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
		long ctCollectionId = _counterLocalService.increment(
			CTCollection.class.getName());

		_ctCollection = _ctCollectionLocalService.createCTCollection(
			ctCollectionId);

		_ctCollection.setUserId(TestPropsValues.getUserId());
		_ctCollection.setName(String.valueOf(ctCollectionId));
		_ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		_ctCollection = _ctCollectionLocalService.updateCTCollection(
			_ctCollection);

		_journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class);
		_journalFolderClassNameId = _classNameLocalService.getClassNameId(
			JournalFolder.class);
		_layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCheckConflictsWithJournalArticles() throws Exception {
		Map<Long, List<ConflictInfo>> conflictInfoMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection);

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalArticle ctJournalArticle1 = null;
		JournalArticle ctJournalArticle2 = null;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(_group.getGroupId());

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

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

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

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
			_ctCollection);

		List<ConflictInfo> conflictInfos = conflictInfoMap.remove(
			_journalArticleClassNameId);

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

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
	}

	@Test
	public void testCheckConflictsWithJournalFolders() throws Exception {
		Map<Long, List<ConflictInfo>> conflictInfoMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection);

		Assert.assertTrue(
			conflictInfoMap.toString(), conflictInfoMap.isEmpty());

		String conflictingFolderName = "conflictingFolderName";

		JournalFolder ctJournalFolder = null;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			ctJournalFolder = JournalTestUtil.addFolder(
				_group.getGroupId(), conflictingFolderName);
		}

		JournalFolder productionJournalFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), conflictingFolderName);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

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
			_ctCollection);

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

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addedLayout = LayoutTestUtil.addLayout(_group);

			_layoutLocalService.deleteLayout(deletedLayout);

			modifiedLayout.setFriendlyURL(newFriendlyURL);

			modifiedLayout = _layoutLocalService.updateLayout(modifiedLayout);

			_layoutLocalService.updateAsset(
				modifiedLayout.getUserId(), modifiedLayout, null,
				new String[] {tagName2});
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			Layout.class.getName(), modifiedLayout.getPlid());

		List<AssetTag> assetTags = _assetTagLocalService.getEntryTags(
			assetEntry.getEntryId());

		Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

		AssetTag assetTag = assetTags.get(0);

		Assert.assertEquals(tagName2, assetTag.getName());

		Assert.assertEquals(
			addedLayout,
			_layoutLocalService.fetchLayout(addedLayout.getPlid()));

		Assert.assertNull(
			_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

		modifiedLayout = _layoutLocalService.fetchLayout(
			modifiedLayout.getPlid());

		Assert.assertEquals(newFriendlyURL, modifiedLayout.getFriendlyURL());

		_ctCollection2 = _ctCollectionLocalService.undoCTCollection(
			_ctCollection.getCtCollectionId(), _ctCollection.getUserId(),
			_ctCollection.getName() + " (undo)", StringPool.BLANK);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
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
	private static CounterLocalService _counterLocalService;

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
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private CTCollection _ctCollection2;

	@DeleteAfterTestRun
	private Group _group;

}