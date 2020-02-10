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
public class DropdownItemListUtil {

	public static DropdownItemList add(
		UnsafeConsumer<DropdownItem, Exception> unsafeConsumer) {

		DropdownItemList dropdownItemList = new DropdownItemList();

		return dropdownItemList.add(unsafeConsumer);
	}

	public static DropdownItemList addCheckbox(
		UnsafeConsumer<DropdownCheckboxItem, Exception> unsafeConsumer) {

		DropdownItemList dropdownItemList = new DropdownItemList();

		return dropdownItemList.addCheckbox(unsafeConsumer);
	}

	public static DropdownItemList addGroup(
		UnsafeConsumer<DropdownGroupItem, Exception> unsafeConsumer) {

		DropdownItemList dropdownItemList = new DropdownItemList();

		return dropdownItemList.addGroup(unsafeConsumer);
	}

	public static DropdownItemList addRadio(
		UnsafeConsumer<DropdownRadioItem, Exception> unsafeConsumer) {

		DropdownItemList dropdownItemList = new DropdownItemList();

		return dropdownItemList.addRadio(unsafeConsumer);
	}

	public static DropdownItemList addRadioGroup(
		UnsafeConsumer<DropdownRadioGroupItem, Exception> unsafeConsumer) {

		DropdownItemList dropdownItemList = new DropdownItemList();

		return dropdownItemList.addRadioGroup(unsafeConsumer);
	}

}