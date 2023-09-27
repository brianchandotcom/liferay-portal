/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.maven;

import com.liferay.portal.tools.bundle.support.commands.DeployCommand;
import com.liferay.portal.tools.bundle.support.commands.DistBundleCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;

import java.io.File;

import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Mojo(defaultPhase = LifecyclePhase.VERIFY, name = "dist")
public class DistBundleMojo extends InitBundleMojo {

	@Override
	public void execute() throws MojoExecutionException {
		MavenProject rootProject = MavenUtil.getRootProject(project);

		Build build = rootProject.getBuild();

		if (archiveFileName == null) {
			archiveFileName = rootProject.getArtifactId();
		}

		String archiveLocation = build.getDirectory() + "/" + archiveFileName;

		setLiferayHome(archiveLocation);

		File archiveFile = new File(archiveLocation + "." + format);

		String packaging = project.getPackaging();

		try {
			if (packaging.equals("jar") || packaging.equals("war")) {
				try {
					DeployCommand deployCommand = new DeployCommand();

					deployCommand.setFile(deployFile);
					deployCommand.setLiferayHomeDir(getLiferayHomeDir());
					deployCommand.setOutputFileName(outputFileName);

					deployCommand.execute();
				}
				catch (Exception exception) {
					throw new MojoExecutionException(
						"Unable to deploy " + outputFileName, exception);
				}
			}
			else if (!project.hasParent()) {
				archiveFile.delete();

				super.execute();
			}

			MavenProject mavenProject = reactorProjects.get(
				reactorProjects.size() - 1);

			if (project.equals(mavenProject)) {
				DistBundleCommand distBundleCommand = new DistBundleCommand();

				distBundleCommand.setFormat(format);
				distBundleCommand.setIncludeFolder(includeFolder);
				distBundleCommand.setLiferayHomeDir(getLiferayHomeDir());
				distBundleCommand.setOutputFile(archiveFile);

				distBundleCommand.execute();

				File liferayHomeDir = getLiferayHomeDir();

				FileUtil.deleteDirectory(liferayHomeDir.toPath());
			}
		}
		catch (MojoExecutionException mojoExecutionException) {
			throw mojoExecutionException;
		}
		catch (Exception exception) {
			throw new MojoExecutionException(
				"Unable to create distributable bundle", exception);
		}
	}

	@Parameter
	protected String archiveFileName;

	@Parameter(
		defaultValue = "${project.build.directory}/${project.build.finalName}.${project.packaging}",
		required = true
	)
	protected File deployFile;

	@Parameter(
		defaultValue = BundleSupportConstants.DEFAULT_BUNDLE_FORMAT,
		required = true
	)
	protected String format;

	@Parameter(
		defaultValue = "" + BundleSupportConstants.DEFAULT_INCLUDE_FOLDER,
		required = true
	)
	protected boolean includeFolder;

	@Parameter(
		defaultValue = "${project.artifactId}.${project.packaging}",
		required = true
	)
	protected String outputFileName;

	@Parameter(property = "reactorProjects", readonly = true)
	protected List<MavenProject> reactorProjects;

}