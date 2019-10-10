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

package com.liferay.layout.seo.internal;

import com.liferay.layout.seo.internal.configuration.LayoutSEOCompanyConfiguration;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = LayoutSEOLinkManager.class)
public class LayoutSEOLinkManagerImpl implements LayoutSEOLinkManager {

	@Override
	public LayoutSEOLink getCanonicalLayoutSEOLink(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		return new LayoutSEOLinkImpl(
			_html.escapeAttribute(
				_getCanonicalURL(layout, locale, canonicalURL, alternateURLs)),
			null, LayoutSEOLink.Relationship.CANONICAL);
	}

	@Override
	public List<LayoutSEOLink> getLocalizedLayoutSEOLinks(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		List<LayoutSEOLink> layoutSEOLinks = new ArrayList<>(
			alternateURLs.size() + 2);

		layoutSEOLinks.add(
			getCanonicalLayoutSEOLink(
				layout, locale, canonicalURL, alternateURLs));

		alternateURLs.forEach(
			(urlLocale, url) -> layoutSEOLinks.add(
				new LayoutSEOLinkImpl(
					_html.escapeAttribute(url),
					LocaleUtil.toW3cLanguageId(urlLocale),
					LayoutSEOLink.Relationship.ALTERNATE)));

		String defaultLocaleURL = alternateURLs.get(LocaleUtil.getDefault());

		if (defaultLocaleURL == null) {
			return layoutSEOLinks;
		}

		layoutSEOLinks.add(
			new LayoutSEOLinkImpl(
				_html.escapeAttribute(defaultLocaleURL), "x-default",
				LayoutSEOLink.Relationship.ALTERNATE));

		return layoutSEOLinks;
	}

	@Override
	public String getPageTitle(
			Layout layout, String portletId, String tilesTitle,
			ListMergeable<String> titleListMergeable,
			ListMergeable<String> subtitleListMergeable, String companyName,
			Locale locale)
		throws PortalException {

		String title = _getPageTitle(
			layout, portletId, tilesTitle, titleListMergeable,
			subtitleListMergeable, locale);

		if (Validator.isNotNull(title)) {
			return _html.escape(title + _getSiteName(layout, companyName));
		}

		return _html.escape(companyName);
	}

	@Override
	public boolean isOpenGraphEnabled(Layout layout) throws PortalException {
		LayoutSEOCompanyConfiguration layoutSEOCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LayoutSEOCompanyConfiguration.class, layout.getCompanyId());

		return layoutSEOCompanyConfiguration.enableOpenGraph();
	}

	private String _getCanonicalURL(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws ConfigurationException {

		String layoutCanonicalURL = _getLayoutCanonicalURL(locale, layout);

		if (Validator.isNotNull(layoutCanonicalURL)) {
			return layoutCanonicalURL;
		}

		LayoutSEOCompanyConfiguration layoutSEOCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				LayoutSEOCompanyConfiguration.class, layout.getCompanyId());

		if (Objects.equals(
				layoutSEOCompanyConfiguration.canonicalURL(),
				"default-language-url")) {

			return canonicalURL;
		}

		return alternateURLs.getOrDefault(locale, canonicalURL);
	}

	private String _getLayoutCanonicalURL(Locale locale, Layout layout) {
		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());

		if ((layoutSEOEntry == null) ||
			!layoutSEOEntry.isCanonicalURLEnabled()) {

			return StringPool.BLANK;
		}

		return layoutSEOEntry.getCanonicalURL(locale);
	}

	private String _getPageTitle(
			Layout layout, String portletId, String tilesTitle,
			ListMergeable<String> titleListMergeable,
			ListMergeable<String> subtitleListMergeable, Locale locale)
		throws PortalException {

		if (Validator.isNotNull(portletId) && layout.isSystem() &&
			!layout.isTypeControlPanel() &&
			StringUtil.equals(layout.getFriendlyURL(), "/manage")) {

			return _portal.getPortletTitle(portletId, locale);
		}

		if (Validator.isNotNull(tilesTitle)) {
			return _language.get(locale, tilesTitle);
		}

		Group group = layout.getGroup();

		if (subtitleListMergeable == null) {
			return _getTitle(group, layout, titleListMergeable, locale);
		}

		return subtitleListMergeable.mergeToString(StringPool.SPACE) + " - " +
			_getTitle(group, layout, titleListMergeable, locale);
	}

	private String _getSiteName(Layout layout, String companyName)
		throws PortalException {

		Group group = layout.getGroup();

		if (group.isLayoutPrototype() ||
			StringUtil.equals(companyName, group.getDescriptiveName())) {

			return " - " + _html.escape(companyName);
		}

		return StringBundler.concat(
			" - ", _html.escape(group.getDescriptiveName()), " - ",
			_html.escape(companyName));
	}

	private String _getTitle(
			Group group, Layout layout,
			ListMergeable<String> titleListMergeable, Locale locale)
		throws PortalException {

		if (group.isLayoutPrototype()) {
			return group.getDescriptiveName(locale);
		}

		if (titleListMergeable != null) {
			return titleListMergeable.mergeToString(StringPool.SPACE);
		}

		return layout.getHTMLTitle(_language.getLanguageId(locale));
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private Portal _portal;

}