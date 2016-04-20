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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
public class UpdateVersionTask extends DefaultTask {

	public static final String VERSION_PLACEHOLDER = "[$VERSION$]";

	public int getMacroIncrement() {
		return _macroIncrement;
	}

	public int getMicroIncrement() {
		return _microIncrement;
	}

	public int getMinorIncrement() {
		return _minorIncrement;
	}

	@Input
	public String getNewVersion() {
		VersionNumber versionNumber = VersionNumber.parse(getVersion());

		VersionNumber newVersionNumber = new VersionNumber(
			versionNumber.getMajor() + getMacroIncrement(),
			versionNumber.getMinor() + getMinorIncrement(),
			versionNumber.getMicro() + getMicroIncrement(),
			versionNumber.getQualifier());

		return newVersionNumber.toString();
	}

	@Input
	@SkipWhenEmpty
	public Map<Object, Object> getPatterns() {
		return _patterns;
	}

	@Input
	public String getVersion() {
		return GradleUtil.toString(_version);
	}

	public UpdateVersionTask pattern(Object file, Object pattern) {
		_patterns.put(file, pattern);

		return this;
	}

	public UpdateVersionTask patterns(Map<?, ?> patterns) {
		_patterns.putAll(patterns);

		return this;
	}

	public void setMacroIncrement(int macroIncrement) {
		_macroIncrement = macroIncrement;
	}

	public void setMicroIncrement(int microIncrement) {
		_microIncrement = microIncrement;
	}

	public void setMinorIncrement(int minorIncrement) {
		_minorIncrement = minorIncrement;
	}

	public void setPatterns(Map<?, ?> patterns) {
		_patterns.clear();

		patterns(patterns);
	}

	public void setVersion(Object version) {
		_version = version;
	}

	@TaskAction
	public void updateVersion() throws IOException {
		String version = getVersion();
		String newVersion = getNewVersion();

		for (Map.Entry<Object, Object> entry : _patterns.entrySet()) {
			File file = GradleUtil.toFile(getProject(), entry.getKey());
			String pattern = GradleUtil.toString(entry.getValue());

			String oldSub = pattern.replace(VERSION_PLACEHOLDER, version);
			String newSub = pattern.replace(VERSION_PLACEHOLDER, newVersion);

			update(file, oldSub, newSub);
		}
	}

	protected void update(File file, String oldSub, String newSub)
		throws IOException {

		Project project = getProject();

		if (!file.exists()) {
			if (_logger.isInfoEnabled()) {
				_logger.info("Unable to find " + project.relativePath(file));
			}

			return;
		}

		Path path = file.toPath();

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		String newContent = content.replace(oldSub, newSub);

		if (content.equals(newContent)) {
			if (_logger.isWarnEnabled()) {
				_logger.warn("Unable to update " + project.relativePath(file));
			}

			return;
		}

		Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));

		if (_logger.isLifecycleEnabled()) {
			_logger.lifecycle("Updated " + project.relativePath(file));
		}
	}

	private static final Logger _logger = Logging.getLogger(
		UpdateVersionTask.class);

	private int _macroIncrement;
	private int _microIncrement = 1;
	private int _minorIncrement;
	private final Map<Object, Object> _patterns = new LinkedHashMap<>();
	private Object _version;

}