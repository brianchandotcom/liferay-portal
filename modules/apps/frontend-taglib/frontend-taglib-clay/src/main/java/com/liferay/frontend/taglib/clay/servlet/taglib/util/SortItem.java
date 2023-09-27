/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.HashMap;

/**
 * @author Luca Pellizzon
 */
public class SortItem extends HashMap<String, Object> {

	public void setDirection(String direction) {
		put("direction", direction);
	}

	public void setKey(String key) {
		put("key", key);
	}

}