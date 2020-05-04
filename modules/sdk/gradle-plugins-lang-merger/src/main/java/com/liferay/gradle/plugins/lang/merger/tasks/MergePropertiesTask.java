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

package com.liferay.gradle.plugins.lang.merger.tasks;

import com.liferay.gradle.plugins.lang.merger.internal.util.MergePropertiesUtil;
import com.liferay.gradle.plugins.lang.merger.internal.work.MergePropertiesWorkAction;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

/**
 * @author Andrea Di Giorgi
 */
public class MergePropertiesTask extends DefaultTask {

	@Inject
	public MergePropertiesTask(WorkerExecutor workerExecutor) {
		Project project = getProject();

		_mergePropertiesSettings = project.container(
			MergePropertiesSetting.class);

		_workerExecutor = workerExecutor;
	}

	@Input
	public String getCharsetName() {
		return GradleUtil.toString(_charsetName);
	}

	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@OutputFiles
	public FileCollection getDestinationFiles() {
		Set<File> destinationFiles = new HashSet<>();

		File destinationDir = getDestinationDir();
		Project project = getProject();

		for (File sourceDir : getSourceDirs()) {
			for (File sourceFile : _getSourceFiles(sourceDir)) {
				String fileName = FileUtil.relativize(sourceFile, sourceDir);

				File destinationFile = new File(destinationDir, fileName);

				destinationFiles.add(destinationFile);
			}
		}

		return project.files(destinationFiles);
	}

	public String getPattern() {
		return _PATTERN;
	}

	public NamedDomainObjectContainer<MergePropertiesSetting> getSettings() {
		return _mergePropertiesSettings;
	}

	public FileCollection getSourceDirs() {
		Project project = getProject();

		return project.files(_sourceDirs);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getSourceFiles() {
		List<FileCollection> fileCollections = new ArrayList<>();

		Project project = getProject();

		for (File sourceDir : getSourceDirs()) {
			FileCollection fileCollection = _getSourceFiles(sourceDir);

			fileCollections.add(fileCollection);
		}

		return project.files(fileCollections.toArray());
	}

	@TaskAction
	public void merge() {
		WorkQueue workQueue = _workerExecutor.noIsolation();

		workQueue.submit(
			MergePropertiesWorkAction.class,
			mergePropertiesWorkParameters -> {
				Property<String> charsetNameProperty =
					mergePropertiesWorkParameters.getCharsetName();

				charsetNameProperty.set(getCharsetName());

				Property<Boolean> copyAllowedProperty =
					mergePropertiesWorkParameters.getCopyAllowed();

				copyAllowedProperty.set(_isCopyAllowed());

				DirectoryProperty destinationDirDirectoryProperty =
					mergePropertiesWorkParameters.getDestinationDir();

				File destinationDir = getDestinationDir();

				destinationDirDirectoryProperty.set(destinationDir);

				ConfigurableFileCollection
					destinationFilesConfigurableFileCollection =
						mergePropertiesWorkParameters.getDestinationFiles();

				destinationFilesConfigurableFileCollection.setFrom(
					getDestinationFiles());

				ConfigurableFileCollection
					sourceDirsConfigurableFileCollection =
						mergePropertiesWorkParameters.getSourceDirs();

				FileCollection sourceDirs = getSourceDirs();

				sourceDirsConfigurableFileCollection.setFrom(sourceDirs);

				MapProperty<String, MergePropertiesSetting>
					settingsMapProperty =
						mergePropertiesWorkParameters.getSettings();

				settingsMapProperty.set(_mergePropertiesSettings.getAsMap());

				Logger logger = getLogger();

				if (logger.isInfoEnabled()) {
					logger.info(
						"Merged " + CollectionUtils.join(", ", sourceDirs) +
							" into " + destinationDir);
				}
			});
	}

	public void setCharsetName(Object charsetName) {
		_charsetName = charsetName;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setSourceDirs(Iterable<Object> sourceDirs) {
		_sourceDirs.clear();

		sourceDirs(sourceDirs);
	}

	public void setSourceDirs(Object... sourceDirs) {
		setSourceDirs(Arrays.asList(sourceDirs));
	}

	public MergePropertiesSetting setting(Object object, Closure<Void> closure)
		throws IOException {

		File dir = GradleUtil.toFile(getProject(), object);

		return _mergePropertiesSettings.create(
			MergePropertiesUtil.getSettingName(dir), closure);
	}

	public MergePropertiesTask sourceDirs(Iterable<Object> sourceDirs) {
		GUtil.addToCollection(_sourceDirs, sourceDirs);

		return this;
	}

	public MergePropertiesTask sourceDirs(Object... sourceDirs) {
		return sourceDirs(Arrays.asList(sourceDirs));
	}

	private FileCollection _getSourceFiles(File sourceDir) {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", sourceDir);
		args.put("include", getPattern());

		return project.fileTree(args);
	}

	private boolean _isCopyAllowed() {
		if (_mergePropertiesSettings.isEmpty()) {
			return true;
		}

		return false;
	}

	private static final String _PATTERN = "*.properties";

	private Object _charsetName = StandardCharsets.UTF_8.name();
	private Object _destinationDir;
	private final NamedDomainObjectContainer<MergePropertiesSetting>
		_mergePropertiesSettings;
	private final Set<Object> _sourceDirs = new LinkedHashSet<>();
	private final WorkerExecutor _workerExecutor;

}