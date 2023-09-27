/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class DropdownGroupItem extends DropdownItem {

	public DropdownGroupItem() {
		put("type", "group");
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		put("items", dropdownItems);
	}

}