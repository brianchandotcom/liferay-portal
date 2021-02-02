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

package com.liferay.headless.admin.content.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.admin.content.internal.dto.v1_0.extension.ExtendedStructuredContent;
import com.liferay.headless.admin.content.internal.dto.v1_0.util.VersionUtil;
import com.liferay.headless.admin.content.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.search.aggregation.AggregationUtil;
import com.liferay.headless.delivery.search.sort.SortUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ContentLanguageUtil;
import com.liferay.portal.vulcan.util.EntityExtensionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.validation.constraints.NotNull;

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
	public Page<StructuredContent> getSiteStructuredContentsPage(
			@NotNull Long siteId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null,
			booleanQuery -> {
			},
			filter, JournalArticle.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ARTICLE_ID, Field.SCOPE_GROUP_ID),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);
				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (siteId != null) {
					searchContext.setGroupIds(new long[] {siteId});
				}

				SearchRequestBuilder searchRequestBuilder =
					_searchRequestBuilderFactory.builder(searchContext);

				AggregationUtil.processVulcanAggregation(
					_aggregations, _ddmIndexer, _queries, searchRequestBuilder,
					aggregation);

				SortUtil.processSorts(
					_ddmIndexer, searchRequestBuilder, searchContext.getSorts(),
					_queries, _sorts);
			},
			sorts,
			document -> {
				JournalArticle journalArticle =
					_journalArticleService.getLatestArticle(
						GetterUtil.getLong(document.get(Field.SCOPE_GROUP_ID)),
						document.get(Field.ARTICLE_ID),
						WorkflowConstants.STATUS_ANY);

				return _toExtendedStructuredContent(journalArticle);
			});
	}

	private StructuredContent _toExtendedStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		ContentLanguageUtil.addContentLanguageHeader(
			journalArticle.getAvailableLanguageIds(),
			journalArticle.getDefaultLanguageId(), contextHttpServletResponse,
			contextAcceptLanguage.getPreferredLocale());

		DTOConverter<JournalArticle, StructuredContent> dtoConverter =
			(DTOConverter<JournalArticle, StructuredContent>)
				_dtoConverterRegistry.getDTOConverter(
					JournalArticle.class.getName());

		if (dtoConverter == null) {
			return null;
		}

		StructuredContent structuredContent = dtoConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), _dtoConverterRegistry,
				contextHttpServletRequest, journalArticle.getResourcePrimKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			journalArticle);

		return EntityExtensionUtil.extend(
			structuredContent, StructuredContent.class,
			ExtendedStructuredContent.class,
			extendedStructuredContent -> extendedStructuredContent.setVersion(
				VersionUtil.toVersion(
					journalArticle.getGroupId(), journalArticle.getStatus(),
					journalArticle.getVersion())));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

}