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

package com.liferay.portlet.blogs.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.messaging.BlogsStatsUpdateRequest;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

/**
 * @author Mate Thurzo
 */
public class UpdateBlogsStatsMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {

		BlogsStatsUpdateRequest updateRequest =
			(BlogsStatsUpdateRequest)message.getPayload();

		BlogsEntry entry =
			BlogsEntryLocalServiceUtil.getEntry(updateRequest.getEntryId());

		// Statistics

		BlogsStatsUserLocalServiceUtil.updateStatsUser(
			updateRequest.getGroupId(), updateRequest.getUserId());

		// Asset

		if (updateRequest.isAssetEntryUpdateNeeded()) {
			AssetEntryLocalServiceUtil.updateVisible(
				BlogsEntry.class.getName(), updateRequest.getEntryId(),
				updateRequest.getAssetEntryVisibility());
		}

		// Social

		if (updateRequest.isSocialActivityUpdateNeeded()) {
			if (updateRequest.isSocialActivityUniqueActivity()) {
				SocialActivityLocalServiceUtil.addUniqueActivity(
					updateRequest.getUserId(), updateRequest.getGroupId(),
					BlogsEntry.class.getName(), updateRequest.getEntryId(),
					updateRequest.getSocialActivityActivityKey(),
					updateRequest.getSocialActivityExtraData(),
					updateRequest.getSocialActivityReceiverUserId());
			}
			else {
				SocialActivityLocalServiceUtil.addActivity(
					updateRequest.getUserId(), updateRequest.getGroupId(),
					BlogsEntry.class.getName(), updateRequest.getEntryId(),
					updateRequest.getSocialActivityActivityKey(),
					updateRequest.getSocialActivityExtraData(),
					updateRequest.getSocialActivityReceiverUserId());
			}

			// Indexer

			Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

			indexer.reindex(entry);

			// Subscriptions

			BlogsEntryLocalServiceUtil.notifySubscribers(
				entry, updateRequest.getServiceContext());

			// Ping

			BlogsEntryLocalServiceUtil.performPings(
				entry, updateRequest.getServiceContext());
		}

		// Cleanup

		BlogsEntryLocalServiceUtil.unscheduleBlogsStatsUpdate(entry);
	}

}