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