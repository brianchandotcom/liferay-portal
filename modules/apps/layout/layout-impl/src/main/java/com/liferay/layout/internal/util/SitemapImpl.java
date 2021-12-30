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
import com.liferay.layout.admin.kernel.util.SitemapURLProviderRegistryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.portal.util.PropsValues;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Vilmos Papp
 */
@Component(service = Sitemap.class)
public class SitemapImpl implements Sitemap {

	@Override
	public void addURLElement(
		Element element, String url,
		UnicodeProperties typeSettingsUnicodeProperties, Date modifiedDate,
		String canonicalURL, Map<Locale, String> alternateURLs) {

		Element urlElement = element.addElement("url");

		Element locElement = urlElement.addElement("loc");

		locElement.addText(encodeXML(url));

		if (modifiedDate != null) {
			Element modifiedDateElement = urlElement.addElement("lastmod");

			DateFormat iso8601DateFormat = DateUtil.getISO8601Format();

			modifiedDateElement.addText(iso8601DateFormat.format(modifiedDate));
		}

		if (typeSettingsUnicodeProperties == null) {
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
			String changefreq = typeSettingsUnicodeProperties.getProperty(
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

			String priority = typeSettingsUnicodeProperties.getProperty(
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

		_removeOldestElement(element, urlElement);
	}

	@Override
	public String encodeXML(String input) {
		return StringUtil.replace(
			input, new char[] {'&', '<', '>', '\'', '\"'},
			new String[] {"&amp;", "&lt;", "&gt;", "&apos;", "&quot;"});
	}

	@Override
	public Map<Locale, String> getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		return _portal.getAlternateURLs(canonicalURL, themeDisplay, layout);
	}

	@Override
	public String getSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getSitemap(null, groupId, privateLayout, themeDisplay);
	}

	@Override
	public String getSitemap(
			String layoutUuid, long groupId, boolean privateLayout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		if (Validator.isNull(layoutUuid) &&
			PropsValues.XML_SITEMAP_INDEX_ENABLED) {

			return _getIndexSitemap(groupId, privateLayout, themeDisplay);
		}

		return _getSitemap(layoutUuid, groupId, privateLayout, themeDisplay);
	}

	private String _getIndexSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		Document document = _saxReader.createDocument();

		document.setXMLEncoding(StringPool.UTF8);

		Element rootElement = document.addElement(
			"sitemapindex", "http://www.sitemaps.org/schemas/sitemap/0.9");

		rootElement.addAttribute("xmlns:xhtml", "http://www.w3.org/1999/xhtml");

		_initEntriesAndSize(rootElement);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		_visitLayoutSet(rootElement, layoutSet, themeDisplay);

		_removeEntriesAndSize(rootElement);

		return document.asXML();
	}

