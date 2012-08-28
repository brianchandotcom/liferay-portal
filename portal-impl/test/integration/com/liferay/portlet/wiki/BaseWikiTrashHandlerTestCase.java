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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntryList;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseWikiTrashHandlerTestCase {

	protected WikiNode addWikiNode(String name, ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		WikiNode wikiNode = WikiNodeLocalServiceUtil.addNode(
			getUserId(), name, ServiceTestUtil.randomString(), serviceContext);

		return wikiNode;
	}

	protected WikiPage addWikiPage(
			long nodeId, String title, ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		WikiPage wikiPage = WikiPageLocalServiceUtil.addPage(
			getUserId(), nodeId, title, ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), true, serviceContext);

		WikiPageLocalServiceUtil.updateStatus(
			getUserId(), wikiPage.getResourcePrimKey(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return wikiPage;
	}

	protected AssetEntry fetchAssetEntry(String className, long classPk)
		throws Exception {

		return AssetEntryLocalServiceUtil.fetchEntry(className, classPk);
	}

	protected int getTrashEntriesCount(long groupId) throws Exception {
		TrashEntryList trashEntryList = TrashEntryServiceUtil.getEntries(
			groupId);

		return trashEntryList.getCount();
	}

	protected long getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	protected int getWikiPagesNotInTrashCount(long nodeId) throws Exception {
		return WikiPageLocalServiceUtil.getPagesCount(
			nodeId, WorkflowConstants.STATUS_APPROVED);
	}

	protected boolean isAssetEntryVisible(String className, long classPk)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPk);

		return assetEntry.isVisible();
	}

	protected int searchCount(String className, long groupId) throws Exception {
		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		Indexer indexer = IndexerRegistryUtil.getIndexer(className);

		SearchContext searchContext = ServiceTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		Thread.sleep(1000 * TestPropsValues.JUNIT_DELAY_FACTOR);

		Hits results = TrashEntryServiceUtil.search(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return results.getLength();
	}

}