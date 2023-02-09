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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemListBuilder;

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class ClaySampleDisplayContext {

	public List<TabsItem> getTabsItems() {
		if (_tabsItems != null) {
			return _tabsItems;
		}

		_tabsItems = TabsItemListBuilder.add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel("alerts");
				tabsItem.setPanelId("alerts");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("badges");
				tabsItem.setPanelId("badges");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("buttons");
				tabsItem.setPanelId("buttons");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("cards");
				tabsItem.setPanelId("cards");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("dropdowns");
				tabsItem.setPanelId("dropdowns");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("form_elements");
				tabsItem.setPanelId("form_elements");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("icons");
				tabsItem.setPanelId("icons");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("labels");
				tabsItem.setPanelId("labels");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("links");
				tabsItem.setPanelId("links");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("management_toolbars");
				tabsItem.setPanelId("management_toolbars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("navigation_bars");
				tabsItem.setPanelId("navigation_bars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("pagination_bars");
				tabsItem.setPanelId("pagination_bars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("progress_bars");
				tabsItem.setPanelId("progress_bars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("stickers");
				tabsItem.setPanelId("stickers");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("tabs");
				tabsItem.setPanelId("tabs");
			}
		).build();

		return _tabsItems;
	}

	private List<TabsItem> _tabsItems;

}