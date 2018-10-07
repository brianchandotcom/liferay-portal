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

package com.liferay.segments.apio.internal.retriever;

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
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.segments.apio.internal.architect.filter.ExpressionVisitorImpl;
import com.liferay.segments.apio.internal.architect.filter.UserEntityModel;
import com.liferay.segments.retriever.UserRetriever;
import com.liferay.structured.content.apio.architect.entity.EntityModel;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.filter.FilterParser;
import com.liferay.structured.content.apio.architect.filter.InvalidFilterException;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;

import java.text.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = UserRetriever.class)
public class UserRetrieverImpl implements UserRetriever {

	@Override
	public List<User> getUsers(
			long companyId, String filterString, Locale locale, int start,
			int end)
		throws PortalException {

		try {
			Filter filter = (Filter)_filterParser.parse(filterString);

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

	private com.liferay.portal.kernel.search.filter.Filter _getSearchFilter(
		Filter filter, Locale locale) {

		if ((filter == null) || (filter == Filter.emptyFilter())) {
			return null;
		}

		try {
			Expression expression = filter.getExpression();

			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
				PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

			return (com.liferay.portal.kernel.search.filter.Filter)
				expression.accept(
					new ExpressionVisitorImpl(format, locale, _entityModel));
		}
		catch (ExpressionVisitException eve) {
			throw new InvalidFilterException(
				"Invalid filter: " + eve.getMessage(), eve);
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