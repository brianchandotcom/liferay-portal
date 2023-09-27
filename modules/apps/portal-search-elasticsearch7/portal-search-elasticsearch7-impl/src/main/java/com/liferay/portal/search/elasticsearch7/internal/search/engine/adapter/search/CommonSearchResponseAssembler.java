/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author Michael C. Han
 */
public interface CommonSearchResponseAssembler {

	public void assemble(
		SearchSourceBuilder searchSourceBuilder, SearchResponse searchResponse,
		BaseSearchRequest baseSearchRequest,
		BaseSearchResponse baseSearchResponse);

}