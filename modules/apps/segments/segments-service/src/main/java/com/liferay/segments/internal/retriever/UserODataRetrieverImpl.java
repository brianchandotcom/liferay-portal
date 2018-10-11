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

package com.liferay.segments.internal.retriever;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.segments.internal.odata.entity.UserEntityModel;
import com.liferay.segments.retriever.UserODataRetriever;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = UserODataRetriever.class)
public class UserODataRetrieverImpl implements UserODataRetriever {

	@Override
	public List<User> getUsers(
			long companyId, String filterString, Locale locale, int start,
			int end)
		throws PortalException {

		try {
			Filter filter = new Filter(_filterParser.parse(filterString));

			SearchContext searchContext = _createSearchContext(
				companyId, start, end);

			Query fullQuery = _getFullQuery(filter, locale, searchContext);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			Hits hits;

			if (permissionChecker != null) {
				if (searchContext.getUserId() == 0) {
					searchContext.setUserId(permissionChecker.getUserId());
				}

				SearchResultPermissionFilter searchResultPermissionFilter =
					_searchResultPermissionFilterFactory.create(
						searchContext1 -> IndexSearcherHelperUtil.search(
							searchContext1, fullQuery),
						permissionChecker);

				hits = searchResultPermissionFilter.search(searchContext);
			}
			else {
				hits = IndexSearcherHelperUtil.search(searchContext, fullQuery);
			}

			return _getUsers(hits);
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to retrieve users: " + e.getMessage(), e);
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

	private Query _getFullQuery(
			Filter filter, Locale locale, SearchContext searchContext)
		throws SearchException {

		Indexer<User> indexer = _indexerRegistry.getIndexer(User.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		com.liferay.portal.kernel.search.filter.Filter searchFilter =
			_getSearchFilter(filter, locale);

		if (searchFilter != null) {
			BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

			preBooleanFilter.add(searchFilter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	@SuppressWarnings("unchecked")
	private com.liferay.portal.kernel.search.filter.Filter _getSearchFilter(
		Filter filter, Locale locale) {

		if ((filter == null) || (filter == Filter.emptyFilter())) {
			return null;
		}

		try {
			return _expressionConvert.convert(
				filter.getExpression(), locale, _entityModel);
		}
		catch (Exception e) {
			throw new InvalidFilterException(
				"Invalid filter: " + e.getMessage(), e);
		}
	}

	private User _getUser(Document document) throws PortalException {
		long userId = GetterUtil.getLong(document.get(Field.USER_ID));

		return _userLocalService.getUser(userId);
	}

	private List<User> _getUsers(Hits hits) throws PortalException {
		Document[] documents = hits.getDocs();

		List<User> users = new ArrayList<>(documents.length);

		for (Document document : documents) {
			users.add(_getUser(document));
		}

		return users;
	}

	@Reference(target = "(entity.model.name=" + UserEntityModel.NAME + ")")
	private EntityModel _entityModel;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		_expressionConvert;

	@Reference(target = "(entity.model.name=" + UserEntityModel.NAME + ")")
	private FilterParser _filterParser;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}