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
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.AUIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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

		_hidePortletWhenEmpty = GetterUtil.getBoolean(
			(String)_httpServletRequest.getAttribute(
				"liferay-asset:asset-tags-navigation:hidePortletWhenEmpty"));
		_vocabularyIds = (long[])httpServletRequest.getAttribute(
			"liferay-asset:asset-tags-navigation:vocabularyIds");
	}

	public long getCategoryId() {
		if (_categoryId != null) {
			return _categoryId;
		}

		_categoryId = ParamUtil.getLong(_httpServletRequest, "categoryId");

		return _categoryId;
	}

	public Map<String, Object> getData() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"namespace", getNamespace()
		).put(
			"selectedCategoryId", getCategoryId()
		).put(
			"vocabularies", _getVocabulariesJSONArray()
		).build();
	}

	public String getNamespace() {
		if (_namespace != null) {
			return _namespace;
		}

		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		_namespace = AUIUtil.getNamespace(portletRequest, portletResponse);

		if (Validator.isNull(_namespace)) {
			_namespace = AUIUtil.getNamespace(_httpServletRequest);
		}

		return _namespace;
	}

	public List<AssetVocabulary> getVocabularies() throws PortalException {
		if (_vocabularies != null) {
			return _vocabularies;
		}

		if (_vocabularyIds == null) {
			_vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
				SiteConnectedGroupGroupProviderUtil.
					getCurrentAndAncestorSiteAndDepotGroupIds(
						_themeDisplay.getScopeGroupId()),
				new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC});

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

	public boolean hasCategories() throws PortalException {
		JSONArray vocabulariesJSONArray = _getVocabulariesJSONArray();

		if (vocabulariesJSONArray.length() > 0) {
			return true;
		}

		return false;
	}

	public boolean hidePortletWhenEmpty() {
		return _hidePortletWhenEmpty;
	}

	private JSONArray _getCategoriesJSONArray(long groupId, long vocabularyId)
		throws PortalException {

		JSONArray categoriesJSONArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> categories =
			AssetCategoryServiceUtil.getVocabularyRootCategories(
				groupId, vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (AssetCategory category : categories) {
			categoriesJSONArray.put(_getCategoryJSONObject(category));
		}

		return categoriesJSONArray;
	}

	private JSONObject _getCategoryJSONObject(AssetCategory category)
		throws PortalException {

		return JSONUtil.put(
			"categoryId", category.getCategoryId()
		).put(
			"children", _getChildCategoriesJSONArray(category.getCategoryId())
		).put(
			"icon", "categories"
		).put(
			"id", category.getCategoryId()
		).put(
			"name",
			HtmlUtil.escape(category.getTitle(_themeDisplay.getLocale()))
		).put(
			"url", _getPortletURL(category.getCategoryId())
		).put(
			"vocabularyId", category.getVocabularyId()
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

	private JSONArray _getVocabulariesJSONArray() throws PortalException {
		if (_vocabulariesJSONArray != null) {
			return _vocabulariesJSONArray;
		}

		_vocabulariesJSONArray = JSONFactoryUtil.createJSONArray();

		for (AssetVocabulary vocabulary : getVocabularies()) {
			JSONArray categoriesJSONArray = _getCategoriesJSONArray(
				vocabulary.getGroupId(), vocabulary.getVocabularyId());

			if (categoriesJSONArray.length() <= 0) {
				continue;
			}

			_vocabulariesJSONArray.put(
				JSONUtil.put(
					"children", categoriesJSONArray
				).put(
					"disabled", true
				).put(
					"icon", "vocabulary"
				).put(
					"id", vocabulary.getVocabularyId()
				).put(
					"name",
					HtmlUtil.escape(
						vocabulary.getTitle(_themeDisplay.getLocale()))
				));
		}

		return _vocabulariesJSONArray;
	}

	private Long _categoryId;
	private final boolean _hidePortletWhenEmpty;
	private final HttpServletRequest _httpServletRequest;
	private String _namespace;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private List<AssetVocabulary> _vocabularies;
	private JSONArray _vocabulariesJSONArray;
	private long[] _vocabularyIds;

}