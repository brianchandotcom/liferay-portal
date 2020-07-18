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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditStyleBookEntryDisplayContext {

	public EditStyleBookEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_groupURLProvider = (GroupURLProvider)_renderRequest.getAttribute(
			GroupURLProvider.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public Map<String, Object> getStyleBookEditorData() {
		return HashMapBuilder.<String, Object>put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"previewURL",
			() -> {
				String layoutURL = _groupURLProvider.getGroupLayoutsURL(
					_themeDisplay.getScopeGroup(), false, _renderRequest);

				return HttpUtil.addParameter(
					layoutURL, "p_l_mode", Constants.PREVIEW);
			}
		).put(
			"publishURL",
			() -> {
				PortletURL actionURL = _renderResponse.createActionURL();

				actionURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/style_book/publish_style_book_entry");

				return actionURL.toString();
			}
		).put(
			"redirectURL", _getRedirect()
		).put(
			"saveDraftURL",
			() -> {
				PortletURL actionURL = _renderResponse.createActionURL();

				actionURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/style_book/save_draft_style_book_entry");

				return actionURL.toString();
			}
		).put(
			"styleBookEntryId", _getStyleBookEntryId()
		).put(
			"tokenCategories", _getTokenCategories()
		).put(
			"tokensValues",
			() -> {
				StyleBookEntry styleBookEntry = _getStyleBookEntry();

				String tokensValues = styleBookEntry.getTokensValues();

				if (styleBookEntry.isHead()) {
					StyleBookEntry draftStyleBookEntry =
						StyleBookEntryLocalServiceUtil.getDraft(styleBookEntry);

					tokensValues = draftStyleBookEntry.getTokensValues();
				}

				return JSONFactoryUtil.createJSONObject(tokensValues);
			}
		).build();
	}

	private String _getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/style_book/view");

		return portletURL.toString();
	}

	private StyleBookEntry _getStyleBookEntry() {
		if (_styleBookEntry != null) {
			return _styleBookEntry;
		}

		_styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
			_getStyleBookEntryId());

		return _styleBookEntry;
	}

	private long _getStyleBookEntryId() {
		if (_styleBookEntryId != null) {
			return _styleBookEntryId;
		}

		_styleBookEntryId = ParamUtil.getLong(
			_httpServletRequest, "styleBookEntryId");

		return _styleBookEntryId;
	}

	private String _getStyleBookEntryTitle() {
		StyleBookEntry styleBookEntry = _getStyleBookEntry();

		return styleBookEntry.getName();
	}

	private JSONArray _getTokenCategories() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		JSONObject tokenDefinitionJSONObject =
			JSONFactoryUtil.createJSONObject();

		JSONArray tokenCategoriesJSONArray =
			tokenDefinitionJSONObject.getJSONArray("tokenCategories");

		tokenCategoriesJSONArray.forEach(
			object -> {
				JSONObject tokenCategoryJSONObject = (JSONObject)object;

				jsonArray.put(
					JSONUtil.put(
						"label", tokenCategoryJSONObject.getString("label")
					).put(
						"name", tokenCategoryJSONObject.getString("name")
					).put(
						"tokenSets",
						_getTokenSetsJSONArray(
							tokenCategoryJSONObject.getString("name"),
							tokenDefinitionJSONObject)
					));
			});

		return jsonArray;
	}

	private JSONObject _getTokenSetJSONObject(
		String tokenSetName, JSONArray tokenSetsJSONArray) {

		for (int i = 0; i < tokenSetsJSONArray.length(); i++) {
			JSONObject tokenSetJSONObject = tokenSetsJSONArray.getJSONObject(i);

			if (Objects.equals(
					tokenSetJSONObject.getString("name"), tokenSetName)) {

				return tokenSetJSONObject;
			}
		}

		return null;
	}

	private JSONArray _getTokenSetsJSONArray(
		String tokenCategoryName, JSONObject tokenDefinitionJSONObject) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		JSONArray tokensJSONArray = tokenDefinitionJSONObject.getJSONArray(
			"tokens");

		JSONArray tokenSetsJSONArray = tokenDefinitionJSONObject.getJSONArray(
			"tokenSets");

		for (String tokenSetName :
				_getTokenSetsName(tokenCategoryName, tokensJSONArray)) {

			JSONObject tokenSet = _getTokenSetJSONObject(
				tokenSetName, tokenSetsJSONArray);

			if (tokenSet == null) {
				continue;
			}

			jsonArray.put(
				JSONUtil.put(
					"label", tokenSet.getString("label")
				).put(
					"name", tokenSet.getString("name")
				).put(
					"tokens",
					_getTokensJSONArray(
						tokenCategoryName, tokenSet.getString("name"),
						tokensJSONArray)
				));
		}

		return jsonArray;
	}

	private Set<String> _getTokenSetsName(
		String tokenCategoryName, JSONArray tokensJSONArray) {

		Set<String> tokenSetsNames = new HashSet<>();

		tokensJSONArray.forEach(
			object -> {
				JSONObject tokenJSONObject = (JSONObject)object;

				if (Objects.equals(
						tokenJSONObject.getString("tokenCategoryName"),
						tokenCategoryName)) {

					tokenSetsNames.add(
						tokenJSONObject.getString("tokenSetName"));
				}
			});

		return tokenSetsNames;
	}

	private JSONArray _getTokensJSONArray(
		String tokenCategoryName, String tokenSetName,
		JSONArray tokensJSONArray) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		tokensJSONArray.forEach(
			object -> {
				JSONObject tokenJSONObject = (JSONObject)object;

				if (Objects.equals(
						tokenJSONObject.getString("tokenCategoryName"),
						tokenCategoryName) &&
					Objects.equals(
						tokenJSONObject.getString("tokenSetName"),
						tokenSetName)) {

					jsonArray.put(tokenJSONObject);
				}
			});

		return jsonArray;
	}

	private void _setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(_getRedirect());

		_renderResponse.setTitle(_getStyleBookEntryTitle());
	}

	private final GroupURLProvider _groupURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private StyleBookEntry _styleBookEntry;
	private Long _styleBookEntryId;
	private final ThemeDisplay _themeDisplay;

}