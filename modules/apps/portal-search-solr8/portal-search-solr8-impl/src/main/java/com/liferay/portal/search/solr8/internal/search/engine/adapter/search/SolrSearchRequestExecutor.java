/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.ClearScrollRequest;
import com.liferay.portal.search.engine.adapter.search.ClearScrollResponse;
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeResponse;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchResponse;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "search.engine.impl=Solr", service = SearchRequestExecutor.class
)
public class SolrSearchRequestExecutor implements SearchRequestExecutor {

	@Override
	public ClearScrollResponse executeSearchRequest(
		ClearScrollRequest clearScrollRequest) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ClosePointInTimeResponse executeSearchRequest(
		ClosePointInTimeRequest closePointInTimeRequest) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CountSearchResponse executeSearchRequest(
		CountSearchRequest countSearchRequest) {

		return _countSearchRequestExecutor.execute(countSearchRequest);
	}

	@Override
	public MultisearchSearchResponse executeSearchRequest(
		MultisearchSearchRequest multisearchSearchRequest) {

		return _multisearchSearchRequestExecutor.execute(
			multisearchSearchRequest);
	}

	@Override
	public OpenPointInTimeResponse executeSearchRequest(
		OpenPointInTimeRequest openPointInTimeRequest) {

		throw new UnsupportedOperationException();
	}

	@Override
	public SearchSearchResponse executeSearchRequest(
		SearchSearchRequest searchSearchRequest) {

		return _searchSearchRequestExecutor.execute(searchSearchRequest);
	}

	@Override
	public SuggestSearchResponse executeSearchRequest(
		SuggestSearchRequest suggestSearchRequest) {

		return _suggestSearchRequestExecutor.execute(suggestSearchRequest);
	}

	@Reference
	private CountSearchRequestExecutor _countSearchRequestExecutor;

	@Reference
	private MultisearchSearchRequestExecutor _multisearchSearchRequestExecutor;

	@Reference
	private SearchSearchRequestExecutor _searchSearchRequestExecutor;

	@Reference
	private SuggestSearchRequestExecutor _suggestSearchRequestExecutor;

}