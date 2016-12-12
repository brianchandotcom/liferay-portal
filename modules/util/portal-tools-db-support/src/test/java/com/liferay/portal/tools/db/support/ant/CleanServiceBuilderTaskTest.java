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

package com.liferay.portal.tools.db.support.ant;

import com.liferay.portal.tools.db.support.commands.CleanServiceBuilderCommandTest;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * @author Andrea Di Giorgi
 */
public class CleanServiceBuilderTaskTest
	extends CleanServiceBuilderCommandTest {

	public CleanServiceBuilderTaskTest(String mode) throws IOException {
		super(mode);
	}

	@Before
	public void setUp() throws Exception {
		URL url = CleanServiceBuilderTask.class.getResource(
			"dependencies/build.xml");

		File buildXmlFile = new File(url.toURI());

		Assert.assertTrue(buildXmlFile.isFile());

		buildFileRule.configureProject(buildXmlFile.getAbsolutePath());
	}

	@Rule
	public final BuildFileRule buildFileRule = new BuildFileRule();

	@Override
	protected void cleanServiceBuilder(
			File serviceXmlFile, String servletContextName, String url)
		throws Exception {

		Project project = buildFileRule.getProject();

		project.setProperty(
			"clean.service.builder.service.xml.file",
			serviceXmlFile.getAbsolutePath());
		project.setProperty(
			"clean.service.builder.servlet.context.name", servletContextName);
		project.setProperty("clean.service.builder.url", url);

		project.executeTarget("clean-service-builder");
	}

}