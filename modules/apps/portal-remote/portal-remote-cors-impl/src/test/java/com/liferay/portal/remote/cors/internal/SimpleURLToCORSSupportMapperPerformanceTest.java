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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class SimpleURLToCORSSupportMapperPerformanceTest
	extends BaseURLToCORSSupportMapperTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		setLogFilePath("simple-url-to-cors-support-mapper-performance.log");
	}

	@Before
	public void setUp() {
		_simpleURLToCORSSupportMapper = new URLToCORSSupportMapper(
			super.buildCORSSupports());
	}

	@Test
	public void testGet() throws Exception {
		super.testGetPerformance(_simpleURLToCORSSupportMapper);
	}

	private URLToCORSSupportMapper _simpleURLToCORSSupportMapper;

}