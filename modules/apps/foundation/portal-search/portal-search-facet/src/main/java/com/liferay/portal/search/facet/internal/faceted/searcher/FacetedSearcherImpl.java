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

package com.liferay.portal.search.facet.internal.faceted.searcher;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class FacetedSearcherImpl
	extends BaseSearcher implements FacetedSearcher {

	public FacetedSearcherImpl(
		ExpandoBridgeFactory expandoBridgeFactory,
		GroupLocalService groupLocalService, IndexerRegistry indexerRegistry,
		IndexSearcherHelper indexSearcherHelper,
		Collection<SearchPermissionFilterContributor>
			searchPermissionFilterContributors) {

		_expandoBridgeFactory = expandoBridgeFactory;
		_groupLocalService = groupLocalService;
		_indexerRegistry = indexerRegistry;
		_indexSearcherHelper = indexSearcherHelper;
		_searchPermissionFilterContributors =
			searchPermissionFilterContributors;
	}

	protected void addSearchExpandoKeywords(
			BooleanQuery searchQuery, SearchContext searchContext,
			String keywords, String className)
		throws Exception {

		ExpandoBridge expandoBridge = _expandoBridgeFactory.getExpandoBridge(
			searchContext.getCompanyId(), className);

		Set<String> attributeNames = SetUtil.fromEnumeration(
			expandoBridge.getAttributeNames());

		for (String attributeName : attributeNames) {
			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				attributeName);

			int indexType = GetterUtil.getInteger(
				properties.getProperty(ExpandoColumnConstants.INDEX_TYPE));

			if (indexType != ExpandoColumnConstants.INDEX_TYPE_NONE) {
				String fieldName = getExpandoFieldName(
					searchContext, expandoBridge, attributeName);

				if (searchContext.isAndSearch()) {
					searchQuery.addRequiredTerm(fieldName, keywords);
				}
				else {
					searchQuery.addTerm(fieldName, keywords);
				}
			}
		}
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = new BooleanQueryImpl();

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute("luceneSyntax"));

		String keywords = searchContext.getKeywords();

		if (Validator.isNotNull(keywords)) {
			if (luceneSyntax) {
				searchQuery.add(
					new StringQuery(keywords), BooleanClauseOccur.MUST);
			}
			else {
				addSearchLocalizedTerm(
					searchQuery, searchContext, Field.ASSET_CATEGORY_TITLES,
					false);

				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords);

				searchQuery.addTerms(Field.KEYWORDS, keywords);
			}

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

		for (String entryClassName : searchContext.getEntryClassNames()) {
			visitEntryClassName(
				entryClassName, searchContext, searchQuery,
				fullQueryBooleanFilter, luceneSyntax, keywords);
		}

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

		for (String entryClassName : searchContext.getEntryClassNames()) {
			Indexer<?> indexer = _indexerRegistry.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			String searchEngineId = searchContext.getSearchEngineId();

			if (!searchEngineId.equals(indexer.getSearchEngineId())) {
				continue;
			}

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessFullQuery(
					fullQuery, searchContext);
			}
		}

		return fullQuery;
	}

	protected BooleanFilter createPermissionBooleanFilter(
		String entryClassName, SearchContext searchContext) {

		BooleanFilter permissionBooleanFilter = new BooleanFilter();

		permissionBooleanFilter.addTerm(Field.ENTRY_CLASS_NAME, entryClassName);

		if (searchContext.getUserId() > 0) {
			SearchPermissionChecker searchPermissionChecker =
				SearchEngineHelperUtil.getSearchPermissionChecker();

			Optional<String> parentEntryClassNameOptional =
				getParentEntryClassName(entryClassName);

			String permissionedEntryClassName =
				parentEntryClassNameOptional.orElse(entryClassName);

			permissionBooleanFilter =
				searchPermissionChecker.getPermissionBooleanFilter(
					searchContext.getCompanyId(), searchContext.getGroupIds(),
					searchContext.getUserId(), permissionedEntryClassName,
					permissionBooleanFilter, searchContext);
		}

		return permissionBooleanFilter;
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

	protected Optional<String> getParentEntryClassName(String entryClassName) {
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

	@Override
	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		if (searchContext.getEntryClassNames() == null) {
			return super.isFilterSearch();
		}

		for (String entryClassName : searchContext.getEntryClassNames()) {
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

	protected void visitEntryClassName(
			String entryClassName, SearchContext searchContext,
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			boolean luceneSyntax, String keywords)
		throws Exception {

		Indexer<?> indexer = _indexerRegistry.getIndexer(entryClassName);

		if (indexer == null) {
			return;
		}

		String searchEngineId = searchContext.getSearchEngineId();

		if (!searchEngineId.equals(indexer.getSearchEngineId())) {
			return;
		}

		fullQueryBooleanFilter.add(
			createPermissionBooleanFilter(entryClassName, searchContext));

		if (!luceneSyntax) {
			if (Validator.isNotNull(keywords)) {
				addSearchExpandoKeywords(
					searchQuery, searchContext, keywords, entryClassName);
			}

			indexer.postProcessSearchQuery(
				searchQuery, fullQueryBooleanFilter, searchContext);
		}

		for (IndexerPostProcessor indexerPostProcessor :
				indexer.getIndexerPostProcessors()) {

			indexerPostProcessor.postProcessSearchQuery(
				searchQuery, fullQueryBooleanFilter, searchContext);
		}
	}

	private final ExpandoBridgeFactory _expandoBridgeFactory;
	private final GroupLocalService _groupLocalService;
	private final IndexerRegistry _indexerRegistry;
	private final IndexSearcherHelper _indexSearcherHelper;
	private final Collection<SearchPermissionFilterContributor>
		_searchPermissionFilterContributors;

}