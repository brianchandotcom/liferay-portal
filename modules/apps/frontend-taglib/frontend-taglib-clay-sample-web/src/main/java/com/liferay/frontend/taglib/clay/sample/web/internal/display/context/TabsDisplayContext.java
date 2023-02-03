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