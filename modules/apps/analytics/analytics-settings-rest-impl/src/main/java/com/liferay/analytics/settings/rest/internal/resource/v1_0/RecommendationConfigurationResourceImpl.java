/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.RecommendationConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.RecommendationItem;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.RecommendationConfigurationResource;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/recommendation-configuration.properties",
	scope = ServiceScope.PROTOTYPE,
	service = RecommendationConfigurationResource.class
)
public class RecommendationConfigurationResourceImpl
	extends BaseRecommendationConfigurationResourceImpl {

	@Override
	public RecommendationConfiguration getRecommendationConfiguration()
		throws Exception {

		if (_recommendationConfiguration != null) {
			return _recommendationConfiguration;
		}

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		return new RecommendationConfiguration() {
			{
				setContentRecommenderMostPopularItems(
					() -> new RecommendationItem() {
						{
							setEnabled(
								analyticsConfiguration::
									contentRecommenderMostPopularItemsEnabled);

							setStatus(
								() -> _getStatus(
									"analytics-download-most-viewed-content-" +
										"recommendation"));
						}
					});
				setContentRecommenderUserPersonalization(
					() -> new RecommendationItem() {
						{
							setEnabled(
								analyticsConfiguration::
									contentRecommenderUserPersonalizationEnabled);

							setStatus(
								() -> _getStatus(
									"analytics-download-user-content-" +
										"recommendation"));
						}
					});
			}
		};
	}

	@Override
	public void putRecommendationConfiguration(
			RecommendationConfiguration recommendationConfiguration)
		throws Exception {

		RecommendationItem contentRecommenderMostPopularItems =
			recommendationConfiguration.getContentRecommenderMostPopularItems();

		RecommendationItem contentRecommenderUserPersonalization =
			recommendationConfiguration.
				getContentRecommenderUserPersonalization();

		_analyticsCloudClient.updateAnalyticsDataSourceDetails(
			_configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, contextCompany.getCompanyId()),
			contentRecommenderMostPopularItems.getEnabled(),
			contentRecommenderUserPersonalization.getEnabled());

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextCompany.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"contentRecommenderMostPopularItemsEnabled",
				contentRecommenderMostPopularItems.getEnabled()
			).put(
				"contentRecommenderUserPersonalizationEnabled",
				contentRecommenderUserPersonalization.getEnabled()
			).build());

		_recommendationConfiguration = recommendationConfiguration;
	}

	@Activate
	protected void activate() {
		_analyticsCloudClient = new AnalyticsCloudClient(_http);
	}

	private RecommendationItem.Status _getStatus(String name) {
		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				contextCompany.getCompanyId(), name);

		if (dispatchTrigger == null) {
			return RecommendationItem.Status.DISABLED;
		}

		List<DispatchLog> dispatchLogs =
			_dispatchLogLocalService.getDispatchLogs(
				dispatchTrigger.getDispatchTriggerId(), 0, 1,
				OrderByComparatorFactoryUtil.create(
					"DispatchLog", "startDate", "true"));

		if (ListUtil.isEmpty(dispatchLogs)) {
			return RecommendationItem.Status.CONFIGURING;
		}

		DispatchLog dispatchLog = dispatchLogs.get(0);

		DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(
			dispatchLog.getStatus());

		if (dispatchTaskStatus == DispatchTaskStatus.FAILED) {
			return RecommendationItem.Status.FAILED;
		}

		if (dispatchTaskStatus == DispatchTaskStatus.IN_PROGRESS) {
			return RecommendationItem.Status.CONFIGURING;
		}

		if (dispatchTaskStatus == DispatchTaskStatus.SUCCESSFUL) {
			return RecommendationItem.Status.ENABLED;
		}

		return RecommendationItem.Status.DISABLED;
	}

	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private Http _http;

	private RecommendationConfiguration _recommendationConfiguration;

}