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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.layout.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class LayoutPageTemplateCollectionsDisplayContext {

	public LayoutPageTemplateCollectionsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectCollections");

		return _eventName;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-collections");

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<LayoutPageTemplateCollection> orderByComparator =
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateCollectionOrderByComparator(
					_getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections = null;
		int layoutPageTemplateCollectionsCount = 0;

		if (_isSearch()) {
			layoutPageTemplateCollections =
				LayoutPageTemplateCollectionServiceUtil.
					getLayoutPageTemplateCollections(
						themeDisplay.getScopeGroupId(), _getKeywords(),
						searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator);

			layoutPageTemplateCollectionsCount =
				LayoutPageTemplateCollectionServiceUtil.
					getLayoutPageTemplateCollectionsCount(
						themeDisplay.getScopeGroupId(), _getKeywords());
		}
		else {
			layoutPageTemplateCollections =
				LayoutPageTemplateCollectionServiceUtil.
					getLayoutPageTemplateCollections(
						themeDisplay.getScopeGroupId(),
						searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator);

			layoutPageTemplateCollectionsCount =
				LayoutPageTemplateCollectionServiceUtil.
					getLayoutPageTemplateCollectionsCount(
						themeDisplay.getScopeGroupId());
		}

		searchContainer.setTotal(layoutPageTemplateCollectionsCount);
		searchContainer.setResults(layoutPageTemplateCollections);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private boolean _isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	private String _eventName;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;

}