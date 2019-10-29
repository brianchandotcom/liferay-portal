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

package com.liferay.sass.compiler.jsass.internal;

import com.liferay.sass.compiler.SassCompiler;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Truong
 */
public class JSassCompilerTest {

	@Test
	public void testBoxShadowTransparent() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			String expectedOutput =
				"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }";
			String actualOutput = sassCompiler.compileString(
				"foo { box-shadow: 2px 4px 7px rgba(0, 0, 0, 0.5); }", "");

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileFileClayCss() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			File clayCssDir = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/clay-css");

			for (File inputFile : clayCssDir.listFiles()) {
				if (inputFile.isDirectory()) {
					continue;
				}

				String fileName = inputFile.getName();

				if (fileName.startsWith("_") || !fileName.endsWith("scss")) {
					continue;
				}

				String actualOutput = sassCompiler.compileFile(
					inputFile.getCanonicalPath(), "", true);

				Assert.assertNotNull("Testing: " + fileName, actualOutput);

				String expectedOutputFileName =
					"expected" + File.separator +
						fileName.replace("scss", "css");

				File expectedOutputFile = new File(
					clayCssDir, expectedOutputFileName);

				if (expectedOutputFile.exists()) {
					String expectedOutput = read(expectedOutputFile.toPath());

					Assert.assertEquals(
						"Testing: " + fileName, stripNewLines(expectedOutput),
						stripNewLines(actualOutput));
				}
			}
		}
	}

	@Test
	public void testCompileFileSassSpec() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			File sassSpecDir = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/sass-spec");

			for (File testDir : sassSpecDir.listFiles()) {
				File inputFile = new File(testDir, "input.scss");

				String dirName = testDir.getName();

				if (!inputFile.exists() || dirName.endsWith("-4.0")) {
					continue;
				}

				String actualOutput = sassCompiler.compileFile(
					inputFile.getCanonicalPath(), "", false);

				Assert.assertNotNull("Testing: " + dirName, actualOutput);

				File expectedOutputFile = new File(
					testDir, "expected_output.css");

				String expectedOutput = read(expectedOutputFile.toPath());

				Assert.assertEquals(
					"Testing: " + dirName, stripNewLines(expectedOutput),
					stripNewLines(actualOutput));
			}
		}
	}

	@Test
	public void testCompileFileSassVariableWithUnicode() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			File inputDir = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/");

			File inputFile = new File(inputDir, "/unicode/input.scss");

			String actualOutput = sassCompiler.compileFile(
				inputFile.getCanonicalPath(), "");

			Assert.assertNotNull(actualOutput);

			File expectedOutputFile = new File(
				inputDir, "/unicode/expected_output.css");

			String expectedOutput = read(expectedOutputFile.toPath());

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileFileWithSourceMap() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			File inputDir = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/sass-spec/14_imports");

			File sourceMapFile = new File(
				inputDir, ".sass-cache/input.css.map");

			sourceMapFile.deleteOnExit();

			Assert.assertFalse(sourceMapFile.exists());

			File inputFile = new File(inputDir, "input.scss");

			String actualOutput = sassCompiler.compileFile(
				inputFile.getCanonicalPath(), "", true,
				sourceMapFile.getCanonicalPath());

			Assert.assertNotNull(actualOutput);

			Assert.assertTrue(sourceMapFile.exists());

			File expectedOutputFile = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/sourcemap",
				"expected_output_custom_source_map.css");

			String expectedOutput = read(expectedOutputFile.toPath());

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileString() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			String expectedOutput = "foo { margin: 42px; }";
			String actualOutput = sassCompiler.compileString(
				"foo { margin: 21px * 2; }", "");

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileStringSassVariableWithUnicode() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			File inputDir = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/");

			File inputFile = new File(inputDir, "/unicode/input.scss");

			String input = read(inputFile.toPath());

			String actualOutput = sassCompiler.compileString(input, "");

			Assert.assertNotNull(actualOutput);

			File expectedOutputFile = new File(
				inputDir, "/unicode/expected_output.css");

			String expectedOutput = read(expectedOutputFile.toPath());

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testCompileStringWithSourceMap() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler()) {
			File inputDir = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/sass-spec/14_imports");

			File sourceMapFile = new File(inputDir, "input.css.map");

			sourceMapFile.deleteOnExit();

			Assert.assertFalse(sourceMapFile.exists());

			File inputFile = new File(inputDir, "input.scss");

			String input = read(inputFile.toPath());

			String actualOutput = sassCompiler.compileString(
				input, inputFile.getCanonicalPath(), "", true);

			Assert.assertNotNull(actualOutput);

			Assert.assertTrue(sourceMapFile.exists());

			File expectedOutputFile = new File(
				"../sass-compiler-jni/test-classes/unit/com/liferay/sass" +
					"/compiler/jni/internal/dependencies/sourcemap",
				"expected_output.css");

			String expectedOutput = read(expectedOutputFile.toPath());

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	@Test
	public void testSassPrecision() throws Exception {
		try (SassCompiler sassCompiler = new JSassCompiler(10)) {
			String expectedOutput = ".foo { line-height: 1.428571429; }";
			String actualOutput = sassCompiler.compileString(
				"$val: 1.428571429;.foo { line-height: $val; }", "");

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}

		try (SassCompiler sassCompiler = new JSassCompiler(3)) {
			String expectedOutput = ".foo { line-height: 1.429; }";
			String actualOutput = sassCompiler.compileString(
				"$val: 1.428571429;.foo { line-height: $val; }", "");

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	protected String read(Path filePath) throws Exception {
		return new String(Files.readAllBytes(filePath));
	}

	protected String stripNewLines(String string) {
		string = string.replaceAll("\\n|\\r", "");

		return string.replaceAll("\\s", "");
	}

}