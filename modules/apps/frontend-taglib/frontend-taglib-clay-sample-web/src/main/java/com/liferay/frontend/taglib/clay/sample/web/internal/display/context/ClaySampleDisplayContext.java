/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		).add(
			tabsItem -> {
				tabsItem.setLabel("Vertical Nav");
				tabsItem.setPanelId("vertical_nav");
			}
		).build();

		return _tabsItems;
	}

	private List<TabsItem> _tabsItems;

}