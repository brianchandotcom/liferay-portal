/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface SearchSearchResponseAssembler {

	public void assemble(
		SearchRequest searchRequest, SearchResponse<JsonData> searchResponse,
		SearchSearchRequest searchSearchRequest,
		SearchSearchResponse searchSearchResponse);

}