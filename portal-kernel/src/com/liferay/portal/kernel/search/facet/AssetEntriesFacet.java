/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author     Raymond Augé
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class AssetEntriesFacet extends MultiValueFacet {

	public AssetEntriesFacet(SearchContext searchContext) {
		super(searchContext);

		setFieldName(Field.ENTRY_CLASS_NAME);

		initFacetClause();
	}

	@Override
	public void setFacetConfiguration(FacetConfiguration facetConfiguration) {
		super.setFacetConfiguration(facetConfiguration);

		initFacetClause();
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		SearchContext searchContext = getSearchContext();

		String[] entryClassNames = searchContext.getEntryClassNames();

		BooleanFilter facetFilter = new BooleanFilter();

		for (String entryClassName : entryClassNames) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			try {
				BooleanFilter indexerBooleanFilter =
					indexer.getFacetBooleanFilter(
						entryClassName, searchContext);

				if ((indexerBooleanFilter == null) ||
					!indexerBooleanFilter.hasClauses()) {

					indexerBooleanFilter = _getFacetBooleanFilter(
						entryClassName, searchContext);
				}

				BooleanFilter entityBooleanFilter = new BooleanFilter();

				entityBooleanFilter.add(
					indexerBooleanFilter, BooleanClauseOccur.MUST);

				indexer.postProcessContextBooleanFilter(
					entityBooleanFilter, searchContext);

				for (IndexerPostProcessor indexerPostProcessor :
						indexer.getIndexerPostProcessors()) {

					indexerPostProcessor.postProcessContextBooleanFilter(
						entityBooleanFilter, searchContext);
				}

				postProcessContextQuery(
					entityBooleanFilter, searchContext, indexer);

				if (indexer.isStagingAware()) {
					if (!searchContext.isIncludeLiveGroups() &&
						searchContext.isIncludeStagingGroups()) {

						entityBooleanFilter.addRequiredTerm(
							Field.STAGING_GROUP, true);
					}
					else if (searchContext.isIncludeLiveGroups() &&
							 !searchContext.isIncludeStagingGroups()) {

						entityBooleanFilter.addRequiredTerm(
							Field.STAGING_GROUP, false);
					}
				}

				if (entityBooleanFilter.hasClauses()) {
					facetFilter.add(
						entityBooleanFilter, BooleanClauseOccur.SHOULD);
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		if (!facetFilter.hasClauses()) {
			return null;
		}

		return BooleanClauseFactoryUtil.createFilter(
			searchContext, facetFilter, BooleanClauseOccur.MUST);
	}

	protected void initFacetClause() {
		SearchContext searchContext = getSearchContext();

		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		String[] entryClassNames = null;

		if (dataJSONObject.has("values")) {
			JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

			entryClassNames = new String[valuesJSONArray.length()];

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				entryClassNames[i] = valuesJSONArray.getString(i);
			}
		}

		if (ArrayUtil.isEmpty(entryClassNames)) {
			entryClassNames = searchContext.getEntryClassNames();
		}

		if (!isStatic()) {
			String[] entryClassNameParam = StringUtil.split(
				GetterUtil.getString(
					searchContext.getAttribute(getFieldName())));

			if (ArrayUtil.isNotEmpty(entryClassNameParam)) {
				entryClassNames = entryClassNameParam;
			}
		}

		if (ArrayUtil.isEmpty(entryClassNames)) {
			entryClassNames = SearchEngineHelperUtil.getEntryClassNames();

			if (!dataJSONObject.has("values")) {
				JSONArray entriesJSONArray = JSONFactoryUtil.createJSONArray();

				for (String entryClassName : entryClassNames) {
					entriesJSONArray.put(entryClassName);
				}

				dataJSONObject.put("values", entriesJSONArray);
			}
		}

		searchContext.setEntryClassNames(entryClassNames);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), added strictly to support
	 *             backwards compatibility of {@link
	 *             Indexer#postProcessContextQuery(BooleanQuery, SearchContext)}
	 */
	@Deprecated
	protected void postProcessContextQuery(
			BooleanFilter entityFilter, SearchContext searchContext,
			Indexer<?> indexer)
		throws Exception {

		BooleanQuery entityBooleanQuery = new BooleanQueryImpl();

		indexer.postProcessContextQuery(entityBooleanQuery, searchContext);

		if (entityBooleanQuery.hasClauses()) {
			QueryFilter queryFilter = new QueryFilter(entityBooleanQuery);

			entityFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}
	}

	private BooleanFilter _getFacetBooleanFilter(
			String className, SearchContext searchContext)
		throws Exception {

		BooleanFilter facetBooleanFilter = new BooleanFilter();

		facetBooleanFilter.addTerm(Field.ENTRY_CLASS_NAME, className);

		if (searchContext.getUserId() > 0) {
			facetBooleanFilter =
				_searchPermissionChecker.getPermissionBooleanFilter(
					searchContext.getCompanyId(), searchContext.getGroupIds(),
					searchContext.getUserId(), className, facetBooleanFilter,
					searchContext);
		}

		return facetBooleanFilter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntriesFacet.class);

	private static volatile SearchPermissionChecker _searchPermissionChecker =
		ServiceProxyFactory.newServiceTrackedInstance(
			SearchPermissionChecker.class, AssetEntriesFacet.class,
			"_searchPermissionChecker", false);

}