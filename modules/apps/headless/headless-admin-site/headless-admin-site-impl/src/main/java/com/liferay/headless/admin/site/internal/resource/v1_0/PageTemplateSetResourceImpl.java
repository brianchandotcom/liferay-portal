/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateSetResource;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-template-set.properties",
	scope = ServiceScope.PROTOTYPE, service = PageTemplateSetResource.class
)
public class PageTemplateSetResourceImpl
	extends BasePageTemplateSetResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode,
			String pageTemplateSetExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(
			pageTemplateSetExternalReferenceCode, group.getGroupId());
	}

	@Override
	public PageTemplateSet getSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode,
			String pageTemplateSetExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					pageTemplateSetExternalReferenceCode, group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			throw new NoSuchPageTemplateCollectionException();
		}

		return _toPageTemplateSet(layoutPageTemplateCollection);
	}

	private PageTemplateSet _toPageTemplateSet(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws Exception {

		return _pageTemplateSetDTOConverter.toDTO(layoutPageTemplateCollection);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageTemplateSetDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateCollection, PageTemplateSet>
		_pageTemplateSetDTOConverter;

}