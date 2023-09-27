/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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