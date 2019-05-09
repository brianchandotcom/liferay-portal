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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = ODataSearchHelper.class)
public class ODataSearchHelper {

	public Hits search(
			long companyId, String filterString, String className,
			EntityModel entityModel, FilterParser filterParser, Locale locale,
			int start, int end)
		throws PortalException {

		try {
			SearchContext searchContext = _createSearchContext(
				companyId, start, end);

			Query query = _getQuery(
				filterString, className, entityModel, filterParser, locale,
				searchContext);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker == null) {
				return IndexSearcherHelperUtil.search(searchContext, query);
			}

			if (searchContext.getUserId() == 0) {
				searchContext.setUserId(permissionChecker.getUserId());
			}

			SearchResultPermissionFilter searchResultPermissionFilter =
				_searchResultPermissionFilterFactory.create(
					searchContext2 -> IndexSearcherHelperUtil.search(
						searchContext2, query),
					permissionChecker);

			return searchResultPermissionFilter.search(searchContext);
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to retrieve results with filter " + filterString, e);
		}
	}

	public int searchCount(
			long companyId, String filterString, String className,
			EntityModel entityModel, FilterParser filterParser, Locale locale)
		throws PortalException {

		try {
			SearchContext searchContext = _createSearchContext(companyId, 0, 0);

			Query query = _getQuery(
				filterString, className, entityModel, filterParser, locale,
				searchContext);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker == null) {
				return (int)IndexSearcherHelperUtil.searchCount(
					searchContext, query);
			}

			if (searchContext.getUserId() == 0) {
				searchContext.setUserId(permissionChecker.getUserId());
			}

			SearchResultPermissionFilter searchResultPermissionFilter =
				_searchResultPermissionFilterFactory.create(
					searchContext2 -> IndexSearcherHelperUtil.search(
						searchContext2, query),
					permissionChecker);

			Hits hits = searchResultPermissionFilter.search(searchContext);

			return hits.getLength();
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to retrieve results with filter " + filterString, e);
		}
	}

	private SearchContext _createSearchContext(
		long companyId, int start, int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private Query _getQuery(
			String filterString, String className, EntityModel entityModel,
			FilterParser filterParser, Locale locale,
			SearchContext searchContext)
		throws Exception {

		Indexer<Organization> indexer = _indexerRegistry.getIndexer(className);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		com.liferay.portal.kernel.search.filter.Filter searchFilter =
			_getSearchFilter(filterString, entityModel, filterParser, locale);

		if (searchFilter != null) {
			BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

			preBooleanFilter.add(searchFilter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	private com.liferay.portal.kernel.search.filter.Filter _getSearchFilter(
			String filterString, EntityModel entityModel,
			FilterParser filterParser, Locale locale)
		throws ExpressionVisitException {

		Filter filter = new Filter(filterParser.parse(filterString));

		if (filter == Filter.emptyFilter()) {
			return null;
		}

		try {
			return _expressionConvert.convert(
				filter.getExpression(), locale, entityModel);
		}
		catch (Exception e) {
			throw new InvalidFilterException(
				"Invalid filter: " + e.getMessage(), e);
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