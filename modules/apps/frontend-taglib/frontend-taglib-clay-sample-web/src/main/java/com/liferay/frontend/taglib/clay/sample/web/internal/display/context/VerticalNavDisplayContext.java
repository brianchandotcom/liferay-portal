/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.VerticalNavItemList;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Eduardo Allegrini
 * @author Daniel Sanz
 */
public class VerticalNavDisplayContext {

	public List<String> getVerticalNavDefaultExpandedKeys() {
		return ListUtil.fromArray("id-2", "id-6");
	}

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
							verticalNavItem.setId(
								"id-" + integerWrapper.getValue());

							if ((integerWrapper.getValue() % 2) == 0) {
								verticalNavItem.setItems(
									_createVerticalNavItemsList(
										integerWrapper.getValue(),
										verticalNavItem));
							}

							if (integerWrapper.getValue() == 4) {
								verticalNavItem.setExpanded(true);
							}

							if (integerWrapper.getValue() == 3) {
								verticalNavItem.setActive(true);
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
							verticalNavItem.setId(parent.get("id") + suffix);
							verticalNavItem.setLabel(
								parent.get("label") + suffix);

							if (size == 4) {
								verticalNavItem.setItems(
									_createVerticalNavItemsList(
										5, verticalNavItem));
								verticalNavItem.setExpanded(position == 2);
							}
						});

					i++;
				}
			}
		};
	}

	private List<VerticalNavItem> _verticalNavItems;

}