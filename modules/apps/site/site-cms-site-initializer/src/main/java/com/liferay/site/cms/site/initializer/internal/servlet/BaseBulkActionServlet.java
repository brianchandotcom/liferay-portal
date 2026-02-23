/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.servlet;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.search.rest.util.FilterUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Locale;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public abstract class BaseBulkActionServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		_createContext(httpServletRequest, httpServletResponse);

		try {
			User user = portal.getUser(httpServletRequest);

			if ((user == null) || user.isGuestUser()) {
				throw new PrincipalException.MustBeAuthenticated(
					StringPool.BLANK);
			}
		}
		catch (PortalException portalException) {
			throw new ServletException(portalException);
		}

		super.service(httpServletRequest, httpServletResponse);
	}

	@Reference(target = "(entity.model.name=BulkAction)")
	protected EntityModel entityModel;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	protected ExpressionConvert<Filter> expressionConvert;

	@Reference
	protected FilterParserProvider filterParserProvider;

	@Reference
	protected Portal portal;

	private void _createContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				httpServletResponse);
		}
		catch (ActionException actionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(actionException);
			}
		}
	}

	private BooleanClause<Query> _getBooleanClause(
		UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
		Filter filter) {

		BooleanQuery booleanQuery = new BooleanQueryImpl() {
			{
				add(new MatchAllQuery(), BooleanClauseOccur.MUST);

				BooleanFilter booleanFilter = new BooleanFilter();

				booleanFilter.add(filter, BooleanClauseOccur.MUST);

				setPreBooleanFilter(booleanFilter);
			}
		};

		try {
			booleanQueryUnsafeConsumer.accept(booleanQuery);

			return BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _populateSearchContext(
		Filter filter, String search, SearchContext searchContext, User user) {

		int[] statuses = FilterUtil.getStatuses(filter);

		if (ArrayUtil.isNotEmpty(statuses)) {
			searchContext.setAttribute("status", statuses);
		}

		if (filter != null) {
			searchContext.setBooleanClauses(
				new BooleanClause[] {
					_getBooleanClause(
						booleanQuery -> {
						},
						filter)
				});
		}

		searchContext.setCompanyId(user.getCompanyId());
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setKeywords(search);
		searchContext.setLocale(user.getLocale());
		searchContext.setStart(QueryUtil.ALL_POS);
		searchContext.setTimeZone(user.getTimeZone());
		searchContext.setUserId(user.getUserId());
	}

	private Filter _toFilter(String filterString, Locale locale) {
		try {
			FilterParser filterParser = filterParserProvider.provide(
				entityModel);

			com.liferay.portal.odata.filter.Filter oDataFilter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse(filterString));

			return expressionConvert.convert(
				oDataFilter.getExpression(), locale, entityModel);
		}
		catch (Exception exception) {
			_log.error("Invalid filter " + filterString, exception);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBulkActionServlet.class);

}