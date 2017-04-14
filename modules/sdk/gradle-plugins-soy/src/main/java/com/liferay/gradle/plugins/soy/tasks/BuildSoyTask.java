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

package com.liferay.gradle.plugins.soy.tasks;

import com.liferay.portal.tools.soy.builder.commands.BuildSoyCommand;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Transformer;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.CollectionUtils;

/**
 * @author Andrea Di Giorgi
 */
public class BuildSoyTask extends SourceTask {

	@TaskAction
	public void buildSoy() throws IOException {
		List<Path> paths = CollectionUtils.collect(
			getSource(),
			new Transformer<Path, File>() {

				@Override
				public Path transform(File file) {
					return file.toPath();
				}

			});

		_buildSoyCommand.execute(paths);
	}

	@OutputFiles
	public Iterable<File> getOutputFiles() {
		List<File> outputFiles = new ArrayList<>();

		for (File sourceFile : getSource()) {
			String fileName = sourceFile.getName();

			File outputFile = new File(
				sourceFile.getParentFile(), fileName + ".js");

			outputFiles.add(outputFile);
		}

		return outputFiles;
	}

	private final BuildSoyCommand _buildSoyCommand = new BuildSoyCommand();

}