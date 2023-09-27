/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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