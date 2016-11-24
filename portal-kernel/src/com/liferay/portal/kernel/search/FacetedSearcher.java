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

package com.liferay.portal.kernel.search;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author     Raymond Augé
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.portal.kernel.search.facet.searcher.FacetedSearcher}
 */
@Deprecated
public class FacetedSearcher extends BaseSearcher {

	public static Indexer<?> getInstance() {
		return new FacetedSearcher();
	}

	protected void addSearchExpandoKeywords(
			BooleanQuery searchQuery, SearchContext searchContext,
			String keywords, String className)
		throws Exception {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
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

		String keywords = searchContext.getKeywords();

		if (Validator.isNotNull(keywords)) {
			addSearchLocalizedTerm(
				searchQuery, searchContext, Field.ASSET_CATEGORY_TITLES, false);

			searchQuery.addExactTerm(Field.ASSET_TAG_NAMES, keywords);

			int groupId = GetterUtil.getInteger(
				searchContext.getAttribute(Field.GROUP_ID));

			if (groupId == 0) {
				fullQueryBooleanFilter.addTerm(
					Field.STAGING_GROUP, "true", BooleanClauseOccur.MUST_NOT);
			}

			searchQuery.addTerms(Field.KEYWORDS, keywords);
		}

		List<Group> inactiveGroups = GroupLocalServiceUtil.getActiveGroups(
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
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			String searchEngineId = searchContext.getSearchEngineId();

			if (!searchEngineId.equals(indexer.getSearchEngineId())) {
				continue;
			}

			if (Validator.isNotNull(keywords)) {
				addSearchExpandoKeywords(
					searchQuery, searchContext, keywords, entryClassName);
			}

			indexer.postProcessSearchQuery(
				searchQuery, fullQueryBooleanFilter, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessSearchQuery(
					searchQuery, fullQueryBooleanFilter, searchContext);
			}

			doPostProcessSearchQuery(indexer, searchQuery, searchContext);
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

		if (facetBooleanFilter.hasClauses()) {
			fullQueryBooleanFilter.add(
				facetBooleanFilter, BooleanClauseOccur.MUST);
		}

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

		for (String entryClassName : searchContext.getEntryClassNames()) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

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

			return IndexSearcherHelperUtil.search(searchContext, fullQuery);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		if (searchContext.getEntryClassNames() == null) {
			return super.isFilterSearch();
		}

		for (String entryClassName : searchContext.getEntryClassNames()) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			if (indexer.isFilterSearch()) {
				return true;
			}
		}

		return super.isFilterSearch();
	}

}