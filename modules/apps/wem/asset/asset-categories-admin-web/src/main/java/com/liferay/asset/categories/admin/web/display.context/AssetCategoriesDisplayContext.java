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

package com.liferay.asset.categories.admin.web.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyDisplay;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.comparator.AssetVocabularyCreateDateComparator;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class AssetCategoriesDisplayContext {

	public AssetCategoriesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;

		_renderResponse = renderResponse;

		_request = request;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "displayStyle", "list");

		return _displayStyle;
	}

	public String getKeywords() {
		return ParamUtil.getString(_request, "keywords");
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public SearchContainer getVocabulariesSearchContainer()
		throws PortalException {

		SearchContainer vocabulariesSearchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-vocabularies.-you-can-add-a-vocabulary-by-clicking" +
				"-the-plus-button-on-the-bottom-right-corner");

		String keywords = getKeywords();

		vocabulariesSearchContainer.setSearch(Validator.isNotNull(keywords));

		vocabulariesSearchContainer.setOrderByCol(getOrderByCol());

		String orderByType = getOrderByType();

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetVocabulary> orderByComparator =
			new AssetVocabularyCreateDateComparator(orderByAsc);

		vocabulariesSearchContainer.setOrderByComparator(orderByComparator);

		vocabulariesSearchContainer.setOrderByType(orderByType);
		vocabulariesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<AssetVocabulary> vocabularies = null;
		int vocabulariesCount = 0;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (Validator.isNotNull(keywords)) {
			Sort sort = new Sort("createDate", Sort.LONG_TYPE, orderByAsc);

			AssetVocabularyDisplay assetVocabularyDisplay =
				AssetVocabularyServiceUtil.searchVocabulariesDisplay(
					scopeGroupId, keywords, true,
					vocabulariesSearchContainer.getStart(),
					vocabulariesSearchContainer.getEnd(), sort);

			vocabulariesCount = assetVocabularyDisplay.getTotal();

			vocabulariesSearchContainer.setTotal(vocabulariesCount);

			vocabularies = assetVocabularyDisplay.getVocabularies();
		}
		else {
			vocabulariesCount =
				AssetVocabularyServiceUtil.getGroupVocabulariesCount(
					scopeGroupId);

			vocabulariesSearchContainer.setTotal(vocabulariesCount);

			vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(
				scopeGroupId, true, vocabulariesSearchContainer.getStart(),
				vocabulariesSearchContainer.getEnd(),
				vocabulariesSearchContainer.getOrderByComparator());
		}

		vocabulariesSearchContainer.setResults(vocabularies);

		return vocabulariesSearchContainer;
	}

	private String _displayStyle;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}