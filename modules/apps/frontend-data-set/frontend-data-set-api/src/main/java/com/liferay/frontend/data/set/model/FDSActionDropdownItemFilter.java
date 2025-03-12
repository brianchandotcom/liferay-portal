/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.model;

import java.util.HashMap;

/**
 * @author Marco Leo
 */
public class FDSActionDropdownItemFilter extends HashMap<String, Object> {

	public FDSActionDropdownItemFilter(String key, Object value) {
		setKey(key);

		setValue(value);
	}

	public void setKey(String key) {
		put("key", key);
	}

	public void setValue(Object value) {
		put("value", value);
	}

}