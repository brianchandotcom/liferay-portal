/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.DateRangeFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.solr8.internal.AggregationFilteringFacetProcessorContext;
import com.liferay.portal.search.solr8.internal.FacetProcessorContext;
import com.liferay.portal.search.solr8.internal.facet.DateRangeFacetProcessor;
import com.liferay.portal.search.solr8.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr8.internal.facet.FacetUtil;
import com.liferay.portal.search.solr8.internal.facet.RangeFacetProcessor;
import com.liferay.portal.search.solr8.internal.filter.FilterTranslator;
import com.liferay.portal.search.solr8.internal.query.SolrQueryVisitor;
import com.liferay.portal.search.solr8.internal.query.translator.SolrQueryTranslator;
import com.liferay.portal.search.solr8.internal.stats.StatsTranslator;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author Bryan Engler
 */
public class BaseSolrQueryAssembler {

	public void assemble(
		SolrQuery solrQuery, BaseSearchRequest baseSearchRequest) {

		setExplain(solrQuery, baseSearchRequest);
		setFacets(solrQuery, baseSearchRequest);
		setFilterQueries(solrQuery, baseSearchRequest);
		setQuery(solrQuery, baseSearchRequest);
		setStatsRequests(solrQuery, baseSearchRequest);
	}

	protected void addFilterQuery(
		List<String> filterQueries, Facet facet, String tag) {

		BooleanClause<Filter> booleanClause =
			facet.getFacetFilterBooleanClause();

		if (booleanClause == null) {
			return;
		}

		filterQueries.add(
			StringBundler.concat(
				"{!tag=", tag, StringPool.CLOSE_CURLY_BRACE,
				translate(booleanClause)));
	}

	protected void excludeTags(
		Map<String, JSONObject> map, String excludeTagsString) {

		for (JSONObject jsonObject : map.values()) {
			jsonObject.put("excludeTags", excludeTagsString);
		}
	}

	protected String getExcludeTagsString(
		String tag, FacetProcessorContext facetProcessorContext) {

		String excludeTagsString = facetProcessorContext.getExcludeTagsString();

		if (excludeTagsString == null) {
			return tag;
		}

		return excludeTagsString;
	}

	protected String getFacetString(Map<String, JSONObject> jsonObjects) {
		Set<Map.Entry<String, JSONObject>> entrySet = jsonObjects.entrySet();

		StringBundler sb = new StringBundler((2 * entrySet.size()) + 1);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		for (Map.Entry<String, JSONObject> entry : entrySet) {
			sb.append(
				StringBundler.concat(
					StringPool.QUOTE, entry.getKey(), StringPool.QUOTE,
					StringPool.COLON, entry.getValue()));
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	protected String getQueryString(BaseSearchRequest baseSearchRequest) {
		com.liferay.portal.search.query.Query query =
			baseSearchRequest.getQuery();

		if (query != null) {
			return _solrQueryTranslator.translate(query);
		}

		Query legacyQuery = baseSearchRequest.getQuery71();

		if (legacyQuery != null) {
			return SolrQueryVisitor.INSTANCE.translate(legacyQuery);
		}

		return null;
	}

	protected void setExplain(
		SolrQuery solrQuery, BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.isExplain()) {
			solrQuery.setShowDebugInfo(true);
		}
	}

	protected void setFacets(
		SolrQuery solrQuery, BaseSearchRequest baseSearchRequest) {

		Map<String, Facet> facets = baseSearchRequest.getFacets();

		Map<String, JSONObject> jsonObjects = new LinkedHashMap<>();

		List<String> postFilterQueries = new ArrayList<>();

		FacetProcessorContext facetProcessorContext =
			AggregationFilteringFacetProcessorContext.newInstance(
				facets, baseSearchRequest.isBasicFacetSelection());

		for (Facet facet : facets.values()) {
			if (facet.isStatic()) {
				continue;
			}

			String tag = FacetUtil.getAggregationName(facet);

			addFilterQuery(postFilterQueries, facet, tag);

			Map<String, JSONObject> facetParameters = _getFacetParameters(
				facet);

			excludeTags(
				facetParameters,
				getExcludeTagsString(tag, facetProcessorContext));

			jsonObjects.putAll(facetParameters);
		}

		if (!jsonObjects.isEmpty()) {
			solrQuery.add("json.facet", getFacetString(jsonObjects));
		}

		if (!postFilterQueries.isEmpty()) {
			solrQuery.setFilterQueries(
				ArrayUtil.toStringArray(postFilterQueries));
		}
	}

	protected void setFilterQueries(
		SolrQuery solrQuery, BaseSearchRequest baseSearchRequest) {

		List<String> filterQueries = new ArrayList<>();

		Query query = baseSearchRequest.getQuery71();

		if (query != null) {
			_add(filterQueries, query.getPreBooleanFilter());
			_add(filterQueries, query.getPostFilter());
		}

		_addAll(filterQueries, solrQuery.getFilterQueries());

		if (!filterQueries.isEmpty()) {
			solrQuery.setFilterQueries(filterQueries.toArray(new String[0]));

			if (Validator.isBlank(solrQuery.getQuery())) {
				solrQuery.setQuery("*:*");
			}
		}
	}

	protected void setQuery(
		SolrQuery solrQuery, BaseSearchRequest baseSearchRequest) {

		String queryString = getQueryString(baseSearchRequest);

		if (!Validator.isBlank(queryString)) {
			solrQuery.setQuery(queryString);
		}
	}

	protected void setStatsRequests(
		SolrQuery solrQuery, BaseSearchRequest baseSearchRequest) {

		for (StatsRequest statsRequest : baseSearchRequest.getStatsRequests()) {
			_statsTranslator.populateRequest(solrQuery, statsRequest);
		}
	}

	protected org.apache.lucene.search.Query translate(
		BooleanClause<Filter> booleanClause) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			booleanClause.getClause(), booleanClause.getBooleanClauseOccur());

