/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class YMLSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testBooleanValues() throws Exception {
		test("BooleanValues.testyaml");
	}

	@Test
	public void testFormatDescriptions() throws Exception {
		test("FormatDescriptions.testyaml");
	}

	@Test
	public void testFormatIndentations() throws Exception {
		test("FormatIndentations.testyaml");
	}

	@Test
	public void testFormatReviewComments() throws Exception {
		test("FormatReviewComments.testyaml");
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testyaml");
	}

	@Test
	public void testSortDefinitions() throws Exception {
		test("SortDefinitions.testyaml");
	}

	@Test
	public void testSortDefinitionsOnHelmYaml() throws Exception {
		test("SortDefinitionsOnHelmYaml.testyaml");
	}

	@Test
	public void testSortFeatureFlags() throws Exception {
		test("SortFeatureFlags.testyaml");
	}

	//

	// 	@Test

	//	public void testLongLinesCheck() throws Exception {
	//		test("ExceedMaxLineLength.testyaml");
	//	}
	//
	//
	@Test
	public void testSortInPaths() throws Exception {
		test("SortInPaths.testyaml");
	}

	@Test
	public void testSortMountPaths() throws Exception {
		test("SortMountPaths.testyaml");
	}

	@Test
	public void testSortParameters() throws Exception {
		test("SortParameters.testyaml");
	}

	//

	// 	@Test

	//	public void testStylingCheck() throws Exception {
	//		test("StylingCheck.testyaml");
	//	}

}