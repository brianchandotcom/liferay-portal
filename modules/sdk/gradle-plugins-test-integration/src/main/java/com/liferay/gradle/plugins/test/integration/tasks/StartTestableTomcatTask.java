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

package com.liferay.gradle.plugins.test.integration.tasks;

import com.liferay.gradle.plugins.test.integration.util.GradleUtil;

import java.io.File;
import java.io.OutputStream;

import java.util.concurrent.Callable;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

import org.zeroturnaround.exec.StartedProcess;

/**
 * @author Andrea Di Giorgi
 */
public class StartTestableTomcatTask extends StartAppServerTask {

	@Input
	@Optional
	public File getLiferayHome() {
		return GradleUtil.toFile(getProject(), _liferayHome);
	}

	@Input
	public boolean isDeleteLiferayHome() {
		return _deleteLiferayHome;
	}

	public void setDeleteLiferayHome(boolean deleteLiferayHome) {
		_deleteLiferayHome = deleteLiferayHome;
	}

	public void setLiferayHome(Object liferayHome) {
		_liferayHome = liferayHome;
	}

	@Override
	public void startAppServer() throws Exception {
		if (isDeleteLiferayHome()) {
			deleteLiferayHome();
		}

		super.startAppServer();
	}

	protected void deleteLiferayHome() {
		Project project = getProject();

		File liferayHome = getLiferayHome();

		if (liferayHome == null) {
			throw new InvalidUserDataException(
				"No value has been specified for property 'liferayHome'.");
		}

		project.delete(
			new File(liferayHome, "data"), new File(liferayHome, "logs"),
			new File(liferayHome, "osgi/state"),
			new File(liferayHome, "portal-setup-wizard.properties"));
	}

	@Override
	protected void waitForStarted(
		StartedProcess startedProcess, final OutputStream outputStream) {

		waitFor(
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					String output = outputStream.toString();

					if (output.contains("Server startup in")) {
						return true;
					}

					return false;
				}

			});
	}

	private boolean _deleteLiferayHome = true;
	private Object _liferayHome;

}