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
public class YMLSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testExceedMaxLineLength() throws Exception {
		test("ExceedMaxLineLength.testyaml", "> 120", 22);
	}

	@Test
	public void testIncorrectEmptyLines() throws Exception {
		test("IncorrectEmptyLines.testyaml");
	}

	@Test
	public void testIncorrectWhitespaceOnHelmYaml() throws Exception {
		test("IncorrectWhitespaceOnHelmYaml.testyaml");
	}

	@Test
	public void testReviewTags() throws Exception {
		test("ReviewTags.testyaml");
	}

	@Test
	public void testSortDefinitionsAndWhitespaceCheck() throws Exception {
		test("SortDefinitionsAndWhitespace.testyaml");
	}

	@Test
	public void testSortDefinitionsOnHelmYaml() throws Exception {
		test("SortDefinitionsOnHelmYaml.testyaml");
	}

	@Test
	public void testSortSpecificDefinitions() throws Exception {
		test("SortSpecificDefinitions.testyaml");
	}

	@Test
	public void testStyleBlock() throws Exception {
		test("StyleBlock.testyaml");
	}

}