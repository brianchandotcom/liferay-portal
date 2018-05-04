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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.indexer.IndexerQueryBuilder;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.SearchContextContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;
import com.liferay.portal.search.spi.model.query.contributor.helper.SearchContextContributorHelper;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 */
public class IndexerQueryBuilderImpl<T extends BaseModel<?>>
	implements IndexerQueryBuilder {

	public IndexerQueryBuilderImpl(
		IndexerRegistry indexerRegistry,
		ModelSearchSettings modelSearchSettings,
		Iterable<KeywordQueryContributor> modelKeywordQueryContributors,
		ModelPreFilterContributorsHolder modelPreFilterContributorsHolder,
		Iterable<SearchContextContributor> modelSearchContextContributor,
		Iterable<KeywordQueryContributor> keywordQueryContributors,
		Iterable<QueryPreFilterContributor> queryPreFilterContributors,
		Iterable<SearchContextContributor> searchContextContributors,
		Iterable<SearchPermissionFilterContributor>
			searchPermissionFilterContributors,
		IndexerPostProcessorsHolder indexerPostProcessorsHolder,
		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry) {

		_indexerRegistry = indexerRegistry;
		_modelSearchSettings = modelSearchSettings;
		_modelKeywordQueryContributors = modelKeywordQueryContributors;
		_modelPreFilterContributorsHolder = modelPreFilterContributorsHolder;
		_modelSearchContextContributors = modelSearchContextContributor;
		_keywordQueryContributors = keywordQueryContributors;
		_queryPreFilterContributors = queryPreFilterContributors;
		_searchContextContributors = searchContextContributors;
		_searchPermissionFilterContributors =
			searchPermissionFilterContributors;
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

		contribute(_queryPreFilterContributors, booleanFilter, searchContext);

		_addPreFilters(booleanFilter, entryClassNameIndexerMap, searchContext);

		BooleanQuery fullQuery = createFullQuery(booleanFilter, searchContext);

		fullQuery.setQueryConfig(searchContext.getQueryConfig());

		return fullQuery;
	}

	protected void addPreFiltersFromModel(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		Stream<ModelPreFilterContributor> stream =
			_modelPreFilterContributorsHolder.getByModel(
				modelSearchSettings.getClassName());

		stream.forEach(
			modelPreFilterContributor -> modelPreFilterContributor.contribute(
				booleanFilter, modelSearchSettings, searchContext));
	}

	protected void addSearchTermsFromModel(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		contribute(_modelKeywordQueryContributors, searchContext, booleanQuery);
	}

	protected void contribute(
		Iterable<KeywordQueryContributor> keywordQueryContributors,
		SearchContext searchContext, BooleanQuery booleanQuery) {

		for (KeywordQueryContributor keywordQueryContributor :
				keywordQueryContributors) {

			keywordQueryContributor.contribute(
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

				});
		}
	}

	protected void contribute(
		Iterable<QueryPreFilterContributor> queryPreFilterContributors,
		BooleanFilter booleanFilter, SearchContext searchContext) {

		for (QueryPreFilterContributor queryPreFilterContributor :
				queryPreFilterContributors) {

			queryPreFilterContributor.contribute(booleanFilter, searchContext);
		}
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
			searchContextContributor -> {
				searchContextContributor.contribute(
					searchContext, searchContextContributorHelper);
			});

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

		Stream<IndexerPostProcessor> stream =
			_indexerPostProcessorsHolder.stream();

		stream.forEach(
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

	private void _addPermissionFilter(
		BooleanFilter booleanFilter, String entryClassName,
		SearchContext searchContext) {

		if (searchContext.getUserId() == 0) {
			return;
		}

		SearchPermissionChecker searchPermissionChecker =
			SearchEngineHelperUtil.getSearchPermissionChecker();

		Optional<String> permissionedEntryClassNameOptional =
			_getPermissionedEntryClassName(entryClassName);

		String permissionedEntryClassName =
			permissionedEntryClassNameOptional.orElse(entryClassName);

		searchPermissionChecker.getPermissionBooleanFilter(
			searchContext.getCompanyId(), searchContext.getGroupIds(),
			searchContext.getUserId(), permissionedEntryClassName,
			booleanFilter, searchContext);
	}

	private void _addPreFilters(
		BooleanFilter queryBooleanFilter,
		Map<String, Indexer<?>> entryClassNameIndexerMap,
		SearchContext searchContext) {

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

	private void _addSearchKeywords(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		contribute(_keywordQueryContributors, searchContext, booleanQuery);
	}

	private void _addSearchTermsFromIndexerPostProcessors(
		BooleanQuery booleanQuery, BooleanFilter booleanFilter,
		SearchContext searchContext) {

		Stream<IndexerPostProcessor> stream =
			_indexerPostProcessorsHolder.stream();

		stream.forEach(
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

	private BooleanFilter _createPreFilterForEntryClassName(
		String entryClassName, Indexer indexer, SearchContext searchContext) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.addTerm(
			Field.ENTRY_CLASS_NAME, entryClassName, BooleanClauseOccur.MUST);

		_addPermissionFilter(booleanFilter, entryClassName, searchContext);

		try {
			_addIndexerProvidedPreFilters(
				booleanFilter, indexer, searchContext);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		ModelSearchSettings modelSearchSettings = _getModelSearchSettings(
			indexer);

		addPreFiltersFromModel(
			booleanFilter, modelSearchSettings, searchContext);

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

	private ModelSearchSettings _getModelSearchSettings(Indexer indexer) {
		ModelSearchSettingsImpl modelSearchSettingsImpl =
			new ModelSearchSettingsImpl(indexer.getClassName());

		modelSearchSettingsImpl.setStagingAware(indexer.isStagingAware());

		return modelSearchSettingsImpl;
	}

	private Optional<String> _getPermissionedEntryClassName(
		String entryClassName) {

		for (SearchPermissionFilterContributor
				searchPermissionFilterContributor :
					_searchPermissionFilterContributors) {

			Optional<String> permissionedEntryClassNameOptional =
				searchPermissionFilterContributor.
					getParentEntryClassNameOptional(entryClassName);

			if ((permissionedEntryClassNameOptional != null) &&
				permissionedEntryClassNameOptional.isPresent()) {

				return permissionedEntryClassNameOptional;
			}
		}

		return Optional.empty();
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
	private final Iterable<KeywordQueryContributor> _keywordQueryContributors;
	private final Iterable<KeywordQueryContributor>
		_modelKeywordQueryContributors;
	private final ModelPreFilterContributorsHolder
		_modelPreFilterContributorsHolder;
	private final Iterable<SearchContextContributor>
		_modelSearchContextContributors;
	private final ModelSearchSettings _modelSearchSettings;
	private final Iterable<QueryPreFilterContributor>
		_queryPreFilterContributors;
	private final RelatedEntryIndexerRegistry _relatedEntryIndexerRegistry;
	private final Iterable<SearchContextContributor> _searchContextContributors;
	private final Iterable<SearchPermissionFilterContributor>
		_searchPermissionFilterContributors;

}