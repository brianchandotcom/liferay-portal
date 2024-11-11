/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateResource;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

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

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageTemplateDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateEntry, PageTemplate>
		_pageTemplateDTOConverter;

}