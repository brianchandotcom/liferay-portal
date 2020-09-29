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

package com.liferay.segments.internal.odata.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.segments.odata.search.ODataSearchAdapter;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = ODataSearchAdapter.class)
public class ODataSearchAdapterImpl implements ODataSearchAdapter {

	@Override
	public Hits search(
			long companyId, FilterParser filterParser, String filterString,
			String className, EntityModel entityModel, Locale locale, int start,
			int end)
		throws PortalException {

		try {
			SearchContext searchContext = _createSearchContext(
				companyId, start, end);

			Indexer<?> indexer = _indexerRegistry.getIndexer(className);

			searchContext.setBooleanClauses(
				new BooleanClause[] {
					_getBooleanClause(
						filterString, entityModel, filterParser, locale)
				});

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to search with filter " + filterString, exception);
		}
	}

	@Override
	public int searchCount(
			long companyId, FilterParser filterParser, String filterString,
			String className, EntityModel entityModel, Locale locale)
		throws PortalException {

		try {
			SearchContext searchContext = _createSearchContext(companyId, 0, 0);

			Indexer<?> indexer = _indexerRegistry.getIndexer(className);

			searchContext.setBooleanClauses(
				new BooleanClause[] {
					_getBooleanClause(
						filterString, entityModel, filterParser, locale)
				});

			return (int)indexer.searchCount(searchContext);
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to search with filter " + filterString, exception);
		}
	}

	private SearchContext _createSearchContext(
		long companyId, int start, int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {-1L});
		searchContext.setStart(start);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			searchContext.setUserId(permissionChecker.getUserId());
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private BooleanClause<Query> _getBooleanClause(
			String filterString, EntityModel entityModel,
			FilterParser filterParser, Locale locale)
		throws Exception {

		return BooleanClauseFactoryUtil.create(
			_getBooleanQuery(filterString, entityModel, filterParser, locale),
			BooleanClauseOccur.MUST.getName());
	}

	private BooleanQuery _getBooleanQuery(
			String filterString, EntityModel entityModel,
			FilterParser filterParser, Locale locale)
		throws Exception {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);

		BooleanFilter booleanFilter = new BooleanFilter();

		com.liferay.portal.kernel.search.filter.Filter filter =
			_getSearchFilter(filterString, entityModel, filterParser, locale);

		if (filter != null) {
			booleanFilter.add(filter, BooleanClauseOccur.MUST);
		}

		booleanQuery.setPreBooleanFilter(booleanFilter);

		return booleanQuery;
	}

	private com.liferay.portal.kernel.search.filter.Filter _getSearchFilter(
			String filterString, EntityModel entityModel,
			FilterParser filterParser, Locale locale)
		throws Exception {

		Filter filter = new Filter(filterParser.parse(filterString));

		if (filter == Filter.emptyFilter()) {
			return null;
		}

		try {
			return _expressionConvert.convert(
				filter.getExpression(), locale, entityModel);
		}
		catch (Exception exception) {
			throw new InvalidFilterException(
				"Invalid filter: " + exception.getMessage(), exception);
		}
	}

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		_expressionConvert;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}