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

package com.liferay.layout.set.prototype.web.internal.display.context;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.LayoutSetPrototypeCreateDateComparator;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSetPrototypeDisplayContext {

	public LayoutSetPrototypeDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;
	}

	public Boolean getActive() {
		String navigation = getNavigation();

		Boolean active = null;

		if (navigation.equals("active")) {
			active = true;
		}
		else if (navigation.equals("inactive")) {
			active = false;
		}

		return active;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE, "display-style",
			"list");

		return _displayStyle;
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

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		return portletURL;
	}

	public SearchContainer getSearchContainer() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-site-templates");

		if (isShowAddButton()) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-site-templates.-you-can-add-a-site-template-by-" +
					"clicking-the-plus-button-on-the-bottom-right-corner");
			searchContainer.setEmptyResultsMessageCssClass(
				"taglib-empty-result-message-header-has-plus-btn");
		}

		searchContainer.setId("layoutSetPrototype");
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		boolean orderByAsc = false;

		if (getOrderByType().equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutSetPrototype> orderByComparator =
			new LayoutSetPrototypeCreateDateComparator(orderByAsc);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		searchContainer.setTotal(getTotal());

		List results = LayoutSetPrototypeLocalServiceUtil.search(
			themeDisplay.getCompanyId(), getActive(),
			searchContainer.getStart(), searchContainer.getEnd(),
			searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		return searchContainer;
	}

	public boolean isDescriptiveView() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() {
		if (getTotal() > 0) {
			return false;
		}

		if (!Objects.equals(getNavigation(), "all")) {
			return false;
		}

		return true;
	}

	public boolean isIconView() {
		if (Objects.equals(getDisplayStyle(), "icon")) {
			return true;
		}

		return false;
	}

	public boolean isListView() {
		if (Objects.equals(getDisplayStyle(), "list")) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PortalPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				ActionKeys.ADD_LAYOUT_SET_PROTOTYPE)) {

			return true;
		}

		return false;
	}

	protected String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation");

		return _navigation;
	}

	protected int getTotal() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return LayoutSetPrototypeLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), getActive());
	}

	private String _displayStyle;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}