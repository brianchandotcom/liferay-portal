/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoryUtil {

	public static List<BreadcrumbEntry> getAssetCategoriesBreadcrumbEntries(
			AssetVocabulary vocabulary, AssetCategory category,
			HttpServletRequest httpServletRequest,
			RenderResponse renderResponse)
		throws PortalException {

		if (category == null) {
			return Collections.emptyList();
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry vocabularyBreadcrumbEntry = new BreadcrumbEntry();

		vocabularyBreadcrumbEntry.setTitle(
			vocabulary.getTitle(themeDisplay.getLocale()));

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/view.jsp"
		).setNavigation(
			() -> {
				String navigation = ParamUtil.getString(
					httpServletRequest, "navigation");

				if (Validator.isNotNull(navigation)) {
					return navigation;
				}

				return null;
			}
		).setParameter(
			"vocabularyId", vocabulary.getVocabularyId()
		).buildPortletURL();

		vocabularyBreadcrumbEntry.setURL(portletURL.toString());

		breadcrumbEntries.add(vocabularyBreadcrumbEntry);

		List<AssetCategory> ancestorsCategories = category.getAncestors();

		Collections.reverse(ancestorsCategories);

		for (AssetCategory curCategory : ancestorsCategories) {
			BreadcrumbEntry categoryBreadcrumbEntry = new BreadcrumbEntry();

			categoryBreadcrumbEntry.setTitle(
				curCategory.getTitle(themeDisplay.getLocale()));

			portletURL.setParameter(
				"categoryId", String.valueOf(curCategory.getCategoryId()));

			categoryBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(categoryBreadcrumbEntry);
		}

		BreadcrumbEntry categoryBreadcrumbEntry = new BreadcrumbEntry();

		categoryBreadcrumbEntry.setTitle(
			category.getTitle(themeDisplay.getLocale()));

		breadcrumbEntries.add(categoryBreadcrumbEntry);

		return breadcrumbEntries;
	}

}