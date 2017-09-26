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

package com.liferay.osgi.bundle.builder.commands;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Processor;

import aQute.lib.strings.Strings;

import com.beust.jcommander.Parameter;

import com.liferay.osgi.bundle.builder.internal.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * @author David Truong
 */
public abstract class BaseCommand implements Command {

	@Override
	public void build() throws Exception {
		Properties properties = FileUtil.readProperties(_bndFile);

		try (Builder builder = new Builder(new Processor(properties, false))) {
			builder.setBase(_baseDir);

			if ((_classesDir != null) && _classesDir.exists()) {
				Jar classesJar = new Jar(_classesDir);

				classesJar.setManifest(new Manifest());

				if ((_resourcesDir != null) && _resourcesDir.exists()) {
					Jar resourcesJar = new Jar(_resourcesDir);

					classesJar.addAll(resourcesJar);
				}

				builder.setJar(classesJar);
			}

			List<Object> buildPath = new ArrayList<>(_classpath.length);

			for (String fileName : _classpath) {
				File file = new File(fileName);

				if (!file.exists()) {
					continue;
				}

				if (file.isDirectory()) {
					Jar jar = new Jar(file);

					builder.addClose(jar);

					builder.updateModified(jar.lastModified(), file.getPath());

					buildPath.add(jar);
				}
				else {
					builder.updateModified(file.lastModified(), file.getPath());

					buildPath.add(file);
				}
			}

			builder.setClasspath(buildPath);
			builder.setProperty(
				"project.buildpath",
				Strings.join(File.pathSeparator, buildPath));

			Jar jar = builder.build();

			writeOutput(jar);
		}
	}

	public File getBaseDir() {
		return _baseDir;
	}

	public File getBndFile() {
		return _bndFile;
	}

	public File getClassesDir() {
		return _classesDir;
	}

	public String[] getClasspath() {
		return _classpath;
	}

	public File getOutput() {
		return _output;
	}

	public File getResourcesDir() {
		return _resourcesDir;
	}

	public void setBaseDir(File baseDir) {
		_baseDir = baseDir;
	}

	public void setBndFile(File bndFile) {
		_bndFile = bndFile;
	}

	public void setClassesDir(File classesDir) {
		_classesDir = classesDir;
	}

	public void setClasspath(String[] classpath) {
		_classpath = classpath;
	}

	public void setOutput(File output) {
		_output = output;
	}

	public void setResourcesDir(File resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	protected abstract void writeOutput(Jar jar) throws Exception;

	@Parameter(description = "The base directory.", names = {"--base-dir"})
	private File _baseDir;

	@Parameter(
		description = "The location of the Bnd file.", names = {"--bnd-file"}
	)
	private File _bndFile;

	@Parameter(
		description = "The directory which contains the class files.",
		names = {"--classes-dir"}
	)
	private File _classesDir;

	@Parameter(
		description = "The list of directories and JAR files to include in the classpath.",
		names = {"--classpath"}
	)
	private String[] _classpath;

	@Parameter(
		description = "The output directory.", names = {"-o", "--output-dir"}
	)
	private File _output;

	@Parameter(
		description = "The directory that contains the processed resources.",
		names = {"--resources-dir"}
	)
	private File _resourcesDir;

}