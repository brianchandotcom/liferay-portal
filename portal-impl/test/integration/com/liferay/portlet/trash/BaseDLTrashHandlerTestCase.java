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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.BaseDLAppTestCase;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLIndexer;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashEntryList;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Chow
 */
public abstract class BaseDLTrashHandlerTestCase extends BaseDLAppTestCase {

	@Test
	public void testTrashSubEntryAndDeleteFolder() throws Exception {
		trashSubEntry(true);
	}

	@Test
	public void testTrashSubEntryAndRestoreSubEntry() throws Exception {
		trashSubEntry(false);
	}

	protected abstract long doAddSubEntry(long folderId1, long folderId2)
		throws Exception;

	protected abstract void doMoveSubEntryFromTrash(long subEntryId)
		throws Exception;

	protected abstract void doMoveSubEntryToTrash(long subEntryId)
		throws Exception;

	protected AssetEntry fetchAssetEntry(String className, long classPK)
		throws Exception {

		return AssetEntryLocalServiceUtil.fetchEntry(className, classPK);
	}

	protected int getActiveFileRanksCount(long fileEntryId) throws Exception {
		List<DLFileRank> dlFileRanks = DLFileRankLocalServiceUtil.getFileRanks(
			parentFolder.getGroupId(), parentFolder.getUserId());

		int count = 0;

		for (DLFileRank dlFileRank : dlFileRanks) {
			if (dlFileRank.getFileEntryId() == fileEntryId) {
				count++;
			}
		}

		return count;
	}

	protected int getNotInTrashCount() throws Exception {
		return DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
			parentFolder.getRepositoryId(), parentFolder.getFolderId(),
			WorkflowConstants.STATUS_ANY, false);
	}

	protected int getTrashEntriesCount() throws Exception {
		TrashEntryList trashEntryList = TrashEntryServiceUtil.getEntries(
			parentFolder.getGroupId());

		return trashEntryList.getCount();
	}

	protected boolean isAssetEntryVisible(String className, long classPK)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		return assetEntry.isVisible();
	}

	protected int searchFileEntriesCount() throws Exception {
		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		Indexer indexer = IndexerRegistryUtil.getIndexer(DLIndexer.class);

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		Hits hits = indexer.search(searchContext);

		return hits.getLength();
	}

	protected int searchTrashEntriesCount(String keywords) throws Exception {
		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		Hits hits = TrashEntryServiceUtil.search(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return hits.getLength();
	}

	protected void trashSubEntry(boolean deleteFolder) throws Exception {
		int initialNotInTrashCount = getNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();

		Folder folder1 = addFolder(false, "Folder A1");
		Folder folder2 = addFolder(false, "Folder A2");

		long subEntryId = doAddSubEntry(
			folder1.getFolderId(), folder2.getFolderId());

		Assert.assertEquals(initialNotInTrashCount + 2, getNotInTrashCount());
		Assert.assertEquals(initialTrashEntriesCount, getTrashEntriesCount());

		doMoveSubEntryToTrash(subEntryId);

		DLAppServiceUtil.moveFolderToTrash(folder1.getFolderId());

		Assert.assertEquals(initialNotInTrashCount + 1, getNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 2, getTrashEntriesCount());

		if (deleteFolder) {
			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				DLFolderConstants.getClassName(), folder1.getFolderId());

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					trashEntry.getClassName());

			trashHandler.deleteTrashEntry(trashEntry.getClassPK());

			Assert.assertEquals(
				initialNotInTrashCount + 1, getNotInTrashCount());
			Assert.assertEquals(
				initialTrashEntriesCount + 1, getTrashEntriesCount());
		}
		else {
			doMoveSubEntryFromTrash(subEntryId);

			Assert.assertEquals(
				initialNotInTrashCount + 2, getNotInTrashCount());
			Assert.assertEquals(
				initialTrashEntriesCount + 1, getTrashEntriesCount());
		}
	}

}