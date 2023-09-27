/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class JournalFolderLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetFoldersAndArticlesCount() throws Exception {
		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			_journalFolderLocalService);

		JournalFolder parentJournalFolder = journalFolderFixture.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		_addApprovedJournalArticle(parentJournalFolder);

		JournalFolder journalFolder = journalFolderFixture.addFolder(
			_group.getGroupId(), parentJournalFolder.getFolderId(),
			RandomTestUtil.randomString());

		_addExpiredJournalArticle(
			journalFolder,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		_addDeletedJournalArticle(journalFolder);

		List<Long> folderIds = Arrays.asList(
			parentJournalFolder.getFolderId(), journalFolder.getFolderId());

		_assertFoldersAndArticlesCount(
			1, folderIds, _group.getGroupId(),
			WorkflowConstants.STATUS_APPROVED);
		_assertFoldersAndArticlesCount(
			1, folderIds, _group.getGroupId(),
			WorkflowConstants.STATUS_EXPIRED);
		_assertFoldersAndArticlesCount(
			1, folderIds, _group.getGroupId(),
			WorkflowConstants.STATUS_IN_TRASH);
	}

	@Test
	public void testGetNoAssetFolders() throws Exception {
		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			_journalFolderLocalService);

		journalFolderFixture.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder folder = journalFolderFixture.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalFolder.class.getName(), folder.getFolderId());

		Assert.assertNotNull(assetEntry);

		_assetEntryLocalService.deleteAssetEntry(assetEntry);

		List<JournalFolder> folders =
			_journalFolderLocalService.getNoAssetFolders();

		Assert.assertEquals(folders.toString(), 1, folders.size());
		Assert.assertEquals(folders.toString(), folder, folders.get(0));
	}

	private JournalArticle _addApprovedJournalArticle(
			JournalFolder journalFolder)
		throws Exception {

		return JournalTestUtil.addArticle(
			_group.getGroupId(), journalFolder.getFolderId(),
			Collections.emptyMap());
	}

	private JournalArticle _addDeletedJournalArticle(
			JournalFolder journalFolder)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), journalFolder.getFolderId(),
			Collections.emptyMap());

		return _journalArticleLocalService.moveArticleToTrash(
			TestPropsValues.getUserId(), journalArticle);
	}

	private JournalArticle _addExpiredJournalArticle(
			JournalFolder journalFolder, ServiceContext serviceContext)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), journalFolder.getFolderId(),
			Collections.emptyMap());

		return _journalArticleLocalService.expireArticle(
			TestPropsValues.getUserId(), _group.getGroupId(),
			journalArticle.getArticleId(), journalArticle.getVersion(), null,
			serviceContext);
	}

	private void _assertFoldersAndArticlesCount(
		long expectedCount, List<Long> folderIds, long groupId, int status) {

		Assert.assertEquals(
			expectedCount,
			_journalFolderLocalService.getFoldersAndArticlesCount(
				groupId, folderIds, status));
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

}