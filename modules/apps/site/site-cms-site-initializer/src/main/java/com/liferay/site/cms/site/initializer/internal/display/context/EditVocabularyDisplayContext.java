/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Noor Najjar
 */
public class EditVocabularyDisplayContext {

	public EditVocabularyDisplayContext(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"backURL",
			PortalUtil.getLayoutFullURL(
				LayoutLocalServiceUtil.getLayoutByFriendlyURL(
					_themeDisplay.getScopeGroupId(), false,
					"/categorization/view_vocabularies"),
				_themeDisplay)
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(_themeDisplay.getSiteDefaultLocale())
		).put(
			"locales",
			JSONUtil.toJSONArray(
				LanguageUtil.getCompanyAvailableLocales(
					_themeDisplay.getCompanyId()),
				locale -> {
					String w3cLanguageId = LocaleUtil.toW3cLanguageId(locale);

					return JSONUtil.put(
						"id", LocaleUtil.toLanguageId(locale)
					).put(
						"label", w3cLanguageId
					).put(
						"name", locale.getDisplayName()
					).put(
						"symbol", StringUtil.toLowerCase(w3cLanguageId)
					);
				})
		).put(

			"spritemap", _themeDisplay.getPathThemeSpritemap()
		).build();
	}

	private final ThemeDisplay _themeDisplay;

}
