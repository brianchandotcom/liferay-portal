/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.stats;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch.core.SearchRequest;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public interface StatsTranslator {

	public void populateRequest(
		SearchRequest.Builder searchRequestBuilder, StatsRequest statsRequest);

	public StatsResponse translateResponse(
		Map<String, Aggregate> aggregateMap, StatsRequest statsRequest);

}