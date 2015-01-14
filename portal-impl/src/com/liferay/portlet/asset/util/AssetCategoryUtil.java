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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

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

	public static void addPortletBreadcrumbEntry(
			AssetVocabulary vocabulary, AssetCategory category,
			HttpServletRequest request, RenderResponse renderResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcPath", "/html/portlet/asset_category_admin/view.jsp");

		PortalUtil.addPortletBreadcrumbEntry(
			request, LanguageUtil.get(request, "vocabularies"),
			portletURL.toString());

		if (category == null) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, vocabulary.getTitle(themeDisplay.getLocale()), null);

			return;
		}

		portletURL.setParameter(
			"mvcPath",
			"/html/portlet/asset_category_admin/view_categories.jsp");

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

	public static List<AssetCategory> getCategories(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<AssetCategory> categories = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long categoryId = GetterUtil.getLong(
				document.get(Field.ASSET_CATEGORY_ID));

			AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
				categoryId);

			if (category == null) {
				categories = null;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					AssetCategory.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (categories != null) {
				categories.add(category);
			}
		}

		return categories;
	}

}