/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.baseline;

import com.liferay.gradle.plugins.baseline.internal.work.BaselineWorkAction;
import com.liferay.gradle.plugins.baseline.internal.work.BaselineWorkParameters;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import javax.inject.Inject;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.provider.Property;
import org.gradle.api.reporting.ReportingExtension;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.VerificationTask;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BaselineTask extends DefaultTask implements VerificationTask {

	@Inject
	public BaselineTask(WorkerExecutor workerExecutor) {
		_workerExecutor = workerExecutor;

		_logFileName = "baseline/" + getName() + ".log";
	}

	@TaskAction
	public void baseline() {
		WorkQueue workQueue = _workerExecutor.noIsolation();

		workQueue.submit(
			BaselineWorkAction.class,
			new Action<BaselineWorkParameters>() {

				@Override
				public void execute(
					BaselineWorkParameters baselineWorkParameters) {

					Logger logger = getLogger();

					if (logger.isInfoEnabled()) {
						StringBuilder sb = new StringBuilder();

						sb.append("Comparing ");
						sb.append(getNewJarFile());
						sb.append(" against ");
						sb.append(getOldJarFile());

						logger.info(sb.toString());
					}

					RegularFileProperty bndFileRegularFileProperty =
						baselineWorkParameters.getBndFile();

					bndFileRegularFileProperty.set(getBndFile());

					Property<Boolean> forceCalculatedVersionProperty =
						baselineWorkParameters.getForceCalculatedVersion();

					forceCalculatedVersionProperty.set(
						isForceCalculatedVersion());

					Property<Boolean> ignoreExcessiveVersionIncreasesProperty =
						baselineWorkParameters.
							getIgnoreExcessiveVersionIncreases();

					ignoreExcessiveVersionIncreasesProperty.set(
						isIgnoreExcessiveVersionIncreases());

					Property<Boolean> ignoreFailuresProperty =
						baselineWorkParameters.getIgnoreFailures();

					ignoreFailuresProperty.set(getIgnoreFailures());

					RegularFileProperty logFileRegularFileProperty =
						baselineWorkParameters.getLogFile();

					logFileRegularFileProperty.set(getLogFile());

					RegularFileProperty newJarFileRegularFileProperty =
						baselineWorkParameters.getNewJarFile();

					newJarFileRegularFileProperty.set(getNewJarFile());

					RegularFileProperty oldJarFileRegularFileProperty =
						baselineWorkParameters.getOldJarFile();

					oldJarFileRegularFileProperty.set(getOldJarFile());

					Property<Boolean> reportDiffProperty =
						baselineWorkParameters.getReportDiff();

					reportDiffProperty.set(isReportDiff());

					Property<Boolean> reportOnlyDirtyPackagesProperty =
						baselineWorkParameters.getReportOnlyDirtyPackages();

					reportOnlyDirtyPackagesProperty.set(
						isReportOnlyDirtyPackages());

					DirectoryProperty sourceDirDirectoryProperty =
						baselineWorkParameters.getSourceDir();

					sourceDirDirectoryProperty.set(getSourceDir());
				}

			});
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	public Configuration getBaselineConfiguration() {
		return _baselineConfiguration;
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getBndFile() {
		return GradleUtil.toFile(getProject(), _bndFile);
	}

	@Override
	public boolean getIgnoreFailures() {
		return _ignoreFailures;
	}

	@Optional
	@OutputFile
	public File getLogFile() {
		if (Validator.isNull(_logFileName)) {
			return null;
		}

		Project project = getProject();

		ExtensionContainer extensionContainer = project.getExtensions();

		ReportingExtension reportingExtension = extensionContainer.findByType(
			ReportingExtension.class);

		if (reportingExtension != null) {
			return reportingExtension.file(_logFileName);
		}

		return GradleUtil.toFile(project, _logFileName);
	}

	@InputFile
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getNewJarFile() {
		return GradleUtil.toFile(getProject(), _newJarFile);
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@Input
	public boolean isForceCalculatedVersion() {
		return _forceCalculatedVersion;
	}

	@Input
	public boolean isIgnoreExcessiveVersionIncreases() {
		return _ignoreExcessiveVersionIncreases;
	}

	@Input
	public boolean isReportDiff() {
		return _reportDiff;
	}

	@Input
	public boolean isReportOnlyDirtyPackages() {
		return _reportOnlyDirtyPackages;
	}

	public void setBaselineConfiguration(Configuration baselineConfiguration) {
		_baselineConfiguration = baselineConfiguration;
	}

	public void setBndFile(Object bndFile) {
		_bndFile = bndFile;
	}

	public void setForceCalculatedVersion(boolean forceCalculatedVersion) {
		_forceCalculatedVersion = forceCalculatedVersion;
	}

	public void setIgnoreExcessiveVersionIncreases(
		boolean ignoreExcessiveVersionIncreases) {

		_ignoreExcessiveVersionIncreases = ignoreExcessiveVersionIncreases;
	}

	@Override
	public void setIgnoreFailures(boolean ignoreFailures) {
		_ignoreFailures = ignoreFailures;
	}

	public void setLogFileName(String logFileName) {
		_logFileName = logFileName;
	}

	public void setNewJarFile(Object newJarFile) {
		_newJarFile = newJarFile;
	}

	public void setReportDiff(boolean reportDiff) {
		_reportDiff = reportDiff;
	}

	public void setReportOnlyDirtyPackages(boolean reportOnlyDirtyPackages) {
		_reportOnlyDirtyPackages = reportOnlyDirtyPackages;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	@Internal
	protected File getOldJarFile() {
		if (_oldJarFile != null) {
			return _oldJarFile;
		}

		Configuration baselineConfiguration = getBaselineConfiguration();

		_oldJarFile = baselineConfiguration.getSingleFile();

		return _oldJarFile;
	}

	private Configuration _baselineConfiguration;
	private Object _bndFile;
	private boolean _forceCalculatedVersion;
	private boolean _ignoreExcessiveVersionIncreases;
	private boolean _ignoreFailures;
	private String _logFileName;
	private Object _newJarFile;
	private File _oldJarFile;
	private boolean _reportDiff;
	private boolean _reportOnlyDirtyPackages;
	private Object _sourceDir;
	private final WorkerExecutor _workerExecutor;

}