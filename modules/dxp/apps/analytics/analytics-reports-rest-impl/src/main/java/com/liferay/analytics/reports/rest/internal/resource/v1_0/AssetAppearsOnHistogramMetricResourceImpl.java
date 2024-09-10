/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.rest.internal.resource.v1_0;

import com.liferay.analytics.reports.rest.dto.v1_0.AssetAppearsOnHistogramMetric;
import com.liferay.analytics.reports.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.reports.rest.resource.v1_0.AssetAppearsOnHistogramMetricResource;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Marcos Martins
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asset-appears-on-histogram-metric.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AssetAppearsOnHistogramMetricResource.class
)
public class AssetAppearsOnHistogramMetricResourceImpl
	extends BaseAssetAppearsOnHistogramMetricResourceImpl {

	@Override
	public AssetAppearsOnHistogramMetric
			getGroupAssetMetricAssetTypeAppearsOnHistogram(
				Long groupId, String assetType, String assetId,
				String identityType, Integer rangeKey)
		throws Exception {

		List<Long> analyticsCloudChannelIds = new ArrayList<>();

		Group group = _groupLocalService.getGroup(groupId);

		DepotEntry depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
			groupId);

		if (depotEntry != null) {
			analyticsCloudChannelIds = _getAnalyticsCloudChannelIds(
				_depotEntryGroupRelLocalService.getDepotEntryGroupRels(
					depotEntry));
		}
		else {
			analyticsCloudChannelIds = Collections.singletonList(
				Long.valueOf(
					group.getTypeSettingsProperty("analyticsChannelId")));
		}

		AnalyticsCloudClient analyticsCloudClient = new AnalyticsCloudClient(
			_http);

		return analyticsCloudClient.getAssetAppearsOnHistogramMetric(
			_analyticsSettingsManager.getAnalyticsConfiguration(
				group.getCompanyId()),
			assetId, assetType, analyticsCloudChannelIds, identityType,
			rangeKey);
	}

	private List<Long> _getAnalyticsCloudChannelIds(
			List<DepotEntryGroupRel> depotEntryGroupRels)
		throws Exception {

		List<Long> analyticsCloudChannelIds = new ArrayList<>();

		for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
			Group group = _groupLocalService.getGroup(
				depotEntryGroupRel.getToGroupId());

			String analyticsChannelId = group.getTypeSettingsProperty(
				"analyticsChannelId");

			if (Validator.isNotNull(analyticsChannelId)) {
				analyticsCloudChannelIds.add(Long.valueOf(analyticsChannelId));
			}
		}

		return analyticsCloudChannelIds;
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

}