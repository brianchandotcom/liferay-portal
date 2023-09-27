/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Hugo Huijser
 */
public class LabelItemListBuilder {

	public static LabelItemListWrapper add(LabelItem labelItem) {
		LabelItemListWrapper labelItemListWrapper = new LabelItemListWrapper();

		return labelItemListWrapper.add(labelItem);
	}

	public static LabelItemListWrapper add(
		UnsafeConsumer<LabelItem, Exception> unsafeConsumer) {

		LabelItemListWrapper labelItemListWrapper = new LabelItemListWrapper();

		return labelItemListWrapper.add(unsafeConsumer);
	}

	public static LabelItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		LabelItem labelItem) {

		LabelItemListWrapper labelItemListWrapper = new LabelItemListWrapper();

		return labelItemListWrapper.add(unsafeSupplier, labelItem);
	}

	public static LabelItemListWrapper add(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<LabelItem, Exception> unsafeConsumer) {

		LabelItemListWrapper labelItemListWrapper = new LabelItemListWrapper();

		return labelItemListWrapper.add(unsafeSupplier, unsafeConsumer);
	}

	public static final class LabelItemListWrapper {

		public LabelItemListWrapper add(LabelItem labelItem) {
			_labelItemList.add(labelItem);

			return this;
		}

		public LabelItemListWrapper add(
			UnsafeConsumer<LabelItem, Exception> unsafeConsumer) {

			_labelItemList.add(unsafeConsumer);

			return this;
		}

		public LabelItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			LabelItem labelItem) {

			try {
				if (unsafeSupplier.get()) {
					_labelItemList.add(labelItem);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public LabelItemListWrapper add(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<LabelItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_labelItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public LabelItemList build() {
			return _labelItemList;
		}

		private final LabelItemList _labelItemList = new LabelItemList();

	}

}