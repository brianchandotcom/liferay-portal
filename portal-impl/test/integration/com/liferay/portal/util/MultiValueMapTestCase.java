/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.MultiValueMap;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public abstract class MultiValueMapTestCase {

	@Test
	public void testDelete() {
		String name = multiValueMap.getClass().getSimpleName();

		multiValueMap.put(5, "hello");
		multiValueMap.put(5, "world");
		multiValueMap.put(10, "world");
		multiValueMap.put(10, "wide");
		multiValueMap.put(10, "web");
		multiValueMap.put(5, "hello");

		Assert.assertEquals(name, 5, multiValueMap.size());

		multiValueMap.remove(10);

		Assert.assertEquals(name, 2, multiValueMap.size());

		Set<String> values = multiValueMap.getAll(5);

		Assert.assertNotNull(name, values);

		Assert.assertTrue(name, values.contains("hello"));
		Assert.assertTrue(name, values.contains("world"));
	}

	@Test
	public void testMultipleInsert() {
		String name = multiValueMap.getClass().getSimpleName();

		multiValueMap.put(5, "hello");
		multiValueMap.put(5, "world");
		multiValueMap.put(10, "world");
		multiValueMap.put(10, "wide");
		multiValueMap.put(10, "web");
		multiValueMap.put(5, "hello");

		Assert.assertEquals(name, 5, multiValueMap.size());

		Set<String> values = multiValueMap.getAll(5);

		Assert.assertNotNull(name, values);

		Assert.assertTrue(name, values.contains("hello"));
		Assert.assertTrue(name, values.contains("world"));
	}

	protected MultiValueMap<Integer, String> multiValueMap;

}