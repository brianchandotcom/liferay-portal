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
import com.liferay.portal.kernel.util.IntegerWrapper;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class NavigationBarsDisplayContext {

	public List<NavigationItem> getNavigationItems() {
		if (_navigationItems != null) {
			return _navigationItems;
		}

		_navigationItems = new NavigationItemList() {
			{
				IntegerWrapper integerWrapper = new IntegerWrapper(1);

				while (true) {
					if (integerWrapper.getValue() == 8) {
						break;
					}

					add(
						navigationItem -> {
							if (integerWrapper.getValue() == 4) {
								navigationItem.setActive(true);
							}

							navigationItem.setHref(
								"#" + integerWrapper.getValue());
							navigationItem.setLabel(
								"Page " + integerWrapper.getValue());
						});

					integerWrapper.increment();
				}
			}
		};

		return _navigationItems;
	}

	private List<NavigationItem> _navigationItems;

}