/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.PerformanceTopAsset;
import com.liferay.analytics.cms.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.cms.rest.internal.depot.entry.util.DepotEntryUtil;
import com.liferay.analytics.cms.rest.resource.v1_0.PerformanceTopAssetResource;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rachael Koestartyo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/performance-top-asset.properties",
	scope = ServiceScope.PROTOTYPE, service = PerformanceTopAssetResource.class
)
public class PerformanceTopAssetResourceImpl
	extends BasePerformanceTopAssetResourceImpl {

	@Override
	public PerformanceTopAsset getPerformanceTopAsset(
			String assetFilterString, Long[] depotEntryIds, Integer rangeKey,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		LicenseManagerUtil.checkFreeTier();

		Long[] groupIds = DepotEntryUtil.getGroupIds(
			DepotEntryUtil.getDepotEntries(
				contextCompany.getCompanyId(), depotEntryIds));

		AnalyticsCloudClient analyticsCloudClient = new AnalyticsCloudClient(
			_http);

		return analyticsCloudClient.getPerformanceTopAsset(
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId()),
			assetFilterString, Arrays.asList(groupIds), pagination.getPage(),
			rangeKey, pagination.getPageSize(), sorts);
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private Http _http;

}