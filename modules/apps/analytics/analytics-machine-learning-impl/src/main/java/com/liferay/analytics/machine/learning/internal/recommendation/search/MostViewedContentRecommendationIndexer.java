/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.search;

import com.liferay.analytics.machine.learning.internal.search.api.RecommendationIndexer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(service = RecommendationIndexer.class)
public class MostViewedContentRecommendationIndexer
	extends BaseRecommendationIndexer {

	public MostViewedContentRecommendationIndexer() {
		super("most-viewed-content-recommendation");
	}

}