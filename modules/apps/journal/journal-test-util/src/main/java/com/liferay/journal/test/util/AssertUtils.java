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

package com.liferay.journal.test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author     André de Oliveira
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class AssertUtils {

	public static void assertEquals(
		String message, Map<?, ?> expectedMap, Map<?, ?> actualMap) {

		Assert.assertEquals(
			message, _toString(expectedMap), _toString(actualMap));
	}

	private static String _toString(Map<?, ?> map) {
		List<String> list = new ArrayList<>(map.size());

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			list.add(entry.toString());
		}

		Collections.sort(list);

		return list.toString();
	}

}