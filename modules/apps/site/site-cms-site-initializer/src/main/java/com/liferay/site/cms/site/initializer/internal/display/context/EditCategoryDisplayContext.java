/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Cheryl Tang
 */
public class EditCategoryDisplayContext {

	public EditCategoryDisplayContext(
		HttpServletRequest httpServletRequest,
		LayoutLocalService layoutLocalService, Language language,
		Portal portal) {

		_httpServletRequest = httpServletRequest;
		_layoutLocalService = layoutLocalService;
		_language = language;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getCategoryByCategoryIdAPIURL() {
		return "/o/headless-admin-taxonomy/v1.0/taxonomy-categories/" +
			getCategoryId();
	}

	public String getCategoryByVocabularyIdAPIURL() {
		return StringBundler.concat(
			"/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/",
			getVocabularyId(), "/taxonomy-categories");
	}

	public long getCategoryId() {
		if (_categoryId != null) {
			return _categoryId;
		}

		_categoryId = ParamUtil.getLong(_httpServletRequest, "categoryId");

		return _categoryId;
	}

	public String getCategoryPermissionsAPIURL() {
		if (getCategoryId() == 0) {
			return "/o/headless-admin-taxonomy/v1.0/taxonomy-categories" +
				"/{taxonomyCategoryId}/permissions";
		}

		return StringBundler.concat(
			"/o/headless-admin-taxonomy/v1.0/taxonomy-categories/",
			getCategoryId(), "/permissions");
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"backURL",
			HttpComponentsUtil.addParameter(
				_portal.getLayoutFullURL(
					_layoutLocalService.getLayoutByFriendlyURL(
						_themeDisplay.getScopeGroupId(), false,
						"/categorization/view_categories"),
					_themeDisplay),
				"vocabularyId", getVocabularyId())
		).put(
			"categoryByCategoryIdAPIURL", getCategoryByCategoryIdAPIURL()
		).put(
			"categoryByVocabularyIdAPIURL", getCategoryByVocabularyIdAPIURL()
		).put(
			"categoryPermissionsAPIURL", getCategoryPermissionsAPIURL()
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(_themeDisplay.getSiteDefaultLocale())
		).put(
			"isCreateNew", getCategoryId() == 0
		).put(
			"locales",
			JSONUtil.toJSONArray(
				_language.getCompanyAvailableLocales(
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
		).put(
			"vocabularyId", getVocabularyId()
		).build();
	}

	public long getVocabularyId() {
		if (_vocabularyId != null) {
			return _vocabularyId;
		}

		_vocabularyId = ParamUtil.getLong(_httpServletRequest, "vocabularyId");

		return _vocabularyId;
	}

	private Long _categoryId;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final ThemeDisplay _themeDisplay;
	private Long _vocabularyId;

}