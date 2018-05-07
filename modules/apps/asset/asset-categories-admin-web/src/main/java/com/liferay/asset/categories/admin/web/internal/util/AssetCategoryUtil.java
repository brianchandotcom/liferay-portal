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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoryUtil {

	public static void addPortletBreadcrumbEntry(
			AssetVocabulary vocabulary, AssetCategory category,
			HttpServletRequest request, RenderResponse renderResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");

		PortalUtil.addPortletBreadcrumbEntry(
			request, LanguageUtil.get(request, "vocabularies"),
			portletURL.toString());

		if (category == null) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, vocabulary.getTitle(themeDisplay.getLocale()), null);

			return;
		}

		portletURL.setParameter("mvcPath", "/view_categories.jsp");

		String navigation = ParamUtil.getString(request, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter("navigation", navigation);
		}

		portletURL.setParameter(
			"vocabularyId", String.valueOf(vocabulary.getVocabularyId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, vocabulary.getTitle(themeDisplay.getLocale()),
			portletURL.toString());

		List<AssetCategory> ancestorsCategories = category.getAncestors();

		Collections.reverse(ancestorsCategories);

		for (AssetCategory curCategory : ancestorsCategories) {
			portletURL.setParameter(
				"categoryId", String.valueOf(curCategory.getCategoryId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, curCategory.getTitle(themeDisplay.getLocale()),
				portletURL.toString());
		}

		PortalUtil.addPortletBreadcrumbEntry(
			request, category.getTitle(themeDisplay.getLocale()), null);
	}

}