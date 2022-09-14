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