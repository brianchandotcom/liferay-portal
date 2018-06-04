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

import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;

/**
 * @author Andrea Di Giorgi
 */
public class MergePropertiesTask extends BaseMergeTask {

	public MergePropertiesTask() {
		Project project = getProject();

		_mergePropertiesSettings = project.container(
			MergePropertiesSetting.class);
	}

	@Input
	public String getCharsetName() {
		return GradleUtil.toString(_charsetName);
	}

	@Override
	public String getPattern() {
		return _PATTERN;
	}

	public NamedDomainObjectContainer<MergePropertiesSetting> getSettings() {
		return _mergePropertiesSettings;
	}

	public void setCharsetName(Object charsetName) {
		_charsetName = charsetName;
	}

	public MergePropertiesSetting setting(Object object, Closure<Void> closure)
		throws IOException {

		File dir = GradleUtil.toFile(getProject(), object);

		return _mergePropertiesSettings.create(_getSettingName(dir), closure);
	}

	@Override
	protected boolean isCopyAllowed() {
		if (_mergePropertiesSettings.isEmpty()) {
			return true;
		}

		return false;
	}

	@Override
	protected void merge(Set<File> sourceFiles, File destinationFile)
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

	private static final String _PATTERN = "*.properties";

	private Object _charsetName = StandardCharsets.UTF_8.name();
	private final NamedDomainObjectContainer<MergePropertiesSetting>
		_mergePropertiesSettings;

}