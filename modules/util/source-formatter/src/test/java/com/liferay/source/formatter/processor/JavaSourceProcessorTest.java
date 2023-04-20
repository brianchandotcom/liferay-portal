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
			"Use org.junit.Assert instead of org.testng.Assert, see LPS-55690");
	}

	@Test
	public void testAssignmentsAndSetCallsOrder() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"AssignmentsAndSetCallsOrder.testjava"
			).addExpectedMessage(
				"The variable assignment for 'appDeployments' should come " +
					"before the variable assignment for 'dataDefinitionId'",
				29
			).addExpectedMessage(
				"The variable assignment for 'settings' should come before " +
					"the variable assignment for 'type'",
				33
			).addExpectedMessage(
				"The variable assignment for 'type' should come before the " +
					"method calling 'setName'",
				42
			).addExpectedMessage(
				"The variable assignment for 'settings' should come before " +
					"the variable assignment for 'type'",
				48
			).addExpectedMessage(
				"The method calling 'setCompany' should come before the " +
					"method calling 'setName'",
				54
			));
	}

	@Test
	public void testBuilder() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"Builder.testjava"
			).addExpectedMessage(
				"Include method call 'hashMap.put' (32) in 'HashMapBuilder' " +
					"(28)",
				28
			).addExpectedMessage(
				"Inline variable definition 'company' (38) inside " +
					"'HashMapBuilder' (40), possibly by using a lambda " +
						"function",
				38
			).addExpectedMessage(
				"Null values are not allowed in 'HashMapBuilder'", 47
			).addExpectedMessage(
				"Use 'HashMapBuilder' (52, 54)", 52
			).addExpectedMessage(
				"Use 'HashMapBuilder' instead of new instance of 'HashMap'", 58
			));
	}

	@Test
	public void testChainPutForOrgJSONObject() throws Exception {
		test(
			"ChainPutForOrgJSONObject.testjava",
			"Chaining on 'jsonObject.put' is preferred", 27);
	}

	@Test
	public void testCollapseImports() throws Exception {
		test("CollapseImports.testjava");
	}

	@Test
	public void testCombineLines() throws Exception {
		test("CombineLines.testjava");
	}

	@Test
	public void testCommentStyling() throws Exception {
		test("CommentStyling.testjava");
	}

	@Test
	public void testConstructorParameterOrder() throws Exception {
		test("ConstructorParameterOrder.testjava");
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
		test("ExceedMaxLineLength.testjava", "> 80", 36);
	}

	@Test
	public void testExceptionMapper() throws Exception {
		test(
			"ExceptionMapperService.testjava",
			"The value of 'osgi.jaxrs.name' should end with 'ExceptionMapper'",
			30);
	}

	@Test
	public void testExceptionPrintStackTrace() throws Exception {
		test(
			"ExceptionPrintStackTrace.testjava",
			"Avoid using method 'printStackTrace'", 31);
	}

	@Test
	public void testExceptionVariableName() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"ExceptionVariableName.testjava"
			).addExpectedMessage(
				"Rename exception variable 'e' to 'configurationException'", 37
			).addExpectedMessage(
				"Rename exception variable 'e' to 'configurationException'", 50
			).addExpectedMessage(
				"Rename exception variable 're' to 'exception'", 61
			).addExpectedMessage(
				"Rename exception variable 'ioe' to 'ioException1'", 66
			).addExpectedMessage(
				"Rename exception variable 'oie' to 'ioException2'", 70
			).addExpectedMessage(
				"Rename exception variable 'ioe1' to 'ioException1'", 81
			).addExpectedMessage(
				"Rename exception variable 'ioe2' to 'ioException2'", 85
			).addExpectedMessage(
				"Rename exception variable 'ioe1' to 'ioException'", 96
			).addExpectedMessage(
				"Rename exception variable 'ioe2' to 'ioException'", 102
			));
	}

	@Test
	public void testFeatureFlagsAnnotationTest() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"FeatureFlagsAnnotationTest.testjava"
			).addExpectedMessage(
				"Use annotation '@FeatureFlags' instead of 'PropsUtil." +
					"addProperties' for feature flag",
				31
			).addExpectedMessage(
				"Use annotation '@FeatureFlags' instead of 'PropsUtil." +
					"addProperties' for feature flag",
				41
			).addExpectedMessage(
				"Use annotation '@FeatureFlags' instead of 'PropsUtil." +
					"addProperties' for feature flag",
				51
			));
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
	public void testGetFeatureFlag() throws Exception {
		test(
			"GetFeatureFlag.testjava",
			"Use 'FeatureFlagManagerUtil.isEnabled' instead of " +
				"'PropsUtil.get' for feature flag",
			26);
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
	public void testImmediateAttribute() throws Exception {
		test(
			"ImmediateAttribute.testjava",
			"Do not use 'immediate = true' in @Component");
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
	public void testIncorrectEmptyLinesInUpgradeProcess() throws Exception {
		test("IncorrectEmptyLinesInUpgradeProcess.testjava");
	}

	@Test
	public void testIncorrectImports() throws Exception {
		test("IncorrectImports1.testjava");
		test(
			SourceProcessorTestParameters.create(
				"IncorrectImports2.testjava"
			).addExpectedMessage(
				"Illegal import: edu.emory.mathcs.backport.java"
			).addExpectedMessage(
				"Illegal import: jodd.util.StringPool"
			).addExpectedMessage(
				"Use ProxyUtil instead of java.lang.reflect.Proxy"
			));
	}

	@Test
	public void testIncorrectOperatorOrder() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"IncorrectOperatorOrder.testjava"
			).addExpectedMessage(
				"'3' should be on the right hand side of the operator", 53
			).addExpectedMessage(
				"'+3' should be on the right hand side of the operator", 57
			).addExpectedMessage(
				"'-3' should be on the right hand side of the operator", 61
			).addExpectedMessage(
				"'3' should be on the right hand side of the operator", 97
			).addExpectedMessage(
				"'+3' should be on the right hand side of the operator", 101
			).addExpectedMessage(
				"'-3' should be on the right hand side of the operator", 105
			).addExpectedMessage(
				"'3' should be on the right hand side of the operator", 141
			).addExpectedMessage(
				"'+3' should be on the right hand side of the operator", 145
			).addExpectedMessage(
				"'-3' should be on the right hand side of the operator", 149
			).addExpectedMessage(
				"'3' should be on the right hand side of the operator", 185
			).addExpectedMessage(
				"'+3' should be on the right hand side of the operator", 189
			).addExpectedMessage(
				"'-3' should be on the right hand side of the operator", 193
			).addExpectedMessage(
				"'3' should be on the right hand side of the operator", 229
			).addExpectedMessage(
				"'+3' should be on the right hand side of the operator", 233
			).addExpectedMessage(
				"'-3' should be on the right hand side of the operator", 237
			).addExpectedMessage(
				"'3' should be on the right hand side of the operator", 273
			).addExpectedMessage(
				"'+3' should be on the right hand side of the operator", 277
			).addExpectedMessage(
				"'-3' should be on the right hand side of the operator", 281
			));
	}

	@Test
	public void testIncorrectParameterNames() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"IncorrectParameterNames.testjava"
			).addExpectedMessage(
				"Parameter 'StringMap' must match pattern " +
					"'^[a-z][_a-zA-Z0-9]*$'",
				24
			).addExpectedMessage(
				"Parameter 'TestString' must match pattern " +
					"'^[a-z][_a-zA-Z0-9]*$'",
				28
			));
	}

	@Test
	public void testIncorrectVariableNames() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"IncorrectVariableNames1.testjava"
			).addExpectedMessage(
				"public constant '_TEST_1' of type 'int' must match pattern " +
					"'^[A-Z0-9][_A-Z0-9]*$'",
				22
			).addExpectedMessage(
				"Protected or public non-static field '_test2' must match " +
					"pattern '^[a-z0-9][_a-zA-Z0-9]*$'",
				28
			));
		test(
			"IncorrectVariableNames2.testjava",
			"private constant 'STRING_1' of type 'String' must match pattern " +
				"'^_[A-Z0-9][_A-Z0-9]*$'",
			26);
		test(
			SourceProcessorTestParameters.create(
				"IncorrectVariableNames3.testjava"
			).addExpectedMessage(
				"Local non-final variable 'TestMapWithARatherLongName' must " +
					"match pattern '^[a-z0-9][_a-zA-Z0-9]*$'",
				26
			).addExpectedMessage(
				"Local non-final variable 'TestString' must match pattern " +
					"'^[a-z0-9][_a-zA-Z0-9]*$'",
				29
			));
	}

	@Test
	public void testIncorrectWhitespace() throws Exception {
		test("IncorrectWhitespace.testjava");
	}

	@Test
	public void testInefficientStringMethods() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"InefficientStringMethods.testjava"
			).addExpectedMessage(
				"Use StringUtil.equalsIgnoreCase", 26
			).addExpectedMessage(
				"Use StringUtil.toLowerCase", 30
			).addExpectedMessage(
				"Use StringUtil.toUpperCase", 31
			));
	}

	@Test
	public void testJavaNewProblemInstantiationParameters() throws Exception {
		test("JavaNewProblemInstantiationParameters.testjava");
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
	public void testListUtilUsages() throws Exception {
		test(
			"ListUtilUsages.testjava",
			"Use 'ListUtil.isEmpty(list)' to simplify code", 25);
	}

	@Test
	public void testLogLevels() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"Levels.testjava"
			).addExpectedMessage(
				"Do not use _log.isErrorEnabled()", 27
			).addExpectedMessage(
				"Use _log.isDebugEnabled()", 36
			).addExpectedMessage(
				"Use _log.isDebugEnabled()", 41
			).addExpectedMessage(
				"Use _log.isInfoEnabled()", 53
			).addExpectedMessage(
				"Use _log.isTraceEnabled()", 58
			).addExpectedMessage(
				"Use _log.isWarnEnabled()", 68
			));
	}

	@Test
	public void testLogParameters() throws Exception {
		test("LogParameters.testjava");
	}

	@Test
	public void testMapBuilderGenerics() throws Exception {
		test("MapBuilderGenerics.testjava");
	}

	@Test
	public void testMissingAuthor() throws Exception {
		test("MissingAuthor.testjava", "Missing author", 20);
	}

	@Test
	public void testMissingDiamondOperator() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"MissingDiamondOperator.testjava"
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'ArrayList'", 45
			).addExpectedMessage(
				"Missing generic types '<String, String>' for type 'ArrayList'",
				47
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'ConcurrentHashMap'", 53
			).addExpectedMessage(
				"Missing diamond operator '<>' for type " +
					"'ConcurrentSkipListMap'",
				55
			).addExpectedMessage(
				"Missing diamond operator '<>' for type " +
					"'ConcurrentSkipListSet'",
				57
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'CopyOnWriteArraySet'",
				59
			).addExpectedMessage(
				"Missing generic types '<Position, String>' for type 'EnumMap'",
				61
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'HashMap'", 68
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'HashSet'", 70
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'Hashtable'", 72
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'IdentityHashMap'", 74
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'LinkedHashMap'", 77
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'LinkedHashSet'", 79
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'LinkedList'", 81
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'Stack'", 83
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'TreeMap'", 85
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'TreeSet'", 87
			).addExpectedMessage(
				"Missing diamond operator '<>' for type 'Vector'", 89
			));
	}

	@Test
	public void testMissingEmptyLines() throws Exception {
		test("MissingEmptyLines.testjava");
	}

	@Test
	public void testMissingEmptyLinesAfterMethodCalls() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"MissingEmptyLinesAfterMethodCalls.testjava"
			).addExpectedMessage(
				"There should be an empty line after 'registry.register'", 23
			).addExpectedMessage(
				"There should be an empty line after 'registry.register'", 24
			).addExpectedMessage(
				"There should be an empty line after 'registry.register'", 34
			));
	}

	@Test
	public void testMissingEmptyLinesBeforeMethodCalls() throws Exception {
		test(
			"MissingEmptyLinesBeforeMethodCalls.testjava",
			"There should be an empty line before 'portletPreferences.store'",
			26);
	}

	@Test
	public void testMissingEmptyLinesInInstanceInit() throws Exception {
		test(
			"MissingEmptyLinesInInstanceInit.testjava",
			"There should be an empty line after line '27'", 27);
	}

	@Test
	public void testMissingReferencePolicyDynamic() throws Exception {
		test(
			"MissingReferencePolicyDynamic.testjava",
			"When using 'cardinality = ReferenceCardinality.OPTIONAL' and " +
				"'policyOption = ReferencePolicyOption.GREEDY', always use " +
					"'policy = ReferencePolicy.DYNAMIC' as well",
			30);
	}

	@Test
	public void testMissingSerialVersionUID() throws Exception {
		test(
			"MissingSerialVersionUID.testjava",
			"Assign ProcessCallable implementation a serialVersionUID");
	}

	@Test
	public void testMoveUpgradeSteps() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"MoveUpgradeSteps.testjava"
			).addExpectedMessage(
				"Move 'alterTableAddColumn' call inside 'getPreUpgradeSteps' " +
					"method",
				26
			).addExpectedMessage(
				"Move 'alterTableAddColumn' call inside " +
					"'getPostUpgradeSteps' method",
				30
			));
	}

	@Test
	public void testNullAssertionInIfStatement() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"NullAssertionInIfStatement.testjava"
			).addExpectedMessage(
				"Null check for variable 'list' should always be first in " +
					"if-statement",
				25
			).addExpectedMessage(
				"Null check for variable 'list' should always be first in " +
					"if-statement",
				33
			).addExpectedMessage(
				"Null check for variable 'nameList1' should always be first " +
					"in if-statement",
				46
			));
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
	public void testRedundantLog() throws Exception {
		test(
			"RedundantLog.testjava",
			"Redundant log between line '26' and line '31'.", 26);
	}

	@Test
	public void testReferenceFieldAuthority() throws Exception {
		test(
			"BaseReferenceFieldAuthority.testjava",
			"@Reference annotation should be on protected fields rather than " +
				"private fields",
			28);
	}

	@Test
	public void testReferenceMethods() throws Exception {
		test(
			"ReferenceMethods.testjava",
			"Do not use @Reference on method testMethod, use @Reference on " +
				"field or ServiceTracker/ServiceTrackerList" +
					"/ServiceTrackerMap instead");
	}

	@Test
	public void testResultCountSet() throws Exception {
		test(
			"ResultSetCount.testjava", "Use resultSet.getInt(1) for count", 35);
	}

	@Test
	public void testRunSqlStyling() throws Exception {
		test("RunSqlStyling.testjava");
	}

	@Test
	public void testSecureRandomNumberGeneration() throws Exception {
		test(
			"SecureRandomNumberGeneration.testjava",
			"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
				"SecureRandom instead of java.security.SecureRandom, see " +
					"LPS-39508");
	}

	@Test
	public void testServiceProxyFactoryNewServiceTrackedInstance()
		throws Exception {

		test(
			"ServiceProxyFactoryNewServiceTrackedInstance.testjava",
			"Pass 'ServiceProxyFactoryNewServiceTrackedInstance.class' as " +
				"the second parameter when calling method " +
					"'ServiceProxyFactory.newServiceTrackedInstance'",
			30);
	}

	@Test
	public void testSimplifyListUtilCalls() throws Exception {
		test("SimplifyListUtilCalls.testjava");
	}

	@Test
	public void testSingleStatementClause() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"SingleStatementClause.testjava"
			).addExpectedMessage(
				"Use braces around if-statement clause", 23
			).addExpectedMessage(
				"Use braces around while-statement clause", 28
			).addExpectedMessage(
				"Use braces around for-statement clause", 31
			).addExpectedMessage(
				"Use braces around if-statement clause", 34
			));
	}

	@Test
	public void testSizeIsZeroCheck() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"SizeIsZero.testjava"
			).addExpectedMessage(
				"Use method '_testList.isEmpty()' instead", 28
			).addExpectedMessage(
				"Use method 'myList.isEmpty()' instead", 33
			));
	}

	@Test
	public void testSortAnnotationParameters() throws Exception {
		test("SortAnnotationParameters.testjava");
	}

	@Test
	public void testSortChainedMethodCalls() throws Exception {
		test("SortChainedMethodCalls.testjava");
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
		test("SortJavaTerms5.testjava");
	}

	@Test
	public void testSortMethodCalls() throws Exception {
		test("SortMethodCalls.testjava");
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
	public void testStringConcatenation() throws Exception {
		test(
			"StringConcatenation.testjava",
			"When concatenating multiple literal strings, only the first " +
				"literal string can start with ' '",
			28);
	}

	@Test
	public void testThrowsSystemException() throws Exception {
		test("ThrowsSystemException.testjava");
	}

	@Test
	public void testToJSONStringMethodCalls() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"ToJSONStringMethodCalls.testjava"
			).addExpectedMessage(
				"Use 'toString' instead of 'toJSONString'", 30
			).addExpectedMessage(
				"Use 'toString' instead of 'toJSONString'", 39
			).addExpectedMessage(
				"Use 'toString' instead of 'toJSONString'", 43
			).addExpectedMessage(
				"Use 'toString' instead of 'toJSONString'", 67
			));
	}

	@Test
	public void testTruncateLongLines() throws Exception {
		test("TruncateLongLines.testjava");
	}

	@Test
	public void testUnnecessaryConfigurationPolicy() throws Exception {
		test(
			"UnnecessaryConfigurationPolicy.testjava",
			"Remove 'configurationPolicy = ConfigurationPolicy.OPTIONAL' as " +
				"it is unnecessary",
			23);
	}

	@Test
	public void testUnnecessaryMethodCalls() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"UnnecessaryMethodCalls.testjava"
			).addExpectedMessage(
				"Use 'webCachePool' instead of calling method " +
					"'_getWebCachePool'",
				35
			).addExpectedMessage(
				"Use 'webCachePool' instead of calling method " +
					"'_getWebCachePool'",
				43
			).addExpectedMessage(
				"Use 'this.name' instead of calling method '_getName'", 47
			).addExpectedMessage(
				"Use 'webCachePool' instead of calling method " +
					"'_getWebCachePool'",
				53
			).addExpectedMessage(
				"Use 'webCachePool_1' instead of calling method " +
					"'getWebCachePool'",
				79
			));
	}

	@Test
	public void testUnnecessaryUpgradeProcessClass() throws Exception {
		test(
			"UnnecessaryUpgradeProcessClass.testjava",
			"No need to create 'UnnecessaryUpgradeProcessClass' class. " +
				"Replace it by inline calls to the 'UpgradeProcessFactory' " +
					"class in the registrator class",
			22);
	}

	@Test
	public void testUnusedImport() throws Exception {
		test("UnusedImport.testjava");
	}

	@Test
	public void testUnusedMethods() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"UnusedMethods.testjava"
			).addExpectedMessage(
				"Method '_getInteger' is unused", 33
			).addExpectedMessage(
				"Method '_getString' is unused", 41
			));
	}

	@Test
	public void testUnusedParameter() throws Exception {
		test("UnusedParameter.testjava", "Parameter 'color' is unused", 26);
	}

	@Test
	public void testUnusedVariable() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"UnusedVariable.testjava"
			).addExpectedMessage(
				"Variable 'matcher' is unused", 26
			).addExpectedMessage(
				"Variable 'hello' is unused", 29
			).addExpectedMessage(
				"Variable '_s' is unused", 41
			));
	}

	@Test
	public void testUpgradeDropTable() throws Exception {
		test("UpgradeDropTable.testjava");
	}

	@Test
	public void testUpgradeProcessUnnecessaryIfStatement() throws Exception {
		test(
			"UpgradeProcessUnnecessaryIfStatement1.testjava",
			"No need to use if-statement to wrap 'alterColumn*' and " +
				"'alterTable*' calls",
			26);
		test(
			"UpgradeProcessUnnecessaryIfStatement2.testjava",
			"No need to use if-statement to wrap 'alterColumn*' and " +
				"'alterTable*' calls",
			26);
	}

	@Test
	public void testUsePassedInVariable() throws Exception {
		test("UsePassedInVariable.testjava");
	}

}