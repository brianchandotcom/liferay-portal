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

package com.liferay.portal.tools.bundle.support.maven;

import com.liferay.portal.tools.bundle.support.commands.DeployCommand;
import com.liferay.portal.tools.bundle.support.commands.DistBundleCommand;
import com.liferay.portal.tools.bundle.support.commands.InitBundleCommand;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;

import java.io.File;

import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author David Truong
 */
@Mojo(name = "dist-bundle")
public class DistBundleMojo extends AbstractBundleMojo {

	@Override
	public void execute() throws MojoExecutionException {
		MavenProject rootProject = MavenUtil.getRootParent(project);

		Build build = rootProject.getBuild();

		if (archiveFileName == null) {
			archiveFileName = rootProject.getArtifactId();
		}

		String archiveLocation = build.getDirectory() + "/" + archiveFileName;

		setLiferayHome(archiveLocation);

		File archive = new File(archiveLocation + "." + format);

		String packaging = project.getPackaging();

		if (!project.hasParent()) {
			try {
				archive.delete();

				File liferayHomeDir = getLiferayHomeDir();

				InitBundleCommand initBundleCommand = new InitBundleCommand(
					new File(project.getBasedir(), configs), environment,
					liferayHomeDir, password, proxyHost, proxyPassword,
					proxyPort, proxyProtocol, proxyUsername, stripComponents,
					url.toString(), username);

				initBundleCommand.execute();

				DistBundleCommand distBundleCommand = new DistBundleCommand(
					format, includeFolder, getLiferayHomeDir(), archive);

				distBundleCommand.execute();

				FileUtil.deleteDirectory(liferayHomeDir.toPath());
			}
			catch (Exception e) {
				throw new MojoExecutionException(
					"Could not create distributable bundle", e);
			}
		}
		else if (packaging.equals("war") || packaging.equals("jar")) {
			try {
				DeployCommand deployCommand = new DeployCommand(
					deployFile, includeFolder, archive, outputFileName);

				deployCommand.execute();
			}
			catch (Exception e) {
				throw new MojoExecutionException(
					"Could not create distributable bundle", e);
			}
		}
	}

	@Parameter
	protected String archiveFileName;

	@Parameter(
		defaultValue = "${project.build.directory}/${project.build.finalName}.${project.packaging}",
		required = true
	)
	protected File deployFile;

	@Parameter(defaultValue = "zip", required = true)
	protected String format;

	@Parameter(defaultValue = "true", required = true)
	protected boolean includeFolder;

	@Parameter(
		defaultValue = "${project.artifactId}.${project.packaging}",
		required = true
	)
	protected String outputFileName;

}