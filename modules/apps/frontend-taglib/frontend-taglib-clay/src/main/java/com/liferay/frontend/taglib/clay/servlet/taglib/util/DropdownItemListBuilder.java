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

/**
 * @author Hugo Huijser
 */
public class DropdownItemListBuilder {

	public static DropdownItemListWrapper add(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.add(unsafeConsumer);
	}

	public static DropdownItemListWrapper addCheckbox(
		UnsafeConsumer<DropdownCheckboxItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.addCheckbox(unsafeConsumer);
	}

	public static DropdownItemListWrapper addGroup(
		UnsafeConsumer<DropdownGroupItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.addGroup(unsafeConsumer);
	}

	public static DropdownItemListWrapper addRadio(
		UnsafeConsumer<DropdownRadioItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.addRadio(unsafeConsumer);
	}

	public static DropdownItemListWrapper addRadioGroup(
		UnsafeConsumer<DropdownRadioGroupItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.addRadioGroup(unsafeConsumer);
	}

	public static final class DropdownItemListWrapper {

		public DropdownItemListWrapper add(
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			_dropdownItemList.add(unsafeConsumer);

			return this;
		}

		public DropdownItemListWrapper addCheckbox(
			UnsafeConsumer<DropdownCheckboxItem, Exception> unsafeConsumer) {

			_dropdownItemList.addCheckbox(unsafeConsumer);

			return this;
		}

		public DropdownItemListWrapper addGroup(
			UnsafeConsumer<DropdownGroupItem, Exception> unsafeConsumer) {

			_dropdownItemList.addGroup(unsafeConsumer);

			return this;
		}

		public DropdownItemListWrapper addRadio(
			UnsafeConsumer<DropdownRadioItem, Exception> unsafeConsumer) {

			_dropdownItemList.addRadio(unsafeConsumer);

			return this;
		}

		public DropdownItemListWrapper addRadioGroup(
			UnsafeConsumer<DropdownRadioGroupItem, Exception> unsafeConsumer) {

			_dropdownItemList.addRadioGroup(unsafeConsumer);

			return this;
		}

		public DropdownItemList build() {
			return _dropdownItemList;
		}

		private final DropdownItemList _dropdownItemList =
			new DropdownItemList();

	}

}