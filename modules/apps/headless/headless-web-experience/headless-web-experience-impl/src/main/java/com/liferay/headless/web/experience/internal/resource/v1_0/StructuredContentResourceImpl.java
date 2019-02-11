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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.internal.util.JournalArticleContentHelper;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalHelper;
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
import com.liferay.portal.kernel.search.SearchResultPermissionFilterSearcher;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Hits hits = _getHits(filter, pagination, sorts);

		return Page.of(
			transform(
				_journalHelper.getArticles(hits), this::_toStructuredContent),
			pagination, hits.getLength());
	}

	@Override
	public StructuredContent postContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureService.getStructure(
			structuredContent.getContentStructureId());

		Locale locale = acceptLanguage.getPreferredLocale();

		String content =
			_journalArticleContentHelper.createJournalArticleContent(
				ddmStructure);

		String ddmStructureKey = ddmStructure.getStructureKey();
		String ddmTemplateKey = _getDDMTemplateKey(ddmStructure);

		LocalDateTime localDateTime = _getLocalDateTime(
			structuredContent.getDatePublished());

		ServiceContext serviceContext = _getServiceContext(
			contentSpaceId, structuredContent);

		JournalArticle journalArticle = _journalArticleService.addArticle(
			contentSpaceId, 0, 0, 0, null, true,
			new HashMap<Locale, String>() {
				{
					put(locale, structuredContent.getTitle());
				}
			},
			new HashMap<Locale, String>() {
				{
					put(locale, structuredContent.getDescription());
				}
			},
			content, ddmStructureKey, ddmTemplateKey, null,
			localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
			localDateTime.getYear(), localDateTime.getHour(),
			localDateTime.getMinute(), 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true,
			true, null, serviceContext);

		return _toStructuredContent(journalArticle);
	}

	private SearchContext _createSearchContext(
		Group group, Pagination pagination, PermissionChecker permissionChecker,
		Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setCompanyId(company.getCompanyId());
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setSorts(sorts);
		searchContext.setStart(pagination.getStartPosition());
		searchContext.setUserId(permissionChecker.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	private String _getDDMTemplateKey(DDMStructure ddmStructure) {
		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		DDMTemplate ddmTemplate = ddmTemplates.get(0);

		return ddmTemplate.getTemplateKey();
	}

	private Hits _getHits(Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		SearchContext searchContext = _createSearchContext(
			company.getGroup(), pagination, permissionChecker, sorts);

		Query query = _getQuery(filter, searchContext);

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

	private LocalDateTime _getLocalDateTime(Date date) {
		Instant instant;

		if (date == null) {
			instant = Instant.now();
		}
		else {
			instant = date.toInstant();
		}

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		return zonedDateTime.toLocalDateTime();
	}

	private Query _getQuery(Filter filter, SearchContext searchContext)
		throws Exception {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		if (filter != null) {
			BooleanFilter booleanFilter = booleanQuery.getPreBooleanFilter();

			booleanFilter.add(filter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	private ServiceContext _getServiceContext(
		long contentSpaceId, StructuredContent structuredContent) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (structuredContent.getKeywords() != null) {
			serviceContext.setAssetTagNames(structuredContent.getKeywords());
		}

		serviceContext.setScopeGroupId(contentSpaceId);

		return serviceContext;
	}

	private StructuredContent _toStructuredContent(
		JournalArticle journalArticle) {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				setContentStructureId(ddmStructure.getStructureId());
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
	private DDMStructureService _ddmStructureService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalArticleContentHelper _journalArticleContentHelper;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}