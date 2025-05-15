/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Pei-Jung Lan
 */
public class CategorizationBreadcrumbUtil {

	public static JSONArray getBreadcrumbsJSONArray(
			boolean activeLastItem, long assetVocabularyId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"active", false
			).put(
				"href",
				() -> PortalUtil.getLayoutFullURL(
					LayoutLocalServiceUtil.getLayoutByFriendlyURL(
						themeDisplay.getScopeGroupId(), false,
						"/categorization/view_vocabularies"),
					themeDisplay)
			).put(
				"label",
				LanguageUtil.get(themeDisplay.getLocale(), "categorization")
			));

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.getAssetVocabulary(
				assetVocabularyId);

		return jsonArray.put(
			JSONUtil.put(
				"active", activeLastItem
			).put(
				"href",
				() -> {
					if (activeLastItem) {
						return null;
					}

					return HttpComponentsUtil.addParameter(
						PortalUtil.getLayoutFullURL(
							LayoutLocalServiceUtil.getLayoutByFriendlyURL(
								themeDisplay.getScopeGroupId(), false,
								"/categorization/view_categories"),
							themeDisplay),
						"vocabularyId", assetVocabularyId);
				}
			).put(
				"label", assetVocabulary.getName()
			));
	}

	public static JSONArray getUsagesBreadcrumbsJSONArray(
			long categoryId, ThemeDisplay themeDisplay)
		throws PortalException {

		AssetCategory assetCategory =
			AssetCategoryLocalServiceUtil.getAssetCategory(categoryId);

		return getBreadcrumbsJSONArray(
			false, assetCategory.getVocabularyId(), themeDisplay
		).put(
			JSONUtil.put(
				"active", true
			).put(
				"label",
				LanguageUtil.format(
					themeDisplay.getLocale(), "x-usages",
					assetCategory.getName())
			)
		);
	}

}