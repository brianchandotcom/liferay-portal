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

package com.liferay.portal.tools.soy.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

/**
 * @author Gregory Amerson
 */
public class SoyBuilderArgs {

	public File getBaseDir() {
		return _baseDir;
	}

	public File getNodeExecutableFile() {
		return _nodeExecutableFile;
	}

	public File getNodeModulesDir() {
		return _nodeModulesDir;
	}

	public File getOutputDir() {
		return _outputDir;
	}

	public void setBaseDir(File baseDir) {
		_baseDir = baseDir;
	}

	public void setNodeExecutableFile(File nodeExecutableFile) {
		_nodeExecutableFile = nodeExecutableFile;
	}

	public void setNodeModulesDir(File nodeModulesDir) {
		_nodeModulesDir = nodeModulesDir;
	}

	public void setOutputDir(File outputDir) {
		_outputDir = outputDir;
	}

	protected boolean isHelp() {
		return _help;
	}

	@Parameter(
		converter = FileConverter.class,
		description = "The base project directory.",
		names = {"-d", "--directory"}
	)
	private File _baseDir;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		converter = FileConverter.class,
		description = "The node js executable file.",
		names = {"-n", "--node-executable"}
	)
	private File _nodeExecutableFile;

	@Parameter(
		converter = FileConverter.class,
		description = "The node modules directory.",
		names = {"-n", "--node-modules"}
	)
	private File _nodeModulesDir;

	@Parameter(
		converter = FileConverter.class, description = "The output directory.",
		names = {"-o", "--output-directory"}
	)
	private File _outputDir;

}