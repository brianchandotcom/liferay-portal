/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Eudaldo Alonso
 */
public class VerticalNavItemListBuilder {

	public static VerticalNavItemListWrapper add(
		UnsafeConsumer<VerticalNavItem, Exception> unsafeConsumer) {

		VerticalNavItemListWrapper verticalNavItemListWrapper =
			new VerticalNavItemListWrapper();

		return verticalNavItemListWrapper.add(unsafeConsumer);
	}

	public static VerticalNavItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<VerticalNavItem, Exception> unsafeConsumer) {

		VerticalNavItemListWrapper verticalNavItemListWrapper =
			new VerticalNavItemListWrapper();

		return verticalNavItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static VerticalNavItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		VerticalNavItem verticalNavItem) {

		VerticalNavItemListWrapper verticalNavItemListWrapper =
			new VerticalNavItemListWrapper();

		return verticalNavItemListWrapper.add(unsafeSupplier, verticalNavItem);
	}

	public static VerticalNavItemListWrapper add(
		VerticalNavItem verticalNavItem) {

		VerticalNavItemListWrapper verticalNavItemListWrapper =
			new VerticalNavItemListWrapper();

		return verticalNavItemListWrapper.add(verticalNavItem);
	}

	public static final class VerticalNavItemListWrapper {

		public VerticalNavItemListWrapper add(
			UnsafeConsumer<VerticalNavItem, Exception> unsafeConsumer) {

			_verticalNavItemList.add(unsafeConsumer);

			return this;
		}

		public VerticalNavItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<VerticalNavItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_verticalNavItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public VerticalNavItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			VerticalNavItem verticalNavItem) {

			try {
				if (unsafeSupplier.get()) {
					_verticalNavItemList.add(verticalNavItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public VerticalNavItemListWrapper add(VerticalNavItem verticalNavItem) {
			_verticalNavItemList.add(verticalNavItem);

			return this;
		}

		public VerticalNavItemList build() {
			return _verticalNavItemList;
		}

		private final VerticalNavItemList _verticalNavItemList =
			new VerticalNavItemList();

	}

}