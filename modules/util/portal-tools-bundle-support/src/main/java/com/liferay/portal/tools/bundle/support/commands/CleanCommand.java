/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;

import java.io.File;

import java.nio.file.Files;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Delete a file from the deploy directory of a Liferay bundle.",
	commandNames = "clean"
)
public class CleanCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		if (File.separatorChar != '/') {
			_fileName = _fileName.replace(File.separatorChar, '/');
		}

		String fileName = _fileName.substring(_fileName.lastIndexOf('/') + 1);

		String dirName = BundleSupportUtil.getDeployDirName(fileName);

		File file = new File(getLiferayHomeDir(), dirName + fileName);

		Files.deleteIfExists(file.toPath());
	}

	public String getFileName() {
		return _fileName;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	@Parameter(
		description = "The name of the file to delete from your bundle.",
		names = {"-f", "--file"}, required = true
	)
	private String _fileName;

}