/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;

import java.io.File;
import java.io.OutputStream;

import java.util.concurrent.Callable;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

import org.zeroturnaround.exec.StartedProcess;

/**
 * @author Andrea Di Giorgi
 */
public class StartTestableTomcatTask extends StartAppServerTask {

	@Internal
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
			_deleteLiferayHome();
		}

		super.startAppServer();
	}

	@Override
	protected void waitForStarted(
		StartedProcess startedProcess, OutputStream outputStream) {

		waitFor(
			new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					if (isReachable()) {
						return true;
					}

					return false;
				}

			});

		super.waitForStarted(startedProcess, outputStream);
	}

	private void _deleteLiferayHome() {
		File liferayHome = getLiferayHome();

		if (liferayHome == null) {
			throw new InvalidUserDataException(
				"No value has been specified for property 'liferayHome'");
		}

		Project project = getProject();

		project.delete(
			new File(liferayHome, "data"), new File(liferayHome, "logs"),
			new File(liferayHome, "osgi/state"),
			new File(liferayHome, "portal-setup-wizard.properties"));
	}

	private boolean _deleteLiferayHome = true;
	private Object _liferayHome;

}