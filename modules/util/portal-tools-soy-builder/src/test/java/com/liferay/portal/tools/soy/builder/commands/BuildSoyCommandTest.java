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

package com.liferay.portal.tools.soy.builder.commands;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class BuildSoyCommandTest extends BaseSoyCommandTestCase {

	@Override
	protected String fixTestContent(String content) {
		content = super.fixTestContent(content);

		content = content.replaceAll("__\\w+", "__TEST");
		content = content.replaceAll(
			"localeMetadata\\w+", "localeMetadataTEST");

		return content;
	}

	@Override
	protected String getTestDirName() {
		return "com/liferay/portal/tools/soy/builder/commands/dependencies" +
			"/build_soy/";
	}

	@Override
	protected String[] getTestExpectedFileNames() {
		String[] testFileNames = getTestFileNames();

		String[] testExpectedFileNames = new String[testFileNames.length];

		for (int i = 0; i < testFileNames.length; i++) {
			testExpectedFileNames[i] = testFileNames[i] + ".js";
		}

		return testExpectedFileNames;
	}

	@Override
	protected String[] getTestFileNames() {
		return new String[] {
			"hello_world.soy", "options.soy", "text_localizable.soy"
		};
	}

	@Override
	protected void testSoy(File dir) throws Exception {
		BuildSoyCommand buildSoyCommand = new BuildSoyCommand();

		buildSoyCommand.setDir(dir);

		buildSoyCommand.execute();
	}

}