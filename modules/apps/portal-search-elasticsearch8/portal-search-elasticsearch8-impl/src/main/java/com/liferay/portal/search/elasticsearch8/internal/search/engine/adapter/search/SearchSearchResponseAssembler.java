/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.core.search.ResponseBody;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

/**
 * @author Michael C. Han
 */
public interface SearchSearchResponseAssembler {

	public void assemble(
		ResponseBody<JsonData> responseBody, String searchRequestString,
		SearchSearchRequest searchSearchRequest,
		SearchSearchResponse searchSearchResponse);

}