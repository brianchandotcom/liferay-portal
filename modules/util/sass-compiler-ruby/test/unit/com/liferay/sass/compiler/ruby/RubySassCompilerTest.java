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

package com.liferay.sass.compiler.ruby;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class RubySassCompilerTest {

	@Test
	public void testCompileFile() throws Exception {
		try (RubySassCompiler rubySassCompiler = new RubySassCompiler()) {
			Assert.assertNotNull(rubySassCompiler);

			String expectedOutput = "foo {\n  margin: 42px; }\n";
			String actualOutput = rubySassCompiler.compileFile(
				System.getProperty("user.dir") + "/test-classes/unit/com" +
					"/liferay/sass/compiler/ruby/dependencies/input.scss",
				"", "");

			Assert.assertEquals(expectedOutput, actualOutput);
		}
	}

	@Test
	public void testCompileString() throws Exception {
		try (RubySassCompiler rubySassCompiler = new RubySassCompiler()) {
			Assert.assertNotNull(rubySassCompiler);

			String expectedOutput = "foo {\n  margin: 42px; }\n";
			String actualOutput = rubySassCompiler.compileString(
				"foo { margin: 21px * 2; }", "", "");

			Assert.assertEquals(expectedOutput, actualOutput);
		}
	}

}