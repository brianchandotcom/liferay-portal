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

package com.liferay.asset.categories.selector.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoriesSelectorDisplayContext {

	public AssetCategoriesSelectorDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public JSONArray getCategoriesJSONArray() throws Exception {
		JSONArray vocabulariesJSONArray = _getVocabulariesJSONArray();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (vocabulariesJSONArray.length() == 1) {
			jsonObject = vocabulariesJSONArray.getJSONObject(0);
		}
		else {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			jsonObject.put(
				"children", vocabulariesJSONArray
			).put(
				"icon", "folder"
			).put(
				"id", "0"
			).put(
				"name",
				LanguageUtil.get(themeDisplay.getLocale(), "vocabularies")
			);
		}

		jsonObject.put(
			"disabled", true
		).put(
			"expanded", true
		);

		return JSONUtil.put(jsonObject);
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectCategory");

		return _eventName;
	}

	public List<String> getSelectedCategoryIds() {
		if (_selectedCategoryIds != null) {
			return _selectedCategoryIds;
		}

		_selectedCategoryIds = Arrays.asList(
			StringUtil.split(
				ParamUtil.getString(
					_httpServletRequest, "selectedCategoryIds")));

		return _selectedCategoryIds;
	}

	public long[] getVocabularyIds() {
		if (_vocabularyIds != null) {
			return _vocabularyIds;
		}

		_vocabularyIds = StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "vocabularyIds"), 0L);

		return _vocabularyIds;
	}

	public String getVocabularyTitle(long vocabularyId) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(vocabularyId);

		StringBundler sb = new StringBundler(5);

		String title = assetVocabulary.getTitle(themeDisplay.getLocale());

		sb.append(HtmlUtil.escape(title));

		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);

		if (assetVocabulary.getGroupId() == themeDisplay.getCompanyGroupId()) {
			sb.append(LanguageUtil.get(_httpServletRequest, "global"));
		}
		else {
			Group group = GroupLocalServiceUtil.fetchGroup(
				assetVocabulary.getGroupId());

			sb.append(group.getDescriptiveName(themeDisplay.getLocale()));
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	public boolean isAllowedSelectVocabularies() {
		if (_allowedSelectVocabularies != null) {
			return _allowedSelectVocabularies;
		}

		_allowedSelectVocabularies = ParamUtil.getBoolean(
			_httpServletRequest, "allowedSelectVocabularies");

		return _allowedSelectVocabularies;
	}

	public boolean isSingleSelect() {
		if (_singleSelect != null) {
			return _singleSelect;
		}

		_singleSelect = ParamUtil.getBoolean(
			_httpServletRequest, "singleSelect");

		return _singleSelect;
	}

	private JSONArray _getCategoriesJSONArray(
			long vocabularyId, long categoryId)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<AssetCategory> categories =
			AssetCategoryServiceUtil.getVocabularyCategories(
				categoryId, vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (AssetCategory category : categories) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray children = _getCategoriesJSONArray(
				vocabularyId, category.getCategoryId());

			if (children.length() > 0) {
				jsonObject.put("children", children);
			}

			jsonObject.put(
				"icon", "categories"
			).put(
				"id", category.getCategoryId()
			).put(
				"name", category.getTitle(themeDisplay.getLocale())
			).put(
				"nodePath", category.getPath(themeDisplay.getLocale(), true)
			);

			if (getSelectedCategoryIds().contains(
					String.valueOf(category.getCategoryId()))) {

				jsonObject.put("selected", true);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getVocabulariesJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		boolean allowedSelectVocabularies = isAllowedSelectVocabularies();

		for (long vocabularyId : getVocabularyIds()) {
			JSONObject jsonObject = JSONUtil.put(
				"children", _getCategoriesJSONArray(vocabularyId, 0)
			).put(
				"disabled", !allowedSelectVocabularies
			).put(
				"icon", "vocabulary"
			).put(
				"id", vocabularyId
			).put(
				"name", getVocabularyTitle(vocabularyId)
			).put(
				"vocabulary", true
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private Boolean _allowedSelectVocabularies;
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private List<String> _selectedCategoryIds;
	private Boolean _singleSelect;
	private long[] _vocabularyIds;

}