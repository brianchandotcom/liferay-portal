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

package com.liferay.portal.tools.theme.builder.ant;

import com.liferay.portal.tools.theme.builder.ThemeBuilderTest;

import java.io.File;

import java.net.URL;

import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * @author Andrea Di Giorgi
 */
public class BuildThemeTaskTest extends ThemeBuilderTest {

	@Before
	public void setUp() throws Exception {
		URL url = BuildThemeTaskTest.class.getResource(
			"dependencies/build.xml");

		File buildXmlFile = new File(url.toURI());

		Assert.assertTrue(buildXmlFile.isFile());

		buildFileRule.configureProject(buildXmlFile.getAbsolutePath());
	}

	@Rule
	public final BuildFileRule buildFileRule = new BuildFileRule();

	@Override
	protected void buildTheme(
			File diffsDir, String name, File outputDir, File parentDir,
			String parentName, String templateExtension, File unstyledDir)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty(
			"build.theme.diffs.dir", diffsDir.getAbsolutePath());
		project.setProperty("build.theme.name", name);
		project.setProperty(
			"build.theme.output.dir", outputDir.getAbsolutePath());

		if (parentDir != null) {
			project.setProperty(
				"build.theme.parent.dir", parentDir.getAbsolutePath());
		}

		project.setProperty("build.theme.parent.name", parentName);
		project.setProperty(
			"build.theme.template.extension", templateExtension);
		project.setProperty(
			"build.theme.unstyled.dir", unstyledDir.getAbsolutePath());

		project.executeTarget("build-theme");
	}

}