/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Layout",
	service = DTOConverter.class
)
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
				setSiteExternalReferenceCode(
					() -> {
						Group group = layout.getGroup();

						return group.getExternalReferenceCode();
					});
				setType(() -> _toType(layout));
				setUuid(layout::getUuid);
			}
		};
	}

	private PageSettings _toPageSettings(Layout layout) {
		PageSettings pageSettings = null;

		SitePage.Type type = _toType(layout);

		if (type == SitePage.Type.WIDGET_PAGE) {
			pageSettings = _toWidgetPageSettings(layout);
		}
		else {
			return null;
		}

		pageSettings.setHiddenFromNavigation(layout::isHidden);

		return pageSettings;
	}

	private SitePage.Type _toType(Layout layout) {
		String type = layout.getType();

		if ((type == null) || type.isEmpty()) {
			return null;
		}
		else if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			return SitePage.Type.WIDGET_PAGE;
		}

		return null;
	}

	private WidgetPageSettings _toWidgetPageSettings(Layout layout) {
		return new WidgetPageSettings() {
			{
				setLayoutTemplateId(
					() -> layout.getTypeSettingsProperty(
						LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));
				setType(Type.WIDGET_PAGE_SETTINGS);
			}
		};
	}

}