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

package com.liferay.headless.web.experience.internal.resource;

import com.liferay.headless.web.experience.dto.StructuredContent;
import com.liferay.headless.web.experience.dto.StructuredContentCollection;
import com.liferay.headless.web.experience.resource.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.model.Company;
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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.context.Pagination;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.annotation.Generated;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-web-experience-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = StructuredContentResource.class
)
@Generated("")
public class StructuredContentResourceImpl
	implements StructuredContentResource {

	@Override
	public StructuredContentCollection<StructuredContent> getStructuredContentCollection(
			Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		Locale locale = LocaleUtil.getDefault();

		SearchContext searchContext = _createSearchContext(
			company.getCompanyId(), group.getGroupId(), locale,
			pagination.getStartPosition(), pagination.getEndPosition());

		Query fullQuery = _getFullQuery(locale, searchContext);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Hits hits = null;

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

		List<StructuredContent> structuredContents = Stream.of(
			_journalHelper.getArticles(hits)
		).flatMap(
			List::stream
		).map(
			journalArticle -> {
				StructuredContent structuredContent = new StructuredContent();

				structuredContent.setId(journalArticle.getResourcePrimKey());

				return structuredContent;
			}
		).collect(
			Collectors.toList()
		);

		return new StructuredContentCollection(
			structuredContents, hits.getLength());
	}

	private SearchContext _createSearchContext(
		long companyId, long groupId, Locale locale, int start,
		int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	private Query _getFullQuery(Locale locale, SearchContext searchContext)
		throws SearchException {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		return indexer.getFullQuery(searchContext);
	}

	@Reference
	private CompanyService _companyService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;
}