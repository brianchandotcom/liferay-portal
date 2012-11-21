package com.liferay.portlet.blogs;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;

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

/**
 * @author Zsolt Berentey
 */
public class BaseBlogsEntryTestCase {

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

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			getUserId(), title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);

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

	protected ServiceContext getServiceContext(BlogsEntry entry)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		String[] trackbacks = StringUtil.split(entry.getTrackbacks());
		String layoutFullURL = PortalUtil.getLayoutFullURL(
			entry.getGroupId(), PortletKeys.BLOGS);

		serviceContext.setAttribute("trackbacks", trackbacks);
		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL(layoutFullURL);
		serviceContext.setScopeGroupId(entry.getGroupId());

		return serviceContext;
	}

	protected long getUserId() {
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

}