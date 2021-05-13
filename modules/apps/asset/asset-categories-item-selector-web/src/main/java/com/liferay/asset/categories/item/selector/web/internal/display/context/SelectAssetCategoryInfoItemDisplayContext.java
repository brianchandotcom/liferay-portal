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

package com.liferay.asset.categories.item.selector.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyGroupLocalizedTitleComparator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectAssetCategoryInfoItemDisplayContext {

	public SelectAssetCategoryInfoItemDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getCategoriesJSONArray() throws Exception {
		JSONArray vocabulariesJSONArray = _getVocabulariesJSONArray();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (vocabulariesJSONArray.length() == 1) {
			jsonObject = vocabulariesJSONArray.getJSONObject(0);
		}
		else {
			jsonObject.put(
				"children", vocabulariesJSONArray
			).put(
				"icon", "folder"
			).put(
				"id", "0"
			).put(
				"name",
				LanguageUtil.get(_themeDisplay.getLocale(), "vocabularies")
			);
		}

		jsonObject.put(
			"disabled", true
		).put(
			"expanded", true
		).put(
			"vocabulary", true
		);

		return JSONUtil.put(jsonObject);
	}

	public Map<String, Object> getData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"itemSelectedEventName", _itemSelectedEventName
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"nodes", getCategoriesJSONArray()
		).build();
	}

	public List<Long> getVocabularyIds() {
		if (_vocabularyIds != null) {
			return _vocabularyIds;
		}

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				new long[] {
					_themeDisplay.getCompanyGroupId(),
					_themeDisplay.getScopeGroupId()
				},
				new int[] {AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC});

		if (assetVocabularies.isEmpty()) {
			_vocabularyIds = Collections.emptyList();

			return _vocabularyIds;
		}

		ListUtil.sort(
			assetVocabularies,
			new AssetVocabularyGroupLocalizedTitleComparator(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getLocale(),
				true));

		_vocabularyIds = ListUtil.toList(
			assetVocabularies, AssetVocabulary.VOCABULARY_ID_ACCESSOR);

		return _vocabularyIds;
	}

	public String getVocabularyTitle(long vocabularyId) throws PortalException {
		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(vocabularyId);

		StringBundler sb = new StringBundler(5);

		sb.append(
			HtmlUtil.escape(
				assetVocabulary.getTitle(_themeDisplay.getLocale())));

		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);

		if (assetVocabulary.getGroupId() == _themeDisplay.getCompanyGroupId()) {
			sb.append(LanguageUtil.get(_httpServletRequest, "global"));
		}
		else {
			Group group = GroupLocalServiceUtil.fetchGroup(
				assetVocabulary.getGroupId());

			sb.append(group.getDescriptiveName(_themeDisplay.getLocale()));
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private JSONArray _getCategoriesJSONArray(
			long vocabularyId, long categoryId)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> categories =
			AssetCategoryServiceUtil.getVocabularyCategories(
				categoryId, vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (AssetCategory category : categories) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray childrenJSONArray = _getCategoriesJSONArray(
				vocabularyId, category.getCategoryId());

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			jsonObject.put(
				"className", AssetCategory.class.getName()
			).put(
				"classNameId",
				PortalUtil.getClassNameId(AssetCategory.class.getName())
			).put(
				"icon", "categories"
			).put(
				"id", category.getCategoryId()
			).put(
				"name", category.getTitle(_themeDisplay.getLocale())
			).put(
				"nodePath", category.getPath(_themeDisplay.getLocale(), true)
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getVocabulariesJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (long vocabularyId : getVocabularyIds()) {
			jsonArray.put(
				JSONUtil.put(
					"children", _getCategoriesJSONArray(vocabularyId, 0)
				).put(
					"disabled", true
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

	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private List<Long> _vocabularyIds;

}