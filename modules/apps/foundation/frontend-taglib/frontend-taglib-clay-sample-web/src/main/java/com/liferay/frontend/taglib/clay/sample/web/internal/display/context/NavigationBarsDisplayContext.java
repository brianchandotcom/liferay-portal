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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItems;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class NavigationBarsDisplayContext {

	public NavigationBarsDisplayContext() {
	}

	public List<NavigationItem> getNavigationItems() {
		if (_navigationItems != null) {
			return _navigationItems.get();
		}

		NavigationItems.Builder builder = new NavigationItems.Builder();

		_navigationItems = builder.item(
			itemBuilder -> itemBuilder.active(
				true
			).href(
				"#1"
			).label(
				"Page 1"
			).build()
		).item(
			itemBuilder -> itemBuilder.active(
				false
			).href(
				"#2"
			).label(
				"Page 2"
			).build()
		).build();

		return _navigationItems.get();
	}

	private NavigationItems _navigationItems;

}