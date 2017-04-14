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

package com.liferay.portal.tools.soy.builder.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.jssrc.SoyJsSrcOptions;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Compiles Closure Templates into JavaScript functions.",
	commandNames = "build"
)
public class BuildSoyCommand implements Command {

	@Override
	public void execute() throws Exception {
		final List<Path> paths = new ArrayList<>();

		File dir = getDir();

		Files.walkFileTree(
			dir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".soy")) {
						paths.add(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		if (!paths.isEmpty()) {
			execute(paths);
		}
	}

	public void execute(List<Path> paths) throws IOException {
		SoyFileSet.Builder builder = SoyFileSet.builder();

		for (Path path : paths) {
			builder.add(path.toFile());
		}

		SoyFileSet soyFileSet = builder.build();

		SoyJsSrcOptions soyJsSrcOptions = new SoyJsSrcOptions();

		List<String> jsContents = soyFileSet.compileToJsSrc(
			soyJsSrcOptions, null);

		for (int i = 0; i < paths.size(); i++) {
			Path path = paths.get(i);
			String jsContent = jsContents.get(i);

			Path dirPath = path.getParent();

			Path jsPath = dirPath.resolve(
				String.valueOf(path.getFileName()) + ".js");

			Files.write(jsPath, jsContent.getBytes(StandardCharsets.UTF_8));
		}
	}

	public File getDir() {
		return _dir;
	}

	public void setDir(File dir) {
		_dir = dir;
	}

	@Parameter(
		description = "The directory containing the .soy files to compile.",
		names = {"-d", "--directory"}, required = true
	)
	private File _dir;

}