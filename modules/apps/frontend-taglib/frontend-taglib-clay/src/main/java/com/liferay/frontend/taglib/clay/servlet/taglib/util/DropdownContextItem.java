/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.List;

/**
 * @author Mikel Lorza
 */
public class DropdownContextItem extends DropdownItem {

	public DropdownContextItem() {
		put("type", "contextual");
	}

	public void setDropdownItems(List<DropdownItem> dropdownItems) {
		put("items", dropdownItems);
	}

}