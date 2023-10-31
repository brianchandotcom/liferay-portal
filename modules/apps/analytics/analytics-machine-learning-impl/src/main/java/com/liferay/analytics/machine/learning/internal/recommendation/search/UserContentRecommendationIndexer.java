/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation.search;

import com.liferay.analytics.machine.learning.internal.search.api.RecommendationIndexer;
import com.liferay.analytics.machine.learning.internal.search.index.helper.RecommendationSearchEngineHelper;
import com.liferay.portal.search.index.IndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = RecommendationIndexer.class)
public class UserContentRecommendationIndexer implements RecommendationIndexer {

	@Override
	public void createIndex(long companyId) {
		_recommendationSearchEngineHelper.createIndex(
			getIndexName(companyId),
			"user-content-recommendation-mappings.json");
	}

	@Override
	public void dropIndex(long companyId) {
		_recommendationSearchEngineHelper.dropIndex(getIndexName(companyId));
	}

	@Override
	public String getIndexName(long companyId) {
		return _indexNameBuilder.getIndexName(companyId) +
			"-user-content-recommendation";
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private RecommendationSearchEngineHelper _recommendationSearchEngineHelper;

}