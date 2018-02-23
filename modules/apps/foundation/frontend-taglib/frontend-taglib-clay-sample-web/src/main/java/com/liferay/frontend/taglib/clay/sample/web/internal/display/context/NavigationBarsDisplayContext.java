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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.util.IntegerWrapper;

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
				IntegerWrapper intgerWrapper = new IntegerWrapper(1);

				while (true) {
					if (intgerWrapper.getValue() == 8) {
						break;
					}

					add(
						navigationItem -> {
							if (intgerWrapper.getValue() == 4) {
								navigationItem.setActive(true);
							}

							navigationItem.setHref(
								"#" + intgerWrapper.getValue());
							navigationItem.setLabel(
								"Page " + intgerWrapper.getValue());
						});

					intgerWrapper.increment();
				}
			}
		};

		return _navigationItems;
	}

	private NavigationItemList _navigationItems;

}