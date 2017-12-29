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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
public class NavigationItems {

	public NavigationItems(Builder builder) {
		_navigationItems = builder._navigationItems;
	}

	public List<NavigationItem> get() {
		return new ArrayList<>(_navigationItems);
	}

	public static class Builder {

		public NavigationItems build() {
			return new NavigationItems(this);
		}

		public Builder item(
			Function<NavigationItem.Builder, NavigationItem> function) {

			_navigationItems.add(function.apply(new NavigationItem.Builder()));

			return this;
		}

		private List<NavigationItem> _navigationItems = new ArrayList<>();

	}

	private final List<NavigationItem> _navigationItems;

}