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

package com.liferay.document.selector.web.portlet;

import com.liferay.document.selector.ItemSelectorView;
import com.liferay.document.selector.ItemSelectorViewRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Iván Zaera
 */
public class ItemSelectorViewRenderers {

	public static ItemSelectorViewRenderers get(PortletRequest portletRequest) {
		return (ItemSelectorViewRenderers)portletRequest.getAttribute(
			_REQUEST_ATTRIBUTE);
	}

	public ItemSelectorViewRenderers(
		Locale locale,
		List<ItemSelectorViewRenderer<?>> itemSelectorViewRenderersList) {

		_locale = locale;

		for (ItemSelectorViewRenderer<?> itemSelectorViewRenderer :
				itemSelectorViewRenderersList) {

			add(itemSelectorViewRenderer);
		}
	}

	public void add(ItemSelectorViewRenderer<?> itemSelectorViewRenderer) {
		ItemSelectorView<?> itemSelectorView =
			itemSelectorViewRenderer.getItemSelectorView();

		String title = itemSelectorView.getTitle(_locale);

		_titles.add(title);
		_itemSelectorViewRenderers.put(title, itemSelectorViewRenderer);
	}

	public ItemSelectorViewRenderer<?> getItemSelectorViewRenderer(
		String title) {

		return _itemSelectorViewRenderers.get(title);
	}

	public List<String> getTitles() {
		return _titles;
	}

	public void store(PortletRequest portletRequest) {
		portletRequest.setAttribute(_REQUEST_ATTRIBUTE, this);
	}

	private static final String _REQUEST_ATTRIBUTE =
		ItemSelectorViewRenderers.class.getName();

	private final Map<String, ItemSelectorViewRenderer<?>>
		_itemSelectorViewRenderers = new HashMap<>();
	private final Locale _locale;
	private final List<String> _titles = new ArrayList<>();

}