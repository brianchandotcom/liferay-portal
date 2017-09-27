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

package com.liferay.osgi.bundle.builder;

import com.beust.jcommander.Parameter;

import com.liferay.osgi.bundle.builder.internal.converters.PathParameterSplitter;

import java.io.File;

import java.util.List;

/**
 * @author David Truong
 */
public class OSGiBundleBuilderArgs {

	public File getBaseDir() {
		return _baseDir;
	}

	public File getBndFile() {
		return _bndFile;
	}

	public File getClassesDir() {
		return _classesDir;
	}

	public List<File> getClasspath() {
		return _classpath;
	}

	public File getOutputDir() {
		return _outputDir;
	}

	public File getResourcesDir() {
		return _resourcesDir;
	}

	public boolean isHelp() {
		return _help;
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

	public void setClasspath(List<File> classpath) {
		_classpath = classpath;
	}

	public void setHelp(boolean help) {
		_help = help;
	}

	public void setOutputDir(File outputDir) {
		_outputDir = outputDir;
	}

	public void setResourcesDir(File resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	@Parameter(
		description = "The base directory.", names = {"--base-dir"},
		required = true
	)
	private File _baseDir;

	@Parameter(
		description = "The location of the Bnd file.", names = {"--bnd-file"},
		required = true
	)
	private File _bndFile;

	@Parameter(
		description = "The directory which contains the class files.",
		names = {"--classes-dir"}
	)
	private File _classesDir;

	@Parameter(
		description = "The list of directories and JAR files to include in the classpath.",
		names = {"--classpath"}, splitter = PathParameterSplitter.class
	)
	private List<File> _classpath;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "The output directory.", names = {"-o", "--output-dir"},
		required = true
	)
	private File _outputDir;

	@Parameter(
		description = "The directory that contains the processed resources.",
		names = {"--resources-dir"}
	)
	private File _resourcesDir;

}