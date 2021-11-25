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