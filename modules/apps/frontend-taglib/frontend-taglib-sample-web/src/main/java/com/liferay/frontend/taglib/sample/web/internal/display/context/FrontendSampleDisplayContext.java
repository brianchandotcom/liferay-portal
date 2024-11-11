/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemListBuilder;

import java.util.List;

/**
 * @author Antonio Ortega
 */
public class FrontendSampleDisplayContext {

	public List<TabsItem> getTabsItems() {
		if (_tabsItems != null) {
			return _tabsItems;
		}

		_tabsItems = TabsItemListBuilder.add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel("Search Iterator");
				tabsItem.setPanelId("search_iterator");
			}
		).add(
			tabsItem -> {
				tabsItem.setLabel("Search Paginator");
				tabsItem.setPanelId("search_paginator");
			}
		).build();

		return _tabsItems;
	}

	private List<TabsItem> _tabsItems;

}