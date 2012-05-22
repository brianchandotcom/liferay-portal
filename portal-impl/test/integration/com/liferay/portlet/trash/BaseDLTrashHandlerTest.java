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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.BaseDLAppTest;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLIndexer;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

/**
 * @author Alexander Chow
 */
public class BaseDLTrashHandlerTest extends BaseDLAppTest {

	protected AssetEntry fetchAssetEntry(long fileEntryId) throws Exception {
		return AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntryId);
	}

	protected int getFolderAndFileEntriesNotInTrashCount() throws Exception {
		return DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
			parentFolder.getRepositoryId(), parentFolder.getFolderId(),
			WorkflowConstants.STATUS_ANY, false);
	}

	protected int getTrashEntriesCount() throws Exception {
		Object[] result = TrashEntryServiceUtil.getEntries(
			parentFolder.getGroupId());

		return (Integer)result[1];
	}

	protected boolean isAssetEntryVisible(long fileEntryId) throws Exception {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			DLFileEntryConstants.getClassName(), fileEntryId);

		return assetEntry.isVisible();
	}

	protected int searchFileEntriesCount() throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(DLIndexer.class);

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

}