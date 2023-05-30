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
				tabsItem.setLabel("Alerts");
				tabsItem.setPanelId("alerts");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Badges");
				tabsItem.setPanelId("badges");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Buttons");
				tabsItem.setPanelId("buttons");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Cards");
				tabsItem.setPanelId("cards");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Dropdowns");
				tabsItem.setPanelId("dropdowns");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Form Elements");
				tabsItem.setPanelId("form_elements");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Icons");
				tabsItem.setPanelId("icons");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Labels");
				tabsItem.setPanelId("labels");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Links");
				tabsItem.setPanelId("links");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Management Toolbars");
				tabsItem.setPanelId("management_toolbars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Navigation Bars");
				tabsItem.setPanelId("navigation_bars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Pagination Bars");
				tabsItem.setPanelId("pagination_bars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Panel");
				tabsItem.setPanelId("panel");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Progress Bars");
				tabsItem.setPanelId("progress_bars");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Stickers");
				tabsItem.setPanelId("Stickers");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Tabs");
				tabsItem.setPanelId("tabs");
			}
		).build();

		return _tabsItems;
	}

	private List<TabsItem> _tabsItems;

}