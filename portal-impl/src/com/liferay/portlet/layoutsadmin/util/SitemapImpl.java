/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Jorge Ferrer
 * @author Vilmos Papp
 */
@DoPrivileged
public class SitemapImpl implements Sitemap {

	@Override
	public String encodeXML(String input) {
		return StringUtil.replace(
			input,
			new String[] {"&", "<", ">", "'", "\""},
			new String[] {"&amp;", "&lt;", "&gt;", "&apos;", "&quot;"});
	}

	@Override
	public String getSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Document document = SAXReaderUtil.createDocument();

		document.setXMLEncoding(StringPool.UTF8);

		Element rootElement = document.addElement(
			"urlset", "http://www.google.com/schemas/sitemap/0.84");

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		visitLayouts(rootElement, layouts, themeDisplay);

		return document.asXML();
	}

	protected void addURLElement(
		Element element, String url, UnicodeProperties typeSettingsProperties,
		Date modifiedDate) {

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
	}

	protected void visitArticles(
			Element element, Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		List<JournalArticle> journalArticles =
			JournalArticleServiceUtil.getArticlesByLayoutUuid(
				layout.getGroupId(), layout.getUuid());

		if (journalArticles.isEmpty()) {
			return;
		}

		Set<String> processedArticleIds = new HashSet<String>();

		for (JournalArticle journalArticle : journalArticles) {
			if (processedArticleIds.contains(
					journalArticle.getArticleId()) ||
				(journalArticle.getStatus() !=
					WorkflowConstants.STATUS_APPROVED)) {

				continue;
			}

			String portalURL = PortalUtil.getPortalURL(layout, themeDisplay);

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				GroupLocalServiceUtil.getGroup(journalArticle.getGroupId()),
				false, themeDisplay);

			StringBundler sb = new StringBundler(4);

			if (!groupFriendlyURL.startsWith(portalURL)) {
				sb.append(portalURL);
			}

			sb.append(groupFriendlyURL);
			sb.append(JournalArticleConstants.CANONICAL_URL_SEPARATOR);
			sb.append(journalArticle.getUrlTitle());

			String articleURL = PortalUtil.getCanonicalURL(
				sb.toString(), themeDisplay, layout);

			addURLElement(
				element, articleURL, null, journalArticle.getModifiedDate());

			Locale[] availableLocales = LanguageUtil.getAvailableLocales();

			if (availableLocales.length > 1) {
				Locale defaultLocale = LocaleUtil.getDefault();

				for (Locale availableLocale : availableLocales) {
					if (!availableLocale.equals(defaultLocale)) {
						String alternateURL = PortalUtil.getAlternateURL(
							articleURL, themeDisplay, availableLocale);

						addURLElement(
							element, alternateURL, null,
							journalArticle.getModifiedDate());
					}
				}
			}

			processedArticleIds.add(journalArticle.getArticleId());
		}
	}

	protected void visitLayout(
			Element element, Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		if (layout.isHidden() || !PortalUtil.isLayoutSitemapable(layout) ||
			!GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("sitemap-include"), true)) {

			return;
		}

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		layoutFullURL = PortalUtil.getCanonicalURL(
			layoutFullURL, themeDisplay, layout);

		addURLElement(
			element, layoutFullURL, typeSettingsProperties,
			layout.getModifiedDate());

		Locale[] availableLocales = LanguageUtil.getAvailableLocales();

		if (availableLocales.length > 1) {
			Locale defaultLocale = LocaleUtil.getDefault();

			for (Locale availableLocale : availableLocales) {
				if (availableLocale.equals(defaultLocale)) {
					continue;
				}

				String alternateURL = PortalUtil.getAlternateURL(
					layoutFullURL, themeDisplay, availableLocale);

				addURLElement(
					element, alternateURL, typeSettingsProperties,
					layout.getModifiedDate());
			}
		}

		visitArticles(element, layout, themeDisplay);
		visitLayouts(element, layout.getChildren(), themeDisplay);
	}

	protected void visitLayouts(
			Element element, List<Layout> layouts, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		for (Layout layout : layouts) {
			visitLayout(element, layout, themeDisplay);
		}
	}

}