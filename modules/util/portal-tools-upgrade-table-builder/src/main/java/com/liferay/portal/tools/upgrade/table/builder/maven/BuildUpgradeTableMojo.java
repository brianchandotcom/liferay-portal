/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @goal build
 */
public class BuildUpgradeTableMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			UpgradeTableBuilderInvoker.invoke(
				baseDir, _upgradeTableBuilderArgs);
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
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
	public void setReleaseInfoVersion(String releaseInfoVersion) {
		_upgradeTableBuilderArgs.setReleaseInfoVersion(releaseInfoVersion);
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