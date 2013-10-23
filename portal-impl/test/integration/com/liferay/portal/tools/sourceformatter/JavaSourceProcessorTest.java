/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hugo Huijser
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JavaSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testCombineLines() throws Exception {
		test("CombineLines.testjava");
	}

	@Test
	public void testExceedMaxLineLength() throws Exception {
		test("ExceedMaxLineLength.testjava", "> 80:", 22);
	}

	@Test
	public void testFormatImports() throws Exception {
		test("FormatImports.testjava");
	}

	@Test
	public void testIncorrectClose() throws Exception {
		test("IncorrectClose.testjava", "}:");
	}

	@Test
	public void testIncorrectImports1() throws Exception {
		test("IncorrectImports1.testjava");
	}

	@Test
	public void testIncorrectImports2() throws Exception {
		test(
			"IncorrectImports2.testjava",
			new String[] {
				"Proxy:", "edu.emory.mathcs.backport.java:",
				"jodd.util.StringPool:"
			});
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testjava");
	}

	@Test
	public void testJavaTermDividers() throws Exception {
		test("JavaTermDividers.testjava");
	}

	@Test
	public void testSortJavaTerms1() throws Exception {
		test("SortJavaTerms1.testjava");
	}

	@Test
	public void testSortJavaTerms2() throws Exception {
		test("SortJavaTerms2.testjava");
	}

	@Test
	public void testSortJavaTerms3() throws Exception {
		test("SortJavaTerms3.testjava");
	}

	@Test
	public void testStaticFinalLog() throws Exception {
		test("StaticFinalLog.testjava");
	}

	@Test
	public void testUnusedImport() throws Exception {
		test("UnusedImport.testjava");
	}

}