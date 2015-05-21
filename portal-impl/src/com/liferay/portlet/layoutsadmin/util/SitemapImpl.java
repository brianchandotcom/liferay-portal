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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Vilmos Papp
 */
@DoPrivileged
public class SitemapImpl implements Sitemap {

	@Override
	public void addURLElement(
		Element element, String url, UnicodeProperties typeSettingsProperties,
		Date modifiedDate, String canonicalURL,
		Map<Locale, String> alternateURLs) {

		Element urlElement = element.addElement("url");

		Element locElement = urlElement.addElement("loc");

		locElement.addText(encodeXML(url));

		if (typeSettingsProperties == null) {
			if (Validator.isNotNull(
					PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY)) {

				Element changefreqElement = urlElement.addElement("changefreq");

				changefreqElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
			}

			if (Validator.isNotNull(
					PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY)) {

				Element priorityElement = urlElement.addElement("priority");

				priorityElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY);
			}
		}
		else {
			String changefreq = typeSettingsProperties.getProperty(
				"sitemap-changefreq");

			if (Validator.isNotNull(changefreq)) {
				Element changefreqElement = urlElement.addElement("changefreq");

				changefreqElement.addText(changefreq);
			}
			else if (Validator.isNotNull(
						PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY)) {

				Element changefreqElement = urlElement.addElement("changefreq");

				changefreqElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
			}

			String priority = typeSettingsProperties.getProperty(
				"sitemap-priority");

			if (Validator.isNotNull(priority)) {
				Element priorityElement = urlElement.addElement("priority");

				priorityElement.addText(priority);
			}
			else if (Validator.isNotNull(
						PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY)) {

				Element priorityElement = urlElement.addElement("priority");

				priorityElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY);
			}
		}

		if (modifiedDate != null) {
			Element modifiedDateElement = urlElement.addElement("lastmod");

			DateFormat iso8601DateFormat = DateUtil.getISO8601Format();

			modifiedDateElement.addText(iso8601DateFormat.format(modifiedDate));
		}

		if (alternateURLs != null) {
			for (Map.Entry<Locale, String> entry : alternateURLs.entrySet()) {
				Locale locale = entry.getKey();
				String href = entry.getValue();

				Element alternateURLElement = urlElement.addElement(
					"xhtml:link", "http://www.w3.org/1999/xhtml");

				alternateURLElement.addAttribute("href", href);
				alternateURLElement.addAttribute(
					"hreflang", LocaleUtil.toW3cLanguageId(locale));
				alternateURLElement.addAttribute("rel", "alternate");
			}

			Element alternateURLElement = urlElement.addElement(
				"xhtml:link", "http://www.w3.org/1999/xhtml");

			alternateURLElement.addAttribute("rel", "alternate");
			alternateURLElement.addAttribute("hreflang", "x-default");
			alternateURLElement.addAttribute("href", canonicalURL);
		}
	}

	@Override
	public String encodeXML(String input) {
		return StringUtil.replace(
				input,
				new String[] {"&", "<", ">", "'", "\""},
				new String[] {"&amp;", "&lt;", "&gt;", "&apos;", "&quot;"});
	}

	@Override
	public Map<Locale, String> getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		Map<Locale, String> alternateURLs = new HashMap<>();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales(
				layout.getGroupId())) {

			String alternateURL = PortalUtil.getAlternateURL(
				canonicalURL, themeDisplay, availableLocale, layout);

			alternateURLs.put(availableLocale, alternateURL);
		}

		return alternateURLs;
	}

	@Override
	public String getSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		Document document = SAXReaderUtil.createDocument();

		document.setXMLEncoding(StringPool.UTF8);

		Element rootElement = document.addElement(
			"urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

		rootElement.addAttribute("xmlns:xhtml", "http://www.w3.org/1999/xhtml");

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		List<SitemapURLProvider> sitemapURLProviders =
			SitemapURLProviderRegistryUtil.getSitemapURLProviders();

		visitLayouts(rootElement, layouts, sitemapURLProviders, themeDisplay);

		return document.asXML();
	}

	protected void visitLayouts(
			Element element, List<Layout> layouts,
			List<SitemapURLProvider> sitemapURLProviders,
			ThemeDisplay themeDisplay)
		throws PortalException {

		for (Layout layout : layouts) {
			for (SitemapURLProvider sitemapURLProvider : sitemapURLProviders) {
				sitemapURLProvider.visitLayout(element, layout, themeDisplay);
			}

			visitLayouts(
				element, layout.getChildren(), sitemapURLProviders,
				themeDisplay);
		}
	}

}