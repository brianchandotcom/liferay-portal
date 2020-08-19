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

package com.liferay.portal.remote.cors.internal;

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class URLToCORSSupportMapperPerformanceTest {

	@Before
	public void setUp() {
		urlToCORSSupportMapper = createURLToCORSSupportMapper(corsSupports);
	}

	@Test
	public void testGet() {
		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (KeyValuePair keyValuePair : _expectedMatches) {
				urlToCORSSupportMapper.get(keyValuePair.getKey());
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 2000);
	}

	protected URLToCORSSupportMapper createURLToCORSSupportMapper(
		Map<String, CORSSupport> corsSupports) {

		return new URLToCORSSupportMapper(corsSupports);
	}

	protected URLToCORSSupportMapper urlToCORSSupportMapper;

}