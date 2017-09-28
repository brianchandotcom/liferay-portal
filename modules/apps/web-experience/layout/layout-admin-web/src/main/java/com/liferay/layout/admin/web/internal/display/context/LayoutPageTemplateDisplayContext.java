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

import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
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
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateDisplayContext {

	public LayoutPageTemplateDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			HttpServletRequest request)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = request;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			LayoutAdminPortletKeys.GROUP_PAGES, "display-style", "icon");

		return _displayStyle;
	}

	public SearchContainer getLayoutPageTemplateCollectionsSearchContainer()
		throws PortalException {

		if (_layoutPageTemplateCollectionsSearchContainer != null) {
			return _layoutPageTemplateCollectionsSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer layoutPageTemplateCollectionsSearchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-collections");

		layoutPageTemplateCollectionsSearchContainer.setEmptyResultsMessage(
			"there-are-no-collections.-you-can-add-a-collection-by-clicking-" +
				"the-plus-button-on-the-bottom-right-corner");

		layoutPageTemplateCollectionsSearchContainer.
			setEmptyResultsMessageCssClass(
				"taglib-empty-result-message-header-has-plus-btn");

		layoutPageTemplateCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<LayoutPageTemplateCollection> orderByComparator =
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateCollectionOrderByComparator(
					getOrderByCol(), getOrderByType());

		layoutPageTemplateCollectionsSearchContainer.setOrderByCol(
			getOrderByCol());
		layoutPageTemplateCollectionsSearchContainer.setOrderByComparator(
			orderByComparator);
		layoutPageTemplateCollectionsSearchContainer.setOrderByType(
			getOrderByType());
		layoutPageTemplateCollectionsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollections(
					themeDisplay.getScopeGroupId(),
					layoutPageTemplateCollectionsSearchContainer.getStart(),
					layoutPageTemplateCollectionsSearchContainer.getEnd(),
					orderByComparator);

		int layoutPageTemplateCollectionsCount =
			LayoutPageTemplateCollectionServiceUtil.
				getLayoutPageTemplateCollectionsCount(
					themeDisplay.getScopeGroupId());

		layoutPageTemplateCollectionsSearchContainer.setTotal(
			layoutPageTemplateCollectionsCount);

		layoutPageTemplateCollectionsSearchContainer.setResults(
			layoutPageTemplateCollections);

		_layoutPageTemplateCollectionsSearchContainer =
			layoutPageTemplateCollectionsSearchContainer;

		return _layoutPageTemplateCollectionsSearchContainer;
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

	private String _displayStyle;
	private SearchContainer _layoutPageTemplateCollectionsSearchContainer;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}