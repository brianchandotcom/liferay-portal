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

package com.liferay.tools.jsc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

import java.io.File;

import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class SassCompilerTest extends BaseTests {

	@Test
	public void testCompile() throws Exception {
		SassCompiler compiler = new SassCompiler();
		assumeNotNull(compiler);

		for (File testDir : new File(_BASE_RESOURCES).listFiles()) {
			File input = new File(testDir, "input.scss");

			if (input.exists()) {
				String output = compiler.compileFile(
					input.getCanonicalPath(), "", "");
				assertNotNull(output);

				File expectedOutput = new File(testDir, "expected_output.css");
				String expected = readFileContents(
					expectedOutput.getCanonicalPath());
				assertEquals(stripNewLines(expected), stripNewLines(output));
			}
		}
	}

	private static String getBaseDir() {
		if (new File("bin").exists()) {
			return "bin";
		}
		else {
			return "test-classes/unit";
		}
	}

	private static final String _BASE_RESOURCES =
		getBaseDir() + "/com/liferay/tools/jsc/dependencies/sass-spec/";

}