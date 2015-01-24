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
	public void testCompile_asset_category_selector() throws Exception {
		SassCompiler compiler = new SassCompiler();
		assumeNotNull(compiler);

		String output = compiler.compileFile(
			_BASE_RESOURCES + "_asset_category_selector.scss", "", "");
		assertNotNull(output);
		String expected = readFileContents(
			_BASE_RESOURCES + "_asset_category_selector.css");
		assertEquals(stripNewLines(expected), stripNewLines(output));
	}

	@Test
	public void testCompile_compass_app_view_entry() throws Exception {
		SassCompiler compiler = new SassCompiler();
		assumeNotNull(compiler);

		String inputFile = _BASE_RESOURCES + "/compass/_app_view_entry.scss";
		String includePath = _BASE_RESOURCES + "/compass/common";
		String output = compiler.compileFile(inputFile, includePath, "");

		assertNotNull(output);
		String expected = readFileContents(
			_BASE_RESOURCES + "/compass/_app_view_entry.css");
		assertEquals(stripNewLines(expected), stripNewLines(output));
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
		getBaseDir() + "/com/liferay/tools/jsc/testfiles/";

}