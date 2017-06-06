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

package com.liferay.asset.categories.selector.web.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoriesSelectorDisplayContext {

	public AssetCategoriesSelectorDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public JSONArray getCategoriesJSONArray() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = _getCategoriesJSONArray();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("children", jsonArray);
		jsonObject.put("disabled", true);
		jsonObject.put("expanded", true);
		jsonObject.put("icon", "folder");
		jsonObject.put("id", "0");
		jsonObject.put(
			"name", LanguageUtil.get(themeDisplay.getLocale(), "vocabularies"));

		JSONArray rootJSONArray = JSONFactoryUtil.createJSONArray();

		rootJSONArray.put(jsonObject);

		return rootJSONArray;
	}

	public long getCategoryId() {
		if (_categoryId != 0) {
			return _categoryId;
		}

		_categoryId = ParamUtil.getLong(_request, "categoryId");

		return _categoryId;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_request, "eventName",
			_renderResponse.getNamespace() + "selectCategory");

		return _eventName;
	}

	public String getSelectedCategories() {
		if (_selectedCategories != null) {
			return _selectedCategories;
		}

		_selectedCategories = ParamUtil.getString(
			_request, "selectedCategories");

		return _selectedCategories;
	}

	public String getType() {
		if (_type != null) {
			return _type;
		}

		if (!isAllowedSelectVocabularies()) {
			_type = "io";

			return _type;
		}

		_type = "check";

		if (isSingleSelect()) {
			_type = "radio";
		}

		return _type;
	}

	public long[] getVocabularyIds() {
		if (_vocabularyIds != null) {
			return _vocabularyIds;
		}

		_vocabularyIds = StringUtil.split(
			ParamUtil.getString(_request, "vocabularyIds"), 0L);

		return _vocabularyIds;
	}

	public String getVocabularyTitle(long vocabularyId) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(vocabularyId);

		StringBundler sb = new StringBundler(4);

		String title = assetVocabulary.getTitle(themeDisplay.getLocale());

		sb.append(HtmlUtil.escape(title));

		sb.append(StringPool.OPEN_PARENTHESIS);

		if (assetVocabulary.getGroupId() == themeDisplay.getCompanyGroupId()) {
			sb.append(LanguageUtil.get(_request, "global"));
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
			_request, "allowedSelectVocabularies");

		return _allowedSelectVocabularies;
	}

	public boolean isSingleSelect() {
		if (_singleSelect != null) {
			return _singleSelect;
		}

		_singleSelect = ParamUtil.getBoolean(_request, "singleSelect");

		return _singleSelect;
	}

	private JSONArray _getCategoriesJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		boolean allowedSelectVocabularies = ParamUtil.getBoolean(
			_request, "allowedSelectVocabularies");

		for (long vocabularyId : getVocabularyIds()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"children", _getCategoriesJSONArray(vocabularyId, 0));
			jsonObject.put("disabled", !allowedSelectVocabularies);
			jsonObject.put("icon", "folder");
			jsonObject.put("id", vocabularyId);
			jsonObject.put("name", getVocabularyTitle(vocabularyId));
			jsonObject.put("vocabulary", true);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getCategoriesJSONArray(
			long vocabularyId, long categoryId)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

			jsonObject.put("icon", "page");
			jsonObject.put("id", category.getCategoryId());
			jsonObject.put("name", category.getTitle(themeDisplay.getLocale()));

			if (getSelectedCategories().contains(
					String.valueOf(category.getCategoryId()))) {

				jsonObject.put("selected", true);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private Boolean _allowedSelectVocabularies;
	private long _categoryId;
	private String _eventName;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private String _selectedCategories;
	private Boolean _singleSelect;
	private String _type;
	private long[] _vocabularyIds;

}