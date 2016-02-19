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

package com.liferay.item.selector.web.portlet;

import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera
 */
public class LocalizedItemSelectorRendering {

	public static LocalizedItemSelectorRendering get(
		PortletRequest portletRequest) {

		return (LocalizedItemSelectorRendering)portletRequest.getAttribute(
			LocalizedItemSelectorRendering.class.getName());
	}

	public LocalizedItemSelectorRendering(
		Locale locale, ItemSelectorRendering itemSelectorRendering) {

		_locale = locale;
		_itemSelectorRendering = itemSelectorRendering;

		for (ItemSelectorViewRenderer itemSelectorViewRenderer :
				itemSelectorRendering.getItemSelectorViewRenderers()) {

			add(itemSelectorViewRenderer);
		}
	}

	public void add(ItemSelectorViewRenderer itemSelectorViewRenderer) {
		ItemSelectorView<?> itemSelectorView =
			itemSelectorViewRenderer.getItemSelectorView();

		String title = itemSelectorView.getTitle(_locale);

		_itemSelectorViewRenderers.put(title, itemSelectorViewRenderer);
		_titles.add(title);
	}

	public String getItemSelectedEventName() {
		return _itemSelectorRendering.getItemSelectedEventName();
	}

	public ItemSelectorViewRenderer getItemSelectorViewRenderer(String title) {
		return _itemSelectorViewRenderers.get(title);
	}

	public String getSelectedTab() {
		return _itemSelectorRendering.getSelectedTab();
	}

	public List<String> getTitles() {
		return _titles;
	}

	public void store(PortletRequest portletRequest) {
		portletRequest.setAttribute(
			LocalizedItemSelectorRendering.class.getName(), this);
	}

	private final ItemSelectorRendering _itemSelectorRendering;
	private final Map<String, ItemSelectorViewRenderer>
		_itemSelectorViewRenderers = new HashMap<>();
	private final Locale _locale;
	private final List<String> _titles = new ArrayList<>();

}