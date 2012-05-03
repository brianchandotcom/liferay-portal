/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BlogsEntryTrashHandlerTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testTrashAndDelete() {
		testTrash(false, true);
	}

	@Test
	@Transactional
	public void testTrashAndRestoreApproved() {
		testTrash(true, false);
	}

	@Test
	@Transactional
	public void testTrashAndRestoreDraft() {
		testTrash(false, false);
	}

	protected BlogsEntry addBlogsEntry(Group group, boolean approved)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		String title = "Title";
		String description = "Description";
		String content = "Content";
		int displayDateMonth = 1;
		int displayDateDay = 1;
		int displayDateYear = 2012;
		int displayDateHour = 12;
		int displayDateMinute = 0;
		boolean allowPingbacks = true;
		boolean allowTrackbacks = true;
		String[] trackbacks = new String[0];
		boolean smallImage = false;
		String smallImageURL = StringPool.BLANK;
		String smallImageFileName = StringPool.BLANK;
		InputStream smallImageInputStream = null;

		BlogsEntry blogsEntry = BlogsEntryServiceUtil.addEntry(
			title, description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);

		if (approved) {
			BlogsEntryLocalServiceUtil.updateStatus(
				getUserId(), blogsEntry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}

		return blogsEntry;
	}

	protected AssetEntry fetchAssetEntry(long blogsEntryId) throws Exception {
		return AssetEntryLocalServiceUtil.fetchEntry(
			BlogsEntry.class.getName(), blogsEntryId);
	}

	protected int getBlogsEntriesNotInTrashCount(long groupId)
		throws Exception {

		return BlogsEntryServiceUtil.getGroupEntriesCount(
			groupId, WorkflowConstants.STATUS_ANY);
	}

	protected int getTrashEntriesCount(long groupId) throws Exception {
		Object[] result = TrashEntryServiceUtil.getEntries(groupId);

		return (Integer)result[1];
	}

	protected long getUserId() throws PrincipalException {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	protected boolean isAssetEntryVisible(long blogsEntryId) throws Exception {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			BlogsEntry.class.getName(), blogsEntryId);

		return assetEntry.isVisible();
	}

	protected int searchBlogsEntriesCount(long groupId) throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected void testTrash(boolean approved, boolean delete) {
		try {
			Group group = ServiceTestUtil.addGroup(
				"BlogsEntryTrashHandlerTest#testGroup");

			int initialBlogsEntriesCount = getBlogsEntriesNotInTrashCount(
				group.getGroupId());
			int initialTrashEntriesCount = getTrashEntriesCount(
				group.getGroupId());
			int initialBlogsEntriesSearchCount = searchBlogsEntriesCount(
				group.getGroupId());

			BlogsEntry blogsEntry = addBlogsEntry(group, approved);

			int oldStatus = blogsEntry.getStatus();

			Assert.assertEquals(
				initialBlogsEntriesCount + 1,
				getBlogsEntriesNotInTrashCount(group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesCount,
				getTrashEntriesCount(group.getGroupId()));

			if (approved) {
				Assert.assertTrue(isAssetEntryVisible(blogsEntry.getEntryId()));
				Assert.assertEquals(
					initialBlogsEntriesSearchCount + 1,
					searchBlogsEntriesCount(group.getGroupId()));
			}
			else {
				Assert.assertFalse(
					isAssetEntryVisible(blogsEntry.getEntryId()));
				Assert.assertEquals(
					initialBlogsEntriesSearchCount,
					searchBlogsEntriesCount(group.getGroupId()));
			}

			BlogsEntryServiceUtil.moveEntryToTrash(blogsEntry.getEntryId());

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

			Assert.assertEquals(
				blogsEntry.getEntryId(), trashEntry.getClassPK());
			Assert.assertEquals(
				WorkflowConstants.STATUS_IN_TRASH, blogsEntry.getStatus());
			Assert.assertEquals(
				initialBlogsEntriesCount,
				getBlogsEntriesNotInTrashCount(group.getGroupId()));
			Assert.assertEquals(
				initialTrashEntriesCount + 1,
				getTrashEntriesCount(group.getGroupId()));
			Assert.assertFalse(isAssetEntryVisible(blogsEntry.getEntryId()));
			Assert.assertEquals(
				initialBlogsEntriesSearchCount,
				searchBlogsEntriesCount(group.getGroupId()));

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					blogsEntry.getModelClassName());

			if (delete) {
				trashHandler.deleteTrashEntry(blogsEntry.getEntryId());

				Assert.assertEquals(
					initialBlogsEntriesCount,
					getBlogsEntriesNotInTrashCount(group.getGroupId()));
				Assert.assertNull(fetchAssetEntry(blogsEntry.getEntryId()));
				Assert.assertEquals(
					initialBlogsEntriesSearchCount,
					searchBlogsEntriesCount(group.getGroupId()));
			}
			else {
				trashHandler.restoreTrashEntry(blogsEntry.getEntryId());

				blogsEntry = BlogsEntryServiceUtil.getEntry(
					blogsEntry.getEntryId());

				Assert.assertEquals(oldStatus, blogsEntry.getStatus());
				Assert.assertEquals(
					initialBlogsEntriesCount + 1,
					getBlogsEntriesNotInTrashCount(group.getGroupId()));

				if (approved) {
					Assert.assertTrue(
						isAssetEntryVisible(blogsEntry.getEntryId()));
					Assert.assertEquals(
						initialBlogsEntriesSearchCount + 1,
						searchBlogsEntriesCount(group.getGroupId()));
				}
				else {
					Assert.assertFalse(
						isAssetEntryVisible(blogsEntry.getEntryId()));
					Assert.assertEquals(
						initialBlogsEntriesSearchCount,
						searchBlogsEntriesCount(group.getGroupId()));
				}
			}
		}

		catch(Exception e) {
			Assert.fail(
				"Unexpected error restoring blogsEntry from Trash: " +
					e.getMessage());
		}
	}

}