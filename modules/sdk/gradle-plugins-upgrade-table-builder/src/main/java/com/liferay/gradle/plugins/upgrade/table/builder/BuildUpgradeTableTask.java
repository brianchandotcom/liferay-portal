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

package com.liferay.gradle.plugins.upgrade.table.builder;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildUpgradeTableTask extends JavaExec {

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("upgrade.base.dir=" + getBaseDirName());
		args.add("upgrade.osgi.module=" + isOsgiModule());
		args.add("upgrade.table.dir=" + getUpgradeTableDirName());

		return args;
	}

	public String getBaseDirName() {
		return _upgradeTableBuilderArgs.getBaseDirName();
	}

	@Override
	public FileCollection getClasspath() {
		return GradleUtil.getConfiguration(
			getProject(), UpgradeTableBuilderPlugin.CONFIGURATION_NAME);
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.upgrade.table.builder." +
			"UpgradeTableBuilder";
	}

	public String getUpgradeTableDirName() {
		return _upgradeTableBuilderArgs.getUpgradeTableDirName();
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	public boolean isOsgiModule() {
		return _upgradeTableBuilderArgs.isOsgiModule();
	}

	public void setBaseDirName(String baseDirName) {
		_upgradeTableBuilderArgs.setBaseDirName(baseDirName);
	}

	public void setOsgiModule(boolean osgiModule) {
		_upgradeTableBuilderArgs.setOsgiModule(osgiModule);
	}

	public void setUpgradeTableDirName(String upgradeTableDirName) {
		_upgradeTableBuilderArgs.setUpgradeTableDirName(upgradeTableDirName);
	}

	private final UpgradeTableBuilderArgs _upgradeTableBuilderArgs =
		new UpgradeTableBuilderArgs();

}