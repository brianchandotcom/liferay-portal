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

import com.liferay.gradle.plugins.soy.internal.util.GradleUtil;
import com.liferay.portal.tools.soy.builder.commands.BuildSoyCommand;

import java.io.File;

import java.lang.reflect.Method;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class BuildSoyTask extends SourceTask {

	@TaskAction
	public void buildSoy() throws Exception {
		final List<Path> paths = new ArrayList<>();

		for (File file : getSource()) {
			paths.add(file.toPath());
		}

		GradleUtil.withClasspath(
			getClasspath(),
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Thread currentThread = Thread.currentThread();

					ClassLoader contextClassLoader =
						currentThread.getContextClassLoader();

					Class<?> clazz = contextClassLoader.loadClass(
						BuildSoyCommand.class.getName());

					Method executeMethod = clazz.getMethod(
						"execute", List.class);

					Object buildSoyCommand = clazz.newInstance();

					executeMethod.invoke(buildSoyCommand, paths);

					return null;
				}

			});
	}

	@InputFiles
	public FileCollection getClasspath() {
		return _classpath;
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

	public void setClasspath(FileCollection classpath) {
		_classpath = classpath;
	}

	private FileCollection _classpath;

}