	private String _getSitemap(
			String layoutUuid, long groupId, boolean privateLayout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Document document = _saxReader.createDocument();

		document.setXMLEncoding(StringPool.UTF8);

		Element rootElement = document.addElement(
			"urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

		rootElement.addAttribute(
			"xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute(
			"xsi:schemaLocation",
			"http://www.w3.org/1999/xhtml " +
				"http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd");
		rootElement.addAttribute("xmlns:xhtml", "http://www.w3.org/1999/xhtml");

		_initEntriesAndSize(rootElement);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		List<SitemapURLProvider> sitemapURLProviders =
			SitemapURLProviderRegistryUtil.getSitemapURLProviders();

		for (SitemapURLProvider sitemapURLProvider : sitemapURLProviders) {
			if (Validator.isNull(layoutUuid)) {
				sitemapURLProvider.visitLayoutSet(
					rootElement, layoutSet, themeDisplay);
			}
			else {
				sitemapURLProvider.visitLayout(
					rootElement, layoutUuid, layoutSet, themeDisplay);
			}
		}

		if (!rootElement.hasContent()) {
			return StringPool.BLANK;
		}

		_removeEntriesAndSize(rootElement);

		return document.asXML();
	}

	private int _getSize(Element element) {
		String string = element.asXML();

		byte[] bytes = string.getBytes();

		int offset = 0;

		String name = element.getName();

		if (name.equals("url")) {
			Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

			int availableLocalesSize = availableLocales.size();

			offset = (availableLocalesSize + 1) * _ATTRIBUTE_XHTML.length;

			offset += _ATTRIBUTE_XMLNS.length;
		}

		return bytes.length - offset;
	}

	private void _initEntriesAndSize(Element rootElement) {
		rootElement.addAttribute("entries", "0");

		int size = _getSize(rootElement);

		rootElement.addAttribute("size", String.valueOf(size));
	}

	private void _removeEntriesAndSize(Element rootElement) {
		Attribute entriesAttribute = rootElement.attribute("entries");
		Attribute sizeAttribute = rootElement.attribute("size");

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Created site map with ");

			if (entriesAttribute != null) {
				sb.append(entriesAttribute.getValue());
			}
			else {
				sb.append("no");
			}

			sb.append(" entries and size ");

			if (sizeAttribute != null) {
				int size = GetterUtil.getInteger(sizeAttribute.getValue());

				sb.append(
					TextFormatter.formatStorageSize(
						size, LocaleUtil.fromLanguageId("en_US")));
			}
			else {
				sb.append("0");
			}

			_log.debug(sb.toString());
		}

		if (entriesAttribute != null) {
			rootElement.remove(entriesAttribute);
		}

		if (sizeAttribute != null) {
			rootElement.remove(sizeAttribute);
		}
	}

	private void _removeOldestElement(Element rootElement, Element newElement) {
		int entries = GetterUtil.getInteger(
			rootElement.attributeValue("entries"));
		int size = GetterUtil.getInteger(rootElement.attributeValue("size"));

		entries++;
		size += _getSize(newElement);

		while ((entries > MAXIMUM_ENTRIES) || (size >= _MAXIMUM_SIZE)) {
			Element oldestUrlElement = rootElement.element(
				newElement.getName());

			entries--;
			size -= _getSize(oldestUrlElement);

			rootElement.remove(oldestUrlElement);
		}

		rootElement.addAttribute("entries", String.valueOf(entries));
		rootElement.addAttribute("size", String.valueOf(size));
	}

	private void _visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		if (layoutSet.isPrivateLayout()) {
			return;
		}

		String portalURL = themeDisplay.getPortalURL();

		Map<String, LayoutTypeController> layoutTypeControllers =
			LayoutTypeControllerTracker.getLayoutTypeControllers();

		for (Map.Entry<String, LayoutTypeController> entry :
				layoutTypeControllers.entrySet()) {

			LayoutTypeController layoutTypeController = entry.getValue();

			if (!layoutTypeController.isSitemapable()) {
				continue;
			}

			List<Layout> layouts = _layoutLocalService.getAllLayouts(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				entry.getKey());

			for (Layout layout : layouts) {
				if (layout.isSystem() && !layout.isTypeAssetDisplay()) {
					continue;
				}

				UnicodeProperties typeSettingsUnicodeProperties =
					layout.getTypeSettingsProperties();

				boolean sitemapInclude = GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.getProperty(
						LayoutTypePortletConstants.SITEMAP_INCLUDE),
					true);

				if (!sitemapInclude) {
					continue;
				}

				Element sitemapElement = element.addElement("sitemap");

				Element locationElement = sitemapElement.addElement("loc");

				locationElement.addText(
					StringBundler.concat(
						portalURL, _portal.getPathContext(),
						"/sitemap.xml?p_l_id=", layout.getPlid(),
						"&layoutUuid=", layout.getUuid(), "&groupId=",
						layoutSet.getGroupId(), "&privateLayout=",
						layout.isPrivateLayout()));

				_removeOldestElement(element, sitemapElement);
			}
		}
	}

	private static final byte[] _ATTRIBUTE_XHTML =
		" xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"".getBytes();

	private static final byte[] _ATTRIBUTE_XMLNS =
		" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"".getBytes();

	private static final int _MAXIMUM_SIZE = 50 * 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		SitemapImpl.class.getName());

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SAXReader _saxReader;

}