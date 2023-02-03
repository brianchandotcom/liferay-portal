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
 * @author Carlos Lancha
 */
public class TabsItemList extends ArrayList<TabsItem> {

	public static TabsItemList of(TabsItem... tabsItems) {
		TabsItemList tabsItemList = new TabsItemList();

		for (TabsItem tabsItem : tabsItems) {
			if (tabsItem != null) {
				tabsItemList.add(tabsItem);
			}
		}

		return tabsItemList;
	}

	public static TabsItemList of(
		UnsafeSupplier<TabsItem, Exception>... unsafeSuppliers) {

		TabsItemList tabsItemList = new TabsItemList();

		for (UnsafeSupplier<TabsItem, Exception> unsafeSupplier :
				unsafeSuppliers) {

			try {
				TabsItem tabsItem = unsafeSupplier.get();

				if (tabsItem != null) {
					tabsItemList.add(tabsItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		return tabsItemList;
	}

	public void add(UnsafeConsumer<TabsItem, Exception> unsafeConsumer) {
		TabsItem tabsItem = new TabsItem();

		try {
			unsafeConsumer.accept(tabsItem);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		add(tabsItem);
	}

}