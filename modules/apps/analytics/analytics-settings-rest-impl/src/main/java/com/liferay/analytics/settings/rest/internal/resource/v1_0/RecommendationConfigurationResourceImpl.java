/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.RecommendationConfiguration;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.RecommendationConfigurationResource;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;

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
				setContentRecommenderMostPopularItemsEnabled(
					analyticsConfiguration::
						contentRecommenderMostPopularItemsEnabled);

				setContentRecommenderUserPersonalizationEnabled(
					analyticsConfiguration::
						contentRecommenderUserPersonalizationEnabled);
			}
		};
	}

	@Override
	public void putRecommendationConfiguration(
			RecommendationConfiguration recommendationConfiguration)
		throws Exception {

		_analyticsCloudClient.updateAnalyticsDataSourceDetails(
			_configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, contextCompany.getCompanyId()),
			recommendationConfiguration.
				getContentRecommenderMostPopularItemsEnabled(),
			recommendationConfiguration.
				getContentRecommenderUserPersonalizationEnabled());

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextCompany.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"contentRecommenderMostPopularItemsEnabled",
				recommendationConfiguration.
					getContentRecommenderMostPopularItemsEnabled()
			).put(
				"contentRecommenderUserPersonalizationEnabled",
				recommendationConfiguration.
					getContentRecommenderUserPersonalizationEnabled()
			).build());

		_recommendationConfiguration = recommendationConfiguration;
	}

	@Activate
	protected void activate() {
		_analyticsCloudClient = new AnalyticsCloudClient(_http);
	}

	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	private RecommendationConfiguration _recommendationConfiguration;

}