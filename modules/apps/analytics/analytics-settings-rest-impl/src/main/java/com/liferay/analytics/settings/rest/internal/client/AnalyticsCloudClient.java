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

package com.liferay.analytics.settings.rest.internal.client;

import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsDataSource;
import com.liferay.analytics.settings.rest.internal.client.pagination.Page;

import java.util.Locale;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public interface AnalyticsCloudClient {

	public AnalyticsChannel addAnalyticsChannel(long companyId, String name)
		throws Exception;

	public Map<String, Object> connectAnalyticsDataSource(
			long companyId, String connectionToken)
		throws Exception;

	public AnalyticsDataSource disconnectAnalyticsDataSource(long companyId)
		throws Exception;

	public Page<AnalyticsChannel> getAnalyticsChannelsPage(
			long companyId, String keywords, int page, int size)
		throws Exception;

	public AnalyticsChannel updateAnalyticsChannel(
			String channelId, Long[] commerceChannelIds, long companyId,
			String dataSourceId, Locale locale, Long[] siteGroupIds)
		throws Exception;

	public AnalyticsDataSource updateAnalyticsDataSourceDetails(
			Boolean accountsSelected, long companyId,
			Boolean commerceChannelsSelected, Boolean contactsSelected,
			Boolean sitesSelected)
		throws Exception;

}