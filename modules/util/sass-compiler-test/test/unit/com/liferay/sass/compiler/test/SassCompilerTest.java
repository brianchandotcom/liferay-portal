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

package com.liferay.sass.compiler.test;

import java.net.URL;

import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Truong
 */
public class SassCompilerTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_rubySassCompiler = new RubySassCompiler();
	}

	@Test
	public void testCompileFile() throws Exception {
		Assert.assertNotNull(_rubySassCompiler);

		String expectedOutput = "foo {\n  margin: 42px; }\n";

		Class<?> clazz = getClass();

		URL url = clazz.getResource("dependencies/input.scss");

		String actualOutput = _rubySassCompiler.compileFile(
			url.getFile(), "", "");

		Assert.assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testCompileString() throws Exception {
		Assert.assertNotNull(_rubySassCompiler);

		String expectedOutput = "foo {\n  margin: 42px; }\n";
		String actualOutput = _rubySassCompiler.compileString(
			"foo { margin: 21px * 2; }", "", "");

		Assert.assertEquals(expectedOutput, actualOutput);
	}

	private static SassCompiler _rubySassCompiler;

}