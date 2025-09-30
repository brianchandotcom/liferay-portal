/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.admin.site.dto.v1_0.CustomMetaTag;
import com.liferay.headless.admin.site.dto.v1_0.NavigationSettings;
import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTag;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class PageSettingsUtil {

	public static PageSettings getPageSettings(
		DLAppService dlAppService, DTOConverterContext dtoConverterContext,
		LayoutSEOEntryLocalService layoutSEOEntryLocalService, Layout layout) {

		return new PageSettings() {
			{
				setCustomMetaTags(
					() -> _getCustomMetaTags(
						layout, layoutSEOEntryLocalService));
				setHiddenFromNavigation(layout::isHidden);
				setNavigationSettings(
					() -> _toNavigationSettings(
						layout.getTypeSettingsProperties()));
				setOpenGraphSettings(
					() -> OpenGraphSettingsUtil.getOpenGraphSettings(
						dlAppService, dtoConverterContext,
						layoutSEOEntryLocalService, layout));
				setQueryString(
					() -> {
						UnicodeProperties unicodeProperties =
							layout.getTypeSettingsProperties();

						return unicodeProperties.getProperty(
							LayoutTypePortletConstants.QUERY_STRING);
					});
				setSeoSettings(
					() -> SEOSettingsUtil.getSeoSettings(
						dtoConverterContext, layoutSEOEntryLocalService,
						layout));
			}
		};
	}

	private static CustomMetaTag[] _getCustomMetaTags(
		Layout layout, LayoutSEOEntryLocalService layoutSEOEntryLocalService) {

		LayoutSEOEntry layoutSEOEntry =
			layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());

		if (layoutSEOEntry == null) {
			return null;
		}

		List<LayoutSEOEntryCustomMetaTag> layoutSEOEntryCustomMetaTags =
			layoutSEOEntryLocalService.getLayoutSEOEntryCustomMetaTags(
				layoutSEOEntry.getGroupId(),
				layoutSEOEntry.getLayoutSEOEntryId());

		List<CustomMetaTag> customMetaTags = new ArrayList<>();

		for (LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag :
				layoutSEOEntryCustomMetaTags) {

			customMetaTags.add(
				new CustomMetaTag() {
					{
						setKey(layoutSEOEntryCustomMetaTag::getProperty);
						setValue_i18n(
							() -> LocalizedMapUtil.getI18nMap(
								layoutSEOEntryCustomMetaTag.getContentMap()));
					}
				});
		}

		return customMetaTags.toArray(new CustomMetaTag[0]);
	}

	private static NavigationSettings _toNavigationSettings(
		UnicodeProperties unicodeProperties) {

		String targetPropertyValue = unicodeProperties.getProperty(
			LayoutTypePortletConstants.TARGET);
		String targetTypePropertyValue = unicodeProperties.getProperty(
			"targetType");

		if ((targetPropertyValue == null) &&
			(targetTypePropertyValue == null)) {

			return null;
		}

		return new NavigationSettings() {
			{
				setTarget(() -> targetPropertyValue);
				setTargetType(
					() -> {
						if (targetTypePropertyValue == null) {
							return null;
						}

						if (Objects.equals(
								targetTypePropertyValue, "useNewTab")) {

							return TargetType.NEW_TAB;
						}

						return TargetType.SPECIFIC_FRAME;
					});
			}
		};
	}

}