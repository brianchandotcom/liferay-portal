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
public class MultiSelectItemListBuilder {

	public static MultiSelectItemListWrapper add(
		MultiSelectItem multiSelectItem) {

		MultiSelectItemListWrapper multiSelectItemListWrapper =
			new MultiSelectItemListWrapper();

		return multiSelectItemListWrapper.add(multiSelectItem);
	}

	public static MultiSelectItemListWrapper add(
		UnsafeConsumer<MultiSelectItem, Exception> unsafeConsumer) {

		MultiSelectItemListWrapper multiSelectItemListWrapper =
			new MultiSelectItemListWrapper();

		return multiSelectItemListWrapper.add(unsafeConsumer);
	}

	public static MultiSelectItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		MultiSelectItem multiSelectItem) {

		MultiSelectItemListWrapper multiSelectItemListWrapper =
			new MultiSelectItemListWrapper();

		return multiSelectItemListWrapper.add(unsafeSupplier, multiSelectItem);
	}

	public static MultiSelectItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<MultiSelectItem, Exception> unsafeConsumer) {

		MultiSelectItemListWrapper multiSelectItemListWrapper =
			new MultiSelectItemListWrapper();

		return multiSelectItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class MultiSelectItemListWrapper {

		public MultiSelectItemListWrapper add(MultiSelectItem multiSelectItem) {
			_multiSelectItemList.add(multiSelectItem);

			return this;
		}

		public MultiSelectItemListWrapper add(
			UnsafeConsumer<MultiSelectItem, Exception> unsafeConsumer) {

			_multiSelectItemList.add(unsafeConsumer);

			return this;
		}

		public MultiSelectItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			MultiSelectItem multiSelectItem) {

			try {
				if (unsafeSupplier.get()) {
					_multiSelectItemList.add(multiSelectItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public MultiSelectItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<MultiSelectItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_multiSelectItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public MultiSelectItemList build() {
			return _multiSelectItemList;
		}

		private final MultiSelectItemList _multiSelectItemList =
			new MultiSelectItemList();

	}

}