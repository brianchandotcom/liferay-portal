/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.upgrade.table.builder.ant;

import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderArgs;
import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class BuildUpgradeTableTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			UpgradeTableBuilderInvoker.invoke(
				project.getBaseDir(), _upgradeTableBuilderArgs);
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setBaseDirName(String baseDirName) {
		_upgradeTableBuilderArgs.setBaseDirName(baseDirName);
	}

	public void setOsgiModule(boolean osgiModule) {
		_upgradeTableBuilderArgs.setOsgiModule(osgiModule);
	}

	public void setReleaseInfoVersion(String releaseInfoVersion) {
		_upgradeTableBuilderArgs.setReleaseInfoVersion(releaseInfoVersion);
	}

	public void setUpgradeTableDirName(String upgradeTableDirName) {
		_upgradeTableBuilderArgs.setUpgradeTableDirName(upgradeTableDirName);
	}

	private final UpgradeTableBuilderArgs _upgradeTableBuilderArgs =
		new UpgradeTableBuilderArgs();

}