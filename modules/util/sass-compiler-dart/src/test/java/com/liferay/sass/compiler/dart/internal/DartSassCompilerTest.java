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

package com.liferay.sass.compiler.dart.internal;

import com.liferay.sass.compiler.SassCompiler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class DartSassCompilerTest {

	@Test
	public void testBoxShadowTransparent() throws Exception {
		try (SassCompiler sassCompiler = new DartSassCompiler()) {
			String expectedOutput =
				"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }";
			String actualOutput = sassCompiler.compileString(
				"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }", "");

			Assert.assertEquals(
				_stripNewLines(expectedOutput), _stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileString() throws Exception {
		try (SassCompiler sassCompiler = new DartSassCompiler()) {
			String expectedOutput = "foo { margin: 42px; }";
			String actualOutput = sassCompiler.compileString(
				"foo { margin: 21px * 2; }", "");

			Assert.assertEquals(
				_stripNewLines(expectedOutput), _stripNewLines(actualOutput));
		}
	}

	private String _stripNewLines(String string) {
		string = string.replaceAll("\\n|\\r", "");

		return string.replaceAll("\\s", "");
	}

}