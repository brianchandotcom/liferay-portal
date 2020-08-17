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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class URLToCORSSupportMapperTest
	extends BaseURLToCORSSupportMapperTestBase {

	@Before
	public void setUp() {
		Map<String, CORSSupport> corsSupports = buildCORSSupports();

		_dynamicURLToCORSSupportMapper = new DynamicURLToCORSSupportMapper(
			corsSupports);
		_simpleURLToCORSSupportMapper = new SimpleURLToCORSSupportMapper(
			corsSupports);
		_staticURLToCORSSupportMapper = new StaticURLToCORSSupportMapper(
			corsSupports);
	}

	@Test
	public void testDynamicGet() throws Exception {
		super.testGetCorrectness(_dynamicURLToCORSSupportMapper);
	}

	@Test
	public void testSimpleGet() throws Exception {
		super.testGetCorrectness(_simpleURLToCORSSupportMapper);
	}

	@Test
	public void testStaticGet() throws Exception {
		super.testGetCorrectness(_staticURLToCORSSupportMapper);
	}

	private URLToCORSSupportMapper _dynamicURLToCORSSupportMapper;
	private URLToCORSSupportMapper _simpleURLToCORSSupportMapper;
	private URLToCORSSupportMapper _staticURLToCORSSupportMapper;

}