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

package com.liferay.site.taglib.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteBrowserDisplayContext {

	public SiteBrowserDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;

		_emptyResultsMessage = GetterUtil.getString(
			httpServletRequest.getAttribute(
				"liferay-site:site-browser:emptyResultsMessage"));
		_groups = (List<Group>)httpServletRequest.getAttribute(
			"liferay-site:site-browser:groups");
		_groupsCount = GetterUtil.getInteger(
			httpServletRequest.getAttribute(
				"liferay-site:site-browser:groupsCount"));
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = GetterUtil.getString(
			_httpServletRequest.getAttribute(
				"liferay-site:site-browser:displayStyle"));

		return _displayStyle;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = (PortletURL)_httpServletRequest.getAttribute(
			"liferay-site:site-browser:portletURL");

		return _portletURL;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null, _emptyResultsMessage);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setResults(_groups);
		searchContainer.setTotal(_groupsCount);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private String _displayStyle;
	private final String _emptyResultsMessage;
	private final List<Group> _groups;
	private final int _groupsCount;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private SearchContainer _searchContainer;

}