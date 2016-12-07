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
import com.beust.jcommander.converters.FileConverter;

import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;

import java.nio.file.NoSuchFileException;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Deploy a file to a Liferay bundle.",
	commandNames = "deploy"
)
public class DeployCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		if (!_file.exists()) {
			throw new NoSuchFileException(
				"Unable to find " + _file.getAbsolutePath());
		}

		String fileName = _file.getName();

		String extension = FileUtil.getExtension(fileName);

		String deployFolder = BundleSupportUtil.getDeployFolder(extension);

		if (_outputFileName == null) {
			_outputFileName = _file.getName();
		}

		File outputFile = new File(
			getLiferayHomeDir(), deployFolder + _outputFileName);

		FileUtil.copyFile(_file, outputFile);
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
		converter = FileConverter.class,
		description = "The file to deploy to your Liferay bundle.",
		names = {"-f", "--file"}, required = true
	)
	private File _file;

	@Parameter(
		description = "The name of the output file.", names = {"-o", "--output"}
	)
	private String _outputFileName;

}