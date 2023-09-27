/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.HashMap;

/**
 * @author Kresimir Coko
 */
public class MultiselectItem extends HashMap<String, String> {

	public MultiselectItem() {
		put("label", "label");
		put("value", "value");
	}

	public void setLabel(String label) {
		put("label", label);
	}

	public void setValue(String value) {
		put("value", value);
	}

}