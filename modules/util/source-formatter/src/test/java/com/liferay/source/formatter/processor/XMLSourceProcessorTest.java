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
 * @author Karen Dang
 */
public class XMLSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectEmptyLines() throws Exception {
		test("IncorrectEmptyLines1.testxml");
		test("IncorrectEmptyLines2.testxml");
	}

	@Test
	public void testIncorrectMessageInEchoTag() throws Exception {
		test("IncorrectEchoTagWithMessage.testxml");
	}

	@Test
	public void testIncorrectTabs() throws Exception {
		test("IncorrectTabs1.testaction");
		test("IncorrectTabs2.testaction");
		test("IncorrectTabs3.testaction");
	}

	@Test
	public void testIncorrectXMLStyling() throws Exception {
		test("IncorrectXMLStyling.testxml");
	}

}