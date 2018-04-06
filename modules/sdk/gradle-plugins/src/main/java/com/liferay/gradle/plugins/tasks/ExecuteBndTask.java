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

package com.liferay.gradle.plugins.tasks;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.JarBuilder;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.Factory;

/**
 * @author Andrea Di Giorgi
 */
public class ExecuteBndTask extends DefaultTask {

	public ExecuteBndTask() {
		Project project = getProject();

		_baseDir = project.getProjectDir();
	}

	@TaskAction
	public void executeBnd() {
		Logger logger = getLogger();
		Properties properties = getProperties();

		long clockStart = System.currentTimeMillis();

		BundleExtension bundleExtension = GradleUtil.getExtension(
			getProject(), BundleExtension.class);

		Factory<JarBuilder> jarBuilderFactory =
			bundleExtension.getJarBuilderFactory();

		JarBuilder jarBuilder = jarBuilderFactory.create();

		jarBuilder.withBase(getBaseDir());
		jarBuilder.withClasspath(_toArray(getClasspath()));
		jarBuilder.withFailOnError(isFailOnError());
		jarBuilder.withName(
			properties.getProperty(Constants.BUNDLE_SYMBOLICNAME));
		jarBuilder.withProperties(properties);
		jarBuilder.withResources(_toArray(getResourceDirs()));
		jarBuilder.withSourcepath(getSourceDirs());
		jarBuilder.withVersion(
			properties.getProperty(Constants.BUNDLE_VERSION));

		if (logger.isInfoEnabled()) {
			jarBuilder.withTrace(true);
		}
		else {
			jarBuilder.withTrace(true);
		}

		File outputFile = getOutputFile();

		if (isWriteManifest()) {
			File outputDir = outputFile.getParentFile();

			outputDir.mkdirs();

			try (OutputStream outputStream = new FileOutputStream(outputFile)) {
				jarBuilder.writeManifestTo(outputStream);
			}
			catch (IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}
		else {
			jarBuilder.writeJarTo(outputFile);
		}

		if (logger.isLifecycleEnabled()) {
			long clockStop = System.currentTimeMillis();

			logger.lifecycle(
				"Building the {} file took {} seconds.", outputFile.getName(),
				(clockStart - clockStop) / 1000);
		}
	}

	@Input
	public File getBaseDir() {
		return GradleUtil.toFile(getProject(), _baseDir);
	}

	@InputFiles
	public FileCollection getClasspath() {
		return _classpath;
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	public Properties getProperties() {
		Properties properties = new Properties();

		for (Map.Entry<String, Object> entry : _properties.entrySet()) {
			properties.setProperty(
				entry.getKey(), GradleUtil.toString(entry.getValue()));
		}

		return properties;
	}

	@InputFiles
	public FileCollection getResourceDirs() {
		return _resourceDirs;
	}

	@InputFiles
	public FileCollection getSourceDirs() {
		return _sourceDirs;
	}

	public boolean isFailOnError() {
		return _failOnError;
	}

	@Input
	public boolean isWriteManifest() {
		return _writeManifest;
	}

	public ExecuteBndTask properties(Map<String, ?> properties) {
		for (Map.Entry<String, ?> entry : properties.entrySet()) {
			property(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public ExecuteBndTask property(String key, Object value) {
		_properties.put(key, value);

		return this;
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	public void setClasspath(FileCollection classpath) {
		_classpath = classpath;
	}

	public void setFailOnError(boolean failOnError) {
		_failOnError = failOnError;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setProperties(Map<String, ?> properties) {
		_properties.clear();

		properties(properties);
	}

	public void setResourceDirs(FileCollection resourceDirs) {
		_resourceDirs = resourceDirs;
	}

	public void setSourceDirs(FileCollection sourceDirs) {
		_sourceDirs = sourceDirs;
	}

	public void setWriteManifest(boolean writeManifest) {
		_writeManifest = writeManifest;
	}

	private static File[] _toArray(FileCollection fileCollection) {
		Set<File> files = fileCollection.getFiles();

		return files.toArray(new File[0]);
	}

	private Object _baseDir;
	private FileCollection _classpath;
	private boolean _failOnError = true;
	private Object _outputFile;
	private final Map<String, Object> _properties = new LinkedHashMap<>();
	private FileCollection _resourceDirs;
	private FileCollection _sourceDirs;
	private boolean _writeManifest;

}