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

package com.liferay.portal.kernel.json;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Jesse Yeh
 */
public class JSONMap<K, V> extends HashMap<K, V> {

	public JSONMap(HashMap map) {
		super(map);
	}

	@Override
	public String toString() {
		Iterator<Entry<K, V>> i = entrySet().iterator();

		if (!i.hasNext())

			return "{}";

		StringBuilder sb = new StringBuilder();

		sb.append('{');

		while (true) {
			Entry<K, V> entry = i.next();

			sb.append(entry.getKey());
			sb.append(':');
			sb.append(entry.getValue());

			if (!i.hasNext()) {
				sb.append('}');

				return sb.toString();
			}

			sb.append(", ");
		}
	}

}