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

package com.liferay.portal.search.internal.query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class MultiMatchQueryImplTest {

	@Test
	public void testMultiMatchQueryConstructor1() {
		Object value = null;

		Map<String, Float> fieldsBoosts = new HashMap<>();

		fieldsBoosts.put("test", null);

		MultiMatchQueryImpl multiMatchQueryImpl = new MultiMatchQueryImpl(
			value, fieldsBoosts);

		Assert.assertNotNull(multiMatchQueryImpl);
	}

	@Test
	public void testMultiMatchQueryConstructor2() {
		Object value = null;

		Set<String> fields = new HashSet<>();

		fields.add("test");

		MultiMatchQueryImpl multiMatchQueryImpl = new MultiMatchQueryImpl(
			value, fields);

		Assert.assertNotNull(multiMatchQueryImpl);
	}

	@Test
	public void testMultiMatchQueryConstructor3() {
		Object value = null;
		String[] fields = {"test"};

		MultiMatchQueryImpl multiMatchQueryImpl = new MultiMatchQueryImpl(
			value, fields);

		Assert.assertNotNull(multiMatchQueryImpl);
	}

}