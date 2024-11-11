/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageTemplate;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-template.properties",
	scope = ServiceScope.PROTOTYPE, service = PageTemplateResource.class
)
public class PageTemplateResourceImpl extends BasePageTemplateResourceImpl {

	@Override
	public Page<PageTemplate>
			getSiteSiteByExternalReferenceCodePageTemplatesPage(
				String siteExternalReferenceCode, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return Page.of(
			transform(
				_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
					group.getGroupId(),
					new int[] {
						LayoutPageTemplateEntryTypeConstants.BASIC,
						LayoutPageTemplateEntryTypeConstants.WIDGET_PAGE
					},
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				layoutPageTemplateEntry -> _pageTemplateDTOConverter.toDTO(
					layoutPageTemplateEntry)));
	}

	@Override
	public PageTemplate postSiteSiteByExternalReferenceCodePageTemplate(
			String siteExternalReferenceCode, PageTemplate pageTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _addPageTemplate(group, pageTemplate);
	}

	private PageTemplate _addPageTemplate(
			Group group, PageTemplate pageTemplate)
		throws Exception {

		if (Objects.equals(
				pageTemplate.getType(),
				PageTemplate.Type.CONTENT_PAGE_TEMPLATE)) {

			return _pageTemplateDTOConverter.toDTO(
				_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
					pageTemplate.getExternalReferenceCode(), group.getGroupId(),
					_getLayoutPageTemplateCollectionId(group, pageTemplate),
					pageTemplate.getName(),
					LayoutPageTemplateEntryTypeConstants.BASIC, 0L,
					WorkflowConstants.STATUS_DRAFT,
					_getServiceContext(
						group, pageTemplate, pageTemplate.getUuid())));
		}

		WidgetPageTemplate widgetPageTemplate =
			(WidgetPageTemplate)pageTemplate;

		ServiceContext serviceContext = _getServiceContext(
			group, widgetPageTemplate, null);

		Map<Locale, String> nameMap = HashMapBuilder.put(
			serviceContext.getLocale(), widgetPageTemplate.getName()
		).build();

		if (widgetPageTemplate.getName_i18n() != null) {
			nameMap = LocalizedMapUtil.getLocalizedMap(
				widgetPageTemplate.getName_i18n());
		}

		Map<Locale, String> descriptionMap = Collections.emptyMap();

		if (widgetPageTemplate.getDescription_i18n() != null) {
			descriptionMap = LocalizedMapUtil.getLocalizedMap(
				widgetPageTemplate.getDescription_i18n());
		}

		LayoutPrototype layoutPrototype =
			_layoutPrototypeService.addLayoutPrototype(
				nameMap, descriptionMap, true, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getFirstLayoutPageTemplateEntry(
					layoutPrototype.getLayoutPrototypeId());

		if (widgetPageTemplate.getExternalReferenceCode() != null) {
			layoutPageTemplateEntry.setExternalReferenceCode(
				widgetPageTemplate.getExternalReferenceCode());
		}

		layoutPageTemplateEntry.setGroupId(group.getGroupId());
		layoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			_getLayoutPageTemplateCollectionId(group, widgetPageTemplate));

		if (widgetPageTemplate.getUuid() != null) {
			layoutPageTemplateEntry.setUuid(widgetPageTemplate.getUuid());
		}

		return _pageTemplateDTOConverter.toDTO(
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry));
	}

	private long _getLayoutPageTemplateCollectionId(
			Group group, PageTemplate pageTemplate)
		throws Exception {

		PageTemplateSet pageTemplateSet = pageTemplate.getPageTemplateSet();

		if (pageTemplateSet == null) {
			return LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					pageTemplateSet.getExternalReferenceCode(),
					group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			return LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;
		}

		return layoutPageTemplateCollection.getLayoutPageTemplateCollectionId();
	}

	private ServiceContext _getServiceContext(
		Group group, PageTemplate pageTemplate, String uuid) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			group.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(pageTemplate.getDateCreated());
		serviceContext.setModifiedDate(pageTemplate.getDateModified());
		serviceContext.setUuid(uuid);

		return serviceContext;
	}

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPrototypeService _layoutPrototypeService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageTemplateDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateEntry, PageTemplate>
		_pageTemplateDTOConverter;

}