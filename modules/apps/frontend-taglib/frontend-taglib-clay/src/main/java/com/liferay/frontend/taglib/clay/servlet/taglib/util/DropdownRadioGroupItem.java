/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

/**
 * @author Carlos Lancha
 */
public class DropdownRadioGroupItem extends DropdownGroupItem {

	public DropdownRadioGroupItem() {
		put("type", "radiogroup");
	}

	public void setInputName(String inputName) {
		put("inputName", inputName);
	}

}