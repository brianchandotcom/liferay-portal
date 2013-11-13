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
	public void testIfClauseParentheses() throws Exception {
		test(
			"IfClauseParentheses.testjava",
			new String[] {
				"missing parentheses:", "missing parentheses:",
				"missing parentheses:", "missing parentheses:",
				"missing parentheses:", "redundant parentheses:",
				"redundant parentheses:", "redundant parentheses:"
			},
			new Integer[] {25, 29, 33, 39, 43, 43, 47, 51});
	}

	@Test
	public void testIfClauseWhitespace() throws Exception {
		test("IfClauseWhitespace.testjava");
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
	public void testIncorrectLineBreaks() throws Exception {
		test(
			"IncorrectLineBreaks.testjava",
			new String[] {
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:"
			},
			new Integer[] {
				23, 27, 31, 38, 42, 45, 48, 52, 55, 60, 66, 70, 74, 77
			});
	}

	@Test
	public void testIncorrectTabs() throws Exception {
		test(
			"IncorrectTabs.testjava",
			new String[] {"tab:", "tab:", "tab:", "tab:", "tab:"},
			new Integer[] {23, 27, 31, 37, 44});
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testjava");
	}

	@Test
	public void testInefficientStringMethods() throws Exception {
		String mainReleaseVersion = sourceFormatter.getMainReleaseVersion();

		if (!mainReleaseVersion.equals(
				BaseSourceProcessor.MAIN_RELEASE_VERSION_6_1_0)) {

			test(
				"InefficientStringMethods.testjava",
				new String[] {
					"Use StringUtil.equalsIgnoreCase:",
					"Use StringUtil.toLowerCase:", "Use StringUtil.toUpperCase:"
				},
				new Integer[] {26, 30, 31});
		}
	}

	@Test
	public void testJavaTermDividers() throws Exception {
		test("JavaTermDividers.testjava");
	}

	@Test
	public void testLogLevels() throws Exception {
		test(
			"Levels.testjava",
			new String[] {
				"Use _log.isDebugEnabled():", "Use _log.isDebugEnabled():",
				"Use _log.isInfoEnabled():", "Use _log.isTraceEnabled():",
				"Use _log.isWarnEnabled():"
			},
			new Integer[] {26, 31, 43, 48, 58});
	}

	@Test
	public void testLPS28266() throws Exception {
		test("LPS28266.testjava", "Use getInt(1) for count:");
	}

	@Test
	public void testMissingSerialVersionUID() throws Exception {
		test(
			"MissingSerialVersionUID.testjava",
			"Assign ProcessCallable implementation a serialVersionUID:");
	}

	@Test
	public void testSecureRandomNumberGeneration() throws Exception {
		test(
			"SecureRandomNumberGeneration.testjava",
			"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
				"SecureRandom instead of java.security.SecureRandom:");
	}

	@Test
	public void testSortAnnotations() throws Exception {
		test("SortAnnotations.testjava");
	}

	@Test
	public void testSortExceptions() throws Exception {
		test("SortExceptions.testjava");
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