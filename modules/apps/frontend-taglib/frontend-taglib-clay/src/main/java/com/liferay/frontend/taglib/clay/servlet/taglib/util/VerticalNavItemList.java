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

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

import java.util.ArrayList;

/**
 * @author Eduardo Allegrini
 */
public class VerticalNavItemList extends ArrayList<VerticalNavItem> {

	public static VerticalNavItemList of(
		UnsafeSupplier<VerticalNavItem, Exception>... unsafeSuppliers) {

		VerticalNavItemList verticalNavItemList = new VerticalNavItemList();

		for (UnsafeSupplier<VerticalNavItem, Exception> unsafeSupplier :
				unsafeSuppliers) {

			try {
				VerticalNavItem verticalNavItem = unsafeSupplier.get();

				if (verticalNavItem != null) {
					verticalNavItemList.add(verticalNavItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return verticalNavItemList;
	}

	public static VerticalNavItemList of(VerticalNavItem... verticalNavItems) {
		VerticalNavItemList verticalNavItemList = new VerticalNavItemList();

		for (VerticalNavItem verticalNavItem : verticalNavItems) {
			if (verticalNavItem != null) {
				verticalNavItemList.add(verticalNavItem);
			}
		}

		return verticalNavItemList;
	}

	public void add(UnsafeConsumer<VerticalNavItem, Exception> unsafeConsumer) {
		VerticalNavItem verticalNavItem = new VerticalNavItem();

		try {
			unsafeConsumer.accept(verticalNavItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(verticalNavItem);
	}

}