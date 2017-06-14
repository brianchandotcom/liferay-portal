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

import com.liferay.portal.tools.bundle.support.commands.DistBundleCommand;
import com.liferay.portal.tools.bundle.support.internal.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Mojo(name = "dist")
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
				String deployDirName = BundleSupportUtil.getDeployDirName(
					deployFile.getName());

				if (includeFolder) {
					deployDirName = archiveFileName + "/" + deployDirName;
				}

				Path entryPath = Paths.get(deployDirName, outputFileName);

				if (format.equals("zip")) {
					FileUtil.appendZip(deployFile, entryPath, archiveFile);
				}
				else if (format.equals("gz") || format.equals("tar") ||
						 format.equals("tar.gz") || format.equals("tgz")) {

					FileUtil.appendTar(deployFile, entryPath, archiveFile);
				}
				else {
					throw new IllegalArgumentException(
						"Please specify either zip or tar.gz or tgz");
				}
			}
			else if (!project.hasParent()) {
				archiveFile.delete();

				File liferayHomeDir = getLiferayHomeDir();

				super.execute();

				DistBundleCommand distBundleCommand = new DistBundleCommand();

				distBundleCommand.setFormat(format);
				distBundleCommand.setIncludeFolder(includeFolder);
				distBundleCommand.setLiferayHomeDir(getLiferayHomeDir());
				distBundleCommand.setOutputFile(archiveFile);

				distBundleCommand.execute();

				FileUtil.deleteDirectory(liferayHomeDir.toPath());
			}
		}
		catch (MojoExecutionException mee) {
			throw mee;
		}
		catch (Exception e) {
			throw new MojoExecutionException(
				"Unable to create distributable bundle", e);
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

}