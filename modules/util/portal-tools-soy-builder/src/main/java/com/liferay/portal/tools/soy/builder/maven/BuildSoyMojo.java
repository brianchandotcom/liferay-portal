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

package com.liferay.portal.tools.soy.builder.maven;

import com.liferay.portal.tools.soy.builder.SoyBuilder;
import com.liferay.portal.tools.soy.builder.SoyBuilderArgs;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author Gregory Amerson
 * @goal build-soy
 * @phase process-classes
 */
public class BuildSoyMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			SoyBuilder soyBuilder = new SoyBuilder(_soyBuilderArgs);

			soyBuilder.build();
		}
		catch (Exception ioe) {
			throw new MojoExecutionException(ioe.getMessage(), ioe);
		}
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 */
	public void setBaseDir(File baseDir) {
		_soyBuilderArgs.setBaseDir(baseDir);
	}

	/**
	 * @parameter default-value="${node.executable}"
	 */
	public void setNodeExecutable(File nodeExecutable) {
		_soyBuilderArgs.setNodeExecutableFile(nodeExecutable);
	}

	/**
	 * @parameter default-value="${project.basedir}/node_modules"
	 */
	public void setNodeModulesDir(File nodeModulesDir) {
		_soyBuilderArgs.setNodeModulesDir(nodeModulesDir);
	}

	/**
	 * @parameter default-value="${project.build.outputDirectory}"
	 */
	public void setOutputDir(File outputDir) {
		_soyBuilderArgs.setOutputDir(outputDir);
	}

	private final SoyBuilderArgs _soyBuilderArgs = new SoyBuilderArgs();

}