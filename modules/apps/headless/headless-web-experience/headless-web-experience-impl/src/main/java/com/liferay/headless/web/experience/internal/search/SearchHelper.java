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

package com.liferay.headless.web.experience.internal.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
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
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = SearchHelper.class)
public class SearchHelper {

	public List<JournalArticle> getJournalArticles(
			Group group, Pagination pagination, Filter filter)
		throws PortalException {

		return _journalHelper.getArticles(getHits(group, pagination, filter));
	}

	protected Hits getHits(Group group, Pagination pagination, Filter filter)
		throws SearchException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		SearchContext searchContext = _createSearchContext(
			group, pagination, permissionChecker);

		Query query = getQuery(searchContext, filter);

		SearchResultPermissionFilter searchResultPermissionFilter =
			_searchResultPermissionFilterFactory.create(
				searchContext1 -> IndexSearcherHelperUtil.search(
					searchContext1, query),
				permissionChecker);

		return searchResultPermissionFilter.search(searchContext);
	}

	protected Query getQuery(SearchContext searchContext, Filter filter)
		throws SearchException {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		if (filter != null) {
			BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

			preBooleanFilter.add(filter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	private SearchContext _createSearchContext(
		Group group, Pagination pagination,
		PermissionChecker permissionChecker) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setCompanyId(group.getCompanyId());
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setStart(pagination.getStartPosition());
		searchContext.setUserId(permissionChecker.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}