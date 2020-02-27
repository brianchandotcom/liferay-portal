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

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;
import java.io.OutputStream;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;

/**
 * @author Andrea Di Giorgi
 */
public class CompileJSPTask extends JavaExec {

	@Override
	public void exec() {
		FileCollection classpath = getClasspath();
		FileCollection jspCClasspath = getJspCClasspath();

		try {
			_runWithClassPath(
				classpath.getAsPath(), jspCClasspath.getAsPath(),
				_getCompleteArgs());
		}
		catch (Exception exception) {
			throw new GradleException(exception.getMessage(), exception);
		}
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputFiles
	public FileCollection getJspCClasspath() {
		return _jspCClasspath;
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getJSPFiles() {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", getWebAppDir());

		List<String> excludes = new ArrayList<>(2);

		excludes.add("**/custom_jsps/**/*");
		excludes.add("**/dependencies/**/*");

		args.put("excludes", excludes);

		args.put("include", "**/*.jsp");

		return project.fileTree(args);
	}

	public File getWebAppDir() {
		return GradleUtil.toFile(getProject(), _webAppDir);
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setJspCClasspath(FileCollection jspCClasspath) {
		_jspCClasspath = jspCClasspath;
	}

	@Override
	public JavaExec setStandardOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	public void setWebAppDir(Object webAppDir) {
		_webAppDir = webAppDir;
	}

	private static BiConsumer<String, String[]> _getJspCBiConsumer(
			String classpath)
		throws Exception {

		Reference<BiConsumer<String, String[]>> jspCBiConsumerReference =
			_jspCBiConsumers.get(classpath);

		BiConsumer<String, String[]> jspCBiConsumer = null;

		if (jspCBiConsumerReference != null) {
			jspCBiConsumer = jspCBiConsumerReference.get();
		}

		if (jspCBiConsumer == null) {
			ClassLoader classLoader = new URLClassLoader(
				_toURLs(classpath), null);

			Class<?> jspCClass = classLoader.loadClass(
				"org.apache.jasper.JspC");

			Method setArgsMethod = jspCClass.getMethod(
				"setArgs", String[].class);

			Method setClassPathMethod = jspCClass.getMethod(
				"setClassPath", String.class);

			Method executeMethod = jspCClass.getMethod("execute");

			jspCBiConsumer = (jspCClasspath, args) -> {
				try {
					Object jspC = jspCClass.newInstance();

					setArgsMethod.invoke(jspC, new Object[] {args});

					setClassPathMethod.invoke(jspC, jspCClasspath);

					Thread currentThread = Thread.currentThread();

					ClassLoader contextClassLoader =
						currentThread.getContextClassLoader();

					currentThread.setContextClassLoader(classLoader);

					try {
						executeMethod.invoke(jspC);
					}
					finally {
						currentThread.setContextClassLoader(contextClassLoader);
					}
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			};

			_jspCBiConsumers.put(
				classpath, new SoftReference<>(jspCBiConsumer));
		}

		return jspCBiConsumer;
	}

	private static void _runWithClassPath(
			String classpath, String jspCClasspath, String[] args)
		throws Exception {

		BiConsumer<String, String[]> jspCBiConsumer = _getJspCBiConsumer(
			classpath);

		jspCBiConsumer.accept(jspCClasspath, args);
	}

	private static URL[] _toURLs(String classpath)
		throws MalformedURLException {

		String[] files = classpath.split(File.pathSeparator);

		URL[] urls = new URL[files.length];

		for (int i = 0; i < files.length; i++) {
			File file = new File(files[i]);

			URI uri = file.toURI();

			urls[i] = uri.toURL();
		}

		return urls;
	}

	private String[] _getCompleteArgs() {
		return new String[] {
			"-d", FileUtil.getAbsolutePath(getDestinationDir()), "-webapp",
			FileUtil.getAbsolutePath(getWebAppDir())
		};
	}

	private static final Map<String, Reference<BiConsumer<String, String[]>>>
		_jspCBiConsumers = new HashMap<>();

	private Object _destinationDir;
	private FileCollection _jspCClasspath;
	private Object _webAppDir;

}