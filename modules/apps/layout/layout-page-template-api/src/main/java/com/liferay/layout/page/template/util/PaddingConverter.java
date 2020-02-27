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

package com.liferay.layout.page.template.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Set;

/**
 * @author Rubén Pulido
 */
public class PaddingConverter {

	public static final Map<Integer, Integer> externalToInternalValuesMap =
		HashMapBuilder.put(
			0, 0
		).put(
			1, 3
		).put(
			2, 4
		).put(
			4, 5
		).put(
			6, 6
		).put(
			8, 7
		).put(
			10, 8
		).build();

	public static Integer convertToExternalValue(Integer value) {
		Set<Integer> externalValues = externalToInternalValuesMap.keySet();

		for (Integer externalValue : externalValues) {
			if (value.equals(externalToInternalValuesMap.get(externalValue))) {
				return externalValue;
			}
		}

		return null;
	}

	public static Integer convertToInternalValue(Integer label) {
		return externalToInternalValuesMap.get(label);
	}

}