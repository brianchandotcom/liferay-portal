/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.List;

/**
 * @author Eduardo Allegrini
 */
public class VerticalNavItem extends NavigationItem {

	public void setExpanded(boolean expanded) {
		put("expanded", expanded);
	}

	public void setIcons(List<IconItem> icons) {
		put("icons", icons);
	}

	public void setId(String id) {
		put("id", id);
	}

	public void setItems(List<VerticalNavItem> verticalNavItems) {
		put("items", verticalNavItems);
	}

}