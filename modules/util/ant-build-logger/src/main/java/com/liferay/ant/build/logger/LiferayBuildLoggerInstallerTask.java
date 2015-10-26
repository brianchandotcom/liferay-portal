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

import org.apache.tools.ant.BuildEvent;
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

					if (buildListener.getClass() == DefaultLogger.class) {
						currentProject.removeBuildListener(buildListener);

						currentProject.addBuildListener(
							new LiferayBuildLogger(buildListener));
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

	private class LiferayBuildLogger implements BuildListener {

		@Override
		public void buildFinished(BuildEvent be) {
			_buildListener.buildFinished(be);
		}

		@Override
		public void buildStarted(BuildEvent be) {
			_buildListener.buildStarted(be);
		}

		@Override
		public void messageLogged(BuildEvent be) {
			String message = be.getMessage();

			if (message.startsWith("Trying to override old definition of ")) {
				be.setMessage(message, Project.MSG_DEBUG);
			}

			_buildListener.messageLogged(be);
		}

		@Override
		public void targetFinished(BuildEvent be) {
			_buildListener.targetFinished(be);
		}

		@Override
		public void targetStarted(BuildEvent be) {
			_buildListener.targetStarted(be);
		}

		@Override
		public void taskFinished(BuildEvent be) {
			_buildListener.taskFinished(be);
		}

		@Override
		public void taskStarted(BuildEvent be) {
			_buildListener.taskStarted(be);
		}

		private LiferayBuildLogger(BuildListener buildListener) {
			_buildListener = buildListener;
		}

		private final BuildListener _buildListener;

	}

}