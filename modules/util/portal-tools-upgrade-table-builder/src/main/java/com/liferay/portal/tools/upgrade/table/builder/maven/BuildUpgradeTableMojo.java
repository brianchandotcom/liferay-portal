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

package com.liferay.portal.tools.upgrade.table.builder.maven;

import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderArgs;
import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Builds upgrade table files.
 *
 * @author Andrea Di Giorgi
 * @goal build-upgrade-table
 */
public class BuildUpgradeTableMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			UpgradeTableBuilderInvoker.invoke(
				baseDir, _upgradeTableBuilderArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setBaseDirName(String baseDirName) {
		_upgradeTableBuilderArgs.setBaseDirName(baseDirName);
	}

	/**
	 * @parameter
	 */
	public void setOsgiModule(boolean osgiModule) {
		_upgradeTableBuilderArgs.setOsgiModule(osgiModule);
	}

	/**
	 * @parameter
	 */
	public void setUpgradeTableDirName(String upgradeTableDirName) {
		_upgradeTableBuilderArgs.setUpgradeTableDirName(upgradeTableDirName);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final UpgradeTableBuilderArgs _upgradeTableBuilderArgs =
		new UpgradeTableBuilderArgs();

}