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
	public void testAttributeOrder() throws Exception {
		test("AttributeOrder.testjava",
			new String[] {
				"Attribute 'dataDefinitionId' should come after attribute " +
					"'appDeployments'",
				"Attribute 'type' should come after attribute 'settings'",
				"Attribute 'type' should come after attribute 'settings'"
			},
			new Integer[] {29, 33, 45});
	}

	@Test
	public void testAnnotationParameterImports() throws Exception {
		test("AnnotationParameterImports.testjava");
	}

	@Test
	public void testAssertUsage() throws Exception {
		test(
			"AssertUsage.testjava",
			"Use org.junit.Assert instead of org.testng.Assert, see LPS-55690");
	}

	@Test
	public void testCombineLines() throws Exception {
		test("CombineLines.testjava");
	}

	@Test
	public void testConstructorParameterOrder() throws Exception {
		test(
			"ConstructorParameterOrder.testjava",
			"'_value = value;' should come before '_attribute = attribute;' " +
				"to match order of constructor parameters");
	}

	@Test
	public void testDeserializationSecurity() throws Exception {
		test(
			"DeserializationSecurity.testjava",
			"Use ProtectedObjectInputStream instead of new ObjectInputStream");
	}

	@Test
	public void testDiamondOperator() throws Exception {
		test("DiamondOperator.testjava");
	}

	@Test
	public void testDuplicateConstructors() throws Exception {
		test(
			"DuplicateConstructors.testjava",
			"Duplicate DuplicateConstructors");
	}

	@Test
	public void testDuplicateMethods() throws Exception {
		test("DuplicateMethods.testjava", "Duplicate method");
	}

	@Test
	public void testDuplicateVariables() throws Exception {
		test("DuplicateVariables.testjava", "Duplicate _STRING_2");
	}

	@Test
	public void testElseStatement() throws Exception {
		test("ElseStatement1.testjava");
		test(
			"ElseStatement2.testjava",
			"Else statement is not needed because of the 'return' statement " +
				"on line 26",
			28);
	}

	@Test
	public void testExceedMaxLineLength() throws Exception {
		test("ExceedMaxLineLength.testjava", "> 80", 37);
	}

	@Test
	public void testExceptionVariableName() throws Exception {
		test("ExceptionVariableName.testjava");
	}

	@Test
	public void testExceptionVariableNameForExceptionMapper() throws Exception {
		test(
			"ExceptionVariableNameForExceptionMapper.testjava",
			"Rename variable 'exception' to 'noSuchStructureException'",
			40);
	}

	@Test
	public void testFormatAnnotations() throws Exception {
		test("FormatAnnotations1.testjava");
		test("FormatAnnotations2.testjava");
	}

	@Test
	public void testFormatBooleanStatements() throws Exception {
		test("FormatBooleanStatements.testjava");
	}

	@Test
	public void testFormatImports() throws Exception {
		test("FormatImports.testjava");
	}

	@Test
	public void testFormatJSONObject() throws Exception {
		test("FormatJSONObject.testjava");
	}

	@Test
	public void testFormatReturnStatements() throws Exception {
		test("FormatReturnStatements.testjava");
	}

	@Test
	public void testIfClauseIncorrectLineBreaks() throws Exception {
		test("IfClauseIncorrectLineBreaks.testjava");
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
	public void testIncorrectCopyright() throws Exception {
		test("IncorrectCopyright.testjava", "File must start with copyright");
	}

	@Test
	public void testIncorrectIfStatement() throws Exception {
		//test("IncorrectIfStatement.testjava", "Incorrect if statement", 23);
	}

	@Test
	public void testIncorrectImports() throws Exception {
		test("IncorrectImports1.testjava");
		test(
			"IncorrectImports2.testjava",
			new String[] {
				"Illegal import: edu.emory.mathcs.backport.java",
				"Illegal import: jodd.util.StringPool",
				"Use ProxyUtil instead of java.lang.reflect.Proxy"
			});
	}

	@Test
	public void testIncorrectOperatorOrder() throws Exception {
		test(
			"IncorrectOperatorOrder.testjava",
			new String[] {
				"'3' should be on the right hand side of the operator",
				"'+3' should be on the right hand side of the operator",
				"'-3' should be on the right hand side of the operator",
				"'3' should be on the right hand side of the operator",
				"'+3' should be on the right hand side of the operator",
				"'-3' should be on the right hand side of the operator",
				"'3' should be on the right hand side of the operator",
				"'+3' should be on the right hand side of the operator",
				"'-3' should be on the right hand side of the operator",
				"'3' should be on the right hand side of the operator",
				"'+3' should be on the right hand side of the operator",
				"'-3' should be on the right hand side of the operator",
				"'3' should be on the right hand side of the operator",
				"'+3' should be on the right hand side of the operator",
				"'-3' should be on the right hand side of the operator",
				"'3' should be on the right hand side of the operator",
				"'+3' should be on the right hand side of the operator",
				"'-3' should be on the right hand side of the operator"
			},
			new Integer[] {
				53, 57, 61, 97, 101, 105, 141, 145, 149, 185, 189, 193, 229,
				233, 237, 273, 277, 281});
		}

	@Test
	public void testIncorrectParameterNames() throws Exception {
		test(
			"IncorrectParameterNames.testjava",
			new String[] {
				"Parameter 'StringMap' must match pattern " +
					"'^[a-z][a-zA-Z0-9]*$'",
				"Parameter 'TestString' must match pattern " +
					"'^[a-z][a-zA-Z0-9]*$'"
			},
			new Integer[] {24, 28});
	}

	@Test
	public void testIncorrectVariableNames() throws Exception {
		test(
			"IncorrectVariableNames1.testjava",
			new String[] {
				"public constant '_TEST_1' of type 'int' must match pattern " +
					"'^[A-Z0-9][_A-Z0-9]*$'",
				"Protected or public non-static field '_test2' must match " +
					"pattern '^[a-z0-9][_a-zA-Z0-9]*$'"
			},
			new Integer[] {22, 28});
		test(
			"IncorrectVariableNames2.testjava",
			"private constant 'STRING_1' of type 'String' must match pattern " +
				"'^_[A-Z0-9][_A-Z0-9]*$'",
			26);
		test(
			"IncorrectVariableNames3.testjava",
			new String[] {
				"Local non-final variable 'TestMapWithARatherLongName' must " +
					"match pattern '^[a-z0-9][_a-zA-Z0-9]*$'",
				"Local non-final variable 'TestString' must match pattern " +
					"'^[a-z0-9][_a-zA-Z0-9]*$'"
			},
			new Integer[] {26, 29});
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
				"Use StringUtil.equalsIgnoreCase", "Use StringUtil.toLowerCase",
				"Use StringUtil.toUpperCase"
			},
			new Integer[] {26, 30, 31});
	}

	@Test
	public void testJavaParameterAnnotations() throws Exception {
		test("JavaParameterAnnotations.testjava");
	}

	@Test
	public void testJavaTermDividers() throws Exception {
		test("JavaTermDividers.testjava");
	}

	@Test
	public void testJavaVariableFinalableFields1() throws Exception {
		test("JavaVariableFinalableFields1.testjava");
	}

	@Test
	public void testJavaVariableFinalableFields2() throws Exception {
		test("JavaVariableFinalableFields2.testjava");
	}

	@Test
	public void testLogLevels() throws Exception {
		test(
			"Levels.testjava",
			new String[] {
				"Do not use _log.isErrorEnabled()", "Use _log.isDebugEnabled()",
				"Use _log.isDebugEnabled()", "Use _log.isInfoEnabled()",
				"Use _log.isTraceEnabled()", "Use _log.isWarnEnabled()"
			},
			new Integer[] {27, 36, 41, 53, 58, 68});
	}

	@Test
	public void testLPS28266() throws Exception {
		test("LPS28266.testjava", "Use rs.getInt(1) for count");
	}

	@Test
	public void testMissingAuthor() throws Exception {
		test("MissingAuthor.testjava", "Missing author", 20);
	}

	@Test
	public void testMissingEmptyLines() throws Exception {
		test("MissingEmptyLines.testjava");
	}

	@Test
	public void testMissingDiamondOperator() throws Exception {
		test("MissingDiamondOperator.testjava",
			new String[] {
				"Missing diamond operator '<>' for type 'ArrayList'",
				"Missing generic types '<String, String>' for type 'ArrayList'",
				"Missing diamond operator '<>' for type 'ConcurrentHashMap'",
				"Missing diamond operator '<>' for type " +
					"'ConcurrentSkipListMap'",
				"Missing diamond operator '<>' for type " +
					"'ConcurrentSkipListSet'",
				"Missing diamond operator '<>' for type 'CopyOnWriteArraySet'",
				"Missing generic types '<Position, String>' for type 'EnumMap'",
				"Missing diamond operator '<>' for type 'HashMap'",
				"Missing generic types '<String, String>' for type 'HashMap'",
				"Missing diamond operator '<>' for type 'HashSet'",
				"Missing diamond operator '<>' for type 'Hashtable'",
				"Missing diamond operator '<>' for type 'IdentityHashMap'",
				"Missing diamond operator '<>' for type 'LinkedHashMap'",
				"Missing diamond operator '<>' for type 'LinkedHashSet'",
				"Missing diamond operator '<>' for type 'LinkedList'",
				"Missing diamond operator '<>' for type 'Stack'",
				"Missing diamond operator '<>' for type 'TreeMap'",
				"Missing diamond operator '<>' for type 'TreeSet'",
				"Missing diamond operator '<>' for type 'Vector'",
				"Missing generic types '<Map<String, String>>' for type " +
					"'ArrayList'",
				"Missing generic types '<String, String>' for type 'HashMap'",
				"Missing generic types '<String, String>' for type 'HashMap'"
			},
			new Integer[] {
				45, 47, 53, 55, 57, 59, 61, 68, 70, 76, 78, 80, 83, 85, 87, 89,
				91, 93, 95, 97, 99, 110
			});
	}

	@Test
	public void testMissingSerialVersionUID() throws Exception {
		test(
			"MissingSerialVersionUID.testjava",
			"Assign ProcessCallable implementation a serialVersionUID");
	}

	@Test
	public void testNullVariable() throws Exception {
		test("NullVariable.testjava");
	}

	@Test
	public void testPackageName() throws Exception {
		test(
			"PackageName.testjava",
			"The declared package 'com.liferay.source.formatter.hello.world' " +
				"does not match the expected package");
	}

	@Test
	public void testProxyUsage() throws Exception {
		test(
			"ProxyUsage.testjava",
			"Use ProxyUtil instead of java.lang.reflect.Proxy");
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
				"SecureRandom instead of java.security.SecureRandom, see " +
					"LPS-39058");
	}

	@Test
	public void testSortAnnotationParameters() throws Exception {
		test("SortAnnotationParameters.testjava");
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
		test("SortJavaTerms4.testjava");
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
		test("UnusedParameter.testjava", "Parameter 'color' is unused", 26);
	}

	@Test
	public void testUnusedVariable() throws Exception {
		test(
			"UnusedVariable.testjava",
			new String[] {
				"Variable 'matcher' is unused", "Variable '_s' is unused"
			},
			new Integer[] {26, 31});
	}

}