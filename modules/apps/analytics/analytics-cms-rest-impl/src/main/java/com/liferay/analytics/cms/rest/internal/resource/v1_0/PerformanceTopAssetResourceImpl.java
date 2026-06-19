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
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import java.io.InputStream;

import java.time.LocalDate;

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

	@Override
	public Response getPerformanceTopAssetExport(
			String assetFilterString, Long[] depotEntryIds, Integer rangeKey,
			Sort[] sorts)
		throws Exception {

		LicenseManagerUtil.checkFreeTier();

		Long[] groupIds = DepotEntryUtil.getGroupIds(
			DepotEntryUtil.getDepotEntries(
				contextCompany.getCompanyId(), depotEntryIds));

		AnalyticsCloudClient analyticsCloudClient = new AnalyticsCloudClient(
			_http);

		InputStream inputStream = analyticsCloudClient.getInputStream(
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId()),
			assetFilterString, Arrays.asList(groupIds), null,
			"/summaries/export", rangeKey, sorts);

		return Response.ok(
			(StreamingOutput)outputStream -> StreamUtil.transfer(
				inputStream, outputStream)
		).header(
			"Content-Disposition",
			StringBundler.concat(
				"attachment; filename=top-assets-", LocalDate.now(), ".csv")
		).build();
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private Http _http;

}