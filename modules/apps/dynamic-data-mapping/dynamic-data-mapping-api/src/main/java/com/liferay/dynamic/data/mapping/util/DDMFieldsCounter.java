/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import java.util.HashMap;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMFieldsCounter extends HashMap<Object, Integer> {

	@Override
	public Integer get(Object key) {
		if (!containsKey(key)) {
			put(key, 0);
		}

		return super.get(key);
	}

	public int incrementKey(Object key) {
		int value = get(key);

		put(key, ++value);

		return value;
	}

}