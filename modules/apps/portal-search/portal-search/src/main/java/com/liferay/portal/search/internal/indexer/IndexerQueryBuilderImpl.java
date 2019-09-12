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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.indexer.IndexerQueryBuilder;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;
import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 */
public class IndexerQueryBuilderImpl<T extends BaseModel<?>>
	implements IndexerQueryBuilder {

	public IndexerQueryBuilderImpl(
		IndexerRegistry indexerRegistry,
		ModelSearchSettings modelSearchSettings,
		ModelKeywordQueryContributorsHolder modelKeywordQueryContributorsHolder,
		Iterable<SearchContextContributor> modelSearchContextContributor,
		KeywordQueryContributorsHolder keywordQueryContributorsHolder,
		PreFilterContributorHelper preFilterContributorHelper,
		Iterable<SearchContextContributor> searchContextContributors,
		IndexerPostProcessorsHolder indexerPostProcessorsHolder,
		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry) {

		_indexerRegistry = indexerRegistry;
		_modelSearchSettings = modelSearchSettings;
		_modelKeywordQueryContributorsHolder =
			modelKeywordQueryContributorsHolder;
		_modelSearchContextContributors = modelSearchContextContributor;
		_keywordQueryContributorsHolder = keywordQueryContributorsHolder;
		_preFilterContributorHelper = preFilterContributorHelper;
		_searchContextContributors = searchContextContributors;
		_indexerPostProcessorsHolder = indexerPostProcessorsHolder;
		_relatedEntryIndexerRegistry = relatedEntryIndexerRegistry;
	}

	@Override
	public BooleanQuery getQuery(SearchContext searchContext) {
		searchContext.setSearchEngineId(
			_modelSearchSettings.getSearchEngineId());

		_resetFullQuery(searchContext);

		String[] fullQueryEntryClassNames =
			searchContext.getFullQueryEntryClassNames();

		if (ArrayUtil.isNotEmpty(fullQueryEntryClassNames)) {
			searchContext.setAttribute(
				"relatedEntryClassNames",
				_modelSearchSettings.getSearchClassNames());
		}

		String[] entryClassNames = ArrayUtil.append(
			_modelSearchSettings.getSearchClassNames(),
			fullQueryEntryClassNames);

		searchContext.setEntryClassNames(entryClassNames);

		contributeSearchContext(searchContext);

		Map<String, Indexer<?>> entryClassNameIndexerMap =
			_getEntryClassNameIndexerMap(
				entryClassNames, searchContext.getSearchEngineId());

		BooleanFilter booleanFilter = new BooleanFilter();

		_addPreFilters(booleanFilter, entryClassNameIndexerMap, searchContext);

		BooleanQuery fullQuery = createFullQuery(booleanFilter, searchContext);

		fullQuery.setQueryConfig(searchContext.getQueryConfig());

		return fullQuery;
	}

	protected void addPreFiltersFromModel(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_preFilterContributorHelper.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	protected void addSearchTermsFromModel(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		contribute(
			_modelKeywordQueryContributorsHolder.getAll(), booleanQuery,
			searchContext);
	}

	protected void contribute(
		Stream<KeywordQueryContributor> stream, BooleanQuery booleanQuery,
		SearchContext searchContext) {

		stream.forEach(
			keywordQueryContributor -> keywordQueryContributor.contribute(
				searchContext.getKeywords(), booleanQuery,
				new KeywordQueryContributorHelper() {

					@Override
					public String getClassName() {
						return _modelSearchSettings.getClassName();
					}

					@Override
					public Stream<String> getSearchClassNamesStream() {
						return Stream.of(
							_modelSearchSettings.getSearchClassNames());
					}

					@Override
					public SearchContext getSearchContext() {
						return searchContext;
					}

				}));
	}

	protected void contributeSearchContext(SearchContext searchContext) {
		SearchContextContributorHelper searchContextContributorHelper =
			new SearchContextContributorHelper() {

				@Override
				public String[] getSearchClassNames() {
					return _modelSearchSettings.getSearchClassNames();
				}

			};

		_searchContextContributors.forEach(
			searchContextContributor -> searchContextContributor.contribute(
				searchContext, searchContextContributorHelper));

		_modelSearchContextContributors.forEach(
			modelSearchContextContributor ->
				modelSearchContextContributor.contribute(
					searchContext, searchContextContributorHelper));
	}

	protected BooleanQuery createFullQuery(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		if (fullQueryBooleanFilter.hasClauses()) {
			booleanQuery.setPreBooleanFilter(fullQueryBooleanFilter);
		}

		BooleanQuery keywordBooleanQuery = createKeywordQuery(
			fullQueryBooleanFilter, searchContext);

		if (keywordBooleanQuery.hasClauses()) {
			_add(booleanQuery, keywordBooleanQuery, BooleanClauseOccur.MUST);
		}

		BooleanClause<Query>[] booleanClauses =
			searchContext.getBooleanClauses();

		if (booleanClauses != null) {
			for (BooleanClause<Query> booleanClause : booleanClauses) {
				_add(
					booleanQuery, booleanClause.getClause(),
					booleanClause.getBooleanClauseOccur());
			}
		}

		postProcessFullQuery(booleanQuery, searchContext);

		return booleanQuery;
	}

	protected BooleanQuery createKeywordQuery(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		_addSearchKeywords(booleanQuery, searchContext);

		addSearchTermsFromModel(booleanQuery, searchContext);

		_addSearchTermsFromIndexerPostProcessors(
			booleanQuery, fullQueryBooleanFilter, searchContext);

		return booleanQuery;
	}

	protected void postProcessFullQuery(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		_indexerPostProcessorsHolder.forEach(
			indexerPostProcessor -> {
				try {
					indexerPostProcessor.postProcessFullQuery(
						booleanQuery, searchContext);
				}
				catch (RuntimeException re) {
					throw re;
				}
				catch (Exception e) {
					throw new SystemException(e);
				}
			});
	}

	private void _add(
		BooleanQuery booleanQuery, Query query,
		BooleanClauseOccur booleanClauseOccur) {

		try {
			booleanQuery.add(query, booleanClauseOccur);
		}
		catch (ParseException pe) {
			throw new SystemException(pe);
		}
	}

	private void _addPreFilters(
		BooleanFilter queryBooleanFilter,
		Map<String, Indexer<?>> entryClassNameIndexerMap,
		SearchContext searchContext) {

		_preFilterContributorHelper.contribute(
			queryBooleanFilter, entryClassNameIndexerMap, searchContext);
	}

	private void _addSearchKeywords(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		contribute(
			_keywordQueryContributorsHolder.getAll(), booleanQuery,
			searchContext);
	}

	private void _addSearchTermsFromIndexerPostProcessors(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		SearchContext searchContext) {

		_indexerPostProcessorsHolder.forEach(
			indexerPostProcessor -> {
				try {
					indexerPostProcessor.postProcessSearchQuery(
						booleanQuery, booleanFilter, searchContext);
				}
				catch (RuntimeException re) {
					throw re;
				}
				catch (Exception e) {
					throw new SystemException(e);
				}
			});
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

	private void _resetFullQuery(SearchContext searchContext) {
		searchContext.clearFullQueryEntryClassNames();

		for (RelatedEntryIndexer relatedEntryIndexer :
				_relatedEntryIndexerRegistry.getRelatedEntryIndexers()) {

			relatedEntryIndexer.updateFullQuery(searchContext);
		}
	}

	private final IndexerPostProcessorsHolder _indexerPostProcessorsHolder;
	private final IndexerRegistry _indexerRegistry;
	private final KeywordQueryContributorsHolder
		_keywordQueryContributorsHolder;
	private final ModelKeywordQueryContributorsHolder
		_modelKeywordQueryContributorsHolder;
	private final Iterable<SearchContextContributor>
		_modelSearchContextContributors;
	private final ModelSearchSettings _modelSearchSettings;
	private final PreFilterContributorHelper _preFilterContributorHelper;
	private final RelatedEntryIndexerRegistry _relatedEntryIndexerRegistry;
	private final Iterable<SearchContextContributor> _searchContextContributors;

}