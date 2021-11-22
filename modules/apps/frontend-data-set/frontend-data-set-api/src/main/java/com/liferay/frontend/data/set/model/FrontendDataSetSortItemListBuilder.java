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
public class FrontendDataSetSortItemListBuilder {

	public static FrontendDataSetSortItemListWrapper add(
		FrontendDataSetSortItem frontendDataSetSortItem) {

		FrontendDataSetSortItemListWrapper frontendDataSetSortItemListWrapper =
			new FrontendDataSetSortItemListWrapper();

		return frontendDataSetSortItemListWrapper.add(frontendDataSetSortItem);
	}

	public static FrontendDataSetSortItemListWrapper add(
		UnsafeConsumer<FrontendDataSetSortItem, Exception> unsafeConsumer) {

		FrontendDataSetSortItemListWrapper frontendDataSetSortItemListWrapper =
			new FrontendDataSetSortItemListWrapper();

		return frontendDataSetSortItemListWrapper.add(unsafeConsumer);
	}

	public static FrontendDataSetSortItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		FrontendDataSetSortItem frontendDataSetSortItem) {

		FrontendDataSetSortItemListWrapper frontendDataSetSortItemListWrapper =
			new FrontendDataSetSortItemListWrapper();

		return frontendDataSetSortItemListWrapper.add(
			unsafeSupplier, frontendDataSetSortItem);
	}

	public static FrontendDataSetSortItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<FrontendDataSetSortItem, Exception> unsafeConsumer) {

		FrontendDataSetSortItemListWrapper frontendDataSetSortItemListWrapper =
			new FrontendDataSetSortItemListWrapper();

		return frontendDataSetSortItemListWrapper.add(
			unsafeSupplier, unsafeConsumer);
	}

	public static final class FrontendDataSetSortItemListWrapper {

		public FrontendDataSetSortItemListWrapper add(
			FrontendDataSetSortItem frontendDataSetSortItem) {

			_frontendDataSetSortItemList.add(frontendDataSetSortItem);

			return this;
		}

		public FrontendDataSetSortItemListWrapper add(
			UnsafeConsumer<FrontendDataSetSortItem, Exception> unsafeConsumer) {

			_frontendDataSetSortItemList.add(unsafeConsumer);

			return this;
		}

		public FrontendDataSetSortItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			FrontendDataSetSortItem frontendDataSetSortItem) {

			try {
				if (unsafeSupplier.get()) {
					_frontendDataSetSortItemList.add(frontendDataSetSortItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public FrontendDataSetSortItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<FrontendDataSetSortItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_frontendDataSetSortItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public FrontendDataSetSortItemList build() {
			return _frontendDataSetSortItemList;
		}

		private final FrontendDataSetSortItemList _frontendDataSetSortItemList =
			new FrontendDataSetSortItemList();

	}

}