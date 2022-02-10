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

package com.liferay.frontend.taglib.clay.internal.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Víctor Galán
 */
public class DropdownItemListUtil {

	public static boolean isEmpty(List<DropdownItem> dropdownItems) {
		if (ListUtil.isEmpty(dropdownItems)) {
			return true;
		}

		for (DropdownItem dropdownItem : dropdownItems) {
			if (!(dropdownItem instanceof DropdownGroupItem)) {
				return false;
			}

			Object items = dropdownItem.get("items");

			if ((items instanceof List) &&
				ListUtil.isNotEmpty((List<?>)items)) {

				return false;
			}
		}

		return true;
	}

}