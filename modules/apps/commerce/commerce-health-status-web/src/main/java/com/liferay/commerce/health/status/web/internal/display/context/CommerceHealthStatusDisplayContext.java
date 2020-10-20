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

package com.liferay.commerce.health.status.web.internal.display.context;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.health.status.CommerceHealthHttpStatus;
import com.liferay.commerce.health.status.CommerceHealthHttpStatusRegistry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceHealthStatusDisplayContext {

	public CommerceHealthStatusDisplayContext(
		CommerceHealthHttpStatusRegistry commerceHealthHttpStatusRegistry,
		PortletResourcePermission portletResourcePermission,
		RenderRequest renderRequest, RenderResponse renderResponse, int type) {

		_commerceHealthHttpStatusRegistry = commerceHealthHttpStatusRegistry;
		_portletResourcePermission = portletResourcePermission;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_type = type;
	}

	public List<CommerceHealthHttpStatus> getCommerceHealthStatuses() {
		return _commerceHealthHttpStatusRegistry.getCommerceHealthStatuses(
			_type);
	}

	public SearchContainer<CommerceHealthHttpStatus> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-results");

		List<CommerceHealthHttpStatus> results = getCommerceHealthStatuses();

		_searchContainer.setResults(results);
		_searchContainer.setTotal(results.size());

		return _searchContainer;
	}

	public boolean hasManageCommerceHealthStatusPermission() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_HEALTH_STATUS);
	}

	private final CommerceHealthHttpStatusRegistry
		_commerceHealthHttpStatusRegistry;
	private final PortletResourcePermission _portletResourcePermission;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CommerceHealthHttpStatus> _searchContainer;
	private final int _type;

}