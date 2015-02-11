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

package com.liferay.sass.compiler;

import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class SassCompilerTest {

	@Before
	public void extendSearchPath() throws Exception {
		NativeLibrary.addSearchPath(
			"sass", new File(getLibraryPath()).getCanonicalPath());
	}

	@Test
	public void testCompile() throws Exception {
		SassCompiler sassCompiler = new SassCompiler();

		Assert.assertNotNull(sassCompiler);

		File sassSpecDir = new File(
			getBaseDir(), "com/liferay/sass/compiler/dependencies/sass-spec");

		for (File testDir : sassSpecDir.listFiles()) {
			File inputFile = new File(testDir, "input.scss");

			if (!inputFile.exists()) {
				continue;
			}

			String actualOutput = sassCompiler.compileFile(
				inputFile.getCanonicalPath(), "", "");

			Assert.assertNotNull(actualOutput);

			File expectedOutputFile = new File(testDir, "expected_output.css");

			String expectedOutput = read(expectedOutputFile.getCanonicalPath());

			Assert.assertEquals(
				stripNewLines(expectedOutput), stripNewLines(actualOutput));
		}
	}

	protected String getBaseDir() {
		File binDir = new File("bin");

		if (binDir.exists()) {
			return "bin";
		}

		return "test-classes/unit";
	}

	private String getLibraryPath() {
		StringBuilder sb = new StringBuilder("resources/");

		sb.append(
			Platform.isLinux() ? "linux" :
			Platform.isMac() ? "darwin" :
			Platform.isWindows() ? "win32" : "");

		if (!Platform.isMac()) {
			sb.append("-x86");

			if (Platform.is64Bit()) {
				sb.append("-64");
			}
		}

		return sb.toString();
	}

	protected String read(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = new char[0x10000];

		Reader reader = new InputStreamReader(inputStream, "UTF-8");

		int read = 0;

		do {
			read = reader.read(chars, 0, chars.length);

			if (read > 0) {
				sb.append(chars, 0, read);
			}
		}
		while (read >= 0);

		inputStream.close();

		return sb.toString();
	}

	protected String read(String fileName) throws Exception {
		String content = read(new FileInputStream(new File(fileName)));

		return content.replaceAll("\\r", "");
	}

	protected String stripNewLines(String string) {
		string = string.replaceAll("\\n|\\r", "");

		return string.replaceAll("\\s+", " ");
	}

}