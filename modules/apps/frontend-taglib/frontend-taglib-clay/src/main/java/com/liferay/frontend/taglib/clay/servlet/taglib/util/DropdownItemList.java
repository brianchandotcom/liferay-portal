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

import java.util.ArrayList;

/**
 * @author Brian Wing Shun Chan
 */
public class DropdownItemList extends ArrayList<DropdownItem> {

	public static DropdownItemList of(DropdownItem... dropdownItems) {
		DropdownItemList dropdownItemList = new DropdownItemList();

		for (DropdownItem dropdownItem : dropdownItems) {
			if (dropdownItem != null) {
				dropdownItemList.add(dropdownItem);
			}
		}

		return dropdownItemList;
	}

	public static DropdownItemList of(
		UnsafeSupplier<DropdownItem, Exception>... unsafeSuppliers) {

		DropdownItemList dropdownItemList = new DropdownItemList();

		for (UnsafeSupplier<DropdownItem, Exception> unsafeSupplier :
				unsafeSuppliers) {

			try {
				DropdownItem dropdownItem = unsafeSupplier.get();

				if (dropdownItem != null) {
					dropdownItemList.add(dropdownItem);
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return dropdownItemList;
	}

	public void add(UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {
		DropdownItem dropdownItem = new DropdownItem();

		try {
			unsafeConsumer.accept(dropdownItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		add(dropdownItem);
	}

	public void addCheckbox(
		UnsafeConsumer<DropdownCheckboxItem, Exception> unsafeConsumer) {

		DropdownCheckboxItem dropdownCheckboxItem = new DropdownCheckboxItem();

		try {
			unsafeConsumer.accept(dropdownCheckboxItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		add(dropdownCheckboxItem);
	}

	public void addGroup(
		UnsafeConsumer<DropdownGroupItem, Exception> unsafeConsumer) {

		DropdownGroupItem dropdownGroupItem = new DropdownGroupItem();

		try {
			unsafeConsumer.accept(dropdownGroupItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		add(dropdownGroupItem);
	}

	public void addRadio(
		UnsafeConsumer<DropdownRadioItem, Exception> unsafeConsumer) {

		DropdownRadioItem dropdownRadioItem = new DropdownRadioItem();

		try {
			unsafeConsumer.accept(dropdownRadioItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		add(dropdownRadioItem);
	}

	public void addRadioGroup(
		UnsafeConsumer<DropdownRadioGroupItem, Exception> unsafeConsumer) {

		DropdownRadioGroupItem dropdownRadioGroupItem =
			new DropdownRadioGroupItem();

		try {
			unsafeConsumer.accept(dropdownRadioGroupItem);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		add(dropdownRadioGroupItem);
	}

}