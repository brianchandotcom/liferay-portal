/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/site-page.properties",
	scope = ServiceScope.PROTOTYPE, service = SitePageResource.class
)
public class SitePageResourceImpl extends BaseSitePageResourceImpl {

	@Override
	public SitePage getSiteSiteByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _toSitePage(
			siteExternalReferenceCode, sitePageExternalReferenceCode);
	}

	private SitePage _toSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode)
		throws Exception {

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode, group.getGroupId());

		DefaultDTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest,
				layout.getPlid(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser);

		return _sitePageDTOConverter.toDTO(dtoConverterContext, layout);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.SitePageDTOConverter)"
	)
	private DTOConverter<Layout, SitePage> _sitePageDTOConverter;

}