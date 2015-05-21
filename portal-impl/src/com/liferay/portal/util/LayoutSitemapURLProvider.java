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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.layoutsadmin.util.SitemapURLProvider;
import com.liferay.portlet.layoutsadmin.util.SitemapUtil;

import java.util.Locale;
import java.util.Set;

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
	public void visitLayout(
			Element element, Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		if (!PortalUtil.isLayoutSitemapable(layout) ||
			!GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("sitemap-include"), true)) {

			return;
		}

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		layoutFullURL = PortalUtil.getCanonicalURL(
			layoutFullURL, themeDisplay, layout);

		SitemapUtil.addURLElement(
			element, layoutFullURL, typeSettingsProperties,
			layout.getModifiedDate(), layoutFullURL,
			SitemapUtil.getAlternateURLs(layoutFullURL, themeDisplay, layout));

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			layout.getGroupId());

		if (availableLocales.size() > 1) {
			Locale defaultLocale = LocaleUtil.getSiteDefault();

			for (Locale availableLocale : availableLocales) {
				if (availableLocale.equals(defaultLocale)) {
					continue;
				}

				String alternateURL = PortalUtil.getAlternateURL(
					layoutFullURL, themeDisplay, availableLocale, layout);

				SitemapUtil.addURLElement(
					element, alternateURL, typeSettingsProperties,
					layout.getModifiedDate(), layoutFullURL,
					SitemapUtil.getAlternateURLs(
						layoutFullURL, themeDisplay, layout));
			}
		}
	}

}