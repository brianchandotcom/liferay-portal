/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Objects;

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
	public void deleteSiteSiteByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		_layoutService.deleteLayout(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));
	}

	@Override
	public SitePage getSiteSiteByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		return _toSitePage(layout);
	}

	@Override
	public SitePage postByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

		WidgetPageSettings widgetPageSettings =
			(WidgetPageSettings)sitePage.getPageSettings();

		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, contextHttpServletRequest, sitePage.getViewableByAsString()
		).build();

		serviceContext.setUuid(sitePage.getUuid());

		return _toSitePage(
			_layoutService.addLayout(
				sitePage.getExternalReferenceCode(), groupId, false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0,
				LocalizedMapUtil.getLocalizedMap(sitePage.getName_i18n()),
				LocalizedMapUtil.getLocalizedMap(sitePage.getName_i18n()), null,
				null, null, _toType(sitePage.getType()),
				UnicodePropertiesBuilder.create(
					true
				).setProperty(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
					widgetPageSettings.getLayoutTemplateId()
				).buildString(),
				GetterUtil.getBoolean(
					widgetPageSettings.getHiddenFromNavigation()),
				false,
				LocalizedMapUtil.getLocalizedMap(
					sitePage.getFriendlyUrlPath_i18n()),
				0, serviceContext));
	}

	private SitePage _toSitePage(Layout layout) throws Exception {
		return _sitePageDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest,
				layout.getPlid(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			layout);
	}

	private String _toType(SitePage.Type type) {
		if (Objects.equals(type, SitePage.Type.COLLECTION_PAGE)) {
			return LayoutConstants.TYPE_COLLECTION;
		}
		else if (Objects.equals(type, SitePage.Type.CONTENT_PAGE)) {
			return LayoutConstants.TYPE_CONTENT;
		}
		else if (Objects.equals(type, SitePage.Type.WIDGET_PAGE)) {
			return LayoutConstants.TYPE_PORTLET;
		}

		throw new UnsupportedOperationException();
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutService _layoutService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.SitePageDTOConverter)"
	)
	private DTOConverter<Layout, SitePage> _sitePageDTOConverter;

}