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

package com.liferay.ant.logger;

import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author William Newbury
 */
public class LiferayLoggerTask extends Task {

	@Override
	public void execute() {
		Project currentProject = getProject();

		for (BuildListener buildListener : currentProject.getBuildListeners()) {
			if (buildListener.getClass() == DefaultLogger.class) {
				currentProject.removeBuildListener(buildListener);
				currentProject.addBuildListener(
					new LiferayBuildLogger((DefaultLogger)buildListener));
			}
		}
	}

	private class LiferayBuildLogger extends DefaultLogger {

		public LiferayBuildLogger(DefaultLogger defaultLogger) {
			_defaultLogger = defaultLogger;
		}

		@Override
		public void buildFinished(BuildEvent be) {
			_defaultLogger.buildFinished(be);
		}

		@Override
		public void buildStarted(BuildEvent be) {
			_defaultLogger.buildStarted(be);
		}

		@Override
		public void messageLogged(BuildEvent be) {
			String message = be.getMessage();

			if (message.startsWith("Trying to override")) {
				return;
			}

			_defaultLogger.messageLogged(be);
		}

		@Override
		public void setEmacsMode(boolean bln) {
			_defaultLogger.setEmacsMode(bln);
		}

		@Override
		public void setErrorPrintStream(PrintStream stream) {
			_defaultLogger.setErrorPrintStream(stream);
		}

		@Override
		public void setMessageOutputLevel(int i) {
			_defaultLogger.setMessageOutputLevel(i);
		}

		@Override
		public void setOutputPrintStream(PrintStream stream) {
			_defaultLogger.setOutputPrintStream(stream);
		}

		@Override
		public void targetFinished(BuildEvent be) {
			_defaultLogger.targetFinished(be);
		}

		@Override
		public void targetStarted(BuildEvent be) {
			_defaultLogger.targetStarted(be);
		}

		@Override
		public void taskFinished(BuildEvent be) {
			_defaultLogger.taskFinished(be);
		}

		@Override
		public void taskStarted(BuildEvent be) {
			_defaultLogger.taskStarted(be);
		}

		private final DefaultLogger _defaultLogger;

	}

}