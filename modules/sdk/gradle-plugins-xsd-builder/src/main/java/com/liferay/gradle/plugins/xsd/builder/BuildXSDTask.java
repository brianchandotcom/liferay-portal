/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.xsd.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class BuildXSDTask extends Zip {

	public BuildXSDTask() {
		Property<String> archiveAppendixProperty = getArchiveAppendix();
		Property<String> archiveExtensionProperty = getArchiveExtension();
		Property<String> archiveVersionProperty = getArchiveVersion();

		archiveAppendixProperty.set("xbean");
		archiveExtensionProperty.set(Jar.DEFAULT_EXTENSION);
		archiveVersionProperty.set("");
	}

	@Override
	public DirectoryProperty getDestinationDirectory() {
		DirectoryProperty destinationDirectory =
			super.getDestinationDirectory();

		if (_destinationDir != null) {
			destinationDirectory.set(
				GradleUtil.toFile(getProject(), _destinationDir));
		}

		return destinationDirectory;
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getInputDir() {
		return GradleUtil.toFile(getProject(), _inputDir);
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	@SkipWhenEmpty
	public FileCollection getInputFiles() {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", getInputDir());
		args.put("include", "**/*.*");

		return project.fileTree(args);
	}

	public void setInputDir(Object inputDir) {
		_inputDir = inputDir;
	}

	protected void setDestinationDir(Callable<File> callable) {
		_destinationDir = callable;
	}

	private Callable<File> _destinationDir;
	private Object _inputDir;

}