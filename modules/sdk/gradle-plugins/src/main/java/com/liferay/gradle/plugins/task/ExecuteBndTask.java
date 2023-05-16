/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.task;

import aQute.bnd.gradle.BndUtils;
import aQute.bnd.gradle.PropertiesWrapper;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Processor;
import aQute.bnd.version.MavenVersion;

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
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
@CacheableTask
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

			if (logger.isDebugEnabled() ||
				Boolean.getBoolean("build.bnd.print.builder.classpath")) {

				logger.lifecycle(
					"BND Builder Classpath {}: {}", project.getName(),
					buildDirs.getAsPath());
			}

			FileCollection sourceDirs = project.files(getSourceDirs());

			builder.setProperty("project.sourcepath", sourceDirs.getAsPath());
			builder.setSourcepath(_toArray(sourceDirs));

			if (logger.isDebugEnabled()) {
				logger.debug(
					"BND Builder Sourcepath {}: {}", project.getName(),
					sourceDirs.getAsPath());
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

				MavenVersion mavenVersion = MavenVersion.parseString(
					String.valueOf(project.getVersion()));

				builder.setProperty(
					Constants.BUNDLE_VERSION,
					String.valueOf(mavenVersion.getOSGiVersion()));
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
					"BND Builder Properties {}: {}", project.getName(),
					properties);
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
				catch (IOException ioException) {
					throw new GradleException(this + " failed", ioException);
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

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getBaseDir() {
		return GradleUtil.toFile(getProject(), _baseDir);
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
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
	@PathSensitive(PathSensitivity.RELATIVE)
	public FileCollection getResourceDirs() {
		return _resourceDirs;
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	public FileCollection getSourceDirs() {
		return _sourceDirs;
	}

	@Input
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