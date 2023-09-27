/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

/**
 * @author Carlos Lancha
 */
public class DropdownItem extends NavigationItem {

	public DropdownItem() {
		put("type", "item");
	}

	public void setIcon(String icon) {
		put("icon", icon);
	}

	public void setKey(String key) {
		put("key", key);
	}

	public void setMultipleTypesBulkActionDisabled(
		Boolean multipleTypesBulkActionDisabled) {

		put("multipleTypesBulkActionDisabled", multipleTypesBulkActionDisabled);
	}

	public void setQuickAction(boolean quickAction) {
		put("quickAction", quickAction);
	}

	public void setSeparator(boolean separator) {
		put("separator", separator);
	}

	public void setTarget(String target) {
		put("target", target);
	}

	public void setType(String type) {
		put("type", type);
	}

}