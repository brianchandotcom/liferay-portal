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
 * @author Hugo Huijser
 */
public class JavaSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testAnnotationParameterImports() throws Exception {
		test("AnnotationParameterImports.testjava");
	}

	@Test
	public void testAssertUsage() throws Exception {
		test(
			"AssertUsage.testjava",
			"Use org.junit.Assert instead of org.testng.Assert:");
	}

	@Test
	public void testCombineLines() throws Exception {
		test("CombineLines.testjava");
	}

	@Test
	public void testConstructorParameterOrder() throws Exception {
		test(
			"ConstructorParameterOrder.testjava",
			"Constructor parameter order attribute:");
	}

	@Test
	public void testDiamondOperator() throws Exception {
		test("DiamondOperator.testjava");
	}

	@Test
	public void testExceedMaxLineLength() throws Exception {
		test("ExceedMaxLineLength.testjava", "> 80:", 37);
	}

	@Test
	public void testExceptionVariableName() throws Exception {
		test("ExceptionVariableName.testjava");
	}

	@Test
	public void testFormatAnnotations() throws Exception {
		test("FormatAnnotations.testjava");
	}

	@Test
	public void testFormatImports() throws Exception {
		test("FormatImports.testjava");
	}

	@Test
	public void testIfClauseIncorrectLineBreaks() throws Exception {
		test("IfClauseIncorrectLineBreaks.testjava");
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
		test("IncorrectClose.testjava");
	}

	@Test
	public void testIncorrectImports() throws Exception {
		test("IncorrectImports1.testjava");
		test(
			"IncorrectImports2.testjava",
			new String[] {
				"edu.emory.mathcs.backport.java:", "jodd.util.StringPool:",
				"Proxy:"
			});
	}

	@Test
	public void testIncorrectLineBreaks() throws Exception {
		test(
			"IncorrectLineBreaks1.testjava",
			new String[] {
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:", "line break:", "line break:",
				"line break:", "line break:"
			},
			new Integer[] {
				31, 35, 43, 47, 49, 52, 55, 59, 62, 67, 71, 77, 81, 87, 98, 111,
				115, 125
			});
		test("IncorrectLineBreaks2.testjava");
	}

	@Test
	public void testIncorrectTabs() throws Exception {
		test(
			"IncorrectTabs.testjava",
			new String[] {
				"Incorrect tab or line break:", "Incorrect tab or line break:",
				"Incorrect tab or line break:"
			},
			new Integer[] {27, 31, 37});
	}

	@Test
	public void testIncorrectVariableNames() throws Exception {
		test(
			"IncorrectVariableNames1.testjava",
			new String[] {
				"Only private var should start with underscore:",
				"Only private var should start with underscore:"
			},
			new Integer[] {22, 28});
		test("IncorrectVariableNames2.testjava");
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testjava");
	}

	@Test
	public void testInefficientStringMethods() throws Exception {
		test(
			"InefficientStringMethods.testjava",
			new String[] {
				"Use StringUtil.equalsIgnoreCase:",
				"Use StringUtil.toLowerCase:", "Use StringUtil.toUpperCase:"
			},
			new Integer[] {26, 30, 31});
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
	public void testMissingAuthor() throws Exception {
		test("MissingAuthor.testjava", "Missing author:");
	}

	@Test
	public void testMissingEmptyLines() throws Exception {
		test("MissingEmptyLines.testjava");
	}

	@Test
	public void testMissingSerialVersionUID() throws Exception {
		test(
			"MissingSerialVersionUID.testjava",
			"Assign ProcessCallable implementation a serialVersionUID:");
	}

	@Test
	public void testPackagePath() throws Exception {
		test("PackagePath.testjava", "Incorrect package path:");
	}

	@Test
	public void testProxyUsage() throws Exception {
		test("ProxyUsage.testjava", "Proxy:");
	}

	@Test
	public void testRedundantCommas() throws Exception {
		test("RedundantCommas.testjava");
	}

	@Test
	public void testSecureRandomNumberGeneration() throws Exception {
		test(
			"SecureRandomNumberGeneration.testjava",
			"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
				"SecureRandom instead of java.security.SecureRandom:");
	}

	@Test
	public void testSortAnnotationParameters() throws Exception {
		test(
			"SortAnnotationParameters.testjava",
			new String[] {
				"sort: @Component#immediate",
				"sort: method#@Transactional#propagation"
			});
	}

	@Test
	public void testSortExceptions() throws Exception {
		test("SortExceptions.testjava");
	}

	@Test
	public void testSortJavaTerms() throws Exception {
		test("SortJavaTerms1.testjava");
		test("SortJavaTerms2.testjava");
		test("SortJavaTerms3.testjava");
	}

	@Test
	public void testSortMethodsWithAnnotatedParameters() throws Exception {
		test("SortMethodsWithAnnotatedParameters.testjava");
	}

	@Test
	public void testStaticFinalLog() throws Exception {
		test("StaticFinalLog.testjava");
	}

	@Test
	public void testThrowsSystemException() throws Exception {
		test("ThrowsSystemException.testjava");
	}

	@Test
	public void testTruncateLongLines() throws Exception {
		test("TruncateLongLines.testjava");
	}

	@Test
	public void testUnusedImport() throws Exception {
		test("UnusedImport.testjava");
	}

	@Test
	public void testUnusedParameter() throws Exception {
		test("UnusedParameter.testjava", "Unused parameter color:", 26);
	}

}