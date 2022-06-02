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

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyGroupLocalizedTitleComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
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

	public String getAddCategoryURL() throws Exception {
		long[] vocabularyIds = getVocabularyIds();

		if (vocabularyIds.length > 1) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.getAssetVocabulary(
				vocabularyIds[0]);

		if (!AssetCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				assetVocabulary.getGroupId(),
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				ActionKeys.ADD_CATEGORY) ||
			!Objects.equals(
				assetVocabulary.getGroupId(), themeDisplay.getScopeGroupId())) {

			return null;
		}

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest,
				AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_category.jsp"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setParameter(
			"groupId", assetVocabulary.getGroupId()
		).setParameter(
			"itemSelectorEventName", getEventName()
		).setParameter(
			"vocabularyId", vocabularyIds[0]
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public JSONArray getCategoriesJSONArray() throws Exception {
		JSONArray vocabulariesJSONArray = _getVocabulariesJSONArray();

		if (vocabulariesJSONArray.length() == 1) {
			return JSONUtil.put(vocabulariesJSONArray.getJSONObject(0));
		}

		return vocabulariesJSONArray;
	}

	public Map<String, Object> getData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"addCategoryURL", getAddCategoryURL()
		).put(
			"inheritSelection", _isInheritSelection()
		).put(
			"itemSelectorSaveEvent", HtmlUtil.escapeJS(getEventName())
		).put(
			"moveCategory", isMoveCategory()
		).put(
			"multiSelection", !isSingleSelect()
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"nodes", getCategoriesJSONArray()
		).put(
			"selectedCategoryIds", getSelectedCategoryIds()
		).put(
			"showSelectedCounter", showSelectedCounter()
		).build();
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
					_httpServletRequest, "selectedCategories")));

		return _selectedCategoryIds;
	}

	public long[] getVocabularyIds() {
		if (_vocabularyIds != null) {
			return _vocabularyIds;
		}

		long[] vocabularyIds = StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "vocabularyIds"), 0L);

		List<AssetVocabulary> assetVocabularies = new ArrayList<>();

		for (long vocabularyId : vocabularyIds) {
			AssetVocabulary assetVocabulary =
				AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
					vocabularyId);

			if (assetVocabulary != null) {
				assetVocabularies.add(assetVocabulary);
			}
		}

		if (assetVocabularies.isEmpty()) {
			_vocabularyIds = new long[0];

			return _vocabularyIds;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		assetVocabularies.sort(
			new AssetVocabularyGroupLocalizedTitleComparator(
				themeDisplay.getScopeGroupId(), themeDisplay.getLocale(),
				true));

		Stream<AssetVocabulary> assetVocabulariesStream =
			assetVocabularies.stream();

		_vocabularyIds = assetVocabulariesStream.mapToLong(
			assetVocabulary -> assetVocabulary.getVocabularyId()
		).toArray();

		return _vocabularyIds;
	}

	public String getVocabularyTitle(long vocabularyId) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(vocabularyId);

		StringBundler sb = new StringBundler(5);

		sb.append(
			HtmlUtil.escape(
				assetVocabulary.getTitle(themeDisplay.getLocale())));

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

	public boolean isMoveCategory() {
		if (_moveCategory != null) {
			return _moveCategory;
		}

		_moveCategory = ParamUtil.getBoolean(
			_httpServletRequest, "moveCategory");

		return _moveCategory;
	}

	public boolean isSingleSelect() {
		if (_singleSelect != null) {
			return _singleSelect;
		}

		_singleSelect = ParamUtil.getBoolean(
			_httpServletRequest, "singleSelect");

		return _singleSelect;
	}

	public boolean showSelectedCounter() {
		if (_showSelectedCounter != null) {
			return _showSelectedCounter;
		}

		_showSelectedCounter = ParamUtil.getBoolean(
			_httpServletRequest, "showSelectedCounter");

		return _showSelectedCounter;
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
			jsonArray.put(
				JSONUtil.put(
					"children",
					() -> {
						JSONArray childrenJSONArray = _getCategoriesJSONArray(
							vocabularyId, category.getCategoryId());

						if (childrenJSONArray.length() > 0) {
							return childrenJSONArray;
						}

						return null;
					}
				).put(
					"icon", "categories"
				).put(
					"id", category.getCategoryId()
				).put(
					"name", category.getTitle(themeDisplay.getLocale())
				).put(
					"nodePath", category.getPath(themeDisplay.getLocale(), true)
				).put(
					"selected",
					() -> {
						if (getSelectedCategoryIds().contains(
								String.valueOf(category.getCategoryId()))) {

							return true;
						}

						return null;
					}
				));
		}

		return jsonArray;
	}

	private JSONArray _getVocabulariesJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		boolean allowedSelectVocabularies = isAllowedSelectVocabularies();

		for (long vocabularyId : getVocabularyIds()) {
			jsonArray.put(
				JSONUtil.put(
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
				));
		}

		return jsonArray;
	}

	private boolean _isInheritSelection() {
		if (_inheritSelection != null) {
			return _inheritSelection;
		}

		_inheritSelection = ParamUtil.getBoolean(
			_httpServletRequest, "inheritSelection");

		return _inheritSelection;
	}

	private Boolean _allowedSelectVocabularies;
	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private Boolean _inheritSelection;
	private Boolean _moveCategory;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private List<String> _selectedCategoryIds;
	private Boolean _showSelectedCounter;
	private Boolean _singleSelect;
	private long[] _vocabularyIds;

}