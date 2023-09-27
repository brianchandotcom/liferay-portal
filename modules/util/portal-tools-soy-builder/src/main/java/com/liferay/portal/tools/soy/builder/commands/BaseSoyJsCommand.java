/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.soy.builder.commands;

import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseSoyJsCommand implements Command {

	@Override
	public void execute() throws Exception {
		File dir = getDir();

		Files.walkFileTree(
			dir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".soy.js")) {
						execute(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public abstract void execute(Path path) throws IOException;

	public File getDir() {
		return _dir;
	}

	public void setDir(File dir) {
		_dir = dir;
	}

	@Parameter(
		description = "The directory containing the .soy.js files to process.",
		names = {"-d", "--directory"}, required = true
	)
	private File _dir;

}