		return _filterTranslator.translate(booleanFilter);
	}

	private void _add(Collection<String> filterQueries, Filter filter) {
		if (filter != null) {
			org.apache.lucene.search.Query query = _filterTranslator.translate(
				filter);

			filterQueries.add(query.toString());
		}
	}

	private void _addAll(
		List<String> filterQueries, String[] facetPostFilterQueries) {

		if (ArrayUtil.isNotEmpty(facetPostFilterQueries)) {
			Collections.addAll(filterQueries, facetPostFilterQueries);
		}
	}

	private Map<String, JSONObject> _getFacetParameters(Facet facet) {
		Class<?> clazz = facet.getClass();

		FacetProcessor<SolrQuery> facetProcessor =
			_facetProcessors.getOrDefault(
				clazz.getName(), _defaultFacetProcessor);

		return facetProcessor.processFacet(facet);
	}

	private static final FacetProcessor<SolrQuery> _defaultFacetProcessor =
		new FacetProcessor<SolrQuery>() {

			@Override
			public Map<String, JSONObject> processFacet(Facet facet) {
				return LinkedHashMapBuilder.<String, JSONObject>put(
					FacetUtil.getAggregationName(facet),
					_getFacetParametersJSONObject(facet)
				).build();
			}

			private JSONObject _getFacetParametersJSONObject(Facet facet) {
				FacetConfiguration facetConfiguration =
					facet.getFacetConfiguration();

				JSONObject dataJSONObject = facetConfiguration.getData();

				return JSONUtil.put(
					"field", facet.getFieldName()
				).put(
					"limit",
					() -> {
						int limit = dataJSONObject.getInt("maxTerms");

						if (limit > 0) {
							return limit;
						}

						return null;
					}
				).put(
					"mincount",
					() -> {
						int minCount = dataJSONObject.getInt(
							"frequencyThreshold", -1);

						if (minCount >= 0) {
							return minCount;
						}

						return null;
					}
				).put(
					"sort",
					() -> {
						String order = facetConfiguration.getOrder();

						if (order.equals("OrderValueAsc")) {
							return JSONUtil.put("index", "asc");
						}

						return JSONUtil.put("count", "desc");
					}
				).put(
					"type", "terms"
				);
			}

		};

	private static final Map<String, FacetProcessor<SolrQuery>>
		_facetProcessors =
			HashMapBuilder.<String, FacetProcessor<SolrQuery>>put(
				DateRangeFacet.class.getName(), DateRangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.DateRangeFacetImpl",
				DateRangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.ModifiedFacetImpl",
				RangeFacetProcessor.INSTANCE
			).put(
				RangeFacet.class.getName(), RangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.RangeFacetImpl",
				RangeFacetProcessor.INSTANCE
			).build();

	private final FilterTranslator _filterTranslator = new FilterTranslator();
	private final SolrQueryTranslator _solrQueryTranslator =
		new SolrQueryTranslator();
	private final StatsTranslator _statsTranslator = new StatsTranslator();

}