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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
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

	public long getCategoryId() {
		if (Validator.isNotNull(_categoryId)) {
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

	public boolean isSingleSelect() {
		if (_singleSelect != null) {
			return _singleSelect;
		}

		_singleSelect = ParamUtil.getBoolean(_request, "singleSelect");

		return _singleSelect;
	}

	private long _categoryId;
	private String _eventName;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private String _selectedCategories;
	private Boolean _singleSelect;
	private long[] _vocabularyIds;

}