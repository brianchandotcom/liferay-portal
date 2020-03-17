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
 * @author Hugo Huijser
 */
public class NavigationItemListBuilder {

	public static NavigationItemListWrapper add(NavigationItem navigationItem) {
		NavigationItemListWrapper navigationItemListWrapper =
			new NavigationItemListWrapper();

		return navigationItemListWrapper.add(navigationItem);
	}

	public static NavigationItemListWrapper add(
		UnsafeConsumer<NavigationItem, Exception> unsafeConsumer) {

		NavigationItemListWrapper navigationItemListWrapper =
			new NavigationItemListWrapper();

		return navigationItemListWrapper.add(unsafeConsumer);
	}

	public static NavigationItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		NavigationItem navigationItem) {

		NavigationItemListWrapper navigationItemListWrapper =
			new NavigationItemListWrapper();

		return navigationItemListWrapper.add(unsafeSupplier, navigationItem);
	}

	public static NavigationItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<NavigationItem, Exception> unsafeConsumer) {

		NavigationItemListWrapper navigationItemListWrapper =
			new NavigationItemListWrapper();

		return navigationItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class NavigationItemListWrapper {

		public NavigationItemListWrapper add(NavigationItem navigationItem) {
			_navigationItemList.add(navigationItem);

			return this;
		}

		public NavigationItemListWrapper add(
			UnsafeConsumer<NavigationItem, Exception> unsafeConsumer) {

			_navigationItemList.add(unsafeConsumer);

			return this;
		}

		public NavigationItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			NavigationItem navigationItem) {

			try {
				if (unsafeSupplier.get()) {
					_navigationItemList.add(navigationItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public NavigationItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<NavigationItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_navigationItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public NavigationItemList build() {
			return _navigationItemList;
		}

		private final NavigationItemList _navigationItemList =
			new NavigationItemList();

	}

}