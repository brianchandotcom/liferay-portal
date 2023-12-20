/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.stats;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.Map;

import org.opensearch.client.opensearch._types.aggregations.Aggregate;
import org.opensearch.client.opensearch.core.SearchRequest;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface StatsTranslator {

	public void populateRequest(
		SearchRequest.Builder searchRequestBuilder, StatsRequest statsRequest);

	public StatsResponse translateResponse(
		Map<String, Aggregate> aggregateMap, StatsRequest statsRequest);

}