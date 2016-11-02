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

package com.liferay.bundle.support;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

import com.liferay.bundle.support.util.FileUtil;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Turn your liferay home into an distributable archive"
)
public class CommandDistBundle extends CommandBase {

	public CommandDistBundle() {
	}

	public CommandDistBundle(
		String format, boolean includeFolder, File liferayHomeDir,
		File outputFile) {

		super(liferayHomeDir);

		_format = format;
		_includeFolder = includeFolder;
		_outputFile = outputFile;
	}

	@Override
	public void execute() throws Exception {
		if (_outputFile.exists()) {
			_outputFile.delete();
		}

		Path outputPath = _outputFile.toPath();

		Files.createDirectories(outputPath.getParent());

		if (_format.equals("zip")) {
			_distBundleZip();
		}
		else if (_format.equals("tar") || _format.equals("tar.gz") ||
				 _format.equals("tgz")) {

			_distBundleTar();
		}
		else {
			throw new Exception(
				"Please specify either zip or tar.gz for format");
		}
	}

	public String getFormat() {
		return _format;
	}

	public File getOutputFile() {
		return _outputFile;
	}

	public boolean isIncludeFolder() {
		return _includeFolder;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setIncludeFolder(boolean includeFolder) {
		_includeFolder = includeFolder;
	}

	public void setOutputFile(File outputFile) {
		_outputFile = outputFile;
	}

	protected static final String DEFAULT_FORMAT = "zip";

	private void _distBundleTar() throws Exception {
		FileUtil.tar(
			getLiferayHomePath(), _outputFile.toPath(), _includeFolder);
	}

	private void _distBundleZip() throws Exception {
		FileUtil.zip(getLiferayHomePath(), _outputFile, _includeFolder);
	}

	@Parameter(description = "The archive format.", names = {"--format"})
	private String _format = DEFAULT_FORMAT;

	@Parameter(
		description = "Add a parent folder to the archive",
		names = {"--include-folder"}
	)
	private boolean _includeFolder;

	@Parameter(
		converter = FileConverter.class,
		description = "The absolute path of your archive.",
		names = {"-o", "--output"}, required = true
	)
	private File _outputFile;

}