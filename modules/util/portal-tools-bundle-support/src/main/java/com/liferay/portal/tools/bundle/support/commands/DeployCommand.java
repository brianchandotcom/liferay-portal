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

import com.liferay.portal.tools.bundle.support.BaseCommand;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.nio.file.FileSystem;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * @author David Truong
 */
@Parameters(commandDescription = "Deploy file to Liferay home")
public class DeployCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		if (!_file.exists()) {
			throw new NoSuchFileException(
				"Could not find " + _file.getAbsolutePath());
		}

		File liferayHomeDir = getLiferayHomeDir();

		if (liferayHomeDir.isDirectory()) {
			String fileName = _file.getName();

			String extension = FileUtil.getExtension(fileName);

			String deployFolder = getDeployFolder(extension);

			if (_outputFileName == null) {
				_outputFileName = _file.getName();
			}

			File outputFile = new File(
				getLiferayHomeDir(), deployFolder + _outputFileName);

			FileUtil.copyFile(_file, outputFile);

			return;
		}

		String extension = FileUtil.getExtension(liferayHomeDir.getName());

		if (extension.equals("zip")) {
			_deployToZip();
		}
		else if (extension.equals("gz") || extension.equals("tar") ||
				 extension.equals("tar.gz") || extension.equals("tgz")) {

			_deployToTar();
		}
		else {
			throw new Exception("Please specify either zip or tar.gz or tgz");
		}
	}

	public File getFile() {
		return _file;
	}

	public String getOutputFileName() {
		return _outputFileName;
	}

	public boolean isIncludeFolder() {
		return _includeFolder;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setIncludeFolder(boolean includeFolder) {
		_includeFolder = includeFolder;
	}

	public void setOutputFileName(String outputFileName) {
		_outputFileName = outputFileName;
	}

	private void _deployToTar() throws Exception {
		File liferayHomeDir = getLiferayHomeDir();

		try (TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(
					new GzipCompressorOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(liferayHomeDir))))) {

			String extension = FileUtil.getExtension(_file.getName());

			String deployFolder = getDeployFolder(extension);

			if (_includeFolder) {
				deployFolder = FileUtil.getFileNameWithExtension(
					liferayHomeDir.getPath()) + "/" + deployFolder;
			}

			Path tarPath = Paths.get(deployFolder, _outputFileName);

			TarArchiveEntry tarFile = new TarArchiveEntry(tarPath.toFile());

			tarFile.setSize(_file.length());

			tarArchiveOutputStream.putArchiveEntry(tarFile);

			IOUtils.copy(new FileInputStream(_file), tarArchiveOutputStream);

			tarArchiveOutputStream.closeArchiveEntry();
		}
	}

	private void _deployToZip() throws Exception {
		File liferayHomeDir = getLiferayHomeDir();

		try (FileSystem fileSystem =
				FileUtil.createFileSystem(liferayHomeDir, false)) {

			String extension = FileUtil.getExtension(_file.getName());

			String deployFolder = getDeployFolder(extension);

			if (_includeFolder) {
				deployFolder = FileUtil.getFileNameWithExtension(
					liferayHomeDir.getPath()) + "/" + deployFolder;
			}

			Path zipPath = fileSystem.getPath(deployFolder + _outputFileName);

			FileUtil.copyFile(_file.toPath(), zipPath);
		}
	}

	@Parameter(
		converter = FileConverter.class,
		description = "The absolute path of the file you want to deploy to your Liferay bundle.",
		names = {"-f", "--file"}, required = true
	)
	private File _file;

	@Parameter(
		description = "Add a parent folder to the archive",
		names = {"--include-folder"}
	)
	private boolean _includeFolder;

	@Parameter(
		description = "The name of the output file.", names = {"-o", "--output"}
	)
	private String _outputFileName;

}