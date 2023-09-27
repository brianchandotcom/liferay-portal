/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.soy.task;

import com.liferay.portal.tools.soy.builder.commands.BuildSoyCommand;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@CacheableTask
@Deprecated
public class BuildSoyTask extends SourceTask {

	@TaskAction
	public void buildSoy() throws Exception {
		final List<Path> paths = new ArrayList<>();

		for (File file : getSource()) {
			paths.add(file.toPath());
		}

		_withClasspath(
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
	@PathSensitive(PathSensitivity.RELATIVE)
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

	private <T> T _withClasspath(Iterable<File> classpath, Callable<T> callable)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		URLClassLoader contextURLClassLoader =
			(URLClassLoader)currentThread.getContextClassLoader();

		List<URL> urls = new ArrayList<>();

		for (File file : classpath) {
			URI uri = file.toURI();

			urls.add(uri.toURL());
		}

		Collections.addAll(urls, contextURLClassLoader.getURLs());

		try (URLClassLoader urlClassLoader = new URLClassLoader(
				urls.toArray(new URL[0]), null)) {

			currentThread.setContextClassLoader(urlClassLoader);

			return callable.call();
		}
		finally {
			currentThread.setContextClassLoader(contextURLClassLoader);
		}
	}

	private FileCollection _classpath;

}