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

import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderArgs;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.JavaExec;

/**
 * @author Andrea Di Giorgi
 */
public class BuildUpgradeTableTask extends JavaExec {

	public BuildUpgradeTableTask() {
		setMain(
			"com.liferay.portal.tools.upgrade.table.builder." +
				"UpgradeTableBuilder");
	}

	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	public String getBaseDirName() {
		return _upgradeTableBuilderArgs.getBaseDirName();
	}

	public String getUpgradeTableDirName() {
		return _upgradeTableBuilderArgs.getUpgradeTableDirName();
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

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("upgrade.base.dir=" + getBaseDirName());
		args.add("upgrade.osgi.module=" + isOsgiModule());
		args.add("upgrade.table.dir=" + getUpgradeTableDirName());

		return args;
	}

	private final UpgradeTableBuilderArgs _upgradeTableBuilderArgs =
		new UpgradeTableBuilderArgs();

}