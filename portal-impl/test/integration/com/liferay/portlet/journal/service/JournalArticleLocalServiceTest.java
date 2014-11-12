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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JournalArticleLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetByNoPermissions() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getResourcePermissions(
				journalArticle2.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(journalArticle2.getResourcePrimKey()));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourcePermissionLocalServiceUtil.deleteResourcePermission(
				resourcePermission.getResourcePermissionId());
		}

		List<JournalArticle> journalArticles =
			JournalArticleLocalServiceUtil.getByNoPermissions();

		Assert.assertEquals(1, journalArticles.size());

		Assert.assertEquals(
			journalArticle2.getArticleId(),
			journalArticles.get(0).getArticleId());
	}

	@Test
	public void testGetNoAssetArticles() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle2.getResourcePrimKey());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<JournalArticle> journalArticles =
			JournalArticleLocalServiceUtil.getNoAssetArticles();

		for (JournalArticle journalArticle : journalArticles) {
			assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey());

			Assert.assertNull(assetEntry);

			JournalArticleLocalServiceUtil.deleteJournalArticle(
				journalArticle.getPrimaryKey());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}