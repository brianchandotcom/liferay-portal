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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.headless.delivery.dto.v1_0.ContentTemplate;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentTemplateUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.ContentTemplateEntityModel;
import com.liferay.headless.delivery.resource.v1_0.ContentTemplateResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-template.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentTemplateResource.class
)
public class ContentTemplateResourceImpl
	extends BaseContentTemplateResourceImpl {

	@Override
	public Page<ContentTemplate> getAssetLibraryContentTemplatesPage(
			Long assetLibraryId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return getSiteContentTemplatesPage(
			assetLibraryId, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public ContentTemplate getContentTemplate(
			Long siteId, String contentTemplateId)
		throws Exception {

		DDMTemplate ddmTemplate = _ddmTemplateService.getTemplate(
			siteId, _classNameLocalService.getClassNameId(DDMStructure.class),
			contentTemplateId);

		return ContentTemplateUtil.toContentTemplate(
			ddmTemplate, _getDtoConverterContext(ddmTemplate),
			groupLocalService, _portal, _userLocalService);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<ContentTemplate> getSiteContentTemplatesPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.singletonMap(
				"get",
				addAction(
					ActionKeys.MANAGE_LAYOUTS, "getSiteContentTemplatesPage",
					Group.class.getName(), siteId)),
			booleanQuery -> {
			},
			filter, DDMTemplate.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);
				searchContext.setAttribute(
					"resourceClassNameId",
					_classNameLocalService.getClassNameId(
						JournalArticle.class));
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> {
				DDMTemplate ddmTemplate = _ddmTemplateService.getTemplate(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

				return ContentTemplateUtil.toContentTemplate(
					ddmTemplate, _getDtoConverterContext(ddmTemplate),
					groupLocalService, _portal, _userLocalService);
			});
	}

	private DefaultDTOConverterContext _getDtoConverterContext(
		DDMTemplate ddmTemplate) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			Collections.singletonMap(
				"get",
				addAction(
					ActionKeys.VIEW, ddmTemplate.getTemplateId(),
					"getContentTemplate", ddmTemplate.getUserId(),
					DDMTemplate.class.getName() + "-" +
						JournalArticle.class.getName(),
					ddmTemplate.getGroupId())),
			null, null, contextAcceptLanguage.getPreferredLocale(),
			contextUriInfo, contextUser);
	}

	private static final EntityModel _entityModel =
		new ContentTemplateEntityModel();

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}