/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Turn a Liferay home into an distributable archive.",
	commandNames = "distBundle"
)
public class DistBundleCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		if (_outputFile.exists()) {
			_outputFile.delete();
		}

		Path outputPath = _outputFile.toPath();

		Files.createDirectories(outputPath.getParent());

		File liferayHomeDir = getLiferayHomeDir();

		Path liferayHomeDirPath = liferayHomeDir.toPath();

		if (_format.equals("7z")) {
			FileUtil.sevenZip(liferayHomeDirPath, _outputFile, _includeFolder);
		}
		else if (_format.equals("tar") || _format.equals("tar.gz") ||
				 _format.equals("tgz")) {

			FileUtil.tar(liferayHomeDirPath, _outputFile, _includeFolder);
		}
		else if (_format.equals("zip")) {
			FileUtil.zip(liferayHomeDirPath, _outputFile, _includeFolder);
		}
		else {
			throw new IllegalArgumentException(
				"Please specify either 7z, tar.gz, or zip for the archive " +
					"format");
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

	@Parameter(description = "The archive format.", names = "--format")
	private String _format = BundleSupportConstants.DEFAULT_BUNDLE_FORMAT;

	@Parameter(
		description = "Add a parent folder to the archive.",
		names = "--include-folder"
	)
	private boolean _includeFolder =
		BundleSupportConstants.DEFAULT_INCLUDE_FOLDER;

	@Parameter(
		description = "The path of the archive.", names = {"-o", "--output"},
		required = true
	)
	private File _outputFile;

}