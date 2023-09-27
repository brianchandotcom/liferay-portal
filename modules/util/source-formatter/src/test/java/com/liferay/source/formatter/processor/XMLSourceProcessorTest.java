/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		test(
			"IncorrectEchoTagWithMessage.testxml",
			"Do not use self-closing tag for attribute 'message' in '" +
				"<echo>' tag",
			9);
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

	@Test
	public void testSortTagAttributes() throws Exception {
		test("SortTagAttributes.testjelly");
	}

}