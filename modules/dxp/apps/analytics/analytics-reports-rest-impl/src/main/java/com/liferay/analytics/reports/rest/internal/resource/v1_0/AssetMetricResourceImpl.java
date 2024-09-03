/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.rest.internal.resource.v1_0;

import com.liferay.analytics.reports.rest.dto.v1_0.AssetMetric;
import com.liferay.analytics.reports.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.reports.rest.resource.v1_0.AssetMetricResource;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Http;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Marcos Martins
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asset-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = AssetMetricResource.class
)
public class AssetMetricResourceImpl extends BaseAssetMetricResourceImpl {

	@Override
	public AssetMetric getGroupAssetMetric(
			Long groupId, String assetType, String assetId, String identityType,
			Integer rangeKey, String[] selectedMetrics)
		throws Exception {

		AnalyticsCloudClient analyticsCloudClient = new AnalyticsCloudClient(
			_http);

		Group group = _groupLocalService.getGroup(groupId);

		return analyticsCloudClient.getAssetMetric(
			_analyticsSettingsManager.getAnalyticsConfiguration(
				group.getCompanyId()),
			assetId, assetType,
			Long.valueOf(group.getTypeSettingsProperty("analyticsChannelId")),
			identityType, rangeKey, selectedMetrics);
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

}