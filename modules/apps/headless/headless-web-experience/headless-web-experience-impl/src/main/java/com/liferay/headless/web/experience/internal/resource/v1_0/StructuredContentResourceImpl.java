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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.model.Group;
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
import com.liferay.portal.kernel.search.SearchResultPermissionFilterSearcher;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content.properties",
	scope = ServiceScope.PROTOTYPE, service = StructuredContentResource.class
)
public class StructuredContentResourceImpl
	extends BaseStructuredContentResourceImpl {

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Long parentId, String filter, String sort, Pagination pagination)
		throws Exception {

		Hits hits = _getHits(pagination);

		return Page.of(
			transform(
				_journalHelper.getArticles(hits), this::_toStructuredContent),
			pagination, hits.getLength());
	}

	private SearchContext _createSearchContext(
		Group group, Pagination pagination) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setCompanyId(company.getCompanyId());
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setStart(pagination.getStartPosition());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	private Hits _getHits(Pagination pagination) throws Exception {
		SearchContext searchContext = _createSearchContext(
			company.getGroup(), pagination);

		Query query = _getQuery(searchContext);

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
				new SearchResultPermissionFilterSearcher() {

					public Hits search(SearchContext searchContext)
						throws SearchException {

						return IndexSearcherHelperUtil.search(
							searchContext, query);
					}

				},
				permissionChecker);

		return searchResultPermissionFilter.search(searchContext);
	}

	private Query _getQuery(SearchContext searchContext) throws Exception {
		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		return indexer.getFullQuery(searchContext);
	}

	private StructuredContent _toStructuredContent(
		JournalArticle journalArticle) {

		return new StructuredContent() {
			{
				setDateCreated(journalArticle.getCreateDate());
				setDateModified(journalArticle.getModifiedDate());
				setDatePublished(journalArticle.getDisplayDate());
				setDescription(
					journalArticle.getDescription(
						acceptLanguage.getPreferredLocale()));
				setId(journalArticle.getResourcePrimKey());
				setTitle(
					journalArticle.getTitle(
						acceptLanguage.getPreferredLocale()));
			}
		};
	}

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}