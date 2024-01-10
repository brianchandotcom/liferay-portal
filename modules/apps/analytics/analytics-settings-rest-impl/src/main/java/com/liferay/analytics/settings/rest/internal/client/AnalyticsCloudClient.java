/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.client;

import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsDataSource;
import com.liferay.analytics.settings.rest.internal.client.pagination.Page;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;

import java.util.Locale;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public interface AnalyticsCloudClient {

	public AnalyticsChannel addAnalyticsChannel(long companyId, String name)
		throws Exception;

	public Map<String, Object> connectAnalyticsDataSource(
			Company company, String connectionToken)
		throws Exception;

	public AnalyticsDataSource disconnectAnalyticsDataSource(long companyId)
		throws Exception;

	public Page<AnalyticsChannel> getAnalyticsChannelsPage(
			long companyId, String keywords, int page, int size, Sort[] sorts)
		throws Exception;

	public AnalyticsChannel updateAnalyticsChannel(
			String analyticsChannelId, Group[] commerceChannelGroups,
			long companyId, String dataSourceId, Locale locale,
			Group[] siteGroups)
		throws Exception;

	public AnalyticsDataSource updateAnalyticsDataSourceDetails(
			Boolean accountsSelected, long companyId,
			Boolean commerceChannelsSelected, Boolean contactsSelected,
			Boolean sitesSelected)
		throws Exception;

}