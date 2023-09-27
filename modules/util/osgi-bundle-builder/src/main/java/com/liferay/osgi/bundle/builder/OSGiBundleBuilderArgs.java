/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder;

import aQute.lib.spring.SpringComponent;

import com.beust.jcommander.Parameter;

import com.liferay.ant.bnd.enterprise.EnterpriseAnalyzerPlugin;
import com.liferay.ant.bnd.jsp.JspAnalyzerPlugin;
import com.liferay.ant.bnd.npm.NpmAnalyzerPlugin;
import com.liferay.ant.bnd.resource.bundle.ResourceBundleLoaderAnalyzerPlugin;
import com.liferay.ant.bnd.sass.SassAnalyzerPlugin;
import com.liferay.ant.bnd.service.ServiceAnalyzerPlugin;
import com.liferay.ant.bnd.social.SocialAnalyzerPlugin;
import com.liferay.ant.bnd.spring.SpringDependencyAnalyzerPlugin;
import com.liferay.osgi.bundle.builder.internal.converters.PathParameterSplitter;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
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

	public File getOutputFile() {
		return _outputFile;
	}

	public List<String> getPlugins() {
		return _plugins;
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

	public void setClasspath(List<File> classpath) {
		_classpath = classpath;
	}

	public void setOutputFile(File outputFile) {
		_outputFile = outputFile;
	}

	public void setPlugins(List<String> plugins) {
		_plugins = plugins;
	}

	public void setResourcesDir(File resourcesDir) {
		_resourcesDir = resourcesDir;
	}

	protected boolean isHelp() {
		return _help;
	}

	private static final List<String> _defaultPlugins = Arrays.asList(
		SpringComponent.class.getName(), JspAnalyzerPlugin.class.getName(),
		NpmAnalyzerPlugin.class.getName(),
		ResourceBundleLoaderAnalyzerPlugin.class.getName(),
		SassAnalyzerPlugin.class.getName(),
		ServiceAnalyzerPlugin.class.getName(),
		SocialAnalyzerPlugin.class.getName(),
		SpringDependencyAnalyzerPlugin.class.getName(),
		EnterpriseAnalyzerPlugin.class.getName());

	@Parameter(description = "The base directory.", names = "--base-dir")
	private File _baseDir = new File(System.getProperty("user.dir"));

	@Parameter(
		description = "The location of the Bnd file.", names = "--bnd-file",
		required = true
	)
	private File _bndFile;

	@Parameter(
		description = "The directory or JAR file which contains the class files.",
		names = "--classes-dir"
	)
	private File _classesDir;

	@Parameter(
		description = "The list of directories and JAR files to include in the classpath.",
		names = "--classpath", splitter = PathParameterSplitter.class
	)
	private List<File> _classpath;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "The output path.", names = {"-o", "--output"},
		required = true
	)
	private File _outputFile;

	@Parameter(
		description = "The class names of the bnd plugins to use.",
		names = "--plugins"
	)
	private List<String> _plugins = new ArrayList<>(_defaultPlugins);

	@Parameter(
		description = "The directory or JAR file that contains the processed resources.",
		names = "--resources-dir"
	)
	private File _resourcesDir;

}