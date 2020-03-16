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

	public static DropdownItemListWrapper conditionalAdd(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.conditionalAdd(
			unsafeSupplier, unsafeConsumer);
	}

	public static DropdownItemListWrapper conditionalAddCheckbox(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownCheckboxItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.conditionalAddCheckbox(
			unsafeSupplier, unsafeConsumer);
	}

	public static DropdownItemListWrapper conditionalAddGroup(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownGroupItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.conditionalAddGroup(
			unsafeSupplier, unsafeConsumer);
	}

	public static DropdownItemListWrapper conditionalAddRadio(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownRadioItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.conditionalAddRadio(
			unsafeSupplier, unsafeConsumer);
	}

	public static DropdownItemListWrapper conditionalAddRadioGroup(
		UnsafeSupplier<Boolean, Exception> unsafeSupplier,
		UnsafeConsumer<DropdownRadioGroupItem, Exception> unsafeConsumer) {

		DropdownItemListWrapper dropdownItemListWrapper =
			new DropdownItemListWrapper();

		return dropdownItemListWrapper.conditionalAddRadioGroup(
			unsafeSupplier, unsafeConsumer);
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

		public DropdownItemListWrapper conditionalAdd(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_dropdownItemList.add(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public DropdownItemListWrapper conditionalAddCheckbox(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownCheckboxItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_dropdownItemList.addCheckbox(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public DropdownItemListWrapper conditionalAddGroup(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownGroupItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_dropdownItemList.addGroup(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public DropdownItemListWrapper conditionalAddRadio(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownRadioItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_dropdownItemList.addRadio(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public DropdownItemListWrapper conditionalAddRadioGroup(
			UnsafeSupplier<Boolean, Exception> unsafeSupplier,
			UnsafeConsumer<DropdownRadioGroupItem, Exception> unsafeConsumer) {

			try {
				if (unsafeSupplier.get()) {
					_dropdownItemList.addRadioGroup(unsafeConsumer);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		private final DropdownItemList _dropdownItemList =
			new DropdownItemList();

	}

}