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

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Deploy a file to a Liferay bundle.",
	commandNames = "deploy"
)
public class DeployCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		Path path = _file.toPath();

		if (Files.notExists(path)) {
			throw new NoSuchFileException("Unable to find " + path);
		}

		String fileName = String.valueOf(path.getFileName());

		String deployDirName = BundleSupportUtil.getDeployDirName(fileName);

		if (_outputFileName == null) {
			_outputFileName = fileName;
		}

		File outputFile = new File(
			getLiferayHomeDir(), deployDirName + _outputFileName);

		FileUtil.copyFile(path, outputFile.toPath());
	}

	public File getFile() {
		return _file;
	}

	public String getOutputFileName() {
		return _outputFileName;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setOutputFileName(String outputFileName) {
		_outputFileName = outputFileName;
	}

	@Parameter(
		description = "The file to deploy to your Liferay bundle.",
		names = {"-f", "--file"}, required = true
	)
	private File _file;

	@Parameter(
		description = "The name of the output file.", names = {"-o", "--output"}
	)
	private String _outputFileName;

}