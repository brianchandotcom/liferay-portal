/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

/**
 * @author Raymond Augé
 */
public class FacetedSearcher extends BaseIndexer {

	public static Indexer getInstance() {
		return new FacetedSearcher();
	}

	public String[] getClassNames() {
		return null;
	}

	public String getPortletId() {
		return null;
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		try {
			searchContext.setSearchEngineId(getSearchEngineId());

			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			contextQuery.addRequiredTerm(
				Field.COMPANY_ID, searchContext.getCompanyId());

			BooleanQuery fullQuery = createFullQuery(
				contextQuery, searchContext);

			fullQuery.setQueryConfig(searchContext.getQueryConfig());

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			int start = searchContext.getStart();
			int end = searchContext.getEnd();

			if (isFilterSearch(searchContext) && (permissionChecker != null)) {
				searchContext.setStart(0);
				searchContext.setEnd(end + INDEX_FILTER_SEARCH_LIMIT);
			}

			Hits hits = SearchEngineUtil.search(searchContext, fullQuery);

			searchContext.setStart(start);
			searchContext.setEnd(end);

			if (isFilterSearch(searchContext) && (permissionChecker != null)) {
				hits = filterSearch(hits, permissionChecker, searchContext);
			}

			return hits;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		throw new UnsupportedOperationException();
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
					searchQuery.addRequiredTerm(fieldName, keywords, true);
				}
				else {
					searchQuery.addTerm(fieldName, keywords, true);
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
			searchQuery.addExactTerm(Field.ASSET_CATEGORY_NAMES, keywords);
			searchQuery.addExactTerm(Field.ASSET_TAG_NAMES, keywords);
			searchQuery.addTerms(Field.KEYWORDS, keywords, true);
		}

		for (String entryClassName : searchContext.getEntryClassNames()) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
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

		for (Facet facet : facets.values()) {
			BooleanClause facetClause = facet.getFacetClause();

			if (facetClause != null) {
				contextQuery.add(
					facetClause.getQuery(),
					facetClause.getBooleanClauseOccur());
			}
		}

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(searchContext);

		fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

		if (!searchQuery.clauses().isEmpty()) {
			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		BooleanClause[] booleanClauses = searchContext.getBooleanClauses();

		if (booleanClauses != null) {
			for (BooleanClause booleanClause : booleanClauses) {
				fullQuery.add(
					booleanClause.getQuery(),
					booleanClause.getBooleanClauseOccur());
			}
		}

		for (String entryClassName : searchContext.getEntryClassNames()) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
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
	protected void doDelete(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return null;
	}

	protected boolean isFilterSearch(SearchContext searchContext) {
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