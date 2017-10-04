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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class CompileJSPTask extends DefaultTask {

	@TaskAction
	public void compileJSP() throws Throwable {
		FileCollection jspCClasspath = getJspCClasspath();
		FileCollection jspCToolClasspath = getJspCToolClasspath();

		String classpath =
			jspCToolClasspath.getAsPath() + File.pathSeparator +
				jspCClasspath.getAsPath();

		String[] files = classpath.split(File.pathSeparator);

		URL[] urls = new URL[files.length];

		for (int i = 0; i < files.length; i++) {
			File file = new File(files[i]);

			URI uri = file.toURI();

			urls[i] = uri.toURL();
		}

		ClassLoader classLoader = new URLClassLoader(urls, null);

		Class<?> jspcClass = classLoader.loadClass(
			"com.liferay.jasper.jspc.JspC");

		Object jspc = jspcClass.newInstance();

		Method setArgsMethod = jspcClass.getMethod("setArgs", String[].class);

		setArgsMethod.invoke(jspc, new Object[] {_getArgs()});

		Method setClassPathMethod = jspcClass.getMethod(
			"setClassPath", String.class);

		setClassPathMethod.invoke(jspc, classpath);

		Method executeMethod = jspcClass.getMethod("execute");

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);

		try {
			executeMethod.invoke(jspc);
		}
		catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
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
	public FileCollection getJspCToolClasspath() {
		return _jspCToolClasspath;
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

	public void setJspCToolClasspath(FileCollection classpath) {
		_jspCToolClasspath = classpath;
	}

	public void setWebAppDir(Object webAppDir) {
		_webAppDir = webAppDir;
	}

	private String[] _getArgs() {
		List<String> args = new ArrayList<>();

		Project project = getProject();

		args.add("-d");
		args.add(project.relativePath(getDestinationDir()));

		args.add("-webapp");
		args.add(FileUtil.getAbsolutePath(getWebAppDir()));

		return args.toArray(new String[args.size()]);
	}

	private Object _destinationDir;
	private FileCollection _jspCClasspath;
	private FileCollection _jspCToolClasspath;
	private Object _webAppDir;

}