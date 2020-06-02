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

package com.liferay.asset.taglib.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoriesNavigationDisplayContext {

	public AssetCategoriesNavigationDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_vocabularyIds = (long[])httpServletRequest.getAttribute(
			"liferay-asset:asset-tags-navigation:vocabularyIds");
	}

	public String buildVocabularyNavigation(AssetVocabulary vocabulary)
		throws Exception {

		List<AssetCategory> categories =
			AssetCategoryServiceUtil.getVocabularyRootCategories(
				vocabulary.getGroupId(), vocabulary.getVocabularyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (categories.isEmpty()) {
			return null;
		}

		StringBundler sb = new StringBundler();

		sb.append("<div class=\"lfr-asset-category-list-container\">");
		sb.append("<ul class=\"lfr-asset-category-list\">");

		_buildCategoriesNavigation(categories, sb);

		sb.append("</ul></div>");

		return sb.toString();
	}

	public JSONArray getCategoriesJSONArray() throws PortalException {
		JSONArray categoriesJSONArray = JSONFactoryUtil.createJSONArray();

		for (AssetVocabulary vocabulary : getVocabularies()) {
			List<AssetCategory> categories =
				AssetCategoryServiceUtil.getVocabularyRootCategories(
					vocabulary.getGroupId(), vocabulary.getVocabularyId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (AssetCategory category : categories) {
				categoriesJSONArray.put(_getCategoryJSONObject(category));
			}
		}

		return categoriesJSONArray;
	}

	public long getCategoryId() {
		if (_categoryId != null) {
			return _categoryId;
		}

		_categoryId = ParamUtil.getLong(_httpServletRequest, "categoryId");

		return _categoryId;
	}

	public List<AssetVocabulary> getVocabularies() throws PortalException {
		if (_vocabularies != null) {
			return _vocabularies;
		}

		if (_vocabularyIds == null) {
			_vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					_themeDisplay.getScopeGroupId()));

			return _vocabularies;
		}

		List<AssetVocabulary> vocabularies = new ArrayList<>();

		for (long vocabularyId : _vocabularyIds) {
			AssetVocabulary vocabulary =
				AssetVocabularyServiceUtil.fetchVocabulary(vocabularyId);

			if (vocabulary != null) {
				vocabularies.add(vocabulary);
			}
		}

		_vocabularies = vocabularies;

		return _vocabularies;
	}

	private void _buildCategoriesNavigation(
			List<AssetCategory> categories, StringBundler sb)
		throws Exception {

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("categoryId", StringPool.BLANK);

		String originalPortletURLString = portletURL.toString();

		for (AssetCategory category : categories) {
			List<AssetCategory> categoriesChildren =
				AssetCategoryServiceUtil.getChildCategories(
					category.getCategoryId());

			sb.append("<li class=\"tree-node\"><span>");

			if (getCategoryId() == category.getCategoryId()) {
				sb.append("<a class=\"tag-selected\" href=\"");
				sb.append(HtmlUtil.escape(originalPortletURLString));
			}
			else {
				portletURL.setParameter(
					"categoryId", String.valueOf(category.getCategoryId()));

				sb.append("<a href=\"");
				sb.append(HtmlUtil.escape(portletURL.toString()));
			}

			sb.append("\">");
			sb.append(
				HtmlUtil.escape(category.getTitle(_themeDisplay.getLocale())));
			sb.append("</a>");
			sb.append("</span>");

			if (!categoriesChildren.isEmpty()) {
				sb.append("<ul>");

				_buildCategoriesNavigation(categoriesChildren, sb);

				sb.append("</ul>");
			}

			sb.append("</li>");
		}
	}

	private JSONObject _getCategoryJSONObject(AssetCategory category)
		throws PortalException {

		return JSONUtil.put(
			"categoryId", category.getCategoryId()
		).put(
			"children", _getChildCategoriesJSONArray(category.getCategoryId())
		).put(
			"id", category.getCategoryId()
		).put(
			"name",
			HtmlUtil.escape(category.getTitle(_themeDisplay.getLocale()))
		).put(
			"url", _getPortletURL(category.getCategoryId())
		);
	}

	private JSONArray _getChildCategoriesJSONArray(long categoryId)
		throws PortalException {

		JSONArray childCategoriesJSONArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> childCategories =
			AssetCategoryServiceUtil.getChildCategories(categoryId);

		for (AssetCategory childCategory : childCategories) {
			childCategoriesJSONArray.put(_getCategoryJSONObject(childCategory));
		}

		return childCategoriesJSONArray;
	}

	private String _getPortletURL(long categoryId) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		if (getCategoryId() == categoryId) {
			portletURL.setParameter("categoryId", StringPool.BLANK);
		}
		else {
			portletURL.setParameter("categoryId", String.valueOf(categoryId));
		}

		return HtmlUtil.escape(portletURL.toString());
	}

	private Long _categoryId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private List<AssetVocabulary> _vocabularies;
	private long[] _vocabularyIds;

}