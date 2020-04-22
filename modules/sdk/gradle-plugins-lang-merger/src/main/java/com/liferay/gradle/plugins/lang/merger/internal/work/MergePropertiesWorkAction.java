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

package com.liferay.gradle.plugins.lang.merger.internal.work;

import com.liferay.gradle.plugins.lang.merger.internal.util.MergePropertiesUtil;
import com.liferay.gradle.plugins.lang.merger.tasks.MergePropertiesSetting;
import com.liferay.gradle.util.FileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkAction;

/**
 * @author Peter Shin
 */
public abstract class MergePropertiesWorkAction
	implements WorkAction<MergePropertiesWorkParameters> {

	@Override
	public void execute() {
		MergePropertiesWorkParameters mergePropertiesWorkParameters =
			getParameters();

		try {
			Property<String> charsetNameProperty =
				mergePropertiesWorkParameters.getCharsetName();

			Property<Boolean> copyAllowedProperty =
				mergePropertiesWorkParameters.getCopyAllowed();

			DirectoryProperty destinationDirDirectoryProperty =
				mergePropertiesWorkParameters.getDestinationDir();

			Directory destinationDirDirectory =
				destinationDirDirectoryProperty.get();

			MapProperty<String, MergePropertiesSetting> settings =
				mergePropertiesWorkParameters.getSettings();

			_merge(
				charsetNameProperty.get(), copyAllowedProperty.get(),
				destinationDirDirectory.getAsFile(),
				mergePropertiesWorkParameters.getDestinationFiles(),
				mergePropertiesWorkParameters.getSourceDirs(), settings.get());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _merge(
			String charsetName, boolean copyAllowed, File destinationDir,
			FileCollection destinationFiles, FileCollection sourceDirs,
			Map<String, MergePropertiesSetting> settings)
		throws IOException {

		for (File destinationFile : destinationFiles) {
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

			if ((sourceFiles.size() == 1) && copyAllowed) {
				Iterator<File> iterator = sourceFiles.iterator();

				File sourceFile = iterator.next();

				Files.copy(
					sourceFile.toPath(), destinationFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			}
			else {
				_merge(charsetName, destinationFile, sourceFiles, settings);
			}
		}
	}

	private void _merge(
			String charsetName, File destinationFile, Set<File> sourceFiles,
			Map<String, MergePropertiesSetting> settings)
		throws IOException {

		Charset charset = Charset.forName(charsetName);

		Properties mergedProperties = new Properties() {

			@Override
			public Enumeration<Object> keys() {
				Set<Object> keys = super.keySet();

				return Collections.enumeration(new TreeSet<Object>(keys));
			}

		};

		for (File sourceFile : sourceFiles) {
			if (!sourceFile.exists()) {
				continue;
			}

			Properties sourceProperties = new Properties();

			try (BufferedReader bufferedReader = Files.newBufferedReader(
					sourceFile.toPath(), charset)) {

				sourceProperties.load(bufferedReader);
			}

			MergePropertiesSetting mergePropertiesSetting = settings.get(
				MergePropertiesUtil.getSettingName(sourceFile));

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

}