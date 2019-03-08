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

package com.liferay.source.formatter;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class PoshiSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testDuplicatedParameters() throws Exception {
		test(
			"PoshiDuplicatedParameters.testmacro",
			new String[] {"Parameter 'userScreenName' is already used"},
			new Integer[] {4});
	}

	@Test
	public void testIncorrectAnnotationsOrder() throws Exception {
		test("IncorrectAnnotationsOrder.testmacro");
	}

	@Test
	public void testIncorrectLineBreak() throws Exception {
		test(
			"IncorrectLineBreak.testmacro",
			new String[] {
				"There should be a line break after ';'",
				"There should be a line break after ';'",
				"There should be a line break after ';'",
				"There should be a line break after ';'"
			},
			new Integer[] {2, 3, 17, 18});
	}

	@Test
	public void testIncorrectParameterOrder() throws Exception {
		test("PoshiIncorrectParameterOrder.testmacro");
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testmacro");
	}

}