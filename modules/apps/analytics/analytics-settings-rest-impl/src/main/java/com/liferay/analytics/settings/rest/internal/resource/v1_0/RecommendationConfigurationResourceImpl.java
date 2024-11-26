/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.rest.resource.v1_0.RecommendationConfigurationResource;

import org.osgi.service.component.annotations.Component;
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
}