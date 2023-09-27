/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.task;

import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildExtInfoTask extends JavaExec {

	public BuildExtInfoTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.portal.tools.ExtInfoBuilder");

		setMaxHeapSize("256m");
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

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	@OutputFile
	public File getOutputFile() {
		return new File(
			getOutputDir(), "ext-" + getServletContextName() + ".xml");
	}

	@Input
	public String getServletContextName() {
		return GradleUtil.toString(_servletContextName);
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	public void setServletContextName(Object servletContextName) {
		_servletContextName = servletContextName;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = getArgs();

		List<String> completeArgs = new ArrayList<>(args.size() + 3);

		completeArgs.add(FileUtil.getAbsolutePath(getBaseDir()));
		completeArgs.add(FileUtil.getAbsolutePath(getOutputDir()));
		completeArgs.add(getServletContextName());
		completeArgs.addAll(args);

		return completeArgs;
	}

	private Object _baseDir;
	private Object _outputDir;
	private Object _servletContextName;

}