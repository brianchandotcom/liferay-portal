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

package com.liferay.layout.internal.util;

import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.admin.kernel.util.Sitemap;
import com.liferay.layout.admin.kernel.util.SitemapURLProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.LayoutTypeControllerTracker;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = SitemapURLProvider.class)
public class LayoutSitemapURLProvider implements SitemapURLProvider {

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	public void visitLayout(
			Element element, String layoutUuid, LayoutSet layoutSet,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layoutUuid, layoutSet.getGroupId(), layoutSet.isPrivateLayout());

		if (layout.isSystem()) {
			return;
		}

		visitLayout(element, layout, themeDisplay);
	}

	@Override
	public void visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		if (layoutSet.isPrivateLayout()) {
			return;
		}

		Map<String, LayoutTypeController> layoutTypeControllers =
			LayoutTypeControllerTracker.getLayoutTypeControllers();

		for (Map.Entry<String, LayoutTypeController> entry :
				layoutTypeControllers.entrySet()) {

			LayoutTypeController layoutTypeController = entry.getValue();

			if (!layoutTypeController.isSitemapable()) {
				continue;
			}

			int end = QueryUtil.ALL_POS;
			int start = QueryUtil.ALL_POS;

			int layoutsCount = _layoutService.getLayoutsCount(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				entry.getKey());

			if (layoutsCount > Sitemap.MAXIMUM_NUMBER_OF_ENTRIES) {
				end = layoutsCount;
				start = layoutsCount - Sitemap.MAXIMUM_NUMBER_OF_ENTRIES;
			}

			List<Layout> layouts = _layoutService.getLayouts(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				entry.getKey(), start, end);

			for (Layout layout : layouts) {
				visitLayout(element, layout, themeDisplay);
			}
		}
	}

	protected void visitLayout(
			Element element, Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		if (layout.isSystem()) {
			return;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		if (!GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					LayoutTypePortletConstants.SITEMAP_INCLUDE),
				true)) {

			return;
		}

		String layoutFullURL = _portal.getLayoutFullURL(layout, themeDisplay);

		layoutFullURL = _portal.getCanonicalURL(
			layoutFullURL, themeDisplay, layout);

		Map<Locale, String> alternateURLs = _sitemap.getAlternateURLs(
			layoutFullURL, themeDisplay, layout);

		for (String alternateURL : alternateURLs.values()) {
			_sitemap.addURLElement(
				element, alternateURL, typeSettingsUnicodeProperties,
				layout.getModifiedDate(), layoutFullURL, alternateURLs);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private Sitemap _sitemap;

}