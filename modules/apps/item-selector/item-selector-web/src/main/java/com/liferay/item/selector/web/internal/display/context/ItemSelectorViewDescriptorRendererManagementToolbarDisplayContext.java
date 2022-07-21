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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
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
		ItemSelectorViewDescriptorRendererDisplayContext
			itemSelectorViewDescriptorRendererDisplayContext,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_itemSelectorViewDescriptorRendererDisplayContext =
			itemSelectorViewDescriptorRendererDisplayContext;

		_itemSelectorViewDescriptor =
			itemSelectorViewDescriptorRendererDisplayContext.
				getItemSelectorViewDescriptor();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
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
		return _itemSelectorViewDescriptorRendererDisplayContext.
			isMultipleSelection();
	}

	@Override
	public Boolean isShowSearch() {
		return _itemSelectorViewDescriptor.isShowSearch();
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return _itemSelectorViewDescriptor.getDefaultDisplayStyle();
	}

	@Override
	protected String getDisplayStyle() {
		return _itemSelectorViewDescriptorRendererDisplayContext.
			getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return _itemSelectorViewDescriptor.getDisplayViews();
	}

	private final ItemSelectorViewDescriptor<Object>
		_itemSelectorViewDescriptor;
	private final ItemSelectorViewDescriptorRendererDisplayContext
		_itemSelectorViewDescriptorRendererDisplayContext;

}