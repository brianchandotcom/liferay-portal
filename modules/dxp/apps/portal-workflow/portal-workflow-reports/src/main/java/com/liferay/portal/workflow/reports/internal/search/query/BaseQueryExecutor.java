/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.reports.internal.search.query;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.facet.custom.CustomFacetFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseQueryExecutor {

	public BooleanQuery createBooleanQuery(BooleanFilter preBooleanFilter) {
		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.setPreBooleanFilter(preBooleanFilter);

		return booleanQuery;
	}

	public Facet createFacet(String fieldName) {
		com.liferay.portal.search.facet.Facet facet =
			customFacetFactory.newInstance(null);

		facet.setFieldName(fieldName);

		return facet;
	}

	public abstract String getIndexName();

	public Hits search(
		Query query, Facet facet, int start, int size, Sort... sorts) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.putFacet(facet.getFieldName(), facet);
		searchSearchRequest.setIndexNames(new String[] {getIndexName()});
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(size);
		searchSearchRequest.setStart(start);
		searchSearchRequest.setSorts(sorts);
		searchSearchRequest.setStats(Collections.emptyMap());

		SearchSearchResponse searchSearchResponse =
			searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search request string: " +
					searchSearchResponse.getSearchRequestString());
		}

		return searchSearchResponse.getHits();
	}

	public Map<String, Hits> search(
		Query query, GroupBy groupBy, int start, int size, Sort... sorts) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setGroupBy(groupBy);
		searchSearchRequest.setIndexNames(new String[] {getIndexName()});
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(size);
		searchSearchRequest.setSorts(groupBy.getSorts());
		searchSearchRequest.setStart(start);
		searchSearchRequest.setStats(Collections.emptyMap());

		SearchSearchResponse searchSearchResponse =
			searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search request string: " +
					searchSearchResponse.getSearchRequestString());
		}

		Hits hits = searchSearchResponse.getHits();

		return hits.getGroupedHits();
	}

	public Hits search(Query query, int start, int size, Sort... sorts) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(new String[] {getIndexName()});
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(size);
		searchSearchRequest.setStart(start);
		searchSearchRequest.setSorts(sorts);
		searchSearchRequest.setStats(Collections.emptyMap());

		SearchSearchResponse searchSearchResponse =
			searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search request string: " +
					searchSearchResponse.getSearchRequestString());
		}

		return searchSearchResponse.getHits();
	}

	public long searchCount(Query query) {
		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(new String[] {getIndexName()});
		countSearchRequest.setQuery(query);

		CountSearchResponse countSearchResponse =
			searchRequestExecutor.executeSearchRequest(countSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search request string: " +
					countSearchResponse.getSearchRequestString());
		}

		return countSearchResponse.getCount();
	}

	public long searchCount(Query query, Facet facet) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.putFacet(facet.getFieldName(), facet);
		searchSearchRequest.setIndexNames(new String[] {getIndexName()});
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(0);
		searchSearchRequest.setStart(0);
		searchSearchRequest.setStats(Collections.emptyMap());

		SearchSearchResponse searchSearchResponse =
			searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Search request string: " +
					searchSearchResponse.getSearchRequestString());
		}

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		return termCollectors.size();
	}

	@Reference
	protected CustomFacetFactory customFacetFactory;

	@Reference
	protected SearchRequestExecutor searchRequestExecutor;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseQueryExecutor.class);

}