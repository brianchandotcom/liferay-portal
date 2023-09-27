/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.model;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Luca Pellizzon
 */
public class FDSSortItemListBuilder {

	public static FDSSortItemListWrapper add(FDSSortItem fdsSortItem) {
		FDSSortItemListWrapper fdsSortItemListWrapper =
			new FDSSortItemListWrapper();

		return fdsSortItemListWrapper.add(fdsSortItem);
	}

	public static FDSSortItemListWrapper add(
		UnsafeConsumer<FDSSortItem, Exception> unsafeConsumer) {

		FDSSortItemListWrapper fdsSortItemListWrapper =
			new FDSSortItemListWrapper();

		return fdsSortItemListWrapper.add(unsafeConsumer);
	}

	public static FDSSortItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		FDSSortItem fdsSortItem) {

		FDSSortItemListWrapper fdsSortItemListWrapper =
			new FDSSortItemListWrapper();

		return fdsSortItemListWrapper.add(unsafeSupplier, fdsSortItem);
	}

	public static FDSSortItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<FDSSortItem, Exception> unsafeConsumer) {

		FDSSortItemListWrapper fdsSortItemListWrapper =
			new FDSSortItemListWrapper();

		return fdsSortItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class FDSSortItemListWrapper {

		public FDSSortItemListWrapper add(FDSSortItem fdsSortItem) {
			_fdsSortItemList.add(fdsSortItem);

			return this;
		}

		public FDSSortItemListWrapper add(
			UnsafeConsumer<FDSSortItem, Exception> unsafeConsumer) {

			_fdsSortItemList.add(unsafeConsumer);

			return this;
		}

		public FDSSortItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			FDSSortItem fdsSortItem) {

			try {
				if (unsafeSupplier.get()) {
					_fdsSortItemList.add(fdsSortItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public FDSSortItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<FDSSortItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_fdsSortItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public FDSSortItemList build() {
			return _fdsSortItemList;
		}

		private final FDSSortItemList _fdsSortItemList = new FDSSortItemList();

	}

}