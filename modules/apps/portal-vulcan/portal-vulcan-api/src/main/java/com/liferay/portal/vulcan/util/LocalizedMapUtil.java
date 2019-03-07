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

package com.liferay.portal.vulcan.util;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizedMapUtil {

	public static Map<Locale, String> merge(
		Map<Locale, String> map, Map.Entry<Locale, String> mapEntry) {

		if (map == null) {
			return Stream.of(
				mapEntry
			).collect(
				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
			);
		}

		if (mapEntry == null) {
			return map;
		}

		if (mapEntry.getValue() == null) {
			map.remove(mapEntry.getKey());

			return map;
		}

		Set<Map.Entry<Locale, String>> mapEntries = map.entrySet();

		return Stream.concat(
			mapEntries.stream(), Stream.of(mapEntry)
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue,
				(value1, value2) -> value2)
		);
	}

}