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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;

/**
 * @author Chema Balsas
 */
public class NavigationBarsDisplayContext {

	public NavigationItemList getNavigationItems() {
		if (_navigationItems != null) {
			return _navigationItems;
		}

		_navigationItems = new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setHref("#1");
						navigationItem.setLabel("Page 1");
					});

				add(
					navigationItem -> {
						navigationItem.setHref("#2");
						navigationItem.setLabel("Page 2");
					});

				add(
					navigationItem -> {
						navigationItem.setHref("#3");
						navigationItem.setLabel("Page 3");
					});

				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref("#4");
						navigationItem.setLabel("Page 4");
					});

				add(
					navigationItem -> {
						navigationItem.setHref("#5");
						navigationItem.setLabel("Page 5");
					});

				add(
					navigationItem -> {
						navigationItem.setHref("#6");
						navigationItem.setLabel("Page 6");
					});

				add(
					navigationItem -> {
						navigationItem.setHref("#7");
						navigationItem.setLabel("Page 7");
					});
			}
		};

		return _navigationItems;
	}

	private NavigationItemList _navigationItems;

}