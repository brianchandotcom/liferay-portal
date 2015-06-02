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

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class FacetedSearcher extends BaseSearcher {

	public static Indexer getInstance() {
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
				String fieldName = ExpandoBridgeIndexerUtil.encodeFieldName(
					attributeName);

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
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		String keywords = searchContext.getKeywords();

		if (Validator.isNotNull(keywords)) {
			addSearchLocalizedTerm(
				searchQuery, searchContext, Field.ASSET_CATEGORY_TITLES, false);

			searchQuery.addExactTerm(Field.ASSET_TAG_NAMES, keywords);

			int groupId = GetterUtil.getInteger(
				searchContext.getAttribute(Field.GROUP_ID));

			if (groupId == 0) {
				searchQuery.addTerm(
					Field.STAGING_GROUP, "true", false,
					BooleanClauseOccur.MUST_NOT);
			}

			searchQuery.addTerms(Field.KEYWORDS, keywords);
		}

		for (String entryClassName : searchContext.getEntryClassNames()) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

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

			indexer.postProcessSearchQuery(searchQuery, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessSearchQuery(
					searchQuery, searchContext);
			}
		}

		Map<String, Facet> facets = searchContext.getFacets();

		BooleanFilter facetFilter = new BooleanFilter();

		for (Facet facet : facets.values()) {
			BooleanClause<Filter> facetClause = facet.getFacetFilterClause();

			if (facetClause != null) {
				facetFilter.add(
					facetClause.getClause(),
					facetClause.getBooleanClauseOccur());
			}
		}

		doAddFacetClause(searchContext, facetFilter, facets.values());

		BooleanFilter fullQueryFilter = new BooleanFilter();

		fullQueryFilter.add(facetFilter, BooleanClauseOccur.MUST);

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(searchContext);

		if (contextQuery.hasClauses()) {
			QueryFilter queryFilter = new QueryFilter(contextQuery);

			fullQueryFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}

		fullQuery.setPreBooleanFilter(fullQueryFilter);

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
			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

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

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			contextQuery.addRequiredTerm(
				Field.COMPANY_ID, searchContext.getCompanyId());

			BooleanQuery fullQuery = createFullQuery(
				contextQuery, searchContext);

			QueryConfig queryConfig = searchContext.getQueryConfig();

			fullQuery.setQueryConfig(queryConfig);

			return SearchEngineUtil.search(searchContext, fullQuery);
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
			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

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