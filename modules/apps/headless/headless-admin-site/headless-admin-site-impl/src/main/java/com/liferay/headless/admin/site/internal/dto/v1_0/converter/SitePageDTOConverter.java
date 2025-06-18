/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.ContentPageSettings;
import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.SitePageTypeUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = DTOConverter.class)
public class SitePageDTOConverter implements DTOConverter<Layout, SitePage> {

	@Override
	public String getContentType() {
		return SitePage.class.getSimpleName();
	}

	@Override
	public SitePage toDTO(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws Exception {

		return new SitePage() {
			{
				setAvailableLanguages(
					() -> LocaleUtil.toW3cLanguageIds(
						layout.getAvailableLanguageIds()));
				setDateCreated(layout::getCreateDate);
				setDateModified(layout::getModifiedDate);
				setDatePublished(layout::getPublishDate);
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setFriendlyUrlPath_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, layout.getFriendlyURLMap()));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, layout.getNameMap()));
				setPageSettings(() -> _toPageSettings(layout));
				setParentSitePageExternalReferenceCode(
					() -> {
						if (layout.getParentLayoutId() == 0) {
							return null;
						}

						Layout parentLayout = _layoutLocalService.getLayout(
							layout.getGroupId(), layout.isPrivateLayout(),
							layout.getParentLayoutId());

						return parentLayout.getExternalReferenceCode();
					});
				setType(
					() -> SitePageTypeUtil.toExternalType(layout.getType()));
				setUuid(layout::getUuid);
			}
		};
	}

	private PageSettings _getPageSettings(Layout layout) {
		SitePage.Type type = SitePageTypeUtil.toExternalType(layout.getType());

		if (type == SitePage.Type.CONTENT_PAGE) {
			return new ContentPageSettings();
		}

		return _toWidgetPageSettings(layout);
	}

	private PageSettings _toPageSettings(Layout layout) {
		PageSettings pageSettings = _getPageSettings(layout);

		pageSettings.setHiddenFromNavigation(layout::isHidden);

		return pageSettings;
	}

	private WidgetPageSettings _toWidgetPageSettings(Layout layout) {
		WidgetPageSettings widgetPageSettings = new WidgetPageSettings();

		widgetPageSettings.setLayoutTemplateId(
			() -> layout.getTypeSettingsProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));

		return widgetPageSettings;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}