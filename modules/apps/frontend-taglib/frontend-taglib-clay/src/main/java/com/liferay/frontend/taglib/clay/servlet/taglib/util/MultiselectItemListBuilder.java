/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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