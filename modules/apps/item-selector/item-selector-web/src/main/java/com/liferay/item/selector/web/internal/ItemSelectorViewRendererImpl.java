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

package com.liferay.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public class ItemSelectorViewRendererImpl implements ItemSelectorViewRenderer {

	public ItemSelectorViewRendererImpl(
		ItemSelectorView<ItemSelectorCriterion> itemSelectorView,
		ItemSelectorCriterion itemSelectorCriterion, PortletURL portletURL,
		String itemSelectedEventName, boolean search) {

		_itemSelectorView = itemSelectorView;
		_itemSelectorCriterion = itemSelectorCriterion;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
	}

	@Override
	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	@Override
	public ItemSelectorCriterion getItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	@Override
	public ItemSelectorView<ItemSelectorCriterion> getItemSelectorView() {
		return _itemSelectorView;
	}

	@Override
	public PortletURL getPortletURL() {
		return _portletURL;
	}

	@Override
	public void renderHTML(PageContext pageContext)
		throws IOException, ServletException {

		PortalIncludeUtil.include(
			pageContext,
			new PortalIncludeUtil.HTMLRenderer() {

				@Override
				public void renderHTML(
						HttpServletRequest httpServletRequest,
						HttpServletResponse httpServletResponse)
					throws IOException, ServletException {

					_itemSelectorView.renderHTML(
						httpServletRequest, httpServletResponse,
						_itemSelectorCriterion, _portletURL,
						_itemSelectedEventName, _search);
				}

			});
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorCriterion _itemSelectorCriterion;
	private final ItemSelectorView<ItemSelectorCriterion> _itemSelectorView;
	private final PortletURL _portletURL;
	private final boolean _search;

}