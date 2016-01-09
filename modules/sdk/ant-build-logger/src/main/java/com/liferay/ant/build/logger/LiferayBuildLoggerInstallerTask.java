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

package com.liferay.ant.build.logger;

import java.lang.reflect.Field;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author William Newbury
 * @author Shuyang Zhou
 */
public class LiferayBuildLoggerInstallerTask extends Task {

	@Override
	public void execute() throws BuildException {
		Project currentProject = getProject();

		try {
			synchronized(_listenersLockField.get(currentProject)) {
				for (BuildListener buildListener :
						currentProject.getBuildListeners()) {

					if (buildListener.getClass() != DefaultLogger.class) {
						continue;
					}

					boolean buildPerformanceLoggerEnabled =
						isBuildPerformanceLoggerEnabled();

					currentProject.removeBuildListener(buildListener);

					currentProject.addBuildListener(
						new LiferayBuildLogger(buildListener));

					if (buildPerformanceLoggerEnabled) {
						currentProject.addBuildListener(
							new LiferayBuildPerformanceLogger());
					}
				}
			}
		}
		catch (IllegalAccessException iae) {
			throw new BuildException(
				"Unable to access listenersLock field of " + currentProject,
				iae);
		}
	}

	private boolean isBuildPerformanceLoggerEnabled() {
		Project project = getProject();

		String buildPerformanceLoggerEnabled = project.getProperty(
			"build.performance.logger.enabled");

		if ((buildPerformanceLoggerEnabled != null) &&
			buildPerformanceLoggerEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	private static final Field _listenersLockField;

	static {
		try {
			_listenersLockField = Project.class.getDeclaredField(
				"listenersLock");

			_listenersLockField.setAccessible(true);
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

}