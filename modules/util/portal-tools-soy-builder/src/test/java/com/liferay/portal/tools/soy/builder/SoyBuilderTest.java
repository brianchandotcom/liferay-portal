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

package com.liferay.portal.tools.soy.builder;

import static org.junit.Assert.assertTrue;

import com.liferay.portal.tools.soy.builder.internal.util.FileUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.List;

import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class SoyBuilderTest {

	@Test
	public void testSoyBuilderExecution() throws Exception {
		File testDir = new File("build/test");

		if (testDir.exists()) {
			FileUtil.delete(testDir);
		}

		assertTrue(testDir.mkdirs());

		File resources = new File("src/test/resources");

		assertTrue(resources.exists());

		assertTrue(resources.listFiles().length > 0);

		for (File resource : resources.listFiles()) {
			FileUtil.copy(resource, new File(testDir, resource.getName()));
		}

		File nodeExecutable = new File("build/node/bin/node");

		assertTrue(nodeExecutable.exists());

		File nodeModulesDir = new File("node_modules");

		assertTrue(nodeModulesDir.exists());

		File baseDir = new File(testDir, "srcDir");

		File outputDir = new File(baseDir, "outputDir");

		SoyBuilder soyBuilder = new SoyBuilder(
			baseDir, nodeExecutable, nodeModulesDir, outputDir);

		soyBuilder.build();

		File outputFile = new File(
			outputDir, "META-INF/resources/Footer.soy.js");

		assertTrue(outputFile.exists());

		List<String> lines = Files.readAllLines(
			outputFile.toPath(), StandardCharsets.UTF_8);

		assertTrue(_contains("Liferay.Language.get", lines));

		assertTrue(
			_contains("this-portlet-was-written-using-soy-templates", lines));
	}

	private boolean _contains(String expected, List<String> lines) {
		for (String line : lines) {
			if (line.contains(expected)) {
				return true;
			}
		}

		return false;
	}

}