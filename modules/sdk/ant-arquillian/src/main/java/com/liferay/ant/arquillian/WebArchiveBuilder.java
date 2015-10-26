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

package com.liferay.ant.arquillian;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Manuel de la Peña
 * @author Cristina González
 */
public class WebArchiveBuilder {

	public static WebArchive build() {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(
				"ant", "direct-deploy",
				"-Dapp.server.deploy.dir=" + tempDir.getAbsolutePath(),
				"-Dauto.deploy.unpack.war=false");

			Process process = processBuilder.start();

			process.waitFor();

			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

			String line = bufferedReader.readLine();

			while (line != null) {
				_logger.debug(line);

				line = bufferedReader.readLine();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		Project project = _getProject();

		File warFile = new File(
			tempDir.getAbsolutePath(),
			project.getProperty("plugin.name") + ".war");

		return ShrinkWrap.createFromZipFile(WebArchive.class, warFile);
	}

	private static Project _getProject() {
		Project project = new Project();

		File buildFile = new File("build.xml");

		project.setUserProperty("ant.file", buildFile.getAbsolutePath());

		project.init();

		ProjectHelper projectHelper = ProjectHelper.getProjectHelper();

		project.addReference("ant.projectHelper", projectHelper);

		projectHelper.parse(project, buildFile);

		return project;
	}

	private static final Logger _logger = Logger.getLogger(
		WebArchiveBuilder.class);

}