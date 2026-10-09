/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.schema.manifest.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Luis Ortiz
 */
public class BuildSchemaManifestTask extends JavaExec {

	public BuildSchemaManifestTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set(
			"com.liferay.portal.tools.schema.manifest.builder." +
				"SchemaManifestBuilder");
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

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getManifestFile() {
		File file = GradleUtil.toFile(getProject(), _manifestFile);

		if (!file.exists()) {
			return null;
		}

		return file;
	}

	@Input
	@Optional
	public String getModule() {
		return GradleUtil.toString(_module);
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	public void setManifestFile(Object manifestFile) {
		_manifestFile = manifestFile;
	}

	public void setModule(Object module) {
		_module = module;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add(
			"schema.manifest.base.dir=" +
				FileUtil.getAbsolutePath(getBaseDir()));

		if (_manifestFile != null) {
			File manifestFile = GradleUtil.toFile(getProject(), _manifestFile);

			args.add(
				"schema.manifest.file=" +
					FileUtil.relativize(manifestFile, getBaseDir()));
		}

		String module = getModule();

		if (module != null) {
			args.add("schema.manifest.module=" + module);
		}

		return args;
	}

	private Object _baseDir;
	private Object _manifestFile;
	private Object _module;

}