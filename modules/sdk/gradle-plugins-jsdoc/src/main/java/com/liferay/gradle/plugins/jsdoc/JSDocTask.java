/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.jsdoc;

import com.liferay.gradle.plugins.node.task.ExecuteNodeScriptTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.resources.TextResource;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class JSDocTask extends ExecuteNodeScriptTask {

	public JSDocTask() {
		Project project = getProject();

		File packageJsonFile = project.file("package.json");

		if (packageJsonFile.exists()) {
			setPackageJsonFile(packageJsonFile);
		}

		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					for (File sourceDir : getSourceDirs()) {
						if (sourceDir.exists()) {
							return true;
						}
					}

					return false;
				}

			});
	}

	@Nested
	@Optional
	public TextResource getConfiguration() {
		return _configurationTextResource;
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getPackageJsonFile() {
		return GradleUtil.toFile(getProject(), _packageJsonFile);
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getReadmeFile() {
		return GradleUtil.toFile(getProject(), _readmeFile);
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	public FileCollection getSourceDirs() {
		Project project = getProject();

		return project.files(_sourceDirs);
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getTutorialsDir() {
		return GradleUtil.toFile(getProject(), _tutorialsDir);
	}

	public void setConfiguration(TextResource configurationTextResource) {
		_configurationTextResource = configurationTextResource;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setPackageJsonFile(Object packageJsonFile) {
		_packageJsonFile = packageJsonFile;
	}

	public void setReadmeFile(Object readmeFile) {
		_readmeFile = readmeFile;
	}

	public void setSourceDirs(Iterable<Object> sourceDirs) {
		_sourceDirs.clear();

		sourceDirs(sourceDirs);
	}

	public void setSourceDirs(Object... sourceDirs) {
		setSourceDirs(Arrays.asList(sourceDirs));
	}

	public void setTutorialsDir(Object tutorialsDir) {
		_tutorialsDir = tutorialsDir;
	}

	public JSDocTask sourceDirs(Iterable<Object> sourceDirs) {
		GUtil.addToCollection(_sourceDirs, sourceDirs);

		return this;
	}

	public JSDocTask sourceDirs(Object... sourceDirs) {
		return sourceDirs(Arrays.asList(sourceDirs));
	}

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		Logger logger = getLogger();

		for (File sourceDir : getSourceDirs()) {
			completeArgs.add(_relativize(sourceDir));
		}

		TextResource configurationTextResource = getConfiguration();

		if (configurationTextResource != null) {
			completeArgs.add("--configure");
			completeArgs.add(
				_relativize(
					configurationTextResource.asFile(
						StandardCharsets.UTF_8.name())));
		}

		if (logger.isDebugEnabled()) {
			completeArgs.add("--debug");
		}

		_addArg(completeArgs, "--destination", getDestinationDir());
		_addArg(completeArgs, "--package", getPackageJsonFile());

		completeArgs.add("--recurse");

		_addArg(completeArgs, "--readme", getReadmeFile());
		_addArg(completeArgs, "--tutorials", getTutorialsDir());

		if (logger.isInfoEnabled()) {
			completeArgs.add("--verbose");
		}

		return completeArgs;
	}

	private void _addArg(List<String> args, String name, File file) {
		if (file == null) {
			return;
		}

		args.add(name);
		args.add(_relativize(file));
	}

	private String _relativize(File file) {
		String relativePath = FileUtil.relativize(file, getWorkingDir());

		if (File.separatorChar != '/') {
			relativePath = relativePath.replace(File.separatorChar, '/');
		}

		return relativePath;
	}

	private TextResource _configurationTextResource;
	private Object _destinationDir;
	private Object _packageJsonFile;
	private Object _readmeFile;
	private final Set<Object> _sourceDirs = new LinkedHashSet<>();
	private Object _tutorialsDir;

}