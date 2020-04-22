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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class MergePropertiesTask extends DefaultTask {

	public MergePropertiesTask() {
		Project project = getProject();

		_mergePropertiesSettings = project.container(
			MergePropertiesSetting.class);
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
	@PathSensitive(PathSensitivity.RELATIVE)
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
	public void merge() throws IOException {
		File destinationDir = getDestinationDir();
		FileCollection sourceDirs = getSourceDirs();

		for (File destinationFile : getDestinationFiles()) {
			Set<File> sourceFiles = new LinkedHashSet<>();

			String fileName = FileUtil.relativize(
				destinationFile, destinationDir);

			for (File sourceDir : sourceDirs) {
				File sourceFile = new File(sourceDir, fileName);

				if (sourceFile.exists()) {
					sourceFiles.add(sourceFile);
				}
			}

			File destinationFileDir = destinationFile.getParentFile();

			destinationFileDir.mkdirs();

			if ((sourceFiles.size() == 1) && _isCopyAllowed()) {
				Iterator<File> iterator = sourceFiles.iterator();

				File sourceFile = iterator.next();

				Files.copy(
					sourceFile.toPath(), destinationFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);

				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Copied " + sourceFile + " into " + destinationFile);
				}
			}
			else {
				_merge(sourceFiles, destinationFile);

				if (_logger.isInfoEnabled()) {
					_logger.info(
						"Merged " + CollectionUtils.join(", ", sourceFiles) +
							" into " + destinationFile);
				}
			}
		}
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

		return _mergePropertiesSettings.create(_getSettingName(dir), closure);
	}

	public MergePropertiesTask sourceDirs(Iterable<Object> sourceDirs) {
		GUtil.addToCollection(_sourceDirs, sourceDirs);

		return this;
	}

	public MergePropertiesTask sourceDirs(Object... sourceDirs) {
		return sourceDirs(Arrays.asList(sourceDirs));
	}

	private String _getSettingName(File file) throws IOException {
		if (file.isFile()) {
			file = file.getParentFile();
		}

		String name = file.getCanonicalPath();

		if (File.separatorChar != '/') {
			name = name.replace(File.separatorChar, '/');
		}

		return name;
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

	private void _merge(Set<File> sourceFiles, File destinationFile)
		throws IOException {

		Charset charset = Charset.forName(getCharsetName());

		Properties mergedProperties = new Properties();

		for (File sourceFile : sourceFiles) {
			if (!sourceFile.exists()) {
				continue;
			}

			Properties sourceProperties = new Properties();

			try (BufferedReader bufferedReader = Files.newBufferedReader(
					sourceFile.toPath(), charset)) {

				sourceProperties.load(bufferedReader);
			}

			MergePropertiesSetting mergePropertiesSetting =
				_mergePropertiesSettings.findByName(
					_getSettingName(sourceFile));

			if (mergePropertiesSetting != null) {
				Map<String, String> transformKeys =
					mergePropertiesSetting.getTransformKeys();

				for (Map.Entry<String, String> entry :
						transformKeys.entrySet()) {

					String sourceKey = entry.getKey();
					String destinationKey = entry.getValue();

					String value = sourceProperties.getProperty(sourceKey);

					mergedProperties.setProperty(destinationKey, value);
				}
			}
			else {
				mergedProperties.putAll(sourceProperties);
			}
		}

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				destinationFile.toPath(), charset)) {

			mergedProperties.store(bufferedWriter, null);
		}
	}

	private static final String _PATTERN = "*.properties";

	private static final Logger _logger = Logging.getLogger(
		MergePropertiesTask.class);

	private Object _charsetName = StandardCharsets.UTF_8.name();
	private Object _destinationDir;
	private final NamedDomainObjectContainer<MergePropertiesSetting>
		_mergePropertiesSettings;
	private final Set<Object> _sourceDirs = new LinkedHashSet<>();

}