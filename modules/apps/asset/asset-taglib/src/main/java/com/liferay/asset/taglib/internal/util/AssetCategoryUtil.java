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

package com.liferay.asset.taglib.internal.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoryUtil {

	public static final String CATEGORY_SEPARATOR = "_CATEGORY_";

	public static void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest httpServletRequest,
			PortletURL portletURL)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean portletBreadcrumbEntry = false;

		if (Validator.isNotNull(portletDisplay.getId()) &&
			!portletDisplay.isFocused()) {

			portletBreadcrumbEntry = true;
		}

		addPortletBreadcrumbEntries(
			assetCategoryId, httpServletRequest, portletURL,
			portletBreadcrumbEntry);
	}

	public static void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest httpServletRequest,
			PortletURL portletURL, boolean portletBreadcrumbEntry)
		throws Exception {

		AssetCategory assetCategory =
			AssetCategoryLocalServiceUtil.fetchAssetCategory(assetCategoryId);

		if (assetCategory == null) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<AssetCategory> ancestorCategories = assetCategory.getAncestors();

		Collections.reverse(ancestorCategories);

		for (AssetCategory ancestorCategory : ancestorCategories) {
			portletURL.setParameter(
				"categoryId", String.valueOf(ancestorCategory.getCategoryId()));

			PortalUtil.addPortletBreadcrumbEntry(
				httpServletRequest,
				ancestorCategory.getTitle(themeDisplay.getLocale()),
				portletURL.toString(), null, portletBreadcrumbEntry);
		}

		portletURL.setParameter("categoryId", String.valueOf(assetCategoryId));

		PortalUtil.addPortletBreadcrumbEntry(
			httpServletRequest,
			assetCategory.getTitle(themeDisplay.getLocale()),
			portletURL.toString(), null, portletBreadcrumbEntry);
	}

	public static long[] filterCategoryIds(
		long vocabularyId, long[] categoryIds) {

		List<Long> filteredCategoryIds = new ArrayList<>();

		for (long categoryId : categoryIds) {
			AssetCategory category =
				AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

			if (category == null) {
				continue;
			}

			if (category.getVocabularyId() == vocabularyId) {
				filteredCategoryIds.add(category.getCategoryId());
			}
		}

		return ArrayUtil.toArray(filteredCategoryIds.toArray(new Long[0]));
	}

	public static String[] getCategoryIdsTitles(
		String categoryIds, String categoryNames, long vocabularyId,
		ThemeDisplay themeDisplay) {

		if (Validator.isNotNull(categoryIds)) {
			long[] categoryIdsArray = GetterUtil.getLongValues(
				StringUtil.split(categoryIds));

			if (vocabularyId > 0) {
				categoryIdsArray = filterCategoryIds(
					vocabularyId, categoryIdsArray);
			}

			categoryIds = StringPool.BLANK;
			categoryNames = StringPool.BLANK;

			if (categoryIdsArray.length > 0) {
				StringBundler categoryIdsSB = new StringBundler(
					categoryIdsArray.length * 2);
				StringBundler categoryNamesSB = new StringBundler(
					categoryIdsArray.length * 2);

				for (long categoryId : categoryIdsArray) {
					AssetCategory category =
						AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

					if (category == null) {
						continue;
					}

					categoryIdsSB.append(categoryId);
					categoryIdsSB.append(StringPool.COMMA);

					categoryNamesSB.append(
						category.getTitle(themeDisplay.getLocale()));
					categoryNamesSB.append(CATEGORY_SEPARATOR);
				}

				if (categoryIdsSB.index() > 0) {
					categoryIdsSB.setIndex(categoryIdsSB.index() - 1);
					categoryNamesSB.setIndex(categoryNamesSB.index() - 1);

					categoryIds = categoryIdsSB.toString();
					categoryNames = categoryNamesSB.toString();
				}
			}
		}

		return new String[] {categoryIds, categoryNames};
	}

}