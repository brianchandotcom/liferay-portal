/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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