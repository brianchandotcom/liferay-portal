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

import aQute.bnd.gradle.BndUtils;
import aQute.bnd.gradle.PropertiesWrapper;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Processor;
import aQute.bnd.version.MavenVersion;
import aQute.bnd.version.Version;

import aQute.lib.utf8properties.UTF8Properties;

import aQute.service.reporter.Report;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Manifest;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class ExecuteBndTask extends DefaultTask {

	public ExecuteBndTask() {
		Project project = getProject();

		_baseDir = project.getProjectDir();
	}

	@TaskAction
	public void executeBnd() throws Exception {
		Project project = getProject();

		Logger logger = getLogger();

		long clockStart = System.currentTimeMillis();

		Properties gradleProperties = new PropertiesWrapper();

		gradleProperties.put("project", project);
		gradleProperties.put("task", this);

		try (Builder builder = new Builder(
				new Processor(gradleProperties, false))) {

			Properties properties = getProperties();

			builder.setBase(getBaseDir());
			builder.setJar(new Jar("dot"));
			builder.setProperties(properties);

			FileCollection buildDirs = project.files(
				getClasspath(), getResourceDirs());

			builder.setClasspath(_toArray(buildDirs));
			builder.setProperty("project.buildpath", buildDirs.getAsPath());

			if (logger.isDebugEnabled()) {
				logger.debug("Builder Classpath: {}", buildDirs.getAsPath());
			}

			FileCollection sourceDirs = project.files(getSourceDirs());

			builder.setProperty("project.sourcepath", sourceDirs.getAsPath());
			builder.setSourcepath(_toArray(sourceDirs));

			if (logger.isDebugEnabled()) {
				logger.debug("Builder Sourcepath: {}", sourceDirs.getAsPath());
			}

			String bundleSymbolicName = builder.getProperty(
				Constants.BUNDLE_SYMBOLICNAME);

			if (Validator.isNull(bundleSymbolicName) ||
				Constants.EMPTY_HEADER.equals(bundleSymbolicName)) {

				builder.setProperty(
					Constants.BUNDLE_SYMBOLICNAME, project.getName());
			}

			String bundleVersion = builder.getProperty(
				Constants.BUNDLE_VERSION);

			if ((Validator.isNull(bundleVersion) ||
				 Constants.EMPTY_HEADER.equals(bundleVersion)) &&
				(project.getVersion() != null)) {

				Object version = project.getVersion();

				MavenVersion mavenVersion = MavenVersion.parseString(
					version.toString());

				Version osgiVersion = mavenVersion.getOSGiVersion();

				builder.setProperty(
					Constants.BUNDLE_VERSION, osgiVersion.toString());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Builder Properties: {}", properties);
			}

			Jar jar = builder.build();

			if (!builder.isOk()) {
				BndUtils.logReport(builder, logger);

				throw new GradleException(this + " failed");
			}

			File outputFile = getOutputFile();

			if (isWriteManifest()) {
				File outputDir = outputFile.getParentFile();

				outputDir.mkdirs();

				try (OutputStream outputStream = new FileOutputStream(
						outputFile)) {

					Manifest manifest = jar.getManifest();

					manifest.write(outputStream);
				}
				catch (IOException ioe) {
					throw new GradleException(this + " failed", ioe);
				}
			}
			else {
				jar.write(outputFile);
			}

			_logReport(builder, logger);

			if (!builder.isOk()) {
				throw new GradleException(this + " failed");
			}

			if (logger.isInfoEnabled()) {
				long clockStop = System.currentTimeMillis();

				logger.info(
					"Building the {} file took {} seconds.",
					outputFile.getName(), (clockStop - clockStart) / 1000);
			}
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
		Properties properties = new UTF8Properties();

		for (Map.Entry<String, Object> entry : _properties.entrySet()) {
			properties.put(
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

	private void _logReport(Report report, Logger logger) {
		if (logger.isWarnEnabled()) {
			for (String warning : report.getWarnings()) {
				Report.Location location = report.getLocation(warning);

				if ((location != null) && (location.file != null)) {
					logger.warn(
						"{}:{}:{}", location.file, location.line, warning);
				}
				else {
					logger.warn(warning);
				}
			}
		}

		if (logger.isErrorEnabled()) {
			for (String error : report.getErrors()) {
				Report.Location location = report.getLocation(error);

				if ((location != null) && (location.file != null)) {
					logger.error(
						"{}:{}:{}", location.file, location.line, error);
				}
				else {
					logger.error(error);
				}
			}
		}
	}

	private File[] _toArray(FileCollection fileCollection) {
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