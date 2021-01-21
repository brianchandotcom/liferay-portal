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

import com.liferay.headless.admin.content.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.content.internal.dto.v1_0.converter.DisplayPageTemplateDTOConverter;
import com.liferay.headless.admin.content.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jürgen Kappler
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/display-page-template.properties",
	scope = ServiceScope.PROTOTYPE, service = DisplayPageTemplateResource.class
)
public class DisplayPageTemplateResourceImpl
	extends BaseDisplayPageTemplateResourceImpl {

	@Override
	public DisplayPageTemplate getSiteDisplayPageTemplate(
			Long siteId, String displayPageTemplateKey)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntry(
				siteId, displayPageTemplateKey);

		if (layoutPageTemplateEntry.getType() !=
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			throw new NoSuchPageTemplateEntryException(
				"No Display Page Template exists with key: " +
					displayPageTemplateKey);
		}

		return _toDisplayPageTemplate(layoutPageTemplateEntry);
	}

	@Override
	public Page<DisplayPageTemplate> getSiteDisplayPageTemplatesPage(
		Long siteId, Pagination pagination, Sort[] sorts) {

		DynamicQuery dynamicQuery = _getDynamicQuery(siteId);

		if (sorts != null) {
			for (Sort sort : sorts) {
				String fieldName = sort.getFieldName();

				fieldName = StringUtil.removeSubstring(fieldName, "_sortable");

				fieldName = _sortFieldNames.getOrDefault(fieldName, fieldName);

				if (sort.isReverse()) {
					dynamicQuery.addOrder(OrderFactoryUtil.desc(fieldName));
				}
				else {
					dynamicQuery.addOrder(OrderFactoryUtil.asc(fieldName));
				}
			}
		}

		return Page.of(
			HashMapBuilder.put(
				"get",
				addAction(
					"VIEW", "getSiteDisplayPageTemplatesPage",
					"com.liferay.portal.kernel.model.Group", siteId)
			).build(),
			TransformUtil.transform(
				_layoutPageTemplateEntryLocalService.dynamicQuery(
					dynamicQuery, pagination.getStartPosition(),
					pagination.getEndPosition()),
				(LayoutPageTemplateEntry layoutPageTemplateEntry) ->
					_toDisplayPageTemplate(layoutPageTemplateEntry)),
			pagination,
			_layoutPageTemplateEntryLocalService.dynamicQueryCount(
				dynamicQuery));
	}

	private DynamicQuery _getDynamicQuery(long groupId) {
		DynamicQuery dynamicQuery =
			_layoutPageTemplateEntryLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"companyId", contextCompany.getCompanyId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", groupId));

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"type",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"status", WorkflowConstants.STATUS_APPROVED));

		return dynamicQuery;
	}

	private DisplayPageTemplate _toDisplayPageTemplate(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		return _displayPageTemplateDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			layoutPageTemplateEntry);
	}

	private static final Map<String, String> _sortFieldNames =
		HashMapBuilder.put(
			"dateCreated", "createDate"
		).put(
			"dateModified", "modifiedDate"
		).put(
			"title", "name"
		).build();

	@Reference
	private DisplayPageTemplateDTOConverter _displayPageTemplateDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

}