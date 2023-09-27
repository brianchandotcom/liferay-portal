/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.upgrade.table.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilderArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildUpgradeTableTask extends JavaExec {

	public BuildUpgradeTableTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set(
			"com.liferay.portal.tools.upgrade.table.builder." +
				"UpgradeTableBuilder");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getBaseDir() {
		return GradleUtil.toFile(getProject(), _baseDir);
	}

	@Input
	@Optional
	public String getReleaseInfoVersion() {
		return GradleUtil.toString(_releaseInfoVersion);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getUpgradeTableDir() {
		return GradleUtil.toFile(getProject(), _upgradeTableDir);
	}

	@Input
	public boolean isOsgiModule() {
		return _osgiModule;
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	public void setOsgiModule(boolean osgiModule) {
		_osgiModule = osgiModule;
	}

	public void setReleaseInfoVersion(Object releaseInfoVersion) {
		_releaseInfoVersion = releaseInfoVersion;
	}

	public void setUpgradeTableDir(Object upgradeTableDir) {
		_upgradeTableDir = upgradeTableDir;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("upgrade.base.dir=" + FileUtil.getAbsolutePath(getBaseDir()));
		args.add("upgrade.osgi.module=" + isOsgiModule());

		String releaseInfoVersion = getReleaseInfoVersion();

		if (releaseInfoVersion != null) {
			args.add("upgrade.release.info.version=" + releaseInfoVersion);
		}

		args.add(
			"upgrade.table.dir=" +
				FileUtil.getAbsolutePath(getUpgradeTableDir()));

		return args;
	}

	private Object _baseDir = UpgradeTableBuilderArgs.BASE_DIR_NAME;
	private boolean _osgiModule = UpgradeTableBuilderArgs.OSGI_MODULE;
	private Object _releaseInfoVersion;
	private Object _upgradeTableDir;

}