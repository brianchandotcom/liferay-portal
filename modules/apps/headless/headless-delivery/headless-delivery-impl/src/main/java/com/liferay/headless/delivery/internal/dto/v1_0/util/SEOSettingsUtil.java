/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.SEOSettings;
import com.liferay.headless.delivery.dto.v1_0.SiteMapSettings;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class SEOSettingsUtil {

	public static SEOSettings getSeoSettings(
		DTOConverterContext dtoConverterContext,
		LayoutSEOEntryLocalService layoutSEOEntryLocalService, Layout layout) {

		LayoutSEOEntry layoutSEOEntry =
			layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId());

		return new SEOSettings() {
			{
				setCustomCanonicalURL(
					() -> {
						if ((layoutSEOEntry != null) &&
							layoutSEOEntry.isCanonicalURLEnabled()) {

							return layoutSEOEntry.getCanonicalURL(
								dtoConverterContext.getLocale());
						}

						return null;
					});
				setCustomCanonicalURL_i18n(
					() -> {
						if ((layoutSEOEntry != null) &&
							layoutSEOEntry.isCanonicalURLEnabled()) {

							return LocalizedMapUtil.getI18nMap(
								dtoConverterContext.isAcceptAllLanguages(),
								layoutSEOEntry.getCanonicalURLMap());
						}

						return null;
					});
				setDescription(
					() -> layout.getDescription(
						dtoConverterContext.getLocale()));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						layout.getDescriptionMap()));
				setHtmlTitle(
					() -> layout.getTitle(dtoConverterContext.getLocale()));
				setHtmlTitle_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						layout.getTitleMap()));
				setRobots(
					() -> layout.getRobots(dtoConverterContext.getLocale()));
				setRobots_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						layout.getRobotsMap()));
				setSeoKeywords(
					() -> layout.getKeywords(dtoConverterContext.getLocale()));
				setSeoKeywords_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						dtoConverterContext.isAcceptAllLanguages(),
						layout.getKeywordsMap()));
				setSiteMapSettings(
					() -> _toSiteMapSettings(
						layout.getTypeSettingsProperties()));
			}
		};
	}

	private static SiteMapSettings _toSiteMapSettings(
		UnicodeProperties unicodeProperties) {

		String siteMapChangeFreq = unicodeProperties.getProperty(
			"sitemap-changefreq");
		String siteMapInclude = unicodeProperties.getProperty(
			"sitemap-include");
		String siteMapIncludeChildLayouts = unicodeProperties.getProperty(
			"sitemap-include-child-layouts");
		String siteMapPriority = unicodeProperties.getProperty(
			"sitemap-priority");

		if ((siteMapChangeFreq == null) && (siteMapInclude == null) &&
			(siteMapIncludeChildLayouts == null) && (siteMapPriority == null)) {

			return null;
		}

		return new SiteMapSettings() {
			{
				setChangeFrequency(
					() -> {
						if (siteMapChangeFreq == null) {
							return null;
						}

						return ChangeFrequency.create(
							StringUtil.upperCaseFirstLetter(siteMapChangeFreq));
					});

				setInclude(
					() -> {
						if (siteMapInclude == null) {
							return null;
						}

						if (siteMapInclude.equals("0")) {
							return false;
						}

						if (siteMapInclude.equals("1")) {
							return true;
						}

						return null;
					});

				setIncludeChildSitePages(
					() -> {
						if (siteMapIncludeChildLayouts == null) {
							return null;
						}

						if (siteMapIncludeChildLayouts.equals("false")) {
							return false;
						}

						if (siteMapIncludeChildLayouts.equals("true")) {
							return true;
						}

						return null;
					});

				setPagePriority(
					() -> {
						if (siteMapPriority == null) {
							return null;
						}

						try {
							return Double.parseDouble(siteMapPriority);
						}
						catch (NumberFormatException numberFormatException) {
							if (_log.isWarnEnabled()) {
								_log.warn(numberFormatException);
							}

							return null;
						}
					});
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SEOSettingsUtil.class);

}