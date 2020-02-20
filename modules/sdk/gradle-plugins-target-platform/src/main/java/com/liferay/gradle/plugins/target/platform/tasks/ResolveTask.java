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
import aQute.bnd.build.model.clauses.HeaderClause;
import aQute.bnd.build.model.conversions.Converter;
import aQute.bnd.gradle.FileSetRepositoryConvention;
import aQute.bnd.gradle.PropertiesWrapper;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Processor;
import aQute.bnd.service.RepositoryPlugin;

import aQute.service.reporter.Report;

import biz.aQute.resolve.Bndrun;
import biz.aQute.resolve.ResolveProcess;

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;

import java.io.File;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.StartParameter;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.Convention;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

import org.osgi.service.resolver.ResolutionException;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class ResolveTask extends DefaultTask {

	public ResolveTask() {
		Project project = getProject();

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		_offline = startParameter.isOffline();

		Convention convention = getConvention();

		Map<String, Object> plugins = convention.getPlugins();

		plugins.put("bundles", new FileSetRepositoryConvention(this));
	}

	@InputFile
	public File getBndrunFile() {
		return GradleUtil.toFile(getProject(), _bndrunFile);
	}

	@InputFile
	public File getDistroFile() {
		return _distroFileCollection.getSingleFile();
	}

	public List<String> getRunBundles() {
		return _runBundles;
	}

	@Input
	public boolean isFailOnChanges() {
		return GradleUtil.toBoolean(_failOnChanges);
	}

	public boolean isOffline() {
		return GradleUtil.toBoolean(_offline);
	}

	@Input
	public boolean isReportOptional() {
		return GradleUtil.toBoolean(_reportOptional);
	}

	@TaskAction
	public void resolve() throws Exception {
		Logger logger = getLogger();
		Project project = getProject();
		File bndrunFile = getBndrunFile();
		File temporaryDir = getTemporaryDir();

		try (Bndrun bndrun = Bndrun.createBndrun(null, bndrunFile)) {
			Workspace workspace = bndrun.getWorkspace();

			bndrun.setBase(temporaryDir);

			workspace.setOffline(isOffline());

			File cnfDir = new File(temporaryDir, Workspace.CNFDIR);

			project.mkdir(cnfDir);

			workspace.setBuildDir(cnfDir);

			Convention convention = getConvention();

			FileSetRepositoryConvention fileSetRepositoryConvention =
				convention.findPlugin(FileSetRepositoryConvention.class);

			if (fileSetRepositoryConvention != null) {
				workspace.addBasicPlugin(
					fileSetRepositoryConvention.getFileSetRepository(
						getName()));

				for (RepositoryPlugin repositoryPlugin :
						workspace.getRepositories()) {

					repositoryPlugin.list(null);
				}
			}

			bndrun.getInfo(workspace);

			_logReport(bndrun, logger);

			if (!bndrun.isOk()) {
				throw new GradleException(
					bndrun.getPropertiesFile() + " has workspace errors");
			}

			try {
				Properties gradleProperties = new PropertiesWrapper();

				gradleProperties.put("project", project);

				File distroFile = getDistroFile();

				String distroReference =
					distroFile.getAbsolutePath() + ";version=file";

				gradleProperties.put("targetPlatformDistro", distroReference);

				gradleProperties.put("task", this);

				Processor processor = new ProcessorWrapper(gradleProperties);

				processor.setParent(bndrun.getParent());

				bndrun.setParent(processor);

				logger.info(
					"Resolving bundles required for {}",
					bndrun.getPropertiesFile());

				_runBundles = bndrun.resolve(
					isFailOnChanges(), false, _runbundlesFormatter);

				Stream<String> stream = _runBundles.stream();

				logger.lifecycle(
					"{}:\n    {}", Constants.RUNBUNDLES,
					stream.collect(Collectors.joining("\n    ")));
			}
			catch (ResolutionException resolutionException) {
				logger.error(
					ResolveProcess.format(
						resolutionException, isReportOptional()));

				throw new GradleException(
					bndrun.getPropertiesFile() + " resolution exception",
					resolutionException);
			}
			finally {
				_logReport(bndrun, logger);
			}

			if (!bndrun.isOk()) {
				throw new GradleException(
					bndrun.getPropertiesFile() + " resolution failure");
			}
		}
	}

	public void setBndrunFile(Object bndrunFile) {
		_bndrunFile = bndrunFile;
	}

	public void setDistro(FileCollection distroFileCollection) {
		_distroFileCollection = distroFileCollection;
	}

	public void setFailOnChanges(Object failOnChanges) {
		_failOnChanges = failOnChanges;
	}

	public void setOffline(Object offline) {
		_offline = offline;
	}

	public void setReportOptional(Object reportOptional) {
		_reportOptional = reportOptional;
	}

	private void _logReport(Report report, Logger logger) {
		if (logger.isWarnEnabled()) {
			for (String warning : report.getWarnings()) {
				Report.Location location = report.getLocation(warning);

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
				Report.Location location = report.getLocation(error);

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

	private static Converter<List<String>, Collection<? extends HeaderClause>>
		_runbundlesFormatter =
			new Converter<List<String>, Collection<? extends HeaderClause>>() {

				@Override
				public List<String> convert(
						Collection<? extends HeaderClause> input)
					throws IllegalArgumentException {

					Stream<? extends HeaderClause> stream = input.stream();

					return stream.map(
						HeaderClause::toString
					).collect(
						Collectors.toList()
					);
				}

				@Override
				public List<String> error(String msg) {
					return null;
				}

			};

	private Object _bndrunFile;
	private FileCollection _distroFileCollection;
	private Object _failOnChanges = Boolean.FALSE;
	private Object _offline;
	private Object _reportOptional = Boolean.TRUE;
	private List<String> _runBundles = Collections.emptyList();

	private static class ProcessorWrapper extends Processor {

		public ProcessorWrapper(Properties internalProperties) {
			_internalProperties = internalProperties;
		}

		public Properties getProperties() {
			return _internalProperties;
		}

		private final Properties _internalProperties;

	}

}