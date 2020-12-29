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
public class MultiselectItemListBuilder {

	public static MultiselectItemListWrapper add(
		MultiselectItem multiselectItem) {

		MultiselectItemListWrapper multiselectItemListWrapper =
			new MultiselectItemListWrapper();

		return multiselectItemListWrapper.add(multiselectItem);
	}

	public static MultiselectItemListWrapper add(
		UnsafeConsumer<MultiselectItem, Exception> unsafeConsumer) {

		MultiselectItemListWrapper multiselectItemListWrapper =
			new MultiselectItemListWrapper();

		return multiselectItemListWrapper.add(unsafeConsumer);
	}

	public static MultiselectItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		MultiselectItem multiselectItem) {

		MultiselectItemListWrapper multiselectItemListWrapper =
			new MultiselectItemListWrapper();

		return multiselectItemListWrapper.add(unsafeSupplier, multiselectItem);
	}

	public static MultiselectItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<MultiselectItem, Exception> unsafeConsumer) {

		MultiselectItemListWrapper multiselectItemListWrapper =
			new MultiselectItemListWrapper();

		return multiselectItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class MultiselectItemListWrapper {

		public MultiselectItemListWrapper add(MultiselectItem multiselectItem) {
			_multiselectItemList.add(multiselectItem);

			return this;
		}

		public MultiselectItemListWrapper add(
			UnsafeConsumer<MultiselectItem, Exception> unsafeConsumer) {

			_multiselectItemList.add(unsafeConsumer);

			return this;
		}

		public MultiselectItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			MultiselectItem multiselectItem) {

			try {
				if (unsafeSupplier.get()) {
					_multiselectItemList.add(multiselectItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public MultiselectItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<MultiselectItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_multiselectItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public MultiselectItemList build() {
			return _multiselectItemList;
		}

		private final MultiselectItemList _multiselectItemList =
			new MultiselectItemList();

	}

}