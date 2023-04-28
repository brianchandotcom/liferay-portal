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

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class GradleSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testMissingLineBreaksAroundCurlyBraces() throws Exception {
		test("MissingLineBreaksAroundCurlyBraces.testgradle");
	}

	@Test
	public void testSortDependencies() throws Exception {
		test("SortDependencies.testgradle");
	}

	@Test
	public void testSortMapKeys() throws Exception {
		test("SortMapKeys1.testgradle");
		test("SortMapKeys2.testgradle");
		test("SortMapKeys3.testgradle");
		test("SortMapKeys4.testgradle");
		test("SortMapKeys5.testgradle");
	}

	@Test
	public void testStylingCheck() throws Exception {
		test("StylingCheck.testgradle");
	}

}