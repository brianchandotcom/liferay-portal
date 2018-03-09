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

package com.liferay.gradle.plugins.target.platform.tasks;

import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.Constants;
import aQute.bnd.repository.fileset.FileSetRepository;
import aQute.bnd.service.RepositoryPlugin;

import aQute.service.reporter.Report;
import aQute.service.reporter.Report.Location;

import biz.aQute.resolve.Bndrun;
import biz.aQute.resolve.ResolveProcess;

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.gradle.StartParameter;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

import org.osgi.service.resolver.ResolutionException;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 */
public class ResolveTask extends DefaultTask {

	public ResolveTask() {
		Project project = getProject();

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		_offline = startParameter.isOffline();
	}

	@InputFile
	@Optional
	public File getBndrunFile() {
		return GradleUtil.toFile(getProject(), _bndrunFile);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getBundles() {
		return _bundlesFileCollection;
	}

	public FileCollection getDistro() {
		return _distroFileCollection;
	}

	@InputFile
	public File getDistroFile() {
		return _distroFileCollection.getSingleFile();
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getRequirements() {
		return _requirementsFileCollection;
	}

	@Input
	public boolean isIgnoreFailures() {
		return GradleUtil.toBoolean(_ignoreFailures);
	}

	public boolean isOffline() {
		return GradleUtil.toBoolean(_offline);
	}

	@TaskAction
	public void resolve() throws IOException {
		Logger logger = getLogger();
		Project project = getProject();

		File bndrunFile = getBndrunFile();

		if (bndrunFile == null) {
			bndrunFile = _writeTemporaryBndrunFile();
		}

		File temporaryDir = getTemporaryDir();

		File cnfDir = new File(temporaryDir, Workspace.CNFDIR);

		project.mkdir(cnfDir);

		try (Bndrun bndrun = Bndrun.createBndrun(null, bndrunFile)) {
			bndrun.setBase(temporaryDir);

			Workspace workspace = bndrun.getWorkspace();

			workspace.setBuildDir(cnfDir);
			workspace.setOffline(isOffline());

			FileCollection bundlesFileCollection = getBundles();

			if (logger.isInfoEnabled()) {
				logger.info(
					"Bundles available for resolving: {}",
					bundlesFileCollection.getAsPath());
			}

			FileSetRepository fileSetRepository = new FileSetRepository(
				getName(), bundlesFileCollection.getFiles());

			workspace.addBasicPlugin(fileSetRepository);

			if (logger.isInfoEnabled()) {
				logger.info(
					"Resolving runbundles required for {}",
					bndrun.getPropertiesFile());
			}

			for (RepositoryPlugin repositoryPlugin :
					workspace.getRepositories()) {

				repositoryPlugin.list(null);
			}

			bndrun.getInfo(workspace);

			_logReport(bndrun);

			if (!bndrun.isOk()) {
				throw new GradleException(
					"Standalone workspace errors in " + bndrunFile);
			}

			try {
				String result = bndrun.resolve(false, true);

				if (logger.isInfoEnabled()) {
					logger.info("{}: {}", Constants.RUNBUNDLES, result);
				}
			}
			catch (ResolutionException re) {
				String message =
					"Unresolved requirements in " + project + ": " +
						ResolveProcess.format(re.getUnresolvedRequirements());

				if (isIgnoreFailures()) {
					logger.error(message);
				}
				else {
					throw new GradleException(message, re);
				}
			}
			finally {
				_logReport(bndrun);
			}

			if (!bndrun.isOk() && !isIgnoreFailures()) {
				throw new GradleException("Resolution failure in " + project);
			}
		}
		catch (Exception e) {
			String message =
				"Resolution exception in " + project + ": " + e.getMessage();

			if (isIgnoreFailures()) {
				logger.error(message);
			}
			else {
				throw new GradleException(message, e);
			}
		}
	}

	public void setBndrunFile(Object bndrunFile) {
		_bndrunFile = bndrunFile;
	}

	public void setBundles(FileCollection bundlesFileCollection) {
		_bundlesFileCollection = bundlesFileCollection;
	}

	public void setDistro(FileCollection distroFileCollection) {
		_distroFileCollection = distroFileCollection;
	}

	public void setIgnoreFailures(Object ignoreFailures) {
		_ignoreFailures = ignoreFailures;
	}

	public void setOffline(Object offline) {
		_offline = offline;
	}

	public void setRequirements(FileCollection requirementsFileCollection) {
		_requirementsFileCollection = requirementsFileCollection;
	}

	private String _getManifestValue(File file, String name)
		throws IOException {

		try (JarFile jarFile = new JarFile(file)) {
			Manifest manifest = jarFile.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			return attributes.getValue(name);
		}
	}

	private void _logReport(Report report) {
		Logger logger = getLogger();

		if (logger.isWarnEnabled()) {
			for (String warning : report.getWarnings()) {
				Location location = report.getLocation(warning);

				if ((location != null) && (location.file != null)) {
					logger.warn(
						"{}:{}: warning: {}", location.file, location.line,
						warning);
				}
				else {
					logger.warn("Warning: {}", warning);
				}
			}
		}

		if (logger.isErrorEnabled()) {
			for (String error : report.getErrors()) {
				Location location = report.getLocation(error);

				if ((location != null) && (location.file != null)) {
					logger.error(
						"{}:{}: error: {}", location.file, location.line,
						error);
				}
				else {
					logger.error("Error: {}", error);
				}
			}
		}
	}

	private File _writeTemporaryBndrunFile() throws IOException {
		File bndrunFile = new File(getTemporaryDir(), "resolve.bndrun");

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				bndrunFile.toPath(), StandardCharsets.UTF_8)) {

			bufferedWriter.write("-distro: \"");
			bufferedWriter.write(FileUtil.getAbsolutePath(getDistroFile()));
			bufferedWriter.write("\";version=file");
			bufferedWriter.newLine();

			bufferedWriter.write("-resolve.effective: active, resolve");
			bufferedWriter.newLine();

			bufferedWriter.write("-resourceonly: true");
			bufferedWriter.newLine();

			bufferedWriter.write("-runrequires:\\");
			bufferedWriter.newLine();

			boolean first = true;

			for (File requirementFile : getRequirements()) {
				String bundleSymbolicName = _getManifestValue(
					requirementFile, Constants.BUNDLE_SYMBOLICNAME);

				if (!first) {
					bufferedWriter.write(",\\");
					bufferedWriter.newLine();
				}
				else {
					first = false;
				}

				bufferedWriter.write(
					"\tosgi.identity;filter:='(osgi.identity=");
				bufferedWriter.write(bundleSymbolicName);
				bufferedWriter.write(")'");
			}

			bufferedWriter.newLine();

			bufferedWriter.write("-standalone:");
		}

		return bndrunFile;
	}

	private Object _bndrunFile;
	private FileCollection _bundlesFileCollection;
	private FileCollection _distroFileCollection;
	private Object _ignoreFailures;
	private Object _offline;
	private FileCollection _requirementsFileCollection;

}