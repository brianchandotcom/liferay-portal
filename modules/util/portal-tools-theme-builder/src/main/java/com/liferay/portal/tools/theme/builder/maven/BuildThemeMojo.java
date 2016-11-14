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

package com.liferay.portal.tools.theme.builder.maven;

import com.liferay.portal.tools.theme.builder.ThemeBuilder;
import com.liferay.portal.tools.theme.builder.ThemeBuilderArgs;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Build a theme.
 *
 * @author Andrea Di Giorgi
 * @goal build-theme
 */
public class BuildThemeMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			ThemeBuilder themeBuilder = new ThemeBuilder(_themeBuilderArgs);

			themeBuilder.build();
		}
		catch (IOException ioe) {
			throw new MojoExecutionException(ioe.getMessage(), ioe);
		}
	}

	/**
	 * @parameter default-value="${maven.war.src}"
	 */
	public void setDiffsDir(File diffsDir) {
		_themeBuilderArgs.setDiffsDir(diffsDir);
	}

	/**
	 * @parameter default-value="${project.artifactId}"
	 */
	public void setName(String name) {
		_themeBuilderArgs.setName(name);
	}

	/**
	 * @parameter default-value="${project.build.directory}/${project.build.finalName}"
	 */
	public void setOutputDir(File outputDir) {
		_themeBuilderArgs.setOutputDir(outputDir);
	}

	/**
	 * @parameter
	 */
	public void setParentDir(File parentDir) {
		_themeBuilderArgs.setParentDir(parentDir);
	}

	/**
	 * @parameter
	 */
	public void setParentName(String parentName) {
		_themeBuilderArgs.setParentName(parentName);
	}

	/**
	 * @parameter default-value="ftl"
	 */
	public void setTemplateExtension(String templateExtension) {
		_themeBuilderArgs.setTemplateExtension(templateExtension);
	}

	/**
	 * @parameter
	 */
	public void setUnstyledDir(File unstyledDir) {
		_themeBuilderArgs.setUnstyledDir(unstyledDir);
	}

	private final ThemeBuilderArgs _themeBuilderArgs = new ThemeBuilderArgs();

}