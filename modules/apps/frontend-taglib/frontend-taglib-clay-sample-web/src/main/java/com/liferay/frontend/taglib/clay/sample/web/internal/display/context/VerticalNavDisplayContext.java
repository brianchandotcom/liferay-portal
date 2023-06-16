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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItemList;
import com.liferay.portal.kernel.util.IntegerWrapper;

import java.util.List;

/**
 * @author Eduardo Allegrini
 * @author Daniel Sanz
 */
public class VerticalNavDisplayContext {

	public List<VerticalNavItem> getVerticalNavItems() {
		if (_verticalNavItems != null) {
			return _verticalNavItems;
		}

		_verticalNavItems = new VerticalNavItemList() {
			{
				IntegerWrapper integerWrapper = new IntegerWrapper(1);

				while (true) {
					if (integerWrapper.getValue() == 8) {
						break;
					}

					add(
						verticalNavItem -> {
							verticalNavItem.setHref(
								"#" + integerWrapper.getValue());
							verticalNavItem.setLabel(
								"Item " + integerWrapper.getValue());

							if ((integerWrapper.getValue() % 2) == 0) {
								verticalNavItem.setItems(
									_createVerticalNavItemsList(
										integerWrapper.getValue(),
										verticalNavItem));

								verticalNavItem.setExpanded(
									integerWrapper.getValue() == 4);
							}
						});

					integerWrapper.increment();
				}
			}
		};

		return _verticalNavItems;
	}

	private VerticalNavItemList _createVerticalNavItemsList(
		int size, VerticalNavItem parent) {

		return new VerticalNavItemList() {
			{
				int i = 0;

				while (i < size) {
					int position = i;
					String suffix = "." + position;

					add(
						verticalNavItem -> {
							verticalNavItem.setHref(
								parent.get("href") + suffix);
							verticalNavItem.setLabel(
								parent.get("label") + suffix);

							if (size == 4) {
								verticalNavItem.setItems(
									_createVerticalNavItemsList(
										5, verticalNavItem));
							}

							verticalNavItem.setExpanded(position == 2);
						});

					i++;
				}
			}
		};
	}

	private List<VerticalNavItem> _verticalNavItems;

}