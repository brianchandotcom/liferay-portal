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

package com.liferay.ant.arquilian;

import java.io.File;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
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

		Project project = _getProject();

		project.setProperty("app.server.deploy.dir", tempDir.getAbsolutePath());
		project.setProperty("auto.deploy.unpack.war", "false");

		project.executeTarget("direct-deploy");

		File warFile = new File(
			tempDir.getAbsolutePath(),
			project.getProperty("plugin.name") + ".war");

		return ShrinkWrap.createFromZipFile(WebArchive.class, warFile);
	}

	private static Project _getProject() {
		Project project = new Project();

		DefaultLogger defaultLogger = new DefaultLogger() {

			@Override
			public void buildFinished(BuildEvent buildEvent) {
				System.out.println("[BUILD FINISHED]");
			}

			@Override
			public void buildStarted(BuildEvent buildEvent) {
				System.out.println("[BUILD STARTED]");
			}

			@Override
			public void messageLogged(BuildEvent buildEvent) {
				System.out.println(buildEvent.getMessage());
			}

		};

		defaultLogger.setErrorPrintStream(System.err);
		defaultLogger.setMessageOutputLevel(Project.MSG_INFO);
		defaultLogger.setOutputPrintStream(System.out);

		project.addBuildListener(defaultLogger);

		File buildFile = new File("build.xml");

		project.setUserProperty("ant.file", buildFile.getAbsolutePath());

		project.init();

		ProjectHelper projectHelper = ProjectHelper.getProjectHelper();

		project.addReference("ant.projectHelper", projectHelper);

		projectHelper.parse(project, buildFile);

		return project;
	}

}