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

package com.liferay.gradle.plugins.workspace.task;

import java.util.Objects;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Simon Jiang
 */
public class VerifyProductTask extends DefaultTask {

	@Input
	@Optional
	public String getBundleUrl() {
		return _bundleUrl;
	}

	@Input
	@Optional
	public String getDockerImageLiferay() {
		return _dockerImageLiferay;
	}

	@Input
	@Optional
	public String getErrorMessage() {
		return _errorMessage;
	}

	@Input
	@Optional
	public String getTargetPlatformVersion() {
		return _targetPlatformVersion;
	}

	public void setBundleUrl(String bundleUrl) {
		_bundleUrl = bundleUrl;
	}

	public void setDockerImageLiferay(String dockerImageLiferay) {
		_dockerImageLiferay = dockerImageLiferay;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setTargetPlatformVersion(String targetPlatformVersion) {
		_targetPlatformVersion = targetPlatformVersion;
	}

	@TaskAction
	public void verifyProduct() throws Exception {
		if (!_errorMessage.isEmpty()) {
			throw new GradleException(_errorMessage);
		}

		if (Objects.isNull(_bundleUrl) || _bundleUrl.isEmpty()) {
			throw new GradleException("Liferay bundle URL should not be null");
		}

		if (Objects.isNull(_dockerImageLiferay) ||
			_dockerImageLiferay.isEmpty()) {

			throw new GradleException(
				"Liferay Docker image name should not be null");
		}
	}

	private String _bundleUrl = "";
	private String _dockerImageLiferay = "";
	private String _errorMessage = "";
	private String _targetPlatformVersion = "";

}