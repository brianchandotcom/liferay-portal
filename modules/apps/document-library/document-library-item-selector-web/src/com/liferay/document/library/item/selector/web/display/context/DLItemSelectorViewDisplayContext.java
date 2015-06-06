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

package com.liferay.document.library.item.selector.web.display.context;

import com.liferay.item.selector.ItemSelectorCriterion;

import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class DLItemSelectorViewDisplayContext {

	public DLItemSelectorViewDisplayContext(
		ItemSelectorCriterion itemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		_itemSelectorCriterion = itemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_portletURL = portletURL;
	}

	public ItemSelectorCriterion getDLItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorCriterion _itemSelectorCriterion;
	private final PortletURL _portletURL;

}