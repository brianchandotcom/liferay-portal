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
import aQute.bnd.header.Attrs;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Domain;
import aQute.bnd.repository.fileset.FileSetRepository;
import aQute.bnd.service.RepositoryPlugin;

import aQute.service.reporter.Report;
import aQute.service.reporter.Report.Location;

import biz.aQute.resolve.Bndrun;
import biz.aQute.resolve.ResolveProcess;

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
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
import org.gradle.api.tasks.StopExecutionException;
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
		return _bundles;
	}

	@InputFile
	public File getDistro() {
		return _distro.getSingleFile();
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getRequirements() {
		return _requirements;
	}

	@Input
	public boolean isIgnoreFailures() {
		return GradleUtil.toBoolean(_ignoreFailures);
	}

	@TaskAction
	public void resolve() {
		FileCollection requirements = getRequirements();

		File distro = getDistro();

		try {
			_writeBndrunFile(requirements, distro, getBndrunFile());
		}
		catch (IOException ioe) {
			StopExecutionException exception = new StopExecutionException();

			exception.initCause(ioe);

			throw exception;
		}

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

			FileCollection bundles = getBundles();

			FileSetRepository fileSetRepository = new FileSetRepository(
				getName(), bundles.getFiles());

			workspace.addBasicPlugin(fileSetRepository);

			logger.info(
				"Resolving runbundles required for {}",
				bndrun.getPropertiesFile());

			for (RepositoryPlugin repo : workspace.getRepositories()) {
				repo.list(null);
			}

			bndrun.getInfo(workspace);

			_logReport(bndrun, logger);

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
				_logReport(bndrun, logger);
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

	public void setBundles(FileCollection bundles) {
		_bundles = bundles;
	}

	public void setDistro(FileCollection distro) {
		_distro = distro;
	}

	public void setIgnoreFailures(Object ignoreFailures) {
		_ignoreFailures = ignoreFailures;
	}

	public void setRequirements(FileCollection requirements) {
		_requirements = requirements;
	}

	private static void _logReport(Report report, Logger logger) {
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

	private static void _writeBndrunFile(
			FileCollection requirements, File distroFile, File bndrunFile)
		throws IOException {

		try (BufferedWriter writer = Files.newBufferedWriter(
				bndrunFile.toPath(), Charset.forName("UTF-8"),
				StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING)) {

			writer.write("-standalone:\n");
			writer.write("-resourceonly: true\n");
			writer.write("-resolve.effective: resolve, active\n");
			writer.write(
				"-distro: \"" + distroFile.getAbsolutePath() +
					"\";version=file");

			writer.write("\n-runrequires:\\\n");

			List<File> runRequirements = new ArrayList<>();

			runRequirements.addAll(requirements.getFiles());

			for (File runRequirement : runRequirements) {
				try (JarFile jarFile = new JarFile(runRequirement)) {
					Manifest manifest = jarFile.getManifest();

					Domain jar = Domain.domain(manifest);

					Entry<String, Attrs> bundleSymbolicName =
						jar.getBundleSymbolicName();

					String bsnKey = bundleSymbolicName.getKey();

					writer.write(
						"  osgi.identity;filter:='(osgi.identity=" + bsnKey +
							")'");

					if (!runRequirement.equals(
							runRequirements.get(runRequirements.size() - 1))) {

						writer.write(",\\\n");
					}
				}
			}
		}
	}

	private Object _bndrunFile;
	private FileCollection _bundles;
	private FileCollection _distro;
	private Object _ignoreFailures = Boolean.FALSE;
	private FileCollection _requirements;

}