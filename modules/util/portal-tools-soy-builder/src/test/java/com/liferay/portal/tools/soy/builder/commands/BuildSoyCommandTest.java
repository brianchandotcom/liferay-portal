/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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