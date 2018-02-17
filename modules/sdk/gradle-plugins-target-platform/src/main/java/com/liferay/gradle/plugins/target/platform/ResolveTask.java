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

package com.liferay.gradle.plugins.target.platform;

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

import java.util.concurrent.Callable;
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
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

import org.osgi.service.resolver.ResolutionException;

/**
 * @author Gregory Amerson
 */
public class ResolveTask extends DefaultTask {

	public ResolveTask() {
		_bndrunFile = new Callable<File>() {

			@Override
			public File call() throws Exception {
				Project project = getProject();

				return new File(project.getBuildDir(), "resolve.bndrun");
			}

		};
	}

	@OutputFile
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

	@TaskAction
	public void resolve() throws IOException {
		_writeBndrunFile();

		File cnfDir = new File(getTemporaryDir(), Workspace.CNFDIR);

		Project project = getProject();

		project.mkdir(cnfDir);

		Logger logger = getLogger();

		try (Bndrun bndrun = Bndrun.createBndrun(null, getBndrunFile())) {
			bndrun.setBase(getTemporaryDir());

			Workspace workspace = bndrun.getWorkspace();

			workspace.setBuildDir(cnfDir);

			Gradle gradle = project.getGradle();

			StartParameter startParameter = gradle.getStartParameter();

			workspace.setOffline(startParameter.isOffline());

			FileCollection bundlesFileCollection = getBundles();

			FileSetRepository fileSetRepository = new FileSetRepository(
				getName(), bundlesFileCollection.getFiles());

			workspace.addBasicPlugin(fileSetRepository);

			logger.info(
				"Resolving runbundles required for {}",
				bndrun.getPropertiesFile());

			for (RepositoryPlugin repositoryPlugin :
					workspace.getRepositories()) {

				repositoryPlugin.list(null);
			}

			bndrun.getInfo(workspace);

			_logReport(bndrun);

			if (!bndrun.isOk()) {
				throw new GradleException(
					getBndrunFile() + " standalone workspace errors");
			}

			try {
				String result = bndrun.resolve(false, true);

				logger.info("{}: {}", Constants.RUNBUNDLES, result);
			}
			catch (ResolutionException re) {
				logger.error(
					"Unresolved requirements: {}",
					ResolveProcess.format(re.getUnresolvedRequirements()));

				if (!isIgnoreFailures()) {
					throw new GradleException(
						project.getName() + " resolution exception", re);
				}
			}
			finally {
				_logReport(bndrun);
			}

			if (!bndrun.isOk() && !isIgnoreFailures()) {
				throw new GradleException(
					project.getName() + " resolution failure");
			}
		}
		catch (Exception e) {
			String msg = project.getName() + " resolution exception: {}";

			logger.error(msg, e);

			if (!isIgnoreFailures()) {
				throw new GradleException(msg, e);
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
					logger.warn("warning: {}", warning);
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
					logger.error("error: {}", error);
				}
			}
		}
	}

	private void _writeBndrunFile() throws IOException {
		File bndrunFile = getBndrunFile();

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				bndrunFile.toPath(), StandardCharsets.UTF_8)) {

			bufferedWriter.write("-standalone:");
			bufferedWriter.newLine();

			bufferedWriter.write("-resourceonly: true");
			bufferedWriter.newLine();

			bufferedWriter.write("-resolve.effective: resolve, active");
			bufferedWriter.newLine();

			bufferedWriter.write("-distro: \"");
			bufferedWriter.write(FileUtil.getAbsolutePath(getDistroFile()));
			bufferedWriter.write("\";version=file");
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
					"\tosgi.identity;filter:='(osgi.identity=\"");
				bufferedWriter.write(bundleSymbolicName);
				bufferedWriter.write(")'");
			}
		}
	}

	private Object _bndrunFile;
	private FileCollection _bundlesFileCollection;
	private FileCollection _distroFileCollection;
	private Object _ignoreFailures;
	private FileCollection _requirementsFileCollection;

}