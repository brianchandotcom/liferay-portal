/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.RecommendationConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.RecommendationConfigurationResource;
import com.liferay.portal.kernel.util.HashMapBuilder;

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
				setMostPopularContentEnabled(
					analyticsConfiguration::mostPopularContentEnabled);

				setUserContentEnabled(
					analyticsConfiguration::userContentEnabled);
			}
		};
	}

	@Override
	public void putRecommendationConfiguration(
			RecommendationConfiguration recommendationConfiguration)
		throws Exception {

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextCompany.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"mostPopularContentEnabled",
				recommendationConfiguration.getMostPopularContentEnabled()
			).put(
				"userContentEnabled",
				recommendationConfiguration.getUserContentEnabled()
			).build());

		_recommendationConfiguration = recommendationConfiguration;
	}

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private RecommendationConfiguration _recommendationConfiguration;

}