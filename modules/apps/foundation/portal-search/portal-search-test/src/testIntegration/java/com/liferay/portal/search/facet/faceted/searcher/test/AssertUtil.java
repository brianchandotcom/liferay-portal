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

package com.liferay.portal.search.facet.faceted.searcher.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class AssertUtil {

	public static void assertEquals(
		String message, Map<?, ?> expected, Map<?, ?> actual) {

		Assert.assertEquals(message, _toString(expected), _toString(actual));
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