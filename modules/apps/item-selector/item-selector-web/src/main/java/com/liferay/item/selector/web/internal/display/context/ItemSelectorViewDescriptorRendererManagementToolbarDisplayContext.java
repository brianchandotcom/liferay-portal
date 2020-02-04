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

package com.liferay.item.selector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ItemSelectorViewDescriptorRendererManagementToolbarDisplayContext(
		ItemSelectorViewDescriptor itemSelectorViewDescriptor,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_itemSelectorViewDescriptor = itemSelectorViewDescriptor;
	}

	@Override
	public String getClearResultsURL() {
		return String.valueOf(getPortletURL());
	}

	@Override
	public String[] getOrderByKeys() {
		return _itemSelectorViewDescriptor.getOrderByKeys();
	}

	@Override
	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowSearch() {
		return _itemSelectorViewDescriptor.isShowSearch();
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"descriptive", "icon"};
	}

	private final ItemSelectorViewDescriptor _itemSelectorViewDescriptor;

}