/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetcategoryadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iliyan Peychev
 * @author Julio Camarero
 */
public class ActionUtil {

	public static void getCategory(HttpServletRequest request)
		throws Exception {

		long categoryId = ParamUtil.getLong(request, "categoryId");

		AssetCategory category = null;

		if (categoryId > 0) {
			category = AssetCategoryServiceUtil.getCategory(categoryId);
		}

		request.setAttribute(WebKeys.ASSET_CATEGORY, category);
	}

	public static void getCategory(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getCategory(request);
	}

	public static void getParentCategory(HttpServletRequest request)
		throws Exception {

		long parentCategoryId = ParamUtil.getLong(request, "parentCategoryId");

		AssetCategory parentCategory = null;

		if (parentCategoryId > 0) {
			parentCategory = AssetCategoryServiceUtil.getCategory(
					parentCategoryId);
		}

		request.setAttribute(WebKeys.ASSET_CATEGORY_PARENT, parentCategory);
	}

	public static void getParentCategory(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
				portletRequest);

		getParentCategory(request);
	}

	public static void getVocabularies(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				themeDisplay.getScopeGroupId());

		request.setAttribute(WebKeys.ASSET_VOCABULARIES, vocabularies);
	}

	public static void getVocabularies(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getVocabularies(request);
	}

	public static void getVocabulary(HttpServletRequest request)
		throws Exception {

		long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

		AssetVocabulary vocabulary = null;

		if (vocabularyId > 0) {
			vocabulary = AssetVocabularyServiceUtil.getVocabulary(vocabularyId);
		}

		request.setAttribute(WebKeys.ASSET_VOCABULARY, vocabulary);
	}

	public static void getVocabulary(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getVocabulary(request);
	}

}