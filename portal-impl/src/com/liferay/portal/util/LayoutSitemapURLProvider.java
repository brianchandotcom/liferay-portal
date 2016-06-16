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

package com.liferay.portal.util;

import com.liferay.layouts.admin.kernel.util.SitemapURLProvider;
import com.liferay.layouts.admin.kernel.util.SitemapUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
@OSGiBeanProperties
public class LayoutSitemapURLProvider implements SitemapURLProvider {

	@Override
	public String getClassName() {
		return Layout.class.getName();
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

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				layoutSet.getGroupId(), layoutSet.getPrivateLayout(),
				entry.getKey());

			for (Layout layout : layouts) {
				visitLayout(element, layout, themeDisplay);
			}
		}
	}

	protected void visitLayout(
			Element element, Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		if (!GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("sitemap-include"), true)) {

			return;
		}

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		layoutFullURL = PortalUtil.getCanonicalURL(
			layoutFullURL, themeDisplay, layout);

		Map<Locale, String> alternateURLs = SitemapUtil.getAlternateURLs(
			layoutFullURL, themeDisplay, layout);

		SitemapUtil.addURLElement(
			element, layoutFullURL, typeSettingsProperties,
			layout.getModifiedDate(), layoutFullURL, alternateURLs);

		if (alternateURLs.size() > 1) {
			Locale defaultLocale = LocaleUtil.getSiteDefault();

			for (Map.Entry<Locale, String> entry : alternateURLs.entrySet()) {
				Locale availableLocale = entry.getKey();
				String alternateURL = entry.getValue();

				if (!availableLocale.equals(defaultLocale) ||
					(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2)) {

					SitemapUtil.addURLElement(
						element, alternateURL, typeSettingsProperties,
						layout.getModifiedDate(), layoutFullURL, alternateURLs);
				}
			}
		}
	}

}