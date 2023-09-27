/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.servlet.taglib.util;

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