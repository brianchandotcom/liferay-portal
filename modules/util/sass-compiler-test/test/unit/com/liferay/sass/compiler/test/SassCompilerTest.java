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

import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Truong
 * @author Greg Amerson
 */
public class SassCompilerTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_rubySassCompiler = new RubySassCompiler();
		_jniSassCompiler = new JniSassCompiler();
	}

	@Test
	public void testBoxShadowTransparent() throws Exception {
		String input = "foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }";
		String expectedOutput = stripNewLines(
			"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }");

		String jniOutput = _jniSassCompiler.compileString(input, "", "");
		Assert.assertEquals(expectedOutput, stripNewLines(jniOutput));

		String rubyOutput = _jniSassCompiler.compileString(input, "", "");
		Assert.assertEquals(expectedOutput, stripNewLines(rubyOutput));
	}

	@Test
	public void testCompileFile() throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource("dependencies/sass-spec");

		File sassSpecDir = new File(url.toURI());

		for (File testDir : sassSpecDir.listFiles()) {
			File inputFile = new File(testDir, "input.scss");

			if (!inputFile.exists()) {
				continue;
			}

			String inputPath = inputFile.getCanonicalPath();

			File expectedOutputFile = new File(testDir, "expected_output.css");
			String expectedOutput = stripNewLines(
				read(expectedOutputFile.toPath()));

			String jniOutput = _jniSassCompiler.compileFile(inputPath, "", "");
			Assert.assertEquals(expectedOutput, stripNewLines(jniOutput));

			String rubyOutput = _rubySassCompiler.compileFile(
				inputPath, "", "");
			Assert.assertEquals(expectedOutput, stripNewLines(rubyOutput));
		}
	}

	@Test
	public void testCompiler() {
		Assert.assertNotNull(_rubySassCompiler);
		Assert.assertNotNull(_jniSassCompiler);
	}

	@Test
	public void testCompileString() throws Exception {
		String input = "foo { margin: 21px * 2; }";
		String expectedOutput = stripNewLines("foo { margin: 42px; }");

		String jniOutput = _jniSassCompiler.compileString(input, "", "");
		Assert.assertEquals(expectedOutput, stripNewLines(jniOutput));

		String rubyOutput = _rubySassCompiler.compileString(input, "", "");
		Assert.assertEquals(expectedOutput, stripNewLines(rubyOutput));
	}

	protected String read(Path filePath) throws Exception {
		String content = new String(Files.readAllBytes(filePath));

		return stripNewLines(content);
	}

	protected String stripNewLines(String string) {
		string = string.replaceAll("\\n|\\r", "");

		return string.replaceAll("\\s+", " ");
	}

	private static SassCompiler _jniSassCompiler;
	private static SassCompiler _rubySassCompiler;

}