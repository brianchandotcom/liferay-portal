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

package com.liferay.sass.compiler.ruby.internal;

import com.liferay.sass.compiler.SassCompiler;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class RubySassCompilerTest {

	@Test
	public void testBoxShadowTransparent() throws Exception {
		SassCompiler sassCompiler = new RubySassCompiler();

		Assert.assertNotNull(sassCompiler);

		String expectedOutput =
			"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }";
		String actualOutput = sassCompiler.compileString(
			"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }", "");

		Assert.assertEquals(
			stripNewLines(expectedOutput), stripNewLines(actualOutput));
	}

	@Test
	public void testCompileFile() throws Exception {
		SassCompiler sassCompiler = new RubySassCompiler();

		Assert.assertNotNull(sassCompiler);

		File sassSpecDir = new File(
			"../sass-compiler-jni/test/unit/com/liferay/sass/compiler/jni" +
				"/internal/dependencies/sass-spec");

		for (File testDir : sassSpecDir.listFiles()) {
			File inputFile = new File(testDir, "input.scss");

			if (!inputFile.exists()) {
				continue;
			}

			String actualOutput = sassCompiler.compileFile(
				inputFile.getCanonicalPath(), "");

			Assert.assertNotNull(actualOutput);

			File expectedOutputFile = new File(testDir, "expected_output.css");

			String expectedOutput = read(expectedOutputFile.toPath());

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileFileWithSourceMaps() throws Exception {
		SassCompiler sassCompiler = new RubySassCompiler();

		Assert.assertNotNull(sassCompiler);

		Class<?> clazz = getClass();

		File sourceMapsDir = new File(
			"../sass-compiler-jni/test/unit/com/liferay/sass/compiler/jni" +
				"/internal/dependencies/sourcemaps");

		File inputFile = new File(sourceMapsDir, "input.scss");
		File sourceMapFile = new File(sourceMapsDir, "input.css.map");
		sourceMapFile.deleteOnExit();

		Assert.assertFalse(sourceMapFile.exists());

		String actualOutput = sassCompiler.compileFile(
			inputFile.getCanonicalPath(), "", true);

		Assert.assertNotNull(actualOutput);
		Assert.assertTrue(sourceMapFile.exists());

		File expectedOutputFile = new File(
			sourceMapsDir, "expected_output.css");

		String expectedOutput = read(expectedOutputFile.toPath());

		Assert.assertEquals(
			stripNewLines(expectedOutput), stripNewLines(actualOutput));
	}

	@Test
	public void testCompileString() throws Exception {
		SassCompiler sassCompiler = new RubySassCompiler();

		Assert.assertNotNull(sassCompiler);

		String expectedOutput = "foo { margin: 42px; }";
		String actualOutput = sassCompiler.compileString(
			"foo { margin: 21px * 2; }", "");

		Assert.assertEquals(
			stripNewLines(expectedOutput), stripNewLines(actualOutput));
	}

	@Test
	public void testCompileStringWithSourceMaps() throws Exception {
		SassCompiler sassCompiler = new RubySassCompiler();

		Assert.assertNotNull(sassCompiler);

		Class<?> clazz = getClass();

		File sourceMapsDir = new File(
			"../sass-compiler-jni/test/unit/com/liferay/sass/compiler/jni" +
				"/internal/dependencies/sourcemaps");

		File inputFile = new File(sourceMapsDir, "input.scss");
		File sourceMapFile = new File(
			sourceMapsDir, ".sass-cache/input.css.map");
		sourceMapFile.deleteOnExit();

		Assert.assertFalse(sourceMapFile.exists());

		String input = read(inputFile.toPath());

		String actualOutput = sassCompiler.compileString(
			input, inputFile.getCanonicalPath(), "", true,
			sourceMapFile.getCanonicalPath());

		Assert.assertNotNull(actualOutput);
		Assert.assertTrue(sourceMapFile.exists());

		File expectedOutputFile = new File(
			sourceMapsDir, "expected_custom_output.css");

		String expectedOutput = read(expectedOutputFile.toPath());

		Assert.assertEquals(
			stripNewLines(expectedOutput), stripNewLines(actualOutput));
	}

	protected String read(Path filePath) throws Exception {
		String content = new String(Files.readAllBytes(filePath));

		return stripNewLines(content);
	}

	protected String stripNewLines(String string) {
		string = string.replaceAll("\\n|\\r", "");

		return string.replaceAll("\\s+", " ");
	}

}