/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch6.internal.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author Michael C. Han
 */
public class SearchResponseScroller {

	public SearchResponseScroller(
		Client client, SearchContext searchContext,
		IndexNameBuilder indexNameBuilder, QueryBuilder queryBuilder,
		TimeValue scrollTimeValue, String... types) {

		_client = client;
		_searchContext = searchContext;
		_indexNameBuilder = indexNameBuilder;
		_queryBuilder = queryBuilder;
		_scrollTimeValue = scrollTimeValue;
		_types = types;
	}

	public boolean close() {
		try {
			ClearScrollRequestBuilder clearScrollRequestBuilder =
				_client.prepareClearScroll();

			clearScrollRequestBuilder.setScrollIds(_previousScrollIds);

			ClearScrollResponse clearScrollResponse =
				clearScrollRequestBuilder.get();

			LogUtil.logActionResponse(_log, clearScrollResponse);

			return clearScrollResponse.isSucceeded();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return false;
		}
	}

	public void prepare() throws Exception {
		SearchRequestBuilder searchRequestBuilder = _client.prepareSearch(
			_indexNameBuilder.getIndexName(_searchContext.getCompanyId()));

		searchRequestBuilder.addSort(
			FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC);
		searchRequestBuilder.addStoredField(Field.UID);
		searchRequestBuilder.setQuery(_queryBuilder);
		searchRequestBuilder.setTypes(_types);

		Scroll scroll = new Scroll(_scrollTimeValue);

		searchRequestBuilder.setScroll(scroll);

		SearchResponse searchResponse = searchRequestBuilder.get();

		_scrollId = searchResponse.getScrollId();

		LogUtil.logActionResponse(_log, searchResponse);
	}

	public boolean scroll(SearchHitsProcessor searchHitsProcessor)
		throws Exception {

		if (Validator.isNull(_scrollId)) {
			return false;
		}

		SearchScrollRequestBuilder searchScrollRequestBuilder =
			_client.prepareSearchScroll(_scrollId);

		Scroll scroll = new Scroll(_scrollTimeValue);

		searchScrollRequestBuilder.setScroll(scroll);

		SearchResponse searchResponse = searchScrollRequestBuilder.get();

		LogUtil.logActionResponse(_log, searchResponse);

		_previousScrollIds.add(_scrollId);

		SearchHits searchHits = searchResponse.getHits();

		SearchHit[] searchHitsArray = searchHits.getHits();

		if (ArrayUtil.isEmpty(searchHitsArray)) {
			_scrollId = null;

			return false;
		}

		searchHitsProcessor.processSearchHits(_searchContext, searchHits);

		_scrollId = searchResponse.getScrollId();

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResponseScroller.class);

	private final Client _client;
	private final IndexNameBuilder _indexNameBuilder;
	private final List<String> _previousScrollIds = new ArrayList<>();
	private final QueryBuilder _queryBuilder;
	private String _scrollId;
	private final TimeValue _scrollTimeValue;
	private final SearchContext _searchContext;
	private final String[] _types;

}