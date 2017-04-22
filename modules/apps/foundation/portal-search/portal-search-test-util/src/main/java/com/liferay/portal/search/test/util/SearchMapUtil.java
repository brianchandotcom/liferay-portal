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

package com.liferay.portal.search.test.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class SearchMapUtil {

	@SafeVarargs
	public static <K, V> Map<K, V> join(
		final Map<? extends K, ? extends V> map1,
		final Map<? extends K, ? extends V> map2,
		final Map<? extends K, ? extends V>... maps) {

		return new HashMap<K, V>() {
			{
				putAll(map1);
				putAll(map2);

				for (Map<? extends K, ? extends V> map : maps) {
					putAll(map);
				}
			}
		};
	}

}