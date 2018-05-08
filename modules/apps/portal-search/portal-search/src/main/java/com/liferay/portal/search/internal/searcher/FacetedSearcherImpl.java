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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.internal.expando.ExpandoQueryContributorHelper;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * @author Raymond Augé
 */
public class FacetedSearcherImpl
	extends BaseSearcher implements FacetedSearcher {

	public FacetedSearcherImpl(
		ExpandoQueryContributorHelper expandoQueryContributorHelper,
		GroupLocalService groupLocalService, IndexerRegistry indexerRegistry,
		IndexSearcherHelper indexSearcherHelper,
		SearchEngineHelper searchEngineHelper,
		Collection<SearchPermissionFilterContributor>
			searchPermissionFilterContributors) {

		_expandoQueryContributorHelper = expandoQueryContributorHelper;
		_groupLocalService = groupLocalService;
		_indexerRegistry = indexerRegistry;
		_indexSearcherHelper = indexSearcherHelper;
		_searchEngineHelper = searchEngineHelper;
		_searchPermissionFilterContributors =
			searchPermissionFilterContributors;
	}

	protected void addSearchExpando(
			BooleanQuery booleanQuery, SearchContext searchContext,
			String keywords, Collection<String> classNames)
		throws Exception {

		_expandoQueryContributorHelper.setAndSearch(
			searchContext.isAndSearch());
		_expandoQueryContributorHelper.setBooleanQuery(booleanQuery);
		_expandoQueryContributorHelper.setClassNamesStream(classNames.stream());
		_expandoQueryContributorHelper.setCompanyId(
			searchContext.getCompanyId());
		_expandoQueryContributorHelper.setKeywords(keywords);
		_expandoQueryContributorHelper.setLocale(searchContext.getLocale());

		_expandoQueryContributorHelper.contribute();
	}

	protected void addSearchKeywords(
			BooleanQuery searchQuery, String keywords, boolean luceneSyntax,
			Map<String, Indexer<?>> entryClassNameIndexerMap,
			SearchContext searchContext)
		throws Exception {

		if (Validator.isNull(keywords)) {
			return;
		}

		if (luceneSyntax) {
			searchQuery.add(new StringQuery(keywords), BooleanClauseOccur.MUST);
		}
		else {
			addSearchLocalizedTerm(
				searchQuery, searchContext, Field.ASSET_CATEGORY_TITLES, false);

			searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords);

			searchQuery.addTerms(Field.KEYWORDS, keywords);

			addSearchExpando(
				searchQuery, searchContext, keywords,
				entryClassNameIndexerMap.keySet());
		}
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = new BooleanQueryImpl();

		String keywords = searchContext.getKeywords();

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute("luceneSyntax"));

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			_getEntryClassNameIndexerMap(
				_getEntryClassNames(searchContext),
				searchContext.getSearchEngineId());

		addSearchKeywords(
			searchQuery, keywords, luceneSyntax, entryClassNameIndexerMap,
			searchContext);

		_addSearchTerms(
			searchQuery, fullQueryBooleanFilter, luceneSyntax,
			entryClassNameIndexerMap, searchContext);

		if (Validator.isNotNull(keywords)) {
			int groupId = GetterUtil.getInteger(
				searchContext.getAttribute(Field.GROUP_ID));

			if (groupId == 0) {
				fullQueryBooleanFilter.addTerm(
					Field.STAGING_GROUP, "true", BooleanClauseOccur.MUST_NOT);
			}
		}

		List<Group> inactiveGroups = _groupLocalService.getActiveGroups(
			searchContext.getCompanyId(), false);

		if (ListUtil.isNotEmpty(inactiveGroups)) {
			TermsFilter groupIdTermsFilter = new TermsFilter(Field.GROUP_ID);

			groupIdTermsFilter.addValues(
				ArrayUtil.toStringArray(
					ListUtil.toArray(inactiveGroups, Group.GROUP_ID_ACCESSOR)));

			fullQueryBooleanFilter.add(
				groupIdTermsFilter, BooleanClauseOccur.MUST_NOT);
		}

		_addPreFilters(
			fullQueryBooleanFilter, entryClassNameIndexerMap, searchContext);

		Map<String, Facet> facets = searchContext.getFacets();

		BooleanFilter facetBooleanFilter = new BooleanFilter();

		for (Facet facet : facets.values()) {
			BooleanClause<Filter> facetClause =
				facet.getFacetFilterBooleanClause();

			if (facetClause != null) {
				facetBooleanFilter.add(
					facetClause.getClause(),
					facetClause.getBooleanClauseOccur());
			}
		}

		addFacetClause(searchContext, facetBooleanFilter, facets.values());

		BooleanQuery fullQuery = new BooleanQueryImpl();

		if (facetBooleanFilter.hasClauses()) {
			fullQuery.setPostFilter(facetBooleanFilter);
		}

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

		try {
			searchContext.setSearchEngineId(getSearchEngineId());

			BooleanFilter queryBooleanFilter = new BooleanFilter();

			queryBooleanFilter.addRequiredTerm(
				Field.COMPANY_ID, searchContext.getCompanyId());

			Query fullQuery = createFullQuery(
				queryBooleanFilter, searchContext);

			if (!fullQuery.hasChildren()) {
				BooleanFilter preBooleanFilter =
					fullQuery.getPreBooleanFilter();

				fullQuery = new MatchAllQuery();

				fullQuery.setPreBooleanFilter(preBooleanFilter);
			}

			QueryConfig queryConfig = searchContext.getQueryConfig();

			fullQuery.setQueryConfig(queryConfig);

			return _indexSearcherHelper.search(searchContext, fullQuery);
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

	private void _addIndexerProvidedPreFilters(
			BooleanFilter booleanFilter, Indexer<?> indexer,
			SearchContext searchContext)
		throws Exception {

		indexer.postProcessContextBooleanFilter(booleanFilter, searchContext);

		for (IndexerPostProcessor indexerPostProcessor :
				indexer.getIndexerPostProcessors()) {

			indexerPostProcessor.postProcessContextBooleanFilter(
				booleanFilter, searchContext);
		}
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

	private void _addPermissionFilter(
			BooleanFilter booleanFilter, String entryClassName,
			SearchContext searchContext)
		throws Exception {

		if (searchContext.getUserId() == 0) {
			return;
		}

		SearchPermissionChecker searchPermissionChecker =
			SearchEngineHelperUtil.getSearchPermissionChecker();

		Optional<String> parentEntryClassNameOptional =
			_getParentEntryClassName(entryClassName);

		String permissionedEntryClassName = parentEntryClassNameOptional.orElse(
			entryClassName);

		searchPermissionChecker.getPermissionBooleanFilter(
			searchContext.getCompanyId(), searchContext.getGroupIds(),
			searchContext.getUserId(), permissionedEntryClassName,
			booleanFilter, searchContext);
	}

	private void _addPreFilters(
			BooleanFilter queryBooleanFilter,
			Map<String, Indexer<?>> entryClassNameIndexerMap,
			SearchContext searchContext)
		throws Exception {

		BooleanFilter preFilterBooleanFilter = new BooleanFilter();

		for (Entry<String, Indexer<?>> entry :
				entryClassNameIndexerMap.entrySet()) {

			String entryClassName = entry.getKey();
			Indexer<?> indexer = entry.getValue();

			preFilterBooleanFilter.add(
				_createPreFilterForEntryClassName(
					entryClassName, indexer, searchContext),
				BooleanClauseOccur.SHOULD);
		}

		if (preFilterBooleanFilter.hasClauses()) {
			queryBooleanFilter.add(
				preFilterBooleanFilter, BooleanClauseOccur.MUST);
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

	private void _addStagingFilter(
		BooleanFilter booleanFilter, Indexer<?> indexer,
		SearchContext searchContext) {

		if (!indexer.isStagingAware()) {
			return;
		}

		if (!searchContext.isIncludeLiveGroups() &&
			searchContext.isIncludeStagingGroups()) {

			booleanFilter.addRequiredTerm(Field.STAGING_GROUP, true);
		}
		else if (searchContext.isIncludeLiveGroups() &&
				 !searchContext.isIncludeStagingGroups()) {

			booleanFilter.addRequiredTerm(Field.STAGING_GROUP, false);
		}
	}

	private Filter _createPreFilterForEntryClassName(
			String entryClassName, Indexer<?> indexer,
			SearchContext searchContext)
		throws Exception {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.addTerm(
			Field.ENTRY_CLASS_NAME, entryClassName, BooleanClauseOccur.MUST);

		_addStagingFilter(booleanFilter, indexer, searchContext);

		_addPermissionFilter(booleanFilter, entryClassName, searchContext);

		_addIndexerProvidedPreFilters(booleanFilter, indexer, searchContext);

		return booleanFilter;
	}

	private Map<String, Indexer<?>> _getEntryClassNameIndexerMap(
		String[] entryClassNames, String searchEngineId) {

		Map<String, Indexer<?>> entryClassNameIndexerMap = new LinkedHashMap<>(
			entryClassNames.length);

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

	private Optional<String> _getParentEntryClassName(String entryClassName) {
		for (SearchPermissionFilterContributor
				searchPermissionFilterContributor :
					_searchPermissionFilterContributors) {

			Optional<String> parentEntryClassNameOptional =
				searchPermissionFilterContributor.
					getParentEntryClassNameOptional(entryClassName);

			if ((parentEntryClassNameOptional != null) &&
				parentEntryClassNameOptional.isPresent()) {

				return parentEntryClassNameOptional;
			}
		}

		return Optional.empty();
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
		};
	}

	private final ExpandoQueryContributorHelper _expandoQueryContributorHelper;
	private final GroupLocalService _groupLocalService;
	private final IndexerRegistry _indexerRegistry;
	private final IndexSearcherHelper _indexSearcherHelper;
	private final SearchEngineHelper _searchEngineHelper;
	private final Collection<SearchPermissionFilterContributor>
		_searchPermissionFilterContributors;

}