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

/**
 * @author Carlos Lancha
 */
public class TabsItemListBuilder {

	public static TabsItemListWrapper add(TabsItem tabsItem) {
		TabsItemListWrapper tabsItemListWrapper = new TabsItemListWrapper();

		return tabsItemListWrapper.add(tabsItem);
	}

	public static TabsItemListWrapper add(
		UnsafeConsumer<TabsItem, Exception> unsafeConsumer) {

		TabsItemListWrapper tabsItemListWrapper = new TabsItemListWrapper();

		return tabsItemListWrapper.add(unsafeConsumer);
	}

	public static TabsItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier, TabsItem tabsItem) {

		TabsItemListWrapper tabsItemListWrapper = new TabsItemListWrapper();

		return tabsItemListWrapper.add(unsafeSupplier, tabsItem);
	}

	public static TabsItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<TabsItem, Exception> unsafeConsumer) {

		TabsItemListWrapper tabsItemListWrapper = new TabsItemListWrapper();

		return tabsItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class TabsItemListWrapper {

		public TabsItemListWrapper add(TabsItem tabsItem) {
			_tabsItemList.add(tabsItem);

			return this;
		}

		public TabsItemListWrapper add(
			UnsafeConsumer<TabsItem, Exception> unsafeConsumer) {

			_tabsItemList.add(unsafeConsumer);

			return this;
		}

		public TabsItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			TabsItem tabsItem) {

			try {
				if (unsafeSupplier.get()) {
					_tabsItemList.add(tabsItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public TabsItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<TabsItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_tabsItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public TabsItemList build() {
			return _tabsItemList;
		}

		private final TabsItemList _tabsItemList = new TabsItemList();

	}

}