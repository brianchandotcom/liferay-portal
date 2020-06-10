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

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.admin.content.dto.v1_0.StructuredContent;
import com.liferay.headless.admin.content.internal.dto.v1_0.converter.StructuredContentDTOConverter;
import com.liferay.headless.admin.content.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.admin.content.internal.odata.entity.v1_0.EntityFieldsProvider;
import com.liferay.headless.admin.content.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.admin.content.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

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
	extends BaseStructuredContentResourceImpl implements EntityModelResource {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		long structuredContentId = GetterUtil.getLong(
			(String)multivaluedMap.getFirst("structuredContentId"));

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		List<EntityField> entityFields = _entityFieldsProvider.provide(
			journalArticle.getDDMStructure());

		return new StructuredContentEntityModel(
			entityFields,
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(JournalArticle.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
	}

	@Override
	public Page<StructuredContent> getStructuredContentsVersionsPage(
			Long structuredContentId, Boolean flatten, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _getStructuredContentsPage(
			HashMapBuilder.<String, Map<String, String>>put(
				"get",
				addAction(
					"VIEW", journalArticle.getResourcePrimKey(),
					"getStructuredContentsVersionsPage",
					journalArticle.getUserId(), JournalArticle.class.getName(),
					journalArticle.getGroupId())
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.ENTRY_CLASS_PK, structuredContentId.toString()),
					BooleanClauseOccur.MUST);
			},
			journalArticle.getGroupId(), filter, search, pagination, sorts);
	}

	private Page<StructuredContent> _getStructuredContentsPage(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Long siteId, Filter filter, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions, booleanQueryUnsafeConsumer, filter, JournalArticle.class,
			keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ARTICLE_ID, Field.SCOPE_GROUP_ID, Field.VERSION),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (siteId != null) {
					searchContext.setGroupIds(new long[] {siteId});
				}

				searchContext.setAttribute("head", Boolean.TRUE);
				searchContext.setAttribute("head", Boolean.FALSE);
				searchContext.setAttribute(
					"status", WorkflowConstants.STATUS_ANY);
			},
			sorts,
			document -> {
				double version = GetterUtil.getDouble(
					document.get(Field.VERSION));
				long groupId = GetterUtil.getLong(
					document.get(Field.SCOPE_GROUP_ID));
				String articleId = document.get(Field.ARTICLE_ID);

				return _toStructuredContent(
					_journalArticleService.getArticle(
						groupId, articleId, version));
			});
	}

	private StructuredContent _toStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		return _structuredContentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), _dtoConverterRegistry,
				journalArticle.getResourcePrimKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			journalArticle);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private EntityFieldsProvider _entityFieldsProvider;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

	@Reference
	private StructuredContentDTOConverter _structuredContentDTOConverter;

}