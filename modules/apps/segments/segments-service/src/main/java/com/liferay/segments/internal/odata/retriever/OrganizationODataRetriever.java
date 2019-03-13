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

package com.liferay.segments.internal.odata.retriever;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
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
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.segments.internal.odata.entity.OrganizationEntityModel;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.Organization",
	service = ODataRetriever.class
)
public class OrganizationODataRetriever
	implements ODataRetriever<Organization> {

	@Override
	public List<Organization> getResults(
			long companyId, String filterString, Locale locale, int start,
			int end)
		throws PortalException {

		try {
			Hits hits = null;

			SearchContext searchContext1 = _createSearchContext(
				companyId, start, end);

			Query query = _getQuery(filterString, locale, searchContext1);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker != null) {
				if (searchContext1.getUserId() == 0) {
					searchContext1.setUserId(permissionChecker.getUserId());
				}

				SearchResultPermissionFilter searchResultPermissionFilter =
					_searchResultPermissionFilterFactory.create(
						searchContext2 -> IndexSearcherHelperUtil.search(
							searchContext2, query),
						permissionChecker);

				hits = searchResultPermissionFilter.search(searchContext1);
			}
			else {
				hits = IndexSearcherHelperUtil.search(searchContext1, query);
			}

			return _getOrganizations(hits);
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to retrieve organizations with filter " + filterString,
				e);
		}
	}

	@Override
	public int getResultsCount(
			long companyId, String filterString, Locale locale)
		throws PortalException {

		try {
			SearchContext searchContext1 = _createSearchContext(
				companyId, 0, 0);

			Query query = _getQuery(filterString, locale, searchContext1);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker != null) {
				if (searchContext1.getUserId() == 0) {
					searchContext1.setUserId(permissionChecker.getUserId());
				}

				SearchResultPermissionFilter searchResultPermissionFilter =
					_searchResultPermissionFilterFactory.create(
						searchContext2 -> IndexSearcherHelperUtil.search(
							searchContext2, query),
						permissionChecker);

				Hits hits = searchResultPermissionFilter.search(searchContext1);

				return hits.getLength();
			}

			return (int)IndexSearcherHelperUtil.searchCount(
				searchContext1, query);
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to retrieve organizations count with filter " +
					filterString,
				e);
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

	private Organization _getOrganization(Document document)
		throws PortalException {

		long organizationId = GetterUtil.getLong(
			document.get(Field.ORGANIZATION_ID));

		return _organizationLocalService.getOrganization(organizationId);
	}

	private List<Organization> _getOrganizations(Hits hits)
		throws PortalException {

		Document[] documents = hits.getDocs();

		List<Organization> organizations = new ArrayList<>(documents.length);

		for (Document document : documents) {
			organizations.add(_getOrganization(document));
		}

		return organizations;
	}

	private Query _getQuery(
			String filterString, Locale locale, SearchContext searchContext)
		throws Exception {

		Indexer<Organization> indexer = _indexerRegistry.getIndexer(
			Organization.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		com.liferay.portal.kernel.search.filter.Filter searchFilter =
			_getSearchFilter(
				new Filter(_filterParser.parse(filterString)), locale);

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
			return _expressionConvert.convert(
				filter.getExpression(), locale, _entityModel);
		}
		catch (Exception e) {
			throw new InvalidFilterException(
				"Invalid filter: " + e.getMessage(), e);
		}
	}

	@Reference(
		target = "(entity.model.name=" + OrganizationEntityModel.NAME + ")"
	)
	private EntityModel _entityModel;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		_expressionConvert;

	@Reference(
		target = "(entity.model.name=" + OrganizationEntityModel.NAME + ")"
	)
	private FilterParser _filterParser;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}