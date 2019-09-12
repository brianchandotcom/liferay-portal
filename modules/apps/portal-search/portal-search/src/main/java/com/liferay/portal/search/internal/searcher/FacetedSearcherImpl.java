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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.internal.indexer.PreFilterContributorHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class FacetedSearcherImpl
	extends BaseSearcher implements FacetedSearcher {

	public FacetedSearcherImpl(
		ExpandoQueryContributor expandoQueryContributor,
		IndexerRegistry indexerRegistry,
		IndexSearcherHelper indexSearcherHelper,
		PreFilterContributorHelper preFilterContributorHelper,
		SearchEngineHelper searchEngineHelper) {

		_expandoQueryContributor = expandoQueryContributor;
		_indexerRegistry = indexerRegistry;
		_indexSearcherHelper = indexSearcherHelper;
		_preFilterContributorHelper = preFilterContributorHelper;
		_searchEngineHelper = searchEngineHelper;
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = new BooleanQueryImpl();

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			_getEntryClassNameIndexerMap(
				_getEntryClassNames(searchContext),
				searchContext.getSearchEngineId());

		_addSearchKeywords(
			searchQuery, luceneSyntax, entryClassNameIndexerMap, searchContext);

		_addSearchTerms(
			searchQuery, fullQueryBooleanFilter, luceneSyntax,
			entryClassNameIndexerMap, searchContext);

		_addPreFilters(
			fullQueryBooleanFilter, entryClassNameIndexerMap, searchContext);

		BooleanQuery fullQuery = new BooleanQueryImpl();

		if (fullQueryBooleanFilter.hasClauses()) {
			fullQuery.setPreBooleanFilter(fullQueryBooleanFilter);
		}

		if (searchQuery.hasClauses()) {
			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		BooleanClause<Query>[] booleanClauses =
			searchContext.getBooleanClauses();

		if (booleanClauses != null) {
			for (BooleanClause<Query> booleanClause : booleanClauses) {
				fullQuery.add(
					booleanClause.getClause(),
					booleanClause.getBooleanClauseOccur());
			}
		}

		_postProcessFullQuery(
			entryClassNameIndexerMap, fullQuery, searchContext);

		return fullQuery;
	}

	@Override
	protected Hits doSearch(SearchContext searchContext)
		throws SearchException {

		String keywords = StringUtil.trim(searchContext.getKeywords());

		if (Validator.isBlank(keywords) &&
			!GetterUtil.getBoolean(
				searchContext.getAttribute(
					SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH))) {

			return new HitsImpl();
		}

		try {
			searchContext.setSearchEngineId(getSearchEngineId());

			BooleanFilter booleanFilter = new BooleanFilter();

			booleanFilter.addRequiredTerm(
				Field.COMPANY_ID, searchContext.getCompanyId());

			Query query = _getFinalQuery(
				createFullQuery(booleanFilter, searchContext));

			query.setQueryConfig(searchContext.getQueryConfig());

			return _indexSearcherHelper.search(searchContext, query);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		String[] entryClassNames = _getEntryClassNames(searchContext);

		if (ArrayUtil.isEmpty(entryClassNames)) {
			return super.isFilterSearch();
		}

		for (String entryClassName : entryClassNames) {
			Indexer<?> indexer = _indexerRegistry.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			if (indexer.isFilterSearch()) {
				return true;
			}
		}

		return super.isFilterSearch();
	}

	private void _addIndexerProvidedSearchTerms(
			BooleanQuery searchQuery, Indexer<?> indexer,
			BooleanFilter booleanFilter, boolean luceneSyntax,
			SearchContext searchContext)
		throws Exception {

		if (!luceneSyntax) {
			indexer.postProcessSearchQuery(
				searchQuery, booleanFilter, searchContext);
		}

		for (IndexerPostProcessor indexerPostProcessor :
				indexer.getIndexerPostProcessors()) {

			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, booleanFilter, searchContext);
		}
	}

	private void _addPreFilters(
		BooleanFilter booleanFilter,
		Map<String, Indexer<?>> entryClassNameIndexerMap,
		SearchContext searchContext) {

		_preFilterContributorHelper.contribute(
			booleanFilter, entryClassNameIndexerMap, searchContext);
	}

	private void _addSearchKeywords(
			BooleanQuery booleanQuery, boolean luceneSyntax,
			Map<String, Indexer<?>> entryClassNameIndexerMap,
			SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (luceneSyntax) {
			if (!Validator.isBlank(keywords)) {
				booleanQuery.add(
					new StringQuery(keywords), BooleanClauseOccur.MUST);
			}
		}
		else {
			_expandoQueryContributor.contribute(
				keywords, booleanQuery,
				ArrayUtil.toStringArray(entryClassNameIndexerMap.keySet()),
				searchContext);
		}
	}

	private void _addSearchTerms(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			boolean luceneSyntax,
			Map<String, Indexer<?>> entryClassNameIndexerMap,
			SearchContext searchContext)
		throws Exception {

		for (Indexer<?> indexer : entryClassNameIndexerMap.values()) {
			_addIndexerProvidedSearchTerms(
				searchQuery, indexer, fullQueryBooleanFilter, luceneSyntax,
				searchContext);
		}
	}

	private Map<String, Indexer<?>> _getEntryClassNameIndexerMap(
		String[] entryClassNames, String searchEngineId) {

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			new LinkedHashMap<>();

		for (String entryClassName : entryClassNames) {
			Indexer<?> indexer = _indexerRegistry.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			if (!searchEngineId.equals(indexer.getSearchEngineId())) {
				continue;
			}

			entryClassNameIndexerMap.put(entryClassName, indexer);
		}

		return entryClassNameIndexerMap;
	}

	private String[] _getEntryClassNames(SearchContext searchContext) {
		String[] entryClassNames = searchContext.getEntryClassNames();

		if (!ArrayUtil.isEmpty(entryClassNames)) {
			return entryClassNames;
		}

		return _searchEngineHelper.getEntryClassNames();
	}

	private Query _getFinalQuery(Query query) {
		if (query.hasChildren()) {
			return query;
		}

		MatchAllQuery matchAllQuery = new MatchAllQuery();

		matchAllQuery.setPostFilter(query.getPostFilter());
		matchAllQuery.setPreBooleanFilter(query.getPreBooleanFilter());

		return matchAllQuery;
	}

	private void _postProcessFullQuery(
			Map<String, Indexer<?>> entryClassNameIndexerMap,
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		for (Indexer<?> indexer : entryClassNameIndexerMap.values()) {
			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessFullQuery(
					fullQuery, searchContext);
			}
		}
	}

	private final ExpandoQueryContributor _expandoQueryContributor;
	private final IndexerRegistry _indexerRegistry;
	private final IndexSearcherHelper _indexSearcherHelper;
	private final PreFilterContributorHelper _preFilterContributorHelper;
	private final SearchEngineHelper _searchEngineHelper;

}