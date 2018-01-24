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

package com.liferay.gradle.plugins.baseline;

import com.liferay.gradle.plugins.baseline.internal.util.GradleUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.concurrent.Callable;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class BaselineConfigurationExtension {

	public BaselineConfigurationExtension(Project project) {
		_project = project;

		_lowestMajorVersion = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				File file = _project.file(".lfrbuild-lowest-major-version");

				file = GradleUtil.getProperty(
					_project, "baseline.lowest.major.version.file", file);

				if (!file.exists()) {
					return null;
				}

				String content = new String(
					Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

				return Integer.valueOf(content);
			}

		};
	}

	public String getLowestBaselineVersion() {
		return GradleUtil.toString(_lowestBaselineVersion);
	}

	public Integer getLowestMajorVersion() {
		return GradleUtil.toInteger(_lowestMajorVersion);
	}

	public boolean isAllowMavenLocal() {
		return _allowMavenLocal;
	}

	public boolean isLowestMajorVersionRequired() {
		return GradleUtil.toBoolean(_lowestMajorVersionRequired);
	}

	public void setAllowMavenLocal(boolean allowMavenLocal) {
		_allowMavenLocal = allowMavenLocal;
	}

	public void setLowestBaselineVersion(Object lowestBaselineVersion) {
		_lowestBaselineVersion = lowestBaselineVersion;
	}

	public void setLowestMajorVersion(Object lowestMajorVersion) {
		_lowestMajorVersion = lowestMajorVersion;
	}

	public void setLowestMajorVersionRequired(
		Object lowestMajorVersionRequired) {

		_lowestMajorVersionRequired = lowestMajorVersionRequired;
	}

	private boolean _allowMavenLocal;
	private Object _lowestBaselineVersion = "1.0.0";
	private Object _lowestMajorVersion;
	private Object _lowestMajorVersionRequired;
	private final Project _project;

}