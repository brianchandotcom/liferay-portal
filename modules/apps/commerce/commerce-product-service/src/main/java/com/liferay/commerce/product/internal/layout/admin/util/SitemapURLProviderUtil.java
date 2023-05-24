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

package com.liferay.commerce.product.internal.layout.admin.util;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.servlet.I18nServlet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian I. Kim
 */
public class SitemapURLProviderUtil {

	protected static Map<Locale, String> getAlternateFriendlyURLs(
		String currentSiteURL, long friendlyURLEntryId,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService, long groupId,
		Language language, String urlSeparator) {

		Map<Locale, String> alternateFriendlyURLs = new HashMap<>();

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
					friendlyURLEntryId)) {

			if (_isAvailableLocale(
					groupId, language,
					friendlyURLEntryLocalization.getLanguageId())) {

				alternateFriendlyURLs.put(
					LocaleUtil.fromLanguageId(
						friendlyURLEntryLocalization.getLanguageId()),
					StringBundler.concat(
						currentSiteURL, urlSeparator,
						friendlyURLEntryLocalization.getUrlTitle()));
			}
		}

		return alternateFriendlyURLs;
	}

	protected static ThemeDisplay updateThemeDisplay(
		Language language, Locale locale, ThemeDisplay themeDisplay) {

		String i18nPath = null;

		Set<String> languageIds = I18nServlet.getLanguageIds();

		int localePrependFriendlyURLStyle = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(),
			PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE);

		if ((languageIds.contains(CharPool.SLASH + locale.toString()) &&
			 (localePrependFriendlyURLStyle == 1) &&
			 !locale.equals(LocaleUtil.getDefault())) ||
			(localePrependFriendlyURLStyle == 2)) {

			i18nPath = _getI18nPath(language, locale);
		}

		themeDisplay.setI18nLanguageId(locale.toString());
		themeDisplay.setI18nPath(i18nPath);
		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		themeDisplay.setLocale(locale);

		return themeDisplay;
	}

	private static String _getI18nPath(Language language, Locale locale) {
		Locale defaultLocale = language.getLocale(locale.getLanguage());

		if (LocaleUtil.equals(defaultLocale, locale)) {
			return StringPool.SLASH + defaultLocale.getLanguage();
		}

		return StringPool.SLASH + locale.toLanguageTag();
	}

	private static boolean _isAvailableLocale(
		long groupId, Language language, String languageId) {

		Set<Locale> siteAvailableLocales = language.getAvailableLocales(
			groupId);

		if (siteAvailableLocales.contains(
				LocaleUtil.fromLanguageId(languageId))) {

			return true;
		}

		return false;
	}

}