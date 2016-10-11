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

package com.liferay.gradle.plugins.theme.builder;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Andrea Di Giorgi
 */
public class BuildThemeTask extends JavaExec {

	public BuildThemeTask() {
		setMain("com.liferay.portal.tools.theme.builder.ThemeBuilder");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@InputDirectory
	@Optional
	public File getDiffsDir() {
		return GradleUtil.toFile(getProject(), _diffsDir);
	}

	@OutputDirectory
	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	@InputDirectory
	@Optional
	public File getParentDir() {
		return GradleUtil.toFile(getProject(), _parentDir);
	}

	@InputFile
	@Optional
	public File getParentFile() {
		return GradleUtil.toFile(getProject(), _parentFile);
	}

	@Input
	@Optional
	public String getParentName() {
		return GradleUtil.toString(_parentName);
	}

	@Input
	public String getTemplateExtension() {
		return GradleUtil.toString(_templateExtension);
	}

	@Input
	public String getThemeName() {
		return GradleUtil.toString(_themeName);
	}

	@InputDirectory
	@Optional
	public File getUnstyledDir() {
		return GradleUtil.toFile(getProject(), _unstyledDir);
	}

	@InputFile
	@Optional
	public File getUnstyledFile() {
		return GradleUtil.toFile(getProject(), _unstyledFile);
	}

	public void setDiffsDir(Object diffsDir) {
		_diffsDir = diffsDir;
	}

	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	public void setParentDir(Object parentDir) {
		_parentDir = parentDir;
	}

	public void setParentFile(Object parentFile) {
		_parentFile = parentFile;
	}

	public void setParentName(Object parentName) {
		_parentName = parentName;
	}

	public void setTemplateExtension(Object templateExtension) {
		_templateExtension = templateExtension;
	}

	public void setThemeName(Object themeName) {
		_themeName = themeName;
	}

	public void setUnstyledDir(Object unstyledDir) {
		_unstyledDir = unstyledDir;
	}

	public void setUnstyledFile(Object unstyledFile) {
		_unstyledFile = unstyledFile;
	}

	private static void _addArg(List<String> args, String name, File file) {
		if (file != null) {
			_addArg(args, name, file.getAbsolutePath());
		}
	}

	private static void _addArg(List<String> args, String name, String value) {
		if (Validator.isNotNull(value)) {
			args.add(name);
			args.add(value);
		}
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		_addArg(args, "--diffs-dir", getDiffsDir());
		_addArg(args, "--name", getThemeName());
		_addArg(args, "--output-dir", getOutputDir());
		_addArg(args, "--parent-name", getParentName());
		_addArg(args, "--parent-path", _getParentPath());
		_addArg(args, "--template-extension", getTemplateExtension());
		_addArg(args, "--unstyled-path", _getUnstyledPath());

		return args;
	}

	private File _getParentPath() {
		File parentPath = getParentDir();

		if (parentPath == null) {
			parentPath = getParentFile();
		}

		return parentPath;
	}

	private File _getUnstyledPath() {
		File unstyledPath = getUnstyledDir();

		if (unstyledPath == null) {
			unstyledPath = getUnstyledFile();
		}

		return unstyledPath;
	}

	private Object _diffsDir;
	private Object _outputDir;
	private Object _parentDir;
	private Object _parentFile;
	private Object _parentName;
	private Object _templateExtension;
	private Object _themeName;
	private Object _unstyledDir;
	private Object _unstyledFile;

}