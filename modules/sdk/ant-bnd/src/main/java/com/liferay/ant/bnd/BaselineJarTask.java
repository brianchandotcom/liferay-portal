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

package com.liferay.ant.bnd;

import aQute.service.reporter.Reporter;

import java.io.File;
import java.io.FileInputStream;

import java.util.Map;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author Raymond Augé
 */
public class BaselineJarTask extends BaseBndTask {

	public void setBndFile(File bndFile) {
		_bndFile = bndFile;
	}

	public void setNewJarFile(File newJarFile) {
		_newJarFile = newJarFile;
	}

	public void setOldJarFile(File oldJarFile) {
		_oldJarFile = oldJarFile;
	}

	@Override
	public void trace(String format, Object... args) {
	}

	@Override
	protected void doBeforeExecute() throws BuildException {
		super.doBeforeExecute();

		File bndRootFile = getBndRootFile();

		File rootDir = bndRootFile.getParentFile();

		if ((_bndFile == null) || !_bndFile.exists() ||
			_bndFile.isDirectory()) {

			if (_bndFile != null) {
				log(
					"File is either missing or is a directory " +
						_bndFile.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("Bnd file is invalid");
		}

		if (_newJarFile == null) {
			throw new BuildException("New jar file is invalid");
		}

		_forceVersionOneOnAddedPackages = true;

		String forceVersionOneOnAddedPackages = project.getProperty(
			"baseline.added.packages.force.version.one");

		if (forceVersionOneOnAddedPackages != null) {
			_forceVersionOneOnAddedPackages = Boolean.parseBoolean(
				forceVersionOneOnAddedPackages);
		}

		_reportLevel = project.getProperty("baseline.jar.report.level");

		if (_reportLevel == null) {
			_reportLevel = "";
		}

		_reportLevelIsDiff = _reportLevel.equals("diff");
		_reportLevelIsOff = _reportLevel.equals("off");
		_reportLevelIsPersist = _reportLevel.equals("persist");

		if (_reportLevelIsPersist) {
			_reportLevelIsDiff = true;

			File baselineReportsDir = new File(
				rootDir, getBaselineReportsDirName());

			_logFile = new File(
				baselineReportsDir, _newJarFile.getName() + ".log");
		}

		_reportOnlyDirtyPackages = Boolean.parseBoolean(
			project.getProperty("baseline.jar.report.only.dirty.packages"));

		String sourceDirName = project.getProperty("plugin.source.dir");

		if (sourceDirName == null) {
			sourceDirName = project.getBaseDir() + "/src";
		}

		_sourceDir = new File(sourceDirName);
	}

	@Override
	protected void doExecute() throws Exception {
		if (_reportLevelIsOff) {
			return;
		}

		Baseline baseline = new Baseline() {

			@Override
			protected void log(Reporter reporter) {
				report(reporter);
			}

			@Override
			protected void log(String output) {
				BaselineJarTask.this.log(output, Project.MSG_WARN);
			}

		};

		if (_reportLevelIsPersist) {
			baseline.setBndFile(_bndFile);
		}

		Properties properties = baseline.getProperties();

		properties.putAll(project.getProperties());
		properties.putAll(getBndFileProperties());

		baseline.setForceVersionOneOnAddedPackages(
			_forceVersionOneOnAddedPackages);
		baseline.setLogFile(_logFile);
		baseline.setNewJarFile(_newJarFile);
		baseline.setOldJarFile(_oldJarFile);
		baseline.setReportDiff(_reportLevelIsDiff);
		baseline.setReportOnlyDirtyPackages(_reportOnlyDirtyPackages);
		baseline.setSourceDir(_sourceDir);

		baseline.execute();
	}

	protected String getBaselineReportsDirName() {
		if (_baselineReportsDirName != null) {
			return _baselineReportsDirName;
		}

		_baselineReportsDirName = project.getProperty(
			"baseline.jar.reports.dir.name");

		if (_baselineReportsDirName == null) {
			_baselineReportsDirName = _BASELINE_REPORTS_DIR;
		}

		return _baselineReportsDirName;
	}

	protected Map<? extends Object, ? extends Object> getBndFileProperties()
		throws Exception {

		Properties properties = new Properties();

		properties.load(new FileInputStream(_bndFile));

		return properties;
	}

	private static final String _BASELINE_REPORTS_DIR = "baseline-reports";

	private String _baselineReportsDirName;
	private File _bndFile;
	private boolean _forceVersionOneOnAddedPackages;
	private File _logFile;
	private File _newJarFile;
	private File _oldJarFile;
	private String _reportLevel;
	private boolean _reportLevelIsDiff;
	private boolean _reportLevelIsOff = true;
	private boolean _reportLevelIsPersist;
	private boolean _reportOnlyDirtyPackages;
	private File _sourceDir;

}