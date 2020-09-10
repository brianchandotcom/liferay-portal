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
 * @author Luca Pellizzon
 */
public class SortItemListBuilder {

	public static SortItemListWrapper add(SortItem sortItem) {
		SortItemListWrapper sortItemListWrapper = new SortItemListWrapper();

		return sortItemListWrapper.add(sortItem);
	}

	public static SortItemListWrapper add(
		UnsafeConsumer<SortItem, Exception> unsafeConsumer) {

		SortItemListWrapper sortItemListWrapper = new SortItemListWrapper();

		return sortItemListWrapper.add(unsafeConsumer);
	}

	public static SortItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier, SortItem sortItem) {

		SortItemListWrapper sortItemListWrapper = new SortItemListWrapper();

		return sortItemListWrapper.add(unsafeSupplier, sortItem);
	}

	public static SortItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<SortItem, Exception> unsafeConsumer) {

		SortItemListWrapper sortItemListWrapper = new SortItemListWrapper();

		return sortItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class SortItemListWrapper {

		public SortItemListWrapper add(SortItem sortItem) {
			_sortItemList.add(sortItem);

			return this;
		}

		public SortItemListWrapper add(
			UnsafeConsumer<SortItem, Exception> unsafeConsumer) {

			_sortItemList.add(unsafeConsumer);

			return this;
		}

		public SortItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			SortItem sortItem) {

			try {
				if (unsafeSupplier.get()) {
					_sortItemList.add(sortItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public SortItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<SortItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_sortItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public SortItemList build() {
			return _sortItemList;
		}

		private final SortItemList _sortItemList = new SortItemList();

	}

}