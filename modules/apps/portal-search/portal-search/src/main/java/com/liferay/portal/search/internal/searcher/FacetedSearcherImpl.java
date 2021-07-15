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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.internal.expando.ExpandoQueryContributorHelper;
import com.liferay.portal.search.internal.indexer.AddSearchKeywordsQueryContributorHelper;
import com.liferay.portal.search.internal.indexer.PostProcessSearchQueryContributorHelper;
import com.liferay.portal.search.internal.indexer.PreFilterContributorHelper;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class FacetedSearcherImpl
	extends BaseSearcher implements FacetedSearcher {

	public FacetedSearcherImpl(
		AddSearchKeywordsQueryContributorHelper
			addSearchKeywordsQueryContributorHelper,
		ExpandoQueryContributorHelper expandoQueryContributorHelper,
		IndexerRegistry indexerRegistry,
		IndexSearcherHelper indexSearcherHelper,
		PostProcessSearchQueryContributorHelper
			postProcessSearchQueryContributorHelper,
		PreFilterContributorHelper preFilterContributorHelper,
		SearchableAssetClassNamesProvider searchableAssetClassNamesProvider,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_addSearchKeywordsQueryContributorHelper =
			addSearchKeywordsQueryContributorHelper;
		_expandoQueryContributorHelper = expandoQueryContributorHelper;
		_indexerRegistry = indexerRegistry;
		_indexSearcherHelper = indexSearcherHelper;
		_postProcessSearchQueryContributorHelper =
			postProcessSearchQueryContributorHelper;
		_preFilterContributorHelper = preFilterContributorHelper;
		_searchableAssetClassNamesProvider = searchableAssetClassNamesProvider;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = new BooleanQueryImpl();

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			_getEntryClassNameIndexerMap(
				_getEntryClassNames(
					_getSearchRequest(searchContext),
					searchContext.getCompanyId()),
				searchContext.getSearchEngineId());

		_addSearchKeywords(
			searchQuery, entryClassNameIndexerMap.keySet(), searchContext);

		_postProcessSearchQuery(
			searchQuery, fullQueryBooleanFilter,
			entryClassNameIndexerMap.values(), searchContext);

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

			Query query = createFullQuery(booleanFilter, searchContext);

			query.setQueryConfig(searchContext.getQueryConfig());

			return _indexSearcherHelper.search(searchContext, query);
		}
		catch (Exception exception) {
			throw new SearchException(exception);
		}
	}

	@Override
	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		List<String> entryClassNames = _getEntryClassNames(
			_getSearchRequest(searchContext), searchContext.getCompanyId());

		if (ListUtil.isEmpty(entryClassNames)) {
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

	private void _addPreFilters(
		BooleanFilter booleanFilter,
		Map<String, Indexer<?>> entryClassNameIndexerMap,
		SearchContext searchContext) {

		_preFilterContributorHelper.contribute(
			booleanFilter, entryClassNameIndexerMap, searchContext);
	}

	private void _addSearchExpando(
		BooleanQuery booleanQuery, Collection<String> searchClassNames,
		SearchContext searchContext) {

		_expandoQueryContributorHelper.contribute(
			StringUtil.trim(searchContext.getKeywords()), booleanQuery,
			searchClassNames, searchContext);
	}

	private void _addSearchKeywords(
		BooleanQuery booleanQuery, Collection<String> searchClassNames,
		SearchContext searchContext) {

		_addSearchKeywordsQueryContributorHelper.contribute(
			booleanQuery, searchContext);

		_addSearchExpando(booleanQuery, searchClassNames, searchContext);
	}

	private Map<String, Indexer<?>> _getEntryClassNameIndexerMap(
		List<String> entryClassNames, String searchEngineId) {

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			new LinkedHashMap<>();

		for (String entryClassName : entryClassNames) {
			Indexer<?> indexer = _indexerRegistry.getIndexer(entryClassName);

			if ((indexer == null) ||
				!searchEngineId.equals(indexer.getSearchEngineId())) {

				continue;
			}

			entryClassNameIndexerMap.put(entryClassName, indexer);
		}

		return entryClassNameIndexerMap;
	}

	private List<String> _getEntryClassNames(
		SearchRequest searchRequest, long companyId) {

		List<String> entryClassNames = searchRequest.getEntryClassNames();

		if (!ListUtil.isEmpty(entryClassNames)) {
			return entryClassNames;
		}

		List<String> modelIndexerClassNames =
			searchRequest.getModelIndexerClassNames();

		if (!ListUtil.isEmpty(modelIndexerClassNames)) {
			return modelIndexerClassNames;
		}

		return ListUtil.fromArray(
			_searchableAssetClassNamesProvider.getClassNames(companyId));
	}

	private SearchRequest _getSearchRequest(SearchContext searchContext) {
		return _searchRequestBuilderFactory.builder(
			searchContext
		).build();
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

	private void _postProcessSearchQuery(
			BooleanQuery booleanQuery, BooleanFilter booleanFilter,
			Collection<Indexer<?>> indexers, SearchContext searchContext)
		throws Exception {

		_postProcessSearchQueryContributorHelper.contribute(
			booleanQuery, booleanFilter, indexers, searchContext);
	}

	private final AddSearchKeywordsQueryContributorHelper
		_addSearchKeywordsQueryContributorHelper;
	private final ExpandoQueryContributorHelper _expandoQueryContributorHelper;
	private final IndexerRegistry _indexerRegistry;
	private final IndexSearcherHelper _indexSearcherHelper;
	private final PostProcessSearchQueryContributorHelper
		_postProcessSearchQueryContributorHelper;
	private final PreFilterContributorHelper _preFilterContributorHelper;
	private final SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}