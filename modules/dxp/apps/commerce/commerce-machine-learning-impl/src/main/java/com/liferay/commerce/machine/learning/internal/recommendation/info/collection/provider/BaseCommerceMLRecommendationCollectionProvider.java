/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.info.collection.provider;

import com.liferay.commerce.machine.learning.internal.recommendation.configuration.CommerceMLRecommendationsCollectionProviderConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Riccardo Ferrari
 */
public class BaseCommerceMLRecommendationCollectionProvider {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		commerceMLRecommendationsCollectionProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceMLRecommendationsCollectionProviderConfiguration.class,
				properties);
	}

	protected volatile CommerceMLRecommendationsCollectionProviderConfiguration
		commerceMLRecommendationsCollectionProviderConfiguration;

}