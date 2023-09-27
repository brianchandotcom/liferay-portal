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
public class TabsDisplayContext {

	public List<TabsItem> getDefaultTabsItems() {
		if (_defaultTabsItems != null) {
			return _defaultTabsItems;
		}

		_defaultTabsItems = TabsItemListBuilder.add(
			tabsItem -> tabsItem.setLabel("Option 1")
		).add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel("Option 2");
			}
		).add(
			tabsItem -> {
				tabsItem.setDisabled(true);
				tabsItem.setLabel("Option 3");
			}
		).add(
			tabsItem -> {
				tabsItem.setHref("#3");
				tabsItem.setLabel("Option 4");
			}
		).build();

		return _defaultTabsItems;
	}

	private List<TabsItem> _defaultTabsItems;

}