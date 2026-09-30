/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class JavaParserTest extends BaseSourceProcessorTestCase {

	@Test
	public void testJavaAnnotation() throws Exception {
		test("JavaAnnotation.testjava");
	}

	@Test
	public void testJavaArray() throws Exception {
		test("JavaArray.testjava");
	}

	@Test
	public void testJavaLogVariableDefinition() throws Exception {
		test("JavaLogVariableDefinition.testjava");
	}

	@Test
	public void testJavaModifierStrictfp() throws Exception {
		test("JavaModifierStrictfp.testjava");
	}

	@Test
	public void testJavaPatternMatchingForInstanceof() throws Exception {
		test("JavaPatternMatchingForInstanceof.testjava");
	}

	@Test
	public void testJavaPatternMatchingForSwitch() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"JavaPatternMatchingForSwitch.testjava"
			).addExpectedMessage(
				"Use \"if/else\" statement instead of \"switch\"", 14
			).addExpectedMessage(
				"Use \"if/else\" statement instead of \"switch\"", 24
			).addExpectedMessage(
				"Use \"if/else\" statement instead of \"switch\"", 33
			));
	}

	@Test
	public void testJavaRecordPatterns() throws Exception {
		test(
			"JavaRecordPatterns.testjava",
			"Use \"if/else\" statement instead of \"switch\"", 17);
	}